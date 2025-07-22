package com.movaintelligence.barber.catalog.application;

import com.movaintelligence.barber.catalog.domain.entity.Treatment;
import com.movaintelligence.barber.catalog.domain.repository.TreatmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Alexander Hansen, Irfin Afifudin
 */
@Service
public class TreatmentService {

    private final TreatmentRepository repository;

    public TreatmentService(TreatmentRepository tr) {
        repository = tr;
    }

    public List<Treatment> listTreatments() {
        return repository.findAll();
    }
}
