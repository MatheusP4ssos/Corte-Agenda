package com.MatheusHolanda.agendamento.repository;

import com.MatheusHolanda.agendamento.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for Client entity.
 * This interface extends JpaRepository to provide CRUD operations for Client.
 */

public interface ClientRepository extends JpaRepository<Client, Long> {
    // Método para encontrar clientes pelo email
    List<Client> findByEmail(String email);
}