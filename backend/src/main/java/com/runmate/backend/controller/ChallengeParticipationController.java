package com.runmate.backend.controller;

import com.runmate.backend.entity.ChallengeParticipation;
import com.runmate.backend.service.ChallengeParticipationService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/challenge-participations")
public class ChallengeParticipationController {

    private final ChallengeParticipationService service;

    public ChallengeParticipationController(
            ChallengeParticipationService service) {

        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ChallengeParticipation joinChallenge(
            @RequestParam Long runnerId,
            @RequestParam Long challengeId) {

        return service.joinChallenge(runnerId, challengeId);
    }

    @GetMapping("/runner/{runnerId}")
    public List<ChallengeParticipation> getRunnerChallenges(
            @PathVariable Long runnerId) {

        return service.getRunnerChallenges(runnerId);
    }

    @GetMapping("/progress")
    public ChallengeParticipationService.ChallengeProgress
            getProgress(
                @RequestParam Long runnerId,
                @RequestParam Long challengeId) {

        return service.getProgress(runnerId, challengeId);
    }

    @GetMapping("/leaderboard/{challengeId}")
    public List<ChallengeParticipationService.ChallengeProgress>
            getLeaderboard(@PathVariable Long challengeId) {

        return service.getLeaderboard(challengeId);
    }
}