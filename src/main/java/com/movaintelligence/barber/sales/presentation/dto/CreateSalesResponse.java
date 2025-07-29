package com.movaintelligence.barber.sales.presentation.dto;

public record CreateSalesResponse(Long memberNo,
                                  Boolean createNewMember,
                                  Long treatmentId
) {

}
