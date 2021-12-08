package ru.lanit.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class PersonDTOValidationException extends RuntimeException {

    public PersonDTOValidationException() {
        super("PersonDTO is not correct");
    }
}
