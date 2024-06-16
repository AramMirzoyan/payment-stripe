package com.payment.stripe.payment.stripe.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BaseException  extends RuntimeException {
    private static final String PREFIX_ERROR = "[ERROR]";
    private static final String PREFIX_INFO = "[INFO]";
    private static final String PREFIX_WARN = "[WARNING]";
    @Getter
    private final ErrorCode errorCode;
    private final String messageKey;
    @Getter private String debug;


    protected BaseException(final ErrorCode errorCode) {
        super(errorCode.getMessageKey());
        this.errorCode = errorCode;
        this.messageKey = errorCode.getMessageKey();
    }

    protected BaseException(final ErrorCode errorCode, final String debug) {
        super(errorCode.getMessageKey());
        this.errorCode = errorCode;
        this.messageKey = errorCode.getMessageKey();
        this.debug = debug;
    }

}
