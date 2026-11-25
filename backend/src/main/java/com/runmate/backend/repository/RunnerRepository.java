package com.runmate.backend.repository;

import com.runmate.backend.entity.Runner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RunnerRepository extends JpaRepository<Runner, Long> {

    Optional<Runner> findByEmail(String email);

    List<Runner> findByCityIgnoreCase(String city);

    boolean existsByEmail(String email);
}