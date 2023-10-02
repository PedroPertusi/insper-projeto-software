package com.prova.insper.entregas;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EntregasRepository extends JpaRepository<Entrega, Integer> {
    Entrega findByIdentifier(String identifier);

    List<Entrega> findByDistanciaGreaterThan(Float minimo);

    List<Entrega> findByDistanciaGreaterThanAndCpfEntregadorIn(Float minimo, List<String> cpfs);

    public boolean existsByIdentifier(String identifier);
}
