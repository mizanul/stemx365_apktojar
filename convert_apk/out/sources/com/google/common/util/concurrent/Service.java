package com.google.common.util.concurrent;

public interface Service {

    public enum State {
        NEW,
        STARTING,
        RUNNING,
        STOPPING,
        TERMINATED,
        FAILED
    }

    boolean isRunning();

    ListenableFuture<State> start();

    State startAndWait();

    State state();

    ListenableFuture<State> stop();

    State stopAndWait();
}
