package com.eventos.api.service;

import java.nio.ByteBuffer;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.eventos.api.domain.cupom.Cupom;
import com.eventos.api.domain.endereco.Endereco;
import com.eventos.api.domain.evento.Evento;
import com.eventos.api.domain.evento.EventoDetailsDTO;
import com.eventos.api.domain.evento.EventoDetailsDTO.CupomDto;
import com.eventos.api.domain.evento.EventoEnderecoProjecao;
import com.eventos.api.domain.evento.EventoRequestDTO;
import com.eventos.api.domain.evento.EventoResponseDTO;
import com.eventos.api.repositories.EventoRepository;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class EventoService {
    @Autowired
    private EventoRepository repository;

    @Value("${aws.bucket.name}")
    private String bucketName;

      @Value("${admin.key}")
    private String adminKey;

    @Autowired
    private EnderecoService enderecoService;

    @Autowired
    private CupomService cupomService;


    @Autowired
    private S3Client s3Client;
    
    public Evento criarEvento(EventoRequestDTO dto ){

        String imgUrl = null;



        Evento newEvent = new Evento();
        newEvent.setTitle(dto.title());
        newEvent.setDescription(dto.description());
        newEvent.setEventUrl(dto.eventUrl());
        newEvent.setDate(new Date(dto.date()));
        newEvent.setImgUrl(imgUrl);
        newEvent.setRemote(dto.remote());

        repository.save(newEvent);

        return newEvent;
    }

    private String uploadImg(MultipartFile multipartFile) {
            String filename = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();

        try {
            PutObjectRequest putOb = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(filename)
                    .build();
            s3Client.putObject(putOb, RequestBody.fromByteBuffer(ByteBuffer.wrap(multipartFile.getBytes())));
            GetUrlRequest request = GetUrlRequest.builder()
                    .bucket(bucketName)
                    .key(filename)
                    .build();

            return s3Client.utilities().getUrl(request).toString();
        } catch (Exception e) {
            System.out.println("erro ao subir arquivo");
            System.out.println(e.getMessage());
            return "";
        }
    }

    
    public List<EventoResponseDTO> getUpcomingEvents(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<EventoEnderecoProjecao> eventsPage = this.repository.findUpcomingEvents(new Date(), pageable);
        return eventsPage.map(event -> new EventoResponseDTO(
                        event.getId(),
                        event.getTitle(),
                        event.getDescription(),
                        event.getDate(),
                        event.getCity() != null ? event.getCity() : "",
                        event.getUf() != null ? event.getUf() : "",
                        event.getRemote(),
                        event.getEventUrl(),
                        event.getImgUrl())
                )
                .stream().toList();
    }

    public List<EventoResponseDTO> getFilteredEvents(int page, int size, String city, String uf, Date startDate, Date endDate){
        city = (city != null) ? city : "";
        uf = (uf != null) ? uf : "";
        startDate = (startDate != null) ? startDate : new Date(0);
        endDate = (endDate != null) ? endDate : new Date();

        Pageable pageable = PageRequest.of(page, size);

        Page<EventoEnderecoProjecao> eventsPage = this.repository.findFilteredEvents(city, uf, startDate, endDate, pageable);
        return eventsPage.map(event -> new EventoResponseDTO(
                        event.getId(),
                        event.getTitle(),
                        event.getDescription(),
                        event.getDate(),
                        event.getCity() != null ? event.getCity() : "",
                        event.getUf() != null ? event.getUf() : "",
                        event.getRemote(),
                        event.getEventUrl(),
                        event.getImgUrl())
                )
                .stream().toList();
    }

       public EventoDetailsDTO getEventDetails(UUID eventId) {
        Evento event = repository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Evento não encontrado"));
                Optional<Endereco> address = enderecoService.findByEventId(eventId);

                List<Cupom> coupons = cupomService.consultCoupons(eventId, new Date());
        
                List<CupomDto> couponDTOs = coupons.stream()
                        .map(coupon -> new CupomDto(
                                coupon.getCode(),
                                coupon.getDiscount(),
                                coupon.getValid()))
                        .collect(Collectors.toList());
        
                return new EventoDetailsDTO(
                        event.getId(),
                        event.getTitle(),
                        event.getDescription(),
                        event.getDate(),
                        address.isPresent() ? address.get().getCity() : "",
                        address.isPresent() ? address.get().getUf() : "",
                        event.getImgUrl(),
                        event.getEventUrl(),
                        couponDTOs);
            }

    public Void deleteEvent(UUID eventId, String adminKey){
        if(adminKey == null || !adminKey.equals(this.adminKey)){
            throw new IllegalArgumentException("Chave inválida");
        }

        this.repository.delete(this.repository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Evento não encontrado")));

        return null;
    }




    
}
