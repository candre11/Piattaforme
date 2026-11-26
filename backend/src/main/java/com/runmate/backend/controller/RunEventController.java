package com.runmate.backend.controller;

import com.runmate.backend.entity.RunEvent;
import com.runmate.backend.service.RunEventService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class RunEventController {

    private final RunEventService eventService;

    public RunEventController(RunEventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public List<RunEvent> getAllEvents() {
        return eventService.getAllEvents();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RunEvent createEvent(
            @RequestBody RunEvent event,
            @RequestParam Long clubId) {

        return eventService.createEvent(event, clubId);
    }

    @GetMapping("/upcoming")
    public List<RunEvent> getUpcomingEvents() {
        return eventService.getUpcomingEvents();
    }

    @GetMapping("/club/{clubId}")
    public List<RunEvent> getEventsByClub(
            @PathVariable Long clubId) {

        return eventService.getEventsByClub(clubId);
    }

    @GetMapping("/city/{city}")
    public List<RunEvent> getEventsByCity(
            @PathVariable String city) {

        return eventService.getEventsByCity(city);
    }
}