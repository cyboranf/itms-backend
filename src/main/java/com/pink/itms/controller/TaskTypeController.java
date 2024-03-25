package com.pink.itms.controller;

import com.pink.itms.dto.taskType.TaskTypeRequestDTO;
import com.pink.itms.dto.taskType.TaskTypeResponseDTO;
import com.pink.itms.service.TaskTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TaskTypeController {
    TaskTypeService taskTypeService;

    public TaskTypeController(TaskTypeService taskTypeService) {this.taskTypeService = taskTypeService;}

    @PostMapping("/tasks/types")
    public ResponseEntity<?> createTaskType(@RequestBody TaskTypeRequestDTO taskTypeRequestDTO) {
        try {
            TaskTypeResponseDTO taskTypeResponseDTO = taskTypeService.createTaskType(taskTypeRequestDTO);
            return new ResponseEntity<>(taskTypeResponseDTO, HttpStatus.CREATED);
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
