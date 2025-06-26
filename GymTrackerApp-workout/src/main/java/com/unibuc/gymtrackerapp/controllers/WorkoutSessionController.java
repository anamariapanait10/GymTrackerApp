package com.unibuc.gymtrackerapp.controllers;

import com.unibuc.gymtrackerapp.domain.entity.Workout;
import com.unibuc.gymtrackerapp.domain.entity.WorkoutSession;
import com.unibuc.gymtrackerapp.domain.entity.security.User;
import com.unibuc.gymtrackerapp.exceptions.ResourceNotFoundException;
import com.unibuc.gymtrackerapp.services.UserServiceProxy;
import com.unibuc.gymtrackerapp.services.WorkoutService;
import com.unibuc.gymtrackerapp.services.WorkoutSessionService;
import com.unibuc.gymtrackerapp.utils.UserAuthenticationUtils;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/sessions")
@Slf4j
public class WorkoutSessionController {

    @Value("${gymtrackerapp.gateway.base-url}")
    private String gatewayBaseUrl;

    private final WorkoutSessionService workoutSessionService;
    private final WorkoutService workoutService;
    private final UserServiceProxy userServiceProxy;

    @GetMapping
    public String showWorkoutsForm(@RequestParam(value = "year", required = false) Integer year,
                                   @RequestParam(value = "month", required = false) Integer month, Model model, @RequestHeader("awbd-id")
                                       String correlationId) {
        log.info("correlation-id discount: {}", correlationId);
        String username = UserAuthenticationUtils.getLoggedUsername();

        YearMonth currentMonth = (year != null && month != null)
                ? YearMonth.of(year, month)
                : YearMonth.now();

        YearMonth prevMonth = currentMonth.minusMonths(1);
        YearMonth nextMonth = currentMonth.plusMonths(1);
        model.addAttribute("currentMonth", currentMonth);
        model.addAttribute("prevMonth", prevMonth);
        model.addAttribute("nextMonth", nextMonth);

        Map<LocalDate, WorkoutSession> sessions = workoutSessionService.getAllSessionsOfUserForMonth(username, currentMonth.getYear(), currentMonth.getMonthValue()).stream()
                .collect(Collectors.toMap(WorkoutSession::getDate, session -> session));

        model.addAttribute("sessions", sessions);

        List<Workout> workouts = workoutService.getAllWorkouts();
        model.addAttribute("workouts", workouts);

        model.addAttribute("workoutSession", new WorkoutSession());
        return "sessions";
    }

    @PostMapping
    @CircuitBreaker(name="saveWorkoutSession", fallbackMethod = "saveWorkoutSessionFallback")
    public String saveWorkoutSession(@ModelAttribute WorkoutSession session) {
        String username = UserAuthenticationUtils.getLoggedUsername();
        UUID userId = userServiceProxy.getUserByEmail(username).block();

        WorkoutSession existingSession = workoutSessionService.getUserSessionByDate(username, session.getDate());
        if (existingSession != null) {
            workoutSessionService.updateSession(existingSession.getId(), session, userId);
            return "redirect:" + gatewayBaseUrl + "/workout/sessions?year=" + session.getDate().getYear() + "&month=" + session.getDate().getMonthValue();
        }

        workoutSessionService.saveSession(session, userId);

        return "redirect:" + gatewayBaseUrl + "/workout/sessions?year=" + session.getDate().getYear() + "&month=" + session.getDate().getMonthValue();
    }

    public String saveWorkoutSessionFallback(WorkoutSession session, Throwable throwable) {
        log.error("Failed to save workout session: {}", throwable.getMessage());
        String username = UserAuthenticationUtils.getLoggedUsername();

        UUID userId = userServiceProxy.getUserIdByUsernameFallback(username);

        WorkoutSession existingSession = workoutSessionService.getUserSessionByDate(username, session.getDate());
        if (existingSession != null) {
            workoutSessionService.updateSession(existingSession.getId(), session, userId);
            return "redirect:" + gatewayBaseUrl + "/workout/sessions?year=" + session.getDate().getYear() + "&month=" + session.getDate().getMonthValue();
        }

        workoutSessionService.saveSession(session, userId);

        return "redirect:" + gatewayBaseUrl + "/workout/sessions?year=" + session.getDate().getYear() + "&month=" + session.getDate().getMonthValue();
    }

    @DeleteMapping("/{date}")
    public ResponseEntity<String> delete(@PathVariable String date) {
        WorkoutSession session = workoutSessionService.getUserSessionByDate(UserAuthenticationUtils.getLoggedUsername(), LocalDate.parse(date));
        if (session == null)
            throw new ResourceNotFoundException("Workout session not found for date: " + date);

        workoutSessionService.deleteSession(session.getId());
        return ResponseEntity.ok().build();
    }
}