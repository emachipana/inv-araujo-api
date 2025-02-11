package com.inversionesaraujo.api.business.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.inversionesaraujo.api.model.Offer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OfferDTO {
    private Long id;
    private String title;
    private String description;
    private String markedWord;
    private Boolean isUsed;

    public static OfferDTO toDTO(Offer offer) {
        return OfferDTO
            .builder()
            .id(offer.getId())
            .title(offer.getTitle())
            .description(offer.getDescription())
            .markedWord(offer.getMarkedWord())
            .isUsed(offer.getIsUsed())
            .build();
    }

    public static Offer toEntity(OfferDTO offer) {
        return Offer
            .builder()
            .id(offer.getId())
            .title(offer.getTitle())
            .description(offer.getDescription())
            .markedWord(offer.getMarkedWord())
            .isUsed(offer.getIsUsed())
            .build();
    }

    public static List<OfferDTO> toListDTO(List<Offer> offers) {
        return offers
            .stream()
            .map(OfferDTO::toDTO)
            .collect(Collectors.toList());
    }
}
