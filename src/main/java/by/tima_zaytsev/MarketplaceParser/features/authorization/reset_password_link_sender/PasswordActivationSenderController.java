package by.tima_zaytsev.MarketplaceParser.features.authorization.reset_password_link_sender;

import by.tima_zaytsev.MarketplaceParser.common.exceptions.BadRequestException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api")
@Tag(name = "Activation message controller to reset password")
public class PasswordActivationSenderController {
    @Autowired
    private SendResetPasswordLinkHandler sendResetPasswordLinkHandler;

    @PostMapping(value = "/user/reset-password")
    @Operation(summary = "Send reset password message to email")
    public ResponseEntity sendResetPassword(@RequestParam String email) throws BadRequestException,
            UnsupportedEncodingException {
        sendResetPasswordLinkHandler.execute(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
