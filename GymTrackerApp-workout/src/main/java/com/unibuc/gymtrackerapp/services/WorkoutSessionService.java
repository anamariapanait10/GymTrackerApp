package com.unibuc.gymtrackerapp.services;

import com.unibuc.gymtrackerapp.domain.entity.WorkoutSession;
import com.unibuc.gymtrackerapp.repositories.WorkoutSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
        return sessionRepository.findAllByUserUsername(email);
    }

    public List<WorkoutSession> getAllSessionsOfUserForMonth(String email, int year, int month) {
        return sessionRepository.findAllByUserUsername(email).stream()
                .filter(session -> session.getDate().getYear() == year && session.getDate().getMonthValue() == month)
                .toList();
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

    public WorkoutSession getUserSessionByDate(String email, LocalDate date) {
        return sessionRepository.findByUserUsernameAndDate(email, date);
    }

    public WorkoutSession updateSession(UUID id, WorkoutSession updatedSession) {
        WorkoutSession existingSession = sessionRepository.findById(id).orElse(null);
        if (existingSession != null) {
            updatedSession.setId(id);
            return sessionRepository.save(updatedSession);
        }
        return null;
    }
}
