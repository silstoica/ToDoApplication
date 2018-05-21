package com.projects.todo.validation;

import com.projects.todo.model.User;
import com.projects.todo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class UserValidator implements Validator {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User)target;
        if(userRepository.findByUsername(user.getUsername()) != null){
            errors.rejectValue("username", "not unique", "Username already taken");
        }
    }
}
