package com.google.common.collect;

import com.google.common.collect.BstNode;
import com.google.common.collect.BstPath;

interface BstPathFactory<N extends BstNode<?, N>, P extends BstPath<N, P>> {
    P extension(P p, BstSide bstSide);

    P initialPath(N n);
}
