package com.movaintelligence.barber.service;

import com.movaintelligence.barber.catalog.domain.entity.Treatment;
import com.movaintelligence.barber.catalog.domain.repository.TreatmentRepository;
import com.movaintelligence.barber.crm.domain.entity.Customer;
import com.movaintelligence.barber.crm.domain.repository.CustomerRepository;
import com.movaintelligence.barber.sales.domain.entity.Order;
import com.movaintelligence.barber.sales.domain.repository.OrderRepository;
import com.movaintelligence.barber.sales.domain.entity.Sale;
import com.movaintelligence.barber.sales.domain.repository.SaleRepository;
import com.movaintelligence.barber.sales.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class OrderServiceTest {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private SaleRepository saleRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private TreatmentRepository treatmentRepository;
    @Autowired
    private OrderService orderService;

    private Treatment defaultTreatment;

    @BeforeEach
    void setUp() {
        // Clean up test data before each test
        saleRepository.deleteAll();
        orderRepository.deleteAll();
        customerRepository.deleteAll();
        treatmentRepository.deleteAll();
        // Insert a default treatment for use in tests
        Treatment treatment = new Treatment();
        treatment.setName("Default Treatment");
        treatment.setPrice(BigDecimal.valueOf(100));
        defaultTreatment = treatmentRepository.save(treatment);
    }

    @Test
    void testCreateOrderWithBirthdayDiscount() {
        Customer customer = new Customer();
        customer.setName("Test User");
        customer.setBirthday(LocalDate.now());
        customer.setPoint(5);
        customer = customerRepository.save(customer);
        // Use default treatment from setUp
        Treatment treatment = defaultTreatment;
        Order order = orderService.createOrder(customer.getId(), treatment.getId(), false, true, LocalDateTime.now());
        assertTrue(order.isBirthdayDiscount());
        assertFalse(order.isRedeemed());
        assertEquals(customer.getId(), order.getCustomer().getId());
        assertEquals(treatment.getId(), order.getTreatment().getId());
        // Check that order and sale are persisted
        assertTrue(orderRepository.findById(order.getId()).isPresent());
        assertFalse(saleRepository.findAll().isEmpty());
        // Check customer points updated
        Customer updatedCustomer = customerRepository.findById(customer.getId()).orElseThrow();
        assertEquals(5, updatedCustomer.getPoint());
    }

    @Test
    void testCreateOrderWithRedemption() {
        Customer customer = new Customer();
        customer.setName("Redeem User");
        customer.setBirthday(LocalDate.now());
        customer.setPoint(10);
        customer = customerRepository.save(customer);
        // Use default treatment from setUp
        Treatment treatment = defaultTreatment;
        Order order = orderService.createOrder(customer.getId(), treatment.getId(), true, false, LocalDateTime.now());
        assertFalse(order.isBirthdayDiscount());
        assertTrue(order.isRedeemed());
        assertEquals(customer.getId(), order.getCustomer().getId());
        assertEquals(treatment.getId(), order.getTreatment().getId());
        // Check that order and sale are persisted
        assertTrue(orderRepository.findById(order.getId()).isPresent());
        assertFalse(saleRepository.findAll().isEmpty());
        // Check customer points updated
        Customer updatedCustomer = customerRepository.findById(customer.getId()).orElseThrow();
        assertEquals(0, updatedCustomer.getPoint());
    }
}
