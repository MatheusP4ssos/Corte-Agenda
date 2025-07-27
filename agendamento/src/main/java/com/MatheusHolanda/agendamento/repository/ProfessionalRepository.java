package com.MatheusHolanda.agendamento.repository;

import com.MatheusHolanda.agendamento.domain.Professional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repository interface for Professional entity.
 * This interface extends JpaRepository to provide CRUD operations for Professional.
 */
public interface ProfessionalRepository extends JpaRepository<Professional, Long> {
    @Query("SELECT p FROM Professional p LEFT JOIN FETCH p.availableTimes WHERE UPPER(p.name) LIKE UPPER(concat('%', :name, '%'))")
    List<Professional> findByNameContainingIgnoreCase(@Param("name") String name);
}