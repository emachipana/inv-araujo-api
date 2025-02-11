package com.inversionesaraujo.api.business.dto;

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
        return OfferProductDTO
            .builder()
            .id(item.getId())
            .offerId(item.getOffer().getId())
            .product(ProductDTO.toDTO(item.getProduct()))
            .build();
    }

    public static OfferProduct toEntity(OfferProductDTO item) {
        Offer offer = new Offer();
        offer.setId(item.getId());

        return OfferProduct
            .builder()
            .id(item.getId())
            .offer(offer)
            .product(ProductDTO.toEntity(item.getProduct()))
            .build();
    }
}
