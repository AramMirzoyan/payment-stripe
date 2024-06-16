package com.payment.stripe.payment.stripe.events;

import com.payment.stripe.payment.stripe.entity.PaymentStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class PaymentEvent {
    private final UUID userId;

    private final PaymentStatus paymentStatus;

    private final ListenersEventType eventType;
}
