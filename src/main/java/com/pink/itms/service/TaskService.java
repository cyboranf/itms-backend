package com.pink.itms.service;

import com.pink.itms.dto.task.TaskRequestDTO;
import com.pink.itms.dto.task.TaskResponseDTO;
import com.pink.itms.mapper.TaskMapper;
import com.pink.itms.model.Task;
import com.pink.itms.repository.TaskRepository;
import com.pink.itms.validation.TaskValidator;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

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

        Task task = taskMapper.toEntity(taskRequestDTO);
        task.setCreationDate(LocalDateTime.now());

        Task savedTask = taskRepository.save(task);
        return taskMapper.toDto(savedTask);
    }

    public TaskResponseDTO getTaskById(long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task with id = " + id + " does not exist"));
        return taskMapper.toDto(task);
    }

    public void deleteTask(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task with id = " + taskId + " does not exist"));

        taskRepository.delete(task);
    }

    /**
     * Get all tasks
     *
     * @return
     */
    public List<TaskResponseDTO> getAll() {
        return taskRepository.findAll()
                .stream()
                .map(taskMapper::toDto)
                .toList();
    }

    /**
     * @param taskId
     * @param taskRequestDTO
     * @return TaskResponseDTO
     */
    public TaskResponseDTO editTask(Long taskId, TaskRequestDTO taskRequestDTO) {
        Task task = taskValidator.validateUpdate(taskId, taskRequestDTO);
        Task savedTask = taskRepository.save(task);
        return taskMapper.toDto(savedTask);
    }
}
