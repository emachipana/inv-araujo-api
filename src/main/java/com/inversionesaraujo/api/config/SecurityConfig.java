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
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import static org.springframework.security.config.Customizer.withDefaults;

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
            .cors(withDefaults())
            .authorizeHttpRequests(authRequest ->
                authRequest
                    // messages
                    .requestMatchers(HttpMethod.GET, "/api/v1/messages/**").hasAnyAuthority("ADMINISTRADOR")
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/messages/**").hasAnyAuthority("ADMINISTRADOR")
                    .requestMatchers(HttpMethod.POST, "/api/v1/messages/**").permitAll()
                    // tubers
                    .requestMatchers(HttpMethod.GET, "/api/v1/tubers/**").hasAnyAuthority("ADMINISTRADOR")
                    .requestMatchers(HttpMethod.POST, "/api/v1/tubers/**").hasAnyAuthority("ADMINISTRADOR")
                    .requestMatchers(HttpMethod.PUT, "/api/v1/tubers/**").hasAnyAuthority("ADMINISTRADOR")
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/tubers/**").hasAnyAuthority("ADMINISTRADOR")
                    // varieties
                    .requestMatchers(HttpMethod.GET, "/api/v1/varieties/**").hasAnyAuthority("ADMINISTRADOR")
                    .requestMatchers(HttpMethod.POST, "/api/v1/varieties/**").hasAnyAuthority("ADMINISTRADOR")
                    .requestMatchers(HttpMethod.PUT, "/api/v1/varieties/**").hasAnyAuthority("ADMINISTRADOR")
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/varieties/**").hasAnyAuthority("ADMINISTRADOR")
                    // vitroOrders
                    .requestMatchers(HttpMethod.PUT, "/api/v1/vitroOrders/**").hasAnyAuthority("ADMINISTRADOR")
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/vitroOrders/**").hasAnyAuthority("ADMINISTRADOR")
                    // orderVarieties
                    .requestMatchers(HttpMethod.PUT, "/api/v1/orderVarieties/**").hasAnyAuthority("ADMINISTRADOR")
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/orderVarieties/**").hasAnyAuthority("ADMINISTRADOR")
                    // advances
                    .requestMatchers(HttpMethod.PUT, "/api/v1/advances/**").hasAnyAuthority("ADMINISTRADOR")
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/advances/**").hasAnyAuthority("ADMINISTRADOR")
                    // invoices
                    .requestMatchers(HttpMethod.GET, "/api/v1/invoices/**").hasAnyAuthority("ADMINISTRADOR")
                    .requestMatchers(HttpMethod.POST, "/api/v1/invoices/**").hasAnyAuthority("ADMINISTRADOR")
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/invoices/**").hasAnyAuthority("ADMINISTRADOR")
                    // invoiceItems
                    .requestMatchers(HttpMethod.GET, "/api/v1/invoiceItems/**").hasAnyAuthority("ADMINISTRADOR")
                    .requestMatchers(HttpMethod.POST, "/api/v1/invoiceItems/**").hasAnyAuthority("ADMINISTRADOR")
                    // profits
                    .requestMatchers(HttpMethod.GET, "/api/v1/profits/**").hasAnyAuthority("ADMINISTRADOR")
                    .requestMatchers(HttpMethod.POST, "/api/v1/profits/**").hasAnyAuthority("ADMINISTRADOR")
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/profits/**").hasAnyAuthority("ADMINISTRADOR")
                    // expenses
                    .requestMatchers(HttpMethod.GET, "/api/v1/expenses").hasAnyAuthority("ADMINISTRADOR")
                    .requestMatchers(HttpMethod.POST, "/api/v1/expenses/**").hasAnyAuthority("ADMINISTRADOR")
                    .requestMatchers(HttpMethod.PUT, "/api/v1/expenses/**").hasAnyAuthority("ADMINISTRADOR")
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/expenses/**").hasAnyAuthority("ADMINISTRADOR")
                    // categories
                    .requestMatchers(HttpMethod.GET, "/api/v1/categories/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/v1/categories/**").hasAuthority("ADMINISTRADOR")
                    .requestMatchers(HttpMethod.PUT, "/api/v1/categories/**").hasAnyAuthority("ADMINISTRADOR")
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/categories/**").hasAnyAuthority("ADMINISTRADOR")
                    // products
                    .requestMatchers(HttpMethod.GET, "/api/v1/products/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/v1/products/**").hasAnyAuthority("ADMINISTRADOR")
                    .requestMatchers(HttpMethod.PUT, "/api/v1/products/**").hasAnyAuthority("ADMINISTRADOR")
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/products/**").hasAnyAuthority("ADMINISTRADOR")
                    // offers
                    .requestMatchers(HttpMethod.GET, "/api/v1/offers/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/v1/offers/**").hasAnyAuthority("ADMINISTRADOR")
                    .requestMatchers(HttpMethod.PUT, "/api/v1/offers/**").hasAnyAuthority("ADMINISTRADOR")
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/offers/**").hasAnyAuthority("ADMINISTRADOR")
                    // offerProducts
                    .requestMatchers(HttpMethod.POST, "/api/v1/offerProducts/**").hasAnyAuthority("ADMINISTRADOR")
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/offerProducts/**").hasAnyAuthority("ADMINISTRADOR")
                    // orders
                    .requestMatchers(HttpMethod.PUT, "/api/v1/orders/**").hasAnyAuthority("ADMINISTRADOR")
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/orders/**").hasAnyAuthority("ADMINISTRADOR")
                    .requestMatchers(HttpMethod.POST, "/api/v1/orders/**").permitAll()
                    // orderProducts
                    .requestMatchers(HttpMethod.PUT, "/api/v1/orderProducts/**").hasAnyAuthority("ADMINISTRADOR")
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/orderProducts/**").hasAnyAuthority("ADMINISTRADOR")
                    // productImages
                    .requestMatchers(HttpMethod.GET, "/api/v1/productImages/**").hasAnyAuthority("ADMINISTRADOR")
                    .requestMatchers(HttpMethod.POST, "/api/v1/productImages/**").hasAnyAuthority("ADMINISTRADOR")
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/productImages/**").hasAnyAuthority("ADMINISTRADOR")
                    // discounts
                    .requestMatchers(HttpMethod.GET, "/api/v1/discounts/**").hasAnyAuthority("ADMINISTRADOR")
                    .requestMatchers(HttpMethod.POST, "/api/v1/discounts/**").hasAnyAuthority("ADMINISTRADOR")
                    .requestMatchers(HttpMethod.PUT, "/api/v1/discounts/**").hasAnyAuthority("ADMINISTRADOR")
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/discounts/**").hasAnyAuthority("ADMINISTRADOR")
                    // clients
                    .requestMatchers(HttpMethod.POST, "/api/v1/clients").permitAll()
                    // admins
                    .requestMatchers(HttpMethod.GET, "/api/v1/admins/**").hasAnyAuthority("ADMINISTRADOR")
                    .requestMatchers(HttpMethod.PUT, "/api/v1/admins/**").hasAnyAuthority("ADMINISTRADOR")
                    // auth
                    .requestMatchers("/api/v1/auth/**").permitAll()
                    .anyRequest().authenticated())
            .sessionManagement(sessionManager ->
                sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authProvider)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000, https://inv-araujo-app.vercel.app")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}
