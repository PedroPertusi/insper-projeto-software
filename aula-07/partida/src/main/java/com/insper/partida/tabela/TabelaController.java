package com.insper.partida.tabela;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tabela")
public class TabelaController {
    
    @Autowired
    private TabelaService tabelaService;

    @GetMapping
    public List<TimeDTO> setTabela() {
        return tabelaService.getTabela();
    }

}
