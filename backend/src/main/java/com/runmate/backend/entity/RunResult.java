package com.runmate.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "run_results")
public class RunResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "runner_id", nullable = false)
    private Runner runner;

    @Column(nullable = false)
    private Double distance;

    @Column(nullable = false)
    private Integer durationSeconds;

    @Column(nullable = false)
    private LocalDateTime completedAt;

    public RunResult() {
    }

    public RunResult(Runner runner, Double distance,
                     Integer durationSeconds,
                     LocalDateTime completedAt) {

        this.runner = runner;
        this.distance = distance;
        this.durationSeconds = durationSeconds;
        this.completedAt = completedAt;
    }

    public Long getId() {
        return id;
    }

    public Runner getRunner() {
        return runner;
    }

    public Double getDistance() {
        return distance;
    }

    public Integer getDurationSeconds() {
        return durationSeconds;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    @Transient
    public double getAveragePace() {
        return durationSeconds / distance;
    }
}