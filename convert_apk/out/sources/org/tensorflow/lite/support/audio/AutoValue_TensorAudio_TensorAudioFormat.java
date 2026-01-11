package org.tensorflow.lite.support.audio;

import org.tensorflow.lite.support.audio.TensorAudio;

final class AutoValue_TensorAudio_TensorAudioFormat extends TensorAudio.TensorAudioFormat {
    private final int channels;
    private final int sampleRate;

    private AutoValue_TensorAudio_TensorAudioFormat(int channels2, int sampleRate2) {
        this.channels = channels2;
        this.sampleRate = sampleRate2;
    }

    public int getChannels() {
        return this.channels;
    }

    public int getSampleRate() {
        return this.sampleRate;
    }

    public String toString() {
        return "TensorAudioFormat{channels=" + this.channels + ", sampleRate=" + this.sampleRate + "}";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TensorAudio.TensorAudioFormat)) {
            return false;
        }
        TensorAudio.TensorAudioFormat that = (TensorAudio.TensorAudioFormat) o;
        if (this.channels == that.getChannels() && this.sampleRate == that.getSampleRate()) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (((1 * 1000003) ^ this.channels) * 1000003) ^ this.sampleRate;
    }

    static final class Builder extends TensorAudio.TensorAudioFormat.Builder {
        private Integer channels;
        private Integer sampleRate;

        Builder() {
        }

        public TensorAudio.TensorAudioFormat.Builder setChannels(int channels2) {
            this.channels = Integer.valueOf(channels2);
            return this;
        }

        public TensorAudio.TensorAudioFormat.Builder setSampleRate(int sampleRate2) {
            this.sampleRate = Integer.valueOf(sampleRate2);
            return this;
        }

        /* access modifiers changed from: package-private */
        public TensorAudio.TensorAudioFormat autoBuild() {
            String missing = "";
            if (this.channels == null) {
                missing = missing + " channels";
            }
            if (this.sampleRate == null) {
                missing = missing + " sampleRate";
            }
            if (missing.isEmpty()) {
                return new AutoValue_TensorAudio_TensorAudioFormat(this.channels.intValue(), this.sampleRate.intValue());
            }
            throw new IllegalStateException("Missing required properties:" + missing);
        }
    }
}
