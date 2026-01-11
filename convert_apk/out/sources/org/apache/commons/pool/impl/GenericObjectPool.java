package org.apache.commons.pool.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.TimerTask;
import org.apache.commons.pool.BaseObjectPool;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.commons.pool.impl.GenericKeyedObjectPool;
import org.apache.p010ws.commons.util.Base64;

public class GenericObjectPool<T> extends BaseObjectPool<T> implements ObjectPool<T> {
    public static final boolean DEFAULT_LIFO = true;
    public static final int DEFAULT_MAX_ACTIVE = 8;
    public static final int DEFAULT_MAX_IDLE = 8;
    public static final long DEFAULT_MAX_WAIT = -1;
    public static final long DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS = 1800000;
    public static final int DEFAULT_MIN_IDLE = 0;
    public static final int DEFAULT_NUM_TESTS_PER_EVICTION_RUN = 3;
    public static final long DEFAULT_SOFT_MIN_EVICTABLE_IDLE_TIME_MILLIS = -1;
    public static final boolean DEFAULT_TEST_ON_BORROW = false;
    public static final boolean DEFAULT_TEST_ON_RETURN = false;
    public static final boolean DEFAULT_TEST_WHILE_IDLE = false;
    public static final long DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS = -1;
    public static final byte DEFAULT_WHEN_EXHAUSTED_ACTION = 1;
    public static final byte WHEN_EXHAUSTED_BLOCK = 1;
    public static final byte WHEN_EXHAUSTED_FAIL = 0;
    public static final byte WHEN_EXHAUSTED_GROW = 2;
    private final LinkedList<Latch<T>> _allocationQueue;
    private CursorableLinkedList<GenericKeyedObjectPool.ObjectTimestampPair<T>>.Cursor _evictionCursor;
    private GenericObjectPool<T>.Evictor _evictor;
    private PoolableObjectFactory<T> _factory;
    private boolean _lifo;
    private int _maxActive;
    private int _maxIdle;
    private long _maxWait;
    private long _minEvictableIdleTimeMillis;
    private int _minIdle;
    private int _numActive;
    private int _numInternalProcessing;
    private int _numTestsPerEvictionRun;
    private CursorableLinkedList<GenericKeyedObjectPool.ObjectTimestampPair<T>> _pool;
    private long _softMinEvictableIdleTimeMillis;
    private volatile boolean _testOnBorrow;
    private volatile boolean _testOnReturn;
    private boolean _testWhileIdle;
    private long _timeBetweenEvictionRunsMillis;
    private byte _whenExhaustedAction;

    public static class Config {
        public boolean lifo = true;
        public int maxActive = 8;
        public int maxIdle = 8;
        public long maxWait = -1;
        public long minEvictableIdleTimeMillis = 1800000;
        public int minIdle = 0;
        public int numTestsPerEvictionRun = 3;
        public long softMinEvictableIdleTimeMillis = -1;
        public boolean testOnBorrow = false;
        public boolean testOnReturn = false;
        public boolean testWhileIdle = false;
        public long timeBetweenEvictionRunsMillis = -1;
        public byte whenExhaustedAction = 1;
    }

    public GenericObjectPool() {
        this((PoolableObjectFactory) null, 8, (byte) 1, -1, 8, 0, false, false, -1, 3, 1800000, false);
    }

