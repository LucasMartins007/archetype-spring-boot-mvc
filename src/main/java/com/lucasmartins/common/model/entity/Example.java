package com.lucasmartins.common.model.entity;

import com.lucasmartins.common.model.entity.pattern.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Example extends AbstractEntity<Integer> {

    private Integer id;

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
