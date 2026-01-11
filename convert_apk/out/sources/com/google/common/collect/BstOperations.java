package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.BstModificationResult;
import java.util.Comparator;
import javax.annotation.Nullable;

final class BstOperations {
    private BstOperations() {
    }

    @Nullable
    public static <K, N extends BstNode<K, N>> N seek(Comparator<? super K> comparator, @Nullable N tree, @Nullable K key) {
        Preconditions.checkNotNull(comparator);
        if (tree == null) {
            return null;
        }
        int cmp = comparator.compare(key, tree.getKey());
        if (cmp == 0) {
            return tree;
        }
        return seek(comparator, tree.childOrNull(cmp < 0 ? BstSide.LEFT : BstSide.RIGHT), key);
    }

    public static <K, N extends BstNode<K, N>> BstMutationResult<K, N> mutate(Comparator<? super K> comparator, BstMutationRule<K, N> mutationRule, @Nullable N tree, @Nullable K key) {
        int cmp;
        Preconditions.checkNotNull(comparator);
        Preconditions.checkNotNull(mutationRule);
        if (tree == null || (cmp = comparator.compare(key, tree.getKey())) == 0) {
            return modify(tree, key, mutationRule);
        }
        BstSide side = cmp < 0 ? BstSide.LEFT : BstSide.RIGHT;
        return mutate(comparator, mutationRule, tree.childOrNull(side), key).lift(tree, side, mutationRule.getNodeFactory(), mutationRule.getBalancePolicy());
    }

    public static <K, N extends BstNode<K, N>> BstMutationResult<K, N> mutate(BstInOrderPath<N> path, BstMutationRule<K, N> mutationRule) {
        Preconditions.checkNotNull(path);
        Preconditions.checkNotNull(mutationRule);
        BstBalancePolicy<N> balancePolicy = mutationRule.getBalancePolicy();
        BstNodeFactory<N> nodeFactory = mutationRule.getNodeFactory();
        N target = path.getTip();
        BstMutationResult<K, N> result = modify(target, target.getKey(), mutationRule);
        while (path.hasPrefix()) {
            BstInOrderPath<N> prefix = (BstInOrderPath) path.getPrefix();
            result = result.lift(prefix.getTip(), path.getSideOfExtension(), nodeFactory, balancePolicy);
            path = prefix;
        }
        return result;
    }

    private static <K, N extends BstNode<K, N>> BstMutationResult<K, N> modify(@Nullable N tree, K key, BstMutationRule<K, N> mutationRule) {
        N changedRoot;
        BstBalancePolicy<N> rebalancePolicy = mutationRule.getBalancePolicy();
        BstNodeFactory<N> nodeFactory = mutationRule.getNodeFactory();
        N originalRoot = tree;
        BstModificationResult<N> modResult = mutationRule.getModifier().modify(key, tree == null ? null : nodeFactory.createLeaf(tree));
        N originalLeft = null;
        N originalRight = null;
        if (tree != null) {
            originalLeft = tree.childOrNull(BstSide.LEFT);
            originalRight = tree.childOrNull(BstSide.RIGHT);
        }
        int i = C04511.f65xabb36de9[modResult.getType().ordinal()];
        if (i == 1) {
            changedRoot = tree;
        } else if (i != 2) {
            if (i != 3) {
                throw new AssertionError();
            } else if (modResult.getChangedTarget() != null) {
                changedRoot = rebalancePolicy.balance(nodeFactory, modResult.getChangedTarget(), originalLeft, originalRight);
            } else if (tree != null) {
                changedRoot = rebalancePolicy.combine(nodeFactory, originalLeft, originalRight);
            } else {
                changedRoot = null;
            }
        } else if (modResult.getChangedTarget() != null) {
            changedRoot = nodeFactory.createNode(modResult.getChangedTarget(), originalLeft, originalRight);
        } else if (tree == null) {
            changedRoot = null;
        } else {
            throw new AssertionError("Modification result is a REBUILDING_CHANGE, but rebalancing required");
        }
        return BstMutationResult.mutationResult(key, originalRoot, changedRoot, modResult);
    }

    /* renamed from: com.google.common.collect.BstOperations$1 */
    static /* synthetic */ class C04511 {

        /* renamed from: $SwitchMap$com$google$common$collect$BstModificationResult$ModificationType */
        static final /* synthetic */ int[] f65xabb36de9;

        static {
            int[] iArr = new int[BstModificationResult.ModificationType.values().length];
            f65xabb36de9 = iArr;
            try {
                iArr[BstModificationResult.ModificationType.IDENTITY.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f65xabb36de9[BstModificationResult.ModificationType.REBUILDING_CHANGE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f65xabb36de9[BstModificationResult.ModificationType.REBALANCING_CHANGE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    public static <K, N extends BstNode<K, N>> BstMutationResult<K, N> extractMin(N root, BstNodeFactory<N> nodeFactory, BstBalancePolicy<N> balancePolicy) {
        Preconditions.checkNotNull(root);
        Preconditions.checkNotNull(nodeFactory);
        Preconditions.checkNotNull(balancePolicy);
        if (root.hasChild(BstSide.LEFT)) {
            return extractMin(root.getChild(BstSide.LEFT), nodeFactory, balancePolicy).lift(root, BstSide.LEFT, nodeFactory, balancePolicy);
        }
        return BstMutationResult.mutationResult(root.getKey(), root, root.childOrNull(BstSide.RIGHT), BstModificationResult.rebalancingChange(root, null));
    }

    public static <K, N extends BstNode<K, N>> BstMutationResult<K, N> extractMax(N root, BstNodeFactory<N> nodeFactory, BstBalancePolicy<N> balancePolicy) {
        Preconditions.checkNotNull(root);
        Preconditions.checkNotNull(nodeFactory);
        Preconditions.checkNotNull(balancePolicy);
        if (root.hasChild(BstSide.RIGHT)) {
            return extractMax(root.getChild(BstSide.RIGHT), nodeFactory, balancePolicy).lift(root, BstSide.RIGHT, nodeFactory, balancePolicy);
        }
        return BstMutationResult.mutationResult(root.getKey(), root, root.childOrNull(BstSide.LEFT), BstModificationResult.rebalancingChange(root, null));
    }

    public static <N extends BstNode<?, N>> N insertMin(@Nullable N root, N entry, BstNodeFactory<N> nodeFactory, BstBalancePolicy<N> balancePolicy) {
        Preconditions.checkNotNull(entry);
        Preconditions.checkNotNull(nodeFactory);
        Preconditions.checkNotNull(balancePolicy);
        if (root == null) {
            return nodeFactory.createLeaf(entry);
        }
        return balancePolicy.balance(nodeFactory, root, insertMin(root.childOrNull(BstSide.LEFT), entry, nodeFactory, balancePolicy), root.childOrNull(BstSide.RIGHT));
    }

    public static <N extends BstNode<?, N>> N insertMax(@Nullable N root, N entry, BstNodeFactory<N> nodeFactory, BstBalancePolicy<N> balancePolicy) {
        Preconditions.checkNotNull(entry);
        Preconditions.checkNotNull(nodeFactory);
        Preconditions.checkNotNull(balancePolicy);
        if (root == null) {
            return nodeFactory.createLeaf(entry);
        }
        return balancePolicy.balance(nodeFactory, root, root.childOrNull(BstSide.LEFT), insertMax(root.childOrNull(BstSide.RIGHT), entry, nodeFactory, balancePolicy));
    }
}
