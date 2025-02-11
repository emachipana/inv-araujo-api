package com.inversionesaraujo.api.business.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private Long clientId;
    private String password;
}
