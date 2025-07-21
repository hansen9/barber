package com.movaintelligence.barber.controller;

import com.movaintelligence.barber.models.Customer;
import com.movaintelligence.barber.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {
    private final CustomerRepository customerRepository;

    @Autowired
    public HomeController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping("/{customerId}")
    public String home(@PathVariable Long customerId, Model model) {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        model.addAttribute("customer", customer);
        return "home";
    }
}