    public GenericObjectPool(PoolableObjectFactory<T> factory) {
        this(factory, 8, (byte) 1, -1, 8, 0, false, false, -1, 3, 1800000, false);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public GenericObjectPool(org.apache.commons.pool.PoolableObjectFactory<T> r23, org.apache.commons.pool.impl.GenericObjectPool.Config r24) {
        /*
            r22 = this;
            r0 = r24
            r1 = r22
            r2 = r23
            int r3 = r0.maxActive
            byte r4 = r0.whenExhaustedAction
            long r5 = r0.maxWait
            int r7 = r0.maxIdle
            int r8 = r0.minIdle
            boolean r9 = r0.testOnBorrow
            boolean r10 = r0.testOnReturn
            long r11 = r0.timeBetweenEvictionRunsMillis
            int r13 = r0.numTestsPerEvictionRun
            long r14 = r0.minEvictableIdleTimeMillis
            r20 = r1
            boolean r1 = r0.testWhileIdle
            r16 = r1
            r21 = r2
            long r1 = r0.softMinEvictableIdleTimeMillis
            r17 = r1
            boolean r1 = r0.lifo
            r19 = r1
            r1 = r20
            r2 = r21
            r1.<init>(r2, r3, r4, r5, r7, r8, r9, r10, r11, r13, r14, r16, r17, r19)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.pool.impl.GenericObjectPool.<init>(org.apache.commons.pool.PoolableObjectFactory, org.apache.commons.pool.impl.GenericObjectPool$Config):void");
    }

    public GenericObjectPool(PoolableObjectFactory<T> factory, int maxActive) {
        this(factory, maxActive, (byte) 1, -1, 8, 0, false, false, -1, 3, 1800000, false);
    }

    public GenericObjectPool(PoolableObjectFactory<T> factory, int maxActive, byte whenExhaustedAction, long maxWait) {
        this(factory, maxActive, whenExhaustedAction, maxWait, 8, 0, false, false, -1, 3, 1800000, false);
    }

    public GenericObjectPool(PoolableObjectFactory<T> factory, int maxActive, byte whenExhaustedAction, long maxWait, boolean testOnBorrow, boolean testOnReturn) {
        this(factory, maxActive, whenExhaustedAction, maxWait, 8, 0, testOnBorrow, testOnReturn, -1, 3, 1800000, false);
    }

    public GenericObjectPool(PoolableObjectFactory<T> factory, int maxActive, byte whenExhaustedAction, long maxWait, int maxIdle) {
        this(factory, maxActive, whenExhaustedAction, maxWait, maxIdle, 0, false, false, -1, 3, 1800000, false);
    }

    public GenericObjectPool(PoolableObjectFactory<T> factory, int maxActive, byte whenExhaustedAction, long maxWait, int maxIdle, boolean testOnBorrow, boolean testOnReturn) {
        this(factory, maxActive, whenExhaustedAction, maxWait, maxIdle, 0, testOnBorrow, testOnReturn, -1, 3, 1800000, false);
    }

    public GenericObjectPool(PoolableObjectFactory<T> factory, int maxActive, byte whenExhaustedAction, long maxWait, int maxIdle, boolean testOnBorrow, boolean testOnReturn, long timeBetweenEvictionRunsMillis, int numTestsPerEvictionRun, long minEvictableIdleTimeMillis, boolean testWhileIdle) {
        this(factory, maxActive, whenExhaustedAction, maxWait, maxIdle, 0, testOnBorrow, testOnReturn, timeBetweenEvictionRunsMillis, numTestsPerEvictionRun, minEvictableIdleTimeMillis, testWhileIdle);
    }

    public GenericObjectPool(PoolableObjectFactory<T> factory, int maxActive, byte whenExhaustedAction, long maxWait, int maxIdle, int minIdle, boolean testOnBorrow, boolean testOnReturn, long timeBetweenEvictionRunsMillis, int numTestsPerEvictionRun, long minEvictableIdleTimeMillis, boolean testWhileIdle) {
        this(factory, maxActive, whenExhaustedAction, maxWait, maxIdle, minIdle, testOnBorrow, testOnReturn, timeBetweenEvictionRunsMillis, numTestsPerEvictionRun, minEvictableIdleTimeMillis, testWhileIdle, -1);
    }

    public GenericObjectPool(PoolableObjectFactory<T> factory, int maxActive, byte whenExhaustedAction, long maxWait, int maxIdle, int minIdle, boolean testOnBorrow, boolean testOnReturn, long timeBetweenEvictionRunsMillis, int numTestsPerEvictionRun, long minEvictableIdleTimeMillis, boolean testWhileIdle, long softMinEvictableIdleTimeMillis) {
        this(factory, maxActive, whenExhaustedAction, maxWait, maxIdle, minIdle, testOnBorrow, testOnReturn, timeBetweenEvictionRunsMillis, numTestsPerEvictionRun, minEvictableIdleTimeMillis, testWhileIdle, softMinEvictableIdleTimeMillis, true);
    }

    public GenericObjectPool(PoolableObjectFactory<T> factory, int maxActive, byte whenExhaustedAction, long maxWait, int maxIdle, int minIdle, boolean testOnBorrow, boolean testOnReturn, long timeBetweenEvictionRunsMillis, int numTestsPerEvictionRun, long minEvictableIdleTimeMillis, boolean testWhileIdle, long softMinEvictableIdleTimeMillis, boolean lifo) {
        byte b = whenExhaustedAction;
        this._maxIdle = 8;
        this._minIdle = 0;
        this._maxActive = 8;
        this._maxWait = -1;
        this._whenExhaustedAction = 1;
        this._testOnBorrow = false;
        this._testOnReturn = false;
        this._testWhileIdle = false;
        this._timeBetweenEvictionRunsMillis = -1;
        this._numTestsPerEvictionRun = 3;
        this._minEvictableIdleTimeMillis = 1800000;
        this._softMinEvictableIdleTimeMillis = -1;
        this._lifo = true;
        this._pool = null;
        this._evictionCursor = null;
        this._factory = null;
        this._numActive = 0;
        this._evictor = null;
        this._numInternalProcessing = 0;
        this._allocationQueue = new LinkedList<>();
        this._factory = factory;
        this._maxActive = maxActive;
        this._lifo = lifo;
        if (b == 0 || b == 1 || b == 2) {
            this._whenExhaustedAction = b;
            this._maxWait = maxWait;
            this._maxIdle = maxIdle;
            this._minIdle = minIdle;
            this._testOnBorrow = testOnBorrow;
            this._testOnReturn = testOnReturn;
            this._timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
            this._numTestsPerEvictionRun = numTestsPerEvictionRun;
            this._minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
            this._softMinEvictableIdleTimeMillis = softMinEvictableIdleTimeMillis;
            this._testWhileIdle = testWhileIdle;
            this._pool = new CursorableLinkedList<>();
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

    public void setMinIdle(int minIdle) {
        synchronized (this) {
            this._minIdle = minIdle;
        }
        allocate();
    }

    public synchronized int getMinIdle() {
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

    public synchronized long getSoftMinEvictableIdleTimeMillis() {
        return this._softMinEvictableIdleTimeMillis;
    }

    public synchronized void setSoftMinEvictableIdleTimeMillis(long softMinEvictableIdleTimeMillis) {
        this._softMinEvictableIdleTimeMillis = softMinEvictableIdleTimeMillis;
    }

    public synchronized boolean getTestWhileIdle() {
        return this._testWhileIdle;
    }

    public synchronized void setTestWhileIdle(boolean testWhileIdle) {
        this._testWhileIdle = testWhileIdle;
    }

    public synchronized boolean getLifo() {
        return this._lifo;
    }

    public synchronized void setLifo(boolean lifo) {
        this._lifo = lifo;
    }

    public void setConfig(Config conf) {
        synchronized (this) {
            setMaxIdle(conf.maxIdle);
            setMinIdle(conf.minIdle);
            setMaxActive(conf.maxActive);
            setMaxWait(conf.maxWait);
            setWhenExhaustedAction(conf.whenExhaustedAction);
            setTestOnBorrow(conf.testOnBorrow);
            setTestOnReturn(conf.testOnReturn);
            setTestWhileIdle(conf.testWhileIdle);
            setNumTestsPerEvictionRun(conf.numTestsPerEvictionRun);
            setMinEvictableIdleTimeMillis(conf.minEvictableIdleTimeMillis);
            setTimeBetweenEvictionRunsMillis(conf.timeBetweenEvictionRunsMillis);
            setSoftMinEvictableIdleTimeMillis(conf.softMinEvictableIdleTimeMillis);
            setLifo(conf.lifo);
        }
        allocate();
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
    public T borrowObject() throws java.lang.Exception {
        /*
            r16 = this;
            r1 = r16
            long r2 = java.lang.System.currentTimeMillis()
            org.apache.commons.pool.impl.GenericObjectPool$Latch r0 = new org.apache.commons.pool.impl.GenericObjectPool$Latch
            r4 = 0
            r0.<init>()
            r4 = r0
            monitor-enter(r16)
            r5 = 0
            r6 = 0
            byte r0 = r1._whenExhaustedAction     // Catch:{ all -> 0x0211 }
            r8 = r0
            long r9 = r1._maxWait     // Catch:{ all -> 0x020f }
            java.util.LinkedList<org.apache.commons.pool.impl.GenericObjectPool$Latch<T>> r0 = r1._allocationQueue     // Catch:{ all -> 0x0216 }
            r0.add(r4)     // Catch:{ all -> 0x0216 }
            monitor-exit(r16)     // Catch:{ all -> 0x0216 }
            r16.allocate()
        L_0x001f:
            monitor-enter(r16)
            r16.assertOpen()     // Catch:{ all -> 0x020c }
            monitor-exit(r16)     // Catch:{ all -> 0x020c }
            org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectTimestampPair r0 = r4.getPair()
            r11 = 1
            if (r0 != 0) goto L_0x014d
            boolean r0 = r4.mayCreate()
            if (r0 == 0) goto L_0x0033
            goto L_0x014d
        L_0x0033:
            if (r8 == 0) goto L_0x012c
            if (r8 == r11) goto L_0x0073
            r0 = 2
            if (r8 != r0) goto L_0x0057
            monitor-enter(r16)
            org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectTimestampPair r0 = r4.getPair()     // Catch:{ all -> 0x0054 }
            if (r0 != 0) goto L_0x0051
            boolean r0 = r4.mayCreate()     // Catch:{ all -> 0x0054 }
            if (r0 != 0) goto L_0x0051
            java.util.LinkedList<org.apache.commons.pool.impl.GenericObjectPool$Latch<T>> r0 = r1._allocationQueue     // Catch:{ all -> 0x0054 }
            r0.remove(r4)     // Catch:{ all -> 0x0054 }
            int r0 = r1._numInternalProcessing     // Catch:{ all -> 0x0054 }
            int r0 = r0 + r11
            r1._numInternalProcessing = r0     // Catch:{ all -> 0x0054 }
        L_0x0051:
            monitor-exit(r16)     // Catch:{ all -> 0x0054 }
            goto L_0x014d
        L_0x0054:
            r0 = move-exception
            monitor-exit(r16)     // Catch:{ all -> 0x0054 }
            throw r0
        L_0x0057:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "WhenExhaustedAction property "
            r5.append(r6)
            r5.append(r8)
            java.lang.String r6 = " not recognized."
            r5.append(r6)
            java.lang.String r5 = r5.toString()
            r0.<init>(r5)
            throw r0
        L_0x0073:
            monitor-enter(r4)     // Catch:{ InterruptedException -> 0x00dc }
            org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectTimestampPair r0 = r4.getPair()     // Catch:{ all -> 0x00d9 }
            if (r0 != 0) goto L_0x00d6
            boolean r0 = r4.mayCreate()     // Catch:{ all -> 0x00d9 }
            if (r0 != 0) goto L_0x00d6
            int r0 = (r9 > r6 ? 1 : (r9 == r6 ? 0 : -1))
            if (r0 > 0) goto L_0x0088
            r4.wait()     // Catch:{ all -> 0x00d9 }
            goto L_0x0097
        L_0x0088:
            long r12 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x00d9 }
            long r12 = r12 - r2
            long r14 = r9 - r12
            int r0 = (r14 > r6 ? 1 : (r14 == r6 ? 0 : -1))
            if (r0 <= 0) goto L_0x0096
            r4.wait(r14)     // Catch:{ all -> 0x00d9 }
        L_0x0096:
        L_0x0097:
            monitor-exit(r4)     // Catch:{ all -> 0x00d9 }
            boolean r0 = r16.isClosed()     // Catch:{ InterruptedException -> 0x00dc }
            if (r0 == r11) goto L_0x00ce
            int r0 = (r9 > r6 ? 1 : (r9 == r6 ? 0 : -1))
            if (r0 <= 0) goto L_0x001f
            long r12 = java.lang.System.currentTimeMillis()
            long r12 = r12 - r2
            int r0 = (r12 > r9 ? 1 : (r12 == r9 ? 0 : -1))
            if (r0 < 0) goto L_0x001f
            monitor-enter(r16)
            org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectTimestampPair r0 = r4.getPair()     // Catch:{ all -> 0x00cb }
            if (r0 != 0) goto L_0x00c8
            boolean r0 = r4.mayCreate()     // Catch:{ all -> 0x00cb }
            if (r0 == 0) goto L_0x00ba
            goto L_0x00c8
        L_0x00ba:
            java.util.LinkedList<org.apache.commons.pool.impl.GenericObjectPool$Latch<T>> r0 = r1._allocationQueue     // Catch:{ all -> 0x00cb }
            r0.remove(r4)     // Catch:{ all -> 0x00cb }
            monitor-exit(r16)     // Catch:{ all -> 0x00cb }
            java.util.NoSuchElementException r0 = new java.util.NoSuchElementException
            java.lang.String r5 = "Timeout waiting for idle object"
            r0.<init>(r5)
            throw r0
        L_0x00c8:
            monitor-exit(r16)     // Catch:{ all -> 0x00cb }
            goto L_0x014d
        L_0x00cb:
            r0 = move-exception
            monitor-exit(r16)     // Catch:{ all -> 0x00cb }
            throw r0
        L_0x00ce:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException     // Catch:{ InterruptedException -> 0x00dc }
            java.lang.String r5 = "Pool closed"
            r0.<init>(r5)     // Catch:{ InterruptedException -> 0x00dc }
            throw r0     // Catch:{ InterruptedException -> 0x00dc }
        L_0x00d6:
            monitor-exit(r4)     // Catch:{ all -> 0x00d9 }
            goto L_0x014d
        L_0x00d9:
            r0 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x00d9 }
            throw r0     // Catch:{ InterruptedException -> 0x00dc }
        L_0x00dc:
            r0 = move-exception
            r12 = r0
            r5 = 0
            monitor-enter(r16)
            org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectTimestampPair r0 = r4.getPair()     // Catch:{ all -> 0x0129 }
            if (r0 != 0) goto L_0x00f2
            boolean r0 = r4.mayCreate()     // Catch:{ all -> 0x0129 }
            if (r0 != 0) goto L_0x00f2
            java.util.LinkedList<org.apache.commons.pool.impl.GenericObjectPool$Latch<T>> r0 = r1._allocationQueue     // Catch:{ all -> 0x0129 }
            r0.remove(r4)     // Catch:{ all -> 0x0129 }
            goto L_0x011b
        L_0x00f2:
            org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectTimestampPair r0 = r4.getPair()     // Catch:{ all -> 0x0129 }
            if (r0 != 0) goto L_0x0106
            boolean r0 = r4.mayCreate()     // Catch:{ all -> 0x0129 }
            if (r0 == 0) goto L_0x0106
            int r0 = r1._numInternalProcessing     // Catch:{ all -> 0x0129 }
            int r0 = r0 - r11
            r1._numInternalProcessing = r0     // Catch:{ all -> 0x0129 }
            r0 = 1
            r5 = r0
            goto L_0x011b
        L_0x0106:
            int r0 = r1._numInternalProcessing     // Catch:{ all -> 0x0129 }
            int r0 = r0 - r11
            r1._numInternalProcessing = r0     // Catch:{ all -> 0x0129 }
            int r0 = r1._numActive     // Catch:{ all -> 0x0129 }
            int r0 = r0 + r11
            r1._numActive = r0     // Catch:{ all -> 0x0129 }
            org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectTimestampPair r0 = r4.getPair()     // Catch:{ all -> 0x0129 }
            java.lang.Object r0 = r0.getValue()     // Catch:{ all -> 0x0129 }
            r1.returnObject(r0)     // Catch:{ all -> 0x0129 }
        L_0x011b:
            monitor-exit(r16)     // Catch:{ all -> 0x0129 }
            if (r5 == 0) goto L_0x0121
            r16.allocate()
        L_0x0121:
            java.lang.Thread r0 = java.lang.Thread.currentThread()
            r0.interrupt()
            throw r12
        L_0x0129:
            r0 = move-exception
            monitor-exit(r16)     // Catch:{ all -> 0x0129 }
            throw r0
        L_0x012c:
            monitor-enter(r16)
            org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectTimestampPair r0 = r4.getPair()     // Catch:{ all -> 0x014a }
            if (r0 != 0) goto L_0x0148
            boolean r0 = r4.mayCreate()     // Catch:{ all -> 0x014a }
            if (r0 == 0) goto L_0x013a
            goto L_0x0148
        L_0x013a:
            java.util.LinkedList<org.apache.commons.pool.impl.GenericObjectPool$Latch<T>> r0 = r1._allocationQueue     // Catch:{ all -> 0x014a }
            r0.remove(r4)     // Catch:{ all -> 0x014a }
            monitor-exit(r16)     // Catch:{ all -> 0x014a }
            java.util.NoSuchElementException r0 = new java.util.NoSuchElementException
            java.lang.String r5 = "Pool exhausted"
            r0.<init>(r5)
            throw r0
        L_0x0148:
            monitor-exit(r16)     // Catch:{ all -> 0x014a }
            goto L_0x014d
        L_0x014a:
            r0 = move-exception
            monitor-exit(r16)     // Catch:{ all -> 0x014a }
            throw r0
        L_0x014d:
            r12 = 0
            org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectTimestampPair r0 = r4.getPair()
            if (r0 != 0) goto L_0x0185
            org.apache.commons.pool.PoolableObjectFactory<T> r0 = r1._factory     // Catch:{ all -> 0x0173 }
            java.lang.Object r0 = r0.makeObject()     // Catch:{ all -> 0x0173 }
            org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectTimestampPair r13 = new org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectTimestampPair     // Catch:{ all -> 0x0173 }
            r13.<init>(r0)     // Catch:{ all -> 0x0173 }
            r4.setPair(r13)     // Catch:{ all -> 0x0173 }
            r12 = 1
            if (r12 != 0) goto L_0x0185
            monitor-enter(r16)
            int r0 = r1._numInternalProcessing     // Catch:{ all -> 0x0170 }
            int r0 = r0 - r11
            r1._numInternalProcessing = r0     // Catch:{ all -> 0x0170 }
            monitor-exit(r16)     // Catch:{ all -> 0x0170 }
            r16.allocate()
            goto L_0x0185
        L_0x0170:
            r0 = move-exception
            monitor-exit(r16)     // Catch:{ all -> 0x0170 }
            throw r0
        L_0x0173:
            r0 = move-exception
            if (r12 != 0) goto L_0x0184
            monitor-enter(r16)
            int r5 = r1._numInternalProcessing     // Catch:{ all -> 0x0181 }
            int r5 = r5 - r11
            r1._numInternalProcessing = r5     // Catch:{ all -> 0x0181 }
            monitor-exit(r16)     // Catch:{ all -> 0x0181 }
            r16.allocate()
            goto L_0x0184
        L_0x0181:
            r0 = move-exception
            monitor-exit(r16)     // Catch:{ all -> 0x0181 }
            throw r0
        L_0x0184:
            throw r0
        L_0x0185:
            org.apache.commons.pool.PoolableObjectFactory<T> r0 = r1._factory     // Catch:{ all -> 0x01c1 }
            org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectTimestampPair r13 = r4.getPair()     // Catch:{ all -> 0x01c1 }
            T r13 = r13.value     // Catch:{ all -> 0x01c1 }
            r0.activateObject(r13)     // Catch:{ all -> 0x01c1 }
            boolean r0 = r1._testOnBorrow     // Catch:{ all -> 0x01c1 }
            if (r0 == 0) goto L_0x01ab
            org.apache.commons.pool.PoolableObjectFactory<T> r0 = r1._factory     // Catch:{ all -> 0x01c1 }
            org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectTimestampPair r13 = r4.getPair()     // Catch:{ all -> 0x01c1 }
            T r13 = r13.value     // Catch:{ all -> 0x01c1 }
            boolean r0 = r0.validateObject(r13)     // Catch:{ all -> 0x01c1 }
            if (r0 == 0) goto L_0x01a3
            goto L_0x01ab
        L_0x01a3:
            java.lang.Exception r0 = new java.lang.Exception     // Catch:{ all -> 0x01c1 }
            java.lang.String r13 = "ValidateObject failed"
            r0.<init>(r13)     // Catch:{ all -> 0x01c1 }
            throw r0     // Catch:{ all -> 0x01c1 }
        L_0x01ab:
            monitor-enter(r16)     // Catch:{ all -> 0x01c1 }
            int r0 = r1._numInternalProcessing     // Catch:{ all -> 0x01be }
            int r0 = r0 - r11
            r1._numInternalProcessing = r0     // Catch:{ all -> 0x01be }
            int r0 = r1._numActive     // Catch:{ all -> 0x01be }
            int r0 = r0 + r11
            r1._numActive = r0     // Catch:{ all -> 0x01be }
            monitor-exit(r16)     // Catch:{ all -> 0x01be }
            org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectTimestampPair r0 = r4.getPair()     // Catch:{ all -> 0x01c1 }
            T r0 = r0.value     // Catch:{ all -> 0x01c1 }
            return r0
        L_0x01be:
            r0 = move-exception
            monitor-exit(r16)     // Catch:{ all -> 0x01be }
            throw r0     // Catch:{ all -> 0x01c1 }
        L_0x01c1:
            r0 = move-exception
            r13 = r0
            org.apache.commons.pool.PoolUtils.checkRethrow(r13)
            org.apache.commons.pool.PoolableObjectFactory<T> r0 = r1._factory     // Catch:{ all -> 0x01d2 }
            org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectTimestampPair r14 = r4.getPair()     // Catch:{ all -> 0x01d2 }
            T r14 = r14.value     // Catch:{ all -> 0x01d2 }
            r0.destroyObject(r14)     // Catch:{ all -> 0x01d2 }
            goto L_0x01d6
        L_0x01d2:
            r0 = move-exception
            org.apache.commons.pool.PoolUtils.checkRethrow(r0)
        L_0x01d6:
            monitor-enter(r16)
            int r0 = r1._numInternalProcessing     // Catch:{ all -> 0x0209 }
            int r0 = r0 - r11
            r1._numInternalProcessing = r0     // Catch:{ all -> 0x0209 }
            if (r12 != 0) goto L_0x01e6
            r4.reset()     // Catch:{ all -> 0x0209 }
            java.util.LinkedList<org.apache.commons.pool.impl.GenericObjectPool$Latch<T>> r0 = r1._allocationQueue     // Catch:{ all -> 0x0209 }
            r0.add(r5, r4)     // Catch:{ all -> 0x0209 }
        L_0x01e6:
            monitor-exit(r16)     // Catch:{ all -> 0x0209 }
            r16.allocate()
            if (r12 != 0) goto L_0x01ee
            goto L_0x001f
        L_0x01ee:
            java.util.NoSuchElementException r0 = new java.util.NoSuchElementException
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "Could not create a validated object, cause: "
            r5.append(r6)
            java.lang.String r6 = r13.getMessage()
            r5.append(r6)
            java.lang.String r5 = r5.toString()
            r0.<init>(r5)
            throw r0
        L_0x0209:
            r0 = move-exception
            monitor-exit(r16)     // Catch:{ all -> 0x0209 }
            throw r0
        L_0x020c:
            r0 = move-exception
            monitor-exit(r16)     // Catch:{ all -> 0x020c }
            throw r0
        L_0x020f:
            r0 = move-exception
            goto L_0x0213
        L_0x0211:
            r0 = move-exception
            r8 = r5
        L_0x0213:
            r9 = r6
        L_0x0214:
            monitor-exit(r16)     // Catch:{ all -> 0x0216 }
            throw r0
        L_0x0216:
            r0 = move-exception
            goto L_0x0214
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.pool.impl.GenericObjectPool.borrowObject():java.lang.Object");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:0x003b, code lost:
        r1 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x006c, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized void allocate() {
        /*
            r3 = this;
            monitor-enter(r3)
            boolean r0 = r3.isClosed()     // Catch:{ all -> 0x006d }
            if (r0 == 0) goto L_0x0009
            monitor-exit(r3)
            return
        L_0x0009:
            org.apache.commons.pool.impl.CursorableLinkedList<org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectTimestampPair<T>> r0 = r3._pool     // Catch:{ all -> 0x006d }
            boolean r0 = r0.isEmpty()     // Catch:{ all -> 0x006d }
            r1 = 1
            if (r0 != 0) goto L_0x003d
            java.util.LinkedList<org.apache.commons.pool.impl.GenericObjectPool$Latch<T>> r0 = r3._allocationQueue     // Catch:{ all -> 0x006d }
            boolean r0 = r0.isEmpty()     // Catch:{ all -> 0x006d }
            if (r0 != 0) goto L_0x003d
            java.util.LinkedList<org.apache.commons.pool.impl.GenericObjectPool$Latch<T>> r0 = r3._allocationQueue     // Catch:{ all -> 0x006d }
            java.lang.Object r0 = r0.removeFirst()     // Catch:{ all -> 0x006d }
            org.apache.commons.pool.impl.GenericObjectPool$Latch r0 = (org.apache.commons.pool.impl.GenericObjectPool.Latch) r0     // Catch:{ all -> 0x006d }
            org.apache.commons.pool.impl.CursorableLinkedList<org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectTimestampPair<T>> r2 = r3._pool     // Catch:{ all -> 0x006d }
            java.lang.Object r2 = r2.removeFirst()     // Catch:{ all -> 0x006d }
            org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectTimestampPair r2 = (org.apache.commons.pool.impl.GenericKeyedObjectPool.ObjectTimestampPair) r2     // Catch:{ all -> 0x006d }
            r0.setPair(r2)     // Catch:{ all -> 0x006d }
            int r2 = r3._numInternalProcessing     // Catch:{ all -> 0x006d }
            int r2 = r2 + r1
            r3._numInternalProcessing = r2     // Catch:{ all -> 0x006d }
            monitor-enter(r0)     // Catch:{ all -> 0x006d }
            r0.notify()     // Catch:{ all -> 0x0038 }
            monitor-exit(r0)     // Catch:{ all -> 0x0038 }
            goto L_0x0009
        L_0x0038:
            r1 = move-exception
        L_0x0039:
            monitor-exit(r0)     // Catch:{ all -> 0x003b }
            throw r1     // Catch:{ all -> 0x006d }
        L_0x003b:
            r1 = move-exception
            goto L_0x0039
        L_0x003d:
            java.util.LinkedList<org.apache.commons.pool.impl.GenericObjectPool$Latch<T>> r0 = r3._allocationQueue     // Catch:{ all -> 0x006d }
            boolean r0 = r0.isEmpty()     // Catch:{ all -> 0x006d }
            if (r0 != 0) goto L_0x006b
            int r0 = r3._maxActive     // Catch:{ all -> 0x006d }
            if (r0 < 0) goto L_0x0052
            int r0 = r3._numActive     // Catch:{ all -> 0x006d }
            int r2 = r3._numInternalProcessing     // Catch:{ all -> 0x006d }
            int r0 = r0 + r2
            int r2 = r3._maxActive     // Catch:{ all -> 0x006d }
            if (r0 >= r2) goto L_0x006b
        L_0x0052:
            java.util.LinkedList<org.apache.commons.pool.impl.GenericObjectPool$Latch<T>> r0 = r3._allocationQueue     // Catch:{ all -> 0x006d }
            java.lang.Object r0 = r0.removeFirst()     // Catch:{ all -> 0x006d }
            org.apache.commons.pool.impl.GenericObjectPool$Latch r0 = (org.apache.commons.pool.impl.GenericObjectPool.Latch) r0     // Catch:{ all -> 0x006d }
            r0.setMayCreate(r1)     // Catch:{ all -> 0x006d }
            int r2 = r3._numInternalProcessing     // Catch:{ all -> 0x006d }
            int r2 = r2 + r1
            r3._numInternalProcessing = r2     // Catch:{ all -> 0x006d }
            monitor-enter(r0)     // Catch:{ all -> 0x006d }
            r0.notify()     // Catch:{ all -> 0x0068 }
            monitor-exit(r0)     // Catch:{ all -> 0x0068 }
            goto L_0x003d
        L_0x0068:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0068 }
            throw r1     // Catch:{ all -> 0x006d }
        L_0x006b:
            monitor-exit(r3)
            return
        L_0x006d:
            r0 = move-exception
            monitor-exit(r3)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.pool.impl.GenericObjectPool.allocate():void");
    }

    public void invalidateObject(T obj) throws Exception {
        try {
            if (this._factory != null) {
                this._factory.destroyObject(obj);
            }
            synchronized (this) {
                this._numActive--;
            }
            allocate();
        } catch (Throwable th) {
            synchronized (this) {
                this._numActive--;
                allocate();
                throw th;
            }
        }
    }

    public void clear() {
        List<GenericKeyedObjectPool.ObjectTimestampPair<T>> toDestroy = new ArrayList<>();
        synchronized (this) {
            toDestroy.addAll(this._pool);
            this._numInternalProcessing += this._pool._size;
            this._pool.clear();
        }
        destroy(toDestroy, this._factory);
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
        	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:561)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processExcHandler(RegionMaker.java:1043)
        	at jadx.core.dex.visitors.regions.RegionMaker.processTryCatchBlocks(RegionMaker.java:975)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:52)
        */
    private void destroy(java.util.Collection<org.apache.commons.pool.impl.GenericKeyedObjectPool.ObjectTimestampPair<T>> r4, org.apache.commons.pool.PoolableObjectFactory<T> r5) {
        /*
            r3 = this;
            java.util.Iterator r0 = r4.iterator()
        L_0x0004:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x0042
            java.lang.Object r1 = r0.next()     // Catch:{ Exception -> 0x0031, all -> 0x0021 }
            org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectTimestampPair r1 = (org.apache.commons.pool.impl.GenericKeyedObjectPool.ObjectTimestampPair) r1     // Catch:{ Exception -> 0x0031, all -> 0x0021 }
            T r1 = r1.value     // Catch:{ Exception -> 0x0031, all -> 0x0021 }
            r5.destroyObject(r1)     // Catch:{ Exception -> 0x0031, all -> 0x0021 }
            monitor-enter(r3)
            int r1 = r3._numInternalProcessing     // Catch:{ all -> 0x001e }
            int r1 = r1 + -1
            r3._numInternalProcessing = r1     // Catch:{ all -> 0x001e }
            monitor-exit(r3)     // Catch:{ all -> 0x001e }
            goto L_0x003a
        L_0x001e:
            r1 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x001e }
            throw r1
        L_0x0021:
            r1 = move-exception
            monitor-enter(r3)
            int r2 = r3._numInternalProcessing     // Catch:{ all -> 0x002e }
            int r2 = r2 + -1
            r3._numInternalProcessing = r2     // Catch:{ all -> 0x002e }
            monitor-exit(r3)     // Catch:{ all -> 0x002e }
            r3.allocate()
            throw r1
        L_0x002e:
            r1 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x002e }
            throw r1
        L_0x0031:
            r1 = move-exception
            monitor-enter(r3)
            int r1 = r3._numInternalProcessing     // Catch:{ all -> 0x003f }
            int r1 = r1 + -1
            r3._numInternalProcessing = r1     // Catch:{ all -> 0x003f }
            monitor-exit(r3)     // Catch:{ all -> 0x003f }
        L_0x003a:
            r3.allocate()
            goto L_0x0004
        L_0x003f:
            r1 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x003f }
            throw r1
        L_0x0042:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.pool.impl.GenericObjectPool.destroy(java.util.Collection, org.apache.commons.pool.PoolableObjectFactory):void");
    }

    public synchronized int getNumActive() {
        return this._numActive;
    }

    public synchronized int getNumIdle() {
        return this._pool.size();
    }

    public void returnObject(T obj) throws Exception {
        try {
            addObjectToPool(obj, true);
        } catch (Exception e) {
            PoolableObjectFactory<T> poolableObjectFactory = this._factory;
            if (poolableObjectFactory != null) {
                try {
                    poolableObjectFactory.destroyObject(obj);
                } catch (Exception e2) {
                }
                synchronized (this) {
                    this._numActive--;
                    allocate();
                }
            }
        }
    }

    private void addObjectToPool(T obj, boolean decrementNumActive) throws Exception {
        boolean success = true;
        if (!this._testOnReturn || this._factory.validateObject(obj)) {
            this._factory.passivateObject(obj);
        } else {
            success = false;
        }
        boolean shouldDestroy = !success;
        boolean doAllocate = false;
        synchronized (this) {
            if (isClosed()) {
                shouldDestroy = true;
            } else if (this._maxIdle >= 0 && this._pool.size() >= this._maxIdle) {
                shouldDestroy = true;
            } else if (success) {
                if (this._lifo) {
                    this._pool.addFirst(new GenericKeyedObjectPool.ObjectTimestampPair(obj));
                } else {
                    this._pool.addLast(new GenericKeyedObjectPool.ObjectTimestampPair(obj));
                }
                if (decrementNumActive) {
                    this._numActive--;
                }
                doAllocate = true;
            }
        }
        if (doAllocate) {
            allocate();
        }
        if (shouldDestroy) {
            try {
                this._factory.destroyObject(obj);
            } catch (Exception e) {
            }
            if (decrementNumActive) {
                synchronized (this) {
                    this._numActive--;
                }
                allocate();
            }
        }
    }

    public void close() throws Exception {
        super.close();
        synchronized (this) {
            clear();
            startEvictor(-1);
            while (this._allocationQueue.size() > 0) {
                Latch<T> l = this._allocationQueue.removeFirst();
                synchronized (l) {
                    l.notify();
                }
            }
        }
    }

    @Deprecated
    public void setFactory(PoolableObjectFactory<T> factory) throws IllegalStateException {
        List<GenericKeyedObjectPool.ObjectTimestampPair<T>> toDestroy = new ArrayList<>();
        PoolableObjectFactory<T> oldFactory = this._factory;
        synchronized (this) {
            assertOpen();
            if (getNumActive() <= 0) {
                toDestroy.addAll(this._pool);
                this._numInternalProcessing += this._pool._size;
                this._pool.clear();
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
        	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:561)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processHandlersOutBlocks(RegionMaker.java:1008)
        	at jadx.core.dex.visitors.regions.RegionMaker.processTryCatchBlocks(RegionMaker.java:978)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:52)
        */
    public void evict() throws java.lang.Exception {
        /*
            r11 = this;
            r11.assertOpen()
            monitor-enter(r11)
            org.apache.commons.pool.impl.CursorableLinkedList<org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectTimestampPair<T>> r0 = r11._pool     // Catch:{ all -> 0x011d }
            boolean r0 = r0.isEmpty()     // Catch:{ all -> 0x011d }
            if (r0 == 0) goto L_0x000e
            monitor-exit(r11)     // Catch:{ all -> 0x011d }
            return
        L_0x000e:
            org.apache.commons.pool.impl.CursorableLinkedList<org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectTimestampPair<T>>$Cursor r0 = r11._evictionCursor     // Catch:{ all -> 0x011d }
            r1 = 0
            if (r0 != 0) goto L_0x0027
            org.apache.commons.pool.impl.CursorableLinkedList<org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectTimestampPair<T>> r0 = r11._pool     // Catch:{ all -> 0x011d }
            boolean r2 = r11._lifo     // Catch:{ all -> 0x011d }
            if (r2 == 0) goto L_0x0020
            org.apache.commons.pool.impl.CursorableLinkedList<org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectTimestampPair<T>> r2 = r11._pool     // Catch:{ all -> 0x011d }
            int r2 = r2.size()     // Catch:{ all -> 0x011d }
            goto L_0x0021
        L_0x0020:
            r2 = r1
        L_0x0021:
            org.apache.commons.pool.impl.CursorableLinkedList$Cursor r0 = r0.cursor(r2)     // Catch:{ all -> 0x011d }
            r11._evictionCursor = r0     // Catch:{ all -> 0x011d }
        L_0x0027:
            monitor-exit(r11)     // Catch:{ all -> 0x011d }
            r0 = 0
            int r2 = r11.getNumTests()
            r3 = 0
        L_0x002e:
            if (r0 >= r2) goto L_0x0119
            monitor-enter(r11)
            boolean r4 = r11._lifo     // Catch:{ all -> 0x0114 }
            if (r4 == 0) goto L_0x003d
            org.apache.commons.pool.impl.CursorableLinkedList<org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectTimestampPair<T>>$Cursor r4 = r11._evictionCursor     // Catch:{ all -> 0x0114 }
            boolean r4 = r4.hasPrevious()     // Catch:{ all -> 0x0114 }
            if (r4 == 0) goto L_0x0049
        L_0x003d:
            boolean r4 = r11._lifo     // Catch:{ all -> 0x0114 }
            if (r4 != 0) goto L_0x0062
            org.apache.commons.pool.impl.CursorableLinkedList<org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectTimestampPair<T>>$Cursor r4 = r11._evictionCursor     // Catch:{ all -> 0x0114 }
            boolean r4 = r4.hasNext()     // Catch:{ all -> 0x0114 }
            if (r4 != 0) goto L_0x0062
        L_0x0049:
            org.apache.commons.pool.impl.CursorableLinkedList<org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectTimestampPair<T>>$Cursor r4 = r11._evictionCursor     // Catch:{ all -> 0x0114 }
            r4.close()     // Catch:{ all -> 0x0114 }
            org.apache.commons.pool.impl.CursorableLinkedList<org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectTimestampPair<T>> r4 = r11._pool     // Catch:{ all -> 0x0114 }
            boolean r5 = r11._lifo     // Catch:{ all -> 0x0114 }
            if (r5 == 0) goto L_0x005b
            org.apache.commons.pool.impl.CursorableLinkedList<org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectTimestampPair<T>> r5 = r11._pool     // Catch:{ all -> 0x0114 }
            int r5 = r5.size()     // Catch:{ all -> 0x0114 }
            goto L_0x005c
        L_0x005b:
            r5 = r1
        L_0x005c:
            org.apache.commons.pool.impl.CursorableLinkedList$Cursor r4 = r4.cursor(r5)     // Catch:{ all -> 0x0114 }
            r11._evictionCursor = r4     // Catch:{ all -> 0x0114 }
        L_0x0062:
            boolean r4 = r11._lifo     // Catch:{ all -> 0x0114 }
            if (r4 == 0) goto L_0x006d
            org.apache.commons.pool.impl.CursorableLinkedList<org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectTimestampPair<T>>$Cursor r4 = r11._evictionCursor     // Catch:{ all -> 0x0114 }
            java.lang.Object r4 = r4.previous()     // Catch:{ all -> 0x0114 }
            goto L_0x0073
        L_0x006d:
            org.apache.commons.pool.impl.CursorableLinkedList<org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectTimestampPair<T>>$Cursor r4 = r11._evictionCursor     // Catch:{ all -> 0x0114 }
            java.lang.Object r4 = r4.next()     // Catch:{ all -> 0x0114 }
        L_0x0073:
            org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectTimestampPair r4 = (org.apache.commons.pool.impl.GenericKeyedObjectPool.ObjectTimestampPair) r4     // Catch:{ all -> 0x0114 }
            r3 = r4
            org.apache.commons.pool.impl.CursorableLinkedList<org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectTimestampPair<T>>$Cursor r4 = r11._evictionCursor     // Catch:{ all -> 0x0117 }
            r4.remove()     // Catch:{ all -> 0x0117 }
            int r4 = r11._numInternalProcessing     // Catch:{ all -> 0x0117 }
            int r4 = r4 + 1
            r11._numInternalProcessing = r4     // Catch:{ all -> 0x0117 }
            monitor-exit(r11)     // Catch:{ all -> 0x0117 }
            r4 = 0
            long r5 = java.lang.System.currentTimeMillis()
            long r7 = r3.tstamp
            long r5 = r5 - r7
            long r7 = r11.getMinEvictableIdleTimeMillis()
            r9 = 0
            int r7 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
            if (r7 <= 0) goto L_0x009e
            long r7 = r11.getMinEvictableIdleTimeMillis()
            int r7 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r7 <= 0) goto L_0x009e
            r4 = 1
            goto L_0x00bb
        L_0x009e:
            long r7 = r11.getSoftMinEvictableIdleTimeMillis()
            int r7 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
            if (r7 <= 0) goto L_0x00bb
            long r7 = r11.getSoftMinEvictableIdleTimeMillis()
            int r7 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r7 <= 0) goto L_0x00bb
            int r7 = r11.getNumIdle()
            int r7 = r7 + 1
            int r8 = r11.getMinIdle()
            if (r7 <= r8) goto L_0x00bb
            r4 = 1
        L_0x00bb:
            boolean r7 = r11.getTestWhileIdle()
            if (r7 == 0) goto L_0x00e7
            if (r4 != 0) goto L_0x00e7
            r7 = 0
            org.apache.commons.pool.PoolableObjectFactory<T> r8 = r11._factory     // Catch:{ Exception -> 0x00cd }
            T r9 = r3.value     // Catch:{ Exception -> 0x00cd }
            r8.activateObject(r9)     // Catch:{ Exception -> 0x00cd }
            r7 = 1
            goto L_0x00cf
        L_0x00cd:
            r8 = move-exception
            r4 = 1
        L_0x00cf:
            if (r7 == 0) goto L_0x00e7
            org.apache.commons.pool.PoolableObjectFactory<T> r8 = r11._factory
            T r9 = r3.value
            boolean r8 = r8.validateObject(r9)
            if (r8 != 0) goto L_0x00dd
            r4 = 1
            goto L_0x00e7
        L_0x00dd:
            org.apache.commons.pool.PoolableObjectFactory<T> r8 = r11._factory     // Catch:{ Exception -> 0x00e5 }
            T r9 = r3.value     // Catch:{ Exception -> 0x00e5 }
            r8.passivateObject(r9)     // Catch:{ Exception -> 0x00e5 }
            goto L_0x00e7
        L_0x00e5:
            r8 = move-exception
            r4 = 1
        L_0x00e7:
            if (r4 == 0) goto L_0x00f2
            org.apache.commons.pool.PoolableObjectFactory<T> r7 = r11._factory     // Catch:{ Exception -> 0x00f1 }
            T r8 = r3.value     // Catch:{ Exception -> 0x00f1 }
            r7.destroyObject(r8)     // Catch:{ Exception -> 0x00f1 }
            goto L_0x00f2
        L_0x00f1:
            r7 = move-exception
        L_0x00f2:
            monitor-enter(r11)
            if (r4 != 0) goto L_0x0106
            org.apache.commons.pool.impl.CursorableLinkedList<org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectTimestampPair<T>>$Cursor r7 = r11._evictionCursor     // Catch:{ all -> 0x0104 }
            r7.add(r3)     // Catch:{ all -> 0x0104 }
            boolean r7 = r11._lifo     // Catch:{ all -> 0x0104 }
            if (r7 == 0) goto L_0x0106
            org.apache.commons.pool.impl.CursorableLinkedList<org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectTimestampPair<T>>$Cursor r7 = r11._evictionCursor     // Catch:{ all -> 0x0104 }
            r7.previous()     // Catch:{ all -> 0x0104 }
            goto L_0x0106
        L_0x0104:
            r1 = move-exception
            goto L_0x0112
        L_0x0106:
            int r7 = r11._numInternalProcessing     // Catch:{ all -> 0x0111 }
            int r7 = r7 + -1
            r11._numInternalProcessing = r7     // Catch:{ all -> 0x0111 }
            monitor-exit(r11)     // Catch:{ all -> 0x0111 }
            int r0 = r0 + 1
            goto L_0x002e
        L_0x0111:
            r1 = move-exception
        L_0x0112:
            monitor-exit(r11)     // Catch:{ all -> 0x0104 }
            throw r1
        L_0x0114:
            r1 = move-exception
        L_0x0115:
            monitor-exit(r11)     // Catch:{ all -> 0x0117 }
            throw r1
        L_0x0117:
            r1 = move-exception
            goto L_0x0115
        L_0x0119:
            r11.allocate()
            return
        L_0x011d:
            r0 = move-exception
            monitor-exit(r11)     // Catch:{ all -> 0x011d }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.pool.impl.GenericObjectPool.evict():void");
    }

    /* access modifiers changed from: private */
    public void ensureMinIdle() throws Exception {
        int objectDeficit = calculateDeficit(false);
        int j = 0;
        while (j < objectDeficit && calculateDeficit(true) > 0) {
            try {
                addObject();
                synchronized (this) {
                    this._numInternalProcessing--;
                }
                allocate();
                j++;
            } catch (Throwable th) {
                synchronized (this) {
                    this._numInternalProcessing--;
                    allocate();
                    throw th;
                }
            }
        }
    }

    private synchronized int calculateDeficit(boolean incrementInternal) {
        int objectDeficit;
        objectDeficit = getMinIdle() - getNumIdle();
        if (this._maxActive > 0) {
            objectDeficit = Math.min(objectDeficit, Math.max(0, ((getMaxActive() - getNumActive()) - getNumIdle()) - this._numInternalProcessing));
        }
        if (incrementInternal && objectDeficit > 0) {
            this._numInternalProcessing++;
        }
        return objectDeficit;
    }

    public void addObject() throws Exception {
        assertOpen();
        PoolableObjectFactory<T> poolableObjectFactory = this._factory;
        if (poolableObjectFactory != null) {
            T obj = poolableObjectFactory.makeObject();
            try {
                assertOpen();
                addObjectToPool(obj, false);
            } catch (IllegalStateException ex) {
                try {
                    this._factory.destroyObject(obj);
                } catch (Exception e) {
                }
                throw ex;
            }
        } else {
            throw new IllegalStateException("Cannot add objects without a factory.");
        }
    }

    /* access modifiers changed from: protected */
    public synchronized void startEvictor(long delay) {
        if (this._evictor != null) {
            EvictionTimer.cancel(this._evictor);
            this._evictor = null;
        }
        if (delay > 0) {
            GenericObjectPool<T>.Evictor evictor = new Evictor();
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
        buf.append("Idle Objects:\n");
        Iterator<GenericKeyedObjectPool.ObjectTimestampPair<T>> it = this._pool.iterator();
        long time = System.currentTimeMillis();
        while (it.hasNext()) {
            GenericKeyedObjectPool.ObjectTimestampPair<T> pair = it.next();
            buf.append("\t");
            buf.append(pair.value);
            buf.append("\t");
            buf.append(time - pair.tstamp);
            buf.append(Base64.LINE_SEPARATOR);
        }
        return buf.toString();
    }

    private int getNumTests() {
        int i = this._numTestsPerEvictionRun;
        if (i >= 0) {
            return Math.min(i, this._pool.size());
        }
        return (int) Math.ceil(((double) this._pool.size()) / Math.abs((double) this._numTestsPerEvictionRun));
    }

    private class Evictor extends TimerTask {
        private Evictor() {
        }

        public void run() {
            try {
                GenericObjectPool.this.evict();
            } catch (Exception e) {
            } catch (OutOfMemoryError oome) {
                oome.printStackTrace(System.err);
            }
            try {
                GenericObjectPool.this.ensureMinIdle();
            } catch (Exception e2) {
            }
        }
    }

    private static final class Latch<T> {
        private boolean _mayCreate;
        private GenericKeyedObjectPool.ObjectTimestampPair<T> _pair;

        private Latch() {
            this._mayCreate = false;
        }

        /* access modifiers changed from: private */
        public synchronized GenericKeyedObjectPool.ObjectTimestampPair<T> getPair() {
            return this._pair;
        }

        /* access modifiers changed from: private */
        public synchronized void setPair(GenericKeyedObjectPool.ObjectTimestampPair<T> pair) {
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
