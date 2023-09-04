package com.insper.partida.tabela;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.insper.partida.equipe.TeamService;
import com.insper.partida.equipe.dto.TeamReturnDTO;
import com.insper.partida.game.Game;
import com.insper.partida.game.GameService;

@Service
public class TabelaService {

    @Autowired
    private TeamService teamService;

    @Autowired
    private GameService gameService;

    public List<TimeDTO> getTabela() {
        List<TeamReturnDTO> times = teamService.listTeams();
        List<TimeDTO> response = new ArrayList<>();

        for (TeamReturnDTO time : times) {
            List<Game> games = gameService.getGameByTeam(time.getIdentifier());
            TimeDTO timeDTO = new TimeDTO();
            timeDTO.setNome(time.getName());

            for (Game game : games) {
                int pontos = verificaResultado(time, game);
                timeDTO.setPontos(timeDTO.getPontos() + pontos);
                if (pontos == 3) {
                    timeDTO.setVitoria(timeDTO.getVitoria() + 1);
                }
                else if (pontos == 1) {
                    timeDTO.setEmpate(timeDTO.getEmpate() + 1);
                }
                else {
                    timeDTO.setDerrota(timeDTO.getDerrota() + 1);
                }
                Integer golsPro = verificaGolsPro(time, game);
                Integer golsCont = verificaGolsCont(time, game);
                timeDTO.setGoalsPro(timeDTO.getGoalsPro() + golsPro);
                timeDTO.setGoalsCont(timeDTO.getGoalsCont() + golsCont);

            }
            response.add(timeDTO);
        }
        response.sort(Comparator.comparingInt(TimeDTO::getPontos).reversed());
        return response;
    }

    private Integer verificaResultado(TeamReturnDTO time, Game game) {
        //pontos 
        if (game.getScoreHome() == game.getScoreAway()) {
            return 1;
        }
        else if (game.getHome().equals(time.getIdentifier()) && game.getScoreHome() > game.getScoreAway()) {
            return 3;
        }
        else if (game.getAway().equals(time.getIdentifier()) && game.getScoreHome() < game.getScoreAway()) {
            return 3;
        }
        else {
            return 0;
        }
    }

    private Integer verificaGolsPro(TeamReturnDTO time, Game game) {
        //gols a favor e gols contra 
        if (game.getHome().equals(time.getIdentifier())) {
            return game.getScoreHome();
        }
        else {
            return game.getScoreAway();
        }
    }

    private Integer verificaGolsCont(TeamReturnDTO time, Game game) {
        //gols a favor e gols contra 
        if (game.getHome().equals(time.getIdentifier())) {
            return game.getScoreAway();
        }
        else {
            return game.getScoreHome();
        }
    }
}
