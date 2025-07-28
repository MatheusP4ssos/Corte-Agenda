package com.MatheusHolanda.agendamento.repository;

import com.MatheusHolanda.agendamento.domain.Professional;
import com.MatheusHolanda.agendamento.domain.Scheduling;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for Scheduling entity.
 * This interface extends JpaRepository to provide CRUD operations for Scheduling.
 */
public interface SchedulingRepository extends JpaRepository <Scheduling, Long>{
    List<Scheduling> findByProfessionalId(Long id);
}