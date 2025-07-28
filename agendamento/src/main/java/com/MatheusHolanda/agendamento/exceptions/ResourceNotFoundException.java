package com.MatheusHolanda.agendamento.exceptions;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends BaseException {
    public ResourceNotFoundException(String resource, String field, String value) {
        super(
            String.format("%s não encontrado com %s: '%s'", resource, field, value),
            HttpStatus.NOT_FOUND,
            "RESOURCE_NOT_FOUND"
        );
    }
}