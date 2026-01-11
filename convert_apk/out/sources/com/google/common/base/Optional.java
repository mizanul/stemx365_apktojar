package com.google.common.base;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;
import javax.annotation.Nullable;

public abstract class Optional<T> implements Serializable {
    private static final long serialVersionUID = 0;

    public abstract Set<T> asSet();

    public abstract boolean equals(@Nullable Object obj);

    public abstract T get();

    public abstract int hashCode();

    public abstract boolean isPresent();

    /* renamed from: or */
    public abstract Optional<T> mo8208or(Optional<? extends T> optional);

    /* renamed from: or */
    public abstract T mo8209or(Supplier<? extends T> supplier);

    /* renamed from: or */
    public abstract T mo8210or(T t);

    @Nullable
    public abstract T orNull();

    public abstract String toString();

    public abstract <V> Optional<V> transform(Function<? super T, V> function);

    public static <T> Optional<T> absent() {
        return Absent.INSTANCE;
    }

    /* renamed from: of */
    public static <T> Optional<T> m20of(T reference) {
        return new Present(Preconditions.checkNotNull(reference));
    }

    public static <T> Optional<T> fromNullable(@Nullable T nullableReference) {
        return nullableReference == null ? absent() : new Present(nullableReference);
    }

    Optional() {
    }

    public static <T> Iterable<T> presentInstances(final Iterable<Optional<T>> optionals) {
        Preconditions.checkNotNull(optionals);
        return new Iterable<T>() {
            public Iterator<T> iterator() {
                return new AbstractIterator<T>() {
                    private final Iterator<Optional<T>> iterator = ((Iterator) Preconditions.checkNotNull(optionals.iterator()));

                    /* access modifiers changed from: protected */
                    public T computeNext() {
                        while (this.iterator.hasNext()) {
                            Optional<T> optional = this.iterator.next();
                            if (optional.isPresent()) {
                                return optional.get();
                            }
                        }
                        return endOfData();
                    }
                };
            }
        };
    }
}
