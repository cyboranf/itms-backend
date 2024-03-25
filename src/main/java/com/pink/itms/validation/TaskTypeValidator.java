package com.pink.itms.validation;

import com.pink.itms.dto.taskType.TaskTypeRequestDTO;
import com.pink.itms.exception.taskType.ExistingNameException;
import com.pink.itms.repository.TaskTypeRepository;
import org.springframework.stereotype.Component;

@Component
public class TaskTypeValidator {
    TaskTypeRepository taskTypeRepository;
    public TaskTypeValidator(TaskTypeRepository taskTypeRepository) { this.taskTypeRepository = taskTypeRepository;}

    public void taskTypeValidation(TaskTypeRequestDTO taskTypeRequestDTO) {
        if (taskTypeRepository.findByName(taskTypeRequestDTO.getName()).isPresent()){
            throw new ExistingNameException("Task type " + taskTypeRequestDTO.getName() + " already exists.");
        }
    }
}
