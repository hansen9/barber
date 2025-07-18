package com.movaintelligence.barber.repositories;

import com.movaintelligence.barber.models.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerRepositoryIntegrationTest {
    @Autowired
    private CustomerRepository customerRepository;

    @Test
    @Transactional
    void testSaveAndFindCustomer() {
        Customer customer = new Customer();
        customer.setName("Test User");
        customer.setPhoneNo("1234567890");
        customer.setBirthday(LocalDate.of(2000, 1, 1));
        customer.setPoint(0);
        System.out.println("Saving customer: " + customer);
        Customer saved = customerRepository.save(customer);
        assertNotNull(saved.getId());
        Customer found = customerRepository.findById(saved.getId()).orElse(null);
        assertNotNull(found);
        assertEquals("Test User", found.getName());
    }
}

