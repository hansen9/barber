package com.movaintelligence.barber.catalog.presentation;

import com.movaintelligence.barber.catalog.application.TreatmentService;
import com.movaintelligence.barber.catalog.domain.entity.Treatment;
import com.movaintelligence.barber.catalog.domain.repository.TreatmentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/treatments")
public class TreatmentController {

    private final TreatmentRepository treatmentRepository;
    private TreatmentService treatmentService;

    public TreatmentController(TreatmentRepository repo) {
        this.treatmentRepository = repo;
    }

    // Treatment List Page (no customer id)
    @GetMapping("/treatments")
    public String treatmentList(Model model) {
        var listTreatment = treatmentService.listTreatments();
        model.addAttribute("treatments", listTreatment);

        return "treatment_list";
    }
}

