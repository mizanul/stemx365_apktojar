package com.google.common.collect;

public interface Constraint<E> {
    E checkElement(E e);

    String toString();
}
