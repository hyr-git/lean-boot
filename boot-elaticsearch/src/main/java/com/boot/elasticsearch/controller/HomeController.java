package com.boot.elasticsearch.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class HomeController {

    //@GetMapping("/home")
    @GetMapping("/home")
    public String home(){
        return "home page ";
    }
}
