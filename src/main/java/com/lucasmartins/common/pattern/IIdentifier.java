package com.lucasmartins.common.pattern;

import java.io.Serializable;

public interface IIdentifier<T extends Number> extends Serializable {

    T getId();
}
