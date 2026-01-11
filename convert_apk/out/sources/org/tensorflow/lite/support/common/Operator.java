package org.tensorflow.lite.support.common;

public interface Operator<T> {
    T apply(T t);
}
