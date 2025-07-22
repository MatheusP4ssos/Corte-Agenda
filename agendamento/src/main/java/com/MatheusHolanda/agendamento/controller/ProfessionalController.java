package com.MatheusHolanda.agendamento.controller;

import com.MatheusHolanda.agendamento.domain.Professional;
import com.MatheusHolanda.agendamento.repository.ProfessionalRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("/professionals")
public class ProfessionalController {

    @Autowired
    private ProfessionalRepository professionalRepository;

    @GetMapping
    public List<Professional> findAll() {
        return professionalRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Professional> findById(@PathVariable Long id) {
        return professionalRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Professional> createProfessional(@RequestBody @Valid Professional professional) {
        Professional savedProfessional = professionalRepository.save(professional);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProfessional);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Professional> updateProfessional(@PathVariable Long id, @RequestBody @Valid Professional professional) {
        return professionalRepository.findById(id)
                .map(existing -> {
                    professional.setId(id); // garante que vai atualizar o mesmo id
                    Professional updated = professionalRepository.save(professional);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfessional(@PathVariable Long id) {
        if (!professionalRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        professionalRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
