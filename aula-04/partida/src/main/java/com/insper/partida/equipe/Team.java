package com.insper.partida.equipe;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.insper.partida.game.Game;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@Document("team")
public class Team {

    @Id
    private String id;

    private String identifier;

    private String name;

    private String stadium;

}
