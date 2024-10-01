package com.inversionesaraujo.api.model.request;

import java.time.LocalDate;

import com.google.auto.value.AutoValue.Builder;
import com.inversionesaraujo.api.model.entity.DocumentType;
import com.inversionesaraujo.api.model.entity.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VitroOrderRequest {
    private DocumentType docType;
    private String document;
    private String firstName;
    private String lastName;
    private String department;
    private String city;
    private LocalDate initDate;
    private LocalDate finishDate;
    private String phone;
    private Status status;
    private Integer invoiceId;
}
