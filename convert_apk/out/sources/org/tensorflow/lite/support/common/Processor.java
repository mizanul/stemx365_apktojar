package org.tensorflow.lite.support.common;

public interface Processor<T> {
    T process(T t);
}
