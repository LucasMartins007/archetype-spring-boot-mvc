package com.lucasmartins.api.validator.pattern;

import com.lucasmartins.common.model.entity.pattern.AbstractEntity;

public interface IValidator<E extends AbstractEntity<?>> {

    default void validateRequiredFields(E entity) {
    }

    default void validateDelete(E entity) {
    }

    default void validateUpdate(E entity) {
    }

    default void validateInsert(E entity) {
    }

    default void validateInsertOrUpdate(E entity) {
    }

    default void validateSizeFields(E entity) {
    }
}
