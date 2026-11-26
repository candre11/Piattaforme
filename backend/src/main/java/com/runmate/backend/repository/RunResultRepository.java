package com.runmate.backend.repository;

import com.runmate.backend.entity.RunResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RunResultRepository
        extends JpaRepository<RunResult, Long> {

    List<RunResult> findByRunnerIdOrderByCompletedAtDesc(
        Long runnerId
    );

    @Query("""
        SELECT r.runner.id,
               r.runner.name,
               SUM(r.distance),
               COUNT(r),
               SUM(r.durationSeconds)
        FROM RunResult r
        GROUP BY r.runner.id, r.runner.name
        """)
    List<Object[]> getRunnerStatistics();
}