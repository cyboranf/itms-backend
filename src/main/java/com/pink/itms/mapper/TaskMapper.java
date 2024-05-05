package com.pink.itms.mapper;

import com.pink.itms.dto.task.TaskRequestDTO;
import com.pink.itms.dto.task.TaskResponseDTO;
import com.pink.itms.model.Task;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class TaskMapper {
    private final UserMapper userMapper;
    private final ProductMapper productMapper;
    private final WarehouseMapper warehouseMapper;

    public TaskMapper(UserMapper userMapper, ProductMapper productMapper, WarehouseMapper warehouseMapper) {
        this.userMapper = userMapper;
        this.productMapper = productMapper;
        this.warehouseMapper = warehouseMapper;
    }

    public Task toEntity(TaskRequestDTO taskRequestDTO) {
        Task task = new Task();
        task.setId(taskRequestDTO.getId());
        task.setName(taskRequestDTO.getName());
        task.setDescription(taskRequestDTO.getDescription());
        task.setState(taskRequestDTO.getState());
        task.setPriority(taskRequestDTO.getPriority());
        task.setIsActive(true);

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
        responseDTO.setIsActive(task.getIsActive());

        if (task.getUsers() != null) {
            responseDTO.setUsers(task.getUsers().stream()
                    .map(userMapper::entityToDtoWithoutTasks)
                    .collect(Collectors.toSet()));
        }

        if (task.getProducts() != null) {
            responseDTO.setProducts(task.getProducts().stream()
                    .map(productMapper::toDto)
                    .collect(Collectors.toSet()));
        }

        if (task.getWarehouses() != null) {
            responseDTO.setWarehouses(task.getWarehouses().stream()
                    .map(warehouseMapper::toDto)
                    .collect(Collectors.toSet()));
        }

        return responseDTO;
    }
}
