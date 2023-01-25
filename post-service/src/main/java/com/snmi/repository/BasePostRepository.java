package com.snmi.repository;

import com.snmi.model.base.BasePostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BasePostRepository<T extends BasePostEntity> extends JpaRepository<T, String> {



}
