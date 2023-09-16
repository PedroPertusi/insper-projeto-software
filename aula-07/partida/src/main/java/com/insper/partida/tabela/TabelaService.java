package com.insper.partida.tabela;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TabelaService {
    @Autowired
    private TabelaRepository tabelaRepository;

    public List<Tabela> getTabela() {
        List<Tabela> tabelas = tabelaRepository.findAll();
        return tabelas;
    }
}
