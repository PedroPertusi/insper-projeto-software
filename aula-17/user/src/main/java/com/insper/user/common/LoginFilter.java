package com.insper.user.common;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.insper.user.user.LoginService;
import com.insper.user.user.UserService;
import com.insper.user.user.dto.ReturnUserDTO;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;


@Component
@Order(1) //ordem de execucao (posso ter vários que existe em ordem)
public class LoginFilter implements Filter {

    @Autowired
    private LoginService loginService;

    List<String> openRoutesGET = Arrays.asList("/user");
    List<String> openRoutesPOST = Arrays.asList("/login");

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        
        String token = req.getHeader("token");

        String uri = req.getRequestURI();
        String method = req.getMethod();

        if (method.equals("GET") && openRoutesGET.contains(uri)) {
            chain.doFilter(request, response);
        }
        else if (method.equals("POST") && openRoutesPOST.contains(uri)) {
            chain.doFilter(request, response);
        }
        else if (method.equals("GET") && uri.startsWith("/login/token")) {
            chain.doFilter(request, response);
        }
        else {
            ReturnUserDTO user = loginService.get(token);   
            if (user == null) {
                throw new RuntimeException("User not found (HTTPS 404)");
            }
            else {
                chain.doFilter(request, response);
            }
        }
    }

}