package com.lucasmartins.common.exception;

import com.lucasmartins.common.exception.pattern.IDomainException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DomainRuntimeException extends RuntimeException {

    private final List<String> details = new ArrayList<>();

    public DomainRuntimeException() {
    }

    public DomainRuntimeException(String message) {
        super(message);
    }

    public DomainRuntimeException(String message, Object... args) {
        this(MessageExceptionFormatter.getMensagem(message, args));
    }

    public DomainRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public DomainRuntimeException(IDomainException messageTemplate, Throwable cause) {
        this(MessageExceptionFormatter.getMensagem(messageTemplate), cause);
    }

    public DomainRuntimeException(IDomainException messageTemplate) {
        this(MessageExceptionFormatter.getMensagem(messageTemplate));
    }

    public DomainRuntimeException(IDomainException messageTemplate, List<String> details) {
        this(MessageExceptionFormatter.getMensagem(messageTemplate));
        this.details.addAll(details);
    }

    public DomainRuntimeException(IDomainException messageTemplate, Object... args) {
        this(MessageExceptionFormatter.getMensagem(messageTemplate, args));
    }

    public DomainRuntimeException(Throwable cause) {
        super(cause.getMessage(), cause);
    }

    public DomainRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public List<String> getDetails() {
        return details;
    }


}
