package org.jboss.netty.util.internal;

import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantLock;
import org.xbill.DNS.TTL;

public final class ConcurrentHashMap<K, V> extends AbstractMap<K, V> implements ConcurrentMap<K, V> {
    static final int DEFAULT_CONCURRENCY_LEVEL = 16;
    static final int DEFAULT_INITIAL_CAPACITY = 16;
    static final float DEFAULT_LOAD_FACTOR = 0.75f;
    static final int MAXIMUM_CAPACITY = 1073741824;
    static final int MAX_SEGMENTS = 65536;
    static final int RETRIES_BEFORE_LOCK = 2;
    Set<Map.Entry<K, V>> entrySet;
    Set<K> keySet;
    final int segmentMask;
    final int segmentShift;
    final Segment<K, V>[] segments;
    Collection<V> values;

    private static int hash(int h) {
        int h2 = h + ((h << 15) ^ -12931);
        int h3 = h2 ^ (h2 >>> 10);
        int h4 = h3 + (h3 << 3);
        int h5 = h4 ^ (h4 >>> 6);
        int h6 = h5 + (h5 << 2) + (h5 << 14);
        return (h6 >>> 16) ^ h6;
    }

    /* access modifiers changed from: package-private */
    public Segment<K, V> segmentFor(int hash) {
        return this.segments[(hash >>> this.segmentShift) & this.segmentMask];
    }

    private static int hashOf(Object key) {
        return hash(key.hashCode());
    }

    static final class HashEntry<K, V> {
        final int hash;
        final Object key;
        final HashEntry<K, V> next;
        volatile Object value;

        HashEntry(K key2, int hash2, HashEntry<K, V> next2, V value2) {
            this.hash = hash2;
            this.next = next2;
            this.key = key2;
            this.value = value2;
        }

        /* access modifiers changed from: package-private */
        public K key() {
            return this.key;
        }

        /* access modifiers changed from: package-private */
        public V value() {
            return this.value;
        }

        /* access modifiers changed from: package-private */
        public void setValue(V value2) {
            this.value = value2;
        }

        static <K, V> HashEntry<K, V>[] newArray(int i) {
            return new HashEntry[i];
        }
    }

    static final class Segment<K, V> extends ReentrantLock {
        private static final long serialVersionUID = -2001752926705396395L;
        volatile transient int count;
        final float loadFactor;
        int modCount;
        volatile transient HashEntry<K, V>[] table;
        int threshold;

        Segment(int initialCapacity, float lf) {
            this.loadFactor = lf;
            setTable(HashEntry.newArray(initialCapacity));
        }

        static <K, V> Segment<K, V>[] newArray(int i) {
            return new Segment[i];
        }

        private static boolean keyEq(Object src2, Object dest) {
            return src2.equals(dest);
        }

        /* access modifiers changed from: package-private */
        public void setTable(HashEntry<K, V>[] newTable) {
            this.threshold = (int) (((float) newTable.length) * this.loadFactor);
            this.table = newTable;
        }

        /* access modifiers changed from: package-private */
        public HashEntry<K, V> getFirst(int hash) {
            HashEntry<K, V>[] tab = this.table;
            return tab[(tab.length - 1) & hash];
        }

        /* access modifiers changed from: package-private */
        public HashEntry<K, V> newHashEntry(K key, int hash, HashEntry<K, V> next, V value) {
            return new HashEntry<>(key, hash, next, value);
        }

        /* access modifiers changed from: package-private */
        public V readValueUnderLock(HashEntry<K, V> e) {
            lock();
            try {
                return e.value();
            } finally {
                unlock();
            }
        }

        /* access modifiers changed from: package-private */
        public V get(Object key, int hash) {
            if (this.count == 0) {
                return null;
            }
            HashEntry<K, V> e = getFirst(hash);
            while (e != null) {
                if (e.hash != hash || !keyEq(key, e.key())) {
                    e = e.next;
                } else {
                    V opaque = e.value();
                    if (opaque != null) {
                        return opaque;
                    }
                    return readValueUnderLock(e);
                }
            }
            return null;
        }

