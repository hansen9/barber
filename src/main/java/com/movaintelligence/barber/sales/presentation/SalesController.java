package com.movaintelligence.barber.sales.presentation;

import com.movaintelligence.barber.sales.presentation.dto.CreateSalesRequest;
import com.movaintelligence.barber.sales.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sales")
public class SalesController {

    private final OrderService orderService;

    public SalesController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Receive PostMapping that contains CreateSalesRequest
    @PostMapping("/create")
    public String createSales(CreateSalesRequest request) {
        try {
            // Berikan data yg dibutuhkan oleh service utk membuat transaksi penjualan.
            var createdOrder = orderService.createAsExistingMember(request.memberNo(), request.treatmentId());
            return "sales/created";
        }
        catch (Exception e) {
            return "sales/error";
        }
    }
}
