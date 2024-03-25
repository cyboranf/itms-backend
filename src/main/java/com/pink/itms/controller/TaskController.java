package com.pink.itms.controller;

import com.pink.itms.dto.task.TaskRequestDTO;
import com.pink.itms.dto.task.TaskResponseDTO;
import com.pink.itms.repository.TaskRepository;
import com.pink.itms.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TaskController {
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody TaskRequestDTO taskRequestDTO) {
        try{
            TaskResponseDTO taskResponseDTO = taskService.createTask(taskRequestDTO);
            return new ResponseEntity<>(taskResponseDTO, HttpStatus.CREATED);
        }catch(Exception e ){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
