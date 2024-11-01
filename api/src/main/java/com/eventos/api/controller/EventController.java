package com.eventos.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.eventos.api.domain.evento.Evento;
import com.eventos.api.domain.evento.EventoRequestDTO;
import com.eventos.api.domain.evento.EventoResponseDTO;
import com.eventos.api.service.EventoService;

@RestController
@RequestMapping("/api/event")
public class EventController {

    @Autowired
    private EventoService eventService;

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Evento> create(@RequestParam("title") String title,
                                        @RequestParam(value = "description", required = false) String description,
                                        @RequestParam("date") Long date,
                                        @RequestParam("city") String city,
                                        @RequestParam("state") String state,
                                        @RequestParam("remote") Boolean remote,
                                        @RequestParam("eventUrl") String eventUrl,
                                        @RequestParam(value = "image", required = false) MultipartFile image) {
                                                                              

        EventoRequestDTO eventRequestDTO = new EventoRequestDTO(title, description, date, city, state, remote, eventUrl, image);
        Evento newEvent = this.eventService.criarEvento(eventRequestDTO);
        return ResponseEntity.ok(newEvent);
    }

    @GetMapping
    public ResponseEntity<List<EventoResponseDTO>> getEvents(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        List<EventoResponseDTO> allEvents = this.eventService.getUpcomingEvents(page, size);
        return ResponseEntity.ok(allEvents);
    }

    

}
