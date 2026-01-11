package com.google.common.collect;

import com.google.common.base.Equivalence;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.collect.MapMaker;
import com.google.common.collect.MapMakerInternalMap;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.ref.ReferenceQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import javax.annotation.Nullable;

class ComputingConcurrentHashMap<K, V> extends MapMakerInternalMap<K, V> {
    private static final long serialVersionUID = 4;
    final Function<? super K, ? extends V> computingFunction;

    ComputingConcurrentHashMap(MapMaker builder, Function<? super K, ? extends V> computingFunction2) {
        super(builder);
        this.computingFunction = (Function) Preconditions.checkNotNull(computingFunction2);
    }

    /* access modifiers changed from: package-private */
    public MapMakerInternalMap.Segment<K, V> createSegment(int initialCapacity, int maxSegmentSize) {
        return new ComputingSegment(this, initialCapacity, maxSegmentSize);
    }

    /* access modifiers changed from: package-private */
    public ComputingSegment<K, V> segmentFor(int hash) {
        return (ComputingSegment) super.segmentFor(hash);
    }

    /* access modifiers changed from: package-private */
    public V getOrCompute(K key) throws ExecutionException {
        int hash = hash(Preconditions.checkNotNull(key));
        return segmentFor(hash).getOrCompute(key, hash, this.computingFunction);
    }

    static final class ComputingSegment<K, V> extends MapMakerInternalMap.Segment<K, V> {
        ComputingSegment(MapMakerInternalMap<K, V> map, int initialCapacity, int maxSegmentSize) {
            super(map, initialCapacity, maxSegmentSize);
        }

