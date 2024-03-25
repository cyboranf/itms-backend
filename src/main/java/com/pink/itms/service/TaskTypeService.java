package com.pink.itms.service;

import com.pink.itms.dto.taskType.TaskTypeRequestDTO;
import com.pink.itms.dto.taskType.TaskTypeResponseDTO;
import com.pink.itms.mapper.TaskTypeMapper;
import com.pink.itms.model.TaskType;
import com.pink.itms.repository.TaskTypeRepository;
import com.pink.itms.validation.TaskTypeValidator;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class TaskTypeService {
    TaskTypeRepository taskTypeRepository;
    TaskTypeMapper taskTypeMapper;
    TaskTypeValidator taskTypeValidator;

    public TaskTypeService(TaskTypeRepository taskTypeRepository, TaskTypeMapper taskTypeMapper, TaskTypeValidator taskTypeValidator) {
        this.taskTypeRepository = taskTypeRepository;
        this.taskTypeMapper = taskTypeMapper;
        this.taskTypeValidator = taskTypeValidator;
    }

    public TaskTypeResponseDTO createTaskType(TaskTypeRequestDTO taskTypeRequestDTO) {
        taskTypeValidator.taskTypeValidation(taskTypeRequestDTO);
        TaskType taskType = taskTypeMapper.toEntity(taskTypeRequestDTO);
        TaskType savedTaskType = taskTypeRepository.save(taskType);

        return  taskTypeMapper.toDto(savedTaskType);
    }
}
