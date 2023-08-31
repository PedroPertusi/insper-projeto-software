package com.insper.partida.game;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.insper.partida.equipe.Team;
import com.insper.partida.equipe.TeamRepository;

@Service
public class  Cache {

    @Autowired TeamRepository teamRepository;

    private Map<String, Team> teams = new HashMap<>();


    public Team getTeam(String team) {
        if (teams.containsKey(team)) {
            return teams.get(team);
        }
        Team teamdb = teamRepository.findByIdentifier(team);
        if (teamdb != null) {
            teams.put(team, teamdb);
        }
        return teamdb;
    }
}
