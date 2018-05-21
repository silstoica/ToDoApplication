package com.projects.todo.service;

import com.projects.todo.model.User;

public interface UserService {
    User registerUser(User user);

    User getOne(Long id);
}
