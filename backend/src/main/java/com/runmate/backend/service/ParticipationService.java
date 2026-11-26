package com.runmate.backend.service;

import com.runmate.backend.entity.*;
import com.runmate.backend.repository.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ParticipationService {

    private final ParticipationRepository participationRepository;
    private final RunnerRepository runnerRepository;
    private final RunEventRepository eventRepository;

    public ParticipationService(
            ParticipationRepository participationRepository,
            RunnerRepository runnerRepository,
            RunEventRepository eventRepository) {

        this.participationRepository = participationRepository;
        this.runnerRepository = runnerRepository;
        this.eventRepository = eventRepository;
    }

    @Transactional
    public Participation joinEvent(Long runnerId, Long eventId) {

        RunEvent event = eventRepository.findByIdForUpdate(eventId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Event not found"));

        Runner runner = runnerRepository.findById(runnerId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Runner not found"));

        if (!event.getEventDate().isAfter(LocalDateTime.now())) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Event already started");
        }

        if (participationRepository.existsByRunnerIdAndEventId(
                runnerId, eventId)) {

            throw new ResponseStatusException(
                HttpStatus.CONFLICT, "Already participating");
        }

        long participants =
            participationRepository.countByEventId(eventId);

        if (participants >= event.getMaxParticipants()) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT, "Event is full");
        }

        Participation participation =
            new Participation(runner, event);

        return participationRepository.save(participation);
    }

    @Transactional
    public void leaveEvent(Long runnerId, Long eventId) {

        eventRepository.findByIdForUpdate(eventId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Event not found"));

        Participation participation = participationRepository
            .findByRunnerIdAndEventId(runnerId, eventId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Participation not found"));

        participationRepository.delete(participation);
    }

    public List<Participation> getEventParticipants(Long eventId) {
        return participationRepository.findByEventId(eventId);
    }

    public List<Participation> getRunnerEvents(Long runnerId) {
        return participationRepository.findByRunnerId(runnerId);
    }

    public long getAvailableSpots(Long eventId) {

        RunEvent event = eventRepository.findById(eventId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Event not found"));

        return event.getMaxParticipants()
            - participationRepository.countByEventId(eventId);
    }
}