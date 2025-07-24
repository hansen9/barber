package com.movaintelligence.barber.sales.presentation;

import com.movaintelligence.barber.catalog.domain.entity.Treatment;
import com.movaintelligence.barber.crm.domain.entity.Customer;
import com.movaintelligence.barber.sales.domain.entity.Sale;
import com.movaintelligence.barber.sales.presentation.dto.OrderRequest;
import com.movaintelligence.barber.sales.presentation.dto.OrderResponse;
import com.movaintelligence.barber.sales.domain.entity.Order;
import com.movaintelligence.barber.sales.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
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

    @GetMapping("/orders")
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
