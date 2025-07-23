package com.MatheusHolanda.agendamento.service;

import com.MatheusHolanda.agendamento.domain.AvailableTime;
import com.MatheusHolanda.agendamento.domain.Client;
import com.MatheusHolanda.agendamento.domain.Professional;
import com.MatheusHolanda.agendamento.repository.AvailableTimeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Serviço para gerenciamento de horários disponíveis.
 * Este serviço permite listar horários disponíveis, adicionar horários e verificar disponibilidade.
 */

@Service // Indica que esta classe é um serviço Spring
public class AvailableTimeService {

    private final AvailableTimeRepository availableTimeRepository;

    public AvailableTimeService(AvailableTimeRepository availableTimeRepository) {
        this.availableTimeRepository = availableTimeRepository;
    }

    /**
     * Lista os horários disponíveis para um profissional em uma data específica.
     *
     * @param professional O profissional cujos horários serão verificados.
     * @param date        A data para a qual os horários disponíveis serão listados.
     * @param intervalMinutes O intervalo em minutos entre os horários disponíveis.
     * @return Uma lista de LocalDateTime representando os horários disponíveis.
     */
    public List<LocalDateTime> listAvailableTimes(
            Professional professional,
            LocalDate date,
            int intervalMinutes
    ) {
        LocalTime opening = LocalTime.of(8, 0); // Horário de abertura
        LocalTime closing = LocalTime.of(18, 0); // Horário de fechamento
        LocalTime startOfBreak = professional.getStartOfBreak(); // Horário de início do intervalo
        LocalTime endOfBreak = professional.getEndOfBreak(); // Horário de fim do intervalo

        List<LocalDateTime> availableTimes = new ArrayList<>();
        LocalDateTime current = LocalDateTime.of(date, opening);

        // Verifica se o horário de abertura é antes do horário de fechamento
        while (!current.isAfter(LocalDateTime.of(date, closing).minusMinutes(intervalMinutes))) {
            LocalTime currentTime = current.toLocalTime();

            // Verifica se o horário atual está dentro do intervalo de pausa
            boolean isInBreak = startOfBreak != null && endOfBreak != null &&
                    !currentTime.isBefore(startOfBreak) && currentTime.isBefore(endOfBreak);

            // Verifica se o horário atual já está ocupado
            boolean isOccupied = availableTimeRepository.existsByProfessionalAndDateTimeAndAvailableFalse(professional, current);

            // Se o horário não está no intervalo de pausa e não está ocupado, adiciona à lista
            if (!isInBreak && !isOccupied) {
                availableTimes.add(current);
            }
            current = current.plusMinutes(intervalMinutes); // Incrementa o horário atual pelo intervalo especificado
        }
        return availableTimes;
    }

    public void addAvailableTime(
            Professional professional,
            LocalDateTime dateTime,
            boolean available
    ) {
        if (availableTimeRepository.existsByProfessionalAndDateTimeAndAvailableFalse(professional, dateTime)) {
            throw new IllegalArgumentException("Este horário está ocupado.");
        }
        AvailableTime availableTime = new AvailableTime();
        availableTime.setProfessional(professional);
        availableTime.setDateTime(dateTime);
        availableTime.setAvailable(available);
        availableTimeRepository.save(availableTime);
    }

    public boolean isTimeAvailable(Professional professional, LocalDateTime dateTime) {
        if (dateTime == null || professional == null) {
            return false; // Se algum parâmetro for nulo, considera indisponível
        }
        // Verifica no repositório se existe um registro desse profissional e horário marcado como indisponível
        boolean isOccupied = availableTimeRepository.existsByProfessionalAndDateTimeAndAvailableFalse(professional, dateTime);
        return !isOccupied; // Retorna true se não estiver ocupado, ou seja, disponível
    }
}