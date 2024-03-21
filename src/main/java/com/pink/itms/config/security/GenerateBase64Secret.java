package com.pink.itms.config.security;

import java.util.Base64;

public class GenerateBase64Secret {
    public static void main(String[] args) {
        String secret = "your_jwt_secret";
        String base64Secret = Base64.getEncoder().encodeToString(secret.getBytes());
        System.out.println(base64Secret);
    }
}
