package com.smartapply.smart_apply.exception;

import lombok.Getter;

@Getter
public class SmartApplyException extends RuntimeException {

    private final SmartApplyErrorMessage error;

    public SmartApplyException(SmartApplyErrorMessage error) {
        super(error.getErrorMessage());
        this.error = error;
    }
}