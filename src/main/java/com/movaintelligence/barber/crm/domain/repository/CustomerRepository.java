package com.movaintelligence.barber.crm.domain.repository;

import com.movaintelligence.barber.crm.domain.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // Additional query methods if needed
}

