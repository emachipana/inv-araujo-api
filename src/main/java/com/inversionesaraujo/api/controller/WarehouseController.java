package com.inversionesaraujo.api.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inversionesaraujo.api.business.dto.EmployeeOperationDTO;
import com.inversionesaraujo.api.business.dto.WarehouseDTO;
import com.inversionesaraujo.api.business.payload.MessageResponse;
import com.inversionesaraujo.api.business.request.WarehouseRequest;
import com.inversionesaraujo.api.business.service.IEmployeeOperation;
import com.inversionesaraujo.api.business.service.IWarehouse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/warehouses")
public class WarehouseController {
    @Autowired
    private IWarehouse warehouseService;
    @Autowired
    private IEmployeeOperation employeeOperationService;

    @GetMapping
    public List<WarehouseDTO> getAll() {
        return warehouseService.listAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<MessageResponse> getOneById(@PathVariable Long id) {
        WarehouseDTO warehouse = warehouseService.findById(id);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El almacén se encontró con éxito")
            .data(warehouse)
            .build());
    }

    @PostMapping
    public ResponseEntity<MessageResponse> create(@RequestBody @Valid WarehouseRequest request) {
        WarehouseDTO savedWarehouse = warehouseService.save(WarehouseDTO
            .builder()
            .name(request.getName())
            .department(request.getDepartment())
            .province(request.getProvince())
            .district(request.getDistrict())
            .address(request.getAddress())
            .ref(request.getRef())
            .latitude(request.getLatitude())
            .longitude(request.getLongitude())
            .build());

        if(request.getEmployeeId() != null && request.getEmployeeId() != 1L) {
            LocalDateTime now = LocalDateTime.now();
            
            EmployeeOperationDTO employeeOperation = EmployeeOperationDTO
                .builder()
                .employeeId(request.getEmployeeId())
                .operation("Creo un almacen")
                .redirectTo("/almacenes/" + savedWarehouse.getId())
                .createdAt(now)
                .build();

            employeeOperationService.save(employeeOperation);
        }

        return ResponseEntity.status(201).body(MessageResponse
            .builder()
            .message("El almacén se creó con éxito")
            .data(savedWarehouse)
            .build());
    }

    @PutMapping("{id}")
    public ResponseEntity<MessageResponse> update(@RequestBody @Valid WarehouseRequest request, @PathVariable Long id) {
        WarehouseDTO warehouse = warehouseService.findById(id);

        warehouse.setName(request.getName());
        warehouse.setDepartment(request.getDepartment());
        warehouse.setProvince(request.getProvince());
        warehouse.setDistrict(request.getDistrict());
        warehouse.setAddress(request.getAddress());
        warehouse.setRef(request.getRef());

        WarehouseDTO warehouseUpdated = warehouseService.save(warehouse);

        if(request.getEmployeeId() != null && request.getEmployeeId() != 1L) {
            LocalDateTime now = LocalDateTime.now();
            
            EmployeeOperationDTO employeeOperation = EmployeeOperationDTO
                .builder()
                .employeeId(request.getEmployeeId())
                .operation("Actualizo un almacen")
                .redirectTo("/almacenes/" + warehouseUpdated.getId())
                .createdAt(now)
                .build();

            employeeOperationService.save(employeeOperation);
        }

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El almacén se actualizón con éxito")
            .data(warehouseUpdated)
            .build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable Long id) {
        warehouseService.delete(id);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El almacén se eliminó con éxito")
            .build());
    }
}
