package com.MatheusHolanda.agendamento.domain;

import com.MatheusHolanda.agendamento.domain.enums.SchedulingStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Scheduling implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime date;

    @ManyToOne
    private Client client;

    @ManyToOne
    @JoinColumn(name = "professional_id", nullable = false)
    @JsonIgnore
    private Professional professional;


    @Enumerated(EnumType.STRING)
    private SchedulingStatus status;

    @ManyToMany
    @JoinTable(name = "scheduling_services",
            joinColumns = @JoinColumn(name = "scheduling_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id"))
    private List<Services> services;

    public Scheduling() {
    }

    public Scheduling(Long id, LocalDateTime date, Client client, Professional professional, SchedulingStatus status, List<Services> services) {
        this.id = id;
        this.date = date;
        this.client = client;
        this.professional = professional;
        this.status = status;
        this.services = services;
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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Professional getProfessional() {
        return professional;
    }

    public void setProfessional(Professional professional) {
        this.professional = professional;
    }

    public SchedulingStatus getStatus() {
        return status;
    }

    public void setStatus(SchedulingStatus status) {
        this.status = status;
    }
}
