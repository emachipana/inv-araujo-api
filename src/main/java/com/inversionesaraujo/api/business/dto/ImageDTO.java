package com.inversionesaraujo.api.business.dto;

import com.inversionesaraujo.api.model.Image;
import com.inversionesaraujo.api.model.ImageType;

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
public class ImageDTO {
    private Long id;
    private String url;
    private ImageType type;
    private String firebaseId;

    public static ImageDTO toDTO(Image image) {
        if(image == null) return null;

        return ImageDTO
            .builder()
            .id(image.getId())
            .url(image.getUrl())
            .type(image.getType())
            .firebaseId(image.getFirebaseId())
            .build();
    }

    public static Image toEntity(ImageDTO image) {
        if(image == null) return null;

        return Image
            .builder()
            .id(image.getId())
            .url(image.getUrl())
            .type(image.getType())
            .firebaseId(image.getFirebaseId())
            .build();
    }
}
