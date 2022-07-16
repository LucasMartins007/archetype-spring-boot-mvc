
package com.lucasmartins.common.utils.optional;

@FunctionalInterface
public interface ThrowableFunction<T, R, E extends Throwable> {

    R apply(T t) throws E;

}

