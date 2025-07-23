package com.MatheusHolanda.agendamento.repository;

import com.MatheusHolanda.agendamento.domain.Services;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for Services entity.
 * This interface extends JpaRepository to provide CRUD operations for Services.
 */
public interface ServiceRepository extends JpaRepository <Services, Long>{
}
