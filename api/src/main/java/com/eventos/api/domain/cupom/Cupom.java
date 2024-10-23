package com.eventos.api.domain.cupom;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Id;
import java.util.UUID;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import com.eventos.api.domain.evento.Evento;

import java.util.Date;

@Entity
@Table(name = "cupom")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cupom {
    
    @Id
    @GeneratedValue
    private UUID id;

    private String code;
    private Integer discount;
    private Date valid;

    //indicar que evento esse cupom pertence
    @ManyToOne
    @JoinColumn(name = "evento_id")
    private Evento evento;
}
