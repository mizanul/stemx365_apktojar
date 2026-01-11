package com.google.common.eventbus;

public class DeadEvent {
    private final Object event;
    private final Object source;

    public DeadEvent(Object source2, Object event2) {
        this.source = source2;
        this.event = event2;
    }

    public Object getSource() {
        return this.source;
    }

    public Object getEvent() {
        return this.event;
    }
}
