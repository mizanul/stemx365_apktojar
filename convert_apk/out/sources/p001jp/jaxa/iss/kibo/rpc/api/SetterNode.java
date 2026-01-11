package p001jp.jaxa.iss.kibo.rpc.api;

import android.util.Log;
import ff_msgs.SignalState;
import java.net.URI;
import org.ros.android.NodeMainExecutorService;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.NodeConfiguration;
import org.ros.node.topic.Publisher;

/* renamed from: jp.jaxa.iss.kibo.rpc.api.SetterNode */
public class SetterNode extends AbstractNodeMain {
    private static final String EMULATOR_ROS_HOSTNAME = "hlp";
    static final String NODE_NAME = "SetterNode";
    static final int PUB_SLEEP_TIME_MS = 10;
    private static final URI ROS_MASTER_URI = URI.create("http://llp:11311");
    static final String TOPIC_SIGNALS = "signals";
    public static SetterNode instance = null;
    private NodeMainExecutorService nodeMainExecutorService = new NodeMainExecutorService();
    private Publisher<SignalState> signalStatePublisher;
    private final Object signal_state_lock = new Object();

    private SetterNode() {
        NodeConfiguration nodeConfiguration = NodeConfiguration.newPublic(EMULATOR_ROS_HOSTNAME);
        nodeConfiguration.setMasterUri(ROS_MASTER_URI);
        this.nodeMainExecutorService.execute(this, nodeConfiguration);
    }

    public GraphName getDefaultNodeName() {
        return GraphName.m181of(NODE_NAME);
    }

    public void onStart(ConnectedNode connectedNode) {
        Log.d("KiboRpcApi", "SetterNode initialization started.");
        try {
            Publisher<SignalState> newPublisher = connectedNode.newPublisher(TOPIC_SIGNALS, SignalState._TYPE);
            this.signalStatePublisher = newPublisher;
            newPublisher.setLatchMode(true);
            instance = this;
            Log.d("KiboRpcApi", "SetterNode initialization succeeded.");
        } catch (Exception e) {
            Log.e("KiboRpcApi", "SetterNode initialization failed, msg:", e);
        }
    }

    public static SetterNode getInstance() {
        if (instance == null) {
            instance = new SetterNode();
        }
        return instance;
    }

    public boolean setSignalState(Byte signalState, int times) {
        synchronized (this.signal_state_lock) {
            if (times <= 0) {
                Log.w("KiboRpcApi", "SetterNode setSignalState : Specified times <" + times + "> is not correct.");
                return false;
            }
            switch (signalState.byteValue()) {
                case 0:
                    Log.i("KiboRpcApi", "SetterNode setSignalState status : VIDEO_ON");
                    break;
                case 1:
                    Log.i("KiboRpcApi", "SetterNode setSignalState status : VIDEO_OFF");
                    break;
                case 3:
                    Log.i("KiboRpcApi", "SetterNode setSignalState status : SUCCESS");
                    break;
                case 4:
                    Log.i("KiboRpcApi", "SetterNode setSignalState status : ENTER_HATCHWAY");
                    break;
                case 5:
                    Log.i("KiboRpcApi", "SetterNode setSignalState status : UNDOCK");
                    break;
                case 6:
                    Log.i("KiboRpcApi", "SetterNode setSignalState status : UNPERCH");
                    break;
                case 7:
                    Log.i("KiboRpcApi", "SetterNode setSignalState status : MOTION_IMPAIRED");
                    break;
                case 8:
                    Log.i("KiboRpcApi", "SetterNode setSignalState status : THRUST_FORWARD");
                    break;
                case 9:
                    Log.i("KiboRpcApi", "SetterNode setSignalState status : THRUST_AFT");
                    break;
                case 10:
                    Log.i("KiboRpcApi", "SetterNode setSignalState status : TURN_RIGHT");
                    break;
                case 11:
                    Log.i("KiboRpcApi", "SetterNode setSignalState status : TURN_LEFT");
                    break;
                case 12:
                    Log.i("KiboRpcApi", "SetterNode setSignalState status : TURN_UP");
                    break;
                case 13:
                    Log.i("KiboRpcApi", "SetterNode setSignalState status : TURN_DOWN");
                    break;
                case 14:
                    Log.i("KiboRpcApi", "SetterNode setSignalState status : CLEAR");
                    break;
                case 15:
                    Log.i("KiboRpcApi", "SetterNode setSignalState status : SLEEP");
                    break;
                case 16:
                    Log.i("KiboRpcApi", "SetterNode setSignalState status : WAKE");
                    break;
                case 17:
                    Log.i("KiboRpcApi", "SetterNode setSignalState status : STOP_ALL_LIGHTS");
                    break;
                case 18:
                    Log.i("KiboRpcApi", "SetterNode setSignalState status : CHARGING");
                    break;
                default:
                    Log.w("KiboRpcApi", "SetterNode setSignalState : Specified state is unknown.");
                    return false;
            }
            Log.i("KiboRpcApi", "SetterNode setSignalState times  : " + times);
            SignalState message = this.signalStatePublisher.newMessage();
            message.setState(signalState.byteValue());
            int i = 0;
            while (i < times) {
                try {
                    this.signalStatePublisher.publish(message);
                    Log.i("KiboRpcApi", "SetterNode setSignalState publish " + (i + 1) + " th");
                    Thread.sleep(10);
                    i++;
                } catch (InterruptedException e) {
                    Log.e("KiboRpcApi", "SetterNode setSignalState All publishes may not have been possible.", e);
                    return false;
                }
            }
            return true;
        }
    }
}
