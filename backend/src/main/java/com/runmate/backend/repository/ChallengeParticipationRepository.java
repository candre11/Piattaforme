package com.runmate.backend.repository;

import com.runmate.backend.entity.ChallengeParticipation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChallengeParticipationRepository
        extends JpaRepository<ChallengeParticipation, Long> {

    boolean existsByRunnerIdAndChallengeId(
        Long runnerId, Long challengeId
    );

    List<ChallengeParticipation> findByRunnerId(
        Long runnerId
    );

    List<ChallengeParticipation> findByChallengeId(
        Long challengeId
    );

    Optional<ChallengeParticipation>
        findByRunnerIdAndChallengeId(
            Long runnerId, Long challengeId
        );
}