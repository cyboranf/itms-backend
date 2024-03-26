package com.pink.itms.service;

import com.pink.itms.dto.taskType.TaskTypeRequestDTO;
import com.pink.itms.dto.taskType.TaskTypeResponseDTO;
import com.pink.itms.exception.taskType.ExistingNameException;
import com.pink.itms.exception.taskType.TaskTypeNotFoundException;
import com.pink.itms.mapper.TaskTypeMapper;
import com.pink.itms.model.TaskType;
import com.pink.itms.repository.TaskRepository;
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

    /**
     *  Creates Entity from request object
     * @param taskTypeRequestDTO request object for entity creation
     * @throws com.pink.itms.exception.taskType.ExistingNameException if type with this name already exists
     * @return {@link TaskTypeResponseDTO} response from created Entity
     */
    public TaskTypeResponseDTO createTaskType(TaskTypeRequestDTO taskTypeRequestDTO) {
        taskTypeValidator.taskTypeValidation(taskTypeRequestDTO);
        TaskType taskType = taskTypeMapper.toEntity(taskTypeRequestDTO);
        TaskType savedTaskType = taskTypeRepository.save(taskType);

        return  taskTypeMapper.toDto(savedTaskType);
    }

    /**
     * Deletes entity with given id
     * @param id id of task type to delete
     * @throws {@link TaskTypeNotFoundException} if task type doesn't exist
     */
    public void deleteTaskType(long id) {
        try {
            taskTypeRepository.deleteById(id);
        } catch(Exception e) {
            throw new TaskTypeNotFoundException("Task type with id " + id + " doesn't exists.");
        }
    }

    /**
     * Edits exisiting entity pointed by given id
     * @param id of entity to edit
     * @param taskTypeRequestDTO values to replace with
     * @return {@link TaskTypeResponseDTO} - rosponse from changed entity
     */
    public TaskTypeResponseDTO editTaskType(Long id, TaskTypeRequestDTO taskTypeRequestDTO) {
        if (taskTypeRepository.findByName(taskTypeRequestDTO.getName()).isPresent()) {
            throw new ExistingNameException("Task type " + taskTypeRequestDTO.getName() + " already exist.");
        }

        TaskType taskType = taskTypeRepository.getById(id);
        taskType.setName(taskTypeRequestDTO.getName());

        return taskTypeMapper.toDto(taskType);
    }
}
