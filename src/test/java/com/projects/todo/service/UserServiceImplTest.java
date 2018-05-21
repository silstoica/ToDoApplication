package com.projects.todo.service;

import com.projects.todo.model.User;
import com.projects.todo.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    private BCryptPasswordEncoder passwordEncoder;
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Before
    public void setUp(){
        passwordEncoder = new BCryptPasswordEncoder();
        userService = new UserServiceImpl(userRepository, passwordEncoder);
    }


    @Test
    public void registerUserTest() {
        User user = new User();
        user.setUsername("user");
        user.setPassword("1234");

        when(userRepository.save(any(User.class))).then(a -> {

            user.setId(1L);
            return user;
        });
        User registeredUser = userService.registerUser(user);

        assertNotNull(registeredUser);
        assertNotNull(registeredUser.getId());

        assertEquals(registeredUser.getUsername(), user.getUsername());
        assertEquals(registeredUser.getPassword(), user.getPassword());
    }
}