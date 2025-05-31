package org.example.lab11_springboot.controller;

import org.example.lab11_springboot.security.token.JwtService;
import org.example.lab11_springboot.model.User;
import org.example.lab11_springboot.repository.UserRepository;
import org.example.lab11_springboot.security.login.LoginRequest;
import org.example.lab11_springboot.security.login.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Optional<User> user = userRepository.findByUsername(loginRequest.getUsername());

        if((user.isPresent()) && (user.get().getPassword().equals(loginRequest.getPassword()))) {
            String token = jwtService.generateToken(user.get());
            return ResponseEntity.ok(new LoginResponse(token));
        }

        return ResponseEntity.status(401).body("Invalid credentials!");
    }
}
