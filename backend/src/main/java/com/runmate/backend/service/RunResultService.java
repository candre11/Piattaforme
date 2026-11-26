package com.runmate.backend.service;

import com.runmate.backend.entity.Runner;
import com.runmate.backend.entity.RunResult;
import com.runmate.backend.repository.RunnerRepository;
import com.runmate.backend.repository.RunResultRepository;

import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Comparator;

@Service
public class RunResultService {

    private final RunResultRepository resultRepository;
    private final RunnerRepository runnerRepository;

    public RunResultService(
            RunResultRepository resultRepository,
            RunnerRepository runnerRepository) {

        this.resultRepository = resultRepository;
        this.runnerRepository = runnerRepository;
    }

    public record RankingEntry(
        Long runnerId,
        String name,
        double totalDistance,
        long totalRuns,
        double averagePace
    ) {}

    public RunResult createResult(
            Long runnerId,
            Double distance,
            Integer durationSeconds) {

        Runner runner = runnerRepository.findById(runnerId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Runner not found"));

        if (distance == null ||
            !Double.isFinite(distance) ||
            distance <= 0) {

            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Invalid distance");
        }

        if (durationSeconds == null ||
            durationSeconds <= 0) {

            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Invalid duration");
        }

        RunResult result = new RunResult(
            runner,
            distance,
            durationSeconds,
            LocalDateTime.now()
        );

        return resultRepository.save(result);
    }

    public List<RunResult> getRunnerResults(Long runnerId) {
        return resultRepository
            .findByRunnerIdOrderByCompletedAtDesc(runnerId);
    }

    public List<RankingEntry> getRanking(String type) {

        Comparator<RankingEntry> comparator = switch (type) {

            case "distance" ->
                Comparator.comparingDouble(
                    RankingEntry::totalDistance).reversed();

            case "activity" ->
                Comparator.comparingLong(
                    RankingEntry::totalRuns).reversed();

            case "pace" ->
                Comparator.comparingDouble(
                    RankingEntry::averagePace);

            default ->
                throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid ranking type");
        };

        return resultRepository.getRunnerStatistics()
            .stream()
            .map(row -> {

                Long id = ((Number) row[0]).longValue();
                String name = (String) row[1];
                double distance =
                    ((Number) row[2]).doubleValue();

                long runs =
                    ((Number) row[3]).longValue();

                long duration =
                    ((Number) row[4]).longValue();

                return new RankingEntry(
                    id,
                    name,
                    distance,
                    runs,
                    duration / distance
                );
            })
            .sorted(comparator)
            .toList();
    }
}