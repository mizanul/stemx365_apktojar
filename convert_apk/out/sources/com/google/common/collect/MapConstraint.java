package com.google.common.collect;

import javax.annotation.Nullable;

public interface MapConstraint<K, V> {
    void checkKeyValue(@Nullable K k, @Nullable V v);

    String toString();
}
