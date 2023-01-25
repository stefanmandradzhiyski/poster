package com.snmi.model.base;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.Instant;
import java.util.function.Consumer;

public class EntityAuditListener {

    @PrePersist
    public void prePersist(BaseEntity entity) {
        lastModifiedAt(entity::setCreatedAt);
    }

    @PreUpdate
    public void preUpdate(BaseEntity entity) {
        lastModifiedAt(entity::setUpdatedAt);
    }

    private void lastModifiedAt(Consumer<Instant> consumer) {
        consumer.accept(Instant.now());
    }

}
