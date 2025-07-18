package com.movaintelligence.barber.repositories;

import com.movaintelligence.barber.models.Treatment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TreatmentRepository extends JpaRepository<Treatment, Long> {
    // Additional query methods if needed
}

