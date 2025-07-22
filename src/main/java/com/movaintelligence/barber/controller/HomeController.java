package com.movaintelligence.barber.controller;

import com.movaintelligence.barber.crm.domain.entity.Customer;
import com.movaintelligence.barber.crm.application.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {
    private final CustomerService customerService;

    @Autowired
    public HomeController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{customerId}")
    public String home(@PathVariable Long customerId, Model model) {
        Customer customer = customerService.findById(customerId).orElse(null);
        model.addAttribute("customer", customer);
        return "home";
    }
}
