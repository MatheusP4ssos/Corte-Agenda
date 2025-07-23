package com.MatheusHolanda.agendamento.service;

import com.MatheusHolanda.agendamento.domain.AvailableTime;
import com.MatheusHolanda.agendamento.domain.Professional;
import com.MatheusHolanda.agendamento.repository.AvailableTimeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AvailableTimeService {

    private final AvailableTimeRepository availableTimeRepository;

    public AvailableTimeService(AvailableTimeRepository availableTimeRepository) {
        this.availableTimeRepository = availableTimeRepository;
    }

    public List<LocalDateTime> listAvailableTimes(
            Professional professional,
            LocalDate date,
            int intervalMinutes
    ) {
        LocalTime opening = LocalTime.of(8, 0);
        LocalTime closing = LocalTime.of(18, 0);
        LocalTime startOfBreak = professional.getStartOfBreak();
        LocalTime endOfBreak = professional.getEndOfBreak();

        List<LocalDateTime> availableTimes = new ArrayList<>();
        LocalDateTime current = LocalDateTime.of(date, opening);

        while (!current.isAfter(LocalDateTime.of(date, closing).minusMinutes(intervalMinutes))) {
            LocalTime currentTime = current.toLocalTime();

            boolean isInBreak = startOfBreak != null && endOfBreak != null &&
                    !currentTime.isBefore(startOfBreak) && currentTime.isBefore(endOfBreak);

            boolean isOccupied = availableTimeRepository.existsByProfessionalAndDateTimeAndAvailableFalse(professional, current);

            if (!isInBreak && !isOccupied) {
                availableTimes.add(current);
            }
            current = current.plusMinutes(intervalMinutes);
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