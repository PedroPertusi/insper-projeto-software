package com.insper.partida.game;

import com.insper.partida.equipe.Team;
import com.insper.partida.equipe.TeamRepository;
import com.insper.partida.game.dto.GameReturnDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private Cache cache;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private GameRepository gameRepository;

    

    @GetMapping("/numbers")
    public HashMap<Integer,Integer> getInts(@RequestParam("amount") Integer amount) {
        List<Integer> nums = new ArrayList<>();
        Random ran = new Random();

        for (int i = 0; i < amount; i++) {
            nums.add(ran.nextInt(100));
        }
        //algoritimo ineficiente (arrumei ja)
        HashMap<Integer, Integer> count = new HashMap<>();
        for (int j = 0 ; j < nums.size(); j++) {
                count.merge(nums.get(j), 1, Integer::sum);
            }
        return count;
        }

    @GetMapping("/tenGamesHomeByTeam")
    public List<GameReturnDTO> getTenGamesHomeByTeam(@RequestParam(name = "team") String team) {

        Team teamDB = teamRepository.findByIdentifier(team);
        if (teamDB == null) {
            throw new RuntimeException("Time não encontrado");
        }

        List<Game> games = gameRepository.findTop10ByHomeOrAway(team);

        return games.stream().map(g -> GameReturnDTO.covert(g)).toList();

    }

    @GetMapping("/getAllGamesByTeams")
    public List<GameReturnDTO> getAllTeams(@RequestParam(name = "team") List<String> teams) {

        for (String team : teams) {
            Team teamDB = cache.getTeam(team);
            if (teamDB == null) {
                throw new RuntimeException("Time não encontrado");
            }
        }

        List<Game> allGames = new ArrayList<>();

        for (String team : teams) { //loop dando find all é dificil, tem como dar um query com IN passando uma lista de times
            List<Game> games = gameRepository.findTop10ByHomeOrAway(team);
            allGames.addAll(games);
        }

        return allGames.stream().map(g -> GameReturnDTO.covert(g)).toList();
    }


    @GetMapping("/getPointsByTeam")
    public HashMap<String, Integer>  getPointsByTeam(@RequestParam(name = "team") List<String> teams) {

        for (String team : teams) {
            Team teamDB = cache.getTeam(team);
            if (teamDB == null) {
                throw new RuntimeException("Time não encontrado");
            }
        }

        HashMap<String, Integer> teamPoints = new HashMap<>();

        for (String team : teams) {
            List<Game> games = gameRepository
                    .findAll()
                    .stream()
                    .filter(g -> g.getHome().equals(team) || g.getAway().equals(team))
                    .toList();

            Optional<Integer> points = games
                .stream() // Inicia uma stream para processar a lista de jogos
                .map(g -> numPoints(g, team)) // Transforma cada jogo em pontos usando a função numPoints
                .reduce((a, p) -> a + p); // Reduz os pontos acumulados somando-os em um único valor
            teamPoints.put(team, points.get());

        }

        return teamPoints;
    }

    private Integer numPoints(Game g, String team) {
        if (g.getScoreAway() == g.getScoreHome()) {
            return 1;
        } else if (g.getHome().equals(team) && g.getScoreHome() > g.getScoreAway()) {
            return 3;
        } else if (g.getAway().equals(team) && g.getScoreAway() > g.getScoreHome()) {
            return 3;
        }
        return 0;
    }


}
