package com.inversionesaraujo.api.controller;

import java.time.LocalDate;
import java.time.Month;
import java.util.Map;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inversionesaraujo.api.business.dto.ClientDTO;
import com.inversionesaraujo.api.business.dto.EmployeeOperationDTO;
import com.inversionesaraujo.api.business.dto.ReceiverInfoDTO;
import com.inversionesaraujo.api.business.dto.OrderDTO;
import com.inversionesaraujo.api.business.dto.PickupInfoDTO;
import com.inversionesaraujo.api.business.dto.WarehouseDTO;
import com.inversionesaraujo.api.business.payload.AvailableHours;
import com.inversionesaraujo.api.business.payload.MessageResponse;
import com.inversionesaraujo.api.business.payload.OrderDataResponse;
import com.inversionesaraujo.api.business.payload.TotalDeliverResponse;
import com.inversionesaraujo.api.business.request.InvitroDeliveredRequest;
import com.inversionesaraujo.api.business.request.NotificationRequest;
import com.inversionesaraujo.api.business.request.OrderRequest;
import com.inversionesaraujo.api.business.request.PickupInfoRequest;
import com.inversionesaraujo.api.business.request.ReceiverInfoRequest;
import com.inversionesaraujo.api.business.request.UpdateOrderRequest;
import com.inversionesaraujo.api.business.service.IClient;
import com.inversionesaraujo.api.business.service.IEmployee;
import com.inversionesaraujo.api.business.service.IEmployeeOperation;
import com.inversionesaraujo.api.business.service.INotification;
import com.inversionesaraujo.api.business.service.IOrder;
import com.inversionesaraujo.api.business.service.IWarehouse;
import com.inversionesaraujo.api.business.service.I_Image;
import com.inversionesaraujo.api.model.NotificationType;
import com.inversionesaraujo.api.model.OrderLocation;
import com.inversionesaraujo.api.model.Permission;
import com.inversionesaraujo.api.model.ShippingType;
import com.inversionesaraujo.api.model.SortBy;
import com.inversionesaraujo.api.model.SortDirection;
import com.inversionesaraujo.api.model.Status;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    @Autowired
    private IOrder orderService;
    @Autowired
    private IClient clientService;
    @Autowired
    private IWarehouse warehouseService;
    @Autowired
    private IEmployeeOperation employeeOperationService;
    @Autowired
    private INotification notiService;
    @Autowired
    private I_Image imageService;
    @Autowired
    private IEmployee employeeService;

    @GetMapping
    public Page<OrderDTO> getAll(
        @RequestParam(required = false) Status status,
        @RequestParam(defaultValue = "0") Integer page,
        @RequestParam(defaultValue = "9") Integer size,
        @RequestParam(defaultValue = "DESC") SortDirection direction,
        @RequestParam(defaultValue = "date") SortBy sortby,
        @RequestParam(required = false) Month month,
        @RequestParam(required = false) ShippingType shipType,
        @RequestParam(required = false) Long warehouseId,
        @RequestParam(required = false) Long employeeId,
        @RequestParam(required = false) OrderLocation location,
        @RequestParam(required = false) Integer day,
        @RequestParam(required = false) Long clientId
    ) {
        return orderService.listAll(status, page, size, direction, month, sortby, shipType, warehouseId, employeeId, location, day, clientId);
    }

    @GetMapping("search")
    public Page<OrderDTO> search(@RequestParam String param, @RequestParam(defaultValue = "0") Integer page) {
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

    @GetMapping("availableHours")
    public ResponseEntity<MessageResponse> getAvailableHours(@RequestParam LocalDate date) {
        AvailableHours availableHours = orderService.getAvailableHours(date);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("Las horas disponibles se obtuvieron con exito")
            .data(availableHours)
            .build());
    }

    @GetMapping("{id}")
    public ResponseEntity<MessageResponse> getOneById(@PathVariable Long id) {
        OrderDTO order = orderService.findById(id);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El pedido se encontro con exito")
            .data(order)
            .build());
    }

    @PutMapping("{id}/updateReceiverInfo")
    public ResponseEntity<MessageResponse> updateReceiverInfo(@PathVariable Long id, @RequestBody @Valid ReceiverInfoRequest request) {
        OrderDTO order = orderService.findById(id);
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
        OrderDTO orderUpdated = orderService.save(order);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("Los datos de la persona que recoje fueron actualizados")
            .data(orderUpdated)
            .build());
    }

    @PutMapping("{id}/updatePickupInfo")
    public ResponseEntity<MessageResponse> updatePickupInfo(@PathVariable Long id, @RequestBody @Valid PickupInfoRequest request) {
        OrderDTO order = orderService.findById(id);
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
        OrderDTO orderUpdated = orderService.save(order);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("Los datos de recojo fueron actualizados")
            .data(orderUpdated)
            .build());
    }

    @PostMapping("{id}/finalize")
    public ResponseEntity<MessageResponse> postCreate(@PathVariable Long id, @RequestParam(defaultValue = "true") Boolean alert) {
        OrderDTO order = orderService.findById(id);

        if(alert) orderService.alertNewOrder(order);

        orderService.createAndSendInvoice(order, true);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El pedido se notifico y se envió el comprobante con éxito")
            .data(order)
            .build());
    }

    @PostMapping
    public ResponseEntity<MessageResponse> create(@RequestBody @Valid OrderRequest request) {
        ClientDTO client = clientService.findById(request.getClientId());
        LocalDate date = request.getDate() == null ? LocalDate.now() : request.getDate();
        LocalDate maxShipDate = date.plusDays(3);
        ShippingType shippingType = request.getShippingType();
        WarehouseDTO warehouse = null;
        ReceiverInfoDTO receiverInfo = null;
        String department = "";
        String city = "";
        
        if(shippingType == ShippingType.RECOJO_ALMACEN) {
            if(request.getWarehouseId() == null) return ResponseEntity.badRequest().body(MessageResponse
                .builder()
                .message("El id del almacén es requerido")
                .build());

            if(request.getPickupInfo() == null) return ResponseEntity.badRequest().body(MessageResponse
                .builder()
                .message("La fecha y hora de recojo son requeridas")
                .build());

            warehouse = warehouseService.findById(request.getWarehouseId());
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

        OrderDTO order = orderService.save(OrderDTO
            .builder()
            .client(client)
            .invoice(null)
            .department(department)
            .city(city)
            .date(date)
            .pickupInfo(request.getPickupInfo())
            .location(request.getLocation())
            .maxShipDate(maxShipDate)
            .shippingType(request.getShippingType())
            .status(request.getStatus())
            .total(0.0)
            .warehouse(warehouse)
            .evidence(null)
            .receiverInfo(receiverInfo)
            .createdBy(request.getCreatedBy())
            .build());

        if(request.getEmployeeId() != null && request.getEmployeeId() != 1L) {
            EmployeeOperationDTO employeeOperation = EmployeeOperationDTO
                .builder()
                .employeeId(request.getEmployeeId())
                .operation("Creo un pedido")
                .redirectTo("/pedidos/" + order.getId())
                .build();

            employeeOperationService.save(employeeOperation);

            NotificationRequest notiRequest = NotificationRequest
                .builder()
                .userId(request.getEmployeeId())
                .type(NotificationType.NEW_ORDER)
                .redirectTo("/pedidos/" + order.getId())
                .build();

            notiService.sendNotificationToUsersWithPermission(notiRequest, Permission.ORDERS_WATCH, request.getEmployeeId());
        }

        return ResponseEntity.status(201).body(MessageResponse
            .builder()
            .message("El pedido se creo con exito")
            .data(order)
            .build());
    }

    @PutMapping("{id}")
    public ResponseEntity<MessageResponse> update(@PathVariable Long id, @RequestBody @Valid OrderRequest request) {
        OrderDTO order = orderService.findById(id);
        WarehouseDTO warehouse = request.getWarehouseId() == null ? null : warehouseService.findById(request.getWarehouseId());
        LocalDate date = request.getDate();
        LocalDate maxShipDate = date.plusDays(3);
        
        order.setDepartment(request.getDepartment());
        order.setCity(request.getCity());
        order.setStatus(request.getStatus());
        order.setDate(date);
        order.setPickupInfo(request.getPickupInfo());
        order.setMaxShipDate(maxShipDate);
        order.setLocation(request.getLocation());
        order.setShippingType(request.getShippingType());
        order.setWarehouse(warehouse);

        OrderDTO orderUptaded = orderService.save(order);

        if(request.getEmployeeId() != null && request.getEmployeeId() != 1L) {
            EmployeeOperationDTO employeeOperation = EmployeeOperationDTO
                .builder()
                .employeeId(request.getEmployeeId())
                .operation("Actualizo un pedido")
                .redirectTo("/pedidos/" + order.getId())
                .build();

            employeeOperationService.save(employeeOperation);
        }

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El pedido se actualizo con exito")
            .data(orderUptaded)
            .build());
    }

    @PutMapping("{id}/delivered")
    public ResponseEntity<MessageResponse> updateDelivered(@PathVariable Long id, @RequestBody @Valid InvitroDeliveredRequest request) {
        OrderDTO order = orderService.findById(id);

        order.setStatus(Status.ENTREGADO);
        order.setEmployee(employeeService.findById(request.getEmployeeId()));
        order.setEvidence(imageService.findById(request.getEvidenceId()));
        order.setDeliveredAt(LocalDateTime.now());

        orderService.save(order);

        if(request.getEmployeeId() != 1L) {
            EmployeeOperationDTO employeeOperation = EmployeeOperationDTO
                .builder()
                .employeeId(request.getEmployeeId())
                .operation("Entrego el pedido")
                .redirectTo("/pedidos/" + order.getId())
                .build();

            employeeOperationService.save(employeeOperation);
        }

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El estado del pedido se actualizo con exito")
            .data(order)
            .build());
    }

    @PutMapping("{id}/agency")
    public ResponseEntity<MessageResponse> updateAgency(@PathVariable Long id, @RequestBody @Valid InvitroDeliveredRequest request) {
        OrderDTO order = orderService.findById(id);
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
                .redirectTo("/pedidos/" + order.getId())
                .build();

            employeeOperationService.save(employeeOperation);
        }

        if(order.getClient().getUserId() != null) {
            NotificationRequest notification = NotificationRequest
                .builder()
                .userId(order.getClient().getUserId())
                .type(NotificationType.ORDER_AT_AGENCY)
                .redirectTo("/perfil/pedidos/" + order.getId())
                .build();
    
            notiService.create(notification);
        }

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El estado del pedido se actualizo con exito")
            .data(order)
            .build());
    }

    @PutMapping("{id}/status")
    public ResponseEntity<MessageResponse> updateStatus(@PathVariable Long id, @RequestBody @Valid UpdateOrderRequest request) {
        OrderDTO order = orderService.findById(id);
        order.setStatus(request.getStatus());

        if(request.getStatus() == Status.PAGADO) {
            order.setPaymentType(request.getPaymentType());
            orderService.orderPaid(order);
        }else if(request.getStatus() == Status.CANCELADO) {
            OrderDTO orderUpdated = orderService.cancelOrder(order);
            return ResponseEntity.ok().body(MessageResponse
                .builder()
                .message("El pedido fue cancelado")
                .data(orderUpdated)
                .build());
        }

        OrderDTO orderUpdated = orderService.save(order);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El estado del pedido se actualizo con exito")
            .data(orderUpdated)
            .build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable Long id) {
        OrderDTO order = orderService.findById(id);
        Status orderStatus = order.getStatus();

        if(orderStatus == Status.ENTREGADO || orderStatus == Status.PAGADO) {
            return ResponseEntity.status(400).body(MessageResponse
                .builder()
                .message("No puedes eliminar el pedido una vez que ya esté" + orderStatus.toString().toLowerCase())
                .build());
        }

        orderService.delete(order.getId());

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El pedido se elimino con exito")
            .build());
    }

    @PostMapping("{id}/generate-invoice")
    public ResponseEntity<MessageResponse> generateInvoice(@PathVariable Long id) {
        OrderDTO order = orderService.findById(id);
        if(order.getStatus() != Status.PAGADO) {
            return ResponseEntity.status(400).body(MessageResponse
                .builder()
                .message("El pedido debe estar pagado para generar el comprobante")
                .build());
        }

        Long invoiceId = orderService.createAndSendInvoice(order, false);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El pedido se notifico y se envió el comprobante con éxito")
            .data(Map.of("invoiceId", invoiceId))
            .build());
    }
}
