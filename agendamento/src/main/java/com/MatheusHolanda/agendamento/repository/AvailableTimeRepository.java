package com.MatheusHolanda.agendamento.repository;

import com.MatheusHolanda.agendamento.domain.AvailableTime;
import com.MatheusHolanda.agendamento.domain.Professional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

/**
 * Repository interface for managing AvailableTime entities.
 * This interface extends JpaRepository to provide CRUD operations.
 */

public interface AvailableTimeRepository extends JpaRepository <AvailableTime, Long>{
    // Metodo para verificar se existe um horário disponível para um profissional em uma data e hora específica
    boolean existsByProfessionalAndDateTimeAndAvailableFalse(Professional professional, LocalDateTime current);
}
