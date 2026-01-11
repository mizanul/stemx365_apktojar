package com.google.common.collect;

import java.util.Iterator;

public abstract class UnmodifiableIterator<E> implements Iterator<E> {
    protected UnmodifiableIterator() {
    }

    public final void remove() {
        throw new UnsupportedOperationException();
    }
}
