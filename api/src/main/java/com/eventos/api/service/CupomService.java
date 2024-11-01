package com.eventos.api.service;


import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eventos.api.domain.cupom.Cupom;
import com.eventos.api.domain.cupom.CupomRequestDTO;
import com.eventos.api.domain.evento.Evento;
import com.eventos.api.repositories.CupomRepository;
import com.eventos.api.repositories.EventoRepository;

@Service
public class CupomService {
    @Autowired
    private CupomRepository cupomRepository;

    @Autowired
    private EventoRepository eventoRepository;

    public Cupom addCouponToEvent(UUID eventId, CupomRequestDTO cupomData) {
        Evento evento = eventoRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Evento não encontrado."));

        Cupom cupom = new Cupom();
        cupom.setCode(cupomData.code());
        cupom.setDiscount(cupomData.discount());
        cupom.setValid(new Date(cupomData.valid()));
        cupom.setEvento(evento);

        return cupomRepository.save(cupom);
    }

    public List<Cupom> consultCoupons(UUID eventId, Date currentDate) {
        return cupomRepository.findByEventIdAndValidAfter(eventId, currentDate);
    }
}
