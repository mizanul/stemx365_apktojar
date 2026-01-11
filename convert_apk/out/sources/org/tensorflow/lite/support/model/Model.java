package org.tensorflow.lite.support.model;

import android.content.Context;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.util.Map;
import org.tensorflow.lite.InterpreterApi;
import org.tensorflow.lite.Tensor;
import org.tensorflow.lite.support.common.FileUtil;
import org.tensorflow.lite.support.common.internal.SupportPreconditions;

public class Model {
    private final MappedByteBuffer byteModel;
    private final GpuDelegateProxy gpuDelegateProxy;
    private final InterpreterApi interpreter;
    private final String modelPath;

    public enum Device {
        CPU,
        NNAPI,
        GPU
    }

    public static class Options {
        /* access modifiers changed from: private */
        public final Device device;
        /* access modifiers changed from: private */
        public final int numThreads;
        /* access modifiers changed from: private */
        public final InterpreterApi.Options.TfLiteRuntime tfLiteRuntime;

        /* synthetic */ Options(Builder x0, C10811 x1) {
            this(x0);
        }

        public static class Builder {
            /* access modifiers changed from: private */
            public Device device = Device.CPU;
            /* access modifiers changed from: private */
            public int numThreads = 1;
            /* access modifiers changed from: private */
            public InterpreterApi.Options.TfLiteRuntime tfLiteRuntime;

            public Builder setDevice(Device device2) {
                this.device = device2;
                return this;
            }

            public Builder setNumThreads(int numThreads2) {
                this.numThreads = numThreads2;
                return this;
            }

            public Builder setTfLiteRuntime(InterpreterApi.Options.TfLiteRuntime tfLiteRuntime2) {
                this.tfLiteRuntime = tfLiteRuntime2;
                return this;
            }

            public Options build() {
                return new Options(this, (C10811) null);
            }
        }

        private Options(Builder builder) {
            this.device = builder.device;
            this.numThreads = builder.numThreads;
            this.tfLiteRuntime = builder.tfLiteRuntime;
        }
    }

    @Deprecated
    public static class Builder {
        private final MappedByteBuffer byteModel;
        private Device device = Device.CPU;
        private final String modelPath;
        private int numThreads = 1;

        public Builder(Context context, String modelPath2) throws IOException {
            this.modelPath = modelPath2;
            this.byteModel = FileUtil.loadMappedFile(context, modelPath2);
        }

        public Builder setDevice(Device device2) {
            this.device = device2;
            return this;
        }

        public Builder setNumThreads(int numThreads2) {
            this.numThreads = numThreads2;
            return this;
        }

        public Model build() {
            return Model.createModel(this.byteModel, this.modelPath, new Options.Builder().setNumThreads(this.numThreads).setDevice(this.device).build());
        }
    }

    public static Model createModel(Context context, String modelPath2) throws IOException {
        return createModel(context, modelPath2, new Options.Builder().build());
    }

    public static Model createModel(Context context, String modelPath2, Options options) throws IOException {
        SupportPreconditions.checkNotEmpty(modelPath2, "Model path in the asset folder cannot be empty.");
        return createModel(FileUtil.loadMappedFile(context, modelPath2), modelPath2, options);
    }

    public static Model createModel(MappedByteBuffer byteModel2, String modelPath2, Options options) {
        InterpreterApi.Options interpreterOptions = new InterpreterApi.Options();
        GpuDelegateProxy gpuDelegateProxy2 = null;
        int i = C10811.$SwitchMap$org$tensorflow$lite$support$model$Model$Device[options.device.ordinal()];
        boolean z = true;
        if (i == 1) {
            interpreterOptions.setUseNNAPI(true);
        } else if (i == 2) {
            gpuDelegateProxy2 = GpuDelegateProxy.maybeNewInstance();
            if (gpuDelegateProxy2 == null) {
                z = false;
            }
            SupportPreconditions.checkArgument(z, "Cannot inference with GPU. Did you add \"tensorflow-lite-gpu\" as dependency?");
            interpreterOptions.addDelegate(gpuDelegateProxy2);
        }
        interpreterOptions.setNumThreads(options.numThreads);
        if (options.tfLiteRuntime != null) {
            interpreterOptions.setRuntime(options.tfLiteRuntime);
        }
        return new Model(modelPath2, byteModel2, InterpreterApi.create(byteModel2, interpreterOptions), gpuDelegateProxy2);
    }

    /* renamed from: org.tensorflow.lite.support.model.Model$1 */
    static /* synthetic */ class C10811 {
        static final /* synthetic */ int[] $SwitchMap$org$tensorflow$lite$support$model$Model$Device;

        static {
            int[] iArr = new int[Device.values().length];
            $SwitchMap$org$tensorflow$lite$support$model$Model$Device = iArr;
            try {
                iArr[Device.NNAPI.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$org$tensorflow$lite$support$model$Model$Device[Device.GPU.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$org$tensorflow$lite$support$model$Model$Device[Device.CPU.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    public MappedByteBuffer getData() {
        return this.byteModel;
    }

    public String getPath() {
        return this.modelPath;
    }

    public Tensor getInputTensor(int inputIndex) {
        return this.interpreter.getInputTensor(inputIndex);
    }

    public Tensor getOutputTensor(int outputIndex) {
        return this.interpreter.getOutputTensor(outputIndex);
    }

    public int[] getOutputTensorShape(int outputIndex) {
        return this.interpreter.getOutputTensor(outputIndex).shape();
    }

    public void run(Object[] inputs, Map<Integer, Object> outputs) {
        this.interpreter.runForMultipleInputsOutputs(inputs, outputs);
    }

    public void close() {
        InterpreterApi interpreterApi = this.interpreter;
        if (interpreterApi != null) {
            interpreterApi.close();
        }
        GpuDelegateProxy gpuDelegateProxy2 = this.gpuDelegateProxy;
        if (gpuDelegateProxy2 != null) {
            gpuDelegateProxy2.close();
        }
    }

    private Model(String modelPath2, MappedByteBuffer byteModel2, InterpreterApi interpreter2, GpuDelegateProxy gpuDelegateProxy2) {
        this.modelPath = modelPath2;
        this.byteModel = byteModel2;
        this.interpreter = interpreter2;
        this.gpuDelegateProxy = gpuDelegateProxy2;
    }
}
