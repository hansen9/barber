package com.movaintelligence.barber.sales.presentation.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class OrderRequest {
    private Long customerId;
    private Long treatmentId;
    private boolean isRedeem;
    private boolean isBirthdayDiscount;
    private LocalDateTime orderDate;

}


