package com.payment.stripe.payment.stripe.exception;

public class StripePaymentSessionNotFoundException extends BaseException {
    public StripePaymentSessionNotFoundException() {
        super(ErrorCode.STRIPE_PAYMENT_SESSION_NOT_FOUND);
    }
}
