package com.google.common.collect;

import com.google.common.collect.BstNode;
import javax.annotation.Nullable;

interface BstModifier<K, N extends BstNode<K, N>> {
    BstModificationResult<N> modify(K k, @Nullable N n);
}
