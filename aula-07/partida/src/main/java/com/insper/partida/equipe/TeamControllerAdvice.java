package com.insper.partida.equipe;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class TeamControllerAdvice {

    @ExceptionHandler(TeamAlreadyExistsException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HashMap<String,String> teamAlreadyExistsException(TeamAlreadyExistsException ex) {
        HashMap<String, String> error = new HashMap<>();
        error.put("message", ex.getMessage());
        error.put("code", "400");
        error.put("time", LocalDateTime.now().toString());
        return error;
    }
    
}
