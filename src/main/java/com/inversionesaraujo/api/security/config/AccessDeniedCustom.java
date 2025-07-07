package com.inversionesaraujo.api.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inversionesaraujo.api.business.payload.MessageResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AccessDeniedCustom implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {

        MessageResponse messageResponse = MessageResponse.builder()
            .message("Acceso denegado, no tienes los permisos necesarios para realizar esta accion.")
            .build();

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write(new ObjectMapper().writeValueAsString(messageResponse));
    }
}
