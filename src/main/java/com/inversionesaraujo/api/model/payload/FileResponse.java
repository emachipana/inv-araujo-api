package com.inversionesaraujo.api.model.payload;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileResponse {
    private String fileUrl;
    private String fileName;    
}
