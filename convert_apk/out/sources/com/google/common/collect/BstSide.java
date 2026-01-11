package com.google.common.collect;

enum BstSide {
    LEFT {
        public BstSide other() {
            return RIGHT;
        }
    },
    RIGHT {
        public BstSide other() {
            return LEFT;
        }
    };

    /* access modifiers changed from: package-private */
    public abstract BstSide other();
}
