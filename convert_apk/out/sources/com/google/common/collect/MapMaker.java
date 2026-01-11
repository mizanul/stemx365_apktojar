package com.google.common.collect;

import com.google.common.base.Ascii;
import com.google.common.base.Equivalence;
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Ticker;
import com.google.common.collect.ComputingConcurrentHashMap;
import com.google.common.collect.MapMakerInternalMap;
import java.io.Serializable;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;

public final class MapMaker extends GenericMapMaker<Object, Object> {
    private static final int DEFAULT_CONCURRENCY_LEVEL = 4;
    private static final int DEFAULT_EXPIRATION_NANOS = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    static final int UNSET_INT = -1;
    int concurrencyLevel = -1;
    long expireAfterAccessNanos = -1;
    long expireAfterWriteNanos = -1;
    int initialCapacity = -1;
    Equivalence<Object> keyEquivalence;
    MapMakerInternalMap.Strength keyStrength;
    int maximumSize = -1;
    RemovalCause nullRemovalCause;
    Ticker ticker;
    boolean useCustomMap;
    Equivalence<Object> valueEquivalence;
    MapMakerInternalMap.Strength valueStrength;

    enum RemovalCause {
        EXPLICIT {
            /* access modifiers changed from: package-private */
            public boolean wasEvicted() {
                return false;
            }
        },
        REPLACED {
            /* access modifiers changed from: package-private */
            public boolean wasEvicted() {
                return false;
            }
        },
        COLLECTED {
            /* access modifiers changed from: package-private */
            public boolean wasEvicted() {
                return true;
            }
        },
        EXPIRED {
            /* access modifiers changed from: package-private */
            public boolean wasEvicted() {
                return true;
            }
        },
        SIZE {
            /* access modifiers changed from: package-private */
            public boolean wasEvicted() {
                return true;
            }
        };

        /* access modifiers changed from: package-private */
        public abstract boolean wasEvicted();
    }

    interface RemovalListener<K, V> {
        void onRemoval(RemovalNotification<K, V> removalNotification);
    }

    private boolean useNullMap() {
        return this.nullRemovalCause == null;
    }

    /* access modifiers changed from: package-private */
    public MapMaker keyEquivalence(Equivalence<Object> equivalence) {
        Preconditions.checkState(this.keyEquivalence == null, "key equivalence was already set to %s", this.keyEquivalence);
        this.keyEquivalence = (Equivalence) Preconditions.checkNotNull(equivalence);
        this.useCustomMap = true;
        return this;
    }

    /* access modifiers changed from: package-private */
    public Equivalence<Object> getKeyEquivalence() {
        return (Equivalence) Objects.firstNonNull(this.keyEquivalence, getKeyStrength().defaultEquivalence());
    }

    /* access modifiers changed from: package-private */
    public MapMaker valueEquivalence(Equivalence<Object> equivalence) {
        Preconditions.checkState(this.valueEquivalence == null, "value equivalence was already set to %s", this.valueEquivalence);
        this.valueEquivalence = (Equivalence) Preconditions.checkNotNull(equivalence);
        this.useCustomMap = true;
        return this;
    }

    /* access modifiers changed from: package-private */
    public Equivalence<Object> getValueEquivalence() {
        return (Equivalence) Objects.firstNonNull(this.valueEquivalence, getValueStrength().defaultEquivalence());
    }

    public MapMaker initialCapacity(int initialCapacity2) {
        boolean z = true;
        Preconditions.checkState(this.initialCapacity == -1, "initial capacity was already set to %s", Integer.valueOf(this.initialCapacity));
        if (initialCapacity2 < 0) {
            z = false;
        }
        Preconditions.checkArgument(z);
        this.initialCapacity = initialCapacity2;
        return this;
    }

    /* access modifiers changed from: package-private */
    public int getInitialCapacity() {
        int i = this.initialCapacity;
        if (i == -1) {
            return 16;
        }
        return i;
    }

    /* access modifiers changed from: package-private */
    @Deprecated
    public MapMaker maximumSize(int size) {
        boolean z = false;
        Preconditions.checkState(this.maximumSize == -1, "maximum size was already set to %s", Integer.valueOf(this.maximumSize));
        if (size >= 0) {
            z = true;
        }
        Preconditions.checkArgument(z, "maximum size must not be negative");
        this.maximumSize = size;
        this.useCustomMap = true;
        if (size == 0) {
            this.nullRemovalCause = RemovalCause.SIZE;
        }
        return this;
    }

    public MapMaker concurrencyLevel(int concurrencyLevel2) {
        boolean z = true;
        Preconditions.checkState(this.concurrencyLevel == -1, "concurrency level was already set to %s", Integer.valueOf(this.concurrencyLevel));
        if (concurrencyLevel2 <= 0) {
            z = false;
        }
        Preconditions.checkArgument(z);
        this.concurrencyLevel = concurrencyLevel2;
        return this;
    }

    /* access modifiers changed from: package-private */
    public int getConcurrencyLevel() {
        int i = this.concurrencyLevel;
        if (i == -1) {
            return 4;
        }
        return i;
    }

