package com.pink.itms.controller;

import com.pink.itms.dto.taskType.TaskTypeRequestDTO;
import com.pink.itms.dto.taskType.TaskTypeResponseDTO;
import com.pink.itms.service.TaskTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/tasks/types/{id}")
    public ResponseEntity<?> editTaskType(@PathVariable long id, @RequestBody TaskTypeRequestDTO taskTypeRequestDTO) {
        try {
            TaskTypeResponseDTO taskTypeResponseDTO = taskTypeService.editTaskType(id, taskTypeRequestDTO);
            return new ResponseEntity<>(taskTypeResponseDTO, HttpStatus.OK);
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/tasks/types/{id}")
    public ResponseEntity<?> deleteTaskType(@PathVariable long id) {
        try {
            taskTypeService.deleteTaskType(id);
            return ResponseEntity.ok().build();
        } catch(Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/tasks/types")
    public ResponseEntity<?> getallTasksTypes() {
        return new ResponseEntity<>(taskTypeService.getAll(), HttpStatus.OK);
    }
}
