package com.MatheusHolanda.agendamento.repository;

import com.MatheusHolanda.agendamento.domain.AvailableTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvailableTimeRepository extends JpaRepository <AvailableTime, Long>{
}
