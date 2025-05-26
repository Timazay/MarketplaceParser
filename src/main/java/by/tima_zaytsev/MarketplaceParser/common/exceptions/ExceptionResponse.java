package by.tima_zaytsev.MarketplaceParser.common.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import java.util.Map;

@Data
@AllArgsConstructor
@Getter
public class ExceptionResponse {
    private String message;
    private Map<String, String> metadata;
}
