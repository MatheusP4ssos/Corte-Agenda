package com.MatheusHolanda.agendamento.exceptions;

import org.springframework.http.HttpStatus;

public class SchedulingConflictException extends BaseException {
    /**
     * SchedulingConflictException é uma exceção personalizada que estende BaseException.
     * Ela é usada para indicar que ocorreu um conflito de agendamento na aplicação.
     * Essa exceção inclui uma mensagem de erro e um código de status HTTP 409 (CONFLICT).
     */

    public SchedulingConflictException(String message) {
        super(message, HttpStatus.CONFLICT, "SCHEDULING_CONFLICT");
    }

    public SchedulingConflictException(String resource, String field, String value) {
        super(
            String.format("%s já agendado com %s: '%s'", resource, field, value),
            HttpStatus.CONFLICT,
            "SCHEDULING_CONFLICT"
        );
    }
}