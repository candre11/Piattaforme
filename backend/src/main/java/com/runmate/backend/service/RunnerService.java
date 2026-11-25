package com.runmate.backend.service;

import com.runmate.backend.entity.Runner;
import com.runmate.backend.repository.RunnerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RunnerService {

    private final RunnerRepository runnerRepository;

    public RunnerService(RunnerRepository runnerRepository) {
        this.runnerRepository = runnerRepository;
    }

    public List<Runner> getAllRunners() {
        return runnerRepository.findAll();
    }

    public Runner createRunner(Runner runner) {
        if (runnerRepository.existsByEmail(runner.getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }

        return runnerRepository.save(runner);
    }

    public List<Runner> getRunnersByCity(String city) {
        return runnerRepository.findByCityIgnoreCase(city);
    }
}