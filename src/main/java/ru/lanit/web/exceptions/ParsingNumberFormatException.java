package ru.lanit.web.exceptions;

public class ParsingNumberFormatException extends Exception {

    public ParsingNumberFormatException() {
        super("Request param is not a number");
    }
}
