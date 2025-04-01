package com.inversionesaraujo.api.business.service.impl;

import org.springframework.beans.factory.annotation.Value;

import com.inversionesaraujo.api.business.service.IStripe;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import org.springframework.stereotype.Service;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

@Service
public class StripeImpl implements IStripe {
    @Value("${stripe.secret.key}")
    private String secretKey;

    @Override
    public String createPaymentIntent(Double amount) throws StripeException {
        Stripe.apiKey = secretKey;
		System.out.println(amount);

        PaymentIntentCreateParams params = PaymentIntentCreateParams
			.builder()
			.setAmount(Math.round((amount * 100)))
			.setCurrency("pen")
			.setAutomaticPaymentMethods(
				PaymentIntentCreateParams.AutomaticPaymentMethods
				.builder()
				.setEnabled(true)
				.build()
			)
			.build();

        PaymentIntent intent = PaymentIntent.create(params);

		return intent.getClientSecret();
    }
}
