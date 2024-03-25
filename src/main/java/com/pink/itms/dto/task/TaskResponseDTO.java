package com.pink.itms.dto.task;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

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
    private Set<Long> userIds;
    private Set<Long> productIds;
    private Set<Long> warehouseIds;
}