package ru.lanit.web.exceptions.person;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class PersonNotExistInDBException extends RuntimeException {

    public PersonNotExistInDBException() {
        super("Person not found");
    }
}
