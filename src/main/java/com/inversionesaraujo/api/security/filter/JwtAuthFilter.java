package com.inversionesaraujo.api.security.filter;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inversionesaraujo.api.business.dto.payload.MessageResponse;
import com.inversionesaraujo.api.business.service.impl.JwtImpl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtImpl jwtService; 
    private final UserDetailsService userDetailsService;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String token = getFromRequest(request);
        final String username;
        
        try {
            if(token == null) {
                filterChain.doFilter(request, response);
                return;
            }
    
            username = jwtService.getUsernameFromToken(token);
    
            if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    
                if(jwtService.isTokenValid(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());
    
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
    
            filterChain.doFilter(request, response);
        }catch(ExpiredJwtException error) {
            MessageResponse meResponse = MessageResponse
                .builder()
                .message("Token expirado, por favor vuelve a iniciar sesion")
                .build();
            ObjectMapper mapper = new ObjectMapper();
            String jsonResponse = mapper.writeValueAsString(meResponse);

            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(jsonResponse);
        }catch(JwtException error) {
            MessageResponse meResponse = MessageResponse
                .builder()
                .message("Token inválido")
                .build();
            ObjectMapper mapper = new ObjectMapper();
            String jsonResponse = mapper.writeValueAsString(meResponse);

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write(jsonResponse);
        }
    }
    
    private String getFromRequest(HttpServletRequest request) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) return authHeader.substring(7);
        
        return null;
    }
}
