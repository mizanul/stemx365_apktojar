package org.tensorflow.lite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public interface InterpreterApi extends AutoCloseable {
    void allocateTensors();

    void close();

    int getInputIndex(String str);

    Tensor getInputTensor(int i);

    int getInputTensorCount();

    Long getLastNativeInferenceDurationNanoseconds();

    int getOutputIndex(String str);

    Tensor getOutputTensor(int i);

    int getOutputTensorCount();

    void resizeInput(int i, int[] iArr);

    void resizeInput(int i, int[] iArr, boolean z);

    void run(Object obj, Object obj2);

    void runForMultipleInputsOutputs(Object[] objArr, Map<Integer, Object> map);

    public static class Options {
        Boolean allowCancellation;
        final List<Delegate> delegates;
        int numThreads;
        Boolean useNNAPI;

        public Options() {
            this.numThreads = -1;
            this.delegates = new ArrayList();
        }

        public Options(Options other) {
            this.numThreads = -1;
            this.numThreads = other.numThreads;
            this.useNNAPI = other.useNNAPI;
            this.allowCancellation = other.allowCancellation;
            this.delegates = new ArrayList(other.delegates);
        }

        public Options setNumThreads(int numThreads2) {
            this.numThreads = numThreads2;
            return this;
        }

        public int getNumThreads() {
            return this.numThreads;
        }

        public Options setUseNNAPI(boolean useNNAPI2) {
            this.useNNAPI = Boolean.valueOf(useNNAPI2);
            return this;
        }

        public boolean getUseNNAPI() {
            Boolean bool = this.useNNAPI;
            return bool != null && bool.booleanValue();
        }

        public Options setCancellable(boolean allow) {
            this.allowCancellation = Boolean.valueOf(allow);
            return this;
        }

        public boolean isCancellable() {
            Boolean bool = this.allowCancellation;
            return bool != null && bool.booleanValue();
        }

        public Options addDelegate(Delegate delegate) {
            this.delegates.add(delegate);
            return this;
        }

        public List<Delegate> getDelegates() {
            return Collections.unmodifiableList(this.delegates);
        }
    }
}
