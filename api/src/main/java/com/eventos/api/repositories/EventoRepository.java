package com.eventos.api.repositories;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.eventos.api.domain.evento.Evento;

public interface EventoRepository extends JpaRepository<Evento,UUID> {
    
}
