package com.eventos.api.controller;


import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventos.api.domain.cupom.Cupom;
import com.eventos.api.domain.cupom.CupomRequestDTO;
import com.eventos.api.service.CupomService;

@RestController
@RequestMapping("/api/cupom")
public class CupomController {
    
    @Autowired
    private CupomService service;

      @PostMapping("/evento/{eventId}")
    public ResponseEntity<Cupom> addCouponsToEvent(@PathVariable UUID eventId, @RequestBody CupomRequestDTO dto) {
        Cupom cupons = service.addCouponToEvent(eventId, dto);
        return ResponseEntity.ok(cupons);
    }
}
