package com.runmate.backend.service;

import com.runmate.backend.entity.Challenge;
import com.runmate.backend.entity.RunClub;
import com.runmate.backend.repository.ChallengeRepository;
import com.runmate.backend.repository.RunClubRepository;

import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChallengeService {

    private final ChallengeRepository challengeRepository;
    private final RunClubRepository clubRepository;

    public ChallengeService(
            ChallengeRepository challengeRepository,
            RunClubRepository clubRepository) {

        this.challengeRepository = challengeRepository;
        this.clubRepository = clubRepository;
    }

    public List<Challenge> getAllChallenges() {
        return challengeRepository.findAll();
    }

    public Challenge createChallenge(
            Challenge challenge, Long clubId) {

        RunClub club = clubRepository.findById(clubId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Club not found"));

        if (challenge.getName() == null ||
            challenge.getName().isBlank()) {

            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Name is required");
        }

        if (challenge.getStartDate() == null ||
            challenge.getEndDate() == null ||
            !challenge.getEndDate().isAfter(
                challenge.getStartDate())) {

            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Invalid dates");
        }

        if (!challenge.getEndDate().isAfter(
                LocalDateTime.now())) {

            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Challenge already ended");
        }

        if (challenge.getTargetDistance() == null ||
            !Double.isFinite(challenge.getTargetDistance()) ||
            challenge.getTargetDistance() <= 0) {

            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Invalid target distance");
        }

        challenge.setClub(club);

        return challengeRepository.save(challenge);
    }

    public List<Challenge> getActiveChallenges() {

        LocalDateTime now = LocalDateTime.now();

        return challengeRepository
            .findByStartDateLessThanEqualAndEndDateGreaterThanEqual(
                now, now);
    }

    public List<Challenge> getChallengesByClub(Long clubId) {
        return challengeRepository.findByClubId(clubId);
    }
}