package com.MatheusHolanda.agendamento.DTO;

import com.MatheusHolanda.agendamento.domain.Client;

/**
 * Data Transfer Object (DTO) para representar um cliente.
 * Este DTO é usado para transferir dados de cliente entre camadas da aplicação.
 */

public class ClientDTO {
    private Long id;
    private String name;
    private String email;
    private String phone;

    public ClientDTO(Client client) {
        this.id = client.getId();
        this.name = client.getName();
        this.email = client.getEmail();
        this.phone = client.getPhone();
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
}