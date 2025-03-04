package com.inversionesaraujo.api.business.dto;

import java.util.ArrayList;
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
    private List<OfferProductDTO> items;

    public static OfferDTO toDTO(Offer offer) {
        return OfferDTO
            .builder()
            .id(offer.getId())
            .title(offer.getTitle())
            .description(offer.getDescription())
            .markedWord(offer.getMarkedWord())
            .isUsed(offer.getIsUsed() != null ? offer.getIsUsed() : false)
            .items(offer.getItems() != null ? OfferProductDTO.toDTOList(offer.getItems()) : new ArrayList<OfferProductDTO>())
            .build();
    }

    public static Offer toEntity(OfferDTO offer) {
        return Offer
            .builder()
            .id(offer.getId())
            .title(offer.getTitle())
            .description(offer.getDescription())
            .markedWord(offer.getMarkedWord())
            .isUsed(offer.getIsUsed() != null ? offer.getIsUsed() : false)
            .build();
    }

    public static List<OfferDTO> toListDTO(List<Offer> offers) {
        return offers
            .stream()
            .map(OfferDTO::toDTO)
            .collect(Collectors.toList());
    }
}
