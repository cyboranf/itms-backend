package com.pink.itms.mapper;

import com.pink.itms.dto.task.TaskRequestDTO;
import com.pink.itms.dto.task.TaskResponseDTO;
import com.pink.itms.model.Task;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class TaskMapper {

    public Task ToEntity(TaskRequestDTO taskRequestDTO) {
        Task task = new Task();
        task.setId(taskRequestDTO.getId());
        task.setName(taskRequestDTO.getName());
        task.setDescription(taskRequestDTO.getDescription());
        task.setState(taskRequestDTO.getState());
        task.setPriority(taskRequestDTO.getPriority());
        // Assuming TaskType is already set in taskRequestDTO
        // task.setType(taskRequestDTO.getType_id());
        // Assuming creationDate, startDate, and endDate are set separately
        // task.setCreationDate(taskRequestDTO.getCreationDate());
        // task.setStartDate(taskRequestDTO.getStartDate());
        // task.setEndDate(taskRequestDTO.getEndDate());
        return task;
    }

    public TaskResponseDTO toDto(Task task) {
        TaskResponseDTO responseDTO = new TaskResponseDTO();
        responseDTO.setId(task.getId());
        responseDTO.setName(task.getName());
        responseDTO.setDescription(task.getDescription());
        responseDTO.setState(task.getState());
        responseDTO.setPriority(task.getPriority());
        responseDTO.setCreationDate(task.getCreationDate());
        responseDTO.setStartDate(task.getStartDate());
        responseDTO.setEndDate(task.getEndDate());
        responseDTO.setUserIds(task.getUsers().stream().map(user -> user.getId()).collect(Collectors.toSet()));
        responseDTO.setProductIds(task.getProducts().stream().map(product -> product.getId()).collect(Collectors.toSet()));
        responseDTO.setWarehouseIds(task.getWarehouses().stream().map(warehouse -> warehouse.getId()).collect(Collectors.toSet()));
        return responseDTO;
    }
}
