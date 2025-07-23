package com.MatheusHolanda.agendamento.service;

import com.MatheusHolanda.agendamento.domain.Client;
import com.MatheusHolanda.agendamento.domain.Professional;
import com.MatheusHolanda.agendamento.domain.Scheduling;
import com.MatheusHolanda.agendamento.repository.ClientRepository;
import com.MatheusHolanda.agendamento.repository.ProfessionalRepository;
import com.MatheusHolanda.agendamento.repository.SchedulingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SchedulingService {

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


    public void scheduleAppointment(Long clientId, Long professionalId, LocalDateTime dateTime) {
        Client client = clientRepository.findById(clientId).orElseThrow(()
                -> new RuntimeException("Cliente não encontrado"));
        Professional professional = professionalRepository.findById(professionalId).orElseThrow(()
                -> new RuntimeException("Profissional não encontrado"));
        boolean isAvailable = availableTimeService.isTimeAvailable(professional, dateTime);
        if (!isAvailable) {
            throw new IllegalArgumentException("Horário não disponível");
        }
    }
}

    /**
    public void cancelAppointment(Long schedulingId) {
        // Implement cancellation logic here
        // Find the scheduling by ID and update its status to cancelled
    }

    public List<Scheduling> getAppointmentsByClient(Long clientId) {
        // Implement logic to retrieve appointments for a specific client
        return schedulingRepository.findByClientId(clientId);
    }
}
/**/