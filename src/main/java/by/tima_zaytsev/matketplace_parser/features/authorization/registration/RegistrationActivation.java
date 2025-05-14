package by.tima_zaytsev.matketplace_parser.features.authorization.registration;

import by.tima_zaytsev.matketplace_parser.common.exceptions.UserNotFoundException;
import by.tima_zaytsev.matketplace_parser.infrastracture.UserRepository;
import by.tima_zaytsev.matketplace_parser.infrastracture.entity.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RegistrationActivation {
    @Autowired
    private UserRepository userRepository;

    public void execute(String email) throws UserNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found",
                HttpStatus.BAD_REQUEST.value(),
                Map.of("email", email)));
        user.setConfirmed(true);
        userRepository.save(user);
    }
}
