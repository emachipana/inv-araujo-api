package com.inversionesaraujo.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.inversionesaraujo.api.model.filter.JwtAuthFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthenticationProvider authProvider;
    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authRequest ->
                authRequest
                    // messages
                    .requestMatchers(HttpMethod.GET, "/api/v1/messages/**").hasRole("ADMINISTRADOR")
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/messages/**").hasRole("ADMINISTRADOR")
                    .requestMatchers(HttpMethod.POST, "/api/v1/messages/**").permitAll()
                    // tubers
                    .requestMatchers(HttpMethod.GET, "/api/v1/tubers/**").hasRole("ADMINISTRADOR")
                    .requestMatchers(HttpMethod.POST, "/api/v1/tubers/**").hasRole("ADMINISTRADOR")
                    .requestMatchers(HttpMethod.PUT, "/api/v1/tubers/**").hasRole("ADMINISTRADOR")
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/tubers/**").hasRole("ADMINISTRADOR")
                    // varieties
                    .requestMatchers(HttpMethod.GET, "/api/v1/varieties/**").hasRole("ADMINISTRADOR")
                    .requestMatchers(HttpMethod.POST, "/api/v1/varieties/**").hasRole("ADMINISTRADOR")
                    .requestMatchers(HttpMethod.PUT, "/api/v1/varieties/**").hasRole("ADMINISTRADOR")
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/varieties/**").hasRole("ADMINISTRADOR")
                    // vitroOrders
                    .requestMatchers(HttpMethod.PUT, "/api/v1/vitroOrders/**").hasRole("ADMINISTRADOR")
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/vitroOrders/**").hasRole("ADMINISTRADOR")
                    // orderVarieties
                    .requestMatchers(HttpMethod.PUT, "/api/v1/orderVarieties/**").hasRole("ADMINISTRADOR")
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/orderVarieties/**").hasRole("ADMINISTRADOR")
                    // invoices
                    .requestMatchers(HttpMethod.GET, "/api/v1/invoices/**").hasRole("ADMINISTRADOR")
                    .requestMatchers(HttpMethod.POST, "/api/v1/invoices/**").hasRole("ADMINISTRADOR")
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/invoices/**").hasRole("ADMINISTRADOR")
                    // invoiceItems
                    .requestMatchers(HttpMethod.GET, "/api/v1/invoiceItems/**").hasRole("ADMINISTRADOR")
                    .requestMatchers(HttpMethod.POST, "/api/v1/invoiceItems/**").hasRole("ADMINISTRADOR")
                    // profits
                    .requestMatchers(HttpMethod.GET, "/api/v1/profits/**").hasRole("ADMINISTRADOR")
                    .requestMatchers(HttpMethod.POST, "/api/v1/profits/**").hasRole("ADMINISTRADOR")
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/profits/**").hasRole("ADMINISTRADOR")
                    // expenses
                    .requestMatchers(HttpMethod.GET, "/api/v1/expenses").hasRole("ADMINISTRADOR")
                    .requestMatchers(HttpMethod.POST, "/api/v1/expenses/**").hasRole("ADMINISTRADOR")
                    .requestMatchers(HttpMethod.PUT, "/api/v1/expenses/**").hasRole("ADMINISTRADOR")
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/expenses/**").hasRole("ADMINISTRADOR")
                    // categories
                    .requestMatchers(HttpMethod.GET, "/api/v1/categories/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/v1/categories/**").hasRole("ADMINISTRADOR")
                    .requestMatchers(HttpMethod.PUT, "/api/v1/categories/**").hasRole("ADMINISTRADOR")
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/categories/**").hasRole("ADMINISTRADOR")
                    // products
                    .requestMatchers(HttpMethod.GET, "/api/v1/products/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/v1/products/**").hasRole("ADMINISTRADOR")
                    .requestMatchers(HttpMethod.PUT, "/api/v1/products/**").hasRole("ADMINISTRADOR")
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/products/**").hasRole("ADMINISTRADOR")
                    // offers
                    .requestMatchers(HttpMethod.GET, "/api/v1/offers/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/v1/offers/**").hasRole("ADMINISTRADOR")
                    .requestMatchers(HttpMethod.PUT, "/api/v1/offers/**").hasRole("ADMINISTRADOR")
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/offers/**").hasRole("ADMINISTRADOR")
                    // offerProducts
                    .requestMatchers(HttpMethod.POST, "/api/v1/offerProducts/**").hasRole("ADMINISTRADOR")
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/offerProducts/**").hasRole("ADMINISTRADOR")
                    // orders
                    .requestMatchers(HttpMethod.PUT, "/api/v1/orders/**").hasRole("ADMINISTRADOR")
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/orders/**").hasRole("ADMINISTRADOR")
                    // orderProducts
                    .requestMatchers(HttpMethod.PUT, "/api/v1/orderProducts/**").hasRole("ADMINISTRADOR")
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/orderProducts/**").hasRole("ADMINISTRADOR")
                    // productImages
                    .requestMatchers(HttpMethod.GET, "/api/v1/productImages/**").hasRole("ADMINISTRADOR")
                    .requestMatchers(HttpMethod.POST, "/api/v1/productImages/**").hasRole("ADMINISTRADOR")
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/productImages/**").hasRole("ADMINISTRADOR")
                    // discounts
                    .requestMatchers(HttpMethod.GET, "/api/v1/discounts/**").hasRole("ADMINISTRADOR")
                    .requestMatchers(HttpMethod.POST, "/api/v1/discounts/**").hasRole("ADMINISTRADOR")
                    .requestMatchers(HttpMethod.PUT, "/api/v1/discounts/**").hasRole("ADMINISTRADOR")
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/discounts/**").hasRole("ADMINISTRADOR")
                    // admins
                    .requestMatchers(HttpMethod.GET, "/api/v1/admins/**").hasRole("ADMINISTRADOR")
                    .requestMatchers(HttpMethod.PUT, "/api/v1/admins/**").hasRole("ADMINISTRADOR")
                    // auth
                    .requestMatchers("/api/v1/auth/**").permitAll()
                    .anyRequest().authenticated())
            .sessionManagement(sessionManager ->
                sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authProvider)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }
}
