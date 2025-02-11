package com.inversionesaraujo.api.business.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.inversionesaraujo.api.model.Tuber;
import com.inversionesaraujo.api.model.Variety;

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
public class VarietyDTO {
    private Long id;
    private String name;
    private Double price;
    private Double minPrice;
    private Long tuberId;

    public static VarietyDTO toDTO(Variety variety) {
        return VarietyDTO
            .builder()
            .id(variety.getId())
            .name(variety.getName())
            .price(variety.getPrice())
            .minPrice(variety.getMinPrice())
            .tuberId(variety.getTuber().getId())
            .build();
    }

    public static Variety toEntity(VarietyDTO variety) {
        Tuber tuber = new Tuber();
        tuber.setId(variety.getTuberId());

        return Variety
            .builder()
            .id(variety.getId())
            .name(variety.getName())
            .price(variety.getPrice())
            .minPrice(variety.getMinPrice())
            .tuber(tuber)
            .build();
    }

    public static List<VarietyDTO> toDTOList(List<Variety> varieties) {
        return varieties
            .stream()
            .map(VarietyDTO::toDTO)
            .collect(Collectors.toList());
    }
}
