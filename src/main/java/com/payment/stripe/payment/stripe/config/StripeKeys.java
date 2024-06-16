package com.payment.stripe.payment.stripe.config;

import com.stripe.Stripe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StripeKeys {

    public final String pk;
    public final String sk;
    public final String priceId;
    public final String earlyBirdPriceId;
    public final String successURL;
    public final String cancelURL;
    public final String webhookSecret;


    public StripeKeys(
            @Value("${application.stripe.pk}") final String pk,
            @Value("${application.stripe.sk}") final String sk,
            @Value("${application.stripe.price-id}") final String priceId,
            @Value("${application.stripe.early-bird-price-id}") final String earlyBirdPriceId,
            @Value("${application.stripe.successURL}") final String successURL,
            @Value("${application.stripe.cancelURL}") final String cancelURL,
            @Value("${application.stripe.webhook-secret}") final String webhookSecret) {
        this.pk = pk;
        this.sk = sk;
        this.priceId = priceId;
        this.earlyBirdPriceId = earlyBirdPriceId;
        this.successURL = successURL;
        this.cancelURL = cancelURL;
        this.webhookSecret = webhookSecret;
        Stripe.apiKey = sk;
    }
}