    /* access modifiers changed from: package-private */
    public MapMaker strongKeys() {
        return setKeyStrength(MapMakerInternalMap.Strength.STRONG);
    }

    public MapMaker weakKeys() {
        return setKeyStrength(MapMakerInternalMap.Strength.WEAK);
    }

    @Deprecated
    public MapMaker softKeys() {
        return setKeyStrength(MapMakerInternalMap.Strength.SOFT);
    }

    /* access modifiers changed from: package-private */
    public MapMaker setKeyStrength(MapMakerInternalMap.Strength strength) {
        Preconditions.checkState(this.keyStrength == null, "Key strength was already set to %s", this.keyStrength);
        this.keyStrength = (MapMakerInternalMap.Strength) Preconditions.checkNotNull(strength);
        if (strength != MapMakerInternalMap.Strength.STRONG) {
            this.useCustomMap = true;
        }
        return this;
    }

    /* access modifiers changed from: package-private */
    public MapMakerInternalMap.Strength getKeyStrength() {
        return (MapMakerInternalMap.Strength) Objects.firstNonNull(this.keyStrength, MapMakerInternalMap.Strength.STRONG);
    }

    /* access modifiers changed from: package-private */
    public MapMaker strongValues() {
        return setValueStrength(MapMakerInternalMap.Strength.STRONG);
    }

    public MapMaker weakValues() {
        return setValueStrength(MapMakerInternalMap.Strength.WEAK);
    }

    public MapMaker softValues() {
        return setValueStrength(MapMakerInternalMap.Strength.SOFT);
    }

    /* access modifiers changed from: package-private */
    public MapMaker setValueStrength(MapMakerInternalMap.Strength strength) {
        Preconditions.checkState(this.valueStrength == null, "Value strength was already set to %s", this.valueStrength);
        this.valueStrength = (MapMakerInternalMap.Strength) Preconditions.checkNotNull(strength);
        if (strength != MapMakerInternalMap.Strength.STRONG) {
            this.useCustomMap = true;
        }
        return this;
    }

    /* access modifiers changed from: package-private */
    public MapMakerInternalMap.Strength getValueStrength() {
        return (MapMakerInternalMap.Strength) Objects.firstNonNull(this.valueStrength, MapMakerInternalMap.Strength.STRONG);
    }

    @Deprecated
    public MapMaker expiration(long duration, TimeUnit unit) {
        return expireAfterWrite(duration, unit);
    }

    /* access modifiers changed from: package-private */
    @Deprecated
    public MapMaker expireAfterWrite(long duration, TimeUnit unit) {
        checkExpiration(duration, unit);
        this.expireAfterWriteNanos = unit.toNanos(duration);
        if (duration == 0 && this.nullRemovalCause == null) {
            this.nullRemovalCause = RemovalCause.EXPIRED;
        }
        this.useCustomMap = true;
        return this;
    }

    private void checkExpiration(long duration, TimeUnit unit) {
        Preconditions.checkState(this.expireAfterWriteNanos == -1, "expireAfterWrite was already set to %s ns", Long.valueOf(this.expireAfterWriteNanos));
        Preconditions.checkState(this.expireAfterAccessNanos == -1, "expireAfterAccess was already set to %s ns", Long.valueOf(this.expireAfterAccessNanos));
        Preconditions.checkArgument(duration >= 0, "duration cannot be negative: %s %s", Long.valueOf(duration), unit);
    }

    /* access modifiers changed from: package-private */
    public long getExpireAfterWriteNanos() {
        long j = this.expireAfterWriteNanos;
        if (j == -1) {
            return 0;
        }
        return j;
    }

    /* access modifiers changed from: package-private */
    @Deprecated
    public MapMaker expireAfterAccess(long duration, TimeUnit unit) {
        checkExpiration(duration, unit);
        this.expireAfterAccessNanos = unit.toNanos(duration);
        if (duration == 0 && this.nullRemovalCause == null) {
            this.nullRemovalCause = RemovalCause.EXPIRED;
        }
        this.useCustomMap = true;
        return this;
    }

    /* access modifiers changed from: package-private */
    public long getExpireAfterAccessNanos() {
        long j = this.expireAfterAccessNanos;
        if (j == -1) {
            return 0;
        }
        return j;
    }

    /* access modifiers changed from: package-private */
    public Ticker getTicker() {
        return (Ticker) Objects.firstNonNull(this.ticker, Ticker.systemTicker());
    }

    /* access modifiers changed from: package-private */
    @Deprecated
    public <K, V> GenericMapMaker<K, V> removalListener(RemovalListener<K, V> listener) {
        Preconditions.checkState(this.removalListener == null);
        this.removalListener = (RemovalListener) Preconditions.checkNotNull(listener);
        this.useCustomMap = true;
        return this;
    }

    public <K, V> ConcurrentMap<K, V> makeMap() {
        if (!this.useCustomMap) {
            return new ConcurrentHashMap(getInitialCapacity(), 0.75f, getConcurrencyLevel());
        }
        return this.nullRemovalCause == null ? new MapMakerInternalMap(this) : new NullConcurrentMap(this);
    }

