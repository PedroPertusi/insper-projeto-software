package com.insper.partida.game;

import com.insper.partida.equipe.Team;
import com.insper.partida.equipe.TeamService;
import com.insper.partida.equipe.dto.SaveTeamDTO;

import com.insper.partida.game.dto.EditGameDTO;
import com.insper.partida.game.dto.GameReturnDTO;
import com.insper.partida.game.dto.SaveGameDTO;
import com.insper.partida.tabela.Tabela;
import com.insper.partida.tabela.TabelaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private TeamService teamService;

    @Autowired
    private TabelaRepository tabelaRepository;

    public Page<GameReturnDTO> listGames(String home, String away, Integer attendance, Pageable pageable) {
        if (home != null && away != null) {

            Team tHome = teamService.getTeam(home);
            Team tAway = teamService.getTeam(away);

            Page<Game> games = gameRepository.findByHomeAndAway(tHome.getIdentifier(), tAway.getIdentifier(), pageable);
            return games.map(game -> GameReturnDTO.covert(game));

        } else if (attendance != null) {
            Page<Game> games =  gameRepository.findByAttendanceGreaterThan(attendance, pageable);
            return games.map(game -> GameReturnDTO.covert(game));
        }
        Page<Game> games =  gameRepository.findAll(pageable);
        return games.map(game -> GameReturnDTO.covert(game));
    }

    public GameReturnDTO saveGame(SaveGameDTO saveGameDTO) {
        Team teamM = teamService.getTeam(saveGameDTO.getHome());
        Team teamV = teamService.getTeam(saveGameDTO.getAway());
        Tabela tabelaV;
        Tabela tabelaM;

        if (teamM == null || teamV == null) {
            return null;
        }

        if (tabelaRepository.findFirstByNome(teamV.getName()) == null) {
            tabelaM = new Tabela();
            tabelaM.setNome(teamM.getName());
        }
        else {
            tabelaM = tabelaRepository.findFirstByNome(teamV.getName());
        }


        if (tabelaRepository.findFirstByNome(teamV.getName()) == null) {
            tabelaV = new Tabela();
            tabelaV.setNome(teamV.getName());
        }
        else {
            tabelaV = tabelaRepository.findFirstByNome(teamV.getName());
        }

        Game game = new Game();
        game.setIdentifier(UUID.randomUUID().toString());
        game.setHome(saveGameDTO.getHome());
        game.setAway(saveGameDTO.getAway());
        game.setAttendance(0);
        game.setScoreHome(0);
        game.setScoreAway(0);
        game.setGameDate(LocalDateTime.now());
        game.setStatus("SCHEDULED");

        gameRepository.save(game);
        tabelaRepository.save(tabelaV);
        tabelaRepository.save(tabelaM);
  
        return GameReturnDTO.covert(game);

    }

    public GameReturnDTO editGame(String identifier, EditGameDTO editGameDTO) {
        Game gameBD = gameRepository.findByIdentifier(identifier);

        gameBD.setScoreAway(editGameDTO.getScoreAway());
        gameBD.setScoreHome(editGameDTO.getScoreHome());
        gameBD.setAttendance(editGameDTO.getAttendance());
        gameBD.setStatus("FINISHED");
        
        Tabela tabelaA = tabelaRepository.findFirstByNome(teamService.getTeam(gameBD.getAway()).getName());
        Tabela tabelaH = tabelaRepository.findFirstByNome(teamService.getTeam(gameBD.getHome()).getName());
        tabelaA.setJogos(tabelaA.getJogos() + 1);
        tabelaH.setJogos(tabelaH.getJogos() + 1);

        if (editGameDTO.getScoreAway() == editGameDTO.getScoreHome()) {
            tabelaA.setPontos(tabelaA.getPontos() + 1);
            tabelaH.setPontos(tabelaH.getPontos() + 1);
            tabelaA.setEmpates(tabelaA.getEmpates() + 1);
            tabelaH.setEmpates(tabelaH.getEmpates() + 1);
        }
        else if (editGameDTO.getScoreAway() > editGameDTO.getScoreHome()) {
            tabelaA.setPontos(tabelaA.getPontos() + 3);
            tabelaA.setVitorias(tabelaA.getVitorias() + 1);
            tabelaH.setDerrotas(tabelaH.getDerrotas() + 1);
        }
        else {
            tabelaH.setPontos(tabelaH.getPontos() + 3);
            tabelaH.setVitorias(tabelaH.getVitorias() + 1);
            tabelaA.setDerrotas(tabelaA.getDerrotas() + 1);
        }

        
        tabelaA.setGolsPro(tabelaA.getGolsPro() + editGameDTO.getScoreAway());
        tabelaA.setGolsContra(tabelaA.getGolsContra() + editGameDTO.getScoreHome());

        tabelaH.setGolsPro(tabelaH.getGolsPro() + editGameDTO.getScoreHome());
        tabelaH.setGolsContra(tabelaH.getGolsContra() + editGameDTO.getScoreAway());

        tabelaRepository.save(tabelaH);
        tabelaRepository.save(tabelaA);
        Game game = gameRepository.save(gameBD);
        return GameReturnDTO.covert(game);
    }

    public void deleteGame(String identifier) {
        Game gameBD = gameRepository.findByIdentifier(identifier);
        if (gameBD != null) {
            gameRepository.delete(gameBD);
        }
    }

    public Integer getScoreTeam(String identifier) {
        Team team = teamService.getTeam(identifier);
        return 0;
    }

    public GameReturnDTO getGame(String identifier) {
        return GameReturnDTO.covert(gameRepository.findByIdentifier(identifier));
    }

    public void generateData() {

        String [] teams = {"botafogo", "palmeiras", "gremio", "flamengo", "fluminense", "bragantino", "atletico-mg", "athletico-pr", "fortaleza", "cuiaba", "sao-paulo",
                        "internacional", "cruzeiro", "corinthians", "goias", "bahia", "santos", "vasco", "coritiba", "america-mg"};

        for (String team : teams) {
            SaveTeamDTO saveTeamDTO = new SaveTeamDTO();
            saveTeamDTO.setName(team);
            saveTeamDTO.setStadium(team);
            saveTeamDTO.setIdentifier(team);

            Team teamDB = teamService.getTeam(team);
            if (teamDB == null) {
                teamService.saveTeam(saveTeamDTO);
            }
        }

        for (int i = 0; i < 100; i++) {

            Integer team1 = new Random().nextInt(20);
            Integer team2 = new Random().nextInt(20);
            while  (team1 == team2) {
                team2 = new Random().nextInt(20);
            }
            
            SaveGameDTO ng = new SaveGameDTO();
            ng.setAway(teams[team1]);
            ng.setHome(teams[team2]);
            GameReturnDTO g = saveGame(ng);

            EditGameDTO eGameDTO = new EditGameDTO();
            eGameDTO.setScoreAway(new Random().nextInt(4));
            eGameDTO.setScoreHome(new Random().nextInt(4));
            eGameDTO.setAttendance(new Random().nextInt(4) * 1000);
            GameReturnDTO gr = editGame(g.getIdentifier(), eGameDTO);

            Game game = gameRepository.findByIdentifier(gr.getIdentifier());
            game.setStadium(teams[team1]);
            System.out.println(i);

            gameRepository.save(game);
        }
    }

    public List<Game> getGameByTeam(String identifier) {
        return gameRepository.findByHomeOrAway(identifier, identifier);
    }
}
