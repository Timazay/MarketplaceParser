package by.tima_zaytsev.MarketplaceParser.common.exceptions;

import lombok.Getter;

import java.util.Map;

@Getter
public class RegValidationException extends RegistrationException{
    public RegValidationException(String message, int status, Map<String, String> metadata) {
        super(message, status, metadata);
    }

    public RegValidationException(String message, int status) {
        super(message, status);
    }
}
