package com.lucasmartins.api.service.pattern;

import com.lucasmartins.common.model.entity.pattern.AbstractEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface IAbstractService<E extends AbstractEntity<?>, I extends Number> {

    JpaRepository<E, I> getRepository();

    <R extends Repository<?,?>> R getRepository(Class<R> classRespository);

    <R extends IAbstractService<?, ?>> R getService(Class<R> classService);

    List<E> findAll();

    Page<E> findAll(Pageable pageable);

    E findAndValidate(I id);


}
