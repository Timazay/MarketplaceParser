package by.tima_zaytsev.MarketplaceParser.common.exceptions;

import lombok.Getter;
import java.util.Map;

@Getter
public class NotFoundException extends ApplicationException {
    public NotFoundException(String message, Map<String, String> metadata) {
        super(message, metadata);
    }

    public NotFoundException(String message) {
        super(message);
    }
}
