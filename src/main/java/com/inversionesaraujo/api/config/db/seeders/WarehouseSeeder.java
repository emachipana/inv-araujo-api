package com.inversionesaraujo.api.config.db.seeders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inversionesaraujo.api.business.dto.WarehouseDTO;
import com.inversionesaraujo.api.business.service.IWarehouse;

@Component
public class WarehouseSeeder {
    @Autowired
    private IWarehouse warehouseService;
    
    public void seed() {
        final WarehouseDTO existing = warehouseService.findByName("Sapallanga");
        if (existing == null) {
            WarehouseDTO warehouse = WarehouseDTO
                .builder()
                .name("Sapallanga")
                .address("Jr. San Bernarndo 467, Sapallanga, Huancayo")
                .department("Junín")
                .province("Huancayo")
                .district("Sapallanga")
                .ref("A 2 cuadras del cementerio de sapallanga")
                .latitude(-12.14321636)
                .longitude(-75.1621926)
                .build();
            warehouseService.save(warehouse);
            System.out.println("Almacén SAPALLANGA creado.");
        } else {
            System.out.println("Almacén SAPALLANGA ya existe.");
        }
    }
}
