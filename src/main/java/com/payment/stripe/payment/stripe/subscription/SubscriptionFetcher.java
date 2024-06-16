package com.payment.stripe.payment.stripe.subscription;

import com.payment.stripe.payment.stripe.entity.PaymentStatus;
import com.payment.stripe.payment.stripe.entity.UserSubscriptionEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;

@Component
public class SubscriptionFetcher {

    public UserSubscriptionEntity fetch(final UUID userID){
        return   UserSubscriptionEntity .builder()
                .userId(userID)
                .paymentStatus(PaymentStatus.PENDING)
                .endDate(LocalDate.now())
                .build()
                ;
    }

}
