package ru.lanit.web.exceptions.car;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CarNotExistInDBException extends RuntimeException {

    public CarNotExistInDBException() {
        super("Car not found");
    }
}
