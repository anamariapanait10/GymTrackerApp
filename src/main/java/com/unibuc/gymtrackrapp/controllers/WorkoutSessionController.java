package com.unibuc.gymtrackrapp.controllers;

import com.unibuc.gymtrackrapp.domain.WorkoutSession;
import com.unibuc.gymtrackrapp.services.WorkoutSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sessions")
public class WorkoutSessionController {

    private final WorkoutSessionService workoutSessionService;

    @GetMapping
    public List<WorkoutSession> getAll() {
        return workoutSessionService.getAllSessions();
    }

    @GetMapping("/{id}")
    public WorkoutSession getById(@PathVariable UUID id) {
        return workoutSessionService.getSession(id);
    }

    @PostMapping
    public WorkoutSession create(@RequestBody WorkoutSession session) {
        return workoutSessionService.saveSession(session);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        workoutSessionService.deleteSession(id);
    }
}
