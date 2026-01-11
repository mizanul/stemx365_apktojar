package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Table;
import com.google.common.collect.Tables;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

public abstract class ImmutableTable<R, C, V> implements Table<R, C, V> {
    public abstract ImmutableSet<Table.Cell<R, C, V>> cellSet();

    public abstract ImmutableMap<R, V> column(C c);

    public abstract ImmutableSet<C> columnKeySet();

    public abstract ImmutableMap<C, Map<R, V>> columnMap();

    public abstract ImmutableMap<C, V> row(R r);

    public abstract ImmutableSet<R> rowKeySet();

    public abstract ImmutableMap<R, Map<C, V>> rowMap();

    /* renamed from: of */
    public static final <R, C, V> ImmutableTable<R, C, V> m130of() {
        return EmptyImmutableTable.INSTANCE;
    }

    /* renamed from: of */
    public static final <R, C, V> ImmutableTable<R, C, V> m131of(R rowKey, C columnKey, V value) {
        return new SingletonImmutableTable(rowKey, columnKey, value);
    }

    public static final <R, C, V> ImmutableTable<R, C, V> copyOf(Table<? extends R, ? extends C, ? extends V> table) {
        if (table instanceof ImmutableTable) {
            return (ImmutableTable) table;
        }
        int size = table.size();
        if (size == 0) {
            return m130of();
        }
        if (size != 1) {
            ImmutableSet.Builder<Table.Cell<R, C, V>> cellSetBuilder = ImmutableSet.builder();
            for (Table.Cell<? extends R, ? extends C, ? extends V> cell : table.cellSet()) {
                cellSetBuilder.add((Object) cellOf(cell.getRowKey(), cell.getColumnKey(), cell.getValue()));
            }
            return RegularImmutableTable.forCells(cellSetBuilder.build());
        }
        Table.Cell<? extends R, ? extends C, ? extends V> onlyCell = (Table.Cell) Iterables.getOnlyElement(table.cellSet());
        return m131of(onlyCell.getRowKey(), onlyCell.getColumnKey(), onlyCell.getValue());
    }

    public static final <R, C, V> Builder<R, C, V> builder() {
        return new Builder<>();
    }

    static <R, C, V> Table.Cell<R, C, V> cellOf(R rowKey, C columnKey, V value) {
        return Tables.immutableCell(Preconditions.checkNotNull(rowKey), Preconditions.checkNotNull(columnKey), Preconditions.checkNotNull(value));
    }

    public static final class Builder<R, C, V> {
        private final List<Table.Cell<R, C, V>> cells = Lists.newArrayList();
        private Comparator<? super C> columnComparator;
        private Comparator<? super R> rowComparator;

        public Builder<R, C, V> orderRowsBy(Comparator<? super R> rowComparator2) {
            this.rowComparator = (Comparator) Preconditions.checkNotNull(rowComparator2);
            return this;
        }

        public Builder<R, C, V> orderColumnsBy(Comparator<? super C> columnComparator2) {
            this.columnComparator = (Comparator) Preconditions.checkNotNull(columnComparator2);
            return this;
        }

        public Builder<R, C, V> put(R rowKey, C columnKey, V value) {
            this.cells.add(ImmutableTable.cellOf(rowKey, columnKey, value));
            return this;
        }

        public Builder<R, C, V> put(Table.Cell<? extends R, ? extends C, ? extends V> cell) {
            if (cell instanceof Tables.ImmutableCell) {
                Preconditions.checkNotNull(cell.getRowKey());
                Preconditions.checkNotNull(cell.getColumnKey());
                Preconditions.checkNotNull(cell.getValue());
                this.cells.add(cell);
            } else {
                put(cell.getRowKey(), cell.getColumnKey(), cell.getValue());
            }
            return this;
        }

        public Builder<R, C, V> putAll(Table<? extends R, ? extends C, ? extends V> table) {
            for (Table.Cell<? extends R, ? extends C, ? extends V> cell : table.cellSet()) {
                put(cell);
            }
            return this;
        }

        public ImmutableTable<R, C, V> build() {
            int size = this.cells.size();
            if (size == 0) {
                return ImmutableTable.m130of();
            }
            if (size != 1) {
                return RegularImmutableTable.forCells(this.cells, this.rowComparator, this.columnComparator);
            }
            return new SingletonImmutableTable((Table.Cell) Iterables.getOnlyElement(this.cells));
        }
    }

    ImmutableTable() {
    }

    public final void clear() {
        throw new UnsupportedOperationException();
    }

    public final V put(R r, C c, V v) {
        throw new UnsupportedOperationException();
    }

    public final void putAll(Table<? extends R, ? extends C, ? extends V> table) {
        throw new UnsupportedOperationException();
    }

    public final V remove(Object rowKey, Object columnKey) {
        throw new UnsupportedOperationException();
    }

    public boolean equals(@Nullable Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Table) {
            return cellSet().equals(((Table) obj).cellSet());
        }
        return false;
    }

    public int hashCode() {
        return cellSet().hashCode();
    }

    public String toString() {
        return rowMap().toString();
    }
}
