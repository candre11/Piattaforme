package com.runmate.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "club_memberships",
    uniqueConstraints = @UniqueConstraint(
        columnNames = {"runner_id", "club_id"}
    )
)
public class ClubMembership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "runner_id", nullable = false)
    private Runner runner;

    @ManyToOne(optional = false)
    @JoinColumn(name = "club_id", nullable = false)
    private RunClub club;

    @Column(nullable = false)
    private LocalDateTime joinedAt;

    public ClubMembership() {
    }

    public ClubMembership(Runner runner, RunClub club) {
        this.runner = runner;
        this.club = club;
        this.joinedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Runner getRunner() {
        return runner;
    }

    public RunClub getClub() {
        return club;
    }

    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }
}