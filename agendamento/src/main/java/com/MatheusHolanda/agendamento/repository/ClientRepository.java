package com.MatheusHolanda.agendamento.repository;

import com.MatheusHolanda.agendamento.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findByEmail(String email);
}
