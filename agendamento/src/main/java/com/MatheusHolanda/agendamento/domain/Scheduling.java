package com.MatheusHolanda.agendamento.domain;

import com.MatheusHolanda.agendamento.domain.enums.SchedulingStatus;
import com.MatheusHolanda.agendamento.domain.enums.ServiseType;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
public class Scheduling implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime date;

    @ManyToOne
    private Client client;

    @ManyToOne
    private Professional professional;


    @Enumerated(EnumType.STRING)
    private SchedulingStatus status;

    @Enumerated(EnumType.STRING)
    private ServiseType serviseType;


}
