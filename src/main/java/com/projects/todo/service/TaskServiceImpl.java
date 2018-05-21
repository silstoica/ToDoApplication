package com.projects.todo.service;

import com.projects.todo.model.Task;
import com.projects.todo.repository.TaskRepository;
import com.projects.todo.security.ToDoUserDetails;
import com.projects.todo.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;
    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, UserService userService, UserDetailsServiceImpl userDetailsService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Task save(Task task){
        ToDoUserDetails currentUser = userDetailsService.getCurrentUser();
        task.setUser(userService.getOne(currentUser.getId()));

        return taskRepository.save(task);
    }

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public Task getOne(Long id) {
        return taskRepository.getOne(id);
    }

    @Override
    public void delete(long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public List<Task> findAllByUser() {
        ToDoUserDetails currentUser = userDetailsService.getCurrentUser();
        return taskRepository.findAllByUserId(currentUser.getId());
    }

    @Override
    public Task update(Task task) {
        Task taskFromDB = taskRepository.getOne(task.getId());
        taskFromDB.setName(task.getName());
        taskFromDB.setDeadline(task.getDeadline());
        taskFromDB.setPriority(task.getPriority());
        taskFromDB.setStatus(task.getStatus());
        taskRepository.save(taskFromDB);
        return taskFromDB;
    }
}
