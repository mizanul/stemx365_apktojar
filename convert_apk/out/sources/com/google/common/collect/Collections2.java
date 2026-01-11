package com.google.common.collect;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.math.IntMath;
import com.google.common.math.LongMath;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import org.xbill.DNS.TTL;

public final class Collections2 {
    static final Joiner STANDARD_JOINER = Joiner.m19on(", ").useForNull("null");

    private Collections2() {
    }

    public static <E> Collection<E> filter(Collection<E> unfiltered, Predicate<? super E> predicate) {
        if (unfiltered instanceof FilteredCollection) {
            return ((FilteredCollection) unfiltered).createCombined(predicate);
        }
        return new FilteredCollection((Collection) Preconditions.checkNotNull(unfiltered), (Predicate) Preconditions.checkNotNull(predicate));
    }

    static boolean safeContains(Collection<?> collection, Object object) {
        try {
            return collection.contains(object);
        } catch (ClassCastException e) {
            return false;
        }
    }

    static class FilteredCollection<E> implements Collection<E> {
        final Predicate<? super E> predicate;
        final Collection<E> unfiltered;

        FilteredCollection(Collection<E> unfiltered2, Predicate<? super E> predicate2) {
            this.unfiltered = unfiltered2;
            this.predicate = predicate2;
        }

        /* access modifiers changed from: package-private */
        public FilteredCollection<E> createCombined(Predicate<? super E> newPredicate) {
            return new FilteredCollection<>(this.unfiltered, Predicates.and(this.predicate, newPredicate));
        }

        public boolean add(E element) {
            Preconditions.checkArgument(this.predicate.apply(element));
            return this.unfiltered.add(element);
        }

        public boolean addAll(Collection<? extends E> collection) {
            for (E element : collection) {
                Preconditions.checkArgument(this.predicate.apply(element));
            }
            return this.unfiltered.addAll(collection);
        }

        public void clear() {
            Iterables.removeIf(this.unfiltered, this.predicate);
        }

        public boolean contains(Object element) {
            try {
                return this.predicate.apply(element) && this.unfiltered.contains(element);
            } catch (NullPointerException e) {
                return false;
            } catch (ClassCastException e2) {
                return false;
            }
        }

        public boolean containsAll(Collection<?> collection) {
            for (Object element : collection) {
                if (!contains(element)) {
                    return false;
                }
            }
            return true;
        }

        public boolean isEmpty() {
            return !Iterators.any(this.unfiltered.iterator(), this.predicate);
        }

        public Iterator<E> iterator() {
            return Iterators.filter(this.unfiltered.iterator(), this.predicate);
        }

        public boolean remove(Object element) {
            try {
                return this.predicate.apply(element) && this.unfiltered.remove(element);
            } catch (NullPointerException e) {
                return false;
            } catch (ClassCastException e2) {
                return false;
            }
        }

        public boolean removeAll(final Collection<?> collection) {
            Preconditions.checkNotNull(collection);
            return Iterables.removeIf(this.unfiltered, new Predicate<E>() {
                public boolean apply(E input) {
                    return FilteredCollection.this.predicate.apply(input) && collection.contains(input);
                }
            });
        }

        public boolean retainAll(final Collection<?> collection) {
            Preconditions.checkNotNull(collection);
            return Iterables.removeIf(this.unfiltered, new Predicate<E>() {
                public boolean apply(E input) {
                    return FilteredCollection.this.predicate.apply(input) && !collection.contains(input);
                }
            });
        }

        public int size() {
            return Iterators.size(iterator());
        }

        public Object[] toArray() {
            return Lists.newArrayList(iterator()).toArray();
        }

        public <T> T[] toArray(T[] array) {
            return Lists.newArrayList(iterator()).toArray(array);
        }

        public String toString() {
            return Iterators.toString(iterator());
        }
    }

    public static <F, T> Collection<T> transform(Collection<F> fromCollection, Function<? super F, T> function) {
        return new TransformedCollection(fromCollection, function);
    }

    static class TransformedCollection<F, T> extends AbstractCollection<T> {
        final Collection<F> fromCollection;
        final Function<? super F, ? extends T> function;

