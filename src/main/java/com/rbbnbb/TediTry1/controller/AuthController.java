package com.rbbnbb.TediTry1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class AuthController {

    // handler method to handle home page request
    @GetMapping("/index")
    public String home(){
        return "Registration and Login System";
    }
}
