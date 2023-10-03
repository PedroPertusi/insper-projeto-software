package com.insper.partida.equipe.exception;

public class TeamDoesntExistException extends RuntimeException{
    
    public TeamDoesntExistException() {
        super("Time não existe!");
    }
}
