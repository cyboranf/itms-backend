package com.pink.itms.dto.task;
import com.pink.itms.dto.user.UserResponseWithoutTasksDTO;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

import com.pink.itms.dto.product.ProductResponseDTO;
import com.pink.itms.dto.user.UserResponseDTO;

@Data
public class TaskResponseDTO {
    private Long id;
    private String name;
    private String description;
    private Integer state;
    private Integer priority;
    private LocalDateTime creationDate;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Set<UserResponseWithoutTasksDTO> users;
    private Set<ProductResponseDTO> products;
    //TODO jak warehouse bedzie istnial
//    private Set<WarehouseResponseDTO> warehouses;
}
