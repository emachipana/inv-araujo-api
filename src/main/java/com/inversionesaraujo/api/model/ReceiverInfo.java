package com.inversionesaraujo.api.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReceiverInfo {
    private String fullName;
    private String document;
    private String phone;
    private String code;
    private String trackingCode;
}
