package by.tima_zaytsev.MarketplaceParser.features.authorization.common;

import by.tima_zaytsev.MarketplaceParser.common.exceptions.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class PasswordValidator {
    public void execute(String password) throws BadRequestException {
        if (password.length() < 6)
            throw new BadRequestException("Password must contain six or more symbols");
        Pattern lowerCasePattern = Pattern.compile("[a-z]");
        Pattern digitPattern = Pattern.compile("[0-9]");
        if (!lowerCasePattern.matcher(password).find()
                && digitPattern.matcher(password).find())
            throw new BadRequestException("Password must at least 1 digit and 1 letter");
    }
}
