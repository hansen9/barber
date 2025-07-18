package com.movaintelligence.barber.service;

import com.movaintelligence.barber.models.*;
import com.movaintelligence.barber.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.movaintelligence.barber.models.Order;
import com.movaintelligence.barber.models.Treatment;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class OrderTreatment {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private SaleRepository saleRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private TreatmentRepository treatmentRepository;

    @Transactional
    public Order createOrder(Long customerId, Long serviceId, boolean redeem, LocalDateTime orderDate) {
        Customer customer = customerRepository.findById(customerId).orElseThrow();
        Treatment treatment = treatmentRepository.findById(serviceId).orElseThrow();
        boolean isBirthday = customer.getBirthday() != null && customer.getBirthday().equals(orderDate.toLocalDate());
        boolean isBirthdayDiscount = isBirthday && !redeem;
        boolean isRedeemed = redeem && customer.getPoint() >= 10;

        BigDecimal price = treatment.getPrice();
        if (isBirthdayDiscount) {
            price = price.multiply(BigDecimal.valueOf(0.85)); // 15% off
        } else if (isRedeemed) {
            price = BigDecimal.ZERO;
            customer.setPoint(customer.getPoint() - 10);
        } else {
            customer.setPoint(customer.getPoint() + 1);
        }

        Order order = new Order();
        order.setCustomer(customer);
        order.setTreatment(treatment);
        order.setOrderDate(orderDate);
        order.setBirthdayDiscount(isBirthdayDiscount);
        order.setRedeemed(isRedeemed);
        orderRepository.save(order);
        customerRepository.save(customer);

        Sale sale = new Sale();
        sale.setOrder(order);
        sale.setAmount(price);
        sale.setDate(orderDate);
        saleRepository.save(sale);

        return order;
    }
}


