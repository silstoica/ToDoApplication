package com.projects.todo.controller;

import com.projects.todo.model.User;
import com.projects.todo.service.UserService;
import com.projects.todo.validation.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/register")
public class RegisterController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator validator;

    @GetMapping
    public String showRegisterPage(Model model){
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result){
        validator.validate(user, result);
        if(result.hasErrors()){
            return "register";
        }
        userService.registerUser(user);
        return "redirect:/login";
    }
}
