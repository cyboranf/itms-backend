package com.pink.itms.dto.login;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
public class LoginResponseDTO {
    private String accessToken;
    private String tokenType = "Bearer";
    private String userName;
    private Collection<?> rank;
    public LoginResponseDTO(String accessToken, String userName, Collection<?> rank) {
        this.accessToken = accessToken;
        this.userName = userName;
        this.rank = rank;
    }
}
