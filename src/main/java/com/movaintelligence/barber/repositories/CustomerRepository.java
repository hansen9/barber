package com.movaintelligence.barber.repositories;

import com.movaintelligence.barber.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // Additional query methods if needed
}

