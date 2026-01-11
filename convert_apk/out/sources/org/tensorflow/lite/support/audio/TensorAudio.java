package org.tensorflow.lite.support.audio;

import android.media.AudioFormat;
import android.media.AudioRecord;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.audio.AutoValue_TensorAudio_TensorAudioFormat;
import org.tensorflow.lite.support.common.internal.SupportPreconditions;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

public class TensorAudio {
    private static final String TAG = TensorAudio.class.getSimpleName();
    private final FloatRingBuffer buffer;
    private final TensorAudioFormat format;

    public static TensorAudio create(TensorAudioFormat format2, int sampleCounts) {
        return new TensorAudio(format2, sampleCounts);
    }

    public static TensorAudio create(AudioFormat format2, int sampleCounts) {
        return new TensorAudio(TensorAudioFormat.create(format2), sampleCounts);
    }

    public static abstract class TensorAudioFormat {
        private static final int DEFAULT_CHANNELS = 1;

        public abstract int getChannels();

        public abstract int getSampleRate();

        public static TensorAudioFormat create(AudioFormat format) {
            return builder().setChannels(format.getChannelCount()).setSampleRate(format.getSampleRate()).build();
        }

        public static Builder builder() {
            return new AutoValue_TensorAudio_TensorAudioFormat.Builder().setChannels(1);
        }

        public static abstract class Builder {
            /* access modifiers changed from: package-private */
            public abstract TensorAudioFormat autoBuild();

            public abstract Builder setChannels(int i);

            public abstract Builder setSampleRate(int i);

            public TensorAudioFormat build() {
                TensorAudioFormat format = autoBuild();
                boolean z = true;
                SupportPreconditions.checkArgument(format.getChannels() > 0, "Number of channels should be greater than 0");
                if (format.getSampleRate() <= 0) {
                    z = false;
                }
                SupportPreconditions.checkArgument(z, "Sample rate should be greater than 0");
                return format;
            }
        }
    }

    public void load(float[] src2) {
        load(src2, 0, src2.length);
    }

    public void load(float[] src2, int offsetInFloat, int sizeInFloat) {
        SupportPreconditions.checkArgument(sizeInFloat % this.format.getChannels() == 0, String.format("Size (%d) needs to be a multiplier of the number of channels (%d)", new Object[]{Integer.valueOf(sizeInFloat), Integer.valueOf(this.format.getChannels())}));
        this.buffer.load(src2, offsetInFloat, sizeInFloat);
    }

    public void load(short[] src2) {
        load(src2, 0, src2.length);
    }

    public void load(short[] src2, int offsetInShort, int sizeInShort) {
        SupportPreconditions.checkArgument(offsetInShort + sizeInShort <= src2.length, String.format("Index out of range. offset (%d) + size (%d) should <= newData.length (%d)", new Object[]{Integer.valueOf(offsetInShort), Integer.valueOf(sizeInShort), Integer.valueOf(src2.length)}));
        float[] floatData = new float[sizeInShort];
        for (int i = 0; i < sizeInShort; i++) {
            floatData[i] = (((float) src2[i + offsetInShort]) * 1.0f) / 32767.0f;
        }
        load(floatData);
    }

    public int load(AudioRecord record) {
        int loadedValues;
        SupportPreconditions.checkArgument(this.format.equals(TensorAudioFormat.create(record.getFormat())), "Incompatible audio format.");
        if (record.getAudioFormat() == 4) {
            float[] newData = new float[(record.getChannelCount() * record.getBufferSizeInFrames())];
            loadedValues = record.read(newData, 0, newData.length, 1);
            if (loadedValues > 0) {
                load(newData, 0, loadedValues);
                return loadedValues;
            }
        } else if (record.getAudioFormat() == 2) {
            short[] newData2 = new short[(record.getChannelCount() * record.getBufferSizeInFrames())];
            loadedValues = record.read(newData2, 0, newData2.length, 1);
            if (loadedValues > 0) {
                load(newData2, 0, loadedValues);
                return loadedValues;
            }
        } else {
            throw new IllegalArgumentException("Unsupported encoding. Requires ENCODING_PCM_16BIT or ENCODING_PCM_FLOAT.");
        }
        if (loadedValues == -6) {
            throw new IllegalStateException("AudioRecord.ERROR_DEAD_OBJECT");
        } else if (loadedValues == -3) {
            throw new IllegalStateException("AudioRecord.ERROR_INVALID_OPERATION");
        } else if (loadedValues == -2) {
            throw new IllegalStateException("AudioRecord.ERROR_BAD_VALUE");
        } else if (loadedValues != -1) {
            return 0;
        } else {
            throw new IllegalStateException("AudioRecord.ERROR");
        }
    }

    public TensorBuffer getTensorBuffer() {
        ByteBuffer byteBuffer = this.buffer.getBuffer();
        TensorBuffer tensorBuffer = TensorBuffer.createFixedSize(new int[]{1, byteBuffer.asFloatBuffer().limit()}, DataType.FLOAT32);
        tensorBuffer.loadBuffer(byteBuffer);
        return tensorBuffer;
    }

    public TensorAudioFormat getFormat() {
        return this.format;
    }

    private TensorAudio(TensorAudioFormat format2, int sampleCounts) {
        this.format = format2;
        this.buffer = new FloatRingBuffer(format2.getChannels() * sampleCounts);
    }

    private static class FloatRingBuffer {
        private final float[] buffer;
        private int nextIndex = 0;

        public FloatRingBuffer(int flatSize) {
            this.buffer = new float[flatSize];
        }

        public void load(float[] newData) {
            load(newData, 0, newData.length);
        }

        public void load(float[] newData, int offset, int size) {
            SupportPreconditions.checkArgument(offset + size <= newData.length, String.format("Index out of range. offset (%d) + size (%d) should <= newData.length (%d)", new Object[]{Integer.valueOf(offset), Integer.valueOf(size), Integer.valueOf(newData.length)}));
            float[] fArr = this.buffer;
            if (size > fArr.length) {
                offset += size - fArr.length;
                size = fArr.length;
            }
            int i = this.nextIndex;
            int i2 = i + size;
            float[] fArr2 = this.buffer;
            if (i2 < fArr2.length) {
                System.arraycopy(newData, offset, fArr2, i, size);
            } else {
                int firstChunkSize = fArr2.length - i;
                System.arraycopy(newData, offset, fArr2, i, firstChunkSize);
                System.arraycopy(newData, offset + firstChunkSize, this.buffer, 0, size - firstChunkSize);
            }
            this.nextIndex = (this.nextIndex + size) % this.buffer.length;
        }

        public ByteBuffer getBuffer() {
            ByteBuffer byteBuffer = ByteBuffer.allocate(DataType.FLOAT32.byteSize() * this.buffer.length);
            byteBuffer.order(ByteOrder.nativeOrder());
            FloatBuffer result = byteBuffer.asFloatBuffer();
            float[] fArr = this.buffer;
            int i = this.nextIndex;
            result.put(fArr, i, fArr.length - i);
            result.put(this.buffer, 0, this.nextIndex);
            byteBuffer.rewind();
            return byteBuffer;
        }

        public int getCapacity() {
            return this.buffer.length;
        }
    }
}
