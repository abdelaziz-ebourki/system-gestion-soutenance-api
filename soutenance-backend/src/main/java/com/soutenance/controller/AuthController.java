package com.soutenance.controller;

import com.soutenance.dto.Dtos.AuthResponse;
import com.soutenance.dto.Dtos.LoginRequest;
import com.soutenance.dto.Dtos.MessageResponse;
import com.soutenance.dto.Dtos.VerifyAccountRequest;
import com.soutenance.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/auth/verify-account")
    public MessageResponse verifyAccount(@Valid @RequestBody VerifyAccountRequest request) {
        return authService.verifyAccount(request);
    }
}
