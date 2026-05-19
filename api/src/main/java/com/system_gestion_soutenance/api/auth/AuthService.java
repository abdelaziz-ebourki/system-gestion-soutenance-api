package com.system_gestion_soutenance.api.auth;

import com.system_gestion_soutenance.api.jwt.JwtTokenProvider;
import com.system_gestion_soutenance.api.user.User;
import com.system_gestion_soutenance.api.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Base64;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(UserRepository userRepository, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                        "Identifiants invalides (E-mail ou mot de passe incorrect)"));

        if (!user.getPassword().equals(request.password())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "Identifiants invalides (E-mail ou mot de passe incorrect)");
        }

        String token = jwtTokenProvider.generateToken(user.getId(), user.getRole().name());
        long expiresAt = System.currentTimeMillis() + jwtTokenProvider.getExpirationMs();

        return new LoginResponse(UserDto.from(user), token, expiresAt);
    }

    public void verifyAccount(VerifyRequest request) {
        String userId;
        try {
            userId = new String(Base64.getDecoder().decode(request.token()));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Token invalide");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        user.setPassword(request.password());
        user.setActive(true);
        userRepository.save(user);
    }
}
