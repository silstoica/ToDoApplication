package com.projects.todo.controller;

import com.projects.todo.model.Task;
import com.projects.todo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public String list(Model model){
        List <Task> tasks = taskService.findAllByUser();
        model.addAttribute("tasks", tasks);
        return "tasks-list";
    }

    @GetMapping("/new")
    public String showNewTaskPage(Model model){
        model.addAttribute("task",new Task());
        return "new-task";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("task") Task task, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "new-task";
        }
        taskService.save(task);
        return "redirect:/tasks";
    }

    @PostMapping("/update/{id}")
    public String update(@ModelAttribute("task") Task task, BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            return "edit-task";
        }

        taskService.update(task);
        return "redirect:/tasks";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @ModelAttribute("task") Task task, @PathVariable("id") long id) {
        model.addAttribute("task",taskService.getOne(id));
        return "edit-task";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id){
        taskService.delete(id);
        return "redirect:/tasks";
    }
}
