package com.movaintelligence.barber.sales.domain.entity;

import com.movaintelligence.barber.catalog.domain.entity.Treatment;
import com.movaintelligence.barber.crm.domain.entity.Customer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id")
    private Treatment treatment;

    private boolean isRedeemed;
    private boolean isBirthdayDiscount;
    private LocalDateTime orderDate;

    // Getters and setters
}
