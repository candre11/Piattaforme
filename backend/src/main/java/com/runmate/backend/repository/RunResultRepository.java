package com.runmate.backend.repository;

import com.runmate.backend.entity.RunResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.time.LocalDateTime;

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

    @Query("""
        SELECT COALESCE(SUM(r.distance), 0.0)
        FROM RunResult r
        WHERE r.runner.id = :runnerId
        AND r.completedAt >= :start
        AND r.completedAt <= :end
        """)
    double getDistanceInPeriod(
        @Param("runnerId") Long runnerId,
        @Param("start") LocalDateTime start,
        @Param("end") LocalDateTime end
    );
}