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

/**
 * Controlador para gerenciamento de horários disponíveis para profissionais.
 * Fornece um endpoint para recuperar os horários disponíveis com base na agenda do profissional.
 */

@RestController // Anotação para indicar que esta classe é um controlador REST
@RequestMapping("/available-times") // Mapeia as requisições para o caminho "/available-times"
public class AvailableTimeController {

    private final AvailableTimeService availableTimeService; // Serviço para lógica de negócios relacionada a horários disponíveis
    private final ProfessionalRepository professionalRepository; // Repositório para acessar dados dos profissionais

    @Autowired // Injeção de dependência do Spring para o construtor
    public AvailableTimeController(
            AvailableTimeService availableTimeService,
            ProfessionalRepository professionalRepository
    ) {
        this.availableTimeService = availableTimeService;
        this.professionalRepository = professionalRepository;
    }

    @GetMapping// Mapeia requisições GET para o método getAvailableTimes
    public ResponseEntity<List<LocalDateTime>> getAvailableTimes(
            @RequestParam Long professionalId, // Parâmetro obrigatório para o ID do profissional
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, // Parâmetro obrigatório para a data, formatado como ISO (AAAA-MM-DD)
            @RequestParam(defaultValue = "30") int intervalMinutes // Parâmetro opcional para definir o intervalo de tempo em minutos, padrão é 30 minutos
    ) {
        return professionalRepository.findById(professionalId) // Busca o profissional pelo ID fornecido
                .map(professional -> {
                    List<LocalDateTime> availableTimes =
                            availableTimeService.listAvailableTimes(professional, date, intervalMinutes);
                    return ResponseEntity.ok(availableTimes);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