        /* access modifiers changed from: package-private */
        public boolean containsKey(Object key, int hash) {
            if (this.count == 0) {
                return false;
            }
            for (HashEntry<K, V> e = getFirst(hash); e != null; e = e.next) {
                if (e.hash == hash && keyEq(key, e.key())) {
                    return true;
                }
            }
            return false;
        }

        /* access modifiers changed from: package-private */
        public boolean containsValue(Object value) {
            V v;
            if (this.count == 0) {
                return false;
            }
            for (HashEntry<K, V> e : this.table) {
                while (e != null) {
                    V opaque = e.value();
                    if (opaque == null) {
                        v = readValueUnderLock(e);
                    } else {
                        v = opaque;
                    }
                    if (value.equals(v)) {
                        return true;
                    }
                    e = e.next;
                }
            }
            return false;
        }

        /* JADX INFO: finally extract failed */
        /* access modifiers changed from: package-private */
        public boolean replace(K key, int hash, V oldValue, V newValue) {
            lock();
            try {
                HashEntry<K, V> e = getFirst(hash);
                while (e != null && (e.hash != hash || !keyEq(key, e.key()))) {
                    e = e.next;
                }
                boolean replaced = false;
                if (e != null && oldValue.equals(e.value())) {
                    replaced = true;
                    e.setValue(newValue);
                }
                unlock();
                boolean z = replaced;
                return replaced;
            } catch (Throwable th) {
                unlock();
                throw th;
            }
        }

        /* JADX INFO: finally extract failed */
        /* access modifiers changed from: package-private */
        public V replace(K key, int hash, V newValue) {
            lock();
            try {
                HashEntry<K, V> e = getFirst(hash);
                while (e != null && (e.hash != hash || !keyEq(key, e.key()))) {
                    e = e.next;
                }
                V oldValue = null;
                if (e != null) {
                    oldValue = e.value();
                    e.setValue(newValue);
                }
                unlock();
                V v = oldValue;
                return oldValue;
            } catch (Throwable th) {
                unlock();
                throw th;
            }
        }

        /* JADX INFO: finally extract failed */
        /* access modifiers changed from: package-private */
        public V put(K key, int hash, V value, boolean onlyIfAbsent) {
            V oldValue;
            int reduced;
            lock();
            try {
                int c = this.count;
                int c2 = c + 1;
                if (c > this.threshold && (reduced = rehash()) > 0) {
                    int i = c2 - reduced;
                    c2 = i;
                    this.count = i - 1;
                }
                HashEntry<K, V>[] tab = this.table;
                int index = (tab.length - 1) & hash;
                HashEntry<K, V> first = tab[index];
                HashEntry<K, V> e = first;
                while (e != null && (e.hash != hash || !keyEq(key, e.key()))) {
                    e = e.next;
                }
                if (e != null) {
                    oldValue = e.value();
                    if (!onlyIfAbsent) {
                        e.setValue(value);
                    }
                } else {
                    oldValue = null;
                    this.modCount++;
                    tab[index] = newHashEntry(key, hash, first, value);
                    this.count = c2;
                }
                unlock();
                V v = oldValue;
                return oldValue;
            } catch (Throwable th) {
                unlock();
                throw th;
            }
        }

