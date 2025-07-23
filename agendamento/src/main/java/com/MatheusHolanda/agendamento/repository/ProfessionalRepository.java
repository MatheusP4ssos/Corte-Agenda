package com.MatheusHolanda.agendamento.repository;

import com.MatheusHolanda.agendamento.domain.Professional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for Professional entity.
 * This interface extends JpaRepository to provide CRUD operations for Professional.
 */
public interface ProfessionalRepository extends JpaRepository<Professional, Long> {
}
