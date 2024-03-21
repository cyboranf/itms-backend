package com.pink.itms.mapper;

import com.pink.itms.dto.register.RegisterRequestDTO;
import com.pink.itms.dto.register.RegisterResponseDTO;
import com.pink.itms.exception.role.RoleNotFoundException;
import com.pink.itms.model.Role;
import com.pink.itms.model.User;
import com.pink.itms.repository.RoleRepository;
import com.pink.itms.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
public class RegisterMapper {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    public RegisterMapper(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User toEntity(RegisterRequestDTO registerRequestDTO) {
        User user = new User();
        user.setUsername(registerRequestDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
        user.setName(registerRequestDTO.getName());
        user.setLastname(registerRequestDTO.getLastname());
        user.setPesel(registerRequestDTO.getPesel());
        user.setPhoneNumber(registerRequestDTO.getPhone());
        user.setEmail(registerRequestDTO.getEmail());

        Role userRole = roleRepository.findById(registerRequestDTO.getRoleId())
                .orElseThrow(() -> new RoleNotFoundException("Error: Role is not found."));
        if (userRole != null) {
            Set<Role> roles = new HashSet<>();
            roles.add(userRole);
            user.setRoles(roles);
        } else {
            throw new RoleNotFoundException("Error: Role is not found.");
        }

        return user;
    }

    public RegisterResponseDTO toDTO(User user) {
        RegisterResponseDTO registerResponseDTO = new RegisterResponseDTO();
        registerResponseDTO.setId(user.getId());
        registerResponseDTO.setUsername(user.getUsername());
        registerResponseDTO.setName(user.getName());
        registerResponseDTO.setLastname(user.getLastname());
        registerResponseDTO.setPesel(user.getPesel());
        registerResponseDTO.setEmail(user.getEmail());
        registerResponseDTO.setPhoneNumber(user.getPhoneNumber());

        return registerResponseDTO;
    }
}
