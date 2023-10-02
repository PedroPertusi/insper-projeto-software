package com.prova.insper.entregas;

import com.prova.insper.entregas.dto.TotalEntregasEntregadorDTO;
import com.prova.insper.entregas.dto.EntregaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/entrega")
public class EntregasController {

    @Autowired
    private EntregasService entregasService;

    @PostMapping
    public EntregaDTO saveEntrega(@RequestBody EntregaDTO entregaDTO) {
        return entregasService.saveEntrega(entregaDTO);
    }

    @GetMapping
    public List<EntregaDTO> getEntregas() {
        return entregasService.getEntregas();
    }

    @GetMapping("/{identifier}")
    public EntregaDTO getEntrega(@PathVariable String identifier) {
        return entregasService.getEntrega(identifier);
    }

    @DeleteMapping("/{identifier}")
    public void deleteEntrega(@PathVariable String identifier) {
        entregasService.deleteEntregas(identifier);
    }

    @GetMapping("/entregasDistanciaMinimo")
    public List<EntregaDTO> getTotal(@RequestParam Float minimo) {
        return entregasService.getEntregasMaiorMinimo(minimo);
    }

    @GetMapping("/entregasPorEntregadorMinimo")
    public List<TotalEntregasEntregadorDTO> getEntregasEntregador(@RequestParam Float minimo, @RequestParam List<String> cpfs) {
        return entregasService.getEntregasMaiorMinimoPorEntregador(minimo, cpfs);
    }

}
