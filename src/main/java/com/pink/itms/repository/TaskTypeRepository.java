package com.pink.itms.repository;

import com.pink.itms.model.TaskType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskTypeRepository extends JpaRepository<TaskType, Long> {
    Optional<TaskType> findByName(String name);

    void deleteById(Long aLong);
    TaskType getById(Long along);
}
