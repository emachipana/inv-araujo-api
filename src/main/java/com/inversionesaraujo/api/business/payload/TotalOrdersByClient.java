package com.inversionesaraujo.api.business.payload;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TotalOrdersByClient {
    private Long orders;
    private Long vitroOrders;  
}
