package com.MatheusHolanda.agendamento.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.util.Objects;

/**
 * Classe que representa um cliente.
 * Contém informações sobre o cliente, como nome, email, telefone e serviços realizados.
 */

@Entity // Anotação para indicar que esta classe é uma entidade JPA
public class Client implements Serializable {
    private static final long serialVersionUID = 1L;

    // Anotação para indicar que este campo é a chave primária da entidade e que o ID será gerado automaticamente
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String phone;
    private int servicesPerformed = 0;


    public Client() {
    }

    public Client(Long id, String name, String email, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
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

    public int getServicesPerformed() {
        return servicesPerformed;
    }

    public void setServicesPerformed(int servicesPerformed) {
        this.servicesPerformed = servicesPerformed;
    }

    @Override // Método para comparar dois objetos Client
    public boolean equals(Object o) {
        if (!(o instanceof Client client)) return false;
        return Objects.equals(id, client.id) && Objects.equals(email, client.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }
}
