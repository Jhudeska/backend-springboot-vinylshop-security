package nl.novi.vinylshop.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @GetMapping("/login")
    public String login(@AuthenticationPrincipal Jwt jwt) {
        if(jwt != null){
            return "Login in " + jwt.getClaimAsString("preferred_username");
        }
        return "Hello, anonymous user!";
    }
}
