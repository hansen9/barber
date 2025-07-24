package com.movaintelligence.barber.sales.service;

import com.movaintelligence.barber.catalog.domain.repository.TreatmentRepository;
import com.movaintelligence.barber.crm.domain.entity.Customer;
import com.movaintelligence.barber.crm.domain.repository.CustomerRepository;
import com.movaintelligence.barber.sales.presentation.dto.OrderRequest;
import com.movaintelligence.barber.sales.presentation.dto.OrderResponse;
import com.movaintelligence.barber.sales.domain.repository.OrderRepository;
import com.movaintelligence.barber.sales.domain.entity.Sale;
import com.movaintelligence.barber.sales.domain.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.movaintelligence.barber.sales.domain.entity.Order;
import com.movaintelligence.barber.catalog.domain.entity.Treatment;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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

    public List<Order> listOrders() {
        return orderRepository.findAll();
    }

    public List<Order> listOrdersByCustomer(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow();
    }

    public OrderResponse toOrderResponse(Order order) {
        OrderResponse response = new OrderResponse();
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

    public Order createOrder(long custId, long trtmId, boolean isRedeem, LocalDateTime now) {
        OrderRequest request = new OrderRequest();
        request.setCustomerId(custId);
        request.setTreatmentId(trtmId);
        request.setRedeem(isRedeem);
        request.setOrderDate(now);
        return createOrder(request).toOrder();
    }

    public Order save(Order order) {
        return orderRepository.save(order);
    }

    public Order update(Long id, Order updated) {
        Order order = orderRepository.findById(id).orElseThrow();
        order.setCustomer(updated.getCustomer());
        order.setTreatment(updated.getTreatment());
        order.setOrderDate(updated.getOrderDate());
        order.setBirthdayDiscount(updated.isBirthdayDiscount());
        order.setRedeemed(updated.isRedeemed());
        return orderRepository.save(order);
    }

    public void delete(Long id) {
        orderRepository.deleteById(id);
    }

    public Order findById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Sale getSale(Order order) { return saleRepository.findByOrder(order); }

    public List<Order> findByCustomerId(Long customerId) { return orderRepository.findByCustomerId(customerId);}
}
