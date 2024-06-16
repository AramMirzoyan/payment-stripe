package com.payment.stripe.payment.stripe.dto;

import lombok.Builder;

@Builder
public record PaymentInfoResponse(
        double earlyBirdPrice,
        double usualPrice,
        int availableCount,
        int earlyBirdCount,
        int pageLimitTrial,
        int pageLimitPremium,
        int trialDocCount,
        int premiumDocCount) {}
