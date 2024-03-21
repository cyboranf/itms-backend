package com.pink.itms.service;

import com.pink.itms.dto.register.RegisterRequestDTO;
import com.pink.itms.dto.register.RegisterResponseDTO;
import com.pink.itms.mapper.RegisterMapper;
import com.pink.itms.model.User;
import com.pink.itms.repository.UserRepository;
import com.pink.itms.validation.RegisterValidator;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class RegisterService {
    private final UserRepository userRepository;
    private final RegisterMapper registerMapper;
    private final RegisterValidator registerValidator;

    public RegisterService(UserRepository userRepository, RegisterMapper registerMapper, RegisterValidator registerValidator) {
        this.userRepository = userRepository;
        this.registerMapper = registerMapper;
        this.registerValidator = registerValidator;
    }

    public RegisterResponseDTO createAccount(RegisterRequestDTO registerRequestDTO) {
        registerValidator.registerValidation(registerRequestDTO);
        User user = registerMapper.toEntity(registerRequestDTO);
        User savedUser = userRepository.save(user);
        return registerMapper.toDTO(savedUser);
    }
}
