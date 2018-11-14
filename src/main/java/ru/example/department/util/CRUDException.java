package ru.example.department.util;

import org.springframework.http.HttpStatus;

import java.util.UUID;

public class CRUDException extends Exception {

    public CRUDException() {
    }

    public CRUDException(String message) {
        super(message);
    }

    public CRUDException(String message, Throwable cause) {
        super(message, cause);
    }

    public CRUDException(Throwable cause) {
        super(cause);
    }

    public CRUDException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
