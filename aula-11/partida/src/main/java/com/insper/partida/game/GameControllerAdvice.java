package com.insper.partida.game;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.insper.partida.common.ErrorDTO;
import com.insper.partida.game.exception.GameDoesntExistException;
import com.insper.partida.game.exception.GameSameTeamException;

@ControllerAdvice
public class GameControllerAdvice {

    @ExceptionHandler(GameSameTeamException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO gameSameTeamException(GameSameTeamException ex) {
        ErrorDTO error = new ErrorDTO();
        error.setMessage(ex.getMessage());
        error.setCode(400);
        error.setTime(LocalDateTime.now());
        return error;
    }

    @ExceptionHandler(GameDoesntExistException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO gameDoesntExistException(GameDoesntExistException ex) {
        ErrorDTO error = new ErrorDTO();
        error.setMessage(ex.getMessage());
        error.setCode(404);
        error.setTime(LocalDateTime.now());
        return error;
    }


    
}
