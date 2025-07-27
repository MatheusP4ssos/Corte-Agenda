package com.MatheusHolanda.agendamento.config;

import com.MatheusHolanda.agendamento.domain.Client;
import com.MatheusHolanda.agendamento.domain.Professional;
import com.MatheusHolanda.agendamento.domain.Scheduling;
import com.MatheusHolanda.agendamento.domain.Services;
import com.MatheusHolanda.agendamento.domain.enums.SchedulingStatus;
import com.MatheusHolanda.agendamento.repository.ClientRepository;
import com.MatheusHolanda.agendamento.repository.ProfessionalRepository;
import com.MatheusHolanda.agendamento.repository.SchedulingRepository;
import com.MatheusHolanda.agendamento.repository.ServiceRepository;
import com.MatheusHolanda.agendamento.service.SchedulingService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * Classe de configuração para popular o banco de dados com dados iniciais.
 * Esta classe é executada ao iniciar a aplicação, preenchendo o banco de dados
 * com profissionais, clientes, serviços e agendamentos pré-definidos.
 */

@Configuration // Anotação para indicar que esta classe é uma configuração do Spring
public class DbSeeder {

    @Bean
        // Método que retorna um CommandLineRunner, que é executado ao iniciar a aplicação
        // CommandLineRunner é uma interface do Spring que permite executar código ao iniciar a aplicação
    CommandLineRunner initDatabase(
            ProfessionalRepository professionalRepository,
            ClientRepository clientRepository,
            ServiceRepository serviceRepository,
            SchedulingService schedulingService,
            SchedulingRepository schedulingRepository) {

        // O CommandLineRunner recebe os repositórios necessários para salvar os dados iniciais
        // e retorna uma implementação que será executada ao iniciar a aplicação.

        // Profissionais são criados com horários de intervalo definidos.
        return args -> {
            Professional prof1 = new Professional(null, "Rafael Duarte", "Duarte_Ducorte@gmail.com", "(21)9828-1876");
            prof1.setStartOfBreak(LocalTime.parse("12:00"));
            prof1.setEndOfBreak(LocalTime.parse("13:30"));

            Professional prof2 = new Professional(null, "Diego Baptista", "<DG_ducorte@gmail.com>", "(21)9882-43118");
            prof2.setStartOfBreak(LocalTime.parse("10:00"));
            prof2.setEndOfBreak(LocalTime.parse("11:30"));

            Professional prof3 = new Professional(null, "Maicon Fernandes Lira", "Maikin@Ducorte@gmail.com", "(21)9577-12543");
            prof3.setStartOfBreak(LocalTime.parse("14:00"));
            prof3.setEndOfBreak(LocalTime.parse("15:30"));

            // Clientes são criados com informações básicas
            Client client1 = new Client(null, "Julio silva", "Julio_Camargo@hotmail.com", "(21) 97752-4532");
            Client client2 = new Client(null, "Luciano Feitosa Ferraz", "Luciano_Oferraz@gmail.com", "(21) 9176-33232");
            Client client3 = new Client(null, "Ronaldo Feliciano", "Ronaldinho.feliciano@gmail.com", "(21) 9743-22542");
            Client client4 = new Client(null, "Matheus Holanda", "matheus_Oholanda@gmail.com", "(21) 9876-5432");

            // Serviços são criados com nome e preço
            Services Corte_Tesoura = new Services();
            Corte_Tesoura.setName("Corte de Tesoura");
            Corte_Tesoura.setPrice(20.0);

            Services Corte_Maquina = new Services();
            Corte_Maquina.setName("Corte de Máquina");
            Corte_Maquina.setPrice(15.0);

            Services Corte_Sobrancelha = new Services();
            Corte_Sobrancelha.setName("Corte de Sobrancelha");
            Corte_Sobrancelha.setPrice(10.0);

            Services Pintura_Cabelo = new Services();
            Pintura_Cabelo.setName("Pintura de Cabelo");
            Pintura_Cabelo.setPrice(50.0);

            // Salvar profissionais, clientes e serviços
            List<Professional> profissionaisSalvos = professionalRepository.saveAll(List.of(prof1, prof2, prof3));
            List<Client> clientesSalvos = clientRepository.saveAll(List.of(client1, client2, client3, client4));
            List<Services> servicosSalvos = serviceRepository.saveAll(List.of(Corte_Tesoura, Corte_Maquina, Corte_Sobrancelha, Pintura_Cabelo));

            // Agendamentos são criados com data, cliente, profissional, status e serviços associados
            Scheduling sched1 = new Scheduling(
                    null,
                    LocalDateTime.parse("2023-10-01T10:00:00"),
                    clientesSalvos.get(0), // client1
                    profissionaisSalvos.get(0), // prof1
                    SchedulingStatus.SCHEDULED,
                    List.of(servicosSalvos.get(0), servicosSalvos.get(2))
            );

            Scheduling sched2 = new Scheduling(
                    null,
                    LocalDateTime.parse("2023-10-02T11:00:00"),
                    clientesSalvos.get(1), // client2
                    profissionaisSalvos.get(1), // prof2
                    SchedulingStatus.SCHEDULED,
                    List.of(servicosSalvos.get(1))
            );

            Scheduling sched3 = new Scheduling(
                    null,
                    LocalDateTime.parse("2025-07-28T14:00:00"),
                    clientesSalvos.get(3), // client3
                    profissionaisSalvos.get(2), // prof3
                    SchedulingStatus.SCHEDULED,
                    List.of(servicosSalvos.get(0), servicosSalvos.get(1), servicosSalvos.get(3))
            );


            sched1 = schedulingRepository.save(sched1);
            sched2 = schedulingRepository.save(sched2);
            sched3 = schedulingRepository.save(sched3); // Agora todos têm ID!

// Teste de reagendamento
            try {
                schedulingService.rescheduleAppointment(sched1.getId(), LocalDateTime.parse("2025-08-02T11:00:00"));
                System.out.println("Agendamento reagendado com sucesso: " + sched1.getId());
            } catch (Exception e) {
                System.out.println("Erro ao reagendar agendamento: " + e.getMessage());
            }

// Teste de cancelamento
            try {
                schedulingService.cancelScheduling(sched2.getId());
                System.out.println("Agendamento cancelado com sucesso: " + sched2.getId());
            } catch (Exception e) {
                System.out.println("Erro ao cancelar agendamento: " + e.getMessage());
            }

// Teste final com sched3
            try {
                schedulingService.rescheduleAppointment(sched3.getId(), LocalDateTime.now().plusDays(2).withHour(14).withMinute(0));
                System.out.println("Agendamento reagendado com sucesso: " + sched3.getId());
                schedulingService.cancelScheduling(sched3.getId());
                System.out.println("Agendamento cancelado com sucesso: " + sched3.getId());
            } catch (Exception e) {
                System.out.println("Erro em operação com sched3: " + e.getMessage());
            }
        };
    }
}