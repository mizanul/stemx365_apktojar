package org.apache.commons.pool.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimerTask;
import java.util.TreeMap;
import org.apache.commons.pool.BaseKeyedObjectPool;
import org.apache.commons.pool.KeyedObjectPool;
import org.apache.commons.pool.KeyedPoolableObjectFactory;
import org.apache.p010ws.commons.util.Base64;
import org.xbill.DNS.TTL;

public class GenericKeyedObjectPool<K, V> extends BaseKeyedObjectPool<K, V> implements KeyedObjectPool<K, V> {
    public static final boolean DEFAULT_LIFO = true;
    public static final int DEFAULT_MAX_ACTIVE = 8;
    public static final int DEFAULT_MAX_IDLE = 8;
    public static final int DEFAULT_MAX_TOTAL = -1;
    public static final long DEFAULT_MAX_WAIT = -1;
    public static final long DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS = 1800000;
    public static final int DEFAULT_MIN_IDLE = 0;
    public static final int DEFAULT_NUM_TESTS_PER_EVICTION_RUN = 3;
    public static final boolean DEFAULT_TEST_ON_BORROW = false;
    public static final boolean DEFAULT_TEST_ON_RETURN = false;
    public static final boolean DEFAULT_TEST_WHILE_IDLE = false;
    public static final long DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS = -1;
    public static final byte DEFAULT_WHEN_EXHAUSTED_ACTION = 1;
    public static final byte WHEN_EXHAUSTED_BLOCK = 1;
    public static final byte WHEN_EXHAUSTED_FAIL = 0;
    public static final byte WHEN_EXHAUSTED_GROW = 2;
    private LinkedList<GenericKeyedObjectPool<K, V>.Latch<K, V>> _allocationQueue;
    private CursorableLinkedList<ObjectTimestampPair<V>>.Cursor _evictionCursor;
    private CursorableLinkedList<K>.Cursor _evictionKeyCursor;
    private GenericKeyedObjectPool<K, V>.Evictor _evictor;
    private KeyedPoolableObjectFactory<K, V> _factory;
    private boolean _lifo;
    private int _maxActive;
    private int _maxIdle;
    private int _maxTotal;
    private long _maxWait;
    private long _minEvictableIdleTimeMillis;
    private volatile int _minIdle;
    private int _numTestsPerEvictionRun;
    private CursorableLinkedList<K> _poolList;
    private Map<K, GenericKeyedObjectPool<K, V>.ObjectQueue> _poolMap;
    private volatile boolean _testOnBorrow;
    private volatile boolean _testOnReturn;
    private boolean _testWhileIdle;
    private long _timeBetweenEvictionRunsMillis;
    private int _totalActive;
    private int _totalIdle;
    private int _totalInternalProcessing;
    private byte _whenExhaustedAction;

    public static class Config {
        public boolean lifo = true;
        public int maxActive = 8;
        public int maxIdle = 8;
        public int maxTotal = -1;
        public long maxWait = -1;
        public long minEvictableIdleTimeMillis = 1800000;
        public int minIdle = 0;
        public int numTestsPerEvictionRun = 3;
        public boolean testOnBorrow = false;
        public boolean testOnReturn = false;
        public boolean testWhileIdle = false;
        public long timeBetweenEvictionRunsMillis = -1;
        public byte whenExhaustedAction = 1;
    }

    static /* synthetic */ int access$1408(GenericKeyedObjectPool x0) {
        int i = x0._totalActive;
        x0._totalActive = i + 1;
        return i;
    }

    static /* synthetic */ int access$1410(GenericKeyedObjectPool x0) {
        int i = x0._totalActive;
        x0._totalActive = i - 1;
        return i;
    }

    static /* synthetic */ int access$1508(GenericKeyedObjectPool x0) {
        int i = x0._totalInternalProcessing;
        x0._totalInternalProcessing = i + 1;
        return i;
    }

    static /* synthetic */ int access$1510(GenericKeyedObjectPool x0) {
        int i = x0._totalInternalProcessing;
        x0._totalInternalProcessing = i - 1;
        return i;
    }

    public GenericKeyedObjectPool() {
        this((KeyedPoolableObjectFactory) null, 8, (byte) 1, -1, 8, false, false, -1, 3, 1800000, false);
    }

