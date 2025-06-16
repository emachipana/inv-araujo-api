package com.inversionesaraujo.api.business.dto;

import com.inversionesaraujo.api.model.Embedding;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmbeddingDTO {
    private Long id;
    private String text;
    private String vector;

    public static EmbeddingDTO toDTO(Embedding embedding) {
        if(embedding == null) return null;

        return EmbeddingDTO
            .builder()
            .id(embedding.getId())
            .text(embedding.getText())
            .vector(embedding.getVector())
            .build();
    }

    public static Embedding toEntity(EmbeddingDTO embedding) {
        if(embedding == null) return null;

        return Embedding
            .builder()
            .id(embedding.getId())
            .text(embedding.getText())
            .vector(embedding.getVector())
            .build();
    }
}
