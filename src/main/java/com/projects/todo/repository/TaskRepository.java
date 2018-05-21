package com.projects.todo.repository;

import com.projects.todo.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("SELECT t FROM Task t where t.user.id = :userId")
    List<Task> findAllByUserId(@Param("userId") Long userId);
}
