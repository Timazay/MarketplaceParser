package by.tima_zaytsev.MarketplaceParser.controllers;

import by.tima_zaytsev.MarketplaceParser.common.exceptions.JwtExpirationException;
import by.tima_zaytsev.MarketplaceParser.common.exceptions.RegValidationException;
import by.tima_zaytsev.MarketplaceParser.common.exceptions.SendMsgException;
import by.tima_zaytsev.MarketplaceParser.common.exceptions.UserNotFoundException;
import by.tima_zaytsev.MarketplaceParser.features.authorization.registration.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Tag(name = "Registration controller")
public class RegistrationController {
    private RegistrationActivation registrationActivation;
    private RegistrationHandler registrationHandler;
    private RegistrationSendMsgAgain sendMsgAgain;
    @Autowired
    public RegistrationController(RegistrationActivation registrationActivation,
                                  RegistrationHandler registrationHandler, RegistrationSendMsgAgain sendMsgAgain) {
        this.registrationActivation = registrationActivation;
        this.registrationHandler = registrationHandler;
        this.sendMsgAgain = sendMsgAgain;
    }
    @PostMapping(value = "/registration", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "Register user")
    public ResponseEntity<String> registerUser(@ModelAttribute RegistrationRequest request)
            throws RegValidationException, Exception {
        registrationHandler.execute(request);
        return new ResponseEntity<>("User created, check mail!", HttpStatus.CREATED);
    }
    @GetMapping("/confirmation")
    @Operation(summary = "Activate user")
    public ResponseEntity<Boolean> activate(@RequestParam(required = false) String token) throws UserNotFoundException,
            SendMsgException, JwtExpirationException {
        registrationActivation.execute(token);
        return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
    }
    @GetMapping("/confirmation/{email}/resend-confirmation")
    @Operation(summary = "Send activation message again")
    public ResponseEntity<Boolean> send(@PathVariable String email) throws UserNotFoundException,
            SendMsgException {
        sendMsgAgain.execute(email);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
