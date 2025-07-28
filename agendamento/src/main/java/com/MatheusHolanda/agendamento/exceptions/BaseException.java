package com.MatheusHolanda.agendamento.exceptions;

import org.springframework.http.HttpStatus;

public class BaseException extends RuntimeException {
    private final String message;
    private final HttpStatus httpStatus;
    private final String errorCode;

    public BaseException(String message, HttpStatus httpStatus, String errorCode) {
        super(message);
        this.message = message;
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getErrorCode() {
        return errorCode;
    }
}