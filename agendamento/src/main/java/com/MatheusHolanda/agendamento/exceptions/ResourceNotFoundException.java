package com.MatheusHolanda.agendamento.exceptions;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends BaseException {
    /**
     * ResourceNotFoundException é uma exceção personalizada que estende BaseException.
     * Ela é usada para indicar que um recurso específico não foi encontrado na aplicação.
     * Essa exceção inclui informações sobre o recurso, o campo e o valor que causaram a falha.
     */

    public ResourceNotFoundException(String resource, String field, String value) {
        super(
            String.format("%s não encontrado com %s: '%s'", resource, field, value),
            HttpStatus.NOT_FOUND,
            "RESOURCE_NOT_FOUND"
        );
    }
}