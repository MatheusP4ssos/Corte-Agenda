package com.MatheusHolanda.agendamento.SchedullingException;

public class SchedulingConflictException extends RuntimeException {
    public SchedulingConflictException(String message) {
        super(message);
    }
}
