package com.runmate.backend.service;

import com.runmate.backend.entity.*;
import com.runmate.backend.repository.*;

import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

@Service
public class ClubMembershipService {

    private final ClubMembershipRepository membershipRepository;
    private final RunnerRepository runnerRepository;
    private final RunClubRepository clubRepository;

    public ClubMembershipService(
            ClubMembershipRepository membershipRepository,
            RunnerRepository runnerRepository,
            RunClubRepository clubRepository) {

        this.membershipRepository = membershipRepository;
        this.runnerRepository = runnerRepository;
        this.clubRepository = clubRepository;
    }

    public ClubMembership joinClub(Long runnerId, Long clubId) {

        Runner runner = runnerRepository.findById(runnerId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Runner not found"));

        RunClub club = clubRepository.findById(clubId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Club not found"));

        if (membershipRepository.existsByRunnerIdAndClubId(
                runnerId, clubId)) {

            throw new ResponseStatusException(
                HttpStatus.CONFLICT, "Already a member");
        }

        ClubMembership membership =
            new ClubMembership(runner, club);

        try {
            return membershipRepository.saveAndFlush(membership);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT, "Membership conflict");
        }
    }

    public void leaveClub(Long runnerId, Long clubId) {

        ClubMembership membership = membershipRepository
            .findByRunnerIdAndClubId(runnerId, clubId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Membership not found"));

        membershipRepository.delete(membership);
    }

    public List<ClubMembership> getClubMembers(Long clubId) {
        return membershipRepository.findByClubId(clubId);
    }

    public List<ClubMembership> getRunnerMemberships(Long runnerId) {
        return membershipRepository.findByRunnerId(runnerId);
    }
}