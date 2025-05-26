package by.tima_zaytsev.MarketplaceParser.features.authorization.login;

import by.tima_zaytsev.MarketplaceParser.common.exceptions.UnauthorizedException;
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
    private LoginHandler loginHandler;

    @PostMapping("/login")
    @Operation(summary = "User authorization")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) throws UnauthorizedException {
        LoginResponse response = loginHandler.execute(request);
        return ResponseEntity.ok(response);
    }
}
