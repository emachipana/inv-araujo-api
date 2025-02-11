package com.inversionesaraujo.api.business.payload;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageResponse {
    private String message;
    private Object data;
}
