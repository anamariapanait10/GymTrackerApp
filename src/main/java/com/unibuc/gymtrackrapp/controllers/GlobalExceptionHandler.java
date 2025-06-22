package com.unibuc.gymtrackrapp.controllers;

import com.unibuc.gymtrackrapp.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public Mono<Rendering> handlerNotFoundException(Exception exception) {
        return Mono.just(Rendering.view("notFoundException")
                .modelAttribute("exception", exception)
                .build());
    }

}