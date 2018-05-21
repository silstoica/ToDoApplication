package com.projects.todo.service;

import com.projects.todo.enums.Priority;
import com.projects.todo.enums.Status;
import com.projects.todo.model.Task;
import com.projects.todo.model.User;
import com.projects.todo.repository.TaskRepository;
import com.projects.todo.security.ToDoUserDetails;
import com.projects.todo.security.UserDetailsServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class TaskServiceImplTest {

    private TaskService taskService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserService userService;

    @Mock
    private UserDetailsServiceImpl userDetailsService;
    private User userDB;
    private ToDoUserDetails currentUser;

    @Before
    public void setUp(){
        taskService = new TaskServiceImpl(taskRepository, userService, userDetailsService);
        currentUser = new ToDoUserDetails();
        currentUser.setId(1L);
        currentUser.setUsername("test-user");
        when(userDetailsService.getCurrentUser()).thenReturn(currentUser);

        userDB = new User();
        when(userService.getOne(currentUser.getId())).thenReturn(userDB);
    }


    @Test
    public void save() {
        Task task = new Task();
        task.setName("new task");
        Date deadline = new Date();
        task.setDeadline(deadline);
        task.setPriority(Priority.HIGH);
        task.setStatus(Status.TO_DO);

        when(taskRepository.save(any(Task.class))).then(a -> {
            task.setId(1l);
            return task;
        });

        Task savedTask = taskService.save(task);

        assertNotNull(savedTask);
        assertEquals(savedTask.getUser().getId(), userDB.getId());
        assertEquals(Long.valueOf(1l), savedTask.getId());
        assertEquals("new task", savedTask.getName());
        assertEquals(deadline, savedTask.getDeadline());
        assertEquals(Priority.HIGH, savedTask.getPriority());
        assertEquals(Status.TO_DO, savedTask.getStatus());
    }

    @Test
    public void update() {
        Task task = new Task();
        task.setId(1l);
        task.setName("new task");
        Date deadline = new Date();
        task.setDeadline(deadline);
        task.setPriority(Priority.HIGH);
        task.setStatus(Status.TO_DO);

        when(taskRepository.save(any(Task.class))).thenReturn(task);

        when(taskRepository.getOne(1l)).thenReturn(task);
        Task savedTask = taskService.update(task);

        assertNotNull(savedTask);
        assertEquals(Long.valueOf(1l), savedTask.getId());
        assertEquals("new task", savedTask.getName());
        assertEquals(deadline, savedTask.getDeadline());
        assertEquals(Priority.HIGH, savedTask.getPriority());
        assertEquals(Status.TO_DO, savedTask.getStatus());
    }
}