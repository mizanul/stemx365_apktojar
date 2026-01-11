package com.google.common.collect;

import java.io.Serializable;
import javax.annotation.Nullable;

class ImmutableEntry<K, V> extends AbstractMapEntry<K, V> implements Serializable {
    private static final long serialVersionUID = 0;
    private final K key;
    private final V value;

    ImmutableEntry(@Nullable K key2, @Nullable V value2) {
        this.key = key2;
        this.value = value2;
    }

    @Nullable
    public K getKey() {
        return this.key;
    }

    @Nullable
    public V getValue() {
        return this.value;
    }

    public final V setValue(V v) {
        throw new UnsupportedOperationException();
    }
}
