package com.payment.stripe.payment.stripe.exception;

import com.stripe.exception.StripeException;

public class StripeSessionCreationExceptionException  extends BaseException {
    public StripeSessionCreationExceptionException() {
        super(ErrorCode.UNABLE_TO_CREATE_STRIPE_SESSION);
    }
}

