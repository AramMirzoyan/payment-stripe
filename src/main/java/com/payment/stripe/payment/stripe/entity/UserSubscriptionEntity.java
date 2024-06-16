package com.payment.stripe.payment.stripe.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
public class UserSubscriptionEntity {
    private UUID userId;
    private PaymentStatus paymentStatus;
    private LocalDate endDate;
}
