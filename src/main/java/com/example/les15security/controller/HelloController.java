package com.example.les15security.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String sayHello() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth.getPrincipal() instanceof UserDetails ud) {
            return "Hello " + ud.getUsername();
        } else {
            return "Hello stranger!";
        }
    }

// Robert-Jan's originele code
//     if (auth.getPrincipal() instanceof UserDetails) {
//        UserDetails ud = (UserDetails) auth.getPrincipal();
//        return "Hello " + ud.getUsername();
//    }
//        else {
//        return "Hello stranger!";
//    }


    // Geeft 401 Unauthorized als niet ingelogd.
    // Geeft 403 Forbidden als USER
    // Geeft 200 OK als ADMIN
    @GetMapping("/secret")
    public String tellSecret() {
        return "This is very secret...";
    }
}
