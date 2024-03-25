package com.pink.itms.repository;

import com.pink.itms.model.Role;
import com.pink.itms.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}

