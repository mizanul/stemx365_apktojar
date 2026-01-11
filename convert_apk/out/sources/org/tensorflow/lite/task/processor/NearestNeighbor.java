package org.tensorflow.lite.task.processor;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public abstract class NearestNeighbor {
    public abstract float getDistance();

    public abstract ByteBuffer getMetadata();

    static NearestNeighbor create(byte[] metadataArray, float distance) {
        ByteBuffer metadata = ByteBuffer.wrap(metadataArray);
        metadata.order(ByteOrder.nativeOrder());
        return new AutoValue_NearestNeighbor(metadata, distance);
    }
}
