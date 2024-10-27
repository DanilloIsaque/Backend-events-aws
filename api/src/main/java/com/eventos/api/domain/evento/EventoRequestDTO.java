package com.eventos.api.domain.evento;

import org.springframework.web.multipart.MultipartFile;
                                                                //long por conta do valor que vai vir, um timestamp
public record EventoRequestDTO(String title, String description, Long date, String city, String state, Boolean remote, String eventUrl, MultipartFile image) {
}