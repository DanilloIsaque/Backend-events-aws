package com.eventos.api.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails.Address;
import org.springframework.stereotype.Service;

import com.eventos.api.domain.endereco.Endereco;
import com.eventos.api.domain.evento.Evento;
import com.eventos.api.domain.evento.EventoRequestDTO;
import com.eventos.api.repositories.EnderecoRepository;

@Service
public class EnderecoService {
    
 @Autowired
    private EnderecoRepository repository;

    public Endereco createAddress(EventoRequestDTO data, Evento event) {
        Endereco endereco = new Endereco();
        endereco.setCity(data.city());
        endereco.setUf(data.state());
        endereco.setEvento(event);

        return repository.save(endereco);
    }

    public Optional<Endereco> findByEventId(UUID eventId) {
        return repository.findByEventId(eventId);
    }

}
