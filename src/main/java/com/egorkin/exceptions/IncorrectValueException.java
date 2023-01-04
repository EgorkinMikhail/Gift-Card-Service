package com.egorkin.exceptions;

import org.springframework.http.HttpStatus;

public class IncorrectValueException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private final String message;

    public IncorrectValueException(String message) {
        super();
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }


}
