
package com.lucasmartins.common.utils.optional;


@FunctionalInterface
public interface ThrowableSupplier<T, E extends Throwable> {

    T get() throws E;

}

