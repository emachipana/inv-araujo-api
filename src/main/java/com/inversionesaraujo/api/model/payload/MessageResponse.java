package com.inversionesaraujo.api.model.payload;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageResponse {
    private String message;
    private Object data;
}
