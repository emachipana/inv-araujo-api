package com.inversionesaraujo.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inversionesaraujo.api.business.payload.MessageResponse;
import com.inversionesaraujo.api.business.service.IStripe;
import com.stripe.exception.StripeException;

@RestController
@RequestMapping("/api/v1/payments")
public class StripeController {
    @Autowired
    private IStripe stripeService;

    @PostMapping("create-intent")
    public ResponseEntity<MessageResponse> createIntent(@RequestParam Double amount) {
		try {
			String clientSecret = stripeService.createPaymentIntent(amount);

			return ResponseEntity.status(201).body(MessageResponse
				.builder()
				.message("El intent de pago se creó con éxito")
				.data(clientSecret)
				.build());
		}catch (StripeException error) {
			return ResponseEntity.status(406).body(MessageResponse
				.builder()
				.message(error.getMessage())
				.build());
		}
    }
}
