package com.MatheusHolanda.agendamento.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

/**
 * Classe que representa um profissional no sistema de agendamento.
 * Um profissional pode ter vários horários disponíveis e agendamentos associados.
 */

@Entity // Anotação para indicar que esta classe é uma entidade JPA
public class Professional implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id // Anotação para indicar que este campo é a chave primária da entidade
    // @GeneratedValue(strategy = GenerationType.IDENTITY) // Geração automática do ID, usando a estratégia de identidade do banco de dados
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) // Coluna não pode ser nula no banco de dados
    @NotBlank(message = "O nome do profissional é obrigatório") // Validação para garantir que o nome não seja vazio
    @Size(min = 3, max = 100, message = "O nome do profissional deve ter entre 3 e 100 caracteres") // Validação de tamanho do nome
    @jakarta.validation.constraints.Pattern(regexp = "^[a-zA-Z\\s]+$", message = "O nome do profissional deve conter apenas letras e espaços") // Validação de formato
    private String name;

    @Column(nullable = false, unique = true) // Coluna não pode ser nula e deve ser única no banco de dados
    @NotBlank(message = "O email do profissional é obrigatório") // Validação para garantir que o email não seja vazio
    @Size(max = 100, message = "O email do profissional deve ter no máximo 100 caracteres") // Validação de tamanho do email
    @Email(message = "O email deve ser válido") // Validação de formato de email
    private String email;

    @Column(nullable = false) // Coluna não pode ser nula no banco de dados
    @NotBlank(message = "O telefone do profissional é obrigatório") // Validação para garantir que o telefone não seja vazio
    @Size(min = 10, max = 15, message = "O telefone do profissional deve ter entre 10 e 15 caracteres") // Validação de tamanho do telefone
    @jakarta.validation.constraints.Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "O telefone deve ser um número válido com até 15 dígitos") // Validação de formato de telefone
    private String phone;

    // Horário de almoço
    private LocalTime startOfBreak;
    private LocalTime endOfBreak;

    // Mapeia a relação com a entidade AvailableTime, onde "professional" é o campo na entidade AvailableTime que referencia este Professional
    @OneToMany(mappedBy = "professional")
    private List<AvailableTime> availableTimes;

    // Mapeia a relação com a entidade Scheduling, onde "professional" é o campo na entidade Scheduling que referencia este Professional
    @OneToMany(mappedBy = "professional", fetch = FetchType.EAGER)
    private List<Scheduling> schedulings;

    // Mapeia a relação muitos-para-muitos com a entidade Services, usando uma tabela intermediária chamada "professional_service"
    @ManyToMany
    @JoinTable(name = "professional_service", // Nome da tabela intermediária
            joinColumns = @JoinColumn(name = "professional_id"), // Coluna que referencia o Professional
            inverseJoinColumns = @JoinColumn(name = "service_id")) // Coluna que referencia o Service
    private List<Services> services;


    public Professional() {
    }

    public Professional(Long id, String name, String email, String phone) {
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

    public List<AvailableTime> getAvailableTimes() {
        return availableTimes;
    }

    public void setAvailableTimes(List<AvailableTime> availableTimes) {
        this.availableTimes = availableTimes;
    }

    public List<Scheduling> getSchedulings() {
        return schedulings;
    }

    public void setSchedulings(List<Scheduling> schedulings) {
        this.schedulings = schedulings;
    }

    public LocalTime getStartOfBreak() {
        return startOfBreak;
    }

    public void setStartOfBreak(LocalTime startOfBreak) {
        this.startOfBreak = startOfBreak;
    }

    public LocalTime getEndOfBreak() {
        return endOfBreak;
    }

    public void setEndOfBreak(LocalTime endOfBreak) {
        this.endOfBreak = endOfBreak;
    }

    public List<Services> getServices() {
        return services;
    }

    public void setServices(List<Services> services) {
        this.services = services;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Professional that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}