        /* access modifiers changed from: package-private */
        /* JADX WARNING: Code restructure failed: missing block: B:12:0x001f, code lost:
            if (r0.getValueReference().isComputingReference() == false) goto L_0x0021;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x005b, code lost:
            if (r0.getValueReference().isComputingReference() == false) goto L_0x005f;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:24:0x005d, code lost:
            r2 = false;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:0x005f, code lost:
            r10 = r0.getValueReference().get();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:26:0x0067, code lost:
            if (r10 != null) goto L_0x006f;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:0x0069, code lost:
            enqueueNotification(r8, r14, r10, com.google.common.collect.MapMaker.RemovalCause.COLLECTED);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:29:0x0075, code lost:
            if (r12.map.expires() == false) goto L_0x0091;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:31:0x007d, code lost:
            if (r12.map.isExpired(r0) == false) goto L_0x0091;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:32:0x007f, code lost:
            enqueueNotification(r8, r14, r10, com.google.common.collect.MapMaker.RemovalCause.EXPIRED);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:33:0x0084, code lost:
            r12.evictionQueue.remove(r0);
            r12.expirationQueue.remove(r0);
            r12.count = r4;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:34:0x0091, code lost:
            recordLockedRead(r0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:36:?, code lost:
            unlock();
            postWriteCleanup();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:37:0x009b, code lost:
            postReadCleanup();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:38:0x009e, code lost:
            return r10;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public V getOrCompute(K r13, int r14, com.google.common.base.Function<? super K, ? extends V> r15) throws java.util.concurrent.ExecutionException {
            /*
                r12 = this;
            L_0x0000:
                com.google.common.collect.MapMakerInternalMap$ReferenceEntry r0 = r12.getEntry(r13, r14)     // Catch:{ all -> 0x00f6 }
                if (r0 == 0) goto L_0x0014
                java.lang.Object r1 = r12.getLiveValue(r0)     // Catch:{ all -> 0x00f6 }
                if (r1 == 0) goto L_0x0014
                r12.recordRead(r0)     // Catch:{ all -> 0x00f6 }
                r12.postReadCleanup()
                return r1
            L_0x0014:
                r1 = 1
                if (r0 == 0) goto L_0x0021
                com.google.common.collect.MapMakerInternalMap$ValueReference r2 = r0.getValueReference()     // Catch:{ all -> 0x00f6 }
                boolean r2 = r2.isComputingReference()     // Catch:{ all -> 0x00f6 }
                if (r2 != 0) goto L_0x00cf
            L_0x0021:
                r2 = 1
                r3 = 0
                r12.lock()     // Catch:{ all -> 0x00f6 }
                r12.preWriteCleanup()     // Catch:{ all -> 0x00ee }
                int r4 = r12.count     // Catch:{ all -> 0x00ee }
                int r4 = r4 - r1
                java.util.concurrent.atomic.AtomicReferenceArray r5 = r12.table     // Catch:{ all -> 0x00ee }
                int r6 = r5.length()     // Catch:{ all -> 0x00ee }
                int r6 = r6 - r1
                r6 = r6 & r14
                java.lang.Object r7 = r5.get(r6)     // Catch:{ all -> 0x00ee }
                com.google.common.collect.MapMakerInternalMap$ReferenceEntry r7 = (com.google.common.collect.MapMakerInternalMap.ReferenceEntry) r7     // Catch:{ all -> 0x00ee }
                r0 = r7
            L_0x003b:
                if (r0 == 0) goto L_0x00a5
                java.lang.Object r8 = r0.getKey()     // Catch:{ all -> 0x00ee }
                int r9 = r0.getHash()     // Catch:{ all -> 0x00ee }
                if (r9 != r14) goto L_0x009f
                if (r8 == 0) goto L_0x009f
                com.google.common.collect.MapMakerInternalMap r9 = r12.map     // Catch:{ all -> 0x00ee }
                com.google.common.base.Equivalence<java.lang.Object> r9 = r9.keyEquivalence     // Catch:{ all -> 0x00ee }
                boolean r9 = r9.equivalent(r13, r8)     // Catch:{ all -> 0x00ee }
                if (r9 == 0) goto L_0x009f
                com.google.common.collect.MapMakerInternalMap$ValueReference r9 = r0.getValueReference()     // Catch:{ all -> 0x00ee }
                boolean r10 = r9.isComputingReference()     // Catch:{ all -> 0x00ee }
                if (r10 == 0) goto L_0x005f
                r2 = 0
                goto L_0x00a5
            L_0x005f:
                com.google.common.collect.MapMakerInternalMap$ValueReference r10 = r0.getValueReference()     // Catch:{ all -> 0x00ee }
                java.lang.Object r10 = r10.get()     // Catch:{ all -> 0x00ee }
                if (r10 != 0) goto L_0x006f
                com.google.common.collect.MapMaker$RemovalCause r11 = com.google.common.collect.MapMaker.RemovalCause.COLLECTED     // Catch:{ all -> 0x00ee }
                r12.enqueueNotification(r8, r14, r10, r11)     // Catch:{ all -> 0x00ee }
                goto L_0x0084
            L_0x006f:
                com.google.common.collect.MapMakerInternalMap r11 = r12.map     // Catch:{ all -> 0x00ee }
                boolean r11 = r11.expires()     // Catch:{ all -> 0x00ee }
                if (r11 == 0) goto L_0x0091
                com.google.common.collect.MapMakerInternalMap r11 = r12.map     // Catch:{ all -> 0x00ee }
                boolean r11 = r11.isExpired(r0)     // Catch:{ all -> 0x00ee }
                if (r11 == 0) goto L_0x0091
                com.google.common.collect.MapMaker$RemovalCause r11 = com.google.common.collect.MapMaker.RemovalCause.EXPIRED     // Catch:{ all -> 0x00ee }
                r12.enqueueNotification(r8, r14, r10, r11)     // Catch:{ all -> 0x00ee }
            L_0x0084:
                java.util.Queue r11 = r12.evictionQueue     // Catch:{ all -> 0x00ee }
                r11.remove(r0)     // Catch:{ all -> 0x00ee }
                java.util.Queue r11 = r12.expirationQueue     // Catch:{ all -> 0x00ee }
                r11.remove(r0)     // Catch:{ all -> 0x00ee }
                r12.count = r4     // Catch:{ all -> 0x00ee }
                goto L_0x00a5
            L_0x0091:
                r12.recordLockedRead(r0)     // Catch:{ all -> 0x00ee }
                r12.unlock()     // Catch:{ all -> 0x00f6 }
                r12.postWriteCleanup()     // Catch:{ all -> 0x00f6 }
                r12.postReadCleanup()
                return r10
            L_0x009f:
                com.google.common.collect.MapMakerInternalMap$ReferenceEntry r8 = r0.getNext()     // Catch:{ all -> 0x00ee }
                r0 = r8
                goto L_0x003b
            L_0x00a5:
                if (r2 == 0) goto L_0x00be
                com.google.common.collect.ComputingConcurrentHashMap$ComputingValueReference r8 = new com.google.common.collect.ComputingConcurrentHashMap$ComputingValueReference     // Catch:{ all -> 0x00ee }
                r8.<init>(r15)     // Catch:{ all -> 0x00ee }
                r3 = r8
                if (r0 != 0) goto L_0x00bb
                com.google.common.collect.MapMakerInternalMap$ReferenceEntry r8 = r12.newEntry(r13, r14, r7)     // Catch:{ all -> 0x00ee }
                r0 = r8
                r0.setValueReference(r3)     // Catch:{ all -> 0x00ee }
                r5.set(r6, r0)     // Catch:{ all -> 0x00ee }
                goto L_0x00be
            L_0x00bb:
                r0.setValueReference(r3)     // Catch:{ all -> 0x00ee }
            L_0x00be:
                r12.unlock()     // Catch:{ all -> 0x00f6 }
                r12.postWriteCleanup()     // Catch:{ all -> 0x00f6 }
                if (r2 == 0) goto L_0x00cf
                java.lang.Object r1 = r12.compute(r13, r14, r0, r3)     // Catch:{ all -> 0x00f6 }
                r12.postReadCleanup()
                return r1
            L_0x00cf:
                boolean r2 = java.lang.Thread.holdsLock(r0)     // Catch:{ all -> 0x00f6 }
                if (r2 != 0) goto L_0x00d6
                goto L_0x00d7
            L_0x00d6:
                r1 = 0
            L_0x00d7:
                java.lang.String r2 = "Recursive computation"
                com.google.common.base.Preconditions.checkState(r1, r2)     // Catch:{ all -> 0x00f6 }
                com.google.common.collect.MapMakerInternalMap$ValueReference r1 = r0.getValueReference()     // Catch:{ all -> 0x00f6 }
                java.lang.Object r1 = r1.waitForValue()     // Catch:{ all -> 0x00f6 }
                if (r1 == 0) goto L_0x0000
                r12.recordRead(r0)     // Catch:{ all -> 0x00f6 }
                r12.postReadCleanup()
                return r1
            L_0x00ee:
                r1 = move-exception
                r12.unlock()     // Catch:{ all -> 0x00f6 }
                r12.postWriteCleanup()     // Catch:{ all -> 0x00f6 }
                throw r1     // Catch:{ all -> 0x00f6 }
            L_0x00f6:
                r0 = move-exception
                r12.postReadCleanup()
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.ComputingConcurrentHashMap.ComputingSegment.getOrCompute(java.lang.Object, int, com.google.common.base.Function):java.lang.Object");
        }

        /* access modifiers changed from: package-private */
        public V compute(K key, int hash, MapMakerInternalMap.ReferenceEntry<K, V> e, ComputingValueReference<K, V> computingValueReference) throws ExecutionException {
            V value = null;
            long nanoTime = System.nanoTime();
            long end = 0;
            try {
                synchronized (e) {
                    value = computingValueReference.compute(key, hash);
                    end = System.nanoTime();
                }
                if (value != null) {
                    if (put(key, hash, value, true) != null) {
                        enqueueNotification(key, hash, value, MapMaker.RemovalCause.REPLACED);
                    }
                }
                if (end == 0) {
                    long end2 = System.nanoTime();
                }
                if (value == null) {
                    clearValue(key, hash, computingValueReference);
                }
                return value;
            } catch (Throwable th) {
                if (end == 0) {
                    long end3 = System.nanoTime();
                }
                if (value == null) {
                    clearValue(key, hash, computingValueReference);
                }
                throw th;
            }
        }
    }

