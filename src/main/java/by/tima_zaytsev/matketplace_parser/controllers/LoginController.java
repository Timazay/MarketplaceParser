package by.tima_zaytsev.matketplace_parser.controllers;

import by.tima_zaytsev.matketplace_parser.common.exceptions.UserNotFoundException;
import by.tima_zaytsev.matketplace_parser.features.authorization.login.LoginHandler;
import by.tima_zaytsev.matketplace_parser.features.authorization.login.LoginRequest;
import by.tima_zaytsev.matketplace_parser.features.authorization.login.LoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Tag(name = "Authentication controller")
public class LoginController {
    @Autowired
    private LoginHandler login;

    @PostMapping("/login")
    @Operation(summary = "User authorization")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) throws UserNotFoundException {
            LoginResponse response = login.execute(request);
            return ResponseEntity.ok(response);
    }
}
