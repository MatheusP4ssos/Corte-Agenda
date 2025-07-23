package com.MatheusHolanda.agendamento.controller;

import com.MatheusHolanda.agendamento.repository.ProfessionalRepository;
import com.MatheusHolanda.agendamento.service.AvailableTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
    @RequestMapping("/available-times")
public class AvailableTimeController {

    private final AvailableTimeService availableTimeService;
    private final ProfessionalRepository professionalRepository;

    @Autowired
    public AvailableTimeController(
            AvailableTimeService availableTimeService,
            ProfessionalRepository professionalRepository
    ) {
        this.availableTimeService = availableTimeService;
        this.professionalRepository = professionalRepository;
    }

    @GetMapping
    public ResponseEntity<List<LocalDateTime>> getAvailableTimes(
            @RequestParam Long professionalId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(defaultValue = "30") int intervalMinutes
    ) {
        return professionalRepository.findById(professionalId)
                .map(professional -> {
                    List<LocalDateTime> availableTimes =
                            availableTimeService.listAvailableTimes(professional, date, intervalMinutes);
                    return ResponseEntity.ok(availableTimes);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
