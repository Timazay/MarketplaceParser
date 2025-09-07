package by.tima_zaytsev.MarketplaceParser.common.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Getter
public class ApplicationException extends Exception {
    private Map<String, String> metadata;

    public ApplicationException(String message, Map<String, String> metadata) {
        super(message);
        this.metadata = metadata;
    }

    public ApplicationException(String message) {
        super(message);
    }
}
