package com.inversionesaraujo.api.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inversionesaraujo.api.business.dto.ClientDTO;
import com.inversionesaraujo.api.business.dto.EmployeeOperationDTO;
import com.inversionesaraujo.api.business.dto.VitroOrderDTO;
import com.inversionesaraujo.api.business.dto.WarehouseDTO;
import com.inversionesaraujo.api.business.payload.AvailbleByMonth;
import com.inversionesaraujo.api.business.payload.MessageResponse;
import com.inversionesaraujo.api.business.payload.OrderDataResponse;
import com.inversionesaraujo.api.business.payload.TotalDeliverResponse;
import com.inversionesaraujo.api.business.request.InvitroDeliveredRequest;
import com.inversionesaraujo.api.business.request.NotificationRequest;
import com.inversionesaraujo.api.business.request.PickupInfoRequest;
import com.inversionesaraujo.api.business.request.ReceiverInfoRequest;
import com.inversionesaraujo.api.business.request.ShippingTypeRequest;
import com.inversionesaraujo.api.business.request.UpdateOrderRequest;
import com.inversionesaraujo.api.business.request.VitroOrderRequest;
import com.inversionesaraujo.api.business.service.IClient;
import com.inversionesaraujo.api.business.service.IEmployee;
import com.inversionesaraujo.api.business.service.IEmployeeOperation;
import com.inversionesaraujo.api.business.service.INotification;
import com.inversionesaraujo.api.business.dto.MonthlyProductionDTO;
import com.inversionesaraujo.api.business.dto.PickupInfoDTO;
import com.inversionesaraujo.api.business.dto.ReceiverInfoDTO;
import com.inversionesaraujo.api.business.service.IVitroOrder;
import com.inversionesaraujo.api.business.service.I_Image;
import com.inversionesaraujo.api.business.service.IWarehouse;
import com.inversionesaraujo.api.model.NotificationType;
import com.inversionesaraujo.api.model.OrderLocation;
import com.inversionesaraujo.api.model.Permission;
import com.inversionesaraujo.api.model.ShippingType;
import com.inversionesaraujo.api.model.SortBy;
import com.inversionesaraujo.api.model.SortDirection;
import com.inversionesaraujo.api.model.Status;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/vitroOrders")
public class VitroOrderController {
    @Autowired
    private IVitroOrder orderService;
    @Autowired
    private IClient clientService;
    @Autowired
    private I_Image imageService;
    @Autowired
    private IEmployee employeeService;
    @Autowired
    private INotification notiService;
    @Autowired
    private IEmployeeOperation employeeOperationService;
    @Autowired
    private IWarehouse warehouseService;

    @GetMapping
    public Page<VitroOrderDTO> getAll(
        @RequestParam(required = false) Long tuberId,
        @RequestParam(defaultValue = "0") Integer page,
        @RequestParam(defaultValue = "9") Integer size,
        @RequestParam(defaultValue = "ASC") SortDirection direction,
        @RequestParam(required = false) Month month,
        @RequestParam(required = false) Status status,
        @RequestParam(required = false) ShippingType shipType,
        @RequestParam(defaultValue = "finishDate") SortBy sortby,
        @RequestParam(defaultValue = "false") Boolean ordersReady,
        @RequestParam(required = false) Long employeeId,
        @RequestParam(required = false) OrderLocation location,
        @RequestParam(required = false) Integer day,
        @RequestParam(required = false) Long clientId
    ) {
        return orderService.listAll(tuberId, page, size, direction, month, status, sortby, shipType, ordersReady, employeeId, location, day, clientId);
    }
    
    @GetMapping("search")
    public Page<VitroOrderDTO> search(@RequestParam String param, @RequestParam(defaultValue = "0") Integer page) {
        return orderService.search(param, param, param, page);
    }

