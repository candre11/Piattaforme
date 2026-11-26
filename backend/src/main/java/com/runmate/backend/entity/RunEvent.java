package com.runmate.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "run_events")
public class RunEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private LocalDateTime eventDate;

    @Column(nullable = false)
    private String meetingPoint;

    @Column(nullable = false)
    private Double distance;

    @Column(nullable = false)
    private Integer targetPace;

    @Column(nullable = false)
    private Integer maxParticipants;

    @ManyToOne(optional = false)
    @JoinColumn(name = "club_id", nullable = false)
    private RunClub club;

    public RunEvent() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDateTime eventDate) {
        this.eventDate = eventDate;
    }

    public String getMeetingPoint() {
        return meetingPoint;
    }

    public void setMeetingPoint(String meetingPoint) {
        this.meetingPoint = meetingPoint;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Integer getTargetPace() {
        return targetPace;
    }

    public void setTargetPace(Integer targetPace) {
        this.targetPace = targetPace;
    }

    public Integer getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(Integer maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public RunClub getClub() {
        return club;
    }

    public void setClub(RunClub club) {
        this.club = club;
    }
}