package com.system_gestion_soutenance.api.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
@Tag(name = "Auth", description = "Authentication endpoints")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    @Operation(summary = "Authenticate a user", description = "Validates credentials and returns a JWT token with user info.")
    @ApiResponse(responseCode = "200", description = "Authentication successful",
        content = @Content(schema = @Schema(implementation = LoginResponse.class)))
    @ApiResponse(responseCode = "401", description = "Invalid email or password",
        content = @Content(examples = @ExampleObject("{\"message\": \"Identifiants invalides (E-mail ou mot de passe incorrect)\"}")))
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/auth/verify-account")
    @Operation(summary = "Verify a new account", description = "Decodes the base64 token to find the user, then sets the password and activates the account.")
    @ApiResponse(responseCode = "200", description = "Account verified",
        content = @Content(examples = @ExampleObject("{\"message\": \"Account verified successfully\"}")))
    @ApiResponse(responseCode = "400", description = "Invalid token")
    @ApiResponse(responseCode = "404", description = "User not found")
    public ResponseEntity<Map<String, String>> verifyAccount(@Valid @RequestBody VerifyRequest request) {
        authService.verifyAccount(request);
        return ResponseEntity.ok(Map.of("message", "Account verified successfully"));
    }
}
