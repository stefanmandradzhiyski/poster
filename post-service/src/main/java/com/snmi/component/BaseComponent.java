package com.snmi.component;

import com.snmi.exception.NotFoundException;
import com.snmi.model.base.BasePostEntity;
import com.snmi.repository.BasePostRepository;
import com.snmi.util.QueryParamUtil;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class BaseComponent<T extends BasePostEntity> {

    private final Class<T> clazz;
    private final BasePostRepository<T> repository;

    public BaseComponent(Class<T> clazz, BasePostRepository<T> repository) {
        this.clazz = clazz;
        this.repository = repository;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public T save(final T entity) {
        return entity == null ? null : repository.save(entity);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public T getByIdOrException(final String id) {
        return repository.findById(QueryParamUtil.getStringOrEmpty(id))
                .orElseThrow(() -> new NotFoundException(clazz, QueryParamUtil.getStringOrEmpty(id)));
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void deleteById(final String id) {
        repository.deleteById(QueryParamUtil.getStringOrEmpty(id));
    }

}
