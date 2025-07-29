package com.inversionesaraujo.api.business.payload;

import java.time.LocalDate;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AvailableHours {
    private LocalDate date;
    private List<String> hours;
}
