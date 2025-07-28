package com.MatheusHolanda.agendamento.controller;

import com.MatheusHolanda.agendamento.domain.Scheduling;
import com.MatheusHolanda.agendamento.repository.SchedulingRepository;
import com.MatheusHolanda.agendamento.service.SchedulingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/scheduling")
public class SchedulingController {

    private final SchedulingService schedulingService;
    private final SchedulingRepository schedulingRepository;

    @Autowired
    public SchedulingController(SchedulingService schedulingService, SchedulingRepository schedulingRepository) {
        this.schedulingService = schedulingService;
        this.schedulingRepository = schedulingRepository;
    }
    
    // Endpoint para agendar uma consulta
    @PostMapping("/schedule")
    public void scheduleAppointment(
            @RequestParam Long clientId,
            @RequestParam Long professionalId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime
    ) {
        schedulingService.scheduleAppointment(clientId, professionalId, dateTime);
    }

    // Endpoint para reagendar uma consulta
    @PutMapping("/reschedule/{id}")
    public void rescheduleAppointment(
            @PathVariable Long id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime newDateTime
    ) {
        schedulingService.rescheduleAppointment(id, newDateTime);
    }

    // Endpoint para cancelar uma consulta
    @PutMapping("/cancel/{id}")
    public void cancelScheduling(@PathVariable Long id) {
        schedulingService.cancelScheduling(id);
    }

    @GetMapping("/professional/{id}")
    public List<Scheduling> findByProfessionalId(@PathVariable Long id) {
        return schedulingRepository.findByProfessionalId(id);
    }

    @GetMapping
    public List<Scheduling> findAll() {
        return schedulingRepository.findAll();
    }
}
