package com.movaintelligence.barber.dto;

import java.time.LocalDateTime;

public class OrderRequest {
    private Long customerId;
    private Long treatmentId;
    private boolean isRedeem;
    private LocalDateTime orderDate;

    // Getters and setters
    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }
    public Long getTreatmentId() { return treatmentId; }
    public void setTreatmentId(Long treatmentId) { this.treatmentId = treatmentId; }
    public boolean isRedeem() { return isRedeem; }
    public void setRedeem(boolean redeem) { isRedeem = redeem; }
    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }
}

