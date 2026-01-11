package gov.nasa.arc.astrobee.ros;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ros.node.DefaultNodeMainExecutor;
import org.ros.node.NodeMainExecutor;

public final class NodeExecutorHolder {
    private static final Log logger = LogFactory.getLog(NodeExecutorHolder.class);
    private static NodeMainExecutor s_executor = null;
    private static ExecutorService s_local = null;

    static final class NamedThreadFactory implements ThreadFactory {
        private boolean m_daemon;
        private final ThreadGroup m_group;
        private final String m_name;

        NamedThreadFactory(String name, boolean daemon) {
            ThreadGroup threadGroup;
            SecurityManager s = System.getSecurityManager();
            if (s != null) {
                threadGroup = s.getThreadGroup();
            } else {
                threadGroup = Thread.currentThread().getThreadGroup();
            }
            this.m_group = threadGroup;
            this.m_name = name;
            this.m_daemon = daemon;
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(this.m_group, r, this.m_name, 0);
            t.setDaemon(this.m_daemon);
            if (t.getPriority() != 5) {
                t.setPriority(5);
            }
            return t;
        }
    }

    public static NodeMainExecutor getExecutor() {
        if (s_local == null) {
            s_local = Executors.newSingleThreadExecutor(new NamedThreadFactory("NodeMain Local", true));
        }
        if (s_executor == null) {
            s_executor = DefaultNodeMainExecutor.newDefault();
        }
        return s_executor;
    }

    public static ExecutorService getLocalExecutor() {
        return s_local;
    }

    public static void shutdownExecutor(long time, TimeUnit units) {
        if (s_executor != null) {
            logger.info("Attempting to shutdown ROS executor service.");
            s_executor.getScheduledExecutorService().shutdown();
            try {
                Log log = logger;
                log.info("Waiting " + time + " " + units + " for termination");
                if (!s_executor.getScheduledExecutorService().awaitTermination(time, units)) {
                    logger.warn("ROS did not shut down in a timely manner, forcing shut down.");
                    s_executor.getScheduledExecutorService().shutdownNow();
                }
            } catch (InterruptedException e) {
            }
            s_executor = null;
        }
    }
}
