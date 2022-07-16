package com.lucasmartins.api.validator.pattern;

public interface IValidator<E> {

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
