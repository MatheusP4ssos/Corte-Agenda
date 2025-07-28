package com.MatheusHolanda.agendamento.exceptions;

import org.springframework.http.HttpStatus;

/**
 * BusinessException é uma classe de exceção personalizada que estende BaseException.
 * Ela é usada para indicar erros relacionados à lógica de negócios na aplicação,
 * como validações falhas ou regras de negócio não atendidas.
 * Essa classe retorna um código de status HTTP 400 (Bad Request) e um código de erro específico "BUSINESS_ERROR".
 */
public class BusinessException extends BaseException {
    public BusinessException(String message) {
        super(message, HttpStatus.BAD_REQUEST, "BUSINESS_ERROR");
    }
}