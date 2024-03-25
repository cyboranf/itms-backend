package com.pink.itms.dto.task;

import lombok.Data;

@Data

public class TaskRequestDTO {
    private Long id;
    private String name;
    private String description;
    private int state;
    private int priority;
    private Long type_id;
}
