package com.movaintelligence.barber.repositories;

import com.movaintelligence.barber.models.Order;
import com.movaintelligence.barber.models.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    Sale findByOrder(Order order);
    // Custom query methods if needed
}

