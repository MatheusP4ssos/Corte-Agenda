package com.MatheusHolanda.agendamento.exceptions;

import org.springframework.http.HttpStatus;

public class SchedulingConflictException extends BaseException {
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