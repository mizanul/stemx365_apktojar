package com.google.common.collect;

import com.google.common.collect.Multiset;
import javax.annotation.Nullable;

final class EmptyImmutableMultiset extends ImmutableMultiset<Object> {
    static final EmptyImmutableMultiset INSTANCE = new EmptyImmutableMultiset();
    private static final long serialVersionUID = 0;

    EmptyImmutableMultiset() {
    }

    public int count(@Nullable Object element) {
        return 0;
    }

    public ImmutableSet<Object> elementSet() {
        return ImmutableSet.m80of();
    }

    public int size() {
        return 0;
    }

    /* access modifiers changed from: package-private */
    public boolean isPartialView() {
        return false;
    }

    /* access modifiers changed from: package-private */
    public ImmutableSet<Multiset.Entry<Object>> createEntrySet() {
        return ImmutableSet.m80of();
    }
}
