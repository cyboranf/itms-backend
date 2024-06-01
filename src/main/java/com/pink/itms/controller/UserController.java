package com.pink.itms.controller;

import com.pink.itms.dto.user.UserRequestDTO;
import com.pink.itms.dto.user.UserResponseDTO;
import com.pink.itms.dto.user.UserResponseWithoutTasksDTO;
import com.pink.itms.dto.warehouse.WarehouseResponseDTO;
import com.pink.itms.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/edit/{userId}")
    public ResponseEntity<UserResponseDTO> editUser(@PathVariable Long userId, @RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO responseDTO = userService.editUser(userId, userRequestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    /**
     * @param id
     * @return {@link UserResponseDTO} - response from deleted user
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get all users
     * @return List of UserResponseDTO
     */
    @GetMapping("")
    public ResponseEntity<List<UserResponseWithoutTasksDTO>> getAll() {
        List<UserResponseWithoutTasksDTO> responseDTOList = userService.getAll();
        return ResponseEntity.ok(responseDTOList);
    }

    /**
     * attaches user given by userId with task given by tasksId, if said connection already existed, does nothing.
     *
     * @param userId id of user to be attached
     * @param taskId id of task to be attached
     * @return either returns status 200 if connection has been created or been created already, or 404 if either user
     * or task does not exist
     */
    @PostMapping("{userId}/join/tasks/{taskId}")
    public ResponseEntity<?> joinTaskToUser(@PathVariable Long userId, @PathVariable Long taskId) {
        try {
            userService.attachTask(userId, taskId);
        } catch(RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

        return ResponseEntity.ok("attached");
    }
}
