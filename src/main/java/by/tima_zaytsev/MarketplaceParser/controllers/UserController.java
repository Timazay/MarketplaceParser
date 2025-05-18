package by.tima_zaytsev.MarketplaceParser.controllers;

import by.tima_zaytsev.MarketplaceParser.common.exceptions.JwtExpirationException;
import by.tima_zaytsev.MarketplaceParser.common.exceptions.RegValidationException;
import by.tima_zaytsev.MarketplaceParser.common.exceptions.SendMsgException;
import by.tima_zaytsev.MarketplaceParser.common.exceptions.UserNotFoundException;
import by.tima_zaytsev.MarketplaceParser.features.authorization.reset_password.PasswordHandler;
import by.tima_zaytsev.MarketplaceParser.features.authorization.reset_password.PasswordResetSender;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Tag(name = "User controller")
public class UserController {
    @Autowired
    private PasswordHandler passwordHandler;
    @Autowired
    private PasswordResetSender passwordResetSender;
    @PostMapping(value = "/user/reset-password")
    @Operation(summary = "Send reset password message to email")
    public ResponseEntity<Boolean> sendResetPassword(@RequestParam String email) {
        passwordResetSender.execute(email);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
    @GetMapping("/user/reset-password/{email}")
    @Operation(summary = "Reset password")
    public ResponseEntity<Boolean> resetPassword(@PathVariable String email, @RequestParam String password)
            throws UserNotFoundException, RegValidationException,
            JwtExpirationException, SendMsgException {
        passwordHandler.execute(email,password);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
