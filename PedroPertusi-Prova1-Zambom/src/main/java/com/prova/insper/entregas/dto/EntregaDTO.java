package com.prova.insper.entregas.dto;

import com.prova.insper.entregas.Entrega;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntregaDTO {

    private String identifier;
    private String cpfEntregador;
    private Double distancia;

    public static EntregaDTO convert(Entrega entrega) {
        EntregaDTO entregaDTO = new EntregaDTO();
        entregaDTO.setCpfEntregador(entrega.getCpfEntregador());
        entregaDTO.setDistancia(entrega.getDistancia());
        entregaDTO.setIdentifier(entrega.getIdentifier());
        return entregaDTO;
    }

}
