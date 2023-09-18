package com.insper.partida.equipe;

public class TeamAlreadyExistsException extends RuntimeException {
    public TeamAlreadyExistsException() { //pode passar string aqui no construtor, se msg dinamica
        super("Time já existe!");
    }
}
