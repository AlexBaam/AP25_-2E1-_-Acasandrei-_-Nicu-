package org.example.lab11_springboot.security.login;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private String token;

    public LoginResponse() {}

    public LoginResponse(String token) {
        this.token = token;
    }
}
