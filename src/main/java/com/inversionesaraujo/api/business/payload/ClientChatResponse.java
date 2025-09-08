package com.inversionesaraujo.api.business.payload;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;

@Getter
@Setter
@Builder
public class ClientChatResponse {
    private String content;
}
