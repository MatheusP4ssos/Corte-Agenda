package com.MatheusHolanda.agendamento.config;

import com.MatheusHolanda.agendamento.domain.Client;
import com.MatheusHolanda.agendamento.domain.Professional;
import com.MatheusHolanda.agendamento.repository.ClientRepository;
import com.MatheusHolanda.agendamento.repository.ProfessionalRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.List;

@Configuration
public class DbSeeder {

    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

    @Bean
    CommandLineRunner initDatabase(
            ProfessionalRepository professionalRepository, ClientRepository clientRepository){



        return args -> {
            Professional prof1 = new Professional(null,
                    "Rafael Duarte",
                    "Duarte_Ducorte@gmail.com",
                    "(21)9828-1876");

            prof1.setStartOfBreak(LocalTime.parse("12:00"));
            prof1.setEndOfBreak(LocalTime.parse("13:30"));

            Professional prof2 = new Professional(null, "Diego Baptista", "<DG_ducorte@gmail.com>", "(21)9882-43118");

            prof2.setStartOfBreak(LocalTime.parse("10:00"));
            prof2.setEndOfBreak(LocalTime.parse("11:30"));

            Professional prof3 = new Professional(null, "Maicon Fernandes Lira", "Maikin@Ducorte@gmail.com", "(21)9577-12543");

            prof3.setStartOfBreak(LocalTime.parse("14:00"));
            prof3.setEndOfBreak(LocalTime.parse("15:30"));

            Client client1 = new Client(null, "Julio silva", "Julio_Camargo@hotmail.com", "(21) 97752-4532");

            Client client2 = new Client(null, "Luciano Feitosa Ferraz", "Luciano_Oferraz@gmail.com", "(21) 9176-33232");

            Client client3 = new Client(null, "Ronaldo Feliciano", "Ronaldinho.feliciano@gmail.com", "(21) 9743-22542");


            professionalRepository.saveAll(List.of(prof1, prof2, prof3));
            clientRepository.saveAll(List.of(client1, client2, client3));
        };
    }


}
