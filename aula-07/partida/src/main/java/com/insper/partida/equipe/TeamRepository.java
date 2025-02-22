package com.insper.partida.equipe;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends MongoRepository<Team, String> {
    Team findByIdentifier(String identifier);

    Team findFirstByIdentifier(String identifier);

    Boolean existsByIdentifier(String identifier);
}
