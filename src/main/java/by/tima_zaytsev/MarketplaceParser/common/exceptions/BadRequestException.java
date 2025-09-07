package by.tima_zaytsev.MarketplaceParser.common.exceptions;

import lombok.Getter;

import java.util.Map;

@Getter
public class BadRequestException extends ApplicationException {
    public BadRequestException(String message, Map<String, String> metadata) {
        super(message, metadata);
    }

    public BadRequestException(String message) {
        super(message);
    }
}
