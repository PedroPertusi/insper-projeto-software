package com.insper.user.user;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.insper.user.user.dto.ReturnUserDTO;

@Service
public class LoginService {

    private HashMap<String, ReturnUserDTO> cash = new HashMap<>();
    
    public ReturnUserDTO get(String token) {
        return cash.get(token);
    }

    public void put(String token, ReturnUserDTO user) {
        cash.put(token, user);
    }
}
