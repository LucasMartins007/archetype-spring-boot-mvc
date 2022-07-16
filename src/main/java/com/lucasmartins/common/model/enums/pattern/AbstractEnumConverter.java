package com.lucasmartins.common.model.enums.pattern;

import javax.persistence.AttributeConverter;
import java.lang.reflect.ParameterizedType;

public class AbstractEnumConverter<T extends Enum<T> & IEnum<E>, E> implements AttributeConverter<T, E> {

    private final Class<T> clazz;

    @SuppressWarnings("unchecked")
    public AbstractEnumConverter() {
        this.clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public E convertToDatabaseColumn(T attribute) {
        return attribute != null ? attribute.getKey() : null;
    }

    @Override
    public T convertToEntityAttribute(E dbData) {
        T[] enums = clazz.getEnumConstants();

        for (T e : enums) {
            if (e.getKey().equals(dbData)) {
                return e;
            }
        }

        return null;
    }

}
