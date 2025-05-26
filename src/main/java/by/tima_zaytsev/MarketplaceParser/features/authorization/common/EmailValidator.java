package by.tima_zaytsev.MarketplaceParser.features.authorization.common;

import by.tima_zaytsev.MarketplaceParser.common.exceptions.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class EmailValidator {
    public void validateEmail(String email) throws BadRequestException {
        if (!org.apache.commons.validator.routines.EmailValidator.getInstance().isValid(email))
            throw new BadRequestException("Email format is not valid");
    }
}
