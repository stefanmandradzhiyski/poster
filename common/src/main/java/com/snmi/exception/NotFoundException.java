package com.snmi.exception;

import com.snmi.model.base.BaseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    private static final String ID_MESSAGE = "%s with id = '%s' isn't found";
    private static final String STRING_MESSAGE = "%s with %s = '%s' isn't found";

    public NotFoundException(Class<? extends BaseEntity> baseEntity, String id) {
        super(String.format(ID_MESSAGE, baseEntity.getSimpleName(), id));
    }

    public NotFoundException(Class<? extends BaseEntity> baseEntity, String property, String value) {
        super(String.format(STRING_MESSAGE, baseEntity.getSimpleName(), property, value));
    }

}