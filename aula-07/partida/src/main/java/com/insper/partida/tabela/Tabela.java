package com.insper.partida.tabela;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Tabela {
    
    @Id
    private String id;

    private String nome;

    private Integer pontos = 0;

    private Integer golsPro = 0;

    private Integer golsContra = 0;

    private Integer vitorias = 0;

    private Integer derrotas = 0;

    private Integer empates = 0;

    private Integer jogos = 0;
}
