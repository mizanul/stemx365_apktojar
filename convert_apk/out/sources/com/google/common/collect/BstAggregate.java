package com.google.common.collect;

import com.google.common.collect.BstNode;
import javax.annotation.Nullable;

interface BstAggregate<N extends BstNode<?, N>> {
    int entryValue(N n);

    long treeValue(@Nullable N n);
}
