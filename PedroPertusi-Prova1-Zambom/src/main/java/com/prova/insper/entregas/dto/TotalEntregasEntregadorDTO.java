package com.prova.insper.entregas.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TotalEntregasEntregadorDTO {
    private String cpfEntregador;
    private List<EntregaDTO> entregas;
}
