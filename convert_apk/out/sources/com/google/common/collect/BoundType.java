package com.google.common.collect;

public enum BoundType {
    OPEN,
    CLOSED;

    static BoundType forBoolean(boolean inclusive) {
        return inclusive ? CLOSED : OPEN;
    }
}
