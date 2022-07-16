package com.lucasmartins.api.controller.pattern;

import com.lucasmartins.api.config.context.IContext;
import com.lucasmartins.api.converter.Converter;
import com.lucasmartins.api.validator.pattern.service.pattern.AbstractService;
import com.lucasmartins.common.model.dto.pattern.AbstractDTO;
import com.lucasmartins.common.model.entity.pattern.AbstractEntity;

import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 *
 * @param <S> Service class referring to the Controller's responsibility entity
 */
public abstract class AbstractController<S extends AbstractService<?, ?>> {

    private final Class<S> serviceClass;

    protected AbstractController() {
        this.serviceClass = resolverServiceClass(this.getClass());
    }

    @SuppressWarnings("unchecked")
    private Class<S> resolverServiceClass(Class<?> serviceClass) {
        return (Class<S>) ((ParameterizedType) serviceClass.getGenericSuperclass()).getActualTypeArguments()[0];
    }

    protected S getService() {
        return getContext().getBean(serviceClass);
    }

    private IContext getContext() {
        return IContext.context();
    }

    protected <D extends AbstractDTO<?>, C extends AbstractEntity<?>> C converterDTOParaEntity(D dto, Class<C> clazzEntity) {
        return Converter.converterDTOParaEntity(dto, clazzEntity);
    }

    protected <D extends AbstractDTO<?>, C extends AbstractEntity<?>> List<C> converterDTOParaEntity(List<D> dtos, Class<C> clazzEntity) {
        return Converter.converterDTOParaEntity(dtos, clazzEntity);
    }

    protected <D extends AbstractDTO<?>, C extends AbstractEntity<?>> D converterEntityParaDTO(C entity, Class<D> clazzDto) {
        return Converter.converterEntityParaDTO(entity, clazzDto);
    }

    protected <D extends AbstractDTO<?>, C extends AbstractEntity<?>> List<D> converterEntityParaDTO(List<C> entitys, Class<D> clazzDto) {
        return Converter.converterEntityParaDTO(entitys, clazzDto);
    }


}