    public GenericKeyedObjectPool(KeyedPoolableObjectFactory<K, V> factory) {
        this(factory, 8, (byte) 1, -1, 8, false, false, -1, 3, 1800000, false);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public GenericKeyedObjectPool(org.apache.commons.pool.KeyedPoolableObjectFactory<K, V> r22, org.apache.commons.pool.impl.GenericKeyedObjectPool.Config r23) {
        /*
            r21 = this;
            r0 = r23
            r1 = r21
            r2 = r22
            int r3 = r0.maxActive
            byte r4 = r0.whenExhaustedAction
            long r5 = r0.maxWait
            int r7 = r0.maxIdle
            int r8 = r0.maxTotal
            int r9 = r0.minIdle
            boolean r10 = r0.testOnBorrow
            boolean r11 = r0.testOnReturn
            long r12 = r0.timeBetweenEvictionRunsMillis
            int r14 = r0.numTestsPerEvictionRun
            r19 = r1
            r20 = r2
            long r1 = r0.minEvictableIdleTimeMillis
            r15 = r1
            boolean r1 = r0.testWhileIdle
            r17 = r1
            boolean r1 = r0.lifo
            r18 = r1
            r1 = r19
            r2 = r20
            r1.<init>(r2, r3, r4, r5, r7, r8, r9, r10, r11, r12, r14, r15, r17, r18)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.pool.impl.GenericKeyedObjectPool.<init>(org.apache.commons.pool.KeyedPoolableObjectFactory, org.apache.commons.pool.impl.GenericKeyedObjectPool$Config):void");
    }

    public GenericKeyedObjectPool(KeyedPoolableObjectFactory<K, V> factory, int maxActive) {
        this(factory, maxActive, (byte) 1, -1, 8, false, false, -1, 3, 1800000, false);
    }

    public GenericKeyedObjectPool(KeyedPoolableObjectFactory<K, V> factory, int maxActive, byte whenExhaustedAction, long maxWait) {
        this(factory, maxActive, whenExhaustedAction, maxWait, 8, false, false, -1, 3, 1800000, false);
    }

    public GenericKeyedObjectPool(KeyedPoolableObjectFactory<K, V> factory, int maxActive, byte whenExhaustedAction, long maxWait, boolean testOnBorrow, boolean testOnReturn) {
        this(factory, maxActive, whenExhaustedAction, maxWait, 8, testOnBorrow, testOnReturn, -1, 3, 1800000, false);
    }

    public GenericKeyedObjectPool(KeyedPoolableObjectFactory<K, V> factory, int maxActive, byte whenExhaustedAction, long maxWait, int maxIdle) {
        this(factory, maxActive, whenExhaustedAction, maxWait, maxIdle, false, false, -1, 3, 1800000, false);
    }

    public GenericKeyedObjectPool(KeyedPoolableObjectFactory<K, V> factory, int maxActive, byte whenExhaustedAction, long maxWait, int maxIdle, boolean testOnBorrow, boolean testOnReturn) {
        this(factory, maxActive, whenExhaustedAction, maxWait, maxIdle, testOnBorrow, testOnReturn, -1, 3, 1800000, false);
    }

    public GenericKeyedObjectPool(KeyedPoolableObjectFactory<K, V> factory, int maxActive, byte whenExhaustedAction, long maxWait, int maxIdle, boolean testOnBorrow, boolean testOnReturn, long timeBetweenEvictionRunsMillis, int numTestsPerEvictionRun, long minEvictableIdleTimeMillis, boolean testWhileIdle) {
        this(factory, maxActive, whenExhaustedAction, maxWait, maxIdle, -1, testOnBorrow, testOnReturn, timeBetweenEvictionRunsMillis, numTestsPerEvictionRun, minEvictableIdleTimeMillis, testWhileIdle);
    }

    public GenericKeyedObjectPool(KeyedPoolableObjectFactory<K, V> factory, int maxActive, byte whenExhaustedAction, long maxWait, int maxIdle, int maxTotal, boolean testOnBorrow, boolean testOnReturn, long timeBetweenEvictionRunsMillis, int numTestsPerEvictionRun, long minEvictableIdleTimeMillis, boolean testWhileIdle) {
        this(factory, maxActive, whenExhaustedAction, maxWait, maxIdle, maxTotal, 0, testOnBorrow, testOnReturn, timeBetweenEvictionRunsMillis, numTestsPerEvictionRun, minEvictableIdleTimeMillis, testWhileIdle);
    }

    public GenericKeyedObjectPool(KeyedPoolableObjectFactory<K, V> factory, int maxActive, byte whenExhaustedAction, long maxWait, int maxIdle, int maxTotal, int minIdle, boolean testOnBorrow, boolean testOnReturn, long timeBetweenEvictionRunsMillis, int numTestsPerEvictionRun, long minEvictableIdleTimeMillis, boolean testWhileIdle) {
        this(factory, maxActive, whenExhaustedAction, maxWait, maxIdle, maxTotal, minIdle, testOnBorrow, testOnReturn, timeBetweenEvictionRunsMillis, numTestsPerEvictionRun, minEvictableIdleTimeMillis, testWhileIdle, true);
    }

    public GenericKeyedObjectPool(KeyedPoolableObjectFactory<K, V> factory, int maxActive, byte whenExhaustedAction, long maxWait, int maxIdle, int maxTotal, int minIdle, boolean testOnBorrow, boolean testOnReturn, long timeBetweenEvictionRunsMillis, int numTestsPerEvictionRun, long minEvictableIdleTimeMillis, boolean testWhileIdle, boolean lifo) {
        byte b = whenExhaustedAction;
        this._maxIdle = 8;
        this._minIdle = 0;
        this._maxActive = 8;
        this._maxTotal = -1;
        this._maxWait = -1;
        this._whenExhaustedAction = 1;
        this._testOnBorrow = false;
        this._testOnReturn = false;
        this._testWhileIdle = false;
        this._timeBetweenEvictionRunsMillis = -1;
        this._numTestsPerEvictionRun = 3;
        this._minEvictableIdleTimeMillis = 1800000;
        this._poolMap = null;
        this._totalActive = 0;
        this._totalIdle = 0;
        this._totalInternalProcessing = 0;
        this._factory = null;
        this._evictor = null;
        this._poolList = null;
        this._evictionCursor = null;
        this._evictionKeyCursor = null;
        this._lifo = true;
        this._allocationQueue = new LinkedList<>();
        this._factory = factory;
        this._maxActive = maxActive;
        this._lifo = lifo;
        if (b == 0 || b == 1 || b == 2) {
            this._whenExhaustedAction = b;
            this._maxWait = maxWait;
            this._maxIdle = maxIdle;
            this._maxTotal = maxTotal;
            this._minIdle = minIdle;
            this._testOnBorrow = testOnBorrow;
            this._testOnReturn = testOnReturn;
            this._timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
            this._numTestsPerEvictionRun = numTestsPerEvictionRun;
            this._minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
            this._testWhileIdle = testWhileIdle;
            this._poolMap = new HashMap();
            this._poolList = new CursorableLinkedList<>();
            startEvictor(this._timeBetweenEvictionRunsMillis);
            return;
        }
        throw new IllegalArgumentException("whenExhaustedAction " + b + " not recognized.");
    }

    public synchronized int getMaxActive() {
        return this._maxActive;
    }

    public void setMaxActive(int maxActive) {
        synchronized (this) {
            this._maxActive = maxActive;
        }
        allocate();
    }

    public synchronized int getMaxTotal() {
        return this._maxTotal;
    }

    public void setMaxTotal(int maxTotal) {
        synchronized (this) {
            this._maxTotal = maxTotal;
        }
        allocate();
    }

    public synchronized byte getWhenExhaustedAction() {
        return this._whenExhaustedAction;
    }

    public void setWhenExhaustedAction(byte whenExhaustedAction) {
        synchronized (this) {
            if (whenExhaustedAction == 0 || whenExhaustedAction == 1 || whenExhaustedAction == 2) {
                this._whenExhaustedAction = whenExhaustedAction;
            } else {
                throw new IllegalArgumentException("whenExhaustedAction " + whenExhaustedAction + " not recognized.");
            }
        }
        allocate();
    }

    public synchronized long getMaxWait() {
        return this._maxWait;
    }

    public void setMaxWait(long maxWait) {
        synchronized (this) {
            this._maxWait = maxWait;
        }
        allocate();
    }

    public synchronized int getMaxIdle() {
        return this._maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        synchronized (this) {
            this._maxIdle = maxIdle;
        }
        allocate();
    }

    public void setMinIdle(int poolSize) {
        this._minIdle = poolSize;
    }

    public int getMinIdle() {
        return this._minIdle;
    }

    public boolean getTestOnBorrow() {
        return this._testOnBorrow;
    }

    public void setTestOnBorrow(boolean testOnBorrow) {
        this._testOnBorrow = testOnBorrow;
    }

    public boolean getTestOnReturn() {
        return this._testOnReturn;
    }

    public void setTestOnReturn(boolean testOnReturn) {
        this._testOnReturn = testOnReturn;
    }

    public synchronized long getTimeBetweenEvictionRunsMillis() {
        return this._timeBetweenEvictionRunsMillis;
    }

    public synchronized void setTimeBetweenEvictionRunsMillis(long timeBetweenEvictionRunsMillis) {
        this._timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
        startEvictor(timeBetweenEvictionRunsMillis);
    }

    public synchronized int getNumTestsPerEvictionRun() {
        return this._numTestsPerEvictionRun;
    }

    public synchronized void setNumTestsPerEvictionRun(int numTestsPerEvictionRun) {
        this._numTestsPerEvictionRun = numTestsPerEvictionRun;
    }

    public synchronized long getMinEvictableIdleTimeMillis() {
        return this._minEvictableIdleTimeMillis;
    }

    public synchronized void setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis) {
        this._minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
    }

    public synchronized boolean getTestWhileIdle() {
        return this._testWhileIdle;
    }

    public synchronized void setTestWhileIdle(boolean testWhileIdle) {
        this._testWhileIdle = testWhileIdle;
    }

    public synchronized void setConfig(Config conf) {
        setMaxIdle(conf.maxIdle);
        setMaxActive(conf.maxActive);
        setMaxTotal(conf.maxTotal);
        setMinIdle(conf.minIdle);
        setMaxWait(conf.maxWait);
        setWhenExhaustedAction(conf.whenExhaustedAction);
        setTestOnBorrow(conf.testOnBorrow);
        setTestOnReturn(conf.testOnReturn);
        setTestWhileIdle(conf.testWhileIdle);
        setNumTestsPerEvictionRun(conf.numTestsPerEvictionRun);
        setMinEvictableIdleTimeMillis(conf.minEvictableIdleTimeMillis);
        setTimeBetweenEvictionRunsMillis(conf.timeBetweenEvictionRunsMillis);
    }

    public synchronized boolean getLifo() {
        return this._lifo;
    }

    public synchronized void setLifo(boolean lifo) {
        this._lifo = lifo;
    }

    /*  JADX ERROR: IndexOutOfBoundsException in pass: RegionMakerVisitor
        java.lang.IndexOutOfBoundsException: Index: 0, Size: 0
        	at java.util.ArrayList.rangeCheck(ArrayList.java:657)
        	at java.util.ArrayList.get(ArrayList.java:433)
        	at jadx.core.dex.nodes.InsnNode.getArg(InsnNode.java:101)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:611)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:561)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processExcHandler(RegionMaker.java:1043)
        	at jadx.core.dex.visitors.regions.RegionMaker.processTryCatchBlocks(RegionMaker.java:975)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:52)
        */
    public V borrowObject(K r18) throws java.lang.Exception {
        /*
            r17 = this;
            r1 = r17
            r2 = r18
            long r3 = java.lang.System.currentTimeMillis()
            org.apache.commons.pool.impl.GenericKeyedObjectPool$Latch r0 = new org.apache.commons.pool.impl.GenericKeyedObjectPool$Latch
            r5 = 0
            r0.<init>(r2)
            r5 = r0
            monitor-enter(r17)
            r6 = 0
            r7 = 0
            byte r0 = r1._whenExhaustedAction     // Catch:{ all -> 0x022b }
            r9 = r0
            long r10 = r1._maxWait     // Catch:{ all -> 0x0228 }
            java.util.LinkedList<org.apache.commons.pool.impl.GenericKeyedObjectPool<K, V>$Latch<K, V>> r0 = r1._allocationQueue     // Catch:{ all -> 0x0230 }
            r0.add(r5)     // Catch:{ all -> 0x0230 }
            monitor-exit(r17)     // Catch:{ all -> 0x0230 }
            r17.allocate()
        L_0x0021:
            monitor-enter(r17)
            r17.assertOpen()     // Catch:{ all -> 0x0225 }
            monitor-exit(r17)     // Catch:{ all -> 0x0225 }
            org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectTimestampPair r0 = r5.getPair()
            if (r0 != 0) goto L_0x015c
            boolean r0 = r5.mayCreate()
            if (r0 == 0) goto L_0x0034
            goto L_0x015c
        L_0x0034:
            if (r9 == 0) goto L_0x013b
            r0 = 1
            if (r9 == r0) goto L_0x0078
            r0 = 2
            if (r9 != r0) goto L_0x005b
            monitor-enter(r17)
            org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectTimestampPair r0 = r5.getPair()     // Catch:{ all -> 0x0058 }
            if (r0 != 0) goto L_0x0055
            boolean r0 = r5.mayCreate()     // Catch:{ all -> 0x0058 }
            if (r0 != 0) goto L_0x0055
            java.util.LinkedList<org.apache.commons.pool.impl.GenericKeyedObjectPool<K, V>$Latch<K, V>> r0 = r1._allocationQueue     // Catch:{ all -> 0x0058 }
            r0.remove(r5)     // Catch:{ all -> 0x0058 }
            org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectQueue r0 = r5.getPool()     // Catch:{ all -> 0x0058 }
            r0.incrementInternalProcessingCount()     // Catch:{ all -> 0x0058 }
        L_0x0055:
            monitor-exit(r17)     // Catch:{ all -> 0x0058 }
            goto L_0x015c
        L_0x0058:
            r0 = move-exception
            monitor-exit(r17)     // Catch:{ all -> 0x0058 }
            throw r0
        L_0x005b:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "whenExhaustedAction "
            r6.append(r7)
            r6.append(r9)
            java.lang.String r7 = " not recognized."
            r6.append(r7)
            java.lang.String r6 = r6.toString()
            r0.<init>(r6)
            throw r0
        L_0x0078:
            monitor-enter(r5)     // Catch:{ InterruptedException -> 0x00e1 }
            org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectTimestampPair r12 = r5.getPair()     // Catch:{ all -> 0x00de }
            if (r12 != 0) goto L_0x00db
            boolean r12 = r5.mayCreate()     // Catch:{ all -> 0x00de }
            if (r12 != 0) goto L_0x00db
            int r12 = (r10 > r7 ? 1 : (r10 == r7 ? 0 : -1))
            if (r12 > 0) goto L_0x008d
            r5.wait()     // Catch:{ all -> 0x00de }
            goto L_0x009c
        L_0x008d:
            long r12 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x00de }
            long r12 = r12 - r3
            long r14 = r10 - r12
            int r16 = (r14 > r7 ? 1 : (r14 == r7 ? 0 : -1))
            if (r16 <= 0) goto L_0x009b
            r5.wait(r14)     // Catch:{ all -> 0x00de }
        L_0x009b:
        L_0x009c:
            monitor-exit(r5)     // Catch:{ all -> 0x00de }
            boolean r12 = r17.isClosed()     // Catch:{ InterruptedException -> 0x00e1 }
            if (r12 == r0) goto L_0x00d3
            int r0 = (r10 > r7 ? 1 : (r10 == r7 ? 0 : -1))
            if (r0 <= 0) goto L_0x0021
            long r12 = java.lang.System.currentTimeMillis()
            long r12 = r12 - r3
            int r0 = (r12 > r10 ? 1 : (r12 == r10 ? 0 : -1))
            if (r0 < 0) goto L_0x0021
            monitor-enter(r17)
            org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectTimestampPair r0 = r5.getPair()     // Catch:{ all -> 0x00d0 }
            if (r0 != 0) goto L_0x00cd
            boolean r0 = r5.mayCreate()     // Catch:{ all -> 0x00d0 }
            if (r0 == 0) goto L_0x00bf
            goto L_0x00cd
        L_0x00bf:
            java.util.LinkedList<org.apache.commons.pool.impl.GenericKeyedObjectPool<K, V>$Latch<K, V>> r0 = r1._allocationQueue     // Catch:{ all -> 0x00d0 }
            r0.remove(r5)     // Catch:{ all -> 0x00d0 }
            monitor-exit(r17)     // Catch:{ all -> 0x00d0 }
            java.util.NoSuchElementException r0 = new java.util.NoSuchElementException
            java.lang.String r6 = "Timeout waiting for idle object"
            r0.<init>(r6)
            throw r0
        L_0x00cd:
            monitor-exit(r17)     // Catch:{ all -> 0x00d0 }
            goto L_0x015c
        L_0x00d0:
            r0 = move-exception
            monitor-exit(r17)     // Catch:{ all -> 0x00d0 }
            throw r0
        L_0x00d3:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException     // Catch:{ InterruptedException -> 0x00e1 }
            java.lang.String r6 = "Pool closed"
            r0.<init>(r6)     // Catch:{ InterruptedException -> 0x00e1 }
            throw r0     // Catch:{ InterruptedException -> 0x00e1 }
        L_0x00db:
            monitor-exit(r5)     // Catch:{ all -> 0x00de }
            goto L_0x015c
        L_0x00de:
            r0 = move-exception
            monitor-exit(r5)     // Catch:{ all -> 0x00de }
            throw r0     // Catch:{ InterruptedException -> 0x00e1 }
        L_0x00e1:
            r0 = move-exception
            r12 = r0
            r6 = 0
            monitor-enter(r17)
            org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectTimestampPair r0 = r5.getPair()     // Catch:{ all -> 0x0138 }
            if (r0 != 0) goto L_0x00f7
            boolean r0 = r5.mayCreate()     // Catch:{ all -> 0x0138 }
            if (r0 != 0) goto L_0x00f7
            java.util.LinkedList<org.apache.commons.pool.impl.GenericKeyedObjectPool<K, V>$Latch<K, V>> r0 = r1._allocationQueue     // Catch:{ all -> 0x0138 }
            r0.remove(r5)     // Catch:{ all -> 0x0138 }
            goto L_0x012a
        L_0x00f7:
            org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectTimestampPair r0 = r5.getPair()     // Catch:{ all -> 0x0138 }
            if (r0 != 0) goto L_0x010d
            boolean r0 = r5.mayCreate()     // Catch:{ all -> 0x0138 }
            if (r0 == 0) goto L_0x010d
            org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectQueue r0 = r5.getPool()     // Catch:{ all -> 0x0138 }
            r0.decrementInternalProcessingCount()     // Catch:{ all -> 0x0138 }
            r0 = 1
            r6 = r0
            goto L_0x012a
        L_0x010d:
            org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectQueue r0 = r5.getPool()     // Catch:{ all -> 0x0138 }
            r0.decrementInternalProcessingCount()     // Catch:{ all -> 0x0138 }
            org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectQueue r0 = r5.getPool()     // Catch:{ all -> 0x0138 }
            r0.incrementActiveCount()     // Catch:{ all -> 0x0138 }
            java.lang.Object r0 = r5.getkey()     // Catch:{ all -> 0x0138 }
            org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectTimestampPair r7 = r5.getPair()     // Catch:{ all -> 0x0138 }
            java.lang.Object r7 = r7.getValue()     // Catch:{ all -> 0x0138 }
            r1.returnObject(r0, r7)     // Catch:{ all -> 0x0138 }
        L_0x012a:
            monitor-exit(r17)     // Catch:{ all -> 0x0138 }
            if (r6 == 0) goto L_0x0130
            r17.allocate()
        L_0x0130:
            java.lang.Thread r0 = java.lang.Thread.currentThread()
            r0.interrupt()
            throw r12
        L_0x0138:
            r0 = move-exception
            monitor-exit(r17)     // Catch:{ all -> 0x0138 }
            throw r0
        L_0x013b:
            monitor-enter(r17)
            org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectTimestampPair r0 = r5.getPair()     // Catch:{ all -> 0x0159 }
            if (r0 != 0) goto L_0x0157
            boolean r0 = r5.mayCreate()     // Catch:{ all -> 0x0159 }
            if (r0 == 0) goto L_0x0149
            goto L_0x0157
        L_0x0149:
            java.util.LinkedList<org.apache.commons.pool.impl.GenericKeyedObjectPool<K, V>$Latch<K, V>> r0 = r1._allocationQueue     // Catch:{ all -> 0x0159 }
            r0.remove(r5)     // Catch:{ all -> 0x0159 }
            monitor-exit(r17)     // Catch:{ all -> 0x0159 }
            java.util.NoSuchElementException r0 = new java.util.NoSuchElementException
            java.lang.String r6 = "Pool exhausted"
            r0.<init>(r6)
            throw r0
        L_0x0157:
            monitor-exit(r17)     // Catch:{ all -> 0x0159 }
            goto L_0x015c
        L_0x0159:
            r0 = move-exception
            monitor-exit(r17)     // Catch:{ all -> 0x0159 }
            throw r0
        L_0x015c:
            r12 = 0
            org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectTimestampPair r0 = r5.getPair()
            if (r0 != 0) goto L_0x0198
            org.apache.commons.pool.KeyedPoolableObjectFactory<K, V> r0 = r1._factory     // Catch:{ all -> 0x0184 }
            java.lang.Object r0 = r0.makeObject(r2)     // Catch:{ all -> 0x0184 }
            org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectTimestampPair r13 = new org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectTimestampPair     // Catch:{ all -> 0x0184 }
            r13.<init>(r0)     // Catch:{ all -> 0x0184 }
            r5.setPair(r13)     // Catch:{ all -> 0x0184 }
            r12 = 1
            if (r12 != 0) goto L_0x0198
            monitor-enter(r17)
            org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectQueue r0 = r5.getPool()     // Catch:{ all -> 0x0181 }
            r0.decrementInternalProcessingCount()     // Catch:{ all -> 0x0181 }
            monitor-exit(r17)     // Catch:{ all -> 0x0181 }
            r17.allocate()
            goto L_0x0198
        L_0x0181:
            r0 = move-exception
            monitor-exit(r17)     // Catch:{ all -> 0x0181 }
            throw r0
        L_0x0184:
            r0 = move-exception
            if (r12 != 0) goto L_0x0197
            monitor-enter(r17)
            org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectQueue r6 = r5.getPool()     // Catch:{ all -> 0x0194 }
            r6.decrementInternalProcessingCount()     // Catch:{ all -> 0x0194 }
            monitor-exit(r17)     // Catch:{ all -> 0x0194 }
            r17.allocate()
            goto L_0x0197
        L_0x0194:
            r0 = move-exception
            monitor-exit(r17)     // Catch:{ all -> 0x0194 }
            throw r0
        L_0x0197:
            throw r0
        L_0x0198:
            org.apache.commons.pool.KeyedPoolableObjectFactory<K, V> r0 = r1._factory     // Catch:{ all -> 0x01d8 }
            org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectTimestampPair r13 = r5.getPair()     // Catch:{ all -> 0x01d8 }
            T r13 = r13.value     // Catch:{ all -> 0x01d8 }
            r0.activateObject(r2, r13)     // Catch:{ all -> 0x01d8 }
            boolean r0 = r1._testOnBorrow     // Catch:{ all -> 0x01d8 }
            if (r0 == 0) goto L_0x01be
            org.apache.commons.pool.KeyedPoolableObjectFactory<K, V> r0 = r1._factory     // Catch:{ all -> 0x01d8 }
            org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectTimestampPair r13 = r5.getPair()     // Catch:{ all -> 0x01d8 }
            T r13 = r13.value     // Catch:{ all -> 0x01d8 }
            boolean r0 = r0.validateObject(r2, r13)     // Catch:{ all -> 0x01d8 }
            if (r0 == 0) goto L_0x01b6
            goto L_0x01be
        L_0x01b6:
            java.lang.Exception r0 = new java.lang.Exception     // Catch:{ all -> 0x01d8 }
            java.lang.String r13 = "ValidateObject failed"
            r0.<init>(r13)     // Catch:{ all -> 0x01d8 }
            throw r0     // Catch:{ all -> 0x01d8 }
        L_0x01be:
            monitor-enter(r17)     // Catch:{ all -> 0x01d8 }
            org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectQueue r0 = r5.getPool()     // Catch:{ all -> 0x01d5 }
            r0.decrementInternalProcessingCount()     // Catch:{ all -> 0x01d5 }
            org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectQueue r0 = r5.getPool()     // Catch:{ all -> 0x01d5 }
            r0.incrementActiveCount()     // Catch:{ all -> 0x01d5 }
            monitor-exit(r17)     // Catch:{ all -> 0x01d5 }
            org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectTimestampPair r0 = r5.getPair()     // Catch:{ all -> 0x01d8 }
            T r0 = r0.value     // Catch:{ all -> 0x01d8 }
            return r0
        L_0x01d5:
            r0 = move-exception
            monitor-exit(r17)     // Catch:{ all -> 0x01d5 }
            throw r0     // Catch:{ all -> 0x01d8 }
        L_0x01d8:
            r0 = move-exception
            r13 = r0
            org.apache.commons.pool.PoolUtils.checkRethrow(r13)
            org.apache.commons.pool.KeyedPoolableObjectFactory<K, V> r0 = r1._factory     // Catch:{ all -> 0x01e9 }
            org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectTimestampPair r14 = r5.getPair()     // Catch:{ all -> 0x01e9 }
            T r14 = r14.value     // Catch:{ all -> 0x01e9 }
            r0.destroyObject(r2, r14)     // Catch:{ all -> 0x01e9 }
            goto L_0x01ed
        L_0x01e9:
            r0 = move-exception
            org.apache.commons.pool.PoolUtils.checkRethrow(r0)
        L_0x01ed:
            monitor-enter(r17)
            org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectQueue r0 = r5.getPool()     // Catch:{ all -> 0x0222 }
            r0.decrementInternalProcessingCount()     // Catch:{ all -> 0x0222 }
            if (r12 != 0) goto L_0x01ff
            r5.reset()     // Catch:{ all -> 0x0222 }
            java.util.LinkedList<org.apache.commons.pool.impl.GenericKeyedObjectPool<K, V>$Latch<K, V>> r0 = r1._allocationQueue     // Catch:{ all -> 0x0222 }
            r0.add(r6, r5)     // Catch:{ all -> 0x0222 }
        L_0x01ff:
            monitor-exit(r17)     // Catch:{ all -> 0x0222 }
            r17.allocate()
            if (r12 != 0) goto L_0x0207
            goto L_0x0021
        L_0x0207:
            java.util.NoSuchElementException r0 = new java.util.NoSuchElementException
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "Could not create a validated object, cause: "
            r6.append(r7)
            java.lang.String r7 = r13.getMessage()
            r6.append(r7)
            java.lang.String r6 = r6.toString()
            r0.<init>(r6)
            throw r0
        L_0x0222:
            r0 = move-exception
            monitor-exit(r17)     // Catch:{ all -> 0x0222 }
            throw r0
        L_0x0225:
            r0 = move-exception
            monitor-exit(r17)     // Catch:{ all -> 0x0225 }
            throw r0
        L_0x0228:
            r0 = move-exception
            r10 = r7
            goto L_0x022e
        L_0x022b:
            r0 = move-exception
            r10 = r7
            r9 = r6
        L_0x022e:
            monitor-exit(r17)     // Catch:{ all -> 0x0230 }
            throw r0
        L_0x0230:
            r0 = move-exception
            goto L_0x022e
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.pool.impl.GenericKeyedObjectPool.borrowObject(java.lang.Object):java.lang.Object");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:54:0x00c1, code lost:
        if (r0 == false) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x00c3, code lost:
        clearOldest();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void allocate() {
        /*
            r7 = this;
            r0 = 0
            monitor-enter(r7)
            boolean r1 = r7.isClosed()     // Catch:{ all -> 0x00c7 }
            if (r1 == 0) goto L_0x000a
            monitor-exit(r7)     // Catch:{ all -> 0x00c7 }
            return
        L_0x000a:
            java.util.LinkedList<org.apache.commons.pool.impl.GenericKeyedObjectPool<K, V>$Latch<K, V>> r1 = r7._allocationQueue     // Catch:{ all -> 0x00c7 }
            java.util.Iterator r1 = r1.iterator()     // Catch:{ all -> 0x00c7 }
        L_0x0010:
            boolean r2 = r1.hasNext()     // Catch:{ all -> 0x00c7 }
            if (r2 == 0) goto L_0x00c0
            java.lang.Object r2 = r1.next()     // Catch:{ all -> 0x00c7 }
            org.apache.commons.pool.impl.GenericKeyedObjectPool$Latch r2 = (org.apache.commons.pool.impl.GenericKeyedObjectPool.Latch) r2     // Catch:{ all -> 0x00c7 }
            java.util.Map<K, org.apache.commons.pool.impl.GenericKeyedObjectPool<K, V>$ObjectQueue> r3 = r7._poolMap     // Catch:{ all -> 0x00c7 }
            java.lang.Object r4 = r2.getkey()     // Catch:{ all -> 0x00c7 }
            java.lang.Object r3 = r3.get(r4)     // Catch:{ all -> 0x00c7 }
            org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectQueue r3 = (org.apache.commons.pool.impl.GenericKeyedObjectPool.ObjectQueue) r3     // Catch:{ all -> 0x00c7 }
            if (r3 != 0) goto L_0x0043
            org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectQueue r4 = new org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectQueue     // Catch:{ all -> 0x00c7 }
            r5 = 0
            r4.<init>()     // Catch:{ all -> 0x00c7 }
            r3 = r4
            java.util.Map<K, org.apache.commons.pool.impl.GenericKeyedObjectPool<K, V>$ObjectQueue> r4 = r7._poolMap     // Catch:{ all -> 0x00c7 }
            java.lang.Object r5 = r2.getkey()     // Catch:{ all -> 0x00c7 }
            r4.put(r5, r3)     // Catch:{ all -> 0x00c7 }
            org.apache.commons.pool.impl.CursorableLinkedList<K> r4 = r7._poolList     // Catch:{ all -> 0x00c7 }
            java.lang.Object r5 = r2.getkey()     // Catch:{ all -> 0x00c7 }
            r4.add(r5)     // Catch:{ all -> 0x00c7 }
        L_0x0043:
            r2.setPool(r3)     // Catch:{ all -> 0x00c7 }
            org.apache.commons.pool.impl.CursorableLinkedList r4 = r3.queue     // Catch:{ all -> 0x00c7 }
            boolean r4 = r4.isEmpty()     // Catch:{ all -> 0x00c7 }
            r5 = 1
            if (r4 != 0) goto L_0x0072
            r1.remove()     // Catch:{ all -> 0x00c7 }
            org.apache.commons.pool.impl.CursorableLinkedList r4 = r3.queue     // Catch:{ all -> 0x00c7 }
            java.lang.Object r4 = r4.removeFirst()     // Catch:{ all -> 0x00c7 }
            org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectTimestampPair r4 = (org.apache.commons.pool.impl.GenericKeyedObjectPool.ObjectTimestampPair) r4     // Catch:{ all -> 0x00c7 }
            r2.setPair(r4)     // Catch:{ all -> 0x00c7 }
            r3.incrementInternalProcessingCount()     // Catch:{ all -> 0x00c7 }
            int r4 = r7._totalIdle     // Catch:{ all -> 0x00c7 }
            int r4 = r4 - r5
            r7._totalIdle = r4     // Catch:{ all -> 0x00c7 }
            monitor-enter(r2)     // Catch:{ all -> 0x00c7 }
            r2.notify()     // Catch:{ all -> 0x006f }
            monitor-exit(r2)     // Catch:{ all -> 0x006f }
            goto L_0x0010
        L_0x006f:
            r4 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x006f }
            throw r4     // Catch:{ all -> 0x00c7 }
        L_0x0072:
            int r4 = r7._maxTotal     // Catch:{ all -> 0x00c7 }
            if (r4 <= 0) goto L_0x0084
            int r4 = r7._totalActive     // Catch:{ all -> 0x00c7 }
            int r6 = r7._totalIdle     // Catch:{ all -> 0x00c7 }
            int r4 = r4 + r6
            int r6 = r7._totalInternalProcessing     // Catch:{ all -> 0x00c7 }
            int r4 = r4 + r6
            int r6 = r7._maxTotal     // Catch:{ all -> 0x00c7 }
            if (r4 < r6) goto L_0x0084
            r0 = 1
            goto L_0x00c0
        L_0x0084:
            int r4 = r7._maxActive     // Catch:{ all -> 0x00c7 }
            if (r4 < 0) goto L_0x0095
            int r4 = r3.activeCount     // Catch:{ all -> 0x00c7 }
            int r6 = r3.internalProcessingCount     // Catch:{ all -> 0x00c7 }
            int r4 = r4 + r6
            int r6 = r7._maxActive     // Catch:{ all -> 0x00c7 }
            if (r4 >= r6) goto L_0x00a6
        L_0x0095:
            int r4 = r7._maxTotal     // Catch:{ all -> 0x00c7 }
            if (r4 < 0) goto L_0x00ad
            int r4 = r7._totalActive     // Catch:{ all -> 0x00c7 }
            int r6 = r7._totalIdle     // Catch:{ all -> 0x00c7 }
            int r4 = r4 + r6
            int r6 = r7._totalInternalProcessing     // Catch:{ all -> 0x00c7 }
            int r4 = r4 + r6
            int r6 = r7._maxTotal     // Catch:{ all -> 0x00c7 }
            if (r4 >= r6) goto L_0x00a6
            goto L_0x00ad
        L_0x00a6:
            int r4 = r7._maxActive     // Catch:{ all -> 0x00c7 }
            if (r4 >= 0) goto L_0x00ab
            goto L_0x00c0
        L_0x00ab:
            goto L_0x0010
        L_0x00ad:
            r1.remove()     // Catch:{ all -> 0x00c7 }
            r2.setMayCreate(r5)     // Catch:{ all -> 0x00c7 }
            r3.incrementInternalProcessingCount()     // Catch:{ all -> 0x00c7 }
            monitor-enter(r2)     // Catch:{ all -> 0x00c7 }
            r2.notify()     // Catch:{ all -> 0x00bd }
            monitor-exit(r2)     // Catch:{ all -> 0x00bd }
            goto L_0x0010
        L_0x00bd:
            r4 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x00bd }
            throw r4     // Catch:{ all -> 0x00c7 }
        L_0x00c0:
            monitor-exit(r7)     // Catch:{ all -> 0x00c7 }
            if (r0 == 0) goto L_0x00c6
            r7.clearOldest()
        L_0x00c6:
            return
        L_0x00c7:
            r1 = move-exception
            monitor-exit(r7)     // Catch:{ all -> 0x00c7 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.pool.impl.GenericKeyedObjectPool.allocate():void");
    }

    public void clear() {
        Map<K, List<ObjectTimestampPair<V>>> toDestroy = new HashMap<>();
        synchronized (this) {
            Iterator<K> it = this._poolMap.keySet().iterator();
            while (it.hasNext()) {
                K key = it.next();
                GenericKeyedObjectPool<K, V>.ObjectQueue pool = this._poolMap.get(key);
                List<ObjectTimestampPair<V>> objects = new ArrayList<>();
                objects.addAll(pool.queue);
                toDestroy.put(key, objects);
                it.remove();
                this._poolList.remove((Object) key);
                this._totalIdle -= pool.queue.size();
                this._totalInternalProcessing += pool.queue.size();
                pool.queue.clear();
            }
        }
        destroy(toDestroy, this._factory);
    }

    public void clearOldest() {
        Map<K, List<ObjectTimestampPair<V>>> toDestroy = new HashMap<>();
        Map<ObjectTimestampPair<V>, K> map = new TreeMap<>();
        synchronized (this) {
            for (K key : this._poolMap.keySet()) {
                for (ObjectTimestampPair<V> put : this._poolMap.get(key).queue) {
                    map.put(put, key);
                }
            }
            Set<Map.Entry<ObjectTimestampPair<V>, K>> setPairKeys = map.entrySet();
            int itemsToRemove = ((int) (((double) map.size()) * 0.15d)) + 1;
            Iterator<Map.Entry<ObjectTimestampPair<V>, K>> iter = setPairKeys.iterator();
            while (iter.hasNext() && itemsToRemove > 0) {
                Map.Entry<ObjectTimestampPair<V>, K> entry = iter.next();
                K key2 = entry.getValue();
                ObjectTimestampPair<V> pairTimeStamp = entry.getKey();
                GenericKeyedObjectPool<K, V>.ObjectQueue objectQueue = this._poolMap.get(key2);
                objectQueue.queue.remove(pairTimeStamp);
                if (toDestroy.containsKey(key2)) {
                    toDestroy.get(key2).add(pairTimeStamp);
                } else {
                    List<ObjectTimestampPair<V>> listForKey = new ArrayList<>();
                    listForKey.add(pairTimeStamp);
                    toDestroy.put(key2, listForKey);
                }
                objectQueue.incrementInternalProcessingCount();
                this._totalIdle--;
                itemsToRemove--;
            }
        }
        destroy(toDestroy, this._factory);
    }

    public void clear(K key) {
        Map<K, List<ObjectTimestampPair<V>>> toDestroy = new HashMap<>();
        synchronized (this) {
            try {
                GenericKeyedObjectPool<K, V>.ObjectQueue pool = this._poolMap.remove(key);
                if (pool == null) {
                    try {
                    } catch (Throwable th) {
                        th = th;
                        throw th;
                    }
                } else {
                    this._poolList.remove((Object) key);
                    List<ObjectTimestampPair<V>> objects = new ArrayList<>();
                    objects.addAll(pool.queue);
                    toDestroy.put(key, objects);
                    this._totalIdle -= pool.queue.size();
                    this._totalInternalProcessing += pool.queue.size();
                    pool.queue.clear();
                    destroy(toDestroy, this._factory);
                }
            } catch (Throwable th2) {
                th = th2;
                throw th;
            }
        }
    }

    /*  JADX ERROR: IndexOutOfBoundsException in pass: RegionMakerVisitor
        java.lang.IndexOutOfBoundsException: Index: 0, Size: 0
        	at java.util.ArrayList.rangeCheck(ArrayList.java:657)
        	at java.util.ArrayList.get(ArrayList.java:433)
        	at jadx.core.dex.nodes.InsnNode.getArg(InsnNode.java:101)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:611)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:561)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processExcHandler(RegionMaker.java:1043)
        	at jadx.core.dex.visitors.regions.RegionMaker.processTryCatchBlocks(RegionMaker.java:975)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:52)
        */
    private void destroy(java.util.Map<K, java.util.List<org.apache.commons.pool.impl.GenericKeyedObjectPool.ObjectTimestampPair<V>>> r9, org.apache.commons.pool.KeyedPoolableObjectFactory<K, V> r10) {
        /*
            r8 = this;
            java.util.Set r0 = r9.entrySet()
            java.util.Iterator r0 = r0.iterator()
        L_0x0008:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x00f0
            java.lang.Object r1 = r0.next()
            java.util.Map$Entry r1 = (java.util.Map.Entry) r1
            java.lang.Object r2 = r1.getKey()
            java.lang.Object r3 = r1.getValue()
            java.util.List r3 = (java.util.List) r3
            java.util.Iterator r4 = r3.iterator()
        L_0x0022:
            boolean r5 = r4.hasNext()
            if (r5 == 0) goto L_0x00ee
            java.lang.Object r5 = r4.next()     // Catch:{ Exception -> 0x00ad, all -> 0x006f }
            org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectTimestampPair r5 = (org.apache.commons.pool.impl.GenericKeyedObjectPool.ObjectTimestampPair) r5     // Catch:{ Exception -> 0x00ad, all -> 0x006f }
            T r5 = r5.value     // Catch:{ Exception -> 0x00ad, all -> 0x006f }
            r10.destroyObject(r2, r5)     // Catch:{ Exception -> 0x00ad, all -> 0x006f }
            monitor-enter(r8)
            java.util.Map<K, org.apache.commons.pool.impl.GenericKeyedObjectPool<K, V>$ObjectQueue> r5 = r8._poolMap     // Catch:{ all -> 0x006c }
            java.lang.Object r5 = r5.get(r2)     // Catch:{ all -> 0x006c }
            org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectQueue r5 = (org.apache.commons.pool.impl.GenericKeyedObjectPool.ObjectQueue) r5     // Catch:{ all -> 0x006c }
            if (r5 == 0) goto L_0x0063
            r5.decrementInternalProcessingCount()     // Catch:{ all -> 0x006c }
            int r6 = r5.internalProcessingCount     // Catch:{ all -> 0x006c }
            if (r6 != 0) goto L_0x0069
            int r6 = r5.activeCount     // Catch:{ all -> 0x006c }
            if (r6 != 0) goto L_0x0069
            org.apache.commons.pool.impl.CursorableLinkedList r6 = r5.queue     // Catch:{ all -> 0x006c }
            boolean r6 = r6.isEmpty()     // Catch:{ all -> 0x006c }
            if (r6 == 0) goto L_0x0069
            java.util.Map<K, org.apache.commons.pool.impl.GenericKeyedObjectPool<K, V>$ObjectQueue> r6 = r8._poolMap     // Catch:{ all -> 0x006c }
            r6.remove(r2)     // Catch:{ all -> 0x006c }
            org.apache.commons.pool.impl.CursorableLinkedList<K> r6 = r8._poolList     // Catch:{ all -> 0x006c }
            r6.remove((java.lang.Object) r2)     // Catch:{ all -> 0x006c }
            goto L_0x0069
        L_0x0063:
            int r6 = r8._totalInternalProcessing     // Catch:{ all -> 0x006c }
            int r6 = r6 + -1
            r8._totalInternalProcessing = r6     // Catch:{ all -> 0x006c }
        L_0x0069:
            monitor-exit(r8)     // Catch:{ all -> 0x006c }
            goto L_0x00e5
        L_0x006c:
            r5 = move-exception
            monitor-exit(r8)     // Catch:{ all -> 0x006c }
            throw r5
        L_0x006f:
            r5 = move-exception
            monitor-enter(r8)
            java.util.Map<K, org.apache.commons.pool.impl.GenericKeyedObjectPool<K, V>$ObjectQueue> r6 = r8._poolMap     // Catch:{ all -> 0x00aa }
            java.lang.Object r6 = r6.get(r2)     // Catch:{ all -> 0x00aa }
            org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectQueue r6 = (org.apache.commons.pool.impl.GenericKeyedObjectPool.ObjectQueue) r6     // Catch:{ all -> 0x00aa }
            if (r6 == 0) goto L_0x009f
            r6.decrementInternalProcessingCount()     // Catch:{ all -> 0x00aa }
            int r7 = r6.internalProcessingCount     // Catch:{ all -> 0x00aa }
            if (r7 != 0) goto L_0x00a5
            int r7 = r6.activeCount     // Catch:{ all -> 0x00aa }
            if (r7 != 0) goto L_0x00a5
            org.apache.commons.pool.impl.CursorableLinkedList r7 = r6.queue     // Catch:{ all -> 0x00aa }
            boolean r7 = r7.isEmpty()     // Catch:{ all -> 0x00aa }
            if (r7 == 0) goto L_0x00a5
            java.util.Map<K, org.apache.commons.pool.impl.GenericKeyedObjectPool<K, V>$ObjectQueue> r7 = r8._poolMap     // Catch:{ all -> 0x00aa }
            r7.remove(r2)     // Catch:{ all -> 0x00aa }
            org.apache.commons.pool.impl.CursorableLinkedList<K> r7 = r8._poolList     // Catch:{ all -> 0x00aa }
            r7.remove((java.lang.Object) r2)     // Catch:{ all -> 0x00aa }
            goto L_0x00a5
        L_0x009f:
            int r7 = r8._totalInternalProcessing     // Catch:{ all -> 0x00aa }
            int r7 = r7 + -1
            r8._totalInternalProcessing = r7     // Catch:{ all -> 0x00aa }
        L_0x00a5:
            monitor-exit(r8)     // Catch:{ all -> 0x00aa }
            r8.allocate()
            throw r5
        L_0x00aa:
            r5 = move-exception
            monitor-exit(r8)     // Catch:{ all -> 0x00aa }
            throw r5
        L_0x00ad:
            r5 = move-exception
            monitor-enter(r8)
            java.util.Map<K, org.apache.commons.pool.impl.GenericKeyedObjectPool<K, V>$ObjectQueue> r5 = r8._poolMap     // Catch:{ all -> 0x00eb }
            java.lang.Object r5 = r5.get(r2)     // Catch:{ all -> 0x00eb }
            org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectQueue r5 = (org.apache.commons.pool.impl.GenericKeyedObjectPool.ObjectQueue) r5     // Catch:{ all -> 0x00eb }
            if (r5 == 0) goto L_0x00de
            r5.decrementInternalProcessingCount()     // Catch:{ all -> 0x00eb }
            int r6 = r5.internalProcessingCount     // Catch:{ all -> 0x00eb }
            if (r6 != 0) goto L_0x00e4
            int r6 = r5.activeCount     // Catch:{ all -> 0x00eb }
            if (r6 != 0) goto L_0x00e4
            org.apache.commons.pool.impl.CursorableLinkedList r6 = r5.queue     // Catch:{ all -> 0x00eb }
            boolean r6 = r6.isEmpty()     // Catch:{ all -> 0x00eb }
            if (r6 == 0) goto L_0x00e4
            java.util.Map<K, org.apache.commons.pool.impl.GenericKeyedObjectPool<K, V>$ObjectQueue> r6 = r8._poolMap     // Catch:{ all -> 0x00eb }
            r6.remove(r2)     // Catch:{ all -> 0x00eb }
            org.apache.commons.pool.impl.CursorableLinkedList<K> r6 = r8._poolList     // Catch:{ all -> 0x00eb }
            r6.remove((java.lang.Object) r2)     // Catch:{ all -> 0x00eb }
            goto L_0x00e4
        L_0x00de:
            int r6 = r8._totalInternalProcessing     // Catch:{ all -> 0x00eb }
            int r6 = r6 + -1
            r8._totalInternalProcessing = r6     // Catch:{ all -> 0x00eb }
        L_0x00e4:
            monitor-exit(r8)     // Catch:{ all -> 0x00eb }
        L_0x00e5:
            r8.allocate()
            goto L_0x0022
        L_0x00eb:
            r5 = move-exception
            monitor-exit(r8)     // Catch:{ all -> 0x00eb }
            throw r5
        L_0x00ee:
            goto L_0x0008
        L_0x00f0:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.pool.impl.GenericKeyedObjectPool.destroy(java.util.Map, org.apache.commons.pool.KeyedPoolableObjectFactory):void");
    }

    public synchronized int getNumActive() {
        return this._totalActive;
    }

    public synchronized int getNumIdle() {
        return this._totalIdle;
    }

    public synchronized int getNumActive(Object key) {
        GenericKeyedObjectPool<K, V>.ObjectQueue pool;
        pool = this._poolMap.get(key);
        return pool != null ? pool.activeCount : 0;
    }

    public synchronized int getNumIdle(Object key) {
        GenericKeyedObjectPool<K, V>.ObjectQueue pool;
        pool = this._poolMap.get(key);
        return pool != null ? pool.queue.size() : 0;
    }

    public void returnObject(K key, V obj) throws Exception {
        try {
            addObjectToPool(key, obj, true);
        } catch (Exception e) {
            KeyedPoolableObjectFactory<K, V> keyedPoolableObjectFactory = this._factory;
            if (keyedPoolableObjectFactory != null) {
                try {
                    keyedPoolableObjectFactory.destroyObject(key, obj);
                } catch (Exception e2) {
                }
                GenericKeyedObjectPool<K, V>.ObjectQueue pool = this._poolMap.get(key);
                if (pool != null) {
                    synchronized (this) {
                        pool.decrementActiveCount();
                        if (pool.queue.isEmpty() && pool.activeCount == 0 && pool.internalProcessingCount == 0) {
                            this._poolMap.remove(key);
                            this._poolList.remove((Object) key);
                        }
                        allocate();
                    }
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0079, code lost:
        if (r2 == false) goto L_0x007e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x007b, code lost:
        allocate();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x007e, code lost:
        if (r1 == false) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:?, code lost:
        r7._factory.destroyObject(r8, r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void addObjectToPool(K r8, V r9, boolean r10) throws java.lang.Exception {
        /*
            r7 = this;
            r0 = 1
            boolean r1 = r7._testOnReturn
            if (r1 == 0) goto L_0x000f
            org.apache.commons.pool.KeyedPoolableObjectFactory<K, V> r1 = r7._factory
            boolean r1 = r1.validateObject(r8, r9)
            if (r1 != 0) goto L_0x000f
            r0 = 0
            goto L_0x0014
        L_0x000f:
            org.apache.commons.pool.KeyedPoolableObjectFactory<K, V> r1 = r7._factory
            r1.passivateObject(r8, r9)
        L_0x0014:
            r1 = r0 ^ 1
            r2 = 0
            monitor-enter(r7)
            r3 = 0
            java.util.Map<K, org.apache.commons.pool.impl.GenericKeyedObjectPool<K, V>$ObjectQueue> r4 = r7._poolMap     // Catch:{ all -> 0x00b6 }
            java.lang.Object r4 = r4.get(r8)     // Catch:{ all -> 0x00b6 }
            org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectQueue r4 = (org.apache.commons.pool.impl.GenericKeyedObjectPool.ObjectQueue) r4     // Catch:{ all -> 0x00b6 }
            if (r4 != 0) goto L_0x0033
            org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectQueue r5 = new org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectQueue     // Catch:{ all -> 0x00bc }
            r5.<init>()     // Catch:{ all -> 0x00bc }
            r4 = r5
            java.util.Map<K, org.apache.commons.pool.impl.GenericKeyedObjectPool<K, V>$ObjectQueue> r3 = r7._poolMap     // Catch:{ all -> 0x00bc }
            r3.put(r8, r4)     // Catch:{ all -> 0x00bc }
            org.apache.commons.pool.impl.CursorableLinkedList<K> r3 = r7._poolList     // Catch:{ all -> 0x00bc }
            r3.add(r8)     // Catch:{ all -> 0x00bc }
        L_0x0033:
            boolean r3 = r7.isClosed()     // Catch:{ all -> 0x00bc }
            if (r3 == 0) goto L_0x003b
            r1 = 1
            goto L_0x0078
        L_0x003b:
            int r3 = r7._maxIdle     // Catch:{ all -> 0x00bc }
            if (r3 < 0) goto L_0x004d
            org.apache.commons.pool.impl.CursorableLinkedList r3 = r4.queue     // Catch:{ all -> 0x00bc }
            int r3 = r3.size()     // Catch:{ all -> 0x00bc }
            int r5 = r7._maxIdle     // Catch:{ all -> 0x00bc }
            if (r3 < r5) goto L_0x004d
            r1 = 1
            goto L_0x0078
        L_0x004d:
            if (r0 == 0) goto L_0x0078
            boolean r3 = r7._lifo     // Catch:{ all -> 0x00bc }
            if (r3 == 0) goto L_0x0060
            org.apache.commons.pool.impl.CursorableLinkedList r3 = r4.queue     // Catch:{ all -> 0x00bc }
            org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectTimestampPair r5 = new org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectTimestampPair     // Catch:{ all -> 0x00bc }
            r5.<init>(r9)     // Catch:{ all -> 0x00bc }
            r3.addFirst(r5)     // Catch:{ all -> 0x00bc }
            goto L_0x006c
        L_0x0060:
            org.apache.commons.pool.impl.CursorableLinkedList r3 = r4.queue     // Catch:{ all -> 0x00bc }
            org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectTimestampPair r5 = new org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectTimestampPair     // Catch:{ all -> 0x00bc }
            r5.<init>(r9)     // Catch:{ all -> 0x00bc }
            r3.addLast(r5)     // Catch:{ all -> 0x00bc }
        L_0x006c:
            int r3 = r7._totalIdle     // Catch:{ all -> 0x00bc }
            int r3 = r3 + 1
            r7._totalIdle = r3     // Catch:{ all -> 0x00bc }
            if (r10 == 0) goto L_0x0077
            r4.decrementActiveCount()     // Catch:{ all -> 0x00bc }
        L_0x0077:
            r2 = 1
        L_0x0078:
            monitor-exit(r7)     // Catch:{ all -> 0x00bc }
            if (r2 == 0) goto L_0x007e
            r7.allocate()
        L_0x007e:
            if (r1 == 0) goto L_0x00b5
            org.apache.commons.pool.KeyedPoolableObjectFactory<K, V> r3 = r7._factory     // Catch:{ Exception -> 0x0086 }
            r3.destroyObject(r8, r9)     // Catch:{ Exception -> 0x0086 }
            goto L_0x0087
        L_0x0086:
            r3 = move-exception
        L_0x0087:
            if (r10 == 0) goto L_0x00b5
            monitor-enter(r7)
            r4.decrementActiveCount()     // Catch:{ all -> 0x00b2 }
            org.apache.commons.pool.impl.CursorableLinkedList r3 = r4.queue     // Catch:{ all -> 0x00b2 }
            boolean r3 = r3.isEmpty()     // Catch:{ all -> 0x00b2 }
            if (r3 == 0) goto L_0x00ad
            int r3 = r4.activeCount     // Catch:{ all -> 0x00b2 }
            if (r3 != 0) goto L_0x00ad
            int r3 = r4.internalProcessingCount     // Catch:{ all -> 0x00b2 }
            if (r3 != 0) goto L_0x00ad
            java.util.Map<K, org.apache.commons.pool.impl.GenericKeyedObjectPool<K, V>$ObjectQueue> r3 = r7._poolMap     // Catch:{ all -> 0x00b2 }
            r3.remove(r8)     // Catch:{ all -> 0x00b2 }
            org.apache.commons.pool.impl.CursorableLinkedList<K> r3 = r7._poolList     // Catch:{ all -> 0x00b2 }
            r3.remove((java.lang.Object) r8)     // Catch:{ all -> 0x00b2 }
        L_0x00ad:
            monitor-exit(r7)     // Catch:{ all -> 0x00b2 }
            r7.allocate()
            goto L_0x00b5
        L_0x00b2:
            r3 = move-exception
            monitor-exit(r7)     // Catch:{ all -> 0x00b2 }
            throw r3
        L_0x00b5:
            return
        L_0x00b6:
            r4 = move-exception
            r6 = r4
            r4 = r3
            r3 = r6
        L_0x00ba:
            monitor-exit(r7)     // Catch:{ all -> 0x00bc }
            throw r3
        L_0x00bc:
            r3 = move-exception
            goto L_0x00ba
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.pool.impl.GenericKeyedObjectPool.addObjectToPool(java.lang.Object, java.lang.Object, boolean):void");
    }

    public void invalidateObject(K key, V obj) throws Exception {
        try {
            this._factory.destroyObject(key, obj);
            synchronized (this) {
                GenericKeyedObjectPool<K, V>.ObjectQueue pool = this._poolMap.get(key);
                if (pool == null) {
                    pool = new ObjectQueue();
                    this._poolMap.put(key, pool);
                    this._poolList.add(key);
                }
                pool.decrementActiveCount();
            }
            allocate();
        } catch (Throwable th) {
            synchronized (this) {
                GenericKeyedObjectPool<K, V>.ObjectQueue pool2 = this._poolMap.get(key);
                if (pool2 == null) {
                    pool2 = new ObjectQueue();
                    this._poolMap.put(key, pool2);
                    this._poolList.add(key);
                }
                pool2.decrementActiveCount();
                allocate();
                throw th;
            }
        }
    }

    public void addObject(K key) throws Exception {
        assertOpen();
        KeyedPoolableObjectFactory<K, V> keyedPoolableObjectFactory = this._factory;
        if (keyedPoolableObjectFactory != null) {
            V obj = keyedPoolableObjectFactory.makeObject(key);
            try {
                assertOpen();
                addObjectToPool(key, obj, false);
            } catch (IllegalStateException ex) {
                try {
                    this._factory.destroyObject(key, obj);
                } catch (Exception e) {
                }
                throw ex;
            }
        } else {
            throw new IllegalStateException("Cannot add objects without a factory.");
        }
    }

    public synchronized void preparePool(K key, boolean populateImmediately) {
        if (this._poolMap.get(key) == null) {
            this._poolMap.put(key, new ObjectQueue());
            this._poolList.add(key);
        }
        if (populateImmediately) {
            try {
                ensureMinIdle(key);
            } catch (Exception e) {
            }
        }
    }

    public void close() throws Exception {
        super.close();
        synchronized (this) {
            clear();
            if (this._evictionCursor != null) {
                this._evictionCursor.close();
                this._evictionCursor = null;
            }
            if (this._evictionKeyCursor != null) {
                this._evictionKeyCursor.close();
                this._evictionKeyCursor = null;
            }
            startEvictor(-1);
            while (this._allocationQueue.size() > 0) {
                GenericKeyedObjectPool<K, V>.Latch<K, V> l = this._allocationQueue.removeFirst();
                synchronized (l) {
                    l.notify();
                }
            }
        }
    }

    @Deprecated
    public void setFactory(KeyedPoolableObjectFactory<K, V> factory) throws IllegalStateException {
        Map<K, List<ObjectTimestampPair<V>>> toDestroy = new HashMap<>();
        KeyedPoolableObjectFactory<K, V> oldFactory = this._factory;
        synchronized (this) {
            assertOpen();
            if (getNumActive() <= 0) {
                Iterator<K> it = this._poolMap.keySet().iterator();
                while (it.hasNext()) {
                    K key = it.next();
                    GenericKeyedObjectPool<K, V>.ObjectQueue pool = this._poolMap.get(key);
                    if (pool != null) {
                        List<ObjectTimestampPair<V>> objects = new ArrayList<>();
                        objects.addAll(pool.queue);
                        toDestroy.put(key, objects);
                        it.remove();
                        this._poolList.remove((Object) key);
                        this._totalIdle -= pool.queue.size();
                        this._totalInternalProcessing += pool.queue.size();
                        pool.queue.clear();
                    }
                }
                this._factory = factory;
            } else {
                throw new IllegalStateException("Objects are already active");
            }
        }
        destroy(toDestroy, oldFactory);
    }

    /*  JADX ERROR: IndexOutOfBoundsException in pass: RegionMakerVisitor
        java.lang.IndexOutOfBoundsException: Index: 0, Size: 0
        	at java.util.ArrayList.rangeCheck(ArrayList.java:657)
        	at java.util.ArrayList.get(ArrayList.java:433)
        	at jadx.core.dex.nodes.InsnNode.getArg(InsnNode.java:101)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:611)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:561)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processHandlersOutBlocks(RegionMaker.java:1008)
        	at jadx.core.dex.visitors.regions.RegionMaker.processTryCatchBlocks(RegionMaker.java:978)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:52)
        */
    /* JADX WARNING: Removed duplicated region for block: B:101:0x0142 A[SYNTHETIC, Splitter:B:101:0x0142] */
    public void evict() throws java.lang.Exception {
        /*
            r14 = this;
            r1 = 0
            monitor-enter(r14)
            r2 = 0
            boolean r0 = r14._testWhileIdle     // Catch:{ all -> 0x01a7 }
            r4 = r0
            long r5 = r14._minEvictableIdleTimeMillis     // Catch:{ all -> 0x01a5 }
            org.apache.commons.pool.impl.CursorableLinkedList<K>$Cursor r0 = r14._evictionKeyCursor     // Catch:{ all -> 0x01ac }
            if (r0 == 0) goto L_0x001c
            org.apache.commons.pool.impl.CursorableLinkedList<K>$Cursor r0 = r14._evictionKeyCursor     // Catch:{ all -> 0x01ac }
            org.apache.commons.pool.impl.CursorableLinkedList$Listable r0 = r0._lastReturned     // Catch:{ all -> 0x01ac }
            if (r0 == 0) goto L_0x001c
            org.apache.commons.pool.impl.CursorableLinkedList<K>$Cursor r0 = r14._evictionKeyCursor     // Catch:{ all -> 0x01ac }
            org.apache.commons.pool.impl.CursorableLinkedList$Listable r0 = r0._lastReturned     // Catch:{ all -> 0x01ac }
            java.lang.Object r0 = r0.value()     // Catch:{ all -> 0x01ac }
            r1 = r0
        L_0x001c:
            monitor-exit(r14)     // Catch:{ all -> 0x01ac }
            r0 = 0
            int r7 = r14.getNumTests()
            r8 = 0
            r9 = r8
            r8 = r1
            r1 = r0
        L_0x0026:
            if (r1 >= r7) goto L_0x01a1
            monitor-enter(r14)
            java.util.Map<K, org.apache.commons.pool.impl.GenericKeyedObjectPool<K, V>$ObjectQueue> r0 = r14._poolMap     // Catch:{ all -> 0x019b }
            if (r0 == 0) goto L_0x0196
            java.util.Map<K, org.apache.commons.pool.impl.GenericKeyedObjectPool<K, V>$ObjectQueue> r0 = r14._poolMap     // Catch:{ all -> 0x019b }
            int r0 = r0.size()     // Catch:{ all -> 0x019b }
            if (r0 != 0) goto L_0x0037
            goto L_0x0196
        L_0x0037:
            org.apache.commons.pool.impl.CursorableLinkedList<K>$Cursor r0 = r14._evictionKeyCursor     // Catch:{ all -> 0x019b }
            if (r0 != 0) goto L_0x003f
            r14.resetEvictionKeyCursor()     // Catch:{ all -> 0x019b }
            r8 = 0
        L_0x003f:
            org.apache.commons.pool.impl.CursorableLinkedList<org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectTimestampPair<V>>$Cursor r0 = r14._evictionCursor     // Catch:{ all -> 0x019b }
            if (r0 != 0) goto L_0x006f
            org.apache.commons.pool.impl.CursorableLinkedList<K>$Cursor r0 = r14._evictionKeyCursor     // Catch:{ all -> 0x019b }
            boolean r0 = r0.hasNext()     // Catch:{ all -> 0x019b }
            if (r0 == 0) goto L_0x0056
            org.apache.commons.pool.impl.CursorableLinkedList<K>$Cursor r0 = r14._evictionKeyCursor     // Catch:{ all -> 0x019b }
            java.lang.Object r0 = r0.next()     // Catch:{ all -> 0x019b }
            r8 = r0
            r14.resetEvictionObjectCursor(r8)     // Catch:{ all -> 0x019b }
            goto L_0x006f
        L_0x0056:
            r14.resetEvictionKeyCursor()     // Catch:{ all -> 0x019b }
            org.apache.commons.pool.impl.CursorableLinkedList<K>$Cursor r0 = r14._evictionKeyCursor     // Catch:{ all -> 0x019b }
            if (r0 == 0) goto L_0x006f
            org.apache.commons.pool.impl.CursorableLinkedList<K>$Cursor r0 = r14._evictionKeyCursor     // Catch:{ all -> 0x019b }
            boolean r0 = r0.hasNext()     // Catch:{ all -> 0x019b }
            if (r0 == 0) goto L_0x006f
            org.apache.commons.pool.impl.CursorableLinkedList<K>$Cursor r0 = r14._evictionKeyCursor     // Catch:{ all -> 0x019b }
            java.lang.Object r0 = r0.next()     // Catch:{ all -> 0x019b }
            r8 = r0
            r14.resetEvictionObjectCursor(r8)     // Catch:{ all -> 0x019b }
        L_0x006f:
            org.apache.commons.pool.impl.CursorableLinkedList<org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectTimestampPair<V>>$Cursor r0 = r14._evictionCursor     // Catch:{ all -> 0x019b }
            if (r0 != 0) goto L_0x0076
            monitor-exit(r14)     // Catch:{ all -> 0x019b }
            goto L_0x0197
        L_0x0076:
            boolean r0 = r14._lifo     // Catch:{ all -> 0x019b }
            if (r0 == 0) goto L_0x0082
            org.apache.commons.pool.impl.CursorableLinkedList<org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectTimestampPair<V>>$Cursor r0 = r14._evictionCursor     // Catch:{ all -> 0x019b }
            boolean r0 = r0.hasPrevious()     // Catch:{ all -> 0x019b }
            if (r0 == 0) goto L_0x008e
        L_0x0082:
            boolean r0 = r14._lifo     // Catch:{ all -> 0x019b }
            if (r0 != 0) goto L_0x00be
            org.apache.commons.pool.impl.CursorableLinkedList<org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectTimestampPair<V>>$Cursor r0 = r14._evictionCursor     // Catch:{ all -> 0x019b }
            boolean r0 = r0.hasNext()     // Catch:{ all -> 0x019b }
            if (r0 != 0) goto L_0x00be
        L_0x008e:
            org.apache.commons.pool.impl.CursorableLinkedList<K>$Cursor r0 = r14._evictionKeyCursor     // Catch:{ all -> 0x019b }
            if (r0 == 0) goto L_0x00be
            org.apache.commons.pool.impl.CursorableLinkedList<K>$Cursor r0 = r14._evictionKeyCursor     // Catch:{ all -> 0x019b }
            boolean r0 = r0.hasNext()     // Catch:{ all -> 0x019b }
            if (r0 == 0) goto L_0x00a5
            org.apache.commons.pool.impl.CursorableLinkedList<K>$Cursor r0 = r14._evictionKeyCursor     // Catch:{ all -> 0x019b }
            java.lang.Object r0 = r0.next()     // Catch:{ all -> 0x019b }
            r8 = r0
            r14.resetEvictionObjectCursor(r8)     // Catch:{ all -> 0x019b }
            goto L_0x00be
        L_0x00a5:
            r14.resetEvictionKeyCursor()     // Catch:{ all -> 0x019b }
            org.apache.commons.pool.impl.CursorableLinkedList<K>$Cursor r0 = r14._evictionKeyCursor     // Catch:{ all -> 0x019b }
            if (r0 == 0) goto L_0x00be
            org.apache.commons.pool.impl.CursorableLinkedList<K>$Cursor r0 = r14._evictionKeyCursor     // Catch:{ all -> 0x019b }
            boolean r0 = r0.hasNext()     // Catch:{ all -> 0x019b }
            if (r0 == 0) goto L_0x00be
            org.apache.commons.pool.impl.CursorableLinkedList<K>$Cursor r0 = r14._evictionKeyCursor     // Catch:{ all -> 0x019b }
            java.lang.Object r0 = r0.next()     // Catch:{ all -> 0x019b }
            r8 = r0
            r14.resetEvictionObjectCursor(r8)     // Catch:{ all -> 0x019b }
        L_0x00be:
            boolean r0 = r14._lifo     // Catch:{ all -> 0x019b }
            if (r0 == 0) goto L_0x00ca
            org.apache.commons.pool.impl.CursorableLinkedList<org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectTimestampPair<V>>$Cursor r0 = r14._evictionCursor     // Catch:{ all -> 0x019b }
            boolean r0 = r0.hasPrevious()     // Catch:{ all -> 0x019b }
            if (r0 == 0) goto L_0x00d6
        L_0x00ca:
            boolean r0 = r14._lifo     // Catch:{ all -> 0x019b }
            if (r0 != 0) goto L_0x00d9
            org.apache.commons.pool.impl.CursorableLinkedList<org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectTimestampPair<V>>$Cursor r0 = r14._evictionCursor     // Catch:{ all -> 0x019b }
            boolean r0 = r0.hasNext()     // Catch:{ all -> 0x019b }
            if (r0 != 0) goto L_0x00d9
        L_0x00d6:
            monitor-exit(r14)     // Catch:{ all -> 0x019b }
            goto L_0x0197
        L_0x00d9:
            boolean r0 = r14._lifo     // Catch:{ all -> 0x019b }
            if (r0 == 0) goto L_0x00e4
            org.apache.commons.pool.impl.CursorableLinkedList<org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectTimestampPair<V>>$Cursor r0 = r14._evictionCursor     // Catch:{ all -> 0x019b }
            java.lang.Object r0 = r0.previous()     // Catch:{ all -> 0x019b }
            goto L_0x00ea
        L_0x00e4:
            org.apache.commons.pool.impl.CursorableLinkedList<org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectTimestampPair<V>>$Cursor r0 = r14._evictionCursor     // Catch:{ all -> 0x019b }
            java.lang.Object r0 = r0.next()     // Catch:{ all -> 0x019b }
        L_0x00ea:
            org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectTimestampPair r0 = (org.apache.commons.pool.impl.GenericKeyedObjectPool.ObjectTimestampPair) r0     // Catch:{ all -> 0x019b }
            r9 = r0
            org.apache.commons.pool.impl.CursorableLinkedList<org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectTimestampPair<V>>$Cursor r0 = r14._evictionCursor     // Catch:{ all -> 0x0193 }
            r0.remove()     // Catch:{ all -> 0x0193 }
            java.util.Map<K, org.apache.commons.pool.impl.GenericKeyedObjectPool<K, V>$ObjectQueue> r0 = r14._poolMap     // Catch:{ all -> 0x0193 }
            java.lang.Object r0 = r0.get(r8)     // Catch:{ all -> 0x0193 }
            org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectQueue r0 = (org.apache.commons.pool.impl.GenericKeyedObjectPool.ObjectQueue) r0     // Catch:{ all -> 0x0193 }
            r0.incrementInternalProcessingCount()     // Catch:{ all -> 0x0193 }
            int r10 = r14._totalIdle     // Catch:{ all -> 0x0193 }
            int r10 = r10 + -1
            r14._totalIdle = r10     // Catch:{ all -> 0x0193 }
            monitor-exit(r14)     // Catch:{ all -> 0x0193 }
            r0 = 0
            int r10 = (r5 > r2 ? 1 : (r5 == r2 ? 0 : -1))
            if (r10 <= 0) goto L_0x0117
            long r10 = java.lang.System.currentTimeMillis()
            long r12 = r9.tstamp
            long r10 = r10 - r12
            int r10 = (r10 > r5 ? 1 : (r10 == r5 ? 0 : -1))
            if (r10 <= 0) goto L_0x0117
            r0 = 1
            r10 = r0
            goto L_0x0118
        L_0x0117:
            r10 = r0
        L_0x0118:
            if (r4 == 0) goto L_0x0140
            if (r10 != 0) goto L_0x0140
            r11 = 0
            org.apache.commons.pool.KeyedPoolableObjectFactory<K, V> r0 = r14._factory     // Catch:{ Exception -> 0x0126 }
            T r12 = r9.value     // Catch:{ Exception -> 0x0126 }
            r0.activateObject(r8, r12)     // Catch:{ Exception -> 0x0126 }
            r11 = 1
            goto L_0x0128
        L_0x0126:
            r0 = move-exception
            r10 = 1
        L_0x0128:
            if (r11 == 0) goto L_0x0140
            org.apache.commons.pool.KeyedPoolableObjectFactory<K, V> r0 = r14._factory
            T r12 = r9.value
            boolean r0 = r0.validateObject(r8, r12)
            if (r0 != 0) goto L_0x0136
            r10 = 1
            goto L_0x0140
        L_0x0136:
            org.apache.commons.pool.KeyedPoolableObjectFactory<K, V> r0 = r14._factory     // Catch:{ Exception -> 0x013e }
            T r12 = r9.value     // Catch:{ Exception -> 0x013e }
            r0.passivateObject(r8, r12)     // Catch:{ Exception -> 0x013e }
            goto L_0x0140
        L_0x013e:
            r0 = move-exception
            r10 = 1
        L_0x0140:
            if (r10 == 0) goto L_0x014b
            org.apache.commons.pool.KeyedPoolableObjectFactory<K, V> r0 = r14._factory     // Catch:{ Exception -> 0x014a }
            T r11 = r9.value     // Catch:{ Exception -> 0x014a }
            r0.destroyObject(r8, r11)     // Catch:{ Exception -> 0x014a }
            goto L_0x014b
        L_0x014a:
            r0 = move-exception
        L_0x014b:
            monitor-enter(r14)
            java.util.Map<K, org.apache.commons.pool.impl.GenericKeyedObjectPool<K, V>$ObjectQueue> r0 = r14._poolMap     // Catch:{ all -> 0x0190 }
            java.lang.Object r0 = r0.get(r8)     // Catch:{ all -> 0x0190 }
            org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectQueue r0 = (org.apache.commons.pool.impl.GenericKeyedObjectPool.ObjectQueue) r0     // Catch:{ all -> 0x0190 }
            r0.decrementInternalProcessingCount()     // Catch:{ all -> 0x0190 }
            if (r10 == 0) goto L_0x017a
            org.apache.commons.pool.impl.CursorableLinkedList r11 = r0.queue     // Catch:{ all -> 0x0190 }
            boolean r11 = r11.isEmpty()     // Catch:{ all -> 0x0190 }
            if (r11 == 0) goto L_0x018e
            int r11 = r0.activeCount     // Catch:{ all -> 0x0190 }
            if (r11 != 0) goto L_0x018e
            int r11 = r0.internalProcessingCount     // Catch:{ all -> 0x0190 }
            if (r11 != 0) goto L_0x018e
            java.util.Map<K, org.apache.commons.pool.impl.GenericKeyedObjectPool<K, V>$ObjectQueue> r11 = r14._poolMap     // Catch:{ all -> 0x0190 }
            r11.remove(r8)     // Catch:{ all -> 0x0190 }
            org.apache.commons.pool.impl.CursorableLinkedList<K> r11 = r14._poolList     // Catch:{ all -> 0x0190 }
            r11.remove((java.lang.Object) r8)     // Catch:{ all -> 0x0190 }
            goto L_0x018e
        L_0x017a:
            org.apache.commons.pool.impl.CursorableLinkedList<org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectTimestampPair<V>>$Cursor r11 = r14._evictionCursor     // Catch:{ all -> 0x0190 }
            r11.add(r9)     // Catch:{ all -> 0x0190 }
            int r11 = r14._totalIdle     // Catch:{ all -> 0x0190 }
            int r11 = r11 + 1
            r14._totalIdle = r11     // Catch:{ all -> 0x0190 }
            boolean r11 = r14._lifo     // Catch:{ all -> 0x0190 }
            if (r11 == 0) goto L_0x018e
            org.apache.commons.pool.impl.CursorableLinkedList<org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectTimestampPair<V>>$Cursor r11 = r14._evictionCursor     // Catch:{ all -> 0x0190 }
            r11.previous()     // Catch:{ all -> 0x0190 }
        L_0x018e:
            monitor-exit(r14)     // Catch:{ all -> 0x0190 }
            goto L_0x0197
        L_0x0190:
            r0 = move-exception
            monitor-exit(r14)     // Catch:{ all -> 0x0190 }
            throw r0
        L_0x0193:
            r0 = move-exception
            r2 = r9
            goto L_0x019d
        L_0x0196:
            monitor-exit(r14)     // Catch:{ all -> 0x019b }
        L_0x0197:
            int r1 = r1 + 1
            goto L_0x0026
        L_0x019b:
            r0 = move-exception
            r2 = r9
        L_0x019d:
            monitor-exit(r14)     // Catch:{ all -> 0x019f }
            throw r0
        L_0x019f:
            r0 = move-exception
            goto L_0x019d
        L_0x01a1:
            r14.allocate()
            return
        L_0x01a5:
            r0 = move-exception
            goto L_0x01a9
        L_0x01a7:
            r0 = move-exception
            r4 = 0
        L_0x01a9:
            r5 = r2
        L_0x01aa:
            monitor-exit(r14)     // Catch:{ all -> 0x01ac }
            throw r0
        L_0x01ac:
            r0 = move-exception
            goto L_0x01aa
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.pool.impl.GenericKeyedObjectPool.evict():void");
    }

    private void resetEvictionKeyCursor() {
        CursorableLinkedList<K>.Cursor cursor = this._evictionKeyCursor;
        if (cursor != null) {
            cursor.close();
        }
        this._evictionKeyCursor = this._poolList.cursor();
        CursorableLinkedList<ObjectTimestampPair<V>>.Cursor cursor2 = this._evictionCursor;
        if (cursor2 != null) {
            cursor2.close();
            this._evictionCursor = null;
        }
    }

    private void resetEvictionObjectCursor(Object key) {
        GenericKeyedObjectPool<K, V>.ObjectQueue pool;
        CursorableLinkedList<ObjectTimestampPair<V>>.Cursor cursor = this._evictionCursor;
        if (cursor != null) {
            cursor.close();
        }
        Map<K, GenericKeyedObjectPool<K, V>.ObjectQueue> map = this._poolMap;
        if (map != null && (pool = map.get(key)) != null) {
            CursorableLinkedList<ObjectTimestampPair<V>> queue = pool.queue;
            this._evictionCursor = queue.cursor(this._lifo ? queue.size() : 0);
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0013, code lost:
        if (r1 >= r0.length) goto L_0x0022;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0015, code lost:
        ensureMinIdle(r0[r1]);
        r1 = r1 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0011, code lost:
        r1 = 0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void ensureMinIdle() throws java.lang.Exception {
        /*
            r3 = this;
            int r0 = r3._minIdle
            if (r0 <= 0) goto L_0x0022
            monitor-enter(r3)
            r0 = 0
            java.util.Map<K, org.apache.commons.pool.impl.GenericKeyedObjectPool<K, V>$ObjectQueue> r1 = r3._poolMap     // Catch:{ all -> 0x001d }
            java.util.Set r1 = r1.keySet()     // Catch:{ all -> 0x001d }
            java.lang.Object[] r0 = r1.toArray()     // Catch:{ all -> 0x001d }
            monitor-exit(r3)     // Catch:{ all -> 0x0020 }
            r1 = 0
        L_0x0012:
            int r2 = r0.length
            if (r1 >= r2) goto L_0x0022
            r2 = r0[r1]
            r3.ensureMinIdle(r2)
            int r1 = r1 + 1
            goto L_0x0012
        L_0x001d:
            r1 = move-exception
        L_0x001e:
            monitor-exit(r3)     // Catch:{ all -> 0x0020 }
            throw r1
        L_0x0020:
            r1 = move-exception
            goto L_0x001e
        L_0x0022:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.pool.impl.GenericKeyedObjectPool.ensureMinIdle():void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0015, code lost:
        if (r2 >= r1) goto L_0x003e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x001c, code lost:
        if (calculateDeficit(r0, true) <= 0) goto L_0x003e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:?, code lost:
        addObject(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0022, code lost:
        monitor-enter(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:?, code lost:
        r0.decrementInternalProcessingCount();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0026, code lost:
        monitor-exit(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0027, code lost:
        allocate();
        r2 = r2 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0031, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0032, code lost:
        monitor-enter(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:?, code lost:
        r0.decrementInternalProcessingCount();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0037, code lost:
        allocate();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x003a, code lost:
        throw r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x000c, code lost:
        if (r0 != null) goto L_0x000f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x000e, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x000f, code lost:
        r1 = calculateDeficit(r0, false);
        r2 = 0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void ensureMinIdle(K r5) throws java.lang.Exception {
        /*
            r4 = this;
            monitor-enter(r4)
            r0 = 0
            java.util.Map<K, org.apache.commons.pool.impl.GenericKeyedObjectPool<K, V>$ObjectQueue> r1 = r4._poolMap     // Catch:{ all -> 0x003f }
            java.lang.Object r1 = r1.get(r5)     // Catch:{ all -> 0x003f }
            org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectQueue r1 = (org.apache.commons.pool.impl.GenericKeyedObjectPool.ObjectQueue) r1     // Catch:{ all -> 0x003f }
            r0 = r1
            monitor-exit(r4)     // Catch:{ all -> 0x0042 }
            if (r0 != 0) goto L_0x000f
            return
        L_0x000f:
            r1 = 0
            int r1 = r4.calculateDeficit(r0, r1)
            r2 = 0
        L_0x0015:
            if (r2 >= r1) goto L_0x003e
            r3 = 1
            int r3 = r4.calculateDeficit(r0, r3)
            if (r3 <= 0) goto L_0x003e
            r4.addObject(r5)     // Catch:{ all -> 0x0031 }
            monitor-enter(r4)
            r0.decrementInternalProcessingCount()     // Catch:{ all -> 0x002e }
            monitor-exit(r4)     // Catch:{ all -> 0x002e }
            r4.allocate()
            int r2 = r2 + 1
            goto L_0x0015
        L_0x002e:
            r3 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x002e }
            throw r3
        L_0x0031:
            r3 = move-exception
            monitor-enter(r4)
            r0.decrementInternalProcessingCount()     // Catch:{ all -> 0x003b }
            monitor-exit(r4)     // Catch:{ all -> 0x003b }
            r4.allocate()
            throw r3
        L_0x003b:
            r3 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x003b }
            throw r3
        L_0x003e:
            return
        L_0x003f:
            r1 = move-exception
        L_0x0040:
            monitor-exit(r4)     // Catch:{ all -> 0x0042 }
            throw r1
        L_0x0042:
            r1 = move-exception
            goto L_0x0040
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.pool.impl.GenericKeyedObjectPool.ensureMinIdle(java.lang.Object):void");
    }

    /* access modifiers changed from: protected */
    public synchronized void startEvictor(long delay) {
        if (this._evictor != null) {
            EvictionTimer.cancel(this._evictor);
            this._evictor = null;
        }
        if (delay > 0) {
            GenericKeyedObjectPool<K, V>.Evictor evictor = new Evictor();
            this._evictor = evictor;
            EvictionTimer.schedule(evictor, delay, delay);
        }
    }

    /* access modifiers changed from: package-private */
    public synchronized String debugInfo() {
        StringBuffer buf;
        buf = new StringBuffer();
        buf.append("Active: ");
        buf.append(getNumActive());
        buf.append(Base64.LINE_SEPARATOR);
        buf.append("Idle: ");
        buf.append(getNumIdle());
        buf.append(Base64.LINE_SEPARATOR);
        for (K key : this._poolMap.keySet()) {
            buf.append("\t");
            buf.append(key);
            buf.append(" ");
            buf.append(this._poolMap.get(key));
            buf.append(Base64.LINE_SEPARATOR);
        }
        return buf.toString();
    }

    private synchronized int getNumTests() {
        if (this._numTestsPerEvictionRun >= 0) {
            return Math.min(this._numTestsPerEvictionRun, this._totalIdle);
        }
        return (int) Math.ceil(((double) this._totalIdle) / Math.abs((double) this._numTestsPerEvictionRun));
    }

    private synchronized int calculateDeficit(GenericKeyedObjectPool<K, V>.ObjectQueue pool, boolean incrementInternal) {
        int objectDefecit;
        objectDefecit = getMinIdle() - pool.queue.size();
        if (getMaxActive() > 0) {
            objectDefecit = Math.min(objectDefecit, Math.max(0, ((getMaxActive() - pool.activeCount) - pool.queue.size()) - pool.internalProcessingCount));
        }
        if (getMaxTotal() > 0) {
            objectDefecit = Math.min(objectDefecit, Math.max(0, ((getMaxTotal() - getNumActive()) - getNumIdle()) - this._totalInternalProcessing));
        }
        if (incrementInternal && objectDefecit > 0) {
            pool.incrementInternalProcessingCount();
        }
        return objectDefecit;
    }

    private class ObjectQueue {
        /* access modifiers changed from: private */
        public int activeCount;
        /* access modifiers changed from: private */
        public int internalProcessingCount;
        /* access modifiers changed from: private */
        public final CursorableLinkedList<ObjectTimestampPair<V>> queue;

        private ObjectQueue() {
            this.activeCount = 0;
            this.queue = new CursorableLinkedList<>();
            this.internalProcessingCount = 0;
        }

        /* access modifiers changed from: package-private */
        public void incrementActiveCount() {
            synchronized (GenericKeyedObjectPool.this) {
                GenericKeyedObjectPool.access$1408(GenericKeyedObjectPool.this);
            }
            this.activeCount++;
        }

        /* access modifiers changed from: package-private */
        public void decrementActiveCount() {
            synchronized (GenericKeyedObjectPool.this) {
                GenericKeyedObjectPool.access$1410(GenericKeyedObjectPool.this);
            }
            int i = this.activeCount;
            if (i > 0) {
                this.activeCount = i - 1;
            }
        }

        /* access modifiers changed from: package-private */
        public void incrementInternalProcessingCount() {
            synchronized (GenericKeyedObjectPool.this) {
                GenericKeyedObjectPool.access$1508(GenericKeyedObjectPool.this);
            }
            this.internalProcessingCount++;
        }

        /* access modifiers changed from: package-private */
        public void decrementInternalProcessingCount() {
            synchronized (GenericKeyedObjectPool.this) {
                GenericKeyedObjectPool.access$1510(GenericKeyedObjectPool.this);
            }
            this.internalProcessingCount--;
        }
    }

    static class ObjectTimestampPair<T> implements Comparable<T> {
        @Deprecated
        long tstamp;
        @Deprecated
        T value;

        ObjectTimestampPair(T val) {
            this(val, System.currentTimeMillis());
        }

        ObjectTimestampPair(T val, long time) {
            this.value = val;
            this.tstamp = time;
        }

        public String toString() {
            return this.value + ";" + this.tstamp;
        }

        public int compareTo(Object obj) {
            return compareTo((ObjectTimestampPair) obj);
        }

        public int compareTo(ObjectTimestampPair<T> other) {
            long tstampdiff = this.tstamp - other.tstamp;
            if (tstampdiff == 0) {
                return System.identityHashCode(this) - System.identityHashCode(other);
            }
            return (int) Math.min(Math.max(tstampdiff, -2147483648L), TTL.MAX_VALUE);
        }

        public T getValue() {
            return this.value;
        }

        public long getTstamp() {
            return this.tstamp;
        }
    }

    private class Evictor extends TimerTask {
        private Evictor() {
        }

        public void run() {
            try {
                GenericKeyedObjectPool.this.evict();
            } catch (Exception e) {
            } catch (OutOfMemoryError oome) {
                oome.printStackTrace(System.err);
            }
            try {
                GenericKeyedObjectPool.this.ensureMinIdle();
            } catch (Exception e2) {
            }
        }
    }

    private final class Latch<LK, LV> {
        private final LK _key;
        private boolean _mayCreate;
        private ObjectTimestampPair<LV> _pair;
        private GenericKeyedObjectPool<K, V>.ObjectQueue _pool;

        private Latch(LK key) {
            this._mayCreate = false;
            this._key = key;
        }

        /* access modifiers changed from: private */
        public synchronized LK getkey() {
            return this._key;
        }

        /* access modifiers changed from: private */
        public synchronized GenericKeyedObjectPool<K, V>.ObjectQueue getPool() {
            return this._pool;
        }

        /* access modifiers changed from: private */
        public synchronized void setPool(GenericKeyedObjectPool<K, V>.ObjectQueue pool) {
            this._pool = pool;
        }

        /* access modifiers changed from: private */
        public synchronized ObjectTimestampPair<LV> getPair() {
            return this._pair;
        }

        /* access modifiers changed from: private */
        public synchronized void setPair(ObjectTimestampPair<LV> pair) {
            this._pair = pair;
        }

        /* access modifiers changed from: private */
        public synchronized boolean mayCreate() {
            return this._mayCreate;
        }

        /* access modifiers changed from: private */
        public synchronized void setMayCreate(boolean mayCreate) {
            this._mayCreate = mayCreate;
        }

        /* access modifiers changed from: private */
        public synchronized void reset() {
            this._pair = null;
            this._mayCreate = false;
        }
    }
}
