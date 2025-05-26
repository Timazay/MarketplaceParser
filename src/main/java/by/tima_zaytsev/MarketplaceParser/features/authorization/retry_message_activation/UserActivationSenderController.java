package by.tima_zaytsev.MarketplaceParser.features.authorization.retry_message_activation;

import by.tima_zaytsev.MarketplaceParser.common.exceptions.BadRequestException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static by.tima_zaytsev.MarketplaceParser.features.authorization.activation_account.UserActivationController.activationUrl;

@RestController
@RequestMapping("/api")
@Tag(name = "User activation sender controller")
public class UserActivationSenderController {
    @Autowired
    private SendUserActivationLinkHandler sendUserActivationLinkHandler;

    @PostMapping("/user/{email}/activation-link")
    @Operation(summary = "Send activation message again")
    public ResponseEntity send(@PathVariable String email) throws BadRequestException {
        sendUserActivationLinkHandler.execute(email, activationUrl);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
