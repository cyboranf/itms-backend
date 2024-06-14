package com.pink.itms.controller;

import com.pink.itms.dto.user.UserRequestDTO;
import com.pink.itms.dto.user.UserResponseDTO;
import com.pink.itms.dto.user.UserResponseWithoutTasksDTO;
import com.pink.itms.jwt.JwtTokenProvider;
import com.pink.itms.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    public UserController(UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }
    //TODO: Napisać edit dla admina (Różni sie od tego ponizej tym, że mozna tez role zmieniac)
    @PutMapping("/edit/{userId}")
    public ResponseEntity<UserResponseDTO> editUser(@PathVariable Long userId, @RequestBody UserRequestDTO userRequestDTO, HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);

        if (token != null && jwtTokenProvider.validateToken(token)) {
            UserResponseDTO responseDTO = userService.editUser(userId, userRequestDTO);
            return ResponseEntity.ok(responseDTO);
        } else if (token == null || !jwtTokenProvider.validateToken(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable long id, HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);

        if (token != null && jwtTokenProvider.validateToken(token)) {
            try {
                userService.deleteUser(id);
                return ResponseEntity.ok().build();
            } catch (Exception e) {
                return ResponseEntity.notFound().build();
            }
        } else if (token == null || !jwtTokenProvider.validateToken(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("")
    public ResponseEntity<List<UserResponseWithoutTasksDTO>> getAll(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);

        if (token != null && jwtTokenProvider.validateToken(token)) {
            List<UserResponseWithoutTasksDTO> responseDTOList = userService.getAll();
            return ResponseEntity.ok(responseDTOList);
        } else if (token == null || !jwtTokenProvider.validateToken(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("{userId}/join/tasks/{taskId}")
    public ResponseEntity<?> joinTaskToUser(@PathVariable Long userId, @PathVariable Long taskId, HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);

        if (token != null && jwtTokenProvider.validateToken(token)) {
            try {
                userService.attachTask(userId, taskId);
            } catch(RuntimeException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }

            return ResponseEntity.ok("attached");
        } else if (token == null || !jwtTokenProvider.validateToken(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/self")
    public ResponseEntity<UserResponseWithoutTasksDTO> getSelf(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);

        if (token != null && jwtTokenProvider.validateToken(token)) {
            UserResponseWithoutTasksDTO responseDTO = userService.getSelf(jwtTokenProvider.getUsername(token));
            return ResponseEntity.ok(responseDTO);
        } else if (token == null || !jwtTokenProvider.validateToken(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

}