package com.unibuc.gymtrackerapp.controllers;

import com.unibuc.gymtrackerapp.domain.Goal;
import com.unibuc.gymtrackerapp.domain.security.User;
import com.unibuc.gymtrackerapp.exceptions.ResourceNotFoundException;
import com.unibuc.gymtrackerapp.service.GoalService;
import com.unibuc.gymtrackerapp.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/goals")
@RequiredArgsConstructor
@Slf4j
public class GoalController {

    @Value("${gymtrackerapp.gateway.base-url}")
    private String gatewayBaseUrl;

    private final GoalService goalService;
    private final UserService userService;

    @GetMapping
    public String listGoals(Model model,  @RequestHeader("awbd-id") String correlationId) {
        log.info("correlation-id discount: {}", correlationId);
        List<Goal> goals = goalService.getAll();
        model.addAttribute("goals", goals);
        return "goals";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("goal", new Goal());
        return "create-goal";
    }

    @PostMapping("/create")
    public String createGoal(@ModelAttribute("goal") Goal goal, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        goal.setUser(user);

        if (user.getGoal() != null) {
            goalService.updateGoal(goal);
        }

        goalService.createGoal(goal);
        return "redirect:" + gatewayBaseUrl + "/user/goals";
    }

    @DeleteMapping("/{id}/delete")
    @Transactional
    public ResponseEntity<String> deleteGoal(@PathVariable UUID id) {
        try {
            goalService.deleteGoal(id);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException ex) {
            throw ex;
        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity.badRequest().body("Cannot delete exercise as it is associated with other records.");
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("An unexpected error occurred while trying to delete the exercise.");
        }

    }

}
