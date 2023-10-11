package com.insper.user.user.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenDTO {
    private String email;
    private String token;
    private List<String> roles;
    
}
