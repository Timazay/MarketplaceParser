package by.tima_zaytsev.MarketplaceParser.common.exceptions;

import lombok.Getter;

import java.util.Map;

@Getter
public class RegistrationSendMsgException extends RegistrationException{

    public RegistrationSendMsgException(String message, int status, Map<String, String> metadata) {
        super(message, status, metadata);
    }
    public RegistrationSendMsgException(String message, int status) {
        super(message, status);
    }
}
