package com.eventos.api.domain.evento;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public record EventoDetailsDTO(
        UUID id,
        String title,
        String description,
        Date date,
        String city,
        String state,
        String imgUrl,
        String eventUrl,
        List<CupomDto> coupons) {

    public record CupomDto(
            String code,
            Integer discount,
            Date valid) {
    }
}