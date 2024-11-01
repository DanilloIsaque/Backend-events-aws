package com.eventos.api.domain.evento;

import java.util.Date;
import java.util.UUID;

public record EventoResponseDTO(UUID id, String title, String description, Date date, String city, String state, Boolean remote, String eventUrl, String imgUrl){
    
}