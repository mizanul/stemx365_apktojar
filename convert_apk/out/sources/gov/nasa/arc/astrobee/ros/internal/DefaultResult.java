package gov.nasa.arc.astrobee.ros.internal;

import ff_msgs.AckStamped;
import gov.nasa.arc.astrobee.Result;

class DefaultResult implements Result {
    private final String m_message;
    private final Result.Status m_status;

    DefaultResult(AckStamped ack) {
        this.m_status = Result.Status.fromValue(ack.getCompletedStatus().getStatus());
        this.m_message = ack.getMessage();
    }

    public String getMessage() {
        return this.m_message;
    }

    public Result.Status getStatus() {
        return this.m_status;
    }

    public boolean hasSucceeded() {
        return this.m_status == Result.Status.OK;
    }
}
