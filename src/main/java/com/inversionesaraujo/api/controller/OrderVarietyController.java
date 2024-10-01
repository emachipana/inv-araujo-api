package com.inversionesaraujo.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inversionesaraujo.api.model.entity.OrderVariety;
import com.inversionesaraujo.api.model.entity.Variety;
import com.inversionesaraujo.api.model.entity.VitroOrder;
import com.inversionesaraujo.api.model.payload.MessageResponse;
import com.inversionesaraujo.api.model.request.OrderVarietyRequest;
import com.inversionesaraujo.api.service.IOrderVariety;
import com.inversionesaraujo.api.service.IVariety;
import com.inversionesaraujo.api.service.IVitroOrder;

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
    public ResponseEntity<MessageResponse> getOneById(@PathVariable Integer id) {
        try {
            OrderVariety item = itemService.findById(id);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El item del pedido invitro se encontro con exito")
                .data(item)
                .build(), HttpStatus.OK);
        }catch(Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<MessageResponse> create(@RequestBody OrderVarietyRequest request) {
        try {
            VitroOrder order = orderService.findById(request.getVitroOrderId());
            Variety variety = varietyService.findById(request.getVarietyId());
            Double subTotal = request.getPrice() * request.getQuantity();
            OrderVariety item = itemService.save(OrderVariety
                .builder()
                .vitroOrder(order)
                .variety(variety)
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .subTotal(subTotal)
                .build());

            Double total = order.getTotal() + subTotal;
            order.setTotal(total);
            order.setPending(total - order.getTotalAdvance());
            orderService.save(order);
            
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El item del pedido invitro se creo con exito")
                .data(item)
                .build(), HttpStatus.CREATED);
        }catch(Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<MessageResponse> update(@PathVariable Integer id, @RequestBody OrderVarietyRequest request) {
        try {
            OrderVariety item = itemService.findById(id);
            Double subTotal = request.getPrice() * request.getQuantity();
            Double oldSubTotal =item.getSubTotal();
            VitroOrder order = item.getVitroOrder();
            item.setPrice(request.getPrice());
            item.setQuantity(request.getQuantity());
            item.setSubTotal(subTotal);
            OrderVariety itemUpdated = itemService.save(item);

            Double total = (order.getTotal() - oldSubTotal) + subTotal;
            order.setTotal(total);
            order.setPending(total - order.getTotalAdvance());
            orderService.save(order);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El item del pedido invitro se actualizo con exito")
                .data(itemUpdated)
                .build(), HttpStatus.OK);
        }catch(Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable Integer id) {
        try {
            OrderVariety item = itemService.findById(id);
            Double oldSubTotal = item.getSubTotal();
            VitroOrder order = item.getVitroOrder();
            itemService.delete(item);

            Double total = order.getTotal() - oldSubTotal;
            order.setTotal(total);
            order.setPending(total - order.getTotalAdvance());
            orderService.save(order);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El item del pedido invitro se elimino con exito")
                .build(), HttpStatus.OK);
        }catch(Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.NOT_FOUND);
        }
    }
}
