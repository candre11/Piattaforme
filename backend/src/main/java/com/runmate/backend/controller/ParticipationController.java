package com.runmate.backend.controller;

import com.runmate.backend.entity.Participation;
import com.runmate.backend.service.ParticipationService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/participations")
public class ParticipationController {

    private final ParticipationService participationService;

    public ParticipationController(
            ParticipationService participationService) {

        this.participationService = participationService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Participation joinEvent(
            @RequestParam Long runnerId,
            @RequestParam Long eventId) {

        return participationService.joinEvent(runnerId, eventId);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void leaveEvent(
            @RequestParam Long runnerId,
            @RequestParam Long eventId) {

        participationService.leaveEvent(runnerId, eventId);
    }

    @GetMapping("/event/{eventId}")
    public List<Participation> getEventParticipants(
            @PathVariable Long eventId) {

        return participationService.getEventParticipants(eventId);
    }

    @GetMapping("/runner/{runnerId}")
    public List<Participation> getRunnerEvents(
            @PathVariable Long runnerId) {

        return participationService.getRunnerEvents(runnerId);
    }

    @GetMapping("/available/{eventId}")
    public long getAvailableSpots(
            @PathVariable Long eventId) {

        return participationService.getAvailableSpots(eventId);
    }
}