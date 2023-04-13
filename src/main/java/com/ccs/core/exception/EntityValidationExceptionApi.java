package com.ccs.core.exception;

import lombok.Getter;
import org.springframework.validation.BindingResult;

@Getter
public class EntityValidationExceptionApi extends ApiServiceException {

    private final BindingResult bindingResult;

    public EntityValidationExceptionApi(String message, BindingResult bindingResult) {
        super(message);
        this.bindingResult = bindingResult;
    }

    public EntityValidationExceptionApi(String message, Throwable cause, BindingResult bindingResult) {
        super(message, cause);
        this.bindingResult = bindingResult;
    }

}
