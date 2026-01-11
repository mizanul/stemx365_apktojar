package com.google.common.p007io;

import java.io.IOException;

/* renamed from: com.google.common.io.InputSupplier */
public interface InputSupplier<T> {
    T getInput() throws IOException;
}
