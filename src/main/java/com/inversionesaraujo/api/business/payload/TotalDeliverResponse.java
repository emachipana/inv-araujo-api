package com.inversionesaraujo.api.business.payload;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class TotalDeliverResponse {
    private Long totalAtWarehouse;
    private Long totalAtAgency;
}
