package com.insper.user.user;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.insper.user.user.dto.LoginDTO;
import com.insper.user.user.dto.ReturnUserDTO;
import com.insper.user.user.dto.TokenDTO;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private LoginService loginService;
    
    @PostMapping
    public TokenDTO login(@RequestBody LoginDTO loginDTO) {
        ReturnUserDTO user = userService.validateUser(loginDTO.getEmail(), loginDTO.getPassword());
        String rs = RandomStringUtils.random(20, true, true);

        loginService.put(rs, user);
        TokenDTO token = new TokenDTO();
        token.setToken(rs);
        token.setEmail(user.getEmail());
        token.setRoles(user.getRoles());

        return token;
        
    }
//rest template
    @GetMapping("/token/{token}")
    public ReturnUserDTO token(@PathVariable String token) {
        ReturnUserDTO user = loginService.get(token);
        if (user == null) {
            throw new RuntimeException("Token not found (HTTPS 404)");
        }
        return user;
    }
}
