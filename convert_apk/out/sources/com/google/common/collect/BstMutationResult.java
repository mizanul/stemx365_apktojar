package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.BstModificationResult;
import com.google.common.collect.BstNode;
import javax.annotation.Nullable;

final class BstMutationResult<K, N extends BstNode<K, N>> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    @Nullable
    private N changedRoot;
    private final BstModificationResult<N> modificationResult;
    @Nullable
    private N originalRoot;
    private final K targetKey;

    public static <K, N extends BstNode<K, N>> BstMutationResult<K, N> mutationResult(@Nullable K targetKey2, @Nullable N originalRoot2, @Nullable N changedRoot2, BstModificationResult<N> modificationResult2) {
        return new BstMutationResult<>(targetKey2, originalRoot2, changedRoot2, modificationResult2);
    }

    private BstMutationResult(@Nullable K targetKey2, @Nullable N originalRoot2, @Nullable N changedRoot2, BstModificationResult<N> modificationResult2) {
        this.targetKey = targetKey2;
        this.originalRoot = originalRoot2;
        this.changedRoot = changedRoot2;
        this.modificationResult = (BstModificationResult) Preconditions.checkNotNull(modificationResult2);
    }

    public K getTargetKey() {
        return this.targetKey;
    }

    @Nullable
    public N getOriginalRoot() {
        return this.originalRoot;
    }

    @Nullable
    public N getChangedRoot() {
        return this.changedRoot;
    }

    @Nullable
    public N getOriginalTarget() {
        return this.modificationResult.getOriginalTarget();
    }

    @Nullable
    public N getChangedTarget() {
        return this.modificationResult.getChangedTarget();
    }

    /* access modifiers changed from: package-private */
    public BstModificationResult.ModificationType modificationType() {
        return this.modificationResult.getType();
    }

    /* renamed from: com.google.common.collect.BstMutationResult$1 */
    static /* synthetic */ class C04491 {

        /* renamed from: $SwitchMap$com$google$common$collect$BstModificationResult$ModificationType */
        static final /* synthetic */ int[] f64xabb36de9;
        static final /* synthetic */ int[] $SwitchMap$com$google$common$collect$BstSide;

        static {
            int[] iArr = new int[BstModificationResult.ModificationType.values().length];
            f64xabb36de9 = iArr;
            try {
                iArr[BstModificationResult.ModificationType.IDENTITY.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f64xabb36de9[BstModificationResult.ModificationType.REBUILDING_CHANGE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f64xabb36de9[BstModificationResult.ModificationType.REBALANCING_CHANGE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            int[] iArr2 = new int[BstSide.values().length];
            $SwitchMap$com$google$common$collect$BstSide = iArr2;
            try {
                iArr2[BstSide.LEFT.ordinal()] = 1;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$google$common$collect$BstSide[BstSide.RIGHT.ordinal()] = 2;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    public BstMutationResult<K, N> lift(N liftOriginalRoot, BstSide side, BstNodeFactory<N> nodeFactory, BstBalancePolicy<N> balancePolicy) {
        int i = C04491.f64xabb36de9[modificationType().ordinal()];
        if (i == 1) {
            this.changedRoot = liftOriginalRoot;
            this.originalRoot = liftOriginalRoot;
            return this;
        } else if (i == 2 || i == 3) {
            this.originalRoot = liftOriginalRoot;
            N resultLeft = liftOriginalRoot.childOrNull(BstSide.LEFT);
            N resultRight = liftOriginalRoot.childOrNull(BstSide.RIGHT);
            int i2 = C04491.$SwitchMap$com$google$common$collect$BstSide[side.ordinal()];
            if (i2 == 1) {
                resultLeft = this.changedRoot;
            } else if (i2 == 2) {
                resultRight = this.changedRoot;
            } else {
                throw new AssertionError();
            }
            if (modificationType() == BstModificationResult.ModificationType.REBUILDING_CHANGE) {
                this.changedRoot = nodeFactory.createNode(liftOriginalRoot, resultLeft, resultRight);
            } else {
                this.changedRoot = balancePolicy.balance(nodeFactory, liftOriginalRoot, resultLeft, resultRight);
            }
            return this;
        } else {
            throw new AssertionError();
        }
    }
}
