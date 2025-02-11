package com.inversionesaraujo.api.business.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidCodeRequest {
    public String code;
    public Long resetId;    
}
