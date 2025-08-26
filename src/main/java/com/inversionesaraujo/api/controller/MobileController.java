package com.inversionesaraujo.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inversionesaraujo.api.business.dto.OrderDTO;
import com.inversionesaraujo.api.business.dto.ProductDTO;
import com.inversionesaraujo.api.business.dto.VitroOrderDTO;
import com.inversionesaraujo.api.business.payload.MessageResponse;
import com.inversionesaraujo.api.business.payload.MobileOnHomeResponse;
import com.inversionesaraujo.api.business.payload.TotalDeliverResponse;
import com.inversionesaraujo.api.business.service.IOrder;
import com.inversionesaraujo.api.business.service.IProduct;
import com.inversionesaraujo.api.business.service.IVitroOrder;
import com.inversionesaraujo.api.model.ShippingType;
import com.inversionesaraujo.api.model.SortBy;
import com.inversionesaraujo.api.model.SortDirection;
import com.inversionesaraujo.api.model.Status;

@RestController
@RequestMapping("/api/v1/mobile")
public class MobileController {
	@Autowired 
	private IOrder orderService;
	@Autowired
	private IVitroOrder vitroOrderService;
	@Autowired
	private IProduct productService;

	@GetMapping("/onHome")
	public ResponseEntity<MessageResponse> loadOnHome() {
		Page<OrderDTO> orders = orderService.listAll(
			Status.PENDIENTE, 0, 5, SortDirection.DESC,
			null, SortBy.maxShipDate, ShippingType.RECOJO_ALMACEN,
			null, null, null, null, null);

		Page<VitroOrderDTO> vitroOrders = vitroOrderService.listAll(
			null, 0, 5, SortDirection.DESC,
			null, Status.PENDIENTE, SortBy.finishDate, 
			ShippingType.RECOJO_ALMACEN, true, null,
			null, null, null);

		TotalDeliverResponse orderTotal = orderService.totalDeliver();
		TotalDeliverResponse vitroTotal = vitroOrderService.totalDeliver();

		Page<ProductDTO> products = productService.filterProducts(
			null, null, null, 0,
			4, null, null, null,
			3, false, false);

		MobileOnHomeResponse response = MobileOnHomeResponse
			.builder()
			.orders(orders.getContent())
			.vitroOrders(vitroOrders.getContent())
			.totalDeliver(orderTotal.getTotal() + vitroTotal.getTotal())
			.lowProductsStock(products.getContent())
			.build();

		return ResponseEntity.ok().body(MessageResponse
			.builder()
			.data(response)
			.message("Datos obtenidos correctamente")
			.build());
	}
}
