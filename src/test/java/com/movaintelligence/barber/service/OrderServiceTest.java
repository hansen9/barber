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
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class OrderServiceTest {
    @MockBean
    private OrderRepository orderRepository;
    @MockBean
    private SaleRepository saleRepository;
    @MockBean
    private CustomerRepository customerRepository;
    @MockBean
    private TreatmentRepository treatmentRepository;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateOrderWithBirthdayDiscount() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setBirthday(LocalDate.now());
        customer.setPoint(5);
        Treatment treatment = new Treatment();
        treatment.setId(1L);
        treatment.setPrice(BigDecimal.valueOf(100));
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(treatmentRepository.findById(1L)).thenReturn(Optional.of(treatment));
        Order order = orderService.createOrder(1L, 1L, false, LocalDateTime.now());
        assertTrue(order.isBirthdayDiscount());
        assertFalse(order.isRedeemed());
        verify(orderRepository).save(any(Order.class));
        verify(saleRepository).save(any(Sale.class));
        verify(customerRepository).save(any(Customer.class));
    }

    @Test
    void testCreateOrderWithRedemption() {
        Customer customer = new Customer();
        customer.setId(2L);
        customer.setBirthday(LocalDate.now());
        customer.setPoint(10);
        Treatment treatment = new Treatment();
        treatment.setId(2L);
        treatment.setPrice(BigDecimal.valueOf(100));
        when(customerRepository.findById(2L)).thenReturn(Optional.of(customer));
        when(treatmentRepository.findById(2L)).thenReturn(Optional.of(treatment));
        Order order = orderService.createOrder(2L, 2L, true, LocalDateTime.now());
        assertFalse(order.isBirthdayDiscount());
        assertTrue(order.isRedeemed());
        verify(orderRepository).save(any(Order.class));
        verify(saleRepository).save(any(Sale.class));
        verify(customerRepository).save(any(Customer.class));
    }
}

