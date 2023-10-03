package com.insper.partida.equipe;

import com.insper.partida.common.ErrorDTO;
import com.insper.partida.equipe.exception.TeamAlreadyExistsException;
import com.insper.partida.equipe.exception.TeamDoesntExistException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

@ControllerAdvice
public class TeamControllerAdvice {

    @ExceptionHandler(TeamAlreadyExistsException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO teamAlreadyExistsException(TeamAlreadyExistsException ex) {
        ErrorDTO error = new ErrorDTO();
        error.setMessage(ex.getMessage());
        error.setCode(400);
        error.setTime(LocalDateTime.now());
        return error;
    }

    @ExceptionHandler(TeamDoesntExistException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO teamDoesntExistException(TeamDoesntExistException ex) {
        ErrorDTO error = new ErrorDTO();
        error.setMessage(ex.getMessage());
        error.setCode(404);
        error.setTime(LocalDateTime.now());
        return error;
    }

}
