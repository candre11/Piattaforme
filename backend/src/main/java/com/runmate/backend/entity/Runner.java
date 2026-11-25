package com.runmate.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "runners")
public class Runner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    private String city;

    private Integer averagePace;

    private Double preferredDistance;

    private String level;

    public Runner() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getAveragePace() {
        return averagePace;
    }

    public void setAveragePace(Integer averagePace) {
        this.averagePace = averagePace;
    }

    public Double getPreferredDistance() {
        return preferredDistance;
    }

    public void setPreferredDistance(Double preferredDistance) {
        this.preferredDistance = preferredDistance;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}