package com.inversionesaraujo.api.security.config;

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

import com.inversionesaraujo.api.security.filter.JwtAuthFilter;

import static org.springframework.security.config.Customizer.withDefaults;

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
                    .requestMatchers(HttpMethod.GET, "/api/v1/messages/**").hasAnyAuthority("MESSAGES_WATCH")
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/messages/**").hasAnyAuthority("MESSAGES_DELETE")
                    .requestMatchers(HttpMethod.POST, "/api/v1/messages/**").permitAll()
                    // chatbot
                    .requestMatchers(HttpMethod.POST, "/api/v1/chatbot/question").permitAll()
                    // notifications
                    .requestMatchers(HttpMethod.POST, "/api/v1/notifications/**").permitAll()
                    .requestMatchers("/api/v1/ws/**").permitAll()
                    // payments
                    .requestMatchers(HttpMethod.POST, "/api/v1/payments/**").permitAll()
                    // tubers
                    .requestMatchers(HttpMethod.GET, "/api/v1/tubers/**").hasAnyAuthority("TUBERS_WATCH")
                    .requestMatchers(HttpMethod.POST, "/api/v1/tubers/**").hasAnyAuthority("TUBERS_CREATE")
                    .requestMatchers(HttpMethod.PUT, "/api/v1/tubers/**").hasAnyAuthority("TUBERS_UPDATE")
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/tubers/**").hasAnyAuthority("TUBERS_DELETE")
                    // varieties
                    .requestMatchers(HttpMethod.GET, "/api/v1/varieties/**").hasAnyAuthority("VARIETIES_WATCH")
                    .requestMatchers(HttpMethod.POST, "/api/v1/varieties/**").hasAnyAuthority("VARIETIES_CREATE")
                    .requestMatchers(HttpMethod.PUT, "/api/v1/varieties/**").hasAnyAuthority("VARIETIES_UPDATE")
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/varieties/**").hasAnyAuthority("VARIETIES_DELETE")
                    // vitroOrders
                    .requestMatchers(HttpMethod.PUT, "/api/v1/vitroOrders/**").hasAnyAuthority("INVITRO_UPDATE")
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/vitroOrders/**").hasAnyAuthority("INVITRO_DELETE")
                    .requestMatchers(HttpMethod.POST, "/api/v1/vitroOrders/**").hasAnyAuthority("INVITRO_CREATE")
                    .requestMatchers(HttpMethod.GET, "/api/v1/vitroOrders/**").hasAnyAuthority("INVITRO_WATCH")
                    // orderVarieties
                    .requestMatchers(HttpMethod.PUT, "/api/v1/orderVarieties/**").hasAnyAuthority("INVITRO_ITEM_UPDATE")
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/orderVarieties/**").hasAnyAuthority("INVITRO_ITEM_DELETE")
                    .requestMatchers(HttpMethod.POST, "/api/v1/orderVarieties/**").hasAnyAuthority("INVITRO_ITEM_CREATE")
                    .requestMatchers(HttpMethod.GET, "/api/v1/orderVarieties/**").hasAnyAuthority("INVITRO_WATCH")
                    // advances
                    .requestMatchers(HttpMethod.PUT, "/api/v1/advances/**").hasAnyAuthority("INVITRO_ADVANCE_UPDATE")
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/advances/**").hasAnyAuthority("INVITRO_ADVANCE_DELETE")
                    .requestMatchers(HttpMethod.POST, "/api/v1/advances/**").hasAnyAuthority("INVITRO_ADVANCE_CREATE")
                    .requestMatchers(HttpMethod.GET, "/api/v1/advances/**").hasAnyAuthority("INVITRO_ADVANCE_WATCH")
                    // invoices
                    .requestMatchers(HttpMethod.PUT, "/api/v1/invoices/**").hasAnyAuthority("INVOICES_UPDATE")
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/invoices/**").hasAnyAuthority("INVOICES_DELETE")
                    .requestMatchers(HttpMethod.POST, "/api/v1/invoices/**").hasAnyAuthority("INVOICES_CREATE")
                    .requestMatchers(HttpMethod.GET, "/api/v1/invoices/**").hasAnyAuthority("INVOICES_WATCH")
                    // invoiceItems
                    .requestMatchers(HttpMethod.PUT, "/api/v1/invoiceItems/**").hasAnyAuthority("INVOICES_ITEM_UPDATE")
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/invoiceItems/**").hasAnyAuthority("INVOICES_ITEM_DELETE")
                    .requestMatchers(HttpMethod.POST, "/api/v1/invoiceItems/**").hasAnyAuthority("INVOICES_ITEM_CREATE")
                    .requestMatchers(HttpMethod.GET, "/api/v1/invoiceItems/**").hasAnyAuthority("INVOICES_WATCH")
                    // // profits
                    .requestMatchers(HttpMethod.GET, "/api/v1/profits/**").hasAnyAuthority("PROFITS_WATCH")
                    // // expenses
                    .requestMatchers(HttpMethod.GET, "/api/v1/expenses").hasAnyAuthority("EXPENSES_WATCH")
                    .requestMatchers(HttpMethod.POST, "/api/v1/expenses/**").hasAnyAuthority("EXPENSES_CREATE")
                    .requestMatchers(HttpMethod.PUT, "/api/v1/expenses/**").hasAnyAuthority("EXPENSES_UPDATE")
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/expenses/**").hasAnyAuthority("EXPENSES_DELETE")
                    // categories
                    .requestMatchers(HttpMethod.GET, "/api/v1/categories/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/v1/categories/**").hasAnyAuthority("PRODUCTS_CATEGORY_CREATE")
                    .requestMatchers(HttpMethod.PUT, "/api/v1/categories/**").hasAnyAuthority("PRODUCTS_CATEGORY_UPDATE")
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/categories/**").hasAnyAuthority("PRODUCTS_CATEGORY_DELETE")
                    // warehouses
                    .requestMatchers(HttpMethod.GET, "/api/v1/warehouses/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/v1/warehouses/**").hasAnyAuthority("WAREHOUSES_CREATE")
                    .requestMatchers(HttpMethod.PUT, "/api/v1/warehouses/**").hasAnyAuthority("WAREHOUSES_UPDATE")
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/warehouses/**").hasAnyAuthority("WAREHOUSES_DELETE")
                    // products
                    .requestMatchers(HttpMethod.GET, "/api/v1/products/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/v1/products/**").hasAnyAuthority("PRODUCT_CREATE")
                    .requestMatchers(HttpMethod.PUT, "/api/v1/products/**").hasAnyAuthority("PRODUCT_UPDATE")
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/products/**").hasAnyAuthority("PRODUCT_DELETE")
                    // offers
                    .requestMatchers(HttpMethod.GET, "/api/v1/offers/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/v1/offers/**").hasAnyAuthority("BANNERS_CREATE")
                    .requestMatchers(HttpMethod.PUT, "/api/v1/offers/**").hasAnyAuthority("BANNERS_UPDATE")
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/offers/**").hasAnyAuthority("BANNERS_DELETE")
                    // offerProducts
                    .requestMatchers(HttpMethod.GET, "/api/v1/offerProducts/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/v1/offerProducts/**").hasAnyAuthority("BANNERS_ITEM_CREATE")
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/offerProducts/**").hasAnyAuthority("BANNERS_ITEM_DELETE")
                    // // orders
                    .requestMatchers(HttpMethod.PUT, "/api/v1/orders/**").hasAnyAuthority("ORDERS_UPDATE")
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/orders/**").hasAnyAuthority("ORDERS_DELETE")
                    // orderProducts
                    .requestMatchers(HttpMethod.PUT, "/api/v1/orderProducts/**").hasAnyAuthority("ORDERS_PRODUCTS_UPDATE")
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/orderProducts/**").hasAnyAuthority("ORDERS_PRODUCTS_DELETE")
                    // // productImages
                    .requestMatchers(HttpMethod.GET, "/api/v1/productImages/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/v1/productImages/**").hasAnyAuthority("PRODUCTS_IMAGE_CREATE")
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/productImages/**").hasAnyAuthority("PRODUCTS_IMAGE_DELETE")
                    // // discounts
                    .requestMatchers(HttpMethod.GET, "/api/v1/discounts/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/v1/discounts/**").hasAnyAuthority("PRODUCTS_DISCOUNT_CREATE")
                    .requestMatchers(HttpMethod.PUT, "/api/v1/discounts/**").hasAnyAuthority("PRODUCTS_DISCOUNT_UPDATE")
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/discounts/**").hasAnyAuthority("PRODUCTS_DISCOUNT_DELETE")
                    // clients
                    .requestMatchers(HttpMethod.POST, "/api/v1/clients").permitAll()
                    // roles
                    .requestMatchers(HttpMethod.GET, "/api/v1/roles/**").hasAnyAuthority("ROLES_WATCH")
                    .requestMatchers(HttpMethod.POST, "/api/v1/roles/**").hasAnyAuthority("ROLES_CREATE")
                    .requestMatchers(HttpMethod.PUT, "/api/v1/roles/**").hasAnyAuthority("ROLES_UPDATE")
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/roles/**").hasAnyAuthority("ROLES_DELETE")
                    // permissions
                    .requestMatchers(HttpMethod.GET, "/api/v1/roles/permissions").hasAnyAuthority("PERMISSIONS_WATCH")
                    // employees
                    .requestMatchers(HttpMethod.GET, "/api/v1/employees/**").hasAnyAuthority("EMPLOYEES_WATCH")
                    .requestMatchers(HttpMethod.POST, "/api/v1/employees/**").hasAnyAuthority("EMPLOYEES_CREATE")
                    .requestMatchers(HttpMethod.PUT, "/api/v1/employees/**").hasAnyAuthority("EMPLOYEES_UPDATE")
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/employees/**").hasAnyAuthority("EMPLOYEES_DELETE")
                    // auth
                    .requestMatchers("/api/v1/auth/**").permitAll()
                    .anyRequest().authenticated())
            .sessionManagement(sessionManager ->
                sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authProvider)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling(exception ->
                exception
                    .authenticationEntryPoint(new AuthEntryPoint())
                    .accessDeniedHandler(new AccessDeniedCustom()))
            .build();
    }

    @Bean
    WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000, https://inv-araujo-app.vercel.app, http://localhost:4200")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}
