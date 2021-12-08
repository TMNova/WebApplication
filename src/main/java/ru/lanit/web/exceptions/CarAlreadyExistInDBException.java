package ru.lanit.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CarAlreadyExistInDBException extends RuntimeException {

    public CarAlreadyExistInDBException() {
        super("Car is already exist");
    }
}
