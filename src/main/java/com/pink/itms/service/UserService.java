package com.pink.itms.service;

import com.pink.itms.dto.user.UserRequestDTO;
import com.pink.itms.dto.user.UserResponseDTO;
import com.pink.itms.dto.user.UserResponseWithoutTasksDTO;
import com.pink.itms.exception.user.UserNotFoundException;
import com.pink.itms.mapper.UserMapper;
import com.pink.itms.model.Task;
import com.pink.itms.model.User;
import com.pink.itms.repository.UserRepository;
import com.pink.itms.validation.UserValidator;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserValidator userValidator;

    public UserService(UserRepository userRepository, UserMapper userMapper, UserValidator userValidator) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userValidator = userValidator;
    }

    public UserResponseDTO editUser(Long userId, UserRequestDTO dto) {
        User user = userValidator.userEditValidation(userId, dto);
        userMapper.updateUserFromRequestDTO(dto, user);
        User savedUser = userRepository.save(user);
        return userMapper.entityToDto(savedUser);
    }

    /**
     * Get all users
     * @return List of UserResponseDTO
     */
    public List<UserResponseWithoutTasksDTO> getAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::entityToDtoWithoutTasks)
                .toList();
    }
}
