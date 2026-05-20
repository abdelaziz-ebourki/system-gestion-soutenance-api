package com.soutenance.service.impl;

import com.soutenance.dto.Dtos.AuthResponse;
import com.soutenance.dto.Dtos.LoginRequest;
import com.soutenance.dto.Dtos.MessageResponse;
import com.soutenance.dto.Dtos.VerifyAccountRequest;
import com.soutenance.entity.User;
import com.soutenance.exception.ResourceNotFoundException;
import com.soutenance.mapper.UserMapper;
import com.soutenance.repository.UserRepository;
import com.soutenance.security.JwtService;
import com.soutenance.security.SecurityUser;
import com.soutenance.service.AuthService;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );
        SecurityUser user = (SecurityUser) authentication.getPrincipal();
        String token = jwtService.generateToken(user);
        return new AuthResponse(userMapper.toResponse(user.user()), token, jwtService.expiresAt());
    }

    @Override
    @Transactional
    public MessageResponse verifyAccount(VerifyAccountRequest request) {
        String userId = decodeToken(request.token());
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("Compte introuvable"));
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setIsActive(Boolean.TRUE);
        userRepository.save(user);
        return new MessageResponse("Account verified successfully");
    }

    private String decodeToken(String token) {
        try {
            return new String(Base64.getDecoder().decode(token), StandardCharsets.UTF_8);
        } catch (IllegalArgumentException ex) {
            return token;
        }
    }
}
