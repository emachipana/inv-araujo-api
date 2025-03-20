package com.inversionesaraujo.api.business.payload;

import java.util.List;

import com.inversionesaraujo.api.business.dto.OrderDTO;
import com.inversionesaraujo.api.business.dto.ProductDTO;
import com.inversionesaraujo.api.business.dto.VitroOrderDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class MobileOnHomeResponse {
	private List<OrderDTO> orders;
	private List<VitroOrderDTO> vitroOrders;
	private Long totalDeliver;
	private List<ProductDTO> lowProductsStock;
}
