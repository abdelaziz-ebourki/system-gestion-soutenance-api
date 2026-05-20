package com.soutenance.service;

import com.soutenance.dto.Dtos.AuthResponse;
import com.soutenance.dto.Dtos.LoginRequest;
import com.soutenance.dto.Dtos.MessageResponse;
import com.soutenance.dto.Dtos.VerifyAccountRequest;

public interface AuthService {
    AuthResponse login(LoginRequest request);
    MessageResponse verifyAccount(VerifyAccountRequest request);
}
