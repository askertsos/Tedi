package com.rbbnbb.TediTry1.controller;

import com.rbbnbb.TediTry1.domain.User;
import com.rbbnbb.TediTry1.dto.LoginDTO;
import com.rbbnbb.TediTry1.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private AuthenticationService authService;

    @PostMapping("/register")
    public User registerUser(@RequestBody LoginDTO body){
        return authService.registerUser(body.getUsername(),body.getPassword());
    }
    // handler method to handle home page request
    @GetMapping("/index")
    public String home(){
        return "Registration and Login System";
    }
}