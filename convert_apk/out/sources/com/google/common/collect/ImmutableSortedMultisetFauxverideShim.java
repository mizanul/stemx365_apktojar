package com.google.common.collect;

import com.google.common.collect.ImmutableSortedMultiset;

abstract class ImmutableSortedMultisetFauxverideShim<E> extends ImmutableMultiset<E> {
    ImmutableSortedMultisetFauxverideShim() {
    }

    @Deprecated
    public static <E> ImmutableSortedMultiset.Builder<E> builder() {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    /* renamed from: of */
    public static <E> ImmutableSortedMultiset<E> m111of(E e) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    /* renamed from: of */
    public static <E> ImmutableSortedMultiset<E> m112of(E e, E e2) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    /* renamed from: of */
    public static <E> ImmutableSortedMultiset<E> m113of(E e, E e2, E e3) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    /* renamed from: of */
    public static <E> ImmutableSortedMultiset<E> m114of(E e, E e2, E e3, E e4) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    /* renamed from: of */
    public static <E> ImmutableSortedMultiset<E> m115of(E e, E e2, E e3, E e4, E e5) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    /* renamed from: of */
    public static <E> ImmutableSortedMultiset<E> m116of(E e, E e2, E e3, E e4, E e5, E e6, E... eArr) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public static <E> ImmutableSortedMultiset<E> copyOf(E[] eArr) {
        throw new UnsupportedOperationException();
    }
}
