package com.inversionesaraujo.api.business.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendCodeRequest {
    String email;
    String origin;
}
