package gov.nasa.arc.astrobee;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public interface PendingResult {
    Result getResult() throws AstrobeeException, InterruptedException;

    Result getResult(long j, TimeUnit timeUnit) throws AstrobeeException, InterruptedException, TimeoutException;

    Status getStatus();

    boolean isFinished();

    public enum Status {
        QUEUED(0),
        EXECUTING(1),
        REQUEUED(2),
        COMPLETED(3);
        
        private final byte m_value;

        private Status(int value) {
            this.m_value = (byte) value;
        }

        public byte getValue() {
            return this.m_value;
        }

        public static Status fromValue(byte value) {
            for (Status s : values()) {
                if (s.m_value == value) {
                    return s;
                }
            }
            throw new IllegalArgumentException("No such Status with that value");
        }

        public String toString() {
            return name() + "(" + this.m_value + ")";
        }
    }
}
