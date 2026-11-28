package com.runmate.backend.service;

import com.runmate.backend.entity.*;
import com.runmate.backend.repository.*;

import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChallengeParticipationService {

    private final ChallengeParticipationRepository participationRepository;
    private final ChallengeRepository challengeRepository;
    private final RunnerRepository runnerRepository;
    private final RunResultRepository resultRepository;

    public ChallengeParticipationService(
            ChallengeParticipationRepository participationRepository,
            ChallengeRepository challengeRepository,
            RunnerRepository runnerRepository,
            RunResultRepository resultRepository) {

        this.participationRepository = participationRepository;
        this.challengeRepository = challengeRepository;
        this.runnerRepository = runnerRepository;
        this.resultRepository = resultRepository;
    }

    public record ChallengeProgress(
        Long challengeId,
        Long runnerId,
        String runnerName,
        double targetDistance,
        double completedDistance,
        double remainingDistance,
        double percentage,
        boolean completed
    ) {}

    public ChallengeParticipation joinChallenge(
            Long runnerId, Long challengeId) {

        Runner runner = runnerRepository.findById(runnerId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Runner not found"));

        Challenge challenge = challengeRepository.findById(challengeId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Challenge not found"));

        if (!challenge.getEndDate().isAfter(LocalDateTime.now())) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Challenge ended");
        }

        if (participationRepository
                .existsByRunnerIdAndChallengeId(
                    runnerId, challengeId)) {

            throw new ResponseStatusException(
                HttpStatus.CONFLICT, "Already participating");
        }

        try {
            return participationRepository.saveAndFlush(
                new ChallengeParticipation(runner, challenge));
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT, "Participation conflict");
        }
    }

    public List<ChallengeParticipation> getRunnerChallenges(
            Long runnerId) {

        return participationRepository.findByRunnerId(runnerId);
    }

    public ChallengeProgress getProgress(
            Long runnerId, Long challengeId) {

        ChallengeParticipation participation =
            participationRepository
                .findByRunnerIdAndChallengeId(
                    runnerId, challengeId)
                .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Participation not found"));

        Challenge challenge = participation.getChallenge();

        LocalDateTime start =
            participation.getJoinedAt().isAfter(
                challenge.getStartDate())
            ? participation.getJoinedAt()
            : challenge.getStartDate();

        double distance = resultRepository.getDistanceInPeriod(
            runnerId, start, challenge.getEndDate());

        double target = challenge.getTargetDistance();

        double remaining = Math.max(0, target - distance);

        double percentage = Math.min(
            100, distance / target * 100);

        return new ChallengeProgress(
            challengeId,
            runnerId,
            participation.getRunner().getName(),
            target,
            distance,
            remaining,
            percentage,
            distance >= target
        );
    }

    public List<ChallengeProgress> getLeaderboard(
            Long challengeId) {

        return participationRepository
            .findByChallengeId(challengeId)
            .stream()
            .map(p -> getProgress(
                p.getRunner().getId(), challengeId))
            .sorted((a, b) -> Double.compare(
                b.completedDistance(),
                a.completedDistance()))
            .toList();
    }
}