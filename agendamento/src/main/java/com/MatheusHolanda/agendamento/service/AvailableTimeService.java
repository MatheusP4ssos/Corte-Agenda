package com.MatheusHolanda.agendamento.service;

import com.MatheusHolanda.agendamento.domain.AvailableTime;
import com.MatheusHolanda.agendamento.domain.Professional;
import com.MatheusHolanda.agendamento.repository.AvailableTimeRepository;
import jakarta.validation.ValidationException;
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
     * @param professional O profissional cujos horários serão listados.
     * @param date         A data para a qual os horários serão listados.
     * @param intervalMinutes O intervalo em minutos entre os horários disponíveis.
     * @return Uma lista de LocalDateTime representando os horários disponíveis.
     */
    public List<LocalDateTime> listAvailableTimes(
            Professional professional,
            LocalDate date,
            int intervalMinutes
    ) {

        // Valida se o profissional, a data e o intervalo são válidos
        if (professional == null) {
            throw new ValidationException("O profissional não pode ser nulo.");
        }
        if (date == null) {
            throw new ValidationException("A data não pode ser nula.");
        }
        if (intervalMinutes <= 0) {
            throw new ValidationException("O intervalo deve ser maior que zero.");
        }

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
            boolean isOccupied = availableTimeRepository.
                    existsByProfessionalAndDateTimeAndAvailableFalse(professional, current);

            // Se o horário não está no intervalo de pausa e não está ocupado, adiciona à lista
            if (!isInBreak && !isOccupied) {
                availableTimes.add(current);
            }
            current = current.plusMinutes(intervalMinutes); // Incrementa o horário atual pelo intervalo especificado
        }
        return availableTimes;
    }

    /**
     * Adiciona um horário disponível para um profissional.
     *
     * @param professional O profissional para o qual o horário será adicionado.
     * @param dateTime     A data e hora do horário a ser adicionado.
     * @param available    Indica se o horário está disponível ou não.
     */
    public void addAvailableTime(Professional professional,LocalDateTime dateTime, boolean available) {
        if (professional == null) {
            throw new ValidationException("O profissional não pode ser nulo.");
        }
        if (dateTime == null) {
            throw new ValidationException("A data e hora não podem ser nulas.");
        }

        boolean exists = availableTimeRepository.existsByProfessionalAndDateTime(professional, dateTime);
        if (exists) {
            throw new ValidationException("Já existe um horário registrado para este profissional nesta data e hora.");
        }

        AvailableTime availableTime = new AvailableTime();
        availableTime.setProfessional(professional);
        availableTime.setDateTime(dateTime);
        availableTime.setAvailable(available);
        availableTimeRepository.save(availableTime);
    }

    /**
     * Verifica se um horário específico está disponível para um profissional.
     *
     * @param professional O profissional a ser verificado.
     * @param dateTime     A data e hora a serem verificadas.
     * @return true se o horário estiver disponível, false caso contrário.
     */
    public boolean isTimeAvailable(Professional professional, LocalDateTime dateTime) {
        if (dateTime == null || professional == null) {
            return false; // Se algum parâmetro for nulo, considera indisponível
        }
        // Verifica no repositório se existe um registro desse profissional e horário marcado como indisponível
        boolean isOccupied = availableTimeRepository.existsByProfessionalAndDateTimeAndAvailableFalse(professional, dateTime);
        return !isOccupied; // Retorna true se não estiver ocupado, ou seja, disponível
    }

    /**
     * Obtém uma lista de horários disponíveis para um profissional.
     *
     * @param selectedProfessional O profissional selecionado.
     * @return Uma lista de strings representando os horários disponíveis.
     */
    public List<String> getAvailableTimes(Professional selectedProfessional) {
        if (selectedProfessional == null) {
            throw new ValidationException("O profissional selecionado não pode ser nulo.");
        }
        List<AvailableTime> availableTimes = availableTimeRepository.findByProfessionalAndAvailableTrue(selectedProfessional);
        List<String> timeSlots = new ArrayList<>();


        for (AvailableTime availableTime : availableTimes) {
            LocalDateTime dateTime = availableTime.getDateTime();
            String timeSlot = dateTime.toLocalDate() + " " + dateTime.toLocalTime();
            timeSlots.add(timeSlot);
        }
        return timeSlots;
    }
}