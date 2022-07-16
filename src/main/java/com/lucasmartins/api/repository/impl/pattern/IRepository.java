package com.lucasmartins.api.repository.impl.pattern;

import com.lucasmartins.common.model.entity.pattern.AbstractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IRepository<T extends AbstractEntity<?>, I extends Number> {

    JpaRepository<T, I> getRepository();

    JpaSpecificationExecutor<T> getSpecRepository();

    <S extends JpaSpecificationExecutor<T>> S getSpecRepository(Class<S> specClass);

    Class<T> getEntityClass();

    void setClass(Class<T> clazz);

    void flush();

    void clear();

    void refresh(T entity);

    T refresh(Integer id);

    T getReference(Integer primaryKey);

    void detach(Object obj);

    void persist(T entity);

    T merge(T entity);

    T mergeOrPersist(T entity);

    T find(Number id);

    T find(T entity);

    T findAndValidate(Integer id);

    T findLock(Integer id);

    T findLockNoWait(Integer id);

    T findRefresh(Integer id);

    void remove(T entity);

    void lock(T entity);

    void lockNoWait(T entity);

}
