package com.inversionesaraujo.api.model.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ValidCodeResponse {
    Boolean isValid;
    Integer userId;    
}
