package com.lucasmartins.api.service.pattern;

import com.lucasmartins.api.config.context.IContext;
import com.lucasmartins.common.exception.DomainRuntimeException;
import com.lucasmartins.common.exception.enums.EnumDomainException;
import com.lucasmartins.common.model.entity.pattern.AbstractEntity;
import com.lucasmartins.common.utils.Utils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public abstract class AbstractService<E extends AbstractEntity<?>, I extends Number> implements IAbstractService<E, I> {

    private final Class<E> entityClass;

    @SuppressWarnings("unchecked")
    protected AbstractService() {
        Type[] actualTypeArguments = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments();
        this.entityClass = (Class<E>) actualTypeArguments[0];
    }

    private IContext getContext() {
        return IContext.context();
    }

    @Override
    public JpaRepository<E, I> getRepository() {
        return getContext().getRepositoryFromClass(entityClass);
    }


    @Override
    public <R extends Repository<?, ?>> R getRepository(Class<R> classRespository) {
        return getContext().getBean(classRespository);
    }

    @Override
    public <R extends IAbstractService<?, ?>> R getService(Class<R> classService) {
        return getContext().getBean(classService);
    }

    @Override
    public E findAndValidate(I id) {
        if (id == null) {
            throw new DomainRuntimeException(EnumDomainException.NULL_POINTER_EXCEPTION);
        }

        return getRepository()
                .findById(id)
                .orElseThrow(() -> new DomainRuntimeException(EnumDomainException.ENTITY_NOT_FOUND, entityClass.getSimpleName(), id));
    }

    @Override
    public List<E> findAll() {
        return getRepository().findAll();
    }

    @Override
    public Page<E> findAll(Pageable pageable) {
        if (Utils.isEmpty(pageable.getSort())) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.unsorted());
        }
        return getRepository().findAll(pageable);
    }
}
