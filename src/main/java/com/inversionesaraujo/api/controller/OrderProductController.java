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

import com.inversionesaraujo.api.model.entity.Order;
import com.inversionesaraujo.api.model.entity.OrderProduct;
import com.inversionesaraujo.api.model.entity.Product;
import com.inversionesaraujo.api.model.payload.MessageResponse;
import com.inversionesaraujo.api.model.request.OrderProductRequest;
import com.inversionesaraujo.api.service.IOrder;
import com.inversionesaraujo.api.service.IOrderProduct;
import com.inversionesaraujo.api.service.IProduct;

@RestController
@RequestMapping("/api/v1/orderProducts")
public class OrderProductController {
    @Autowired
    private IOrderProduct itemService;
    @Autowired
    private IOrder orderService;
    @Autowired
    private IProduct productService;

    @GetMapping("{id}")
    public ResponseEntity<MessageResponse> getOneById(@PathVariable Integer id) {
        try {
            OrderProduct item = itemService.findById(id);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El item del pedido se encontro con exito")
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
    public ResponseEntity<MessageResponse> create(@RequestBody OrderProductRequest request) {
        try {
            Order order = orderService.findById(request.getOrderId());
            Product product = productService.findById(request.getProductId());
            if(request.getQuantity() > product.getStock()) {
                return new ResponseEntity<>(MessageResponse
                    .builder()
                    .message("No hay suficiente stock")
                    .build(), HttpStatus.NOT_ACCEPTABLE);
            }

            Double price = product.getPrice();
            if(product.getDiscount() != null) price = product.getDiscount().getPrice();
            Double subTotal = price * request.getQuantity();

            OrderProduct newItem = itemService.save(OrderProduct
                .builder()
                .order(order)
                .product(product)
                .quantity(request.getQuantity())
                .subTotal(subTotal)
                .price(price)
                .build());

            order.setTotal(order.getTotal() + subTotal);
            orderService.save(order);

            product.setStock(product.getStock() - request.getQuantity());
            productService.save(product);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El item del pedido se creo con exito")
                .data(newItem)
                .build(), HttpStatus.CREATED);
        }catch(Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<MessageResponse> update(@PathVariable Integer id, @RequestBody OrderProductRequest request) {
        try {
            OrderProduct item = itemService.findById(id);
            Product product = productService.findById(request.getProductId());
            Integer firstStock = product.getStock() + item.getQuantity();
            if(request.getQuantity() > firstStock) {
                return new ResponseEntity<>(MessageResponse
                    .builder()
                    .message("No hay suficiente stock")
                    .build(), HttpStatus.NOT_ACCEPTABLE);
            }

            Double price = product.getPrice();
            if(product.getDiscount() != null) price = product.getDiscount().getPrice();
            Double subTotal = price * request.getQuantity();
            Double oldSubTotal = item.getSubTotal();

            item.setProduct(product);
            item.setPrice(price);
            item.setQuantity(request.getQuantity());
            item.setSubTotal(subTotal);
            OrderProduct itemUpdated = itemService.save(item);

            Order order = item.getOrder();
            order.setTotal((order.getTotal() - oldSubTotal) + subTotal);
            orderService.save(order);

            product.setStock(firstStock - request.getQuantity());
            productService.save(product);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El item del pedido se actualizo correctamente")
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
            OrderProduct item = itemService.findById(id);
            Double subTotal = item.getSubTotal();
            Order order = item.getOrder();
            Product product = item.getProduct();
            Integer firstStock = product.getStock() + item.getQuantity();
            itemService.delete(item);
            order.setTotal(order.getTotal() - subTotal);
            orderService.save(order);
            product.setStock(firstStock);
            productService.save(product);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El item del pedido se elimino con exito")
                .build(), HttpStatus.OK);
        }catch(Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.NOT_FOUND);   
        }
    }
}
