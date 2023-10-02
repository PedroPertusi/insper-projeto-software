package com.prova.insper.entregas;

import com.prova.insper.entregas.dto.EntregaDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Entrega {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String identifier;
    private String cpfEntregador;
    private Double distancia;

    public static Entrega convert(EntregaDTO entregaDTO) {
        Entrega entrega = new Entrega();
        entrega.setCpfEntregador(entregaDTO.getCpfEntregador());
        entrega.setDistancia(entregaDTO.getDistancia());
        entrega.setIdentifier(entregaDTO.getIdentifier());
        return entrega;
    }

}
