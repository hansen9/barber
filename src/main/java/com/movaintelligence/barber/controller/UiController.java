package com.movaintelligence.barber.controller;
import com.movaintelligence.barber.catalog.domain.entity.Treatment;
import com.movaintelligence.barber.catalog.application.TreatmentService;
import com.movaintelligence.barber.crm.domain.entity.Customer;
import com.movaintelligence.barber.crm.application.CustomerService;
import com.movaintelligence.barber.sales.domain.entity.Order;
import com.movaintelligence.barber.sales.domain.repository.OrderRepository;
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
    private final TreatmentService treatmentService;
    private final CustomerService customerService;
    private final OrderRepository orderRepository;

    @Autowired
    public UiController(TreatmentService treatmentService, CustomerService customerService, OrderRepository orderRepository) {
        this.treatmentService = treatmentService;
        this.customerService = customerService;
        this.orderRepository = orderRepository;
    }

    // Index page mapping
    @GetMapping("/")
    public String index() {
        return "index";
    }

    // Home page mapping (handles form submission from index)
    @GetMapping("/home")
    public String home(@RequestParam("customerId") Long customerId, Model model) {
        Customer customer = customerService.findById(customerId).orElse(null);
        model.addAttribute("customer", customer);
        return "home";
    }

    // Book Order Page
    @GetMapping("/orders/new/{customerId}")
    public String bookOrder(@PathVariable Long customerId, Model model) {
        Customer customer = customerService.findById(customerId).orElse(null);
        List<Treatment> treatments = treatmentService.listTreatments();
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
        Customer customer = customerService.findById(customerId).orElse(null);
        List<Order> orders = orderRepository.findByCustomerId(customerId);
        model.addAttribute("customer", customer);
        model.addAttribute("orders", orders);
        return "loyalty";
    }

    // Redeem Free Haircut Page
    @GetMapping("/redeem/{customerId}")
    public String redeem(@PathVariable Long customerId, Model model, @RequestParam(value = "redeemSuccess", required = false) Boolean redeemSuccess, @RequestParam(value = "redeemError", required = false) Boolean redeemError) {
        Customer customer = customerService.findById(customerId).orElse(null);
        boolean canRedeem = customer != null && customer.getPoint() != null && customer.getPoint() >= 10;
        model.addAttribute("customer", customer);
        model.addAttribute("canRedeem", canRedeem);
        model.addAttribute("redeemSuccess", redeemSuccess);
        model.addAttribute("redeemError", redeemError);
        return "redeem";
    }


}
