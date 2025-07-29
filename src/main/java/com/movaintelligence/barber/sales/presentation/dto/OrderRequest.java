package com.movaintelligence.barber.sales.presentation.dto;


import java.time.LocalDateTime;

public class OrderRequest {
    private Long customerId;
    private Long treatmentId;
    private boolean isRedeem;
    private boolean isBirthdayDiscount;
    private LocalDateTime orderDate;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getTreatmentId() {
        return treatmentId;
    }

    public void setTreatmentId(Long treatmentId) {
        this.treatmentId = treatmentId;
    }

    public boolean isRedeem() {
        return isRedeem;
    }

    public void setRedeem(boolean redeem) {
        isRedeem = redeem;
    }

    public boolean isBirthdayDiscount() {
        return isBirthdayDiscount;
    }

    public void setBirthdayDiscount(boolean birthdayDiscount) {
        isBirthdayDiscount = birthdayDiscount;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }
}


