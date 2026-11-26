package com.runmate.backend.repository;

import com.runmate.backend.entity.Participation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ParticipationRepository
        extends JpaRepository<Participation, Long> {

    boolean existsByRunnerIdAndEventId(
        Long runnerId, Long eventId
    );

    long countByEventId(Long eventId);

    List<Participation> findByEventId(Long eventId);

    List<Participation> findByRunnerId(Long runnerId);

    Optional<Participation> findByRunnerIdAndEventId(
        Long runnerId, Long eventId
    );
}