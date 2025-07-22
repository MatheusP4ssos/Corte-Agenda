package com.MatheusHolanda.agendamento.repository;

import com.MatheusHolanda.agendamento.domain.Scheduling;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchedulingRepository extends JpaRepository <Scheduling, Long>{
}
