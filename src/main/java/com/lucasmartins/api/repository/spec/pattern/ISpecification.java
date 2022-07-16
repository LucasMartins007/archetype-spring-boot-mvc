package com.lucasmartins.api.repository.spec.pattern;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.List;

public interface ISpecification {

    Predicate equals(CriteriaBuilder builder, Path<?> fieldPath, Object value);

    Predicate equals(CriteriaBuilder builder, Expression<?> fieldExpression, Object value);

    Predicate in(Path<?> fieldPath, List<Object> values);

    Predicate in(Expression<?> fieldPath, List<Object> values);

    Predicate isTrue(CriteriaBuilder builder, Expression<Boolean> fieldExpression);
}
