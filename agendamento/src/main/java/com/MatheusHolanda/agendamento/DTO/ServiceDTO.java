package com.MatheusHolanda.agendamento.DTO;

import com.MatheusHolanda.agendamento.domain.Services;

/**
 * Data Transfer Object for Service.
 * This class is used to transfer service data between layers.
 */

public class ServiceDTO {
    private Long id;
    private String name;
    private Double price;

    public ServiceDTO(Services service) {
        this.id = service.getId();
        this.name = service.getName();
        this.price = service.getPrice();
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}