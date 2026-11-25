package com.runmate.backend.repository;

import com.runmate.backend.entity.ClubMembership;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClubMembershipRepository
        extends JpaRepository<ClubMembership, Long> {

    boolean existsByRunnerIdAndClubId(
        Long runnerId, Long clubId
    );

    List<ClubMembership> findByRunnerId(Long runnerId);

    List<ClubMembership> findByClubId(Long clubId);

    Optional<ClubMembership> findByRunnerIdAndClubId(
        Long runnerId, Long clubId
    );
}