package com.inversionesaraujo.api.utils;

import java.util.List;

import com.inversionesaraujo.api.business.dto.OrderDTO;
import com.inversionesaraujo.api.business.dto.VitroOrderDTO;
import com.inversionesaraujo.api.business.payload.OrderDataResponse;
import com.inversionesaraujo.api.model.Status;

public class OrderData {
    public static OrderDataResponse filterData(List<OrderDTO> orders, List<VitroOrderDTO> vitroOrders) {
        int pen = 0;
        int ship = 0;
        int size = orders == null ? vitroOrders.size() : orders.size();

        for(int i = 0; i < size; i++) {
            Status status = orders == null ? vitroOrders.get(i).getStatus() : orders.get(i).getStatus();

            if(status == Status.ENTREGADO) ship++;
            if(status == Status.PENDIENTE) pen ++;
        }

        return OrderDataResponse
            .builder()
            .pen(pen)
            .ship(ship)
            .build();
    }     
}
