package com.MatheusHolanda.agendamento.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

/**
 * Classe que representa um cliente.
 * Contém informações sobre o cliente, como nome, email, telefone e serviços realizados.
 */

@Entity // Anotação para indicar que esta classe é uma entidade JPA
public class Client implements Serializable {
    private static final long serialVersionUID = 1L;


    // Anotação para indicar que este campo é a chave primária da entidade e que o ID será gerado automaticamente
    // Anotação para indicar que este campo é a chave primária da entidade e que o ID será gerado automaticamente
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do cliente é obrigatório") // Validação para garantir que o nome não seja vazio
    @Column(nullable = false) // Coluna não pode ser nula no banco de dados
    @Size(min = 3, max = 100, message = "O nome do cliente deve ter entre 3 e 100 caracteres") // Validação de tamanho do nome
    private String name;

    @NotBlank(message = "O email do cliente é obrigatório") // Validação para garantir que o email não seja vazio
    @Size(max = 100, message = "O email do cliente deve ter no máximo 100 caracteres") // Validação de tamanho do email
    @Column(nullable = false, unique = true) // Coluna não pode ser nula e deve ser única no banco de dados
    @Email(message = "O email deve ser válido") // Validação de formato de email
    private String email;

    @NotBlank(message = "O telefone do cliente é obrigatório") // Validação para garantir que o telefone não seja vazio
    @Size(min = 10, max = 15, message = "O telefone do cliente deve ter entre 10 e 15 caracteres") // Validação de tamanho do telefone
    @jakarta.validation.constraints.Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "O telefone deve ser um número válido com até 15 dígitos") // Validação de formato de telefone
    @Column(nullable = false) // Coluna não pode ser nula no banco de dados
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

    public Collection<Scheduling> getSchedulings() {

        return null; // Método placeholder, deve ser implementado para retornar os agendamentos do cliente
    }
}