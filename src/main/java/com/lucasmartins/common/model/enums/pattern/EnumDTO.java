package com.lucasmartins.common.model.enums.pattern;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EnumDTO {

    private Object key;

    private Object name;

    private String value;

    public EnumDTO(IEnum<?> enumPadrao) {
        this.key = enumPadrao.getKey();
        this.name = enumPadrao.getName();
        this.value = enumPadrao.getValue();
    }
}
