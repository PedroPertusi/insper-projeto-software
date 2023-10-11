package com.insper.partida.common;

import java.io.IOException;

import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;

@Component
@Order(1)
public class UserFilter implements Filter{

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        
        String token = req.getHeader("token");

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<ReturnUserDTO> responseEntity = restTemplate.getForEntity("http://localhost:8080/login/token/" + token, ReturnUserDTO.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            chain.doFilter(request, response);
        }
        else{
            //retorna exception status code do response entity
            throw new ServletException("Token inválido");
        }
        
    }
}
