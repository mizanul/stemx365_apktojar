package com.google.common.collect;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

public final class HashBiMap<K, V> extends AbstractBiMap<K, V> {
    private static final long serialVersionUID = 0;

    public /* bridge */ /* synthetic */ void clear() {
        super.clear();
    }

    public /* bridge */ /* synthetic */ boolean containsValue(Object x0) {
        return super.containsValue(x0);
    }

    public /* bridge */ /* synthetic */ Set entrySet() {
        return super.entrySet();
    }

    public /* bridge */ /* synthetic */ BiMap inverse() {
        return super.inverse();
    }

    public /* bridge */ /* synthetic */ Set keySet() {
        return super.keySet();
    }

    public /* bridge */ /* synthetic */ void putAll(Map x0) {
        super.putAll(x0);
    }

    public /* bridge */ /* synthetic */ Object remove(Object x0) {
        return super.remove(x0);
    }

    public /* bridge */ /* synthetic */ Set values() {
        return super.values();
    }

    public static <K, V> HashBiMap<K, V> create() {
        return new HashBiMap<>();
    }

    public static <K, V> HashBiMap<K, V> create(int expectedSize) {
        return new HashBiMap<>(expectedSize);
    }

    public static <K, V> HashBiMap<K, V> create(Map<? extends K, ? extends V> map) {
        HashBiMap<K, V> bimap = create(map.size());
        bimap.putAll(map);
        return bimap;
    }

    private HashBiMap() {
        super(new HashMap(), new HashMap());
    }

    private HashBiMap(int expectedSize) {
        super(Maps.newHashMapWithExpectedSize(expectedSize), Maps.newHashMapWithExpectedSize(expectedSize));
    }

    public V put(@Nullable K key, @Nullable V value) {
        return super.put(key, value);
    }

    public V forcePut(@Nullable K key, @Nullable V value) {
        return super.forcePut(key, value);
    }

    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        Serialization.writeMap(this, stream);
    }

    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        int size = Serialization.readCount(stream);
        setDelegates(Maps.newHashMapWithExpectedSize(size), Maps.newHashMapWithExpectedSize(size));
        Serialization.populateMap(this, stream, size);
    }
}
