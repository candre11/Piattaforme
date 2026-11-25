package com.runmate.backend.controller;

import com.runmate.backend.entity.RunClub;
import com.runmate.backend.service.RunClubService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clubs")
public class RunClubController {

    private final RunClubService runClubService;

    public RunClubController(RunClubService runClubService) {
        this.runClubService = runClubService;
    }

    @GetMapping
    public List<RunClub> getAllClubs() {
        return runClubService.getAllClubs();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RunClub createClub(
            @RequestBody RunClub club,
            @RequestParam Long ownerId) {

        return runClubService.createClub(club, ownerId);
    }

    @GetMapping("/city/{city}")
    public List<RunClub> getClubsByCity(
            @PathVariable String city) {

        return runClubService.getClubsByCity(city);
    }

    @GetMapping("/owner/{ownerId}")
    public List<RunClub> getClubsByOwner(
            @PathVariable Long ownerId) {

        return runClubService.getClubsByOwner(ownerId);
    }
}