        TransformedCollection(Collection<F> fromCollection2, Function<? super F, ? extends T> function2) {
            this.fromCollection = (Collection) Preconditions.checkNotNull(fromCollection2);
            this.function = (Function) Preconditions.checkNotNull(function2);
        }

        public void clear() {
            this.fromCollection.clear();
        }

        public boolean isEmpty() {
            return this.fromCollection.isEmpty();
        }

        public Iterator<T> iterator() {
            return Iterators.transform(this.fromCollection.iterator(), this.function);
        }

        public int size() {
            return this.fromCollection.size();
        }
    }

    static boolean containsAllImpl(Collection<?> self, Collection<?> c) {
        Preconditions.checkNotNull(self);
        for (Object o : c) {
            if (!self.contains(o)) {
                return false;
            }
        }
        return true;
    }

    static String toStringImpl(final Collection<?> collection) {
        StringBuilder sb = newStringBuilderForCollection(collection.size()).append('[');
        STANDARD_JOINER.appendTo(sb, (Iterable<?>) Iterables.transform(collection, new Function<Object, Object>() {
            public Object apply(Object input) {
                return input == collection ? "(this Collection)" : input;
            }
        }));
        sb.append(']');
        return sb.toString();
    }

    static StringBuilder newStringBuilderForCollection(int size) {
        Preconditions.checkArgument(size >= 0, "size must be non-negative");
        return new StringBuilder((int) Math.min(((long) size) * 8, 1073741824));
    }

    static <T> Collection<T> cast(Iterable<T> iterable) {
        return (Collection) iterable;
    }

    public static <E extends Comparable<? super E>> Collection<List<E>> orderedPermutations(Iterable<E> elements) {
        return orderedPermutations(elements, Ordering.natural());
    }

    public static <E> Collection<List<E>> orderedPermutations(Iterable<E> elements, Comparator<? super E> comparator) {
        return new OrderedPermutationCollection(elements, comparator);
    }

    private static final class OrderedPermutationCollection<E> extends AbstractCollection<List<E>> {
        final Comparator<? super E> comparator;
        final ImmutableList<E> inputList;
        final int size;

        OrderedPermutationCollection(Iterable<E> input, Comparator<? super E> comparator2) {
            ImmutableList<E> immutableSortedCopy = Ordering.from(comparator2).immutableSortedCopy(input);
            this.inputList = immutableSortedCopy;
            this.comparator = comparator2;
            this.size = calculateSize(immutableSortedCopy, comparator2);
        }

        private static <E> int calculateSize(List<E> sortedInputList, Comparator<? super E> comparator2) {
            long permutations = 1;
            int n = 1;
            int r = 1;
            while (n < sortedInputList.size()) {
                if (comparator2.compare(sortedInputList.get(n - 1), sortedInputList.get(n)) < 0) {
                    permutations *= LongMath.binomial(n, r);
                    r = 0;
                    if (!Collections2.isPositiveInt(permutations)) {
                        return Integer.MAX_VALUE;
                    }
                }
                n++;
                r++;
            }
            long permutations2 = permutations * LongMath.binomial(n, r);
            if (!Collections2.isPositiveInt(permutations2)) {
                return Integer.MAX_VALUE;
            }
            return (int) permutations2;
        }

        public int size() {
            return this.size;
        }

        public boolean isEmpty() {
            return false;
        }

        public Iterator<List<E>> iterator() {
            return new OrderedPermutationIterator(this.inputList, this.comparator);
        }

        public boolean contains(@Nullable Object obj) {
            if (!(obj instanceof List)) {
                return false;
            }
            return Collections2.isPermutation(this.inputList, (List) obj);
        }

        public String toString() {
            return "orderedPermutationCollection(" + this.inputList + ")";
        }
    }

    private static final class OrderedPermutationIterator<E> extends AbstractIterator<List<E>> {
        final Comparator<? super E> comparator;
        List<E> nextPermutation;

        OrderedPermutationIterator(List<E> list, Comparator<? super E> comparator2) {
            this.nextPermutation = Lists.newArrayList(list);
            this.comparator = comparator2;
        }

        /* access modifiers changed from: protected */
        public List<E> computeNext() {
            List<E> list = this.nextPermutation;
            if (list == null) {
                return (List) endOfData();
            }
            ImmutableList<E> next = ImmutableList.copyOf(list);
            calculateNextPermutation();
            return next;
        }

