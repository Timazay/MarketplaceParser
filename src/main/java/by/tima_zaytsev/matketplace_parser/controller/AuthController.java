package by.tima_zaytsev.matketplace_parser.controller;

import by.tima_zaytsev.matketplace_parser.service.AuthService;
import by.tima_zaytsev.matketplace_parser.dto.JwtRefreshAndAccess;
import by.tima_zaytsev.matketplace_parser.dto.JwtRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.security.auth.message.AuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Tag(name = "Authentication controller")
public class AuthController {
    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    @Operation(summary = "User authorization")
    public ResponseEntity<?> login(@RequestBody JwtRequest authRequest) {
        try {
            JwtRefreshAndAccess jwtResponse = authService.login(authRequest);
            return ResponseEntity.ok(jwtResponse);
        } catch (AuthException | EntityNotFoundException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }


}
