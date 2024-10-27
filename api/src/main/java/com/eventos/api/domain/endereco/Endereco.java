package com.eventos.api.domain.endereco;

import java.util.UUID;

import com.eventos.api.domain.evento.Evento;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Table(name = "adress")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
