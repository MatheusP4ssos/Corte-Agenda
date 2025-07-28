package com.MatheusHolanda.agendamento.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    /**
     * ErrorResponse é uma classe que representa a resposta de erro da API.
     * Ela contém informações sobre o erro ocorrido, como o timestamp, mensagem,
     * código de erro, status HTTP, lista de erros e o caminho da requisição.
     */
    private LocalDateTime timestamp;
    private String message;
    private String errorCode;
    private Integer status;
    private List<String> errors;
    private String path;
}