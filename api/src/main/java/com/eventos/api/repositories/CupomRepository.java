package com.eventos.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import com.eventos.api.domain.cupom.Cupom;

public interface CupomRepository extends JpaRepository<Cupom,UUID>{
    
}
