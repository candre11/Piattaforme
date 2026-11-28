package com.runmate.backend.repository;

import com.runmate.backend.entity.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ChallengeRepository
        extends JpaRepository<Challenge, Long> {

    List<Challenge> findByClubId(Long clubId);

    List<Challenge> findByStartDateLessThanEqualAndEndDateGreaterThanEqual(
        LocalDateTime start,
        LocalDateTime end
    );
}