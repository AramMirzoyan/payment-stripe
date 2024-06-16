package com.payment.stripe.payment.stripe.config;

import com.stripe.Stripe;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StripeConfig {

    private final StripeKeys keys;

    @PostConstruct
    void init() {
        Stripe.apiKey = keys.sk;
    }
}
