package by.tima_zaytsev.MarketplaceParser.common.exceptions;

import java.util.Map;

public class JwtExpirationException extends RegistrationException{
    public JwtExpirationException(String message, int status, Map<String, String> metadata) {
        super(message, status, metadata);
    }
    public JwtExpirationException(String message, int status) {
        super(message, status);
    }
}
