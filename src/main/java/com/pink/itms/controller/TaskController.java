package com.pink.itms.controller;

import com.pink.itms.dto.task.TaskRequestDTO;
import com.pink.itms.dto.task.TaskResponseDTO;
import com.pink.itms.jwt.JwtTokenProvider;
import com.pink.itms.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;
    private final JwtTokenProvider jwtTokenProvider;

    public TaskController(TaskService taskService, JwtTokenProvider jwtTokenProvider) {
        this.taskService = taskService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody TaskRequestDTO taskRequestDTO, HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);

        if (token != null && jwtTokenProvider.validateToken(token) && jwtTokenProvider.getAuthentication(token).getAuthorities().contains(new SimpleGrantedAuthority("Admin"))) {
            try {
                TaskResponseDTO taskResponseDTO = taskService.createTask(taskRequestDTO);
                return new ResponseEntity<>(taskResponseDTO, HttpStatus.CREATED);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
        } else if (token == null || !jwtTokenProvider.validateToken(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable long id, HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);

        if (token != null && jwtTokenProvider.validateToken(token)) {
            try {
                TaskResponseDTO taskResponseDTO = taskService.getTaskById(id);
                return ResponseEntity.ok(taskResponseDTO);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); // Return 500 Internal Server Error for other exceptions
            }
        } else if (token == null || !jwtTokenProvider.validateToken(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable long id, HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);

        if (token != null && jwtTokenProvider.validateToken(token) && jwtTokenProvider.getAuthentication(token).getAuthorities().contains(new SimpleGrantedAuthority("Admin"))) {
            try {
                taskService.deleteTask(id);
                return ResponseEntity.ok().build();
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); // Return 500 Internal Server Error for other exceptions
            }
        } else if (token == null || !jwtTokenProvider.validateToken(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("")
    public ResponseEntity<List<TaskResponseDTO>> getAll(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);

        if (token != null && jwtTokenProvider.validateToken(token)) {
            return ResponseEntity.ok(taskService.getAll());
        } else if (token == null || !jwtTokenProvider.validateToken(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> editTask(@PathVariable Long id, @RequestBody TaskRequestDTO taskRequestDTO, HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);

        if (token != null && jwtTokenProvider.validateToken(token) && jwtTokenProvider.getAuthentication(token).getAuthorities().contains(new SimpleGrantedAuthority("Admin"))) {
            TaskResponseDTO responseDTO = taskService.editTask(id, taskRequestDTO);
            return ResponseEntity.ok(responseDTO);
        } else if (token == null || !jwtTokenProvider.validateToken(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/{taskId}/join/products/{productId}")
    public ResponseEntity<?> attachProduct(@PathVariable Long taskId, @PathVariable Long productId, HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);

        if (token != null && jwtTokenProvider.validateToken(token) && jwtTokenProvider.getAuthentication(token).getAuthorities().contains(new SimpleGrantedAuthority("Admin"))) {
            try {
                taskService.attachProduct(taskId, productId);
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }

            return ResponseEntity.ok().build();
        } else if (token == null || !jwtTokenProvider.validateToken(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}