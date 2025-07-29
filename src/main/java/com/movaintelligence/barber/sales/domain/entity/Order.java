package com.movaintelligence.barber.sales.domain.entity;

import com.movaintelligence.barber.catalog.domain.entity.Treatment;
import com.movaintelligence.barber.crm.domain.entity.Customer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Optional;

@Entity
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

    public void setCustomer(Optional<Customer> customer) {
        this.customer = customer.orElse(null);
    }

    public void setTreatment(Optional<Treatment> treatment) {
        this.treatment = treatment.orElse(null);
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Treatment getTreatment() {
        return treatment;
    }

    public boolean isRedeemed() {
        return isRedeemed;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public boolean isBirthdayDiscount() {
        return isBirthdayDiscount;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setTreatment(Treatment treatment) {
        this.treatment = treatment;
    }

    public void setRedeemed(boolean redeemed) {
        isRedeemed = redeemed;
    }

    public void setBirthdayDiscount(boolean birthdayDiscount) {
        isBirthdayDiscount = birthdayDiscount;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }
}
