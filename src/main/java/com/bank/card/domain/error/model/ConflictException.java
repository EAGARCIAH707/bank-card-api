package com.bank.card.domain.error.model;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.CONFLICT)
public class ConflictException extends RuntimeException {

    private final transient Object error;

    public ConflictException(Object error, String message) {
        super(message);
        this.error = error;
    }

    public ConflictException(String message) {
        super(message);
        this.error = null;
    }

}
