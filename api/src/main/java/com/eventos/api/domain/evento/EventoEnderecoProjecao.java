package com.eventos.api.domain.evento;

import java.util.Date;
import java.util.UUID;

public interface EventoEnderecoProjecao {
    UUID getId();
    String getTitle();
    String getDescription();
    Date getDate();
    String getImgUrl();
    String getEventUrl();
    Boolean getRemote();
    String getCity();
    String getUf();
}
