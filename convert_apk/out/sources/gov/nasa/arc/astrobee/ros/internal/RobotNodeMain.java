package gov.nasa.arc.astrobee.ros.internal;

import ff_msgs.AckStamped;
import ff_msgs.CommandStamped;
import ff_msgs.EkfState;
import gov.nasa.arc.astrobee.AstrobeeRuntimeException;
import gov.nasa.arc.astrobee.Kinematics;
import gov.nasa.arc.astrobee.PendingResult;
import gov.nasa.arc.astrobee.ros.DefaultKinematics;
import gov.nasa.arc.astrobee.ros.internal.util.Stringer;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ros.internal.node.topic.SubscriberIdentifier;
import org.ros.message.MessageFactory;
import org.ros.message.MessageListener;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.Node;
import org.ros.node.topic.DefaultPublisherListener;
import org.ros.node.topic.Publisher;

public class RobotNodeMain extends AbstractNodeMain implements MessageListener<AckStamped> {
    private final Log logger = LogFactory.getLog(RobotNodeMain.class);
    private Publisher<CommandStamped> m_cmdPublisher = null;
    /* access modifiers changed from: private */
    public DefaultKinematics m_kinematics = new DefaultKinematics();
    /* access modifiers changed from: private */
    public final Object m_kinematics_lock = new Object();
    private ConnectedNode m_node = null;
    private final Map<String, DefaultPendingResult> m_pending = new HashMap();
    /* access modifiers changed from: private */
    public final Queue<CommandStamped> m_queue = new LinkedBlockingDeque();
    /* access modifiers changed from: private */
    public boolean m_ready = false;

