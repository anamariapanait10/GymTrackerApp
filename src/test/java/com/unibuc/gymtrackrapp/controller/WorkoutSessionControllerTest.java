package com.unibuc.gymtrackrapp.controller;

import com.unibuc.gymtrackrapp.domain.WorkoutSession;
import com.unibuc.gymtrackrapp.domain.security.User;
import com.unibuc.gymtrackrapp.services.UserService;
import com.unibuc.gymtrackrapp.services.WorkoutService;
import com.unibuc.gymtrackrapp.services.WorkoutSessionService;
import com.unibuc.gymtrackrapp.utils.UserAuthenticationUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("h2")
public class WorkoutSessionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WorkoutSessionService workoutSessionService;

    @MockBean
    private WorkoutService workoutService;

    @MockBean
    private UserService userService;


        @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void showWorkoutsForm_ShouldReturnViewWithAttributes() throws Exception {
        YearMonth now = YearMonth.now();

        when(workoutSessionService.getAllSessionsOfUserForMonth(anyString(), anyInt(), anyInt()))
                .thenReturn(List.of());

        when(workoutService.getAllWorkouts())
                .thenReturn(List.of());

        mockMvc.perform(get("/sessions"))
                .andExpect(status().isOk())
                .andExpect(view().name("sessions"))
                .andExpect(model().attributeExists("currentMonth"))
                .andExpect(model().attributeExists("prevMonth"))
                .andExpect(model().attributeExists("nextMonth"))
                .andExpect(model().attributeExists("sessions"))
                .andExpect(model().attributeExists("workouts"))
                .andExpect(model().attributeExists("workoutSession"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void saveWorkoutSession_NewSession_ShouldSave() throws Exception {
        LocalDate date = LocalDate.of(2025, 6, 15);
        WorkoutSession session = new WorkoutSession();
        session.setDate(date);

        when(userService.getUserByEmail(anyString())).thenReturn(new User());
        when(workoutSessionService.getUserSessionByDate(anyString(), eq(date))).thenReturn(null);

        mockMvc.perform(post("/sessions")
                        .with(csrf())
                        .param("date", date.toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/sessions?year=2025&month=6"));

        verify(workoutSessionService, times(1)).saveSession(any(WorkoutSession.class));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void saveWorkoutSession_ExistingSession_ShouldUpdate() throws Exception {
        LocalDate date = LocalDate.of(2025, 6, 15);
        WorkoutSession existing = new WorkoutSession();
        existing.setId(UUID.randomUUID());
        existing.setDate(date);

        when(userService.getUserByEmail(anyString())).thenReturn(
                new com.unibuc.gymtrackrapp.domain.security.User());
        when(workoutSessionService.getUserSessionByDate(anyString(), eq(date))).thenReturn(existing);

        mockMvc.perform(post("/sessions")
                        .with(csrf())
                        .param("date", date.toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/sessions?year=2025&month=6"));

        verify(workoutSessionService, times(1)).updateSession(eq(existing.getId()), any(WorkoutSession.class));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void deleteWorkoutSession_ValidDate_ShouldDelete() throws Exception {
        LocalDate date = LocalDate.of(2025, 6, 15);
        WorkoutSession session = new WorkoutSession();
        session.setId(UUID.randomUUID());
        session.setDate(date);

        when(workoutSessionService.getUserSessionByDate(anyString(), eq(date))).thenReturn(session);

        mockMvc.perform(delete("/sessions/{date}", date.toString())
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(workoutSessionService, times(1)).deleteSession(session.getId());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void deleteWorkoutSession_NotFound_ShouldThrow() throws Exception {
        LocalDate date = LocalDate.of(2025, 6, 15);

        when(workoutSessionService.getUserSessionByDate(anyString(), eq(date))).thenReturn(null);

        mockMvc.perform(delete("/sessions/{date}", date.toString())
                        .with(csrf()))
                .andExpect(status().isNotFound()); // This assumes you have @ControllerAdvice for ResourceNotFoundException
    }
}
