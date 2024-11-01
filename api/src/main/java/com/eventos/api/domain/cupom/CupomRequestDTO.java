package com.eventos.api.domain.cupom;

public record CupomRequestDTO(String code, Integer discount, Long valid) {
    
}
