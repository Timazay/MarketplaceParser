package by.tima_zaytsev.MarketplaceParser.common.exceptions;

import lombok.Getter;

import java.util.Map;

@Getter
public class UnauthorizedException extends ApplicationException {
    public UnauthorizedException(String message, Map<String, String> metadata) {
        super(message, metadata);
    }

    public UnauthorizedException(String message) {
        super(message);
    }
}
