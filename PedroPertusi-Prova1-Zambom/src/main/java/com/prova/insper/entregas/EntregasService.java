package com.prova.insper.entregas;

import com.prova.insper.entregas.dto.TotalEntregasEntregadorDTO;
import com.prova.insper.entregas.dto.EntregaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EntregasService {

    @Autowired
    private EntregasRepository entregasRepository;

    public EntregaDTO saveEntrega(EntregaDTO entregaDTO) {
        entregaDTO.setIdentifier(UUID.randomUUID().toString());
        Entrega venda = Entrega.convert(entregaDTO);
        return EntregaDTO.convert(entregasRepository.save(venda));
    }

    public List<EntregaDTO> getEntregas() {
        return entregasRepository
                .findAll()
                .stream()
                .map(v -> EntregaDTO.convert(v))
                .collect(Collectors.toList());
    }

    public EntregaDTO getEntrega(String identifier) {
        Entrega entrega = entregasRepository.findByIdentifier(identifier);

        if (entrega != null) {
            return EntregaDTO.convert(entrega);
        } else {
            return null;    
        }
    }

    public void deleteEntregas(String identifier) {
        Entrega entrega = entregasRepository.findByIdentifier(identifier);
        if (entrega != null) {
            entregasRepository.delete(entrega);
        }
    }

    public List<EntregaDTO> getEntregasMaiorMinimo(Float minimo) {
        List<EntregaDTO> entregas = entregasRepository.findByDistanciaGreaterThan(minimo)
        .stream().map(entrega -> EntregaDTO.convert(entrega)).collect(Collectors.toList());

        return entregas;
    }

    public List<TotalEntregasEntregadorDTO> getEntregasMaiorMinimoPorEntregador(Float minimo, List<String> cpfs) {
        List<Entrega> entregas = entregasRepository.findByDistanciaGreaterThanAndCpfEntregadorIn(minimo, cpfs);

        Map<String, List<EntregaDTO>> entregasPorCpf = entregas.stream().map(entrega -> EntregaDTO.convert(entrega)).collect(Collectors.groupingBy(EntregaDTO::getCpfEntregador));

        List<TotalEntregasEntregadorDTO> entregasEntregadorDTO = entregasPorCpf.entrySet().stream().map(entregaPorCpf -> {
                    TotalEntregasEntregadorDTO totalEntregasEntregadorDTO = new TotalEntregasEntregadorDTO();
                    totalEntregasEntregadorDTO.setCpfEntregador(entregaPorCpf.getKey());
                    totalEntregasEntregadorDTO.setEntregas(entregaPorCpf.getValue());
                    return totalEntregasEntregadorDTO;
                })
                .collect(Collectors.toList());


        return entregasEntregadorDTO;
    }
}
