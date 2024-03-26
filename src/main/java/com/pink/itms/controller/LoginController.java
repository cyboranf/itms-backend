package com.pink.itms.controller;

import com.pink.itms.dto.login.LoginRequestDTO;
import com.pink.itms.dto.login.LoginResponseDTO;
import com.pink.itms.jwt.JwtTokenProvider;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Controller class for handling user authentication.
 */
@RestController
@RequestMapping("/api")
public class LoginController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    /**
     * Constructor for LoginController.
     *
     * @param authenticationManager the AuthenticationManager instance for authentication.
     * @param jwtTokenProvider      the JwtTokenProvider instance for generating JWT tokens.
     */
    public LoginController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * Endpoint to authenticate a user.
     *
     * @param loginRequest the DTO containing login credentials.
     * @param response     the HttpServletResponse to set JWT token cookie.
     * @return ResponseEntity containing the response DTO with JWT token.
     */
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequestDTO loginRequest, HttpServletResponse response) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtTokenProvider.generateToken(authentication);

        Cookie jwtCookie = new Cookie("cookieJwt", jwt);

        jwtCookie.setHttpOnly(true);
        jwtCookie.setMaxAge(60 * 60 * 24 * 7); // 1 day
        jwtCookie.setPath("/");
        jwtCookie.setSecure(false);
        response.addCookie(jwtCookie);


        return ResponseEntity.ok(new LoginResponseDTO(jwt));
    }
}
