package com.unibuc.gymtrackrapp.controllers;

import com.unibuc.gymtrackrapp.config.Log;
import com.unibuc.gymtrackrapp.domain.Workout;
import com.unibuc.gymtrackrapp.domain.WorkoutSession;
import com.unibuc.gymtrackrapp.domain.security.User;
import com.unibuc.gymtrackrapp.exceptions.ResourceNotFoundException;
import com.unibuc.gymtrackrapp.services.UserService;
import com.unibuc.gymtrackrapp.services.WorkoutService;
import com.unibuc.gymtrackrapp.services.WorkoutSessionService;
import com.unibuc.gymtrackrapp.utils.UserAuthenticationUtils;
import lombok.RequiredArgsConstructor;
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
public class WorkoutSessionController {

    private final WorkoutSessionService workoutSessionService;
    private final WorkoutService workoutService;
    private final UserService userService;

    @Log
    @GetMapping
    public String showWorkoutsForm(@RequestParam(value = "year", required = false) Integer year,
                                   @RequestParam(value = "month", required = false) Integer month, Model model) {

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
    public String saveWorkoutSession(@ModelAttribute WorkoutSession session) {
        String username = UserAuthenticationUtils.getLoggedUsername();
        User user = userService.getUserByEmail(username);
        session.setUser(user);

        WorkoutSession existingSession = workoutSessionService.getUserSessionByDate(username, session.getDate());
        if (existingSession != null) {
            workoutSessionService.updateSession(existingSession.getId(), session);
            return "redirect:/sessions?year=" + session.getDate().getYear() + "&month=" + session.getDate().getMonthValue();
        }

        workoutSessionService.saveSession(session);
        return "redirect:/sessions?year=" + session.getDate().getYear() + "&month=" + session.getDate().getMonthValue();
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