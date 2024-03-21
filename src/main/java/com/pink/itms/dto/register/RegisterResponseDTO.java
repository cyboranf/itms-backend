package com.pink.itms.dto.register;

import lombok.Data;

@Data
public class RegisterResponseDTO {
    private Long id;
    private String username;
    private String name;
    private String lastname;
    private String pesel;
    private String email;
    private String phoneNumber;
}
