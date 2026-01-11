package org.tensorflow.lite.task.processor;

import java.nio.ByteBuffer;

final class AutoValue_NearestNeighbor extends NearestNeighbor {
    private final float distance;
    private final ByteBuffer metadata;

    AutoValue_NearestNeighbor(ByteBuffer metadata2, float distance2) {
        if (metadata2 != null) {
            this.metadata = metadata2;
            this.distance = distance2;
            return;
        }
        throw new NullPointerException("Null metadata");
    }

    public ByteBuffer getMetadata() {
        return this.metadata;
    }

    public float getDistance() {
        return this.distance;
    }

    public String toString() {
        return "NearestNeighbor{metadata=" + this.metadata + ", distance=" + this.distance + "}";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof NearestNeighbor)) {
            return false;
        }
        NearestNeighbor that = (NearestNeighbor) o;
        if (!this.metadata.equals(that.getMetadata()) || Float.floatToIntBits(this.distance) != Float.floatToIntBits(that.getDistance())) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (((1 * 1000003) ^ this.metadata.hashCode()) * 1000003) ^ Float.floatToIntBits(this.distance);
    }
}
