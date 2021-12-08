package ru.lanit.web.exceptions.person;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class PersonMinAgeForCarException extends RuntimeException {

    public PersonMinAgeForCarException() {
        super("Person is not allowed to drive a car");
    }
}
