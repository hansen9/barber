package com.movaintelligence.barber.controller;
import com.movaintelligence.barber.catalog.domain.entity.Treatment;
import com.movaintelligence.barber.catalog.application.TreatmentService;
import com.movaintelligence.barber.crm.domain.entity.Customer;
import com.movaintelligence.barber.crm.application.CustomerService;
import com.movaintelligence.barber.sales.domain.entity.Order;
import com.movaintelligence.barber.sales.domain.repository.OrderRepository;
import com.movaintelligence.barber.sales.presentation.dto.OrderResponse;
import com.movaintelligence.barber.sales.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Controller
public class UiController {
    private final TreatmentService treatmentService;
    private final CustomerService customerService;
    private final OrderService orderService;

    @Autowired
    public UiController(TreatmentService treatmentService, CustomerService customerService, OrderService orderService) {
        this.treatmentService = treatmentService;
        this.customerService = customerService;
        this.orderService = orderService;
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
        List<Order> orders = orderService.findByCustomerId(customerId);
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

    // Customer/Member List Page
    @GetMapping("/customers")
    public String customerList(Model model) {
        List<Customer> customers = customerService.findAll();
        model.addAttribute("customers", customers);
        return "customer_list";
    }

    // Treatment List Page
    @GetMapping("/treatment_list")
    public String treatmentList(Model model) {
        List<Treatment> treatments = treatmentService.listTreatments();
        model.addAttribute("treatments", treatments);
        return "treatment_list";
    }

    // Show update form for a treatment
    @GetMapping("/treatment/update/{id}")
    public String showUpdateTreatmentForm(@PathVariable Long id, Model model) {
        Treatment treatment = treatmentService.findById(id);
        model.addAttribute("treatment", treatment);
        return "treatment_update";
    }

    // Handle update form submission
    @PostMapping("/treatment/update/{id}")
    public String updateTreatment(@PathVariable Long id, Treatment treatment) {
        treatmentService.update(id, treatment);
        return "redirect:/treatment_list";
    }

    // Handle delete treatment
    @PostMapping("/treatment/delete/{id}")
    public String deleteTreatment(@PathVariable Long id) {
        treatmentService.delete(id);
        return "redirect:/treatment_list";
    }

    // Show add treatment form
    @GetMapping("/treatment/add")
    public String showAddTreatmentForm(Model model) {
        model.addAttribute("treatment", new com.movaintelligence.barber.catalog.domain.entity.Treatment());
        return "treatment_add";
    }

    // Handle add treatment form submission
    @PostMapping("/treatment/add")
    public String addTreatment(com.movaintelligence.barber.catalog.domain.entity.Treatment treatment) {
        treatmentService.save(treatment);
        return "redirect:/treatment_list";
    }

    // Show add customer form
    @GetMapping("/customer/add")
    public String showAddCustomerForm(Model model) {
        model.addAttribute("customer", new com.movaintelligence.barber.crm.domain.entity.Customer());
        return "customer_add";
    }

    // Handle add customer form submission
    @PostMapping("/customer/add")
    public String addCustomer(com.movaintelligence.barber.crm.domain.entity.Customer customer) {
        customerService.save(customer);
        return "redirect:/customers";
    }

    // Show update form for a customer
    @GetMapping("/customer/update/{id}")
    public String showUpdateCustomerForm(@PathVariable Long id, Model model) {
        com.movaintelligence.barber.crm.domain.entity.Customer customer = customerService.findByIdOrNull(id);
        model.addAttribute("customer", customer);
        return "customer_update";
    }

    // Handle update form submission
    @PostMapping("/customer/update/{id}")
    public String updateCustomer(@PathVariable Long id, com.movaintelligence.barber.crm.domain.entity.Customer customer) {
        customerService.update(id, customer);
        return "redirect:/customers";
    }

    // Handle delete customer
    @PostMapping("/customer/delete/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        customerService.delete(id);
        return "redirect:/customers";
    }


    // New Order Flow: Step 1 - Customer Search and Select
    @GetMapping("/order/select-customer")
    public String selectCustomerPage(@RequestParam(value = "name", required = false) String name, Model model) {
        List<Customer> customers = null;
        if (name != null && !name.isEmpty()) {
            customers = customerService.findByNameContainingIgnoreCase(name);
        }
        model.addAttribute("customers", customers);
        return "order_customer_select";
    }

    // New Order Flow: Step 2 - Treatment Selection for Selected Customer
    @GetMapping("/order/select-treatment/{customerId}")
    public String selectTreatmentPage(@PathVariable Long customerId, Model model) {
        Customer customer = customerService.findById(customerId).orElse(null);
        List<Treatment> treatments = treatmentService.listTreatments();
        model.addAttribute("customer", customer);
        model.addAttribute("treatments", treatments);
        return "order_treatment_select";
    }

    // New Order Flow: Step 3 - Confirm Order
    @PostMapping("/orders/confirm/{customerId}")
    public String confirmOrder(@PathVariable Long customerId, 
                              @RequestParam Long treatmentId,
                              Model model) {
        Customer customer = customerService.findById(customerId).orElse(null);
        Treatment treatment = treatmentService.findById(treatmentId);
        
        BigDecimal discountedPrice = null;
        BigDecimal finalPrice = treatment.getPrice();
        
        if (customer != null) {
            LocalDate today = LocalDate.now();
            boolean isBirthday = today.getMonth() == customer.getBirthday().getMonth() 
                              && today.getDayOfMonth() == customer.getBirthday().getDayOfMonth();
            
            if (isBirthday) {
                discountedPrice = treatment.getPrice().multiply(new BigDecimal("0.8"));
                finalPrice = discountedPrice;
            } else if (customer.getPoint() != null && customer.getPoint() >= 10) {
                finalPrice = BigDecimal.ZERO;
            }
        }
        
        model.addAttribute("customer", customer);
        model.addAttribute("treatment", treatment);
        model.addAttribute("discountedPrice", discountedPrice);
        model.addAttribute("finalPrice", finalPrice);
        return "order_confirmation";
    }

    // New Order Flow: Step 4 - Create Order
    @PostMapping("/orders/create")
    public String createOrder(@RequestParam Long customerId, @RequestParam Long treatmentId) {
        // Implementation to actually create the order would go here
        return "redirect:/order_history/" + customerId;
    }

    @GetMapping("/sales_list")
    public String salesList(Model model) {
        List<Order> orders = orderService.listOrders();
        List<OrderResponse> responses = orders.stream()
                .map(orderService::toOrderResponse)
                .toList();
        model.addAttribute("sales", responses);
        return "sales_list";
    }
}