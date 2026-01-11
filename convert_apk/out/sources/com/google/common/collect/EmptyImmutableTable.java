package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.Table;
import java.util.Map;
import javax.annotation.Nullable;

final class EmptyImmutableTable extends ImmutableTable<Object, Object, Object> {
    static final EmptyImmutableTable INSTANCE = new EmptyImmutableTable();
    private static final long serialVersionUID = 0;

    private EmptyImmutableTable() {
    }

    public int size() {
        return 0;
    }

    public Object get(@Nullable Object rowKey, @Nullable Object columnKey) {
        return null;
    }

    public boolean isEmpty() {
        return true;
    }

    public boolean equals(@Nullable Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Table) {
            return ((Table) obj).isEmpty();
        }
        return false;
    }

    public int hashCode() {
        return 0;
    }

    public ImmutableSet<Table.Cell<Object, Object, Object>> cellSet() {
        return ImmutableSet.m80of();
    }

    public ImmutableMap<Object, Object> column(Object columnKey) {
        Preconditions.checkNotNull(columnKey);
        return ImmutableMap.m61of();
    }

    public ImmutableSet<Object> columnKeySet() {
        return ImmutableSet.m80of();
    }

    public ImmutableMap<Object, Map<Object, Object>> columnMap() {
        return ImmutableMap.m61of();
    }

    public boolean contains(@Nullable Object rowKey, @Nullable Object columnKey) {
        return false;
    }

    public boolean containsColumn(@Nullable Object columnKey) {
        return false;
    }

    public boolean containsRow(@Nullable Object rowKey) {
        return false;
    }

    public boolean containsValue(@Nullable Object value) {
        return false;
    }

    public ImmutableMap<Object, Object> row(Object rowKey) {
        Preconditions.checkNotNull(rowKey);
        return ImmutableMap.m61of();
    }

    public ImmutableSet<Object> rowKeySet() {
        return ImmutableSet.m80of();
    }

    public ImmutableMap<Object, Map<Object, Object>> rowMap() {
        return ImmutableMap.m61of();
    }

    public String toString() {
        return "{}";
    }

    public ImmutableCollection<Object> values() {
        return ImmutableSet.m80of();
    }

    /* access modifiers changed from: package-private */
    public Object readResolve() {
        return INSTANCE;
    }
}
