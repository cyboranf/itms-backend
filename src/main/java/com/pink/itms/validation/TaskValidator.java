package com.pink.itms.validation;

import com.pink.itms.dto.task.TaskRequestDTO;
import com.pink.itms.dto.task.TaskResponseDTO;
import com.pink.itms.exception.user.ExistingUsernameException;
import org.springframework.stereotype.Component;

@Component
public class TaskValidator {

    //TODO jak bedzie reszta class to wtedy poprawic walidacje np. czy type istnieje itp :D
    public void validateCreate(TaskRequestDTO taskRequestDTO) {
        if (taskRequestDTO.getName() == null || taskRequestDTO.getName().isEmpty()) {
            throw new IllegalArgumentException("Task name must not be empty");
        }

        if (taskRequestDTO.getDescription() == null || taskRequestDTO.getDescription().isEmpty()) {
            throw new IllegalArgumentException("Task description must not be empty");
        }

        if (taskRequestDTO.getState() <= 0) {
            throw new IllegalArgumentException("Task state must be a positive integer");
        }

        if (taskRequestDTO.getPriority() <= 0) {
            throw new IllegalArgumentException("Task priority must be a positive integer");
        }

        if (taskRequestDTO.getType_id() == null || taskRequestDTO.getType_id() <= 0) {
            throw new IllegalArgumentException("Task type ID must be specified and must be a positive integer");
        }

    }
}
