package com.MatheusHolanda.agendamento.repository;

import com.MatheusHolanda.agendamento.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
