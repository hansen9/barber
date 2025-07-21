package com.movaintelligence.barber.controller;

import com.movaintelligence.barber.models.Order;
import com.movaintelligence.barber.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderViewController {
    private final OrderService orderService;

    @Autowired
    public OrderViewController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/history/{customerId}")
    public String orderHistory(@PathVariable Long customerId, Model model) {
        List<Order> orders = orderService.listOrdersByCustomer(customerId);
        model.addAttribute("orders", orders.stream().map(orderService::toOrderResponse).toList());
        return "order_history";
    }
}