    private static final class ComputationExceptionReference<K, V> implements MapMakerInternalMap.ValueReference<K, V> {

        /* renamed from: t */
        final Throwable f69t;

        ComputationExceptionReference(Throwable t) {
            this.f69t = t;
        }

        public V get() {
            return null;
        }

        public MapMakerInternalMap.ReferenceEntry<K, V> getEntry() {
            return null;
        }

        public MapMakerInternalMap.ValueReference<K, V> copyFor(ReferenceQueue<V> referenceQueue, V v, MapMakerInternalMap.ReferenceEntry<K, V> referenceEntry) {
            return this;
        }

        public boolean isComputingReference() {
            return false;
        }

        public V waitForValue() throws ExecutionException {
            throw new ExecutionException(this.f69t);
        }

        public void clear(MapMakerInternalMap.ValueReference<K, V> valueReference) {
        }
    }

    private static final class ComputedReference<K, V> implements MapMakerInternalMap.ValueReference<K, V> {
        final V value;

        ComputedReference(@Nullable V value2) {
            this.value = value2;
        }

        public V get() {
            return this.value;
        }

        public MapMakerInternalMap.ReferenceEntry<K, V> getEntry() {
            return null;
        }

        public MapMakerInternalMap.ValueReference<K, V> copyFor(ReferenceQueue<V> referenceQueue, V v, MapMakerInternalMap.ReferenceEntry<K, V> referenceEntry) {
            return this;
        }

