package com.snmi.mapper;

import java.util.List;

public interface BaseMapper<T, S> {

    T toDTO(S entity);

    S toModel(T dto);

    default List<T> toDTOs(final List<S> entities) {
        return entities.stream().map(this::toDTO).toList();
    }

}
