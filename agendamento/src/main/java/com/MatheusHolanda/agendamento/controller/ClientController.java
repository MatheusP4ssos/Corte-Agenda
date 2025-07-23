package com.MatheusHolanda.agendamento.controller;

import com.MatheusHolanda.agendamento.domain.Client;
import com.MatheusHolanda.agendamento.repository.ClientRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para gerenciamento de clientes.
 * Fornece endpoints para criar, atualizar, excluir e buscar clientes.
 */

@RestController // Anotação para indicar que esta classe é um controlador REST

@RequestMapping("/clients") // Mapeia as requisições para o caminho "/clients"
public class ClientController {

    @Autowired // Injeção de dependência do Spring para o repositório de clientes
    private ClientRepository clientRepository;

    // Mapeia requisições GET para o método findAll
    @GetMapping
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    // Mapeia requisições GET para o método findById, recebendo um ID como parâmetro
    @GetMapping("/{id}")
    public ResponseEntity<Client> findById(@PathVariable Long id) {
        return clientRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Mapeia requisições POST para o método createClient, recebendo um objeto Client no corpo da requisição
    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody @Valid Client client) {
        Client savedClient = clientRepository.save(client);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedClient);
    }

    // Mapeia requisições PUT para o método updateClient, recebendo um ID e um objeto Client no corpo da requisição
    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody @Valid Client client) {
        return clientRepository.findById(id)
                .map(existing -> {
                    client.setId(id); // garante que vai atualizar o mesmo id
                    Client updated = clientRepository.save(client);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Mapeia requisições DELETE para o método deleteClient, recebendo um ID como parâmetro
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        if (!clientRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        clientRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Mapeia requisições GET para o método findByEmail, recebendo um parâmetro de consulta "email"
    @GetMapping("/search")
    public ResponseEntity<List<Client>> findByEmail(@RequestParam String email) {
        List<Client> clients = clientRepository.findByEmail(email);
        if (clients.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(clients);
        }
    }
}