package com.google.common.collect;

import com.google.common.collect.BstNode;
import javax.annotation.Nullable;

interface BstBalancePolicy<N extends BstNode<?, N>> {
    N balance(BstNodeFactory<N> bstNodeFactory, N n, @Nullable N n2, @Nullable N n3);

    @Nullable
    N combine(BstNodeFactory<N> bstNodeFactory, @Nullable N n, @Nullable N n2);
}
