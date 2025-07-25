package com.MatheusHolanda.agendamento.service;

import com.MatheusHolanda.agendamento.SchedullingException.ResourceNotFoundException;
import com.MatheusHolanda.agendamento.SchedullingException.SchedulingConflictException;
import com.MatheusHolanda.agendamento.domain.Client;
import com.MatheusHolanda.agendamento.domain.Professional;
import com.MatheusHolanda.agendamento.domain.Scheduling;
import com.MatheusHolanda.agendamento.domain.enums.SchedulingStatus;
import com.MatheusHolanda.agendamento.repository.ClientRepository;
import com.MatheusHolanda.agendamento.repository.ProfessionalRepository;
import com.MatheusHolanda.agendamento.repository.SchedulingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class SchedulingService {

    /**
     * Serviço para gerenciamento de agendamento de consultas.
     * Este serviço permite agendar consultas, cancelar agendamentos e listar consultas por cliente.
     */

    private final AvailableTimeService availableTimeService;
    private final SchedulingRepository schedulingRepository;
    private final ClientRepository clientRepository;
    private final ProfessionalRepository professionalRepository;

    public SchedulingService(AvailableTimeService availableTimeService, SchedulingRepository schedulingRepository, ClientRepository clientRepository, ProfessionalRepository professionalRepository) {
        this.availableTimeService = availableTimeService;
        this.schedulingRepository = schedulingRepository;
        this.clientRepository = clientRepository;
        this.professionalRepository = professionalRepository;
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
                -> new ResourceNotFoundException("Cliente não encontrado"));
        Professional professional = professionalRepository.findById(professionalId).orElseThrow(()
                -> new ResourceNotFoundException("Profissional não encontrado"));
        boolean isAvailable = availableTimeService.isTimeAvailable(professional, dateTime);
        if (!isAvailable) {
            throw new SchedulingConflictException("Horário não disponível");
        }
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


    public void cancelAppointment(Long schedulingId) {
        Scheduling scheduling = schedulingRepository.findById(schedulingId)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));
        scheduling.setStatus(SchedulingStatus.CANCELED);
        schedulingRepository.save(scheduling);
    }

    public void rescheduleAppointment(Long schedulingId, LocalDateTime newDateTime) {
        Scheduling scheduling = schedulingRepository.findById(schedulingId)
                .orElseThrow(() -> new ResourceNotFoundException("Agendamento não encontrado"));

        if (newDateTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Não é possivel remarcar para uma data no passado");
        }

        Professional professional = scheduling.getProfessional();
        boolean isAvailable = availableTimeService.isTimeAvailable(professional, newDateTime);

        if (!isAvailable) {
            throw new SchedulingConflictException("Novo horário não disponível");
        }

        scheduling.setDate(newDateTime);
        scheduling.setStatus(SchedulingStatus.SCHEDULED);
        schedulingRepository.save(scheduling);
    }
}