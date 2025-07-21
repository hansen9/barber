package com.movaintelligence.barber.controller;
import com.movaintelligence.barber.models.Treatment;
import com.movaintelligence.barber.repositories.TreatmentRepository;
import com.movaintelligence.barber.models.Customer;
import com.movaintelligence.barber.repositories.CustomerRepository;
import com.movaintelligence.barber.models.Order;
import com.movaintelligence.barber.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import java.time.LocalDate;
import java.util.List;

@Controller
public class UiController {
    private final TreatmentRepository treatmentRepository;
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public UiController(TreatmentRepository treatmentRepository, CustomerRepository customerRepository, OrderRepository orderRepository) {
        this.treatmentRepository = treatmentRepository;
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
    }

    // Book Order Page
    @GetMapping("/orders/new/{customerId}")
    public String bookOrder(@PathVariable Long customerId, Model model) {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        List<Treatment> treatments = treatmentRepository.findAll();
        boolean isBirthday = false;
        boolean canRedeem = false;
        if (customer != null) {
            LocalDate today = LocalDate.now();
            isBirthday = today.getMonth() == customer.getBirthday().getMonth() && today.getDayOfMonth() == customer.getBirthday().getDayOfMonth();
            canRedeem = customer.getPoint() != null && customer.getPoint() >= 10;
        }
        model.addAttribute("customer", customer);
        model.addAttribute("treatments", treatments);
        model.addAttribute("isBirthday", isBirthday);
        model.addAttribute("canRedeem", canRedeem);
        // discountedPrice can be calculated in the frontend or backend as needed
        return "book_order";
    }

    // Loyalty Points Page
    @GetMapping("/loyalty/{customerId}")
    public String loyalty(@PathVariable Long customerId, Model model) {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        List<Order> orders = orderRepository.findByCustomerId(customerId);
        model.addAttribute("customer", customer);
        model.addAttribute("orders", orders);
        return "loyalty";
    }

    // Redeem Free Haircut Page
    @GetMapping("/redeem/{customerId}")
    public String redeem(@PathVariable Long customerId, Model model, @RequestParam(value = "redeemSuccess", required = false) Boolean redeemSuccess, @RequestParam(value = "redeemError", required = false) Boolean redeemError) {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        boolean canRedeem = customer != null && customer.getPoint() != null && customer.getPoint() >= 10;
        model.addAttribute("customer", customer);
        model.addAttribute("canRedeem", canRedeem);
        model.addAttribute("redeemSuccess", redeemSuccess);
        model.addAttribute("redeemError", redeemError);
        return "redeem";
    }
}

