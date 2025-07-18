package com.movaintelligence.barber.models;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {
    @Test
    void testCustomerFields() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");
        customer.setPhoneNo("1234567890");
        customer.setBirthday(LocalDate.of(1990, 1, 1));
        customer.setPoint(5);
        assertEquals(1L, customer.getId());
        assertEquals("John Doe", customer.getName());
        assertEquals("1234567890", customer.getPhoneNo());
        assertEquals(LocalDate.of(1990, 1, 1), customer.getBirthday());
        assertEquals(5, customer.getPoint());
    }
}

class TreatmentTest {
    @Test
    void testTreatmentFields() {
        Treatment treatment = new Treatment();
        treatment.setId(1L);
        treatment.setName("Haircut");
        treatment.setPrice(BigDecimal.valueOf(100));
        assertEquals(1L, treatment.getId());
        assertEquals("Haircut", treatment.getName());
        assertEquals(BigDecimal.valueOf(100), treatment.getPrice());
    }
}

class OrderTest {
    @Test
    void testOrderFields() {
        Order order = new Order();
        Customer customer = new Customer();
        Treatment treatment = new Treatment();
        order.setId(1L);
        order.setCustomer(customer);
        order.setTreatment(treatment);
        order.setOrderDate(LocalDateTime.now());
        order.setBirthdayDiscount(true);
        order.setRedeemed(false);
        assertEquals(1L, order.getId());
        assertEquals(customer, order.getCustomer());
        assertEquals(treatment, order.getTreatment());
        assertTrue(order.isBirthdayDiscount());
        assertFalse(order.isRedeemed());
    }
}

class SaleTest {
    @Test
    void testSaleFields() {
        Sale sale = new Sale();
        Order order = new Order();
        sale.setId(1L);
        sale.setOrder(order);
        sale.setAmount(BigDecimal.valueOf(100));
        sale.setDate(LocalDateTime.now());
        assertEquals(1L, sale.getId());
        assertEquals(order, sale.getOrder());
        assertEquals(BigDecimal.valueOf(100), sale.getAmount());
    }
}

