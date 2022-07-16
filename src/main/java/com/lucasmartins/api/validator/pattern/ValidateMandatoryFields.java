package com.lucasmartins.api.validator.pattern;

import com.lucasmartins.common.exception.DomainRuntimeException;
import com.lucasmartins.common.exception.enums.EnumDomainException;
import com.lucasmartins.common.exception.pattern.IDomainException;
import com.lucasmartins.common.utils.ListUtil;
import com.lucasmartins.common.utils.NumericUtil;
import com.lucasmartins.common.utils.StringUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;


public class ValidateMandatoryFields {

    private final List<String> nullFields = new ArrayList<>();

    public void add(Object value, String name) {
        if (isValueNullOrEmpty(value)) {
            nullFields.add(name);
        }
    }

    private boolean isValueNullOrEmpty(Object value) {
        if (value instanceof Collection<?>) {
            return ListUtil.isNullOrEmpty((Collection<?>) value);
        }
        if (value instanceof CharSequence) {
            return StringUtil.isNullOrEmpty(value);
        }
        if (value != null && value.getClass().isArray()) {
            return ListUtil.isNullOrEmpty((Object[]) value);
        }
        return value == null;
    }

    public <T> void add(String name, T value, Predicate<? super T> predicateAdd) {
        if (predicateAdd.test(value)) {
            nullFields.add(name);
        }
    }

    public void addValor(Number value, String name) {
        if (value == null || NumericUtil.isLessOrEquals(value, 0)) {
            nullFields.add(name);
        }
    }

    public void validate() {
        validate(EnumDomainException.MANDATORY_FIELDS);
    }

    public void validate(IDomainException messageTemplate) {
        if (ListUtil.isNotNullOrNotEmpty(nullFields)) {
            throw new DomainRuntimeException(messageTemplate, nullFields);
        }
    }

}
