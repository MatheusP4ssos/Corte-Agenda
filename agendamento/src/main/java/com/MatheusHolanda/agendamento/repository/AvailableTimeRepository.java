package com.MatheusHolanda.agendamento.repository;

import com.MatheusHolanda.agendamento.domain.AvailableTime;
import com.MatheusHolanda.agendamento.domain.Professional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface AvailableTimeRepository extends JpaRepository <AvailableTime, Long>{
    boolean existsByProfessionalAndDateTimeAndAvailableFalse(Professional professional, LocalDateTime current);
}
