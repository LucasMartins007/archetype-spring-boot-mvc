package com.lucasmartins.api.config.context;

import com.lucasmartins.common.model.entity.pattern.AbstractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.io.Serializable;
import java.util.Collection;

public interface IContext extends Serializable{

    IContext context = new ContextImpl();

    void createBean(Class<?> domainClass);

    void destroyBean(Class<?> domainClass);

    <T> T getBean(Class<T> domainClass);

    <T> Collection<T> getBeansInterface(Class<T> domainClassInterface);

    boolean hasRepositoryFor(Class<?> domainClass);

    <T extends AbstractEntity<?>, I extends Serializable> JpaRepository<T, I> getRepositoryFromClass(Class<T> domainClass);

    <T extends AbstractEntity<?>> JpaSpecificationExecutor<T> getSpecRepositoryFromClass(Class<T> domainClass);

    static IContext context() {
        return context;
    }


}
