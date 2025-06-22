package com.unibuc.gymtrackrapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;

@Controller
public class MainController {
    @RequestMapping({"","/","/home"})
    public Mono<Rendering> getHome() {
        return Mono.just(Rendering.view("main").build());
    }

    @GetMapping("/login")
    public Mono<String> showLogInForm() {
        return Mono.just("login");
    }

    @GetMapping("/access_denied")
    public Mono<String> accessDeniedPage() {
        return Mono.just("accessDenied");
    }

}