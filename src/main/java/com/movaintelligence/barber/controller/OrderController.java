package com.movaintelligence.barber.controller;

import com.movaintelligence.barber.dto.OrderRequest;
import com.movaintelligence.barber.dto.OrderResponse;
import com.movaintelligence.barber.models.Order;
import com.movaintelligence.barber.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest request) {
        OrderResponse response = orderService.createOrder(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> listOrders() {
        List<Order> orders = orderService.listOrders();
        List<OrderResponse> responses = orders.stream()
            .map(orderService::toOrderResponse)
            .toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<OrderResponse>> listOrdersByCustomer(@PathVariable Long customerId) {
        List<Order> orders = orderService.listOrdersByCustomer(customerId);
        List<OrderResponse> responses = orders.stream()
            .map(orderService::toOrderResponse)
            .toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable Long orderId) {
        Order order = orderService.getOrder(orderId);
        OrderResponse response = orderService.toOrderResponse(order);
        return ResponseEntity.ok(response);
    }
}
