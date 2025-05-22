package com.unibuc.gymtrackrapp.services;

import com.unibuc.gymtrackrapp.domain.WorkoutSession;
import com.unibuc.gymtrackrapp.domain.security.User;
import com.unibuc.gymtrackrapp.repositories.WorkoutSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WorkoutSessionService {
    private final WorkoutSessionRepository sessionRepository;
    public List<WorkoutSession> getAllSessions() {
        return sessionRepository.findAll();
    }

    public List<WorkoutSession> getAllSessionsOfUser(String email) {
        return sessionRepository.findAllByUserEmail(email);
    }

    public WorkoutSession getSession(UUID id) {
        return sessionRepository.findById(id).orElse(null);
    }

    public WorkoutSession saveSession(WorkoutSession session) {
        return sessionRepository.save(session);
    }

    public void deleteSession(UUID id) {
        sessionRepository.deleteById(id);
    }
}
