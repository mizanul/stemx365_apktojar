package com.google.common.hash;

import com.google.common.base.Preconditions;
import com.google.common.hash.BloomFilterStrategies;
import java.io.Serializable;

public final class BloomFilter<T> implements Serializable {
    private static final double LN2;
    private static final double LN2_SQUARED;
    /* access modifiers changed from: private */
    public final BloomFilterStrategies.BitArray bits;
    /* access modifiers changed from: private */
    public final Funnel<T> funnel;
    /* access modifiers changed from: private */
    public final int numHashFunctions;
    /* access modifiers changed from: private */
    public final Strategy strategy;

    interface Strategy extends Serializable {
        <T> boolean mightContain(T t, Funnel<? super T> funnel, int i, BloomFilterStrategies.BitArray bitArray);

        int ordinal();

        <T> boolean put(T t, Funnel<? super T> funnel, int i, BloomFilterStrategies.BitArray bitArray);
    }

    private BloomFilter(BloomFilterStrategies.BitArray bits2, int numHashFunctions2, Funnel<T> funnel2, Strategy strategy2) {
        Preconditions.checkArgument(numHashFunctions2 > 0, "numHashFunctions zero or negative");
        this.bits = (BloomFilterStrategies.BitArray) Preconditions.checkNotNull(bits2);
        this.numHashFunctions = numHashFunctions2;
        this.funnel = (Funnel) Preconditions.checkNotNull(funnel2);
        this.strategy = strategy2;
        if (numHashFunctions2 > 255) {
            throw new AssertionError("Currently we don't allow BloomFilters that would use more than255 hash functions, please contact the guava team");
        }
    }

    public BloomFilter<T> copy() {
        return new BloomFilter<>(this.bits.copy(), this.numHashFunctions, this.funnel, this.strategy);
    }

    public boolean mightContain(T object) {
        return this.strategy.mightContain(object, this.funnel, this.numHashFunctions, this.bits);
    }

    public boolean put(T object) {
        return this.strategy.put(object, this.funnel, this.numHashFunctions, this.bits);
    }

    public boolean equals(Object o) {
        if (!(o instanceof BloomFilter)) {
            return false;
        }
        BloomFilter<?> that = (BloomFilter) o;
        if (this.numHashFunctions == that.numHashFunctions && this.bits.equals(that.bits) && this.funnel == that.funnel && this.strategy == that.strategy) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return this.bits.hashCode();
    }

    /* access modifiers changed from: package-private */
    public int getHashCount() {
        return this.numHashFunctions;
    }

    /* access modifiers changed from: package-private */
    public double computeExpectedFalsePositiveRate(int insertions) {
        return Math.pow(1.0d - Math.exp(((double) (-this.numHashFunctions)) * (((double) insertions) / ((double) this.bits.size()))), (double) this.numHashFunctions);
    }

    public static <T> BloomFilter<T> create(Funnel<T> funnel2, int expectedInsertions, double falsePositiveProbability) {
        Preconditions.checkNotNull(funnel2);
        boolean z = true;
        Preconditions.checkArgument(expectedInsertions > 0, "Expected insertions must be positive");
        boolean z2 = falsePositiveProbability > 0.0d;
        if (falsePositiveProbability >= 1.0d) {
            z = false;
        }
        Preconditions.checkArgument(z & z2, "False positive probability in (0.0, 1.0)");
        int numBits = optimalNumOfBits(expectedInsertions, falsePositiveProbability);
        return new BloomFilter<>(new BloomFilterStrategies.BitArray(numBits), optimalNumOfHashFunctions(expectedInsertions, numBits), funnel2, BloomFilterStrategies.MURMUR128_MITZ_32);
    }

    public static <T> BloomFilter<T> create(Funnel<T> funnel2, int expectedInsertions) {
        return create(funnel2, expectedInsertions, 0.03d);
    }

    static {
        double log = Math.log(2.0d);
        LN2 = log;
        LN2_SQUARED = log * log;
    }

    static int optimalNumOfHashFunctions(int n, int m) {
        return Math.max(1, (int) Math.round(((double) (m / n)) * LN2));
    }

    static int optimalNumOfBits(int n, double p) {
        return (int) ((((double) (-n)) * Math.log(p)) / LN2_SQUARED);
    }

    private Object writeReplace() {
        return new SerialForm(this);
    }

    private static class SerialForm<T> implements Serializable {
        private static final long serialVersionUID = 1;
        final long[] data;
        final Funnel<T> funnel;
        final int numHashFunctions;
        final Strategy strategy;

        SerialForm(BloomFilter<T> bf) {
            this.data = bf.bits.data;
            this.numHashFunctions = bf.numHashFunctions;
            this.funnel = bf.funnel;
            this.strategy = bf.strategy;
        }

        /* access modifiers changed from: package-private */
        public Object readResolve() {
            return new BloomFilter(new BloomFilterStrategies.BitArray(this.data), this.numHashFunctions, this.funnel, this.strategy);
        }
    }
}
