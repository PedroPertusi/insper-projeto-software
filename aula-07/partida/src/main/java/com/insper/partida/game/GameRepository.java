package com.insper.partida.game;

import com.insper.partida.equipe.Team;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends MongoRepository<Game, String> {
    Game findByIdentifier(String identifier);

    Page<Game> findByHomeAndAway(String tHome, String tAway, Pageable pageable);

    List<Game> findByHomeOrAway(String tHome, String tAway);

    Page<Game> findByAttendanceGreaterThan(Integer attendance, Pageable pageable);


    //@Query("select sum(g.scoreHome) from Game g where g.home = ?1")
    //public Integer sumScoreTeamHome(Team team);
}
