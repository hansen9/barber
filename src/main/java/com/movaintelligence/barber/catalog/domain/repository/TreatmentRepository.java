package com.movaintelligence.barber.catalog.domain.repository;

import com.movaintelligence.barber.catalog.domain.entity.Treatment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TreatmentRepository extends JpaRepository<Treatment, Long> {
    // Additional query methods if needed
    Optional<Treatment> findByName(String name);
}

