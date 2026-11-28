package com.runmate.backend.controller;

import com.runmate.backend.entity.Challenge;
import com.runmate.backend.service.ChallengeService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/challenges")
public class ChallengeController {

    private final ChallengeService service;

    public ChallengeController(ChallengeService service) {
        this.service = service;
    }

    @GetMapping
    public List<Challenge> getAllChallenges() {
        return service.getAllChallenges();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Challenge createChallenge(
            @RequestBody Challenge challenge,
            @RequestParam Long clubId) {

        return service.createChallenge(challenge, clubId);
    }

    @GetMapping("/active")
    public List<Challenge> getActiveChallenges() {
        return service.getActiveChallenges();
    }

    @GetMapping("/club/{clubId}")
    public List<Challenge> getChallengesByClub(
            @PathVariable Long clubId) {

        return service.getChallengesByClub(clubId);
    }
}