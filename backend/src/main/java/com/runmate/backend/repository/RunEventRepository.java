package com.runmate.backend.repository;

import com.runmate.backend.entity.RunEvent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.persistence.LockModeType;

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

public interface RunEventRepository
        extends JpaRepository<RunEvent, Long> {

    List<RunEvent> findByClubId(Long clubId);

    List<RunEvent> findByEventDateAfterOrderByEventDateAsc(
        LocalDateTime date
    );

    List<RunEvent> findByClubCityIgnoreCase(String city);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT e FROM RunEvent e WHERE e.id = :id")
    Optional<RunEvent> findByIdForUpdate(
        @Param("id") Long id
    );
}