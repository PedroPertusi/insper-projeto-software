package com.insper.partida.game.exception;

public class GameSameTeamException extends RuntimeException{

    public GameSameTeamException() {
        super("O time mandante não pode ser igual ao time visitante");
    }
}
