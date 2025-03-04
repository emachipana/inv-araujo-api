package com.inversionesaraujo.api.business.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.inversionesaraujo.api.model.Offer;
import com.inversionesaraujo.api.model.OfferProduct;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OfferProductDTO {
    private Long id;
    private Long offerId;
    private ProductDTO product;

    public static OfferProductDTO toDTO(OfferProduct item) {
        if(item == null) return null;

        return OfferProductDTO
            .builder()
            .id(item.getId())
            .offerId(item.getOffer() != null ? item.getOffer().getId() : null)
            .product(ProductDTO.toDTO(item.getProduct()))
            .build();
    }

    public static OfferProduct toEntity(OfferProductDTO item) {
        if(item == null) return null;
        Offer offer = new Offer();
        offer.setId(item.getOfferId());

        return OfferProduct
            .builder()
            .id(item.getId())
            .offer(offer)
            .product(ProductDTO.toEntity(item.getProduct()))
            .build();
    }

    public static List<OfferProductDTO> toDTOList(List<OfferProduct> items) {
        return items
            .stream()
            .map(OfferProductDTO::toDTO)
            .collect(Collectors.toList());
    }
}
