package com.runmate.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "challenge_participations",
    uniqueConstraints = @UniqueConstraint(
        columnNames = {"runner_id", "challenge_id"}
    )
)
public class ChallengeParticipation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "runner_id", nullable = false)
    private Runner runner;

    @ManyToOne(optional = false)
    @JoinColumn(name = "challenge_id", nullable = false)
    private Challenge challenge;

    @Column(nullable = false)
    private LocalDateTime joinedAt;

    public ChallengeParticipation() {
    }

    public ChallengeParticipation(Runner runner,
                                  Challenge challenge) {
        this.runner = runner;
        this.challenge = challenge;
        this.joinedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Runner getRunner() {
        return runner;
    }

    public Challenge getChallenge() {
        return challenge;
    }

    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }
}