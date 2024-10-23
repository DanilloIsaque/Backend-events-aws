package com.eventos.api.domain.endereco;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.UUID;

import com.eventos.api.domain.evento.Evento;


@Table(name = "endereco")
@Entity
public class Endereco {
    
    @Id
    @GeneratedValue
    private UUID id;

    private String city;
    private String uf;
    
    //endereco para o evento
    @ManyToOne
    @JoinColumn(name= "event_id")
    private Evento evento;


}
