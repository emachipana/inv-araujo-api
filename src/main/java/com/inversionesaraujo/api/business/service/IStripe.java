package com.inversionesaraujo.api.business.service;

import com.stripe.exception.StripeException;

public interface IStripe {
    String createPaymentIntent(Double amount) throws StripeException;
}
