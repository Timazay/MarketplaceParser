package by.tima_zaytsev.MarketplaceParser.common.exceptions;

import lombok.Getter;

import java.util.Map;

@Getter
public class RegistrationException extends Throwable {
    private int status;
    private Map<String, String> metadata;

    public RegistrationException(String message, int status, Map<String, String> metadata) {
        super(message);
        this.status = status;
        this.metadata = metadata;
    }

    public RegistrationException(String message, int status) {
        super(message);
        this.status = status;
    }
}
