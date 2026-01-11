package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.BstNode;
import java.util.Comparator;
import javax.annotation.Nullable;

class BstNode<K, N extends BstNode<K, N>> {
    private final K key;
    @Nullable
    private final N left;
    @Nullable
    private final N right;

    BstNode(@Nullable K key2, @Nullable N left2, @Nullable N right2) {
        this.key = key2;
        this.left = left2;
        this.right = right2;
    }

    @Nullable
    public final K getKey() {
        return this.key;
    }

    /* renamed from: com.google.common.collect.BstNode$1 */
    static /* synthetic */ class C04501 {
        static final /* synthetic */ int[] $SwitchMap$com$google$common$collect$BstSide;

        static {
            int[] iArr = new int[BstSide.values().length];
            $SwitchMap$com$google$common$collect$BstSide = iArr;
            try {
                iArr[BstSide.LEFT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$google$common$collect$BstSide[BstSide.RIGHT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    @Nullable
    public final N childOrNull(BstSide side) {
        int i = C04501.$SwitchMap$com$google$common$collect$BstSide[side.ordinal()];
        if (i == 1) {
            return this.left;
        }
        if (i == 2) {
            return this.right;
        }
        throw new AssertionError();
    }

    public final boolean hasChild(BstSide side) {
        return childOrNull(side) != null;
    }

    public final N getChild(BstSide side) {
        N child = childOrNull(side);
        Preconditions.checkState(child != null);
        return child;
    }

    /* access modifiers changed from: protected */
    public final boolean orderingInvariantHolds(Comparator<? super K> comparator) {
        Preconditions.checkNotNull(comparator);
        boolean result = true;
        boolean z = true;
        if (hasChild(BstSide.LEFT)) {
            result = true & (comparator.compare(getChild(BstSide.LEFT).getKey(), this.key) < 0);
        }
        if (!hasChild(BstSide.RIGHT)) {
            return result;
        }
        if (comparator.compare(getChild(BstSide.RIGHT).getKey(), this.key) <= 0) {
            z = false;
        }
        return result & z;
    }
}
