package com.inversionesaraujo.api.business.request;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PickupInfoRequest {
    private LocalDate date;
    private LocalTime hour;
}
