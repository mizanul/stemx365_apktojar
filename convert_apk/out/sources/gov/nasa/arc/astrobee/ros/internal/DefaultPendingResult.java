package gov.nasa.arc.astrobee.ros.internal;

import ff_msgs.AckStamped;
import ff_msgs.CommandStamped;
import gov.nasa.arc.astrobee.AstrobeeException;
import gov.nasa.arc.astrobee.PendingResult;
import gov.nasa.arc.astrobee.Result;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

class DefaultPendingResult implements PendingResult {
    private final CommandStamped m_cmd;
    private AstrobeeException m_exception = null;
    private final CountDownLatch m_latch = new CountDownLatch(1);
    private DefaultResult m_result = null;
    private PendingResult.Status m_status = PendingResult.Status.EXECUTING;
    private final Object m_sync = new Object();

    DefaultPendingResult(CommandStamped cmd) {
        this.m_cmd = cmd;
    }

    /* access modifiers changed from: package-private */
    public CommandStamped getCommand() {
        return this.m_cmd;
    }

    public synchronized boolean isFinished() {
        return this.m_status == PendingResult.Status.COMPLETED;
    }

    public synchronized PendingResult.Status getStatus() {
        return this.m_status;
    }

    /* access modifiers changed from: package-private */
    public void update(AckStamped ack) {
        setStatus(PendingResult.Status.fromValue(ack.getStatus().getStatus()));
        if (this.m_status == PendingResult.Status.COMPLETED) {
            setResult(new DefaultResult(ack));
        }
    }

    /* access modifiers changed from: package-private */
    public synchronized void setStatus(PendingResult.Status status) {
        this.m_status = status;
    }

    /* access modifiers changed from: package-private */
    public void setResult(DefaultResult r) {
        synchronized (this.m_sync) {
            if (this.m_latch.getCount() != 0) {
                this.m_result = r;
                this.m_latch.countDown();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void setThrowable(Throwable t) {
        synchronized (this.m_sync) {
            if (this.m_latch.getCount() != 0) {
                if (t instanceof AstrobeeException) {
                    this.m_exception = (AstrobeeException) t;
                } else {
                    this.m_exception = new AstrobeeException(t);
                }
                this.m_latch.countDown();
            }
        }
    }

    public Result getResult() throws AstrobeeException, InterruptedException {
        this.m_latch.await();
        AstrobeeException astrobeeException = this.m_exception;
        if (astrobeeException == null) {
            return this.m_result;
        }
        throw astrobeeException;
    }

    public Result getResult(long timeout, TimeUnit unit) throws AstrobeeException, InterruptedException, TimeoutException {
        this.m_latch.await(timeout, unit);
        AstrobeeException astrobeeException = this.m_exception;
        if (astrobeeException == null) {
            return this.m_result;
        }
        throw astrobeeException;
    }
}
