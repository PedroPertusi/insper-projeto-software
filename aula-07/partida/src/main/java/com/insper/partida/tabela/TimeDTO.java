package com.insper.partida.tabela;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TimeDTO {
    private String nome;
    private Integer pontos = 0;
    private Integer goalsPro = 0;
    private Integer goalsCont = 0;
    private Integer vitoria = 0;
    private Integer derrota = 0;
    private Integer empate = 0;
}
