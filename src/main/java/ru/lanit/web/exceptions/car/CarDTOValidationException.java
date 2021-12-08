package ru.lanit.web.exceptions.car;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CarDTOValidationException extends RuntimeException {

    public CarDTOValidationException() {
        super("CarDTO is not correct");
    }
}
