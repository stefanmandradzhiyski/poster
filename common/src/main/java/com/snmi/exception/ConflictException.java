package com.snmi.exception;

import com.snmi.model.base.BaseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ConflictException extends RuntimeException {

    private static final String PROPERTY_MESSAGE = "%s with %s = '%s' already exists";

    public ConflictException(final String message) {
        super(message);
    }

    public ConflictException(final Class<? extends BaseEntity> baseEntity, final String property, final String value) {
        super(String.format(PROPERTY_MESSAGE, baseEntity.getSimpleName(), property, value));
    }

}
