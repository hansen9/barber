package com.movaintelligence.barber.service;

import com.movaintelligence.barber.dto.OrderRequest;
import com.movaintelligence.barber.dto.OrderResponse;
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
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private SaleRepository saleRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private TreatmentRepository treatmentRepository;

    @Transactional
    public OrderResponse createOrder(OrderRequest request) {
        Long customerId = request.getCustomerId();
        Long treatmentId = request.getTreatmentId();
        boolean redeem = request.isRedeem();
        LocalDateTime orderDate = request.getOrderDate() != null ? request.getOrderDate() : LocalDateTime.now();

        Customer customer = customerRepository.findById(customerId).orElseThrow();
        Treatment treatment = treatmentRepository.findById(treatmentId).orElseThrow();
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

        OrderResponse response = toOrderResponse(order);

        return response;
    }

    public java.util.List<Order> listOrders() {
        return orderRepository.findAll();
    }

    public java.util.List<Order> listOrdersByCustomer(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow();
    }

    public com.movaintelligence.barber.dto.OrderResponse toOrderResponse(Order order) {
        com.movaintelligence.barber.dto.OrderResponse response = new com.movaintelligence.barber.dto.OrderResponse();
        response.setOrderId(order.getId());
        response.setCustomerId(order.getCustomer().getId());
        response.setCustomerName(order.getCustomer().getName());
        response.setTreatmentId(order.getTreatment().getId());
        response.setTreatmentName(order.getTreatment().getName());
        response.setOrderDate(order.getOrderDate());
        response.setRedeemed(order.isRedeemed());
        response.setBirthdayDiscount(order.isBirthdayDiscount());
        // Find sale amount for this order
        Sale sale = saleRepository.findByOrder(order);
        response.setPrice(sale != null ? sale.getAmount() : order.getTreatment().getPrice());
        return response;
    }
}
