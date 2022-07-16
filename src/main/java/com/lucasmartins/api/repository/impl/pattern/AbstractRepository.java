package com.lucasmartins.api.repository.impl.pattern;

import com.lucasmartins.api.config.context.IContext;
import com.lucasmartins.common.exception.DomainRuntimeException;
import com.lucasmartins.common.exception.enums.EnumDomainException;
import com.lucasmartins.common.model.entity.pattern.AbstractEntity;
import com.lucasmartins.common.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaQuery;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Map;

@Repository
public abstract class AbstractRepository<T extends AbstractEntity<?>, I extends Number> implements IRepository<T, I> {

    @Lazy
    @Autowired
    private IContext context;

    @PersistenceContext
    private EntityManager entityManager;

    private Class<T> entityClass;

    private final Map<String, Object> mapLockNoWait = Collections.singletonMap("javax.persistence.lock.timeout", (Object) 0);

    protected AbstractRepository() {
        resolverClass(entityClass);
    }

    @Override
    public JpaRepository<T, I> getRepository() {
        return resolverContext().getRepositoryFromClass(getEntityClass());
    }

    @Override
    public JpaSpecificationExecutor<T> getSpecRepository() {
        return resolverContext().getSpecRepositoryFromClass(entityClass);
    }

    @Override
    public <S extends JpaSpecificationExecutor<T>> S getSpecRepository(Class<S> specClass) {
        return resolverContext().getBean(specClass);
    }

    @Override
    @Transactional
    public void flush() {
        getEntityManager().flush();
    }

    @Override
    @Transactional
    public void clear() {
        getEntityManager().clear();
    }

    @Override
    @Transactional
    public void refresh(T entity) {
        getEntityManager().refresh(entity);
    }

    @Override
    @Transactional
    public T refresh(Integer id) {
        T entity = getReference(id);
        refresh(entity);
        return entity;
    }

    @Override
    public T getReference(Integer primaryKey) {
        return getEntityManager().getReference(entityClass, primaryKey);
    }

    @Override
    @Transactional
    public void detach(Object obj) {
        getEntityManager().detach(obj);
    }

    @Override
    @Transactional
    public void persist(T entity) {
        getEntityManager().persist(entity);
    }

    @Override
    @Transactional
    public T merge(T entity) {
        return getEntityManager().merge(entity);
    }

    @Override
    @Transactional
    public T mergeOrPersist(T entity) {
        T entityDB = find(entity);

        if (entityDB == null) {
            persist(entity);
            return entity;
        }
        return merge(entity);
    }

    @Override
    public T find(Number id) {
        return getEntityManager().find(getEntityClass(), id);
    }

    @Override
    public T find(T entity) {
        Number id = entity.getId();
        return find(id);
    }

    @Override
    public T findAndValidate(Integer id) {
        T entity = find(id);
        if (entity != null) {
            return entity;
        }
        throw new DomainRuntimeException(EnumDomainException.ENTITY_NOT_FOUND, getEntityName(), id);
    }

    @Override
    @Transactional
    public T findLock(Integer id) {
        return getEntityManager().find(entityClass, id, LockModeType.PESSIMISTIC_WRITE);
    }

    @Override
    @Transactional
    public T findLockNoWait(Integer id) {
        return getEntityManager().find(entityClass, id, LockModeType.PESSIMISTIC_WRITE, mapLockNoWait);
    }

    @Override
    @Transactional
    public T findRefresh(Integer id) {
        T entity = find(id);
        if (entity != null) {
            refresh(entity);
        }
        return entity;
    }

    @Override
    @Transactional
    public void remove(T entity) {
        getEntityManager().remove(entity);
    }

    @Override
    @Transactional
    public void lock(T entity) {
        getEntityManager().lock(entity, LockModeType.PESSIMISTIC_WRITE);
    }

    @Override
    @Transactional
    public void lockNoWait(T entity) {
        getEntityManager().lock(entity, LockModeType.PESSIMISTIC_WRITE, mapLockNoWait);
    }

    protected Query createQuery(String query) {
        return getEntityManager().createQuery(query);
    }

    protected Query createQuery(StringBuilder query) {
        return createQuery(query.toString());
    }

    protected Query createQuery(CriteriaQuery<T> criteriaQuery) {
        return getEntityManager().createQuery(criteriaQuery);
    }

    protected Query createQuery(String sqlString, Class<T> entityClass) {
        return getEntityManager().createQuery(sqlString, entityClass);
    }

    protected TypedQuery<T> createTypedQuery(String query) {
        return getEntityManager().createQuery(query, getEntityClass());
    }

    protected TypedQuery<T> createTypedQuery(StringBuilder query) {
        return createTypedQuery(query.toString());
    }

    protected <C> TypedQuery<C> createTypedQuery(String sqlString, Class<C> entityClass) {
        return getEntityManager().createQuery(sqlString, entityClass);
    }

    protected Query createNativeQuery(String query) {
        return getEntityManager().createNativeQuery(query, getEntityClass());
    }

    protected Query createNativeQuery(StringBuilder query) {
        return createNativeQuery(query.toString());
    }

    protected Query createNativeQuery(StringBuilder query, Class<T> entityClass) {
        return getEntityManager().createNativeQuery(query.toString(), entityClass);
    }

    protected String getEntityName() {
        if (entityClass.isAnnotationPresent(Entity.class)) {
            Entity annotation = entityClass.getAnnotation(Entity.class);
            if (StringUtil.isNotNullOrEmpty(annotation.name())) {
                return annotation.name();
            }
        }

        return entityClass.getSimpleName();
    }

    @SuppressWarnings("unchecked")
    protected Class<T> resolverClass(Class<T> clazzType) {
        try {
            if (entityClass == null) {
                if (clazzType == null) {
                    final Type genericSuperclass = getClass().getGenericSuperclass();

                    if (genericSuperclass instanceof ParameterizedType) {
                        entityClass = (Class<T>) ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
                    }
                } else {
                    entityClass = clazzType;
                }
            }
            return entityClass;
        } catch (Exception e) {
            throw new DomainRuntimeException(EnumDomainException.ENTITY_NOT_FOUND, getEntityClass(), "0");
        }
    }

    private IContext resolverContext() {
        return ((context != null) ? context : IContext.context());
    }

    public Class<T> getEntityClass() {
        return resolverClass(this.entityClass);
    }

    protected EntityManager getEntityManager() {
        return this.entityManager;
    }

    public void setClass(Class<T> clazz) {
        this.entityClass = resolverClass(clazz);
    }

    public <R extends org.springframework.data.repository.Repository<T, I>> R getRepository(Class<R> classRespository) {
        return getContext().getBean(classRespository);
    }

    private IContext getContext() {
        return IContext.context();
    }
}
