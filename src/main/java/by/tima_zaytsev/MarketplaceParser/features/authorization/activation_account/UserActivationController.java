package by.tima_zaytsev.MarketplaceParser.features.authorization.activation_account;

import by.tima_zaytsev.MarketplaceParser.common.exceptions.BadRequestException;
import by.tima_zaytsev.MarketplaceParser.common.exceptions.UnauthorizedException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Tag(name = "Account activation controller")
public class UserActivationController {
    @Autowired
    private UserActivationHandler userActivationHandler;
    public static final String activationUrl = "/user/activation";

    @PostMapping(activationUrl)
    @Operation(summary = "Activate user")
    public ResponseEntity activate(@RequestParam(required = false) String token) throws UnauthorizedException,
            BadRequestException {
        userActivationHandler.execute(token);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
