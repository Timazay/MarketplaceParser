package by.tima_zaytsev.matketplace_parser.controllers;

import by.tima_zaytsev.matketplace_parser.common.exceptions.RegValidationException;
import by.tima_zaytsev.matketplace_parser.common.exceptions.UserNotFoundException;
import by.tima_zaytsev.matketplace_parser.features.authorization.registration.RegistrationActivation;
import by.tima_zaytsev.matketplace_parser.features.authorization.registration.RegistrationHandler;
import by.tima_zaytsev.matketplace_parser.features.authorization.registration.RegistrationRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Tag(name = "Authentication controller")
public class RegistrationController {
    private RegistrationActivation registrationActivation;
    private RegistrationHandler registrationHandler;

    @Autowired
    public RegistrationController(RegistrationActivation registrationActivation, RegistrationHandler registrationHandler) {
        this.registrationActivation = registrationActivation;
        this.registrationHandler = registrationHandler;
    }

    @PostMapping(value = "/register", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "Register user")
    public ResponseEntity<String> registerUser(@ModelAttribute RegistrationRequest request) throws RegValidationException,
            Exception {
        registrationHandler.execute(request);
        return new ResponseEntity<>("User created, check mail!", HttpStatus.CREATED);
    }

    @GetMapping("/activate")
    @Operation(summary = "Activate user")
    public ResponseEntity<Boolean> activate(@RequestParam(required = false) String email) throws UserNotFoundException {
        registrationActivation.execute(email);
        return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
    }

}
