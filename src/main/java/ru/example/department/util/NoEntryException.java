package ru.example.department.util;

import org.springframework.http.HttpStatus;

import java.util.UUID;

public class NoEntryException extends Exception {

    private UUID id;
    private HttpStatus status = HttpStatus.CONFLICT;
    private String errorMessageRus = DConst.NO_ENTRY_MESSAGE_RUS;
    private String errorMessageEng = DConst.NO_ENTRY_MESSAGE_ENG;

    public NoEntryException() {
    }

    public NoEntryException(UUID id) {
        super();
        this.id = id;
        errorMessageRus = errorMessageRus + id.toString();
        errorMessageEng = errorMessageEng + id.toString();
    }

    public NoEntryException(String message) {
        super(message);
    }

    public NoEntryException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoEntryException(Throwable cause) {
        super(cause);
    }

    public NoEntryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }



    public UUID getId() {
        return id;
    }

    public HttpStatus getStatus() {
        return status;
    }


    public String getErrorMessageRus() {
        return errorMessageRus;
    }


    public String getErrorMessageEng() {
        return errorMessageEng;
    }

}
