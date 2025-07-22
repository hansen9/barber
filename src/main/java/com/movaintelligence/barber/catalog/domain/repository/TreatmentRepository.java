package com.movaintelligence.barber.catalog.domain.repository;

import com.movaintelligence.barber.catalog.domain.entity.Treatment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TreatmentRepository extends JpaRepository<Treatment, Long> {
    // Additional query methods if needed
}

