package com.payment.stripe.payment.stripe.service;

import com.payment.stripe.payment.stripe.dto.PaymentInfoResponse;
import com.payment.stripe.payment.stripe.dto.PaymentResponse;
import com.payment.stripe.payment.stripe.entity.TotalPayedSubscriptionsCountEntity;

public interface PaymentService {
    PaymentInfoResponse getInfo();

    TotalPayedSubscriptionsCountEntity incrementTotalPayedCount();

    PaymentResponse pay();

    void onSuccess(String sessionId);

    void onCancel(String sessionId);

}
