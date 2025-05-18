package by.tima_zaytsev.MarketplaceParser.features.authorization.registration;

import by.tima_zaytsev.MarketplaceParser.common.exceptions.RegValidationException;
import by.tima_zaytsev.MarketplaceParser.features.authorization.common.PasswordValidator;
import by.tima_zaytsev.MarketplaceParser.infrastracture.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.regex.Pattern;

@Component
class RegistrationValidator {
    @Autowired
    private UserRepository repository;
    @Autowired
    private PasswordValidator passwordValidator;

    public void execute(RegistrationRequest request) throws RegValidationException {
        if (repository.existsByEmail(request.getEmail())) {
            throw new RegValidationException("Email already exist", HttpStatus.BAD_REQUEST.value(),
                    Map.of("email", request.getEmail()));
        }
        if (repository.existsByNickname(request.getNickname())) {
            throw new RegValidationException("Nickname already exist", HttpStatus.BAD_REQUEST.value(),
                    Map.of("nickname", request.getNickname()));
        }
        if (request.getNickname().length() < 6) {
            throw new RegValidationException("Nickname must contain min 6 symbols", HttpStatus.BAD_REQUEST.value(),
                    Map.of("nickname", request.getNickname()));
        }

        if (request.getAge() < 14 || request.getAge() > 100) {
            throw new RegValidationException("Age must be from 14 to 100",
                    HttpStatus.BAD_REQUEST.value(), Map.of("age", request.getAge().toString()));
        }
        passwordValidator.execute(request.getPassword());
    }

}
