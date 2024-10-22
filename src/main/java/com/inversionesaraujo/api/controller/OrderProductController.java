package com.inversionesaraujo.api.controller;

import java.time.Month;

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

import com.inversionesaraujo.api.model.entity.Admin;
import com.inversionesaraujo.api.model.entity.Order;
import com.inversionesaraujo.api.model.entity.OrderProduct;
import com.inversionesaraujo.api.model.entity.Product;
import com.inversionesaraujo.api.model.entity.Profit;
import com.inversionesaraujo.api.model.payload.MessageResponse;
import com.inversionesaraujo.api.model.request.OrderProductRequest;
import com.inversionesaraujo.api.service.IAdmin;
import com.inversionesaraujo.api.service.IOrder;
import com.inversionesaraujo.api.service.IOrderProduct;
import com.inversionesaraujo.api.service.IProduct;
import com.inversionesaraujo.api.service.IProfit;

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
    private IAdmin adminService;

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

            Double total = order.getTotal() + subTotal;
            order.setTotal(total);
            orderService.save(order);

            Month month = order.getDate().getMonth();
            Profit profit = profitService.findByMonth(month.toString());
            if(profit == null) {
                String email = System.getenv("ADMIN_EMAIL");
                Admin admin = adminService.findByEmail(email);
                profitService.save(Profit.builder()
                    .admin(admin)
                    .date(order.getDate())
                    .profit(total)
                    .income(total)
                    .month(month.toString())
                    .build()
                );
            }else {
                Double income = profit.getIncome() + total;
                profit.setIncome(income);
                profit.setProfit(income - profit.getTotalExpenses());
                profitService.save(profit);
            }

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
            Double total = (order.getTotal() - oldSubTotal) + subTotal;
            order.setTotal(total);
            orderService.save(order);

            Month month = order.getDate().getMonth();
            Profit profit = profitService.findByMonth(month.toString());
            Double income = (profit.getIncome() - oldSubTotal) + total;
            profit.setIncome(income);
            profit.setProfit(income - profit.getTotalExpenses());
            profitService.save(profit);

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
            Double oldSubTotal = order.getTotal() - subTotal;
            order.setTotal(oldSubTotal);
            orderService.save(order);

            Month month = order.getDate().getMonth();
            Profit profit = profitService.findByMonth(month.toString());
            Double income = (profit.getIncome() - oldSubTotal);
            profit.setIncome(income);
            profit.setProfit(income - profit.getTotalExpenses());
            profitService.save(profit);

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
