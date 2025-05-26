package by.tima_zaytsev.MarketplaceParser.features.authorization.reset_password;

import by.tima_zaytsev.MarketplaceParser.common.exceptions.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Tag(name = "Reset Password controller")
public class ResetPasswordController {
    @Autowired
    private ResetPasswordHandler passwordHandler;

    @PostMapping(value = "/user/{email}/reset-password")
    @Operation(summary = "Reset password")
    public ResponseEntity resetPassword(
            @PathVariable String email,
            @RequestParam String token,
            @JsonProperty String password)
            throws NotFoundException, BadRequestException {
        passwordHandler.execute(email, token, password);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
