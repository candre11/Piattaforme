package com.runmate.backend.service;

import com.runmate.backend.entity.RunEvent;
import com.runmate.backend.entity.RunClub;
import com.runmate.backend.repository.RunEventRepository;
import com.runmate.backend.repository.RunClubRepository;

import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.time.LocalDateTime;

@Service
public class RunEventService {

    private final RunEventRepository eventRepository;
    private final RunClubRepository clubRepository;

    public RunEventService(
            RunEventRepository eventRepository,
            RunClubRepository clubRepository) {

        this.eventRepository = eventRepository;
        this.clubRepository = clubRepository;
    }

    public List<RunEvent> getAllEvents() {
        return eventRepository.findAll();
    }

    public RunEvent createEvent(RunEvent event, Long clubId) {

        RunClub club = clubRepository.findById(clubId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Club not found"));

        if (event.getEventDate() == null ||
            !event.getEventDate().isAfter(LocalDateTime.now())) {

            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Event date must be in the future");
        }

        if (event.getMaxParticipants() == null ||
            event.getMaxParticipants() <= 0) {

            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Invalid maximum number of participants");
        }

        if (event.getDistance() == null ||
            event.getDistance() <= 0) {

            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Invalid distance");
        }

        if (event.getTargetPace() == null ||
            event.getTargetPace() <= 0) {

            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Invalid target pace");
        }

        event.setClub(club);

        return eventRepository.save(event);
    }

    public List<RunEvent> getUpcomingEvents() {
        return eventRepository
            .findByEventDateAfterOrderByEventDateAsc(
                LocalDateTime.now());
    }

    public List<RunEvent> getEventsByClub(Long clubId) {
        return eventRepository.findByClubId(clubId);
    }

    public List<RunEvent> getEventsByCity(String city) {
        return eventRepository.findByClubCityIgnoreCase(city);
    }
}