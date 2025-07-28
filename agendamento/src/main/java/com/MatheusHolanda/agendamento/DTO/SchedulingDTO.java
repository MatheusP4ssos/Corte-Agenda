package com.MatheusHolanda.agendamento.DTO;

import com.MatheusHolanda.agendamento.domain.Scheduling;
import com.MatheusHolanda.agendamento.domain.enums.SchedulingStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;
import java.util.List;

@JsonIgnoreProperties({"professional"})
public class SchedulingDTO {
    private Long id;
    private LocalDateTime dateTime;
    private ClientDTO client;
    private ProfessionalDTO professional;
    private SchedulingStatus status;
    private List<ServiceDTO> services;

    public SchedulingDTO(Scheduling scheduling) {
        this.id = scheduling.getId();
        this.dateTime = scheduling.getDate();
        this.status = scheduling.getStatus();

        if (scheduling.getClient() != null) {
            this.client = new ClientDTO(scheduling.getClient());
        }

        // NÃO atribuir ProfessionalDTO aqui para evitar ciclo
        this.professional = null;

        if (scheduling.getServices() != null) {
            this.services = scheduling.getServices().stream()
                    .map(ServiceDTO::new)
                    .toList();
        }
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public ClientDTO getClient() {
        return client;
    }

    public void setClient(ClientDTO client) {
        this.client = client;
    }

    public ProfessionalDTO getProfessional() {
        return professional;
    }

    public void setProfessional(ProfessionalDTO professional) {
        this.professional = professional;
    }

    public SchedulingStatus getStatus() {
        return status;
    }

    public void setStatus(SchedulingStatus status) {
        this.status = status;
    }

    public List<ServiceDTO> getServices() {
        return services;
    }

    public void setServices(List<ServiceDTO> services) {
        this.services = services;
    }
}