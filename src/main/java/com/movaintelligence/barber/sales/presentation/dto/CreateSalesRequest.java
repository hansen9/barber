package com.movaintelligence.barber.sales.presentation.dto;

public record CreateSalesRequest(Long memberNo,
                                 Boolean createNewMember,
                                 Long treatmentId
                                 ) {
    @Override
    public Long memberNo() {
        return memberNo;
    }

    @Override
    public Boolean createNewMember() {
        return createNewMember;
    }

    @Override
    public Long treatmentId() {
        return treatmentId;
    }
}
