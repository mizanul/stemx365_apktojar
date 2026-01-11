package org.tensorflow.lite.task.vision.core;

import java.util.Arrays;
import org.tensorflow.lite.task.vision.core.BaseVisionTaskApi;

final class AutoValue_BaseVisionTaskApi_FrameBufferData extends BaseVisionTaskApi.FrameBufferData {
    private final byte[] byteArray;
    private final long byteArrayHandle;
    private final long frameBufferHandle;

    AutoValue_BaseVisionTaskApi_FrameBufferData(long frameBufferHandle2, long byteArrayHandle2, byte[] byteArray2) {
        this.frameBufferHandle = frameBufferHandle2;
        this.byteArrayHandle = byteArrayHandle2;
        if (byteArray2 != null) {
            this.byteArray = byteArray2;
            return;
        }
        throw new NullPointerException("Null byteArray");
    }

    /* access modifiers changed from: package-private */
    public long getFrameBufferHandle() {
        return this.frameBufferHandle;
    }

    /* access modifiers changed from: package-private */
    public long getByteArrayHandle() {
        return this.byteArrayHandle;
    }

    /* access modifiers changed from: package-private */
    public byte[] getByteArray() {
        return this.byteArray;
    }

    public String toString() {
        return "FrameBufferData{frameBufferHandle=" + this.frameBufferHandle + ", byteArrayHandle=" + this.byteArrayHandle + ", byteArray=" + Arrays.toString(this.byteArray) + "}";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof BaseVisionTaskApi.FrameBufferData)) {
            return false;
        }
        BaseVisionTaskApi.FrameBufferData that = (BaseVisionTaskApi.FrameBufferData) o;
        if (this.frameBufferHandle == that.getFrameBufferHandle() && this.byteArrayHandle == that.getByteArrayHandle()) {
            if (Arrays.equals(this.byteArray, that instanceof AutoValue_BaseVisionTaskApi_FrameBufferData ? ((AutoValue_BaseVisionTaskApi_FrameBufferData) that).byteArray : that.getByteArray())) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        long j = this.frameBufferHandle;
        long j2 = this.byteArrayHandle;
        return (((((1 * 1000003) ^ ((int) (j ^ (j >>> 32)))) * 1000003) ^ ((int) (j2 ^ (j2 >>> 32)))) * 1000003) ^ Arrays.hashCode(this.byteArray);
    }
}
