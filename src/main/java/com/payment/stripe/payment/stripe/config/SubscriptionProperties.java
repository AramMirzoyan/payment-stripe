package com.payment.stripe.payment.stripe.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "application.payment.subscription-limits")
@Getter
@Setter
public class SubscriptionProperties {
    private int earlyBirdLimit;
    private double earlyBirdPrice;
    private double usualPrice;
    private int pageLimitTrial;
    private int pageLimitSubscribed;
    private int trialDocCount;
    private int subscribedDocCount;
}
