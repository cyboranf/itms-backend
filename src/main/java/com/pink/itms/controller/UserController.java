package com.pink.itms.controller;

import com.pink.itms.dto.user.UserRequestDTO;
import com.pink.itms.dto.user.UserResponseDTO;
import com.pink.itms.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
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
     * Get all users
     * @return List of UserResponseDTO
     */
    @GetMapping("/all")
    public ResponseEntity<List<UserResponseDTO>> getAll() {
        List<UserResponseDTO> responseDTOList = userService.getAll();
        return ResponseEntity.ok(responseDTOList);
    }
}