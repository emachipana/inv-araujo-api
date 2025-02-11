package com.inversionesaraujo.api.controller;

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

import com.inversionesaraujo.api.business.dto.OrderVarietyDTO;
import com.inversionesaraujo.api.business.dto.VarietyDTO;
import com.inversionesaraujo.api.business.dto.VitroOrderDTO;
import com.inversionesaraujo.api.business.payload.MessageResponse;
import com.inversionesaraujo.api.business.request.OrderVarietyRequest;
import com.inversionesaraujo.api.business.service.IOrderVariety;
import com.inversionesaraujo.api.business.service.IVariety;
import com.inversionesaraujo.api.business.service.IVitroOrder;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/orderVarieties")
public class OrderVarietyController {
    @Autowired
    private IOrderVariety itemService;
    @Autowired
    private IVitroOrder orderService;
    @Autowired
    private IVariety varietyService;

    @GetMapping("{id}")
    public ResponseEntity<MessageResponse> getOneById(@PathVariable Long id) {
        OrderVarietyDTO item = itemService.findById(id);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El item del pedido invitro se encontro con exito")
            .data(item)
            .build());
    }

    @PostMapping
    public ResponseEntity<MessageResponse> create(@RequestBody @Valid OrderVarietyRequest request) {
        VitroOrderDTO order = orderService.findById(request.getVitroOrderId());
        VarietyDTO variety = varietyService.findById(request.getVarietyId());
        Double subTotal = request.getPrice() * request.getQuantity();
        OrderVarietyDTO item = itemService.save(OrderVarietyDTO
            .builder()
            .vitroOrderId(request.getVitroOrderId())
            .variety(variety)
            .price(request.getPrice())
            .quantity(request.getQuantity())
            .subTotal(subTotal)
            .build());

        Double total = order.getTotal() + subTotal;
        order.setTotal(total);
        order.setPending(total - order.getTotalAdvance());
        orderService.save(order);
        
        return ResponseEntity.status(201).body(MessageResponse
            .builder()
            .message("El item del pedido invitro se creo con exito")
            .data(item)
            .build());
    }

    @PutMapping("{id}")
    public ResponseEntity<MessageResponse> update(@PathVariable Long id, @RequestBody @Valid OrderVarietyRequest request) {
        OrderVarietyDTO item = itemService.findById(id);
        Double subTotal = request.getPrice() * request.getQuantity();
        Double oldSubTotal =item.getSubTotal();
        VitroOrderDTO order = orderService.findById(item.getVitroOrderId());
        item.setPrice(request.getPrice());
        item.setQuantity(request.getQuantity());
        item.setSubTotal(subTotal);
        OrderVarietyDTO itemUpdated = itemService.save(item);

        Double total = (order.getTotal() - oldSubTotal) + subTotal;
        order.setTotal(total);
        order.setPending(total - order.getTotalAdvance());
        orderService.save(order);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El item del pedido invitro se actualizo con exito")
            .data(itemUpdated)
            .build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable Long id) {
        OrderVarietyDTO item = itemService.findById(id);
        Double oldSubTotal = item.getSubTotal();
        VitroOrderDTO order = orderService.findById(item.getVitroOrderId());
        itemService.delete(id);

        Double total = order.getTotal() - oldSubTotal;
        order.setTotal(total);
        order.setPending(total - order.getTotalAdvance());
        orderService.save(order);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El item del pedido invitro se elimino con exito")
            .build());
    }
}
