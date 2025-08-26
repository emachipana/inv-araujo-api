package com.inversionesaraujo.api.config.db.seeders;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;

@Configuration
public class DatabaseSeeder {
    @Autowired
    private AdminUserSeeder adminUserSeeder;
    @Autowired
    private MonthlyProfitSeeder monthlyProfitSeeder;
    // @Autowired
    // private EmbeddingSeeder embeddingSeeder;
    @Autowired
    private AdminRoleSeeder adminRoleSeeder;
    @Autowired
    private ClienteRoleSeeder clienteRoleSeeder;
    @Autowired
    private AlmaceneroRoleSeeder almaceneroRoleSeeder;
    @Autowired
    private WarehouseSeeder warehouseSeeder;

    @Bean
    CommandLineRunner seedAll() {
        return args -> {
            adminRoleSeeder.seed();
            clienteRoleSeeder.seed();
            almaceneroRoleSeeder.seed();
            adminUserSeeder.seed();
            monthlyProfitSeeder.seed();
            warehouseSeeder.seed();
            // embeddingSeeder.seed();
        };
    }
}
