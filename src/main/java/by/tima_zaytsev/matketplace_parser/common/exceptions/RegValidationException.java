package by.tima_zaytsev.matketplace_parser.common.exceptions;

import lombok.Getter;

import java.util.Map;

@Getter
public class RegValidationException extends Throwable{
    private String msg;
    private int status;
    private Map<String, String> metadata;


    public RegValidationException(String msg, int status, Map<String, String>metadata) {
        this.msg = msg;
        this.status = status;
        this.metadata = metadata;
    }
    public RegValidationException(String msg, int status) {
        this.msg = msg;
        this.status = status;
    }
}
