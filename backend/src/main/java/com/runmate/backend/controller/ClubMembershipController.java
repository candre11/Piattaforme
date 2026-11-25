package com.runmate.backend.controller;

import com.runmate.backend.entity.ClubMembership;
import com.runmate.backend.service.ClubMembershipService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/memberships")
public class ClubMembershipController {

    private final ClubMembershipService membershipService;

    public ClubMembershipController(
            ClubMembershipService membershipService) {

        this.membershipService = membershipService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClubMembership joinClub(
            @RequestParam Long runnerId,
            @RequestParam Long clubId) {

        return membershipService.joinClub(runnerId, clubId);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void leaveClub(
            @RequestParam Long runnerId,
            @RequestParam Long clubId) {

        membershipService.leaveClub(runnerId, clubId);
    }

    @GetMapping("/club/{clubId}")
    public List<ClubMembership> getClubMembers(
            @PathVariable Long clubId) {

        return membershipService.getClubMembers(clubId);
    }

    @GetMapping("/runner/{runnerId}")
    public List<ClubMembership> getRunnerMemberships(
            @PathVariable Long runnerId) {

        return membershipService.getRunnerMemberships(runnerId);
    }
}