        public boolean isComputingReference() {
            return false;
        }

        public V waitForValue() {
            return get();
        }

        public void clear(MapMakerInternalMap.ValueReference<K, V> valueReference) {
        }
    }

    private static final class ComputingValueReference<K, V> implements MapMakerInternalMap.ValueReference<K, V> {
        volatile MapMakerInternalMap.ValueReference<K, V> computedReference = MapMakerInternalMap.unset();
        final Function<? super K, ? extends V> computingFunction;

        public ComputingValueReference(Function<? super K, ? extends V> computingFunction2) {
            this.computingFunction = computingFunction2;
        }

        public V get() {
            return null;
        }

        public MapMakerInternalMap.ReferenceEntry<K, V> getEntry() {
            return null;
        }

        public MapMakerInternalMap.ValueReference<K, V> copyFor(ReferenceQueue<V> referenceQueue, V v, MapMakerInternalMap.ReferenceEntry<K, V> referenceEntry) {
            return this;
        }

        public boolean isComputingReference() {
            return true;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:22:0x0024, code lost:
            r0 = th;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public V waitForValue() throws java.util.concurrent.ExecutionException {
            /*
                r4 = this;
                com.google.common.collect.MapMakerInternalMap$ValueReference<K, V> r0 = r4.computedReference
                com.google.common.collect.MapMakerInternalMap$ValueReference<java.lang.Object, java.lang.Object> r1 = com.google.common.collect.MapMakerInternalMap.UNSET
                if (r0 != r1) goto L_0x0034
                r0 = 0
                monitor-enter(r4)     // Catch:{ all -> 0x0026 }
                r1 = r0
            L_0x0009:
                com.google.common.collect.MapMakerInternalMap$ValueReference<K, V> r0 = r4.computedReference     // Catch:{ all -> 0x0021 }
                com.google.common.collect.MapMakerInternalMap$ValueReference<java.lang.Object, java.lang.Object> r2 = com.google.common.collect.MapMakerInternalMap.UNSET     // Catch:{ all -> 0x0021 }
                if (r0 != r2) goto L_0x0016
                r4.wait()     // Catch:{ InterruptedException -> 0x0013 }
            L_0x0012:
                goto L_0x0009
            L_0x0013:
                r0 = move-exception
                r1 = 1
                goto L_0x0012
            L_0x0016:
                monitor-exit(r4)     // Catch:{ all -> 0x0021 }
                if (r1 == 0) goto L_0x0034
                java.lang.Thread r0 = java.lang.Thread.currentThread()
                r0.interrupt()
                goto L_0x0034
            L_0x0021:
                r0 = move-exception
                monitor-exit(r4)     // Catch:{ all -> 0x0021 }
                throw r0     // Catch:{ all -> 0x0024 }
            L_0x0024:
                r0 = move-exception
                goto L_0x002a
            L_0x0026:
                r1 = move-exception
                r3 = r1
                r1 = r0
                r0 = r3
            L_0x002a:
                if (r1 == 0) goto L_0x0033
                java.lang.Thread r2 = java.lang.Thread.currentThread()
                r2.interrupt()
            L_0x0033:
                throw r0
            L_0x0034:
                com.google.common.collect.MapMakerInternalMap$ValueReference<K, V> r0 = r4.computedReference
                java.lang.Object r0 = r0.waitForValue()
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.ComputingConcurrentHashMap.ComputingValueReference.waitForValue():java.lang.Object");
        }

        public void clear(MapMakerInternalMap.ValueReference<K, V> newValue) {
            setValueReference(newValue);
        }

        /* access modifiers changed from: package-private */
        public V compute(K key, int hash) throws ExecutionException {
            try {
                V value = this.computingFunction.apply(key);
                setValueReference(new ComputedReference(value));
                return value;
            } catch (Throwable t) {
                setValueReference(new ComputationExceptionReference(t));
                throw new ExecutionException(t);
            }
        }

        /* access modifiers changed from: package-private */
        public void setValueReference(MapMakerInternalMap.ValueReference<K, V> valueReference) {
            synchronized (this) {
                if (this.computedReference == MapMakerInternalMap.UNSET) {
                    this.computedReference = valueReference;
                    notifyAll();
                }
            }
        }
    }

    static final class ComputingMapAdapter<K, V> extends ComputingConcurrentHashMap<K, V> implements Serializable {
        private static final long serialVersionUID = 0;

        /* access modifiers changed from: package-private */
        public /* bridge */ /* synthetic */ MapMakerInternalMap.Segment segmentFor(int x0) {
            return ComputingConcurrentHashMap.super.segmentFor(x0);
        }

        ComputingMapAdapter(MapMaker mapMaker, Function<? super K, ? extends V> computingFunction) {
            super(mapMaker, computingFunction);
        }

        public V get(Object key) {
            try {
                V value = getOrCompute(key);
                if (value != null) {
                    return value;
                }
                throw new NullPointerException(this.computingFunction + " returned null for key " + key + ".");
            } catch (ExecutionException e) {
                Throwable cause = e.getCause();
                Throwables.propagateIfInstanceOf(cause, ComputationException.class);
                throw new ComputationException(cause);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public Object writeReplace() {
        return new ComputingSerializationProxy(this.keyStrength, this.valueStrength, this.keyEquivalence, this.valueEquivalence, this.expireAfterWriteNanos, this.expireAfterAccessNanos, this.maximumSize, this.concurrencyLevel, this.removalListener, this, this.computingFunction);
    }

    static final class ComputingSerializationProxy<K, V> extends MapMakerInternalMap.AbstractSerializationProxy<K, V> {
        private static final long serialVersionUID = 4;
        final Function<? super K, ? extends V> computingFunction;

        ComputingSerializationProxy(MapMakerInternalMap.Strength keyStrength, MapMakerInternalMap.Strength valueStrength, Equivalence<Object> keyEquivalence, Equivalence<Object> valueEquivalence, long expireAfterWriteNanos, long expireAfterAccessNanos, int maximumSize, int concurrencyLevel, MapMaker.RemovalListener<? super K, ? super V> removalListener, ConcurrentMap<K, V> delegate, Function<? super K, ? extends V> computingFunction2) {
            super(keyStrength, valueStrength, keyEquivalence, valueEquivalence, expireAfterWriteNanos, expireAfterAccessNanos, maximumSize, concurrencyLevel, removalListener, delegate);
            this.computingFunction = computingFunction2;
        }

        private void writeObject(ObjectOutputStream out) throws IOException {
            out.defaultWriteObject();
            writeMapTo(out);
        }

        private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
            in.defaultReadObject();
            this.delegate = readMapMaker(in).makeComputingMap(this.computingFunction);
            readEntries(in);
        }

        /* access modifiers changed from: package-private */
        public Object readResolve() {
            return this.delegate;
        }
    }
}
