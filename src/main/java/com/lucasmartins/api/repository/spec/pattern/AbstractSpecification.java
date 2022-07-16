package com.lucasmartins.api.repository.spec.pattern;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class AbstractSpecification implements ISpecification {

    @Override
    public Predicate equals(CriteriaBuilder builder, Path<?> fieldPath, Object value){
        return builder.equal(fieldPath, value);
    }

    @Override
    public Predicate equals(CriteriaBuilder builder, Expression<?> fieldExpression, Object value) {
        return builder.equal(fieldExpression, value);
    }

    @Override
    public Predicate in(Path<?> fieldPath, List<Object> values){
        return fieldPath.in(values);
    }

    @Override
    public Predicate in(Expression<?> fieldPath, List<Object> values){
        return fieldPath.in(values);
    }

    @Override
    public Predicate isTrue(CriteriaBuilder builder, Expression<Boolean> fieldExpression){
        return builder.isTrue(fieldExpression);
    }

}
