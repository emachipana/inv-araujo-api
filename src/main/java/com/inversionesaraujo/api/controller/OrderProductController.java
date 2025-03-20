package com.inversionesaraujo.api.controller;

import java.time.Month;
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

import com.inversionesaraujo.api.business.dto.ClientDTO;
import com.inversionesaraujo.api.business.dto.OrderDTO;
import com.inversionesaraujo.api.business.dto.OrderProductDTO;
import com.inversionesaraujo.api.business.dto.ProductDTO;
import com.inversionesaraujo.api.business.dto.ProfitDTO;
import com.inversionesaraujo.api.business.payload.MessageResponse;
import com.inversionesaraujo.api.business.request.OrderProductRequest;
import com.inversionesaraujo.api.business.service.IClient;
import com.inversionesaraujo.api.business.service.IOrder;
import com.inversionesaraujo.api.business.service.IOrderProduct;
import com.inversionesaraujo.api.business.service.IProduct;
import com.inversionesaraujo.api.business.service.IProfit;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/orderProducts")
public class OrderProductController {
    @Autowired
    private IOrderProduct itemService;
    @Autowired
    private IOrder orderService;
    @Autowired
    private IProduct productService;
    @Autowired
    private IProfit profitService;
    @Autowired
    private IClient clientService;

    @GetMapping("{id}")
    public ResponseEntity<MessageResponse> getOneById(@PathVariable Long id) {
        OrderProductDTO item = itemService.findById(id);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El item del pedido se encontro con exito")
            .data(item)
            .build());
    }

    @GetMapping("order/{id}")
    public List<OrderProductDTO> getAllByOrder(@PathVariable Long id) {
        return itemService.findByOrderId(id);
    }

    @PostMapping
    public ResponseEntity<MessageResponse> create(@RequestBody @Valid OrderProductRequest request) {
        OrderDTO order = orderService.findById(request.getOrderId());
        ClientDTO client = order.getClient();
        ProductDTO product = productService.findById(request.getProductId());
        if(request.getQuantity() > product.getStock()) {
            return ResponseEntity.status(406).body(MessageResponse
                .builder()
                .message("No hay suficiente stock")
                .build());
        }

        Double price = product.getPrice();
        if(product.getDiscount() != null) price = product.getDiscount().getPrice();
        Double subTotal = price * request.getQuantity();

        OrderProductDTO newItem = itemService.save(OrderProductDTO
            .builder()
            .orderId(order.getId())
            .product(product)
            .quantity(request.getQuantity())
            .subTotal(subTotal)
            .price(price)
            .build());

        Double total = order.getTotal() + subTotal;
        order.setTotal(total);
        orderService.save(order);

        client.setConsumption(client.getConsumption() + subTotal);
        clientService.save(client);

        Month month = order.getDate().getMonth();
        ProfitDTO profit = profitService.findByMonth(month.toString());
        if(profit == null) {
            profitService.save(ProfitDTO.builder()
                .date(order.getDate())
                .profit(subTotal)
                .income(subTotal)
                .month(month.toString())
                .build()
            );
        }else {
            Double income = profit.getIncome() + subTotal;
            profit.setIncome(income);
            profit.setProfit(income - profit.getTotalExpenses());
            profitService.save(profit);
        }

        product.setStock(product.getStock() - request.getQuantity());
        productService.save(product);

        return ResponseEntity.status(201).body(MessageResponse
            .builder()
            .message("El item del pedido se creo con exito")
            .data(newItem)
            .build());
    }

    @PutMapping("{id}")
    public ResponseEntity<MessageResponse> update(@PathVariable Long id, @RequestBody @Valid OrderProductRequest request) {
        OrderProductDTO item = itemService.findById(id);
        ProductDTO product = productService.findById(request.getProductId());
        Integer firstStock = product.getStock() + item.getQuantity();
        if(request.getQuantity() > firstStock) {
            return ResponseEntity.status(406).body(MessageResponse
                .builder()
                .message("No hay suficiente stock")
                .build());
        }

        Double price = product.getPrice();
        if(product.getDiscount() != null) price = product.getDiscount().getPrice();
        Double subTotal = price * request.getQuantity();
        Double oldSubTotal = item.getSubTotal();

        item.setProduct(product);
        item.setPrice(price);
        item.setQuantity(request.getQuantity());
        item.setSubTotal(subTotal);
        OrderProductDTO itemUpdated = itemService.save(item);

        OrderDTO order = orderService.findById(item.getOrderId());
        Double total = (order.getTotal() - oldSubTotal) + subTotal;
        ClientDTO client = order.getClient();
        order.setTotal(total);
        orderService.save(order);

        client.setConsumption((client.getConsumption() - oldSubTotal) + subTotal);
        clientService.save(client);

        Month month = order.getDate().getMonth();
        ProfitDTO profit = profitService.findByMonth(month.toString());
        Double income = (profit.getIncome() - oldSubTotal) + subTotal;
        profit.setIncome(income);
        profit.setProfit(income - profit.getTotalExpenses());
        profitService.save(profit);

        product.setStock(firstStock - request.getQuantity());
        productService.save(product);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El item del pedido se actualizo correctamente")
            .data(itemUpdated)
            .build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable Long id) {
        OrderProductDTO item = itemService.findById(id);
        Double subTotal = item.getSubTotal();
        OrderDTO order = orderService.findById(item.getOrderId());
        ProductDTO product = item.getProduct();
        Integer firstStock = product.getStock() + item.getQuantity();
        itemService.delete(id);
        Double oldSubTotal = order.getTotal() - subTotal;
        ClientDTO client = order.getClient();
        order.setTotal(oldSubTotal);
        orderService.save(order);

        client.setConsumption(client.getConsumption() - subTotal);
        clientService.save(client);

        Month month = order.getDate().getMonth();
        ProfitDTO profit = profitService.findByMonth(month.toString());
        Double income = (profit.getIncome() - subTotal);
        profit.setIncome(income);
        profit.setProfit(income - profit.getTotalExpenses());
        profitService.save(profit);

        product.setStock(firstStock);
        productService.save(product);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El item del pedido se elimino con exito")
            .build());
    }
}
