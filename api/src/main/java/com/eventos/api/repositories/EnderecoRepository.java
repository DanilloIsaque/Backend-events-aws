package com.eventos.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import com.eventos.api.domain.endereco.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco,UUID>{
    
}