        /* access modifiers changed from: package-private */
        public void calculateNextPermutation() {
            int j = findNextJ();
            if (j == -1) {
                this.nextPermutation = null;
                return;
            }
            Collections.swap(this.nextPermutation, j, findNextL(j));
            Collections.reverse(this.nextPermutation.subList(j + 1, this.nextPermutation.size()));
        }

        /* access modifiers changed from: package-private */
        public int findNextJ() {
            for (int k = this.nextPermutation.size() - 2; k >= 0; k--) {
                if (this.comparator.compare(this.nextPermutation.get(k), this.nextPermutation.get(k + 1)) < 0) {
                    return k;
                }
            }
            return -1;
        }

        /* access modifiers changed from: package-private */
        public int findNextL(int j) {
            E ak = this.nextPermutation.get(j);
            for (int l = this.nextPermutation.size() - 1; l > j; l--) {
                if (this.comparator.compare(ak, this.nextPermutation.get(l)) < 0) {
                    return l;
                }
            }
            throw new AssertionError("this statement should be unreachable");
        }
    }

    public static <E> Collection<List<E>> permutations(Collection<E> elements) {
        return new PermutationCollection(ImmutableList.copyOf(elements));
    }

    private static final class PermutationCollection<E> extends AbstractCollection<List<E>> {
        final ImmutableList<E> inputList;

        PermutationCollection(ImmutableList<E> input) {
            this.inputList = input;
        }

        public int size() {
            return IntMath.factorial(this.inputList.size());
        }

        public boolean isEmpty() {
            return false;
        }

        public Iterator<List<E>> iterator() {
            return new PermutationIterator(this.inputList);
        }

        public boolean contains(@Nullable Object obj) {
            if (!(obj instanceof List)) {
                return false;
            }
            return Collections2.isPermutation(this.inputList, (List) obj);
        }

        public String toString() {
            return "permutations(" + this.inputList + ")";
        }
    }

    private static class PermutationIterator<E> extends AbstractIterator<List<E>> {

        /* renamed from: c */
        final int[] f66c;

        /* renamed from: j */
        int f67j;
        final List<E> list;

        /* renamed from: o */
        final int[] f68o;

        PermutationIterator(List<E> list2) {
            this.list = new ArrayList(list2);
            int n = list2.size();
            this.f66c = new int[n];
            this.f68o = new int[n];
            for (int i = 0; i < n; i++) {
                this.f66c[i] = 0;
                this.f68o[i] = 1;
            }
            this.f67j = Integer.MAX_VALUE;
        }

        /* access modifiers changed from: protected */
        public List<E> computeNext() {
            if (this.f67j <= 0) {
                return (List) endOfData();
            }
            ImmutableList<E> next = ImmutableList.copyOf(this.list);
            calculateNextPermutation();
            return next;
        }

        /* access modifiers changed from: package-private */
        public void calculateNextPermutation() {
            int size = this.list.size() - 1;
            this.f67j = size;
            int s = 0;
            if (size != -1) {
                while (true) {
                    int[] iArr = this.f66c;
                    int i = this.f67j;
                    int q = iArr[i] + this.f68o[i];
                    if (q < 0) {
                        switchDirection();
                    } else if (q != i + 1) {
                        Collections.swap(this.list, (i - iArr[i]) + s, (i - q) + s);
                        this.f66c[this.f67j] = q;
                        return;
                    } else if (i != 0) {
                        s++;
                        switchDirection();
                    } else {
                        return;
                    }
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void switchDirection() {
            int[] iArr = this.f68o;
            int i = this.f67j;
            iArr[i] = -iArr[i];
            this.f67j = i - 1;
        }
    }

    /* access modifiers changed from: private */
    public static boolean isPermutation(List<?> first, List<?> second) {
        if (first.size() != second.size()) {
            return false;
        }
        return HashMultiset.create(first).equals(HashMultiset.create(second));
    }

    /* access modifiers changed from: private */
    public static boolean isPositiveInt(long n) {
        return n >= 0 && n <= TTL.MAX_VALUE;
    }
}
