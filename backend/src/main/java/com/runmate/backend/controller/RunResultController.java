package com.runmate.backend.controller;

import com.runmate.backend.entity.RunResult;
import com.runmate.backend.service.RunResultService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/results")
public class RunResultController {

    private final RunResultService service;

    public RunResultController(RunResultService service) {
        this.service = service;
    }

    public record ResultRequest(
        Long runnerId,
        Double distance,
        Integer durationSeconds
    ) {}

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RunResult createResult(
            @RequestBody ResultRequest request) {

        if (request.runnerId() == null) {
            throw new org.springframework.web.server
                .ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Runner ID is required");
        }

        return service.createResult(
            request.runnerId(),
            request.distance(),
            request.durationSeconds()
        );
    }

    @GetMapping("/runner/{runnerId}")
    public List<RunResult> getRunnerResults(
            @PathVariable Long runnerId) {

        return service.getRunnerResults(runnerId);
    }

    @GetMapping("/ranking/{type}")
    public List<RunResultService.RankingEntry> getRanking(
            @PathVariable String type) {

        return service.getRanking(type);
    }
}