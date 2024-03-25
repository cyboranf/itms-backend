package com.pink.itms.service;

import com.pink.itms.dto.task.TaskRequestDTO;
import com.pink.itms.dto.task.TaskResponseDTO;
import com.pink.itms.mapper.TaskMapper;
import com.pink.itms.model.Task;
import com.pink.itms.repository.TaskRepository;
import com.pink.itms.validation.TaskValidator;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskValidator taskValidator;
    private final TaskMapper taskMapper;

    public TaskService(TaskRepository taskRepository, TaskValidator taskValidator, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskValidator = taskValidator;
        this.taskMapper = taskMapper;
    }

    public TaskResponseDTO createTask(TaskRequestDTO taskRequestDTO) {
        taskValidator.validateCreate(taskRequestDTO);
        Task task = taskMapper.ToEntity(taskRequestDTO);
        Task savedTask = taskRepository.save(task);
        return taskMapper.toDto(savedTask);
    }
}
