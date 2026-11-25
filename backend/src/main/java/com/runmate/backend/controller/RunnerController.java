package com.runmate.backend.controller;

import com.runmate.backend.entity.Runner;
import com.runmate.backend.service.RunnerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/runners")
public class RunnerController {

    private final RunnerService runnerService;

    public RunnerController(RunnerService runnerService) {
        this.runnerService = runnerService;
    }

    @GetMapping
    public List<Runner> getAllRunners() {
        return runnerService.getAllRunners();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Runner createRunner(@RequestBody Runner runner) {
        try {
            return runnerService.createRunner(runner);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT, e.getMessage()
            );
        }
    }

    @GetMapping("/city/{city}")
    public List<Runner> getRunnersByCity(@PathVariable String city) {
        return runnerService.getRunnersByCity(city);
    }
}