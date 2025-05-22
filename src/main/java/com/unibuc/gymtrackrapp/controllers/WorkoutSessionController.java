package com.unibuc.gymtrackrapp.controllers;

import com.unibuc.gymtrackrapp.config.Log;
import com.unibuc.gymtrackrapp.domain.Workout;
import com.unibuc.gymtrackrapp.domain.WorkoutSession;
import com.unibuc.gymtrackrapp.domain.security.User;
import com.unibuc.gymtrackrapp.services.UserService;
import com.unibuc.gymtrackrapp.services.WorkoutService;
import com.unibuc.gymtrackrapp.services.WorkoutSessionService;
import com.unibuc.gymtrackrapp.utils.UserAuthenticationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
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

        Map<LocalDate, WorkoutSession> sessions = workoutSessionService.getAllSessionsOfUser(username).stream()
                .collect(Collectors.toMap(WorkoutSession::getDate, session -> session));

        model.addAttribute("sessions", sessions);

        List<Workout> workouts = workoutService.getAllWorkouts();
        model.addAttribute("workouts", workouts);

        YearMonth currentMonth = (year != null && month != null)
                ? YearMonth.of(year, month)
                : YearMonth.now();

        YearMonth prevMonth = currentMonth.minusMonths(1);
        YearMonth nextMonth = currentMonth.plusMonths(1);
        model.addAttribute("currentMonth", currentMonth);
        model.addAttribute("prevMonth", prevMonth);
        model.addAttribute("nextMonth", nextMonth);
        model.addAttribute("workoutSession", new WorkoutSession());
        return "workouts";
    }

//    @GetMapping
//    public List<WorkoutSession> getAll() {
//        return workoutSessionService.getAllSessions();
//    }
//
//    @GetMapping("/{id}")
//    public WorkoutSession getById(@PathVariable UUID id) {
//        return workoutSessionService.getSession(id);
//    }
//
    @PostMapping
    public String saveWorkoutSession(@ModelAttribute WorkoutSession session) {
        String username = UserAuthenticationUtils.getLoggedUsername();
        User user = userService.getUserByEmail(username);
        session.setUser(user);
        workoutSessionService.saveSession(session);
        return "redirect:/sessions";
    }
//
//    @DeleteMapping("/{id}")
//    public void delete(@PathVariable UUID id) {
//        workoutSessionService.deleteSession(id);
//    }
}
