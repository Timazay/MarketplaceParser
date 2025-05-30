package by.tima_zaytsev.matketplace_parser.controller;

import by.tima_zaytsev.matketplace_parser.entity.User;
import by.tima_zaytsev.matketplace_parser.service.MailService;
import by.tima_zaytsev.matketplace_parser.dto.UserRegistrationRequest;
import by.tima_zaytsev.matketplace_parser.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Registration controller")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private MailService mailService;

    @PostMapping(value = "/register", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "Register user")
    public ResponseEntity<String> registerUser(@ModelAttribute UserRegistrationRequest request) {
        try {
            User user = userService.registerUser(request);
            mailService.sendActivationEmail(user.getEmail());
            return new ResponseEntity<>("User created, check mail!", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/activate")
    @Operation(summary = "Activate user")
    public ResponseEntity<Boolean> activate(@RequestParam(required = false) String email) {
        if (email != null) {
            mailService.activateUser(email);
            return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }
}
