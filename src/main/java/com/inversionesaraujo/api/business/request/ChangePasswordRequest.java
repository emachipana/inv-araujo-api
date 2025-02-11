package com.inversionesaraujo.api.business.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordRequest {
    Long resetId;
    String code;
    String newPassword;    
}
