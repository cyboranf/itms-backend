package com.pink.itms.dto.register;

import lombok.Data;

@Data
public class RegisterRequestDTO {
    private String username;
    private String password;
    private String confirmPassword;
    private String email;
    private String name;
    private String lastname;
    private String pesel;
    private String phone;
    private String phone_number;
    private Long roleId;
}
