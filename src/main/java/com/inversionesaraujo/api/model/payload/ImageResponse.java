package com.inversionesaraujo.api.model.payload;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImageResponse {
    private String imageUrl;
    private String fileName;    
}
