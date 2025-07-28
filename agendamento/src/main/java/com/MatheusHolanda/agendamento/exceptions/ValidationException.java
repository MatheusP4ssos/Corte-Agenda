package com.MatheusHolanda.agendamento.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Classe para representar exceções de validação.
 * Esta exceção é lançada quando há erros de validação nos dados de entrada.
 * Herda de BaseException para manter a consistência no tratamento de erros.
 */

public class ValidationException extends BaseException {
    public ValidationException(String message) {
        super(message, HttpStatus.BAD_REQUEST, "VALIDATION_ERROR");
    }
}