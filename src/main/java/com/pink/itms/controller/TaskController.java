package com.pink.itms.controller;

import com.pink.itms.dto.task.TaskRequestDTO;
import com.pink.itms.dto.task.TaskResponseDTO;
import com.pink.itms.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody TaskRequestDTO taskRequestDTO) {
        try {
            TaskResponseDTO taskResponseDTO = taskService.createTask(taskRequestDTO);
            return new ResponseEntity<>(taskResponseDTO, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable long id) {
        try {
            TaskResponseDTO taskResponseDTO = taskService.getTaskById(id);
            return ResponseEntity.ok(taskResponseDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); // Return 500 Internal Server Error for other exceptions
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable long id) {
        try {
            taskService.deleteTask(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); // Return 500 Internal Server Error for other exceptions
        }
    }

    /**
     * Get all tasks
     *
     * @return list of all tasks
     */
    @GetMapping("")
    public ResponseEntity<List<TaskResponseDTO>> getAll() {
        return ResponseEntity.ok(taskService.getAll());
    }

    /**
     * @param id
     * @param taskRequestDTO
     * @return edited task
     */
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> editTask(@PathVariable Long id, @RequestBody TaskRequestDTO taskRequestDTO) {
        TaskResponseDTO responseDTO = taskService.editTask(id, taskRequestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    /**
     * attaches product by given id to task of given id if said connection already exists do nothing.
     *
     * @param taskId id of task to be attached
     * @param productId id of product to be attached
     * @return returns response with status 200 if said connection was created or been created before and 404 if either
     * task or product couldn't be found by given id.
     */
    @PostMapping("/{taskId}/join/products/{productId}")
    public ResponseEntity<?> attachProduct(@PathVariable Long taskId, @PathVariable Long productId) {
        try {
            taskService.attachProduct(taskId, productId);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

        return ResponseEntity.ok().build();
    }
}