        /* access modifiers changed from: package-private */
        public int rehash() {
            int oldCapacity;
            HashEntry<K, V>[] oldTable;
            HashEntry<K, V>[] oldTable2 = this.table;
            int oldCapacity2 = oldTable2.length;
            if (oldCapacity2 >= 1073741824) {
                return 0;
            }
            HashEntry<K, V>[] newTable = HashEntry.newArray(oldCapacity2 << 1);
            this.threshold = (int) (((float) newTable.length) * this.loadFactor);
            int sizeMask = newTable.length - 1;
            int reduce = 0;
            int i = 0;
            while (i < oldCapacity2) {
                HashEntry<K, V> e = oldTable2[i];
                if (e != null) {
                    HashEntry<K, V> next = e.next;
                    int idx = e.hash & sizeMask;
                    if (next == null) {
                        newTable[idx] = e;
                    } else {
                        HashEntry<K, V> lastRun = e;
                        int lastIdx = idx;
                        for (HashEntry<K, V> last = next; last != null; last = last.next) {
                            int k = last.hash & sizeMask;
                            if (k != lastIdx) {
                                lastIdx = k;
                                lastRun = last;
                            }
                        }
                        newTable[lastIdx] = lastRun;
                        HashEntry<K, V> p = e;
                        while (p != lastRun) {
                            K key = p.key();
                            if (key == null) {
                                reduce++;
                                oldTable = oldTable2;
                                oldCapacity = oldCapacity2;
                            } else {
                                int k2 = p.hash & sizeMask;
                                oldTable = oldTable2;
                                oldCapacity = oldCapacity2;
                                newTable[k2] = newHashEntry(key, p.hash, newTable[k2], p.value());
                            }
                            p = p.next;
                            oldTable2 = oldTable;
                            oldCapacity2 = oldCapacity;
                        }
                    }
                }
                i++;
                oldTable2 = oldTable2;
                oldCapacity2 = oldCapacity2;
            }
            this.table = newTable;
            return reduce;
        }

        /* JADX INFO: finally extract failed */
        /* access modifiers changed from: package-private */
        public V remove(Object key, int hash, Object value, boolean refRemove) {
            Object obj = key;
            int i = hash;
            Object obj2 = value;
            lock();
            try {
                int c = this.count - 1;
                HashEntry<K, V>[] tab = this.table;
                int index = (tab.length - 1) & i;
                HashEntry<K, V> first = tab[index];
                HashEntry<K, V> e = first;
                while (e != null && obj != e.key && (refRemove || i != e.hash || !keyEq(obj, e.key()))) {
                    e = e.next;
                }
                V oldValue = null;
                if (e != null) {
                    V v = e.value();
                    if (obj2 == null || obj2.equals(v)) {
                        oldValue = v;
                        this.modCount++;
                        HashEntry<K, V> newFirst = e.next;
                        for (HashEntry<K, V> p = first; p != e; p = p.next) {
                            K pKey = p.key();
                            if (pKey == null) {
                                c--;
                            } else {
                                newFirst = newHashEntry(pKey, p.hash, newFirst, p.value());
                            }
                        }
                        tab[index] = newFirst;
                        this.count = c;
                    }
                }
                unlock();
                V v2 = oldValue;
                return oldValue;
            } catch (Throwable th) {
                unlock();
                throw th;
            }
        }

        /* access modifiers changed from: package-private */
        public void clear() {
            if (this.count != 0) {
                lock();
                try {
                    HashEntry<K, V>[] tab = this.table;
                    for (int i = 0; i < tab.length; i++) {
                        tab[i] = null;
                    }
                    this.modCount++;
                    this.count = 0;
                } finally {
                    unlock();
                }
            }
        }
    }

    public ConcurrentHashMap(int initialCapacity, float loadFactor, int concurrencyLevel) {
        if (loadFactor <= 0.0f || initialCapacity < 0 || concurrencyLevel <= 0) {
            throw new IllegalArgumentException();
        }
        int sshift = 0;
        int ssize = 1;
        while (ssize < (concurrencyLevel > 65536 ? 65536 : concurrencyLevel)) {
            sshift++;
            ssize <<= 1;
        }
        this.segmentShift = 32 - sshift;
        this.segmentMask = ssize - 1;
        this.segments = Segment.newArray(ssize);
        initialCapacity = initialCapacity > 1073741824 ? 1073741824 : initialCapacity;
        int c = initialCapacity / ssize;
        int cap = 1;
        while (cap < (c * ssize < initialCapacity ? c + 1 : c)) {
            cap <<= 1;
        }
        int i = 0;
        while (true) {
            Segment<K, V>[] segmentArr = this.segments;
            if (i < segmentArr.length) {
                segmentArr[i] = new Segment<>(cap, loadFactor);
                i++;
            } else {
                return;
            }
        }
    }

