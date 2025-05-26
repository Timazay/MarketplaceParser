package by.tima_zaytsev.MarketplaceParser.features.authorization.registration;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static by.tima_zaytsev.MarketplaceParser.features.authorization.activation_account.UserActivationController.activationUrl;

@RestController
@RequestMapping("/api")
@Tag(name = "Registration controller")
public class RegistrationController {
    private final RegistrationHandler registrationHandler;

    @Autowired
    public RegistrationController(RegistrationHandler registrationHandler) {
        this.registrationHandler = registrationHandler;
    }

    @PostMapping(value = "/user/registration", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "Register user")
    public ResponseEntity<String> registerUser(@ModelAttribute RegistrationRequest request)
            throws Exception {
        registrationHandler.execute(request, activationUrl);
        return new ResponseEntity<>("User created, check mail!", HttpStatus.CREATED);
    }

}
