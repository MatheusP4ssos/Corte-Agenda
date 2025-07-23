package com.MatheusHolanda.agendamento.repository;

import com.MatheusHolanda.agendamento.domain.Scheduling;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for Scheduling entity.
 * This interface extends JpaRepository to provide CRUD operations for Scheduling.
 */
public interface SchedulingRepository extends JpaRepository <Scheduling, Long>{
}