    @GetMapping("data")
    public ResponseEntity<MessageResponse> getData() {
        OrderDataResponse response = orderService.getData();

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("Los datos se obtuvieron con éxito")
            .data(response)
            .build());
    }

    @GetMapping("totalDeliver")
    public ResponseEntity<MessageResponse> getTotalDeliver() {
        TotalDeliverResponse response = orderService.totalDeliver();

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("Los datos se obtuvieron con éxito")
            .data(response)
            .build());
    }

    @GetMapping("availableByMonth")
    public ResponseEntity<MessageResponse> availableByMonth(@RequestParam LocalDate date, @RequestParam Integer quantity) {
        AvailbleByMonth response = orderService.availableByMonth(date, quantity);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("Los datos se obtuvieron con éxito")
            .data(response)
            .build());
    }

    @GetMapping("productionSummary")
    public ResponseEntity<MessageResponse> getProductionSummary() {
        List<MonthlyProductionDTO> summary = orderService.getMonthlyProductionSummary();
        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("Los datos se obtuvieron con éxito")
            .data(summary)
            .build());
    }

    @GetMapping("{id}")
    public ResponseEntity<MessageResponse> getOneById(@PathVariable Long id) {
        VitroOrderDTO order = orderService.findById(id);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El pedido invitro se creo con exito")
            .data(order)
            .build());
    }

    @PutMapping("{id}/updateFinishDate")
    public ResponseEntity<MessageResponse> updateFinishDate(@PathVariable Long id, @RequestBody LocalDate finishDate) {
        VitroOrderDTO order = orderService.findById(id);
        order.setFinishDate(finishDate);

        VitroOrderDTO orderUpdated = orderService.save(order);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("La fecha de entrega se actualizo con exito")
            .data(orderUpdated)
            .build());
    }

    @PutMapping("{id}/addShippingType")
    public ResponseEntity<MessageResponse> addShippingType(@PathVariable Long id, @RequestBody @Valid ShippingTypeRequest request) {
        VitroOrderDTO order = orderService.findById(id);

        ShippingType shippingType = request.getShippingType();
        WarehouseDTO warehouse = null;
        ReceiverInfoDTO receiverInfo = null;
        String department = "";
        String city = "";

        if(shippingType == ShippingType.RECOJO_ALMACEN) {
            if(request.getPickupInfo() == null) return ResponseEntity.badRequest().body(MessageResponse
                .builder()
                .message("La fecha y hora de recojo son requeridas")
                .build());

            warehouse = warehouseService.findById(1L);
            department = "Junin";
            city = "Huancayo";
        }else if(shippingType == ShippingType.ENVIO_AGENCIA) {
            if(request.getReceiverInfo() == null) return ResponseEntity.badRequest().body(MessageResponse
                .builder()
                .message("La información de la persona que recogerá el pedido es requerida")
                .build());

            if(request.getDepartment() == null || request.getCity() == null) return ResponseEntity.badRequest().body(MessageResponse
                .builder()
                .message("El departamento y la ciudad son requeridos")
                .build());

            receiverInfo = request.getReceiverInfo();
            department = request.getDepartment();
            city = request.getCity();
        }

        order.setShippingType(shippingType);
        order.setWarehouse(warehouse);
        order.setReceiverInfo(receiverInfo);
        order.setPickupInfo(request.getPickupInfo());
        order.setDepartment(department);
        order.setCity(city);

        orderService.save(order);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El tipo de envio se agrego con exito")
            .data(order)
            .build());   
    }

    @PutMapping("{id}/updateReceiverInfo")
    public ResponseEntity<MessageResponse> updateReceiverInfo(@PathVariable Long id, @RequestBody @Valid ReceiverInfoRequest request) {
        VitroOrderDTO order = orderService.findById(id);
        if(order.getShippingType() == ShippingType.RECOJO_ALMACEN) return ResponseEntity.badRequest().body(MessageResponse
            .builder()
            .message("El pedido será recogido en almacén")
            .build());

        if(order.getLocation() == OrderLocation.AGENCIA) return ResponseEntity.badRequest().body(MessageResponse
            .builder()
            .message("El pedido ya fue enviado")
            .build());

        order.setDepartment(request.getDepartment());
        order.setCity(request.getCity());
        order.setReceiverInfo(
            ReceiverInfoDTO.builder()
                .fullName(request.getFullName())
                .document(request.getDocument())
                .phone(request.getPhone())
                .build()
        );
        VitroOrderDTO orderUpdated = orderService.save(order);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("Los datos de la persona que recoje fueron actualizados")
            .data(orderUpdated)
            .build());
    }

    @PutMapping("{id}/updatePickupInfo")
    public ResponseEntity<MessageResponse> updatePickupInfo(@PathVariable Long id, @RequestBody @Valid PickupInfoRequest request) {
        VitroOrderDTO order = orderService.findById(id);
        if(order.getShippingType() == ShippingType.ENVIO_AGENCIA) return ResponseEntity.badRequest().body(MessageResponse
            .builder()
            .message("El pedido será enviado por agencia")
            .build());

        if(order.getStatus() == Status.ENVIADO) return ResponseEntity.badRequest().body(MessageResponse
            .builder()
            .message("El pedido ya fue entregado")
            .build());

        order.setPickupInfo(
            PickupInfoDTO.builder()
                .date(request.getDate())
                .hour(request.getHour())
                .build()
        );
        VitroOrderDTO orderUpdated = orderService.save(order);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("Los datos de recojo fueron actualizados")
            .data(orderUpdated)
            .build());
    }

    @PostMapping
    public ResponseEntity<MessageResponse> create(@RequestBody @Valid VitroOrderRequest request) {
        ClientDTO client = clientService.findById(request.getClientId());
        LocalDate initDate = request.getInitDate() != null ? request.getInitDate() : LocalDate.now();

        VitroOrderDTO order = orderService.save(VitroOrderDTO
            .builder()
            .client(client)
            .department(request.getDepartment())
            .city(request.getCity())
            .initDate(initDate)
            .finishDate(request.getFinishDate())
            .location(request.getLocation())
            .status(request.getStatus() )
            .createdBy(request.getCreatedBy())
            .shippingType(request.getShippingType())
            .isReady(false)
            .evidence(null)
            .build());

        if (request.getOperatorId() != null && request.getOperatorId() != 1L) {
            EmployeeOperationDTO employeeOperation = EmployeeOperationDTO
                .builder()
                .employeeId(request.getOperatorId())
                .operation("Creo un pedido invitro")
                .redirectTo("/invitro/" + order.getId())
                .build();

            employeeOperationService.save(employeeOperation);

            NotificationRequest notiRequest = NotificationRequest
                .builder()
                .userId(request.getOperatorId())
                .type(NotificationType.NEW_VITRO_ORDER)
                .redirectTo("/invitro/" + order.getId())
                .build();

            notiService.sendNotificationToUsersWithPermission(notiRequest, Permission.INVITRO_WATCH, request.getOperatorId());
        }

        return ResponseEntity.status(201).body(MessageResponse
            .builder()
            .message("El pedido invitro se creo con exito")
            .data(order)
            .build());
    }

    @PutMapping("{id}/ended")
    public ResponseEntity<MessageResponse> orderEnded(@PathVariable Long id) {
        VitroOrderDTO order = orderService.findById(id);
        order.setIsReady(true);
        orderService.save(order);

        if(order.getClient().getUserId() != null) {
            NotificationRequest notification = NotificationRequest
                .builder()
                .userId(order.getClient().getUserId())
                .type(NotificationType.VITRO_ORDER_ALREADY)
                .redirectTo("/perfil/invitro/" + order.getId())
                .build();

            notiService.create(notification);
        }

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El estado del pedido se actualizo con exito")
            .data(order)
            .build());
    }
    
    @PutMapping("{id}/agency")
    public ResponseEntity<MessageResponse> updateAgency(@PathVariable Long id, @RequestBody @Valid InvitroDeliveredRequest request) {
        VitroOrderDTO order = orderService.findById(id);
        order.setStatus(Status.ENVIADO);
        order.setEmployee(employeeService.findById(request.getEmployeeId()));
        order.setEvidence(imageService.findById(request.getEvidenceId()));
        order.getReceiverInfo().setTrackingCode(request.getTrackingCode());
        order.getReceiverInfo().setCode(request.getCode());
        order.setLocation(OrderLocation.AGENCIA);
        order.setDeliveredAt(LocalDateTime.now());

        orderService.save(order);

        if(request.getEmployeeId() != 1L) {
            EmployeeOperationDTO employeeOperation = EmployeeOperationDTO
                .builder()
                .employeeId(request.getEmployeeId())
                .operation("Entrego el pedido")
                .redirectTo("/invitro/" + order.getId())
                .build();

            employeeOperationService.save(employeeOperation);
        }

        if(order.getClient().getUserId() != null) {
            NotificationRequest notification = NotificationRequest
                .builder()
                .userId(order.getClient().getUserId())
                .type(NotificationType.VITRO_ORDER_AT_AGENCY)
                .redirectTo("/perfil/invitro/" + order.getId())
                .build();

            notiService.create(notification);
        }

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El estado del pedido se actualizo con exito")
            .data(order)
            .build());
    }

    @PutMapping("{id}/delivered")
    public ResponseEntity<MessageResponse> orderDelivered(@PathVariable Long id, @RequestBody @Valid InvitroDeliveredRequest request) {
        VitroOrderDTO order = orderService.findById(id);

        order.setStatus(Status.ENTREGADO);
        order.setEmployee(employeeService.findById(request.getEmployeeId()));
        order.setEvidence(imageService.findById(request.getEvidenceId()));
        order.setDeliveredAt(LocalDateTime.now());

        orderService.save(order);

        if(request.getEmployeeId() != 1L) {
            EmployeeOperationDTO employeeOperation = EmployeeOperationDTO
                .builder()
                .employeeId(request.getEmployeeId())
                .operation("Entrego el pedido invitro")
                .redirectTo("/invitro/" + order.getId())
                .build();

            employeeOperationService.save(employeeOperation);
        }

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El estado del pedido se actualizo con exito")
            .data(order)
            .build());
    }

    @PutMapping("{id}/status")
    public ResponseEntity<MessageResponse> updateStatus(@PathVariable Long id, @RequestBody @Valid UpdateOrderRequest request) {
        VitroOrderDTO order = orderService.findById(id);
        order.setStatus(request.getStatus());

        VitroOrderDTO orderUpdated = orderService.save(order);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El estado del pedido se actualizo con exito")
            .data(orderUpdated)
            .build());
    }

    @PostMapping("{id}/alertNewOrder")
    public ResponseEntity<MessageResponse> alertNewOrder(@PathVariable Long id) {
        VitroOrderDTO order = orderService.findById(id);

        orderService.alertNewOrder(order);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El pedido se notifico con éxito")
            .data(order)
            .build());
    }

    @PutMapping("{id}")
    public ResponseEntity<MessageResponse> update(@PathVariable Long id, @RequestBody @Valid VitroOrderRequest request) {
        VitroOrderDTO order = orderService.findById(id);
        
        order.setDepartment(request.getDepartment());
        order.setCity(request.getCity());
        order.setInitDate(request.getInitDate());
        order.setFinishDate(request.getFinishDate());
        order.setStatus(request.getStatus());
        order.setShippingType(request.getShippingType());
        order.setPending(order.getTotal() - order.getTotalAdvance());
        order.setLocation(request.getLocation());
        order.setIsReady(request.getIsReady());

        VitroOrderDTO orderUpdated = orderService.save(order);

        if (request.getOperatorId() != null && request.getOperatorId() != 1L) {
            EmployeeOperationDTO employeeOperation = EmployeeOperationDTO
                .builder()
                .employeeId(request.getOperatorId())
                .operation("Actualizo un pedido invitro")
                .redirectTo("/invitro/" + order.getId())
                .build();

            employeeOperationService.save(employeeOperation);
        }

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El pedido invitro se actualizo con exito")
            .data(orderUpdated)
            .build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable Long id) {
        VitroOrderDTO order = orderService.findById(id);
        ClientDTO client = order.getClient();
        Double totalAdvance = order.getTotalAdvance();
        orderService.delete(id);

        client.setConsumption(client.getConsumption() - totalAdvance);
        clientService.save(client);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El pedido invitro se elimino con exito")
            .build());
    }

    @PostMapping("{id}/generate-invoice")
    public ResponseEntity<MessageResponse> generateInvoice(@PathVariable Long id) {
        VitroOrderDTO order = orderService.findById(id);

        Long invoiceId = orderService.createAndSendInvoice(order);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El pedido se notifico y se envió el comprobante con éxito")
            .data(Map.of("invoiceId", invoiceId))
            .build());
    }
}
