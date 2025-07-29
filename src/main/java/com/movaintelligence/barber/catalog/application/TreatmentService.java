package com.movaintelligence.barber.catalog.application;

import com.movaintelligence.barber.catalog.domain.entity.Treatment;
import com.movaintelligence.barber.catalog.domain.repository.TreatmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Treatment save(Treatment treatment) {
        return repository.save(treatment);
    }

    public Treatment update(Long id, Treatment updated) {
        Treatment treatment = repository.findById(id).orElseThrow();
        treatment.setName(updated.getName());
        treatment.setPrice(updated.getPrice());
        return repository.save(treatment);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Treatment findById(Long id) {
        return repository.findById(id).orElse(null);
    }
    public Optional<Treatment> findByName(String name) {
        return repository.findByName(name);
    }
}
