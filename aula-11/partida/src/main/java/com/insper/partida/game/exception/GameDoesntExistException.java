package com.insper.partida.game.exception;

public class GameDoesntExistException extends RuntimeException {
    
    public GameDoesntExistException() {
        super("Jogo não existe");
    }
}
