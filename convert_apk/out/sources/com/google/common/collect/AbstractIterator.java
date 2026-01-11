package com.google.common.collect;

import com.google.common.base.Preconditions;
import java.util.NoSuchElementException;

public abstract class AbstractIterator<T> extends UnmodifiableIterator<T> {
    private T next;
    private State state = State.NOT_READY;

    private enum State {
        READY,
        NOT_READY,
        DONE,
        FAILED
    }

    /* access modifiers changed from: protected */
    public abstract T computeNext();

    protected AbstractIterator() {
    }

    /* access modifiers changed from: protected */
    public final T endOfData() {
        this.state = State.DONE;
        return null;
    }

    /* renamed from: com.google.common.collect.AbstractIterator$1 */
    static /* synthetic */ class C04241 {
        static final /* synthetic */ int[] $SwitchMap$com$google$common$collect$AbstractIterator$State;

        static {
            int[] iArr = new int[State.values().length];
            $SwitchMap$com$google$common$collect$AbstractIterator$State = iArr;
            try {
                iArr[State.DONE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$google$common$collect$AbstractIterator$State[State.READY.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    public final boolean hasNext() {
        Preconditions.checkState(this.state != State.FAILED);
        int i = C04241.$SwitchMap$com$google$common$collect$AbstractIterator$State[this.state.ordinal()];
        if (i == 1) {
            return false;
        }
        if (i != 2) {
            return tryToComputeNext();
        }
        return true;
    }

    private boolean tryToComputeNext() {
        this.state = State.FAILED;
        this.next = computeNext();
        if (this.state == State.DONE) {
            return false;
        }
        this.state = State.READY;
        return true;
    }

    public final T next() {
        if (hasNext()) {
            this.state = State.NOT_READY;
            return this.next;
        }
        throw new NoSuchElementException();
    }

    public final T peek() {
        if (hasNext()) {
            return this.next;
        }
        throw new NoSuchElementException();
    }
}
