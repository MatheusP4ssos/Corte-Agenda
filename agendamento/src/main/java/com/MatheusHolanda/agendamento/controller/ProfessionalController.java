package com.MatheusHolanda.agendamento.controller;

import com.MatheusHolanda.agendamento.DTO.ProfessionalDTO;
import com.MatheusHolanda.agendamento.domain.Professional;
import com.MatheusHolanda.agendamento.repository.ProfessionalRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador para gerenciamento de profissionais.
 * Fornece endpoints para criar, atualizar, excluir e buscar profissionais.
 */

@RestController // Anotação para indicar que esta classe é um controlador REST

@RequestMapping("/professionals") // Mapeia as requisições para o caminho "/professionals"
public class ProfessionalController {

    @Autowired // Injeção de dependência do Spring para o repositório de profissionais
    private ProfessionalRepository professionalRepository;

    @GetMapping // Mapeia requisições GET para o método findAll
    public List<ProfessionalDTO> findAll() {
        return professionalRepository.findAll().stream().map(ProfessionalDTO::new).collect(Collectors.toList());
    }

    @GetMapping("/{id}") // Mapeia requisições GET para o método findById, recebendo um ID como parâmetro
    public ResponseEntity<Professional> findById(@PathVariable Long id) {
        return professionalRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping // Mapeia requisições POST para o método createProfessional, recebendo um objeto Professional no corpo da requisição
    public ResponseEntity<Professional> createProfessional(@RequestBody @Valid Professional professional) {
        Professional savedProfessional = professionalRepository.save(professional);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProfessional);
    }

    @PutMapping("/{id}") // Mapeia requisições PUT para o método updateProfessional, recebendo um ID e um objeto Professional no corpo da requisição
    public ResponseEntity<Professional> updateProfessional(@PathVariable Long id, @RequestBody @Valid Professional professional) {
        return professionalRepository.findById(id)
                .map(existing -> {
                    professional.setId(id); // garante que vai atualizar o mesmo id
                    Professional updated = professionalRepository.save(professional);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}") // Mapeia requisições DELETE para o método deleteProfessional, recebendo um ID como parâmetro
    public ResponseEntity<Void> deleteProfessional(@PathVariable Long id) {
        if (!professionalRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        professionalRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
