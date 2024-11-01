package com.eventos.api.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eventos.api.domain.endereco.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco,UUID>{
     public Optional<Endereco> findByEventId(UUID eventId);
}
