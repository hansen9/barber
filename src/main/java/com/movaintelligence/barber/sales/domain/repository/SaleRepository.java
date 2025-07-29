package com.movaintelligence.barber.sales.domain.repository;

import com.movaintelligence.barber.sales.domain.entity.Order;
import com.movaintelligence.barber.sales.domain.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    Sale findByOrder(Order order);
    // Custom query methods if needed
}
