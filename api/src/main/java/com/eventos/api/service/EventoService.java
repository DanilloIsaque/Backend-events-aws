package com.eventos.api.service;

import java.nio.ByteBuffer;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.eventos.api.domain.evento.Evento;
import com.eventos.api.domain.evento.EventoRequestDTO;
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


    
}