    public synchronized void onStart(ConnectedNode connectedNode) {
        this.m_node = connectedNode;
        Publisher<CommandStamped> newPublisher = connectedNode.newPublisher("command", CommandStamped._TYPE);
        this.m_cmdPublisher = newPublisher;
        newPublisher.addListener(new DefaultPublisherListener<CommandStamped>() {
            public void onNewSubscriber(Publisher<CommandStamped> publisher, SubscriberIdentifier subscriberIdentifier) {
                while (!RobotNodeMain.this.m_queue.isEmpty()) {
                    publisher.publish((CommandStamped) RobotNodeMain.this.m_queue.poll());
                }
                synchronized (RobotNodeMain.this) {
                    boolean unused = RobotNodeMain.this.m_ready = true;
                }
            }
        });
        connectedNode.newSubscriber("mgt/ack", AckStamped._TYPE).addMessageListener(this);
        connectedNode.newSubscriber("gnc/ekf", EkfState._TYPE).addMessageListener(new MessageListener<EkfState>() {
            public void onNewMessage(EkfState ekfState) {
                synchronized (RobotNodeMain.this.m_kinematics_lock) {
                    DefaultKinematics unused = RobotNodeMain.this.m_kinematics = new DefaultKinematics(ekfState);
                }
            }
        });
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0066, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void onNewMessage(ff_msgs.AckStamped r5) {
        /*
            r4 = this;
            monitor-enter(r4)
            java.util.Map<java.lang.String, gov.nasa.arc.astrobee.ros.internal.DefaultPendingResult> r0 = r4.m_pending     // Catch:{ all -> 0x0067 }
            java.lang.String r1 = r5.getCmdId()     // Catch:{ all -> 0x0067 }
            boolean r0 = r0.containsKey(r1)     // Catch:{ all -> 0x0067 }
            if (r0 != 0) goto L_0x0029
            org.apache.commons.logging.Log r0 = r4.logger     // Catch:{ all -> 0x0067 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0067 }
            r1.<init>()     // Catch:{ all -> 0x0067 }
            java.lang.String r2 = "Unmatched Ack received: "
            r1.append(r2)     // Catch:{ all -> 0x0067 }
            java.lang.String r2 = gov.nasa.arc.astrobee.ros.internal.util.Stringer.toString((ff_msgs.AckStamped) r5)     // Catch:{ all -> 0x0067 }
            r1.append(r2)     // Catch:{ all -> 0x0067 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x0067 }
            r0.warn(r1)     // Catch:{ all -> 0x0067 }
            monitor-exit(r4)
            return
        L_0x0029:
            java.util.Map<java.lang.String, gov.nasa.arc.astrobee.ros.internal.DefaultPendingResult> r0 = r4.m_pending     // Catch:{ all -> 0x0067 }
            java.lang.String r1 = r5.getCmdId()     // Catch:{ all -> 0x0067 }
            java.lang.Object r0 = r0.get(r1)     // Catch:{ all -> 0x0067 }
            gov.nasa.arc.astrobee.ros.internal.DefaultPendingResult r0 = (gov.nasa.arc.astrobee.ros.internal.DefaultPendingResult) r0     // Catch:{ all -> 0x0067 }
            org.apache.commons.logging.Log r1 = r4.logger     // Catch:{ all -> 0x0067 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0067 }
            r2.<init>()     // Catch:{ all -> 0x0067 }
            java.lang.String r3 = "Updating status for "
            r2.append(r3)     // Catch:{ all -> 0x0067 }
            ff_msgs.CommandStamped r3 = r0.getCommand()     // Catch:{ all -> 0x0067 }
            java.lang.String r3 = gov.nasa.arc.astrobee.ros.internal.util.Stringer.toString((ff_msgs.CommandStamped) r3)     // Catch:{ all -> 0x0067 }
            r2.append(r3)     // Catch:{ all -> 0x0067 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x0067 }
            r1.debug(r2)     // Catch:{ all -> 0x0067 }
            r0.update(r5)     // Catch:{ all -> 0x0067 }
            boolean r1 = r0.isFinished()     // Catch:{ all -> 0x0067 }
            if (r1 == 0) goto L_0x0065
            java.util.Map<java.lang.String, gov.nasa.arc.astrobee.ros.internal.DefaultPendingResult> r1 = r4.m_pending     // Catch:{ all -> 0x0067 }
            java.lang.String r2 = r5.getCmdId()     // Catch:{ all -> 0x0067 }
            r1.remove(r2)     // Catch:{ all -> 0x0067 }
        L_0x0065:
            monitor-exit(r4)
            return
        L_0x0067:
            r5 = move-exception
            monitor-exit(r4)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: gov.nasa.arc.astrobee.ros.internal.RobotNodeMain.onNewMessage(ff_msgs.AckStamped):void");
    }

    public synchronized void onShutdown(Node node) {
        this.m_node = null;
        this.m_cmdPublisher = null;
    }

    public Kinematics getKinematics() {
        DefaultKinematics defaultKinematics;
        synchronized (this.m_kinematics_lock) {
            defaultKinematics = this.m_kinematics;
        }
        return defaultKinematics;
    }

    /* access modifiers changed from: package-private */
    public synchronized MessageFactory getTopicMessageFactory() {
        if (this.m_node != null) {
        } else {
            throw new AstrobeeRuntimeException("Node is not ready or died");
        }
        return this.m_node.getTopicMessageFactory();
    }

    /* access modifiers changed from: package-private */
    public synchronized PendingResult publish(CommandStamped cmd) {
        DefaultPendingResult pr;
        if (this.m_cmdPublisher != null) {
            cmd.getHeader().setStamp(this.m_node.getCurrentTime());
            pr = new DefaultPendingResult(cmd);
            if (this.m_ready) {
                Log log = this.logger;
                log.debug("Publishing " + Stringer.toString(cmd));
                this.m_cmdPublisher.publish(cmd);
            } else {
                this.m_queue.add(cmd);
                pr.setStatus(PendingResult.Status.QUEUED);
            }
            this.m_pending.put(cmd.getCmdId(), pr);
        } else {
            throw new AstrobeeRuntimeException("Node not ready or dead");
        }
        return pr;
    }

    public GraphName getDefaultNodeName() {
        return GraphName.m181of("gs_node_main");
    }
}
