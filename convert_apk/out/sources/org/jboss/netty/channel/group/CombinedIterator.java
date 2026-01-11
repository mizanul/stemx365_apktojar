package org.jboss.netty.channel.group;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.apache.xmlrpc.serializer.I1Serializer;
import org.apache.xmlrpc.serializer.I2Serializer;

final class CombinedIterator<E> implements Iterator<E> {
    private Iterator<E> currentIterator;

    /* renamed from: i1 */
    private final Iterator<E> f118i1;

    /* renamed from: i2 */
    private final Iterator<E> f119i2;

    CombinedIterator(Iterator<E> i1, Iterator<E> i2) {
        if (i1 == null) {
            throw new NullPointerException(I1Serializer.I1_TAG);
        } else if (i2 != null) {
            this.f118i1 = i1;
            this.f119i2 = i2;
            this.currentIterator = i1;
        } else {
            throw new NullPointerException(I2Serializer.I2_TAG);
        }
    }

    public boolean hasNext() {
        if (this.currentIterator.hasNext()) {
            return true;
        }
        if (this.currentIterator != this.f118i1) {
            return false;
        }
        this.currentIterator = this.f119i2;
        return hasNext();
    }

    public E next() {
        try {
            return this.currentIterator.next();
        } catch (NoSuchElementException e) {
            if (this.currentIterator == this.f118i1) {
                this.currentIterator = this.f119i2;
                return next();
            }
            throw e;
        }
    }

    public void remove() {
        this.currentIterator.remove();
    }
}
