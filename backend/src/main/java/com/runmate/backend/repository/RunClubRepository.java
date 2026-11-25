package com.runmate.backend.repository;

import com.runmate.backend.entity.RunClub;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RunClubRepository extends JpaRepository<RunClub, Long> {

    List<RunClub> findByCityIgnoreCase(String city);

    List<RunClub> findByOwnerId(Long ownerId);
}