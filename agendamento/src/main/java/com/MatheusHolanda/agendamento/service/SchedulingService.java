package com.MatheusHolanda.agendamento.service;

import com.MatheusHolanda.agendamento.domain.AvailableTime;
import com.MatheusHolanda.agendamento.domain.Client;
import com.MatheusHolanda.agendamento.domain.Professional;
import com.MatheusHolanda.agendamento.domain.Scheduling;
import com.MatheusHolanda.agendamento.domain.enums.SchedulingStatus;
import com.MatheusHolanda.agendamento.exceptions.ResourceNotFoundException;
import com.MatheusHolanda.agendamento.exceptions.SchedulingConflictException;
import com.MatheusHolanda.agendamento.repository.AvailableTimeRepository;
import com.MatheusHolanda.agendamento.repository.ClientRepository;
import com.MatheusHolanda.agendamento.repository.ProfessionalRepository;
import com.MatheusHolanda.agendamento.repository.SchedulingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service // Anotação para indicar que esta classe é um serviço Spring
public class SchedulingService {

    /**
     * Serviço para gerenciamento de agendamento de consultas.
     * Este serviço permite agendar consultas, cancelar agendamentos e listar consultas por cliente.
     */

    // Dependências injetadas via construtor
    // Essas dependências são usadas para acessar os repositórios e serviços necessários
    // para realizar as operações de agendamento.
    private final AvailableTimeService availableTimeService;
    private final SchedulingRepository schedulingRepository;
    private final ClientRepository clientRepository;
    private final ProfessionalRepository professionalRepository;
    private final AvailableTimeRepository availableTimeRepository;

    public SchedulingService(AvailableTimeService availableTimeService, SchedulingRepository schedulingRepository,
                             ClientRepository clientRepository, ProfessionalRepository professionalRepository,
                             AvailableTimeRepository availableTimeRepository) {
        this.availableTimeService = availableTimeService;
        this.schedulingRepository = schedulingRepository;
        this.clientRepository = clientRepository;
        this.professionalRepository = professionalRepository;
        this.availableTimeRepository = availableTimeRepository;
    }

    /**
     * Agenda uma consulta para um cliente com um profissional em um horário específico.
     *
     * @param clientId       ID do cliente que está agendando a consulta.
     * @param professionalId ID do profissional com quem a consulta será agendada.
     * @param dateTime       Data e hora da consulta.
     * @throws RuntimeException         se o cliente ou profissional não for encontrado.
     * @throws IllegalArgumentException se o horário não estiver disponível.
     */
    public void scheduleAppointment(Long clientId, Long professionalId, LocalDateTime dateTime) {
        Client client = clientRepository.findById(clientId).orElseThrow(()
                -> new ResourceNotFoundException("Cliente não encontrado", "id", clientId.toString()));
        Professional professional = professionalRepository.findById(professionalId).orElseThrow(()
                -> new ResourceNotFoundException("Profissional não encontrado", "id", professionalId.toString()));

        if (!availableTimeService.isTimeAvailable(professional, dateTime)) {
            throw new SchedulingConflictException("Horário não disponível");
        }

        // Cria um novo agendamento
        Scheduling scheduling = new Scheduling();
        scheduling.setClient(client);
        scheduling.setProfessional(professional);
        scheduling.setDate(dateTime);
        scheduling.setStatus(SchedulingStatus.SCHEDULED);
        schedulingRepository.save(scheduling);

        // Marca o horário como indisponível
        AvailableTime availableTime = new AvailableTime();
        availableTime.setProfessional(professional);
        availableTime.setDateTime(dateTime);
        availableTime.setAvailable(false);
        availableTimeRepository.save(availableTime);
    }


    /**
     * Realiza o agendamento de serviços para um cliente.
     * Se o cliente já tiver realizado 9 serviços, o próximo será gratuito.
     *
     * @param client     O cliente que está realizando o agendamento.
     * @param scheduling O agendamento que contém os serviços a serem realizados.
     */
    public void performScheduling(Client client, Scheduling scheduling) {
        int schedulingCount = client.getServicesPerformed();
        boolean isFree = (schedulingCount + 1) % 10 == 0;

        if (isFree) {
            scheduling.getServices().forEach(service -> service.setPrice(0.0));
        }

        client.setServicesPerformed(schedulingCount + 1);
        clientRepository.save(client);
        schedulingRepository.save(scheduling);
    }

    /**
     * Libera um horário específico para um profissional.
     *
     * @param professional O profissional cujo horário será liberado.
     * @param dateTime    A data e hora do horário a ser liberado.
     * @throws ResourceNotFoundException se o horário não for encontrado.
     */
    public void releaseAvailableTime(Professional professional, LocalDateTime dateTime) {
        AvailableTime availableTime = availableTimeRepository
                .findByProfessionalAndDateTime(professional, dateTime)
                .orElseThrow(() -> new ResourceNotFoundException("Horário não encontrado", "professionalId",
                        professional.getId().toString()));
        availableTime.setAvailable(true);
        availableTimeRepository.save(availableTime);
    }

    /**
     * Reagenda um agendamento existente para um novo horário.
     *
     * @param schedulingId O ID do agendamento a ser reagendado.
     * @param newDateTime  O novo horário para o agendamento.
     * @throws ResourceNotFoundException       se o agendamento não for encontrado.
     * @throws SchedulingConflictException      se o novo horário não estiver disponível.
     */
    public void rescheduleAppointment(Long schedulingId, LocalDateTime newDateTime) {
        Scheduling oldScheduling = schedulingRepository.findById(schedulingId)
                .orElseThrow(() -> new ResourceNotFoundException("Agendamento não encontrado", "id", schedulingId.toString()));

        // Verifica se o novo horário está disponível antes de cancelar
        if (!availableTimeService.isTimeAvailable(oldScheduling.getProfessional(), newDateTime)) {
            throw new SchedulingConflictException("Novo horário não disponível");
        }

        // Cancela o agendamento antigo
        oldScheduling.setStatus(SchedulingStatus.CANCELED);
        schedulingRepository.save(oldScheduling);

        // Libera o horário antigo
        releaseAvailableTime(oldScheduling.getProfessional(), oldScheduling.getDate());

        // Cria um novo agendamento com a nova data
        Scheduling newScheduling = new Scheduling();
        newScheduling.setClient(oldScheduling.getClient());
        newScheduling.setProfessional(oldScheduling.getProfessional());
        newScheduling.setDate(newDateTime);
        newScheduling.setStatus(SchedulingStatus.SCHEDULED);
        schedulingRepository.save(newScheduling);

        // Marca o novo horário como indisponível
        AvailableTime newAvailableTime = new AvailableTime();
        newAvailableTime.setProfessional(oldScheduling.getProfessional());
        newAvailableTime.setDateTime(newDateTime);
        newAvailableTime.setAvailable(false);
        availableTimeRepository.save(newAvailableTime);
    }

    /**
     * Cancela um agendamento existente.
     *
     * @param schedulingId O ID do agendamento a ser cancelado.
     * @throws ResourceNotFoundException se o agendamento não for encontrado.
     */
    public void cancelScheduling(Long schedulingId) {
        Scheduling scheduling = schedulingRepository.findById(schedulingId)
                .orElseThrow(() -> new SchedulingConflictException("Agendamento não encontrado", "id", schedulingId.toString()));

        scheduling.setStatus(SchedulingStatus.CANCELED);
        schedulingRepository.save(scheduling);
        releaseAvailableTime(scheduling.getProfessional(), scheduling.getDate());

    }
}