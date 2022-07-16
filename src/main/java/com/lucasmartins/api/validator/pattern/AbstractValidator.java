package com.lucasmartins.api.validator.pattern;

import com.lucasmartins.api.config.context.IContext;
import com.lucasmartins.api.service.pattern.IAbstractService;
import com.lucasmartins.common.exception.DomainRuntimeException;
import com.lucasmartins.common.exception.enums.EnumDomainException;
import com.lucasmartins.common.model.entity.pattern.AbstractEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.ParameterizedType;

public abstract class AbstractValidator<T extends AbstractEntity<?>> implements IValidator<T> {

    private Class<T> entityClass;

    protected AbstractValidator() {
        resolveClass(entityClass);
    }

    private IContext getContext() {
        return IContext.context();
    }

    public JpaRepository<T, Number> getRepository() {
        return getContext().getRepositoryFromClass(entityClass);
    }

    public <R extends JpaRepository<T, ?>> R getRepository(Class<R> classRespository) {
        return getContext().getBean(classRespository);
    }

    public <R extends IAbstractService<T, ?>> R getService(Class<R> classService) {
        return getContext().getBean(classService);
    }

    public T findAndValidate(Number id) {
        if (id == null) {
            throw new DomainRuntimeException(EnumDomainException.NULL_POINTER_EXCEPTION);
        }

        return getRepository()
                .findById(id)
                .orElseThrow(() -> new DomainRuntimeException(EnumDomainException.ENTITY_NOT_FOUND, getEntityName(), id));
    }

    public String getEntityName() {
        return entityClass.getSimpleName();
    }

    @SuppressWarnings("unchecked")
    private Class<T> resolveClass(Class<T> clazzType) {
        if (entityClass == null) {
            if (clazzType == null) {
                entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            } else {
                entityClass = clazzType;
            }
        }
        return entityClass;
    }

}
