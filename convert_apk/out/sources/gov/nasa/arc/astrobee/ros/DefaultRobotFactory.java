package gov.nasa.arc.astrobee.ros;

import gov.nasa.arc.astrobee.AstrobeeException;
import gov.nasa.arc.astrobee.Robot;
import gov.nasa.arc.astrobee.RobotFactory;
import gov.nasa.arc.astrobee.ros.internal.DefaultRobot;
import gov.nasa.arc.astrobee.ros.internal.RobotNodeMain;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ros.node.ConnectedNode;
import org.ros.node.DefaultNodeListener;
import org.ros.node.Node;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeListener;

public class DefaultRobotFactory implements RobotFactory {
    /* access modifiers changed from: private */
    public final Log logger;
    /* access modifiers changed from: private */
    public final Condition m_changed;
    /* access modifiers changed from: private */
    public Throwable m_error;
    /* access modifiers changed from: private */
    public final Lock m_lock;
    /* access modifiers changed from: private */
    public RobotNodeMain m_nodeMain;
    private final RobotConfiguration m_robotConfiguration;
    /* access modifiers changed from: private */
    public boolean m_running;
    /* access modifiers changed from: private */
    public CountDownLatch m_shutdownLatch;

    private final class ReadyListener extends DefaultNodeListener {
        private ReadyListener() {
        }

        public void onStart(ConnectedNode connectedNode) {
            DefaultRobotFactory.this.m_lock.lock();
            try {
                boolean unused = DefaultRobotFactory.this.m_running = true;
                Throwable unused2 = DefaultRobotFactory.this.m_error = null;
                DefaultRobotFactory.this.m_changed.signalAll();
            } finally {
                DefaultRobotFactory.this.m_lock.unlock();
            }
        }

        /* JADX INFO: finally extract failed */
        public void onShutdownComplete(Node node) {
            DefaultRobotFactory.this.logger.debug("Node shutdown complete");
            DefaultRobotFactory.this.m_lock.lock();
            try {
                boolean unused = DefaultRobotFactory.this.m_running = false;
                DefaultRobotFactory.this.m_changed.signalAll();
                DefaultRobotFactory.this.m_lock.unlock();
                if (DefaultRobotFactory.this.m_shutdownLatch != null) {
                    DefaultRobotFactory.this.m_shutdownLatch.countDown();
                }
            } catch (Throwable th) {
                DefaultRobotFactory.this.m_lock.unlock();
                throw th;
            }
        }

        /* JADX INFO: finally extract failed */
        public void onError(Node node, Throwable throwable) {
            DefaultRobotFactory.this.logger.error("Node error", throwable);
            DefaultRobotFactory.this.m_lock.lock();
            try {
                boolean unused = DefaultRobotFactory.this.m_running = false;
                Throwable unused2 = DefaultRobotFactory.this.m_error = throwable;
                DefaultRobotFactory.this.m_changed.signalAll();
                DefaultRobotFactory.this.m_lock.unlock();
                DefaultRobotFactory.this.shutdownNode();
            } catch (Throwable th) {
                DefaultRobotFactory.this.m_lock.unlock();
                throw th;
            }
        }
    }

    public DefaultRobotFactory() {
        this(new RobotConfiguration());
    }

    public DefaultRobotFactory(RobotConfiguration configuration) {
        this.logger = LogFactory.getLog(DefaultRobotFactory.class);
        this.m_nodeMain = new RobotNodeMain();
        ReentrantLock reentrantLock = new ReentrantLock();
        this.m_lock = reentrantLock;
        this.m_changed = reentrantLock.newCondition();
        this.m_running = false;
        this.m_error = null;
        this.m_shutdownLatch = null;
        this.m_robotConfiguration = configuration;
        NodeConfiguration nodeConf = configuration.build();
        ArrayList<NodeListener> listeners = new ArrayList<>();
        listeners.add(new ReadyListener());
        NodeExecutorHolder.getExecutor().execute(this.m_nodeMain, nodeConf, listeners);
    }

    /* access modifiers changed from: package-private */
    public void shutdownNode() {
        this.logger.debug("Attempting to shutdown node");
        this.m_shutdownLatch = new CountDownLatch(1);
        NodeExecutorHolder.getExecutor().getScheduledExecutorService().submit(new Runnable() {
            public void run() {
                NodeExecutorHolder.getExecutor().shutdownNodeMain(DefaultRobotFactory.this.m_nodeMain);
            }
        });
        NodeExecutorHolder.getLocalExecutor().submit(new Runnable() {
            public void run() {
                try {
                    if (!DefaultRobotFactory.this.m_shutdownLatch.await(5, TimeUnit.SECONDS)) {
                        DefaultRobotFactory.this.logger.warn("Node did not shut down in a timely manner, forcing shut down.");
                        NodeExecutorHolder.shutdownExecutor(1, TimeUnit.SECONDS);
                    }
                } catch (InterruptedException e) {
                    DefaultRobotFactory.this.logger.debug("Interrupted exception.");
                }
            }
        });
    }

    public void shutdown() {
        shutdownNode();
        NodeExecutorHolder.getLocalExecutor().submit(new Runnable() {
            public void run() {
                NodeExecutorHolder.shutdownExecutor(2, TimeUnit.SECONDS);
            }
        });
    }

    public Robot getRobot() throws AstrobeeException, InterruptedException {
        return getRobot((String) null);
    }

    public Robot getRobot(String name) throws AstrobeeException, InterruptedException {
        if (name == null || name.length() <= 0) {
            this.m_lock.lock();
            while (!this.m_running && this.m_error == null) {
                try {
                    this.m_changed.await();
                } finally {
                    this.m_lock.unlock();
                }
            }
            if (this.m_error == null) {
                return new DefaultRobot(this.m_nodeMain);
            }
            throw new AstrobeeException(this.m_error);
        }
        throw new RuntimeException("Not implemented yet, sorry.");
    }

    public Robot getRobot(long timeout, TimeUnit units) throws AstrobeeException, InterruptedException, TimeoutException {
        return getRobot((String) null);
    }

    public Robot getRobot(String name, long timeout, TimeUnit units) throws AstrobeeException, InterruptedException, TimeoutException {
        if (name == null || name.length() <= 0) {
            this.m_lock.lock();
            while (!this.m_running && this.m_error == null) {
                try {
                    if (!this.m_changed.await(timeout, units)) {
                        throw new TimeoutException("Timed out waiting for Robot");
                    }
                } finally {
                    this.m_lock.unlock();
                }
            }
            if (this.m_error == null) {
                return new DefaultRobot(this.m_nodeMain);
            }
            throw new AstrobeeException(this.m_error);
        }
        throw new RuntimeException("Not implemented yet, sorry.");
    }

    public String getLocalName() {
        return this.m_robotConfiguration.getRobotName();
    }
}
