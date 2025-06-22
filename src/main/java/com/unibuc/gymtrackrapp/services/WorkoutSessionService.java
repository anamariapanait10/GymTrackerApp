package com.unibuc.gymtrackrapp.services;

import com.unibuc.gymtrackrapp.domain.WorkoutSession;
import com.unibuc.gymtrackrapp.repositories.WorkoutSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WorkoutSessionService {
    private final WorkoutSessionRepository sessionRepository;

    public Flux<WorkoutSession> getAllSessions() {
        return sessionRepository.findAll();
    }

    public Flux<WorkoutSession> getAllSessionsOfUser(String email) {
        return sessionRepository.findAllByUserEmail(email);
    }

    public Flux<WorkoutSession> getAllSessionsOfUserForMonth(String email, int year, int month) {
        return sessionRepository.findAllByUserEmail(email).stream()
                .filter(session -> session.getDate().getYear() == year && session.getDate().getMonthValue() == month)
                .toList();
    }


    public Mono<WorkoutSession> getSession(UUID id) {
        return sessionRepository.findById(id).orElse(null);
    }

    public Mono<WorkoutSession> saveSession(WorkoutSession session) {
        return sessionRepository.save(session);
    }

    public Mono<Void> deleteSession(UUID id) {
        sessionRepository.deleteById(id);
    }

    public Mono<WorkoutSession> getUserSessionByDate(String email, LocalDate date) {
        return sessionRepository.findByUserEmailAndDate(email, date);
    }

    public Mono<WorkoutSession> updateSession(UUID id, WorkoutSession updatedSession) {
        WorkoutSession existingSession = sessionRepository.findById(id).orElse(null);
        if (existingSession != null) {
            updatedSession.setId(id);
            return sessionRepository.save(updatedSession);
        }
        return null;
    }
}
