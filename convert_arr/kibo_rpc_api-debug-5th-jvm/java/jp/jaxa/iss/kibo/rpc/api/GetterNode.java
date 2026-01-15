/*
 * Decompiled with CFR 0.152.
 */
package jp.jaxa.iss.kibo.rpc.api;

import android.graphics.Bitmap;
import common.util.Log;
import ff_msgs.DockState;
import ff_msgs.EkfState;
import gov.nasa.arc.astrobee.Kinematics;
import gov.nasa.arc.astrobee.ros.DefaultKinematics;
import java.net.URI;
import jp.jaxa.iss.kibo.rpc.api.types.ImuResult;
import jp.jaxa.iss.kibo.rpc.api.types.MonoImage;
import jp.jaxa.iss.kibo.rpc.api.types.PointCloud;
import org.opencv.core.Mat;
import org.ros.Topics;
import org.ros.android.NodeMainExecutorService;
import org.ros.message.MessageListener;
import org.ros.message.Time;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeMain;
import org.ros.node.parameter.ParameterTree;
import org.ros.node.topic.Subscriber;
import rosgraph_msgs.Clock;
import sensor_msgs.Image;
import sensor_msgs.Imu;
import sensor_msgs.PointCloud2;

public class GetterNode
extends AbstractNodeMain {
    public static GetterNode instance = null;
    private NodeMainExecutorService nodeMainExecutorService;
    private boolean m_nodeStarted = false;
    private Subscriber<EkfState> ekfSubscriber;
    private Subscriber<Imu> imuSubscriber;
    private Subscriber<Image> navCamSubscriber;
    private Subscriber<Image> dockCamSubscriber;
    private Subscriber<PointCloud2> hazCamSubscriber;
    private Subscriber<PointCloud2> perchCamSubscriber;
    private Subscriber<DockState> dockStateSubscriber;
    private Subscriber<Clock> clockSubscriber;
    private static final URI ROS_MASTER_URI = URI.create("http://llp:11311");
    private static final String EMULATOR_ROS_HOSTNAME = "hlp";
    static final String NODE_NAME = "GetterNode";
    static final String EKF_TOPIC = "/gnc/ekf";
    static final String EKF_NOISE_TOPIC = "/gnc/ekf_noise";
    static final String IMU_TOPIC = "/hw/imu";
    static final String NAV_CAM_TOPIC = "/hw/cam_nav";
    static final String DOCK_CAM_TOPIC = "/hw/cam_dock";
    static final String HAZ_CAM_TOPIC = "/hw/depth_haz/points";
    static final String PERCH_CAM_TOPIC = "/hw/depth_perch/points";
    static final String DOCK_STATE_TOPIC = "/beh/dock/state";
    private DefaultKinematics m_kinematics = new DefaultKinematics();
    private ImuResult imu_pub;
    private MonoImage monoImageNavCam;
    private MonoImage monoImageDockCam;
    private PointCloud point_cloud_haz_cam;
    private PointCloud point_cloud_perch_cam;
    private boolean undocked = false;
    private boolean on_simulation = false;
    private Clock clock;
    private final Object m_kinematics_lock = new Object();
    private final Object imu_pub_lock = new Object();
    private final Object mono_image_nav_cam_lock = new Object();
    private final Object mono_image_dock_cam_lock = new Object();
    private final Object point_cloud_haz_cam_lock = new Object();
    private final Object point_cloud_perch_cam_lock = new Object();
    private final Object Undock_lock = new Object();
    private final Object clock_lock = new Object();

    private GetterNode() {
        this.nodeMainExecutorService = new NodeMainExecutorService();
        NodeConfiguration nodeConfiguration = NodeConfiguration.newPublic((String)EMULATOR_ROS_HOSTNAME);
        nodeConfiguration.setMasterUri(ROS_MASTER_URI);
        this.nodeMainExecutorService.execute((NodeMain)this, nodeConfiguration);
    }

    public GraphName getDefaultNodeName() {
        return GraphName.of((String)NODE_NAME);
    }

    public void onStart(ConnectedNode connectedNode) {
        common.util.Log.d((String)"KiboRpcApi", (String)"GetterNode initialization started.");
        try {
            ParameterTree params = connectedNode.getParameterTree();
            this.on_simulation = params.getBoolean("/krpc/on_simulation", false);
            this.ekfSubscriber = this.on_simulation ? connectedNode.newSubscriber(EKF_NOISE_TOPIC, "ff_msgs/EkfState") : connectedNode.newSubscriber(EKF_TOPIC, "ff_msgs/EkfState");
            this.imuSubscriber = connectedNode.newSubscriber(IMU_TOPIC, "sensor_msgs/Imu");
            this.navCamSubscriber = connectedNode.newSubscriber(NAV_CAM_TOPIC, "sensor_msgs/Image");
            this.dockCamSubscriber = connectedNode.newSubscriber(DOCK_CAM_TOPIC, "sensor_msgs/Image");
            this.hazCamSubscriber = connectedNode.newSubscriber(HAZ_CAM_TOPIC, "sensor_msgs/PointCloud2");
            this.perchCamSubscriber = connectedNode.newSubscriber(PERCH_CAM_TOPIC, "sensor_msgs/PointCloud2");
            this.dockStateSubscriber = connectedNode.newSubscriber(DOCK_STATE_TOPIC, "ff_msgs/DockState");
            this.clockSubscriber = connectedNode.newSubscriber(Topics.CLOCK, "rosgraph_msgs/Clock");
            this.ekfSubscriber.addMessageListener((MessageListener)new MessageListener<EkfState>(){

                /*
                 * WARNING - Removed try catching itself - possible behaviour change.
                 */
                public void onNewMessage(EkfState ekfState) {
                    Object object = GetterNode.this.m_kinematics_lock;
                    synchronized (object) {
                        GetterNode.this.m_kinematics = new DefaultKinematics(ekfState);
                    }
                    instance = GetterNode.this;
                }
            });
            this.imuSubscriber.addMessageListener((MessageListener)new MessageListener<Imu>(){

                /*
                 * WARNING - Removed try catching itself - possible behaviour change.
                 */
                public void onNewMessage(Imu imu) {
                    Object object = GetterNode.this.imu_pub_lock;
                    synchronized (object) {
                        GetterNode.this.imu_pub = new ImuResult(imu);
                    }
                    instance = GetterNode.this;
                }
            }, 10);
            this.navCamSubscriber.addMessageListener((MessageListener)new MessageListener<Image>(){

                /*
                 * WARNING - Removed try catching itself - possible behaviour change.
                 */
                public void onNewMessage(Image message) {
                    Object object = GetterNode.this.mono_image_nav_cam_lock;
                    synchronized (object) {
                        GetterNode.this.monoImageNavCam = new MonoImage(message);
                    }
                    instance = GetterNode.this;
                }
            }, 1);
            this.dockCamSubscriber.addMessageListener((MessageListener)new MessageListener<Image>(){

                /*
                 * WARNING - Removed try catching itself - possible behaviour change.
                 */
                public void onNewMessage(Image message) {
                    Object object = GetterNode.this.mono_image_dock_cam_lock;
                    synchronized (object) {
                        GetterNode.this.monoImageDockCam = new MonoImage(message);
                    }
                    instance = GetterNode.this;
                }
            }, 1);
            this.hazCamSubscriber.addMessageListener((MessageListener)new MessageListener<PointCloud2>(){

                /*
                 * WARNING - Removed try catching itself - possible behaviour change.
                 */
                public void onNewMessage(PointCloud2 message) {
                    Object object = GetterNode.this.point_cloud_haz_cam_lock;
                    synchronized (object) {
                        GetterNode.this.point_cloud_haz_cam = new PointCloud(message);
                    }
                    instance = GetterNode.this;
                }
            }, 1);
            this.perchCamSubscriber.addMessageListener((MessageListener)new MessageListener<PointCloud2>(){

                /*
                 * WARNING - Removed try catching itself - possible behaviour change.
                 */
                public void onNewMessage(PointCloud2 message) {
                    Object object = GetterNode.this.point_cloud_perch_cam_lock;
                    synchronized (object) {
                        GetterNode.this.point_cloud_perch_cam = new PointCloud(message);
                    }
                    instance = GetterNode.this;
                }
            }, 1);
            this.dockStateSubscriber.addMessageListener((MessageListener)new MessageListener<DockState>(){

                /*
                 * WARNING - Removed try catching itself - possible behaviour change.
                 */
                public void onNewMessage(DockState message) {
                    if (message.getState() == -4) {
                        Object object = GetterNode.this.Undock_lock;
                        synchronized (object) {
                            GetterNode.this.undocked = true;
                        }
                    }
                    instance = GetterNode.this;
                }
            }, 1);
            this.clockSubscriber.addMessageListener((MessageListener)new MessageListener<Clock>(){

                /*
                 * WARNING - Removed try catching itself - possible behaviour change.
                 */
                public void onNewMessage(Clock message) {
                    Object object = GetterNode.this.clock_lock;
                    synchronized (object) {
                        GetterNode.this.clock = message;
                    }
                }
            });
            instance = this;
            this.m_nodeStarted = true;
            common.util.Log.d((String)"KiboRpcApi", (String)"GetterNode initialization succeeded.");
        }
        catch (Exception e) {
            common.util.Log.e((String)"KiboRpcApi", (String)"GetterNode initialization failed, msg:", (Throwable)e);
        }
    }

    public static GetterNode getInstance() {
        if (instance == null) {
            instance = new GetterNode();
        }
        return instance;
    }

    public boolean isNodeStarted() {
        return this.m_nodeStarted;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Kinematics getCurrentKinematics() {
        Object object = this.m_kinematics_lock;
        synchronized (object) {
            return this.m_kinematics;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public ImuResult getImu() {
        Object object = this.imu_pub_lock;
        synchronized (object) {
            try {
                return this.imu_pub;
            }
            catch (Exception e) {
                common.util.Log.e((String)"KiboRpcApi", (String)"getImu failed, msg:", (Throwable)e);
                return null;
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Bitmap getBitmapNavCam() {
        Object object = this.mono_image_nav_cam_lock;
        synchronized (object) {
            try {
                return this.monoImageNavCam.getBitmap();
            }
            catch (Exception e) {
                common.util.Log.e((String)"KiboRpcApi", (String)"getBitmapNavCam failed, msg:", (Throwable)e);
                return null;
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Bitmap getBitmapDockCam() {
        Object object = this.mono_image_dock_cam_lock;
        synchronized (object) {
            try {
                return this.monoImageDockCam.getBitmap();
            }
            catch (Exception e) {
                common.util.Log.e((String)"KiboRpcApi", (String)"getBitmapDockCam failed, msg:", (Throwable)e);
                return null;
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Mat getMatNavCam() {
        Object object = this.mono_image_nav_cam_lock;
        synchronized (object) {
            try {
                return this.monoImageNavCam.getMat();
            }
            catch (Exception e) {
                common.util.Log.e((String)"KiboRpcApi", (String)"getMatNavCam failed, msg:", (Throwable)e);
                return null;
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Mat getMatDockCam() {
        Object object = this.mono_image_dock_cam_lock;
        synchronized (object) {
            try {
                return this.monoImageDockCam.getMat();
            }
            catch (Exception e) {
                common.util.Log.e((String)"KiboRpcApi", (String)"getMatDockCam failed, msg:", (Throwable)e);
                return null;
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public PointCloud getPointCloudHazCam() {
        Object object = this.point_cloud_haz_cam_lock;
        synchronized (object) {
            try {
                return this.point_cloud_haz_cam.clone();
            }
            catch (Exception e) {
                common.util.Log.e((String)"KiboRpcApi", (String)"getPointCloudHazCam failed, msg:", (Throwable)e);
                return null;
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public PointCloud getPointCloudPerchCam() {
        Object object = this.point_cloud_perch_cam_lock;
        synchronized (object) {
            try {
                return this.point_cloud_perch_cam.clone();
            }
            catch (Exception e) {
                common.util.Log.e((String)"KiboRpcApi", (String)"getPointCloudPerchCam failed, msg:", (Throwable)e);
                return null;
            }
        }
    }

    public boolean undocked() {
        return this.undocked;
    }

    public boolean getOnSimulation() {
        return this.on_simulation;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Time getCurrentTime() {
        if (this.on_simulation) {
            if (this.clock == null) {
                return new Time();
            }
            Object object = this.clock_lock;
            synchronized (object) {
                return new Time(this.clock.getClock());
            }
        }
        return Time.fromMillis((long)System.currentTimeMillis());
    }
}

