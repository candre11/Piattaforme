package com.runmate.backend.service;

import com.runmate.backend.entity.RunClub;
import com.runmate.backend.entity.Runner;
import com.runmate.backend.repository.RunClubRepository;
import com.runmate.backend.repository.RunnerRepository;

import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class RunClubService {

    private final RunClubRepository runClubRepository;
    private final RunnerRepository runnerRepository;

    public RunClubService(
            RunClubRepository runClubRepository,
            RunnerRepository runnerRepository) {

        this.runClubRepository = runClubRepository;
        this.runnerRepository = runnerRepository;
    }

    public List<RunClub> getAllClubs() {
        return runClubRepository.findAll();
    }

    public RunClub createClub(RunClub club, Long ownerId) {

        Runner owner = runnerRepository.findById(ownerId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Runner not found"));

        club.setOwner(owner);

        return runClubRepository.save(club);
    }

    public List<RunClub> getClubsByCity(String city) {
        return runClubRepository.findByCityIgnoreCase(city);
    }

    public List<RunClub> getClubsByOwner(Long ownerId) {
        return runClubRepository.findByOwnerId(ownerId);
    }
}