    public ConcurrentHashMap(int initialCapacity, float loadFactor) {
        this(initialCapacity, loadFactor, 16);
    }

    public ConcurrentHashMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR, 16);
    }

    public ConcurrentHashMap() {
        this(16, DEFAULT_LOAD_FACTOR, 16);
    }

    public ConcurrentHashMap(Map<? extends K, ? extends V> m) {
        this(Math.max(((int) (((float) m.size()) / DEFAULT_LOAD_FACTOR)) + 1, 16), DEFAULT_LOAD_FACTOR, 16);
        putAll(m);
    }

    public boolean isEmpty() {
        Segment<K, V>[] segments2 = this.segments;
        int[] mc = new int[segments2.length];
        int mcsum = 0;
        for (int i = 0; i < segments2.length; i++) {
            if (segments2[i].count != 0) {
                return false;
            }
            int i2 = segments2[i].modCount;
            mc[i] = i2;
            mcsum += i2;
        }
        if (mcsum == 0) {
            return true;
        }
        for (int i3 = 0; i3 < segments2.length; i3++) {
            if (segments2[i3].count != 0 || mc[i3] != segments2[i3].modCount) {
                return false;
            }
        }
        return true;
    }

    public int size() {
        Segment<K, V>[] segments2 = this.segments;
        long sum = 0;
        long check = 0;
        int[] mc = new int[segments2.length];
        for (int k = 0; k < 2; k++) {
            check = 0;
            sum = 0;
            int mcsum = 0;
            for (int i = 0; i < segments2.length; i++) {
                sum += (long) segments2[i].count;
                int i2 = segments2[i].modCount;
                mc[i] = i2;
                mcsum += i2;
            }
            if (mcsum != 0) {
                int i3 = 0;
                while (true) {
                    if (i3 >= segments2.length) {
                        break;
                    }
                    check += (long) segments2[i3].count;
                    if (mc[i3] != segments2[i3].modCount) {
                        check = -1;
                        break;
                    }
                    i3++;
                }
            }
            if (check == sum) {
                break;
            }
        }
        if (check != sum) {
            long sum2 = 0;
            for (Segment<K, V> lock : segments2) {
                lock.lock();
            }
            for (Segment<K, V> segment : segments2) {
                sum2 = sum + ((long) segment.count);
            }
            for (Segment<K, V> unlock : segments2) {
                unlock.unlock();
            }
        }
        if (sum > TTL.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        return (int) sum;
    }

    public V get(Object key) {
        int hash = hashOf(key);
        return segmentFor(hash).get(key, hash);
    }

    public boolean containsKey(Object key) {
        int hash = hashOf(key);
        return segmentFor(hash).containsKey(key, hash);
    }

    /*  JADX ERROR: StackOverflow in pass: MarkFinallyVisitor
        jadx.core.utils.exceptions.JadxOverflowException: 
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:47)
        	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:81)
        */
    public boolean containsValue(java.lang.Object r9) {
        /*
            r8 = this;
            if (r9 == 0) goto L_0x007b
            org.jboss.netty.util.internal.ConcurrentHashMap$Segment<K, V>[] r0 = r8.segments
            int r1 = r0.length
            int[] r1 = new int[r1]
            r2 = 0
        L_0x0008:
            r3 = 2
            if (r2 >= r3) goto L_0x003f
            r3 = 0
            r4 = 0
        L_0x000d:
            int r5 = r0.length
            if (r4 >= r5) goto L_0x0024
            r5 = r0[r4]
            int r5 = r5.modCount
            r1[r4] = r5
            int r3 = r3 + r5
            r5 = r0[r4]
            boolean r5 = r5.containsValue(r9)
            if (r5 == 0) goto L_0x0021
            r5 = 1
            return r5
        L_0x0021:
            int r4 = r4 + 1
            goto L_0x000d
        L_0x0024:
            r4 = 1
            if (r3 == 0) goto L_0x0038
            r5 = 0
        L_0x0028:
            int r6 = r0.length
            if (r5 >= r6) goto L_0x0038
            r6 = r1[r5]
            r7 = r0[r5]
            int r7 = r7.modCount
            if (r6 == r7) goto L_0x0035
            r4 = 0
            goto L_0x0038
        L_0x0035:
            int r5 = r5 + 1
            goto L_0x0028
        L_0x0038:
            if (r4 == 0) goto L_0x003c
            r5 = 0
            return r5
        L_0x003c:
            int r2 = r2 + 1
            goto L_0x0008
        L_0x003f:
            r2 = 0
        L_0x0040:
            int r3 = r0.length
            if (r2 >= r3) goto L_0x004b
            r3 = r0[r2]
            r3.lock()
            int r2 = r2 + 1
            goto L_0x0040
        L_0x004b:
            r2 = 0
            r3 = 0
        L_0x004d:
            int r4 = r0.length     // Catch:{ all -> 0x006d }
            if (r3 >= r4) goto L_0x005d
            r4 = r0[r3]     // Catch:{ all -> 0x006d }
            boolean r4 = r4.containsValue(r9)     // Catch:{ all -> 0x006d }
            if (r4 == 0) goto L_0x005a
            r2 = 1
            goto L_0x005d
        L_0x005a:
            int r3 = r3 + 1
            goto L_0x004d
        L_0x005d:
            r3 = 0
        L_0x005f:
            int r4 = r0.length
            if (r3 >= r4) goto L_0x006a
            r4 = r0[r3]
            r4.unlock()
            int r3 = r3 + 1
            goto L_0x005f
        L_0x006a:
            return r2
        L_0x006d:
            r3 = move-exception
            r4 = 0
        L_0x006f:
            int r5 = r0.length
            if (r4 >= r5) goto L_0x007a
            r5 = r0[r4]
            r5.unlock()
            int r4 = r4 + 1
            goto L_0x006f
        L_0x007a:
            throw r3
        L_0x007b:
            r0 = 0
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jboss.netty.util.internal.ConcurrentHashMap.containsValue(java.lang.Object):boolean");
    }

    public boolean contains(Object value) {
        return containsValue(value);
    }

    public V put(K key, V value) {
        if (value != null) {
            int hash = hashOf(key);
            return segmentFor(hash).put(key, hash, value, false);
        }
        throw null;
    }

    public V putIfAbsent(K key, V value) {
        if (value != null) {
            int hash = hashOf(key);
            return segmentFor(hash).put(key, hash, value, true);
        }
        throw null;
    }

    public void putAll(Map<? extends K, ? extends V> m) {
        for (Map.Entry<? extends K, ? extends V> e : m.entrySet()) {
            put(e.getKey(), e.getValue());
        }
    }

    public V remove(Object key) {
        int hash = hashOf(key);
        return segmentFor(hash).remove(key, hash, (Object) null, false);
    }

    public boolean remove(Object key, Object value) {
        int hash = hashOf(key);
        if (value == null || segmentFor(hash).remove(key, hash, value, false) == null) {
            return false;
        }
        return true;
    }

    public boolean replace(K key, V oldValue, V newValue) {
        if (oldValue == null || newValue == null) {
            throw null;
        }
        int hash = hashOf(key);
        return segmentFor(hash).replace(key, hash, oldValue, newValue);
    }

    public V replace(K key, V value) {
        if (value != null) {
            int hash = hashOf(key);
            return segmentFor(hash).replace(key, hash, value);
        }
        throw null;
    }

    public void clear() {
        int i = 0;
        while (true) {
            Segment<K, V>[] segmentArr = this.segments;
            if (i < segmentArr.length) {
                segmentArr[i].clear();
                i++;
            } else {
                return;
            }
        }
    }

    public Set<K> keySet() {
        Set<K> ks = this.keySet;
        if (ks != null) {
            return ks;
        }
        KeySet keySet2 = new KeySet();
        this.keySet = keySet2;
        return keySet2;
    }

    public Collection<V> values() {
        Collection<V> vs = this.values;
        if (vs != null) {
            return vs;
        }
        Values values2 = new Values();
        this.values = values2;
        return values2;
    }

    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> es = this.entrySet;
        if (es != null) {
            return es;
        }
        EntrySet entrySet2 = new EntrySet();
        this.entrySet = entrySet2;
        return entrySet2;
    }

    public Enumeration<K> keys() {
        return new KeyIterator();
    }

    public Enumeration<V> elements() {
        return new ValueIterator();
    }

    abstract class HashIterator {
        K currentKey;
        HashEntry<K, V>[] currentTable;
        HashEntry<K, V> lastReturned;
        HashEntry<K, V> nextEntry;
        int nextSegmentIndex;
        int nextTableIndex = -1;

        HashIterator() {
            this.nextSegmentIndex = ConcurrentHashMap.this.segments.length - 1;
            advance();
        }

        public void rewind() {
            this.nextSegmentIndex = ConcurrentHashMap.this.segments.length - 1;
            this.nextTableIndex = -1;
            this.currentTable = null;
            this.nextEntry = null;
            this.lastReturned = null;
            this.currentKey = null;
            advance();
        }

        public boolean hasMoreElements() {
            return hasNext();
        }

        /* access modifiers changed from: package-private */
        public final void advance() {
            HashEntry<K, V> hashEntry;
            HashEntry<K, V> hashEntry2 = this.nextEntry;
            if (hashEntry2 != null) {
                HashEntry<K, V> hashEntry3 = hashEntry2.next;
                this.nextEntry = hashEntry3;
                if (hashEntry3 != null) {
                    return;
                }
            }
            do {
                int i = this.nextTableIndex;
                if (i >= 0) {
                    HashEntry<K, V>[] hashEntryArr = this.currentTable;
                    this.nextTableIndex = i - 1;
                    hashEntry = hashEntryArr[i];
                    this.nextEntry = hashEntry;
                } else {
                    while (this.nextSegmentIndex >= 0) {
                        Segment<K, V>[] segmentArr = ConcurrentHashMap.this.segments;
                        int i2 = this.nextSegmentIndex;
                        this.nextSegmentIndex = i2 - 1;
                        Segment<K, V> seg = segmentArr[i2];
                        if (seg.count != 0) {
                            HashEntry<K, V>[] hashEntryArr2 = seg.table;
                            this.currentTable = hashEntryArr2;
                            for (int j = hashEntryArr2.length - 1; j >= 0; j--) {
                                HashEntry<K, V> hashEntry4 = this.currentTable[j];
                                this.nextEntry = hashEntry4;
                                if (hashEntry4 != null) {
                                    this.nextTableIndex = j - 1;
                                    return;
                                }
                            }
                            continue;
                        }
                    }
                    return;
                }
            } while (hashEntry == null);
        }

        public boolean hasNext() {
            while (true) {
                HashEntry<K, V> hashEntry = this.nextEntry;
                if (hashEntry == null) {
                    return false;
                }
                if (hashEntry.key() != null) {
                    return true;
                }
                advance();
            }
        }

        /* access modifiers changed from: package-private */
        public HashEntry<K, V> nextEntry() {
            do {
                HashEntry<K, V> hashEntry = this.nextEntry;
                if (hashEntry != null) {
                    this.lastReturned = hashEntry;
                    this.currentKey = hashEntry.key();
                    advance();
                } else {
                    throw new NoSuchElementException();
                }
            } while (this.currentKey == null);
            return this.lastReturned;
        }

        public void remove() {
            if (this.lastReturned != null) {
                ConcurrentHashMap.this.remove(this.currentKey);
                this.lastReturned = null;
                return;
            }
            throw new IllegalStateException();
        }
    }

    final class KeyIterator extends ConcurrentHashMap<K, V>.HashIterator implements ReusableIterator<K>, Enumeration<K> {
        KeyIterator() {
            super();
        }

        public K next() {
            return super.nextEntry().key();
        }

        public K nextElement() {
            return super.nextEntry().key();
        }
    }

    final class ValueIterator extends ConcurrentHashMap<K, V>.HashIterator implements ReusableIterator<V>, Enumeration<V> {
        ValueIterator() {
            super();
        }

        public V next() {
            return super.nextEntry().value();
        }

        public V nextElement() {
            return super.nextEntry().value();
        }
    }

    static class SimpleEntry<K, V> implements Map.Entry<K, V> {
        private final K key;
        private V value;

        public SimpleEntry(K key2, V value2) {
            this.key = key2;
            this.value = value2;
        }

        public SimpleEntry(Map.Entry<? extends K, ? extends V> entry) {
            this.key = entry.getKey();
            this.value = entry.getValue();
        }

        public K getKey() {
            return this.key;
        }

        public V getValue() {
            return this.value;
        }

        public V setValue(V value2) {
            V oldValue = this.value;
            this.value = value2;
            return oldValue;
        }

        public boolean equals(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry e = (Map.Entry) o;
            if (!m162eq(this.key, e.getKey()) || !m162eq(this.value, e.getValue())) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            K k = this.key;
            int i = 0;
            int hashCode = k == null ? 0 : k.hashCode();
            V v = this.value;
            if (v != null) {
                i = v.hashCode();
            }
            return hashCode ^ i;
        }

        public String toString() {
            return this.key + "=" + this.value;
        }

        /* renamed from: eq */
        private static boolean m162eq(Object o1, Object o2) {
            if (o1 == null) {
                return o2 == null;
            }
            return o1.equals(o2);
        }
    }

    final class WriteThroughEntry extends SimpleEntry<K, V> {
        WriteThroughEntry(K k, V v) {
            super(k, v);
        }

        public V setValue(V value) {
            if (value != null) {
                V v = super.setValue(value);
                ConcurrentHashMap.this.put(getKey(), value);
                return v;
            }
            throw null;
        }
    }

    final class EntryIterator extends ConcurrentHashMap<K, V>.HashIterator implements ReusableIterator<Map.Entry<K, V>> {
        EntryIterator() {
            super();
        }

        public Map.Entry<K, V> next() {
            HashEntry<K, V> e = super.nextEntry();
            return new WriteThroughEntry(e.key(), e.value());
        }
    }

    final class KeySet extends AbstractSet<K> {
        KeySet() {
        }

        public Iterator<K> iterator() {
            return new KeyIterator();
        }

        public int size() {
            return ConcurrentHashMap.this.size();
        }

        public boolean isEmpty() {
            return ConcurrentHashMap.this.isEmpty();
        }

        public boolean contains(Object o) {
            return ConcurrentHashMap.this.containsKey(o);
        }

        public boolean remove(Object o) {
            return ConcurrentHashMap.this.remove(o) != null;
        }

        public void clear() {
            ConcurrentHashMap.this.clear();
        }
    }

    final class Values extends AbstractCollection<V> {
        Values() {
        }

        public Iterator<V> iterator() {
            return new ValueIterator();
        }

        public int size() {
            return ConcurrentHashMap.this.size();
        }

        public boolean isEmpty() {
            return ConcurrentHashMap.this.isEmpty();
        }

        public boolean contains(Object o) {
            return ConcurrentHashMap.this.containsValue(o);
        }

        public void clear() {
            ConcurrentHashMap.this.clear();
        }
    }

    final class EntrySet extends AbstractSet<Map.Entry<K, V>> {
        EntrySet() {
        }

        public Iterator<Map.Entry<K, V>> iterator() {
            return new EntryIterator();
        }

        public boolean contains(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry<?, ?> e = (Map.Entry) o;
            V v = ConcurrentHashMap.this.get(e.getKey());
            if (v == null || !v.equals(e.getValue())) {
                return false;
            }
            return true;
        }

        public boolean remove(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry<?, ?> e = (Map.Entry) o;
            return ConcurrentHashMap.this.remove(e.getKey(), e.getValue());
        }

        public int size() {
            return ConcurrentHashMap.this.size();
        }

        public boolean isEmpty() {
            return ConcurrentHashMap.this.isEmpty();
        }

        public void clear() {
            ConcurrentHashMap.this.clear();
        }
    }
}
