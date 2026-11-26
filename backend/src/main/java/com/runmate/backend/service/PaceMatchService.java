package com.runmate.backend.service;

import com.runmate.backend.entity.Runner;
import com.runmate.backend.repository.RunnerRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Comparator;

@Service
public class PaceMatchService {

    private final RunnerRepository repository;

    public PaceMatchService(RunnerRepository repository) {
        this.repository = repository;
    }

    public record Match(
        Long id,
        String name,
        String city,
        Integer averagePace,
        double score
    ) {}

    public List<Match> findMatches(Long runnerId) {

        Runner runner = repository.findById(runnerId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Runner not found"));

        return repository.findAll().stream()
            .filter(other -> !other.getId().equals(runnerId))
            .map(other -> new Match(
                other.getId(),
                other.getName(),
                other.getCity(),
                other.getAveragePace(),
                calculateScore(runner, other)))
            .filter(match -> match.score() >= 50)
            .sorted(Comparator.comparingDouble(Match::score)
                .reversed())
            .toList();
    }

    private double calculateScore(Runner a, Runner b) {

        double score = 0;

        if (a.getAveragePace() != null &&
            b.getAveragePace() != null) {

            double difference = Math.abs(
                (double) a.getAveragePace() -
                b.getAveragePace());

            score += 50 * Math.max(0, 1 - difference / 120);
        }

        if (a.getPreferredDistance() != null &&
            b.getPreferredDistance() != null) {

            double difference = Math.abs(
                a.getPreferredDistance() -
                b.getPreferredDistance());

            score += 20 * Math.max(0, 1 - difference / 10);
        }

        if (a.getCity() != null &&
            a.getCity().equalsIgnoreCase(b.getCity())) {
            score += 20;
        }

        if (a.getLevel() != null &&
            a.getLevel().equalsIgnoreCase(b.getLevel())) {
            score += 10;
        }

        return Math.round(score * 100.0) / 100.0;
    }
}