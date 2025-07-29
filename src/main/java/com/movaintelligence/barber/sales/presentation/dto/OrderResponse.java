package com.movaintelligence.barber.sales.presentation.dto;

import com.movaintelligence.barber.crm.domain.entity.Customer;
import com.movaintelligence.barber.catalog.domain.entity.Treatment;
import com.movaintelligence.barber.sales.domain.entity.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderResponse {
    private Long orderId;
    private Long customerId;
    private String customerName;
    private Long treatmentId;
    private String treatmentName;
    private BigDecimal price;
    private BigDecimal amountPaid;
    private LocalDateTime orderDate;
    private boolean isRedeemed;
    private boolean isBirthdayDiscount;

    public Order toOrder() {
        Order order = new Order();
        order.setId(orderId);
        Customer customer = new Customer();
        customer.setId(customerId);
        customer.setName(customerName);
        order.setCustomer(customer);
        Treatment treatment = new Treatment();
        treatment.setId(treatmentId);
        treatment.setName(treatmentName);
        treatment.setPrice(price);
        order.setTreatment(treatment);
        order.setOrderDate(orderDate);
        order.setRedeemed(isRedeemed);
        order.setBirthdayDiscount(isBirthdayDiscount);
        return order;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Long getTreatmentId() {
        return treatmentId;
    }

    public void setTreatmentId(Long treatmentId) {
        this.treatmentId = treatmentId;
    }

    public String getTreatmentName() {
        return treatmentName;
    }

    public void setTreatmentName(String treatmentName) {
        this.treatmentName = treatmentName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public boolean isRedeemed() {
        return isRedeemed;
    }

    public void setRedeemed(boolean redeemed) {
        isRedeemed = redeemed;
    }

    public boolean isBirthdayDiscount() {
        return isBirthdayDiscount;
    }

    public void setBirthdayDiscount(boolean birthdayDiscount) {
        isBirthdayDiscount = birthdayDiscount;
    }
}

