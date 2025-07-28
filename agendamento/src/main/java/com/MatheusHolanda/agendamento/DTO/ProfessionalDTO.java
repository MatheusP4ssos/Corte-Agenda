package com.MatheusHolanda.agendamento.DTO;

import com.MatheusHolanda.agendamento.domain.Professional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Data Transfer Object (DTO) para representar um profissional.
 * Este DTO é usado para transferir dados de profissional entre camadas da aplicação.
 */

public class ProfessionalDTO {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private List<SchedulingDTO> schedulings;

    public ProfessionalDTO() {
    }

    public ProfessionalDTO(Professional professional) {
        this.id = professional.getId();
        this.name = professional.getName();
        this.email = professional.getEmail();
        this.phone = professional.getPhone();
        this.schedulings = professional.getSchedulings().stream()
                .map(SchedulingDTO::new)
                .collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<SchedulingDTO> getSchedulings() {
        return schedulings;
    }

    public void setSchedulings(List<SchedulingDTO> schedulings) {
        this.schedulings = schedulings;
    }
}