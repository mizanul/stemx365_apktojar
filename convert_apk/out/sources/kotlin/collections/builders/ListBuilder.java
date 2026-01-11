package kotlin.collections.builders;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;
import kotlin.Metadata;
import kotlin.collections.AbstractList;
import kotlin.collections.AbstractMutableList;
import kotlin.collections.ArrayDeque;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMutableList;
import kotlin.jvm.internal.markers.KMutableListIterator;
import org.jboss.netty.handler.codec.rtsp.RtspHeaders;

@Metadata(mo11628d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u001e\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\b\b\n\u0002\u0010\u0000\n\u0002\b\b\n\u0002\u0010)\n\u0002\b\u0002\n\u0002\u0010+\n\u0002\b\u0015\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\u00060\u0003j\u0002`\u00042\b\u0012\u0004\u0012\u0002H\u00010\u0005:\u0001QB\u0007\b\u0016¢\u0006\u0002\u0010\u0006B\u000f\b\u0016\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tBM\b\u0002\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00000\u000b\u0012\u0006\u0010\f\u001a\u00020\b\u0012\u0006\u0010\r\u001a\u00020\b\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u000e\u0010\u0010\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0000\u0012\u000e\u0010\u0011\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0000¢\u0006\u0002\u0010\u0012J\u0015\u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u0018\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0019J\u001d\u0010\u0017\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\b2\u0006\u0010\u0018\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u001cJ\u001e\u0010\u001d\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\b2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00028\u00000\u001fH\u0016J\u0016\u0010\u001d\u001a\u00020\u000f2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00028\u00000\u001fH\u0016J&\u0010 \u001a\u00020\u001a2\u0006\u0010!\u001a\u00020\b2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00028\u00000\u001f2\u0006\u0010\"\u001a\u00020\bH\u0002J\u001d\u0010#\u001a\u00020\u001a2\u0006\u0010!\u001a\u00020\b2\u0006\u0010\u0018\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010\u001cJ\f\u0010$\u001a\b\u0012\u0004\u0012\u00028\u00000%J\b\u0010&\u001a\u00020\u001aH\u0002J\b\u0010'\u001a\u00020\u001aH\u0016J\u0014\u0010(\u001a\u00020\u000f2\n\u0010)\u001a\u0006\u0012\u0002\b\u00030%H\u0002J\u0010\u0010*\u001a\u00020\u001a2\u0006\u0010+\u001a\u00020\bH\u0002J\u0010\u0010,\u001a\u00020\u001a2\u0006\u0010\"\u001a\u00020\bH\u0002J\u0013\u0010-\u001a\u00020\u000f2\b\u0010)\u001a\u0004\u0018\u00010.H\u0002J\u0016\u0010/\u001a\u00028\u00002\u0006\u0010\u001b\u001a\u00020\bH\u0002¢\u0006\u0002\u00100J\b\u00101\u001a\u00020\bH\u0016J\u0015\u00102\u001a\u00020\b2\u0006\u0010\u0018\u001a\u00028\u0000H\u0016¢\u0006\u0002\u00103J\u0018\u00104\u001a\u00020\u001a2\u0006\u0010!\u001a\u00020\b2\u0006\u0010\"\u001a\u00020\bH\u0002J\b\u00105\u001a\u00020\u000fH\u0016J\u000f\u00106\u001a\b\u0012\u0004\u0012\u00028\u000007H\u0002J\u0015\u00108\u001a\u00020\b2\u0006\u0010\u0018\u001a\u00028\u0000H\u0016¢\u0006\u0002\u00103J\u000e\u00109\u001a\b\u0012\u0004\u0012\u00028\u00000:H\u0016J\u0016\u00109\u001a\b\u0012\u0004\u0012\u00028\u00000:2\u0006\u0010\u001b\u001a\u00020\bH\u0016J\u0015\u0010;\u001a\u00020\u000f2\u0006\u0010\u0018\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0019J\u0016\u0010<\u001a\u00020\u000f2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00028\u00000\u001fH\u0016J\u0015\u0010=\u001a\u00028\u00002\u0006\u0010\u001b\u001a\u00020\bH\u0016¢\u0006\u0002\u00100J\u0015\u0010>\u001a\u00028\u00002\u0006\u0010!\u001a\u00020\bH\u0002¢\u0006\u0002\u00100J\u0018\u0010?\u001a\u00020\u001a2\u0006\u0010@\u001a\u00020\b2\u0006\u0010A\u001a\u00020\bH\u0002J\u0016\u0010B\u001a\u00020\u000f2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00028\u00000\u001fH\u0016J.\u0010C\u001a\u00020\b2\u0006\u0010@\u001a\u00020\b2\u0006\u0010A\u001a\u00020\b2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00028\u00000\u001f2\u0006\u0010D\u001a\u00020\u000fH\u0002J\u001e\u0010E\u001a\u00028\u00002\u0006\u0010\u001b\u001a\u00020\b2\u0006\u0010\u0018\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010FJ\u001e\u0010G\u001a\b\u0012\u0004\u0012\u00028\u00000\u00022\u0006\u0010H\u001a\u00020\b2\u0006\u0010I\u001a\u00020\bH\u0016J\u0015\u0010J\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010.0\u000bH\u0016¢\u0006\u0002\u0010KJ'\u0010J\u001a\b\u0012\u0004\u0012\u0002HL0\u000b\"\u0004\b\u0001\u0010L2\f\u0010M\u001a\b\u0012\u0004\u0012\u0002HL0\u000bH\u0016¢\u0006\u0002\u0010NJ\b\u0010O\u001a\u00020PH\u0016R\u0016\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00000\u000bX\u000e¢\u0006\u0004\n\u0002\u0010\u0013R\u0016\u0010\u0010\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0000X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\bX\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\bX\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\u0011\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0000X\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0014\u001a\u00020\b8VX\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016¨\u0006R"}, mo11629d2 = {"Lkotlin/collections/builders/ListBuilder;", "E", "", "Ljava/util/RandomAccess;", "Lkotlin/collections/RandomAccess;", "Lkotlin/collections/AbstractMutableList;", "()V", "initialCapacity", "", "(I)V", "array", "", "offset", "length", "isReadOnly", "", "backing", "root", "([Ljava/lang/Object;IIZLkotlin/collections/builders/ListBuilder;Lkotlin/collections/builders/ListBuilder;)V", "[Ljava/lang/Object;", "size", "getSize", "()I", "add", "element", "(Ljava/lang/Object;)Z", "", "index", "(ILjava/lang/Object;)V", "addAll", "elements", "", "addAllInternal", "i", "n", "addAtInternal", "build", "", "checkIsMutable", "clear", "contentEquals", "other", "ensureCapacity", "minCapacity", "ensureExtraCapacity", "equals", "", "get", "(I)Ljava/lang/Object;", "hashCode", "indexOf", "(Ljava/lang/Object;)I", "insertAtInternal", "isEmpty", "iterator", "", "lastIndexOf", "listIterator", "", "remove", "removeAll", "removeAt", "removeAtInternal", "removeRangeInternal", "rangeOffset", "rangeLength", "retainAll", "retainOrRemoveAllInternal", "retain", "set", "(ILjava/lang/Object;)Ljava/lang/Object;", "subList", "fromIndex", "toIndex", "toArray", "()[Ljava/lang/Object;", "T", "destination", "([Ljava/lang/Object;)[Ljava/lang/Object;", "toString", "", "Itr", "kotlin-stdlib"}, mo11630k = 1, mo11631mv = {1, 5, 1})
/* compiled from: ListBuilder.kt */
public final class ListBuilder<E> extends AbstractMutableList<E> implements List<E>, RandomAccess, KMutableList {
    /* access modifiers changed from: private */
    public E[] array;
    private final ListBuilder<E> backing;
    private boolean isReadOnly;
    /* access modifiers changed from: private */
    public int length;
    /* access modifiers changed from: private */
    public int offset;
    private final ListBuilder<E> root;

    private ListBuilder(E[] array2, int offset2, int length2, boolean isReadOnly2, ListBuilder<E> backing2, ListBuilder<E> root2) {
        this.array = array2;
        this.offset = offset2;
        this.length = length2;
        this.isReadOnly = isReadOnly2;
        this.backing = backing2;
        this.root = root2;
    }

    public ListBuilder() {
        this(10);
    }

    public ListBuilder(int initialCapacity) {
        this(ListBuilderKt.arrayOfUninitializedElements(initialCapacity), 0, 0, false, (ListBuilder) null, (ListBuilder) null);
    }

    public final List<E> build() {
        if (this.backing == null) {
            checkIsMutable();
            this.isReadOnly = true;
            return this;
        }
        throw new IllegalStateException();
    }

    public int getSize() {
        return this.length;
    }

    public boolean isEmpty() {
        return this.length == 0;
    }

    public E get(int index) {
        AbstractList.Companion.checkElementIndex$kotlin_stdlib(index, this.length);
        return this.array[this.offset + index];
    }

    public E set(int index, E element) {
        checkIsMutable();
        AbstractList.Companion.checkElementIndex$kotlin_stdlib(index, this.length);
        Object[] objArr = this.array;
        int i = this.offset;
        Object old = objArr[i + index];
        objArr[i + index] = element;
        return old;
    }

    public int indexOf(Object element) {
        for (int i = 0; i < this.length; i++) {
            if (Intrinsics.areEqual((Object) this.array[this.offset + i], element)) {
                return i;
            }
        }
        return -1;
    }

    public int lastIndexOf(Object element) {
        for (int i = this.length - 1; i >= 0; i--) {
            if (Intrinsics.areEqual((Object) this.array[this.offset + i], element)) {
                return i;
            }
        }
        return -1;
    }

    public Iterator<E> iterator() {
        return new Itr<>(this, 0);
    }

    public ListIterator<E> listIterator() {
        return new Itr<>(this, 0);
    }

    public ListIterator<E> listIterator(int index) {
        AbstractList.Companion.checkPositionIndex$kotlin_stdlib(index, this.length);
        return new Itr<>(this, index);
    }

    public boolean add(E element) {
        checkIsMutable();
        addAtInternal(this.offset + this.length, element);
        return true;
    }

    public void add(int index, E element) {
        checkIsMutable();
        AbstractList.Companion.checkPositionIndex$kotlin_stdlib(index, this.length);
        addAtInternal(this.offset + index, element);
    }

    public boolean addAll(Collection<? extends E> elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        checkIsMutable();
        int n = elements.size();
        addAllInternal(this.offset + this.length, elements, n);
        return n > 0;
    }

    public boolean addAll(int index, Collection<? extends E> elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        checkIsMutable();
        AbstractList.Companion.checkPositionIndex$kotlin_stdlib(index, this.length);
        int n = elements.size();
        addAllInternal(this.offset + index, elements, n);
        return n > 0;
    }

    public void clear() {
        checkIsMutable();
        removeRangeInternal(this.offset, this.length);
    }

    public E removeAt(int index) {
        checkIsMutable();
        AbstractList.Companion.checkElementIndex$kotlin_stdlib(index, this.length);
        return removeAtInternal(this.offset + index);
    }

    public boolean remove(Object element) {
        checkIsMutable();
        int i = indexOf(element);
        if (i >= 0) {
            remove(i);
        }
        return i >= 0;
    }

    public boolean removeAll(Collection<? extends Object> elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        checkIsMutable();
        return retainOrRemoveAllInternal(this.offset, this.length, elements, false) > 0;
    }

    public boolean retainAll(Collection<? extends Object> elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        checkIsMutable();
        return retainOrRemoveAllInternal(this.offset, this.length, elements, true) > 0;
    }

    public List<E> subList(int fromIndex, int toIndex) {
        AbstractList.Companion.checkRangeIndexes$kotlin_stdlib(fromIndex, toIndex, this.length);
        E[] eArr = this.array;
        int i = this.offset + fromIndex;
        int i2 = toIndex - fromIndex;
        boolean z = this.isReadOnly;
        ListBuilder<E> listBuilder = this.root;
        return new ListBuilder<>(eArr, i, i2, z, this, listBuilder != null ? listBuilder : this);
    }

    public <T> T[] toArray(T[] destination) {
        Intrinsics.checkNotNullParameter(destination, RtspHeaders.Values.DESTINATION);
        int length2 = destination.length;
        int i = this.length;
        if (length2 < i) {
            E[] eArr = this.array;
            int i2 = this.offset;
            T[] copyOfRange = Arrays.copyOfRange(eArr, i2, i + i2, destination.getClass());
            Intrinsics.checkNotNullExpressionValue(copyOfRange, "java.util.Arrays.copyOfR…h, destination.javaClass)");
            return copyOfRange;
        }
        E[] eArr2 = this.array;
        if (eArr2 != null) {
            int i3 = this.offset;
            ArraysKt.copyInto((T[]) eArr2, destination, 0, i3, i + i3);
            int length3 = destination.length;
            int i4 = this.length;
            if (length3 > i4) {
                destination[i4] = null;
            }
            return destination;
        }
        throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T>");
    }

    public Object[] toArray() {
        E[] eArr = this.array;
        int i = this.offset;
        Object[] copyOfRange = ArraysKt.copyOfRange((T[]) eArr, i, this.length + i);
        if (copyOfRange != null) {
            return copyOfRange;
        }
        throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<kotlin.Any?>");
    }

    public boolean equals(Object other) {
        return other == this || ((other instanceof List) && contentEquals((List) other));
    }

    public int hashCode() {
        return ListBuilderKt.subarrayContentHashCode(this.array, this.offset, this.length);
    }

    public String toString() {
        return ListBuilderKt.subarrayContentToString(this.array, this.offset, this.length);
    }

    private final void ensureCapacity(int minCapacity) {
        if (this.backing != null) {
            throw new IllegalStateException();
        } else if (minCapacity > this.array.length) {
            this.array = ListBuilderKt.copyOfUninitializedElements(this.array, ArrayDeque.Companion.newCapacity$kotlin_stdlib(this.array.length, minCapacity));
        }
    }

    private final void checkIsMutable() {
        ListBuilder<E> listBuilder;
        if (this.isReadOnly || ((listBuilder = this.root) != null && listBuilder.isReadOnly)) {
            throw new UnsupportedOperationException();
        }
    }

    private final void ensureExtraCapacity(int n) {
        ensureCapacity(this.length + n);
    }

    private final boolean contentEquals(List<?> other) {
        return ListBuilderKt.subarrayContentEquals(this.array, this.offset, this.length, other);
    }

    private final void insertAtInternal(int i, int n) {
        ensureExtraCapacity(n);
        E[] eArr = this.array;
        ArraysKt.copyInto((T[]) eArr, (T[]) eArr, i + n, i, this.offset + this.length);
        this.length += n;
    }

    private final void addAtInternal(int i, E element) {
        ListBuilder<E> listBuilder = this.backing;
        if (listBuilder != null) {
            listBuilder.addAtInternal(i, element);
            this.array = this.backing.array;
            this.length++;
            return;
        }
        insertAtInternal(i, 1);
        this.array[i] = element;
    }

    private final void addAllInternal(int i, Collection<? extends E> elements, int n) {
        ListBuilder<E> listBuilder = this.backing;
        if (listBuilder != null) {
            listBuilder.addAllInternal(i, elements, n);
            this.array = this.backing.array;
            this.length += n;
            return;
        }
        insertAtInternal(i, n);
        Iterator it = elements.iterator();
        for (int j = 0; j < n; j++) {
            this.array[i + j] = it.next();
        }
    }

    private final E removeAtInternal(int i) {
        ListBuilder<E> listBuilder = this.backing;
        if (listBuilder != null) {
            this.length--;
            return listBuilder.removeAtInternal(i);
        }
        Object[] objArr = this.array;
        Object old = objArr[i];
        ArraysKt.copyInto((T[]) objArr, (T[]) objArr, i, i + 1, this.offset + this.length);
        ListBuilderKt.resetAt(this.array, (this.offset + this.length) - 1);
        this.length--;
        return old;
    }

    private final void removeRangeInternal(int rangeOffset, int rangeLength) {
        ListBuilder<E> listBuilder = this.backing;
        if (listBuilder != null) {
            listBuilder.removeRangeInternal(rangeOffset, rangeLength);
        } else {
            E[] eArr = this.array;
            ArraysKt.copyInto((T[]) eArr, (T[]) eArr, rangeOffset, rangeOffset + rangeLength, this.length);
            E[] eArr2 = this.array;
            int i = this.length;
            ListBuilderKt.resetRange(eArr2, i - rangeLength, i);
        }
        this.length -= rangeLength;
    }

    private final int retainOrRemoveAllInternal(int rangeOffset, int rangeLength, Collection<? extends E> elements, boolean retain) {
        ListBuilder<E> listBuilder = this.backing;
        if (listBuilder != null) {
            int removed = listBuilder.retainOrRemoveAllInternal(rangeOffset, rangeLength, elements, retain);
            this.length -= removed;
            return removed;
        }
        int i = 0;
        int j = 0;
        while (i < rangeLength) {
            if (elements.contains(this.array[rangeOffset + i]) == retain) {
                E[] eArr = this.array;
                eArr[j + rangeOffset] = eArr[i + rangeOffset];
                j++;
                i++;
            } else {
                i++;
            }
        }
        int removed2 = rangeLength - j;
        E[] eArr2 = this.array;
        ArraysKt.copyInto((T[]) eArr2, (T[]) eArr2, rangeOffset + j, rangeOffset + rangeLength, this.length);
        E[] eArr3 = this.array;
        int i2 = this.length;
        ListBuilderKt.resetRange(eArr3, i2 - removed2, i2);
        this.length -= removed2;
        return removed2;
    }

    @Metadata(mo11628d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010+\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\t\b\u0002\u0018\u0000*\u0004\b\u0001\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u001d\b\u0016\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00010\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0015\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\fJ\t\u0010\r\u001a\u00020\u000eH\u0002J\b\u0010\u000f\u001a\u00020\u000eH\u0016J\u000e\u0010\u0010\u001a\u00028\u0001H\u0002¢\u0006\u0002\u0010\u0011J\b\u0010\u0012\u001a\u00020\u0006H\u0016J\r\u0010\u0013\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\u0011J\b\u0010\u0014\u001a\u00020\u0006H\u0016J\b\u0010\u0015\u001a\u00020\nH\u0016J\u0015\u0010\u0016\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\fR\u000e\u0010\u0005\u001a\u00020\u0006X\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00010\u0004X\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"}, mo11629d2 = {"Lkotlin/collections/builders/ListBuilder$Itr;", "E", "", "list", "Lkotlin/collections/builders/ListBuilder;", "index", "", "(Lkotlin/collections/builders/ListBuilder;I)V", "lastIndex", "add", "", "element", "(Ljava/lang/Object;)V", "hasNext", "", "hasPrevious", "next", "()Ljava/lang/Object;", "nextIndex", "previous", "previousIndex", "remove", "set", "kotlin-stdlib"}, mo11630k = 1, mo11631mv = {1, 5, 1})
    /* compiled from: ListBuilder.kt */
    private static final class Itr<E> implements ListIterator<E>, KMutableListIterator {
        private int index;
        private int lastIndex = -1;
        private final ListBuilder<E> list;

        public Itr(ListBuilder<E> list2, int index2) {
            Intrinsics.checkNotNullParameter(list2, "list");
            this.list = list2;
            this.index = index2;
        }

        public boolean hasPrevious() {
            return this.index > 0;
        }

        public boolean hasNext() {
            return this.index < this.list.length;
        }

        public int previousIndex() {
            return this.index - 1;
        }

        public int nextIndex() {
            return this.index;
        }

        public E previous() {
            int i = this.index;
            if (i > 0) {
                int i2 = i - 1;
                this.index = i2;
                this.lastIndex = i2;
                return this.list.array[this.list.offset + this.lastIndex];
            }
            throw new NoSuchElementException();
        }

        public E next() {
            if (this.index < this.list.length) {
                int i = this.index;
                this.index = i + 1;
                this.lastIndex = i;
                return this.list.array[this.list.offset + this.lastIndex];
            }
            throw new NoSuchElementException();
        }

        public void set(E element) {
            if (this.lastIndex != -1) {
                this.list.set(this.lastIndex, element);
                return;
            }
            throw new IllegalStateException("Call next() or previous() before replacing element from the iterator.".toString());
        }

        public void add(E element) {
            ListBuilder<E> listBuilder = this.list;
            int i = this.index;
            this.index = i + 1;
            listBuilder.add(i, element);
            this.lastIndex = -1;
        }

        public void remove() {
            if (this.lastIndex != -1) {
                this.list.remove(this.lastIndex);
                this.index = this.lastIndex;
                this.lastIndex = -1;
                return;
            }
            throw new IllegalStateException("Call next() or previous() before removing element from the iterator.".toString());
        }
    }
}
