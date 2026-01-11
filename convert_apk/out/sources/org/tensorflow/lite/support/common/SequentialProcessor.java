package org.tensorflow.lite.support.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.tensorflow.lite.support.common.internal.SupportPreconditions;

public class SequentialProcessor<T> implements Processor<T> {
    protected final Map<String, List<Integer>> operatorIndex;
    protected final List<Operator<T>> operatorList;

    protected SequentialProcessor(Builder<T> builder) {
        this.operatorList = builder.operatorList;
        this.operatorIndex = Collections.unmodifiableMap(builder.operatorIndex);
    }

    public T process(T x) {
        for (Operator<T> op : this.operatorList) {
            x = op.apply(x);
        }
        return x;
    }

    protected static class Builder<T> {
        /* access modifiers changed from: private */
        public final Map<String, List<Integer>> operatorIndex = new HashMap();
        /* access modifiers changed from: private */
        public final List<Operator<T>> operatorList = new ArrayList();

        protected Builder() {
        }

        public Builder<T> add(Operator<T> op) {
            SupportPreconditions.checkNotNull(op, "Adding null Op is illegal.");
            this.operatorList.add(op);
            String operatorName = op.getClass().getName();
            if (!this.operatorIndex.containsKey(operatorName)) {
                this.operatorIndex.put(operatorName, new ArrayList());
            }
            this.operatorIndex.get(operatorName).add(Integer.valueOf(this.operatorList.size() - 1));
            return this;
        }

        public SequentialProcessor<T> build() {
            return new SequentialProcessor<>(this);
        }
    }
}
