package com.google.common.collect;

import java.lang.Comparable;
import java.util.NoSuchElementException;

public abstract class DiscreteDomain<C extends Comparable> {
    public abstract long distance(C c, C c2);

    public abstract C next(C c);

    public abstract C previous(C c);

    protected DiscreteDomain() {
    }

    public C minValue() {
        throw new NoSuchElementException();
    }

    public C maxValue() {
        throw new NoSuchElementException();
    }
}
