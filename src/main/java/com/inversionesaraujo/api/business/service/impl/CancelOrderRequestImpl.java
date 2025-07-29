package com.inversionesaraujo.api.business.service.impl;

import java.util.List;
import java.time.Month;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.inversionesaraujo.api.business.dto.CancelOrderRequestDTO;
import com.inversionesaraujo.api.business.service.ICancelOrderRequest;
import com.inversionesaraujo.api.model.CancelOrderRequest;
import com.inversionesaraujo.api.model.Client;
import com.inversionesaraujo.api.model.Order;
import com.inversionesaraujo.api.model.Profit;
import com.inversionesaraujo.api.model.Status;
import com.inversionesaraujo.api.repository.CancelOrderRequestRepository;
import com.inversionesaraujo.api.repository.ClientRepository;
import com.inversionesaraujo.api.repository.OrderRepository;
import com.inversionesaraujo.api.repository.ProfitRepository;
import com.inversionesaraujo.api.business.dto.OrderProductDTO;
import com.inversionesaraujo.api.business.dto.ProductDTO;

import jakarta.transaction.Transactional;

@Service
public class CancelOrderRequestImpl implements ICancelOrderRequest {
    @Autowired
    private CancelOrderRequestRepository repo;
    @Autowired
    private ClientRepository clientRepo;
    @Autowired
    private ProfitRepository profitRepo;
    @Autowired
    private OrderProductImpl orderProductService;
    @Autowired
    private ProductImpl productService;
    @Autowired
    private OrderRepository orderRepo;

    @Transactional
    @Override
    public CancelOrderRequestDTO save(CancelOrderRequestDTO cancelOrderRequestDTO) {
        CancelOrderRequest cancelOrderRequestSaved = repo.save(CancelOrderRequestDTO.toEntity(cancelOrderRequestDTO));

        return CancelOrderRequestDTO.toDTO(cancelOrderRequestSaved);
    }

    @Transactional
    @Override
    public boolean acceptRequest(Long id) {
        CancelOrderRequest cancelOrderRequest = repo.findById(id).orElseThrow(() -> new DataAccessException("La solicitud de cancelacion no existe") {});
        Order order = cancelOrderRequest.getOrder();
        Client client = order.getClient();

        client.setConsumption(client.getConsumption() - order.getTotal());
        clientRepo.save(client);

        Month month = order.getDate().getMonth();
        Profit profit = profitRepo.findByMonth(month.toString());
        Double income = (profit.getIncome() - order.getTotal());
        profit.setIncome(income);
        profit.setProfit(income - profit.getTotalExpenses());
        profitRepo.save(profit);

        List<OrderProductDTO> products = orderProductService.findByOrderId(order.getId());
        
        for(OrderProductDTO item : products) {
            ProductDTO product = item.getProduct();
            Integer firstStock = product.getStock() + item.getQuantity();

            product.setStock(firstStock);
            productService.save(product);
        }

        order.setStatus(Status.CANCELADO);
        orderRepo.save(order);

        cancelOrderRequest.setAccepted(true);
        repo.save(cancelOrderRequest);

        return true;
    }

    @Override
    public boolean rejectRequest(Long id) {
        CancelOrderRequest cancelOrderRequest = repo.findById(id).orElseThrow(() -> new DataAccessException("La solicitud de cancelacion no existe") {});

        cancelOrderRequest.setRejected(true);

        repo.save(cancelOrderRequest);

        return true;
    }

    @Override
    public CancelOrderRequestDTO findById(Long id) {
        CancelOrderRequest cancelOrderRequest = repo.findById(id).orElseThrow(() -> new DataAccessException("La solicitud de cancelacion no existe") {});

        return CancelOrderRequestDTO.toDTO(cancelOrderRequest);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

    @Override
    public List<CancelOrderRequestDTO> findByOrderId(Long orderId) {
        List<CancelOrderRequest> cancelOrderRequests = repo.findByOrderId(orderId);

        return CancelOrderRequestDTO.toListDTO(cancelOrderRequests);
    }    
}
