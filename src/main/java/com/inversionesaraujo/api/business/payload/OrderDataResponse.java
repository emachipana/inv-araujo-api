package com.inversionesaraujo.api.business.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDataResponse {
    public Integer ship;
    public Integer pen;
}
