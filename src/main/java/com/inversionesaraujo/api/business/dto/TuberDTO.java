package com.inversionesaraujo.api.business.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.inversionesaraujo.api.model.Tuber;

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
public class TuberDTO {
    private Long id;
    private String name;

    public static TuberDTO toDTO(Tuber tuber) {
        return TuberDTO
            .builder()
            .id(tuber.getId())
            .name(tuber.getName())
            .build();
    }

    public static Tuber toEntity(TuberDTO tuber) {
        return Tuber
            .builder()
            .id(tuber.getId())
            .name(tuber.getName())
            .build();   
    }

    public static List<TuberDTO> toListDTO(List<Tuber> tubers) {
        return tubers
            .stream()
            .map(TuberDTO::toDTO)
            .collect(Collectors.toList());
    }
}
