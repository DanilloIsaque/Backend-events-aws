package com.eventos.api.repositories;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eventos.api.domain.cupom.Cupom;

public interface CupomRepository extends JpaRepository<Cupom,UUID>{
       List<Cupom> findByEventIdAndValidAfter(UUID eventId, Date currentDate);
}
