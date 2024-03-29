package com.pink.itms.mapper;

import com.pink.itms.dto.user.UserRequestDTO;
import com.pink.itms.dto.user.UserResponseDTO;
import com.pink.itms.exception.user.UserNotFoundException;
import com.pink.itms.model.User;
import com.pink.itms.repository.UserRepository;
import com.pink.itms.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    private final UserRepository userRepository;

    public UserMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User dtoToEntity(UserRequestDTO userRequestDTO) {
        User user = new User();
        BeanUtils.copyProperties(userRequestDTO, user);
        return user;
    }

    public UserResponseDTO entityToDto(User user) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        BeanUtils.copyProperties(user, userResponseDTO);
        userResponseDTO.setTasks(userRepository.findById(user.getId()).orElseThrow(() -> new UserNotFoundException("User not found")).getTasks());

        return userResponseDTO;
    }

    public void updateUserFromRequestDTO(UserRequestDTO dto, User user) {
        BeanUtils.copyProperties(dto, user, "id");
    }
}
