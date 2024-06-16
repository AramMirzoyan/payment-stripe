package com.payment.stripe.payment.stripe.exception;

public class CreatedPaymentUserMismatchException extends   BaseException {
    public CreatedPaymentUserMismatchException() {
        super(ErrorCode.CREATED_PAYMENT_USER_MISMATCH);
    }
}
