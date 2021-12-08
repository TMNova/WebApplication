package ru.lanit.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ParsingNumberFormatException extends RuntimeException {

    public ParsingNumberFormatException() {
        super("Request param is not a number");
    }
}
