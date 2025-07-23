package com.MatheusHolanda.agendamento.DTO;

import com.MatheusHolanda.agendamento.domain.Scheduling;
import com.MatheusHolanda.agendamento.domain.enums.SchedulingStatus;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Data Transfer Object for Scheduling.
 * This class is used to transfer scheduling data between layers.
 */

public class SchedulingDTO {
    private Long id;
    private LocalDateTime date;
    private ClientDTO client;
    private List<ServiceDTO> services;
    private SchedulingStatus status;

    public SchedulingDTO(Scheduling scheduling) {
        this.id = scheduling.getId();
        this.date = scheduling.getDate();
        this.status = scheduling.getStatus();
        this.client = new ClientDTO(scheduling.getClient());

        if (scheduling.getServices() != null) {
            this.services = scheduling.getServices().stream()
                    .map(ServiceDTO::new)  // Usando o construtor de ServiceDTO
                    .toList();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public ClientDTO getClient() {
        return client;
    }

    public void setClient(ClientDTO client) {
        this.client = client;
    }

    public List<ServiceDTO> getServices() {
        return services;
    }

    public void setServices(List<ServiceDTO> services) {
        this.services = services;
    }

    public SchedulingStatus getStatus() {
        return status;
    }

    public void setStatus(SchedulingStatus status) {
        this.status = status;
    }
}