    /* access modifiers changed from: package-private */
    public <K, V> MapMakerInternalMap<K, V> makeCustomMap() {
        return new MapMakerInternalMap<>(this);
    }

    @Deprecated
    public <K, V> ConcurrentMap<K, V> makeComputingMap(Function<? super K, ? extends V> computingFunction) {
        return useNullMap() ? new ComputingConcurrentHashMap.ComputingMapAdapter(this, computingFunction) : new NullComputingConcurrentMap(this, computingFunction);
    }

    public String toString() {
        Objects.ToStringHelper s = Objects.toStringHelper((Object) this);
        int i = this.initialCapacity;
        if (i != -1) {
            s.add("initialCapacity", i);
        }
        int i2 = this.concurrencyLevel;
        if (i2 != -1) {
            s.add("concurrencyLevel", i2);
        }
        int i3 = this.maximumSize;
        if (i3 != -1) {
            s.add("maximumSize", i3);
        }
        if (this.expireAfterWriteNanos != -1) {
            s.add("expireAfterWrite", (Object) this.expireAfterWriteNanos + "ns");
        }
        if (this.expireAfterAccessNanos != -1) {
            s.add("expireAfterAccess", (Object) this.expireAfterAccessNanos + "ns");
        }
        MapMakerInternalMap.Strength strength = this.keyStrength;
        if (strength != null) {
            s.add("keyStrength", (Object) Ascii.toLowerCase(strength.toString()));
        }
        MapMakerInternalMap.Strength strength2 = this.valueStrength;
        if (strength2 != null) {
            s.add("valueStrength", (Object) Ascii.toLowerCase(strength2.toString()));
        }
        if (this.keyEquivalence != null) {
            s.addValue((Object) "keyEquivalence");
        }
        if (this.valueEquivalence != null) {
            s.addValue((Object) "valueEquivalence");
        }
        if (this.removalListener != null) {
            s.addValue((Object) "removalListener");
        }
        return s.toString();
    }

    static final class RemovalNotification<K, V> extends ImmutableEntry<K, V> {
        private static final long serialVersionUID = 0;
        private final RemovalCause cause;

        RemovalNotification(@Nullable K key, @Nullable V value, RemovalCause cause2) {
            super(key, value);
            this.cause = cause2;
        }

        public RemovalCause getCause() {
            return this.cause;
        }

        public boolean wasEvicted() {
            return this.cause.wasEvicted();
        }
    }

    static class NullConcurrentMap<K, V> extends AbstractMap<K, V> implements ConcurrentMap<K, V>, Serializable {
        private static final long serialVersionUID = 0;
        private final RemovalCause removalCause;
        private final RemovalListener<K, V> removalListener;

        NullConcurrentMap(MapMaker mapMaker) {
            this.removalListener = mapMaker.getRemovalListener();
            this.removalCause = mapMaker.nullRemovalCause;
        }

        public boolean containsKey(@Nullable Object key) {
            return false;
        }

        public boolean containsValue(@Nullable Object value) {
            return false;
        }

        public V get(@Nullable Object key) {
            return null;
        }

        /* access modifiers changed from: package-private */
        public void notifyRemoval(K key, V value) {
            this.removalListener.onRemoval(new RemovalNotification<>(key, value, this.removalCause));
        }

        public V put(K key, V value) {
            Preconditions.checkNotNull(key);
            Preconditions.checkNotNull(value);
            notifyRemoval(key, value);
            return null;
        }

        public V putIfAbsent(K key, V value) {
            return put(key, value);
        }

        public V remove(@Nullable Object key) {
            return null;
        }

        public boolean remove(@Nullable Object key, @Nullable Object value) {
            return false;
        }

        public V replace(K key, V value) {
            Preconditions.checkNotNull(key);
            Preconditions.checkNotNull(value);
            return null;
        }

        public boolean replace(K key, @Nullable V v, V newValue) {
            Preconditions.checkNotNull(key);
            Preconditions.checkNotNull(newValue);
            return false;
        }

        public Set<Map.Entry<K, V>> entrySet() {
            return Collections.emptySet();
        }
    }

    static final class NullComputingConcurrentMap<K, V> extends NullConcurrentMap<K, V> {
        private static final long serialVersionUID = 0;
        final Function<? super K, ? extends V> computingFunction;

        NullComputingConcurrentMap(MapMaker mapMaker, Function<? super K, ? extends V> computingFunction2) {
            super(mapMaker);
            this.computingFunction = (Function) Preconditions.checkNotNull(computingFunction2);
        }

        public V get(Object k) {
            Object obj = k;
            V value = compute(obj);
            Preconditions.checkNotNull(value, this.computingFunction + " returned null for key " + obj + ".");
            notifyRemoval(obj, value);
            return value;
        }

        private V compute(K key) {
            Preconditions.checkNotNull(key);
            try {
                return this.computingFunction.apply(key);
            } catch (ComputationException e) {
                throw e;
            } catch (Throwable t) {
                throw new ComputationException(t);
            }
        }
    }
}
