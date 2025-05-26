package by.tima_zaytsev.MarketplaceParser.features.authorization.registration;

import by.tima_zaytsev.MarketplaceParser.common.exceptions.BadRequestException;
import by.tima_zaytsev.MarketplaceParser.features.authorization.common.EmailValidator;
import by.tima_zaytsev.MarketplaceParser.features.authorization.common.PasswordValidator;
import by.tima_zaytsev.MarketplaceParser.infrastracture.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
class RegistrationValidator {
    @Autowired
    private UserRepository repository;
    @Autowired
    private PasswordValidator passwordValidator;
    @Autowired
    private EmailValidator emailValidator;

    public void execute(RegistrationRequest request) throws BadRequestException {
        if (request == null)
            throw new BadRequestException("Something is empty");
        if (repository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already exist",
                    Map.of("email", request.getEmail()));
        }
        emailValidator.validateEmail(request.getEmail());
        if (repository.existsByNickname(request.getNickname())) {
            throw new BadRequestException("Nickname already exist",
                    Map.of("nickname", request.getNickname()));
        }
        if (request.getNickname().length() < 6) {
            throw new BadRequestException("Nickname must contain min 6 symbols",
                    Map.of("nickname", request.getNickname()));
        }

        if (request.getAge() < 14 || request.getAge() > 100) {
            throw new BadRequestException("Age must be from 14 to 100",
                    Map.of("age", request.getAge().toString()));
        }
        passwordValidator.execute(request.getPassword());
    }
}
