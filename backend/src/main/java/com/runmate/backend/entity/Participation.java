package com.runmate.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "participations",
    uniqueConstraints = @UniqueConstraint(
        columnNames = {"runner_id", "event_id"}
    )
)
public class Participation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "runner_id", nullable = false)
    private Runner runner;

    @ManyToOne(optional = false)
    @JoinColumn(name = "event_id", nullable = false)
    private RunEvent event;

    @Column(nullable = false)
    private LocalDateTime joinedAt;

    public Participation() {
    }

    public Participation(Runner runner, RunEvent event) {
        this.runner = runner;
        this.event = event;
        this.joinedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Runner getRunner() {
        return runner;
    }

    public RunEvent getEvent() {
        return event;
    }

    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }
}