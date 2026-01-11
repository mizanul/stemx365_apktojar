package com.google.common.collect;

import java.util.NoSuchElementException;
import javax.annotation.Nullable;

@Deprecated
public abstract class AbstractLinkedIterator<T> extends UnmodifiableIterator<T> {
    private T nextOrNull;

    /* access modifiers changed from: protected */
    public abstract T computeNext(T t);

    protected AbstractLinkedIterator(@Nullable T firstOrNull) {
        this.nextOrNull = firstOrNull;
    }

    public final boolean hasNext() {
        return this.nextOrNull != null;
    }

    public final T next() {
        if (hasNext()) {
            try {
                return this.nextOrNull;
            } finally {
                this.nextOrNull = computeNext(this.nextOrNull);
            }
        } else {
            throw new NoSuchElementException();
        }
    }
}
