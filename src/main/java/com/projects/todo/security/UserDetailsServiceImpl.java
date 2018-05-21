package com.projects.todo.security;

import com.projects.todo.model.User;
import com.projects.todo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User " + username + " was not found in the database");
        }
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().toString());
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(authority);

        ToDoUserDetails toDoUserDetails = new ToDoUserDetails();
        toDoUserDetails.setId(user.getId());
        toDoUserDetails.setUsername(user.getUsername());
        toDoUserDetails.setPassword(user.getPassword());
        toDoUserDetails.setAuthorities(authorities);

        return toDoUserDetails;
    }

    public ToDoUserDetails getCurrentUser() {
        return (ToDoUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
