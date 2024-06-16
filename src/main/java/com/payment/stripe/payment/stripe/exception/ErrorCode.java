package com.payment.stripe.payment.stripe.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    STRIPE_PAYMENT_SESSION_NOT_FOUND(
            "NOT_FOUND",
            "We're unable to retrieve your payment details. Please begin the payment process again.",
            "MAPI_E_01"),
    CREATED_PAYMENT_USER_MISMATCH(
            "BAD_REQUEST", "Payment created user not match to logged in user.", "MAPI_E_02"),

    UNABLE_TO_CREATE_STRIPE_SESSION(
            "CONFLICT",
            "We're having trouble processing your checkout. Please try again later.",
            "MAPI_E_03");


    private final String httpStatus;
    private final String messageKey;
    private final String errorCode;
}
