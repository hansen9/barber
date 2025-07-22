package com.movaintelligence.barber.sales.domain.repository;

import com.movaintelligence.barber.sales.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerId(Long customerId);
    // Custom query methods if needed
}

