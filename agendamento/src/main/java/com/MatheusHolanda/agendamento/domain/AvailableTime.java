package com.MatheusHolanda.agendamento.domain;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Classe que representa o horário disponível de um profissional.
 * Contém informações sobre o profissional, data e hora do horário e se está disponível.
 */

@Entity // Anotação para indicar que esta classe é uma entidade JPA
public class AvailableTime implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id // Anotação para indicar que este campo é a chave primária da entidade
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Geração automática do ID, usando a estratégia de identidade do banco de dados
    private Long id;

    @ManyToOne // Anotação para indicar que este campo é uma relação muitos-para-um com a entidade Professional
    private Professional professional;

    private LocalDateTime dateTime;

    private boolean available;

    public AvailableTime() {
    }

    public AvailableTime(Long id, Professional professional, LocalDateTime dateTime, boolean available) {
        this.id = id;
        this.professional = professional;
        this.dateTime = dateTime;
        this.available = available;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Professional getProfessional() {
        return professional;
    }

    public void setProfessional(Professional professional) {
        this.professional = professional;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
