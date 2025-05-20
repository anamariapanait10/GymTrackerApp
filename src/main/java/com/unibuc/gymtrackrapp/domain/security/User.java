package com.unibuc.gymtrackrapp.domain.security;

import com.unibuc.gymtrackrapp.domain.Goal;
import com.unibuc.gymtrackrapp.domain.WorkoutSession;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String email;
    private String password;

    @Singular
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "user_authority", joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")}, inverseJoinColumns = {@JoinColumn(name = "AUTHORITY_ID", referencedColumnName = "ID")})
    private Set<Authority> authorities;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Goal goal;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<WorkoutSession> sessions = new ArrayList<>();

    @Builder.Default
    private Boolean accountNonExpired = true;
    @Builder.Default
    private Boolean accountNonLocked = true;
    @Builder.Default
    private Boolean credentialsNonExpired = true;
    @Builder.Default
    private Boolean enabled = true;

}