package com.movaintelligence.barber.sales.presentation.dto;

import com.movaintelligence.barber.crm.domain.entity.Customer;
import com.movaintelligence.barber.catalog.domain.entity.Treatment;
import com.movaintelligence.barber.sales.domain.entity.Order;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter
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
}

