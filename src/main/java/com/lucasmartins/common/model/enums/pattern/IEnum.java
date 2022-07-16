package com.lucasmartins.common.model.enums.pattern;

public interface IEnum<E> {

    E getKey();

    String getValue();

    default String getName() {
        return ((Enum<?>) this).name();
    }

}
