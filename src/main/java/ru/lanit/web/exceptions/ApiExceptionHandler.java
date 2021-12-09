package ru.lanit.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ObjectAlreadyExistException.class, ValidationException.class,
            ParsingNumberFormatException.class, ObjectNotExistException.class})
    public ResponseEntity<?> handleBadRequestException() {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ObjectNotFoundException.class})
    public ResponseEntity<?> handleNotFoundException() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
