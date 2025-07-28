package com.MatheusHolanda.agendamento.exceptions;

import org.springframework.http.HttpStatus;

/**
 * BaseException é uma classe de exceção personalizada que estende RuntimeException.
 * Ela é usada para encapsular informações sobre erros que ocorrem na aplicação,
 * incluindo uma mensagem de erro, um código de status HTTP e um código de erro específico.
 * * Essa classe é útil para padronizar o tratamento de erros e fornecer informações adicionais
 * sobre o erro ocorrido.
 */
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