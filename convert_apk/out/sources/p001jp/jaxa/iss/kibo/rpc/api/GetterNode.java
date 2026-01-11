package p001jp.jaxa.iss.kibo.rpc.api;

import android.graphics.Bitmap;
import android.util.Log;
import ff_msgs.DockState;
import ff_msgs.EkfState;
import gov.nasa.arc.astrobee.Kinematics;
import gov.nasa.arc.astrobee.ros.DefaultKinematics;
import java.net.URI;
import org.opencv.core.Mat;
import org.ros.Topics;
import org.ros.android.NodeMainExecutorService;
import org.ros.message.MessageListener;
import org.ros.message.Time;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.NodeConfiguration;
import org.ros.node.topic.Subscriber;
import p001jp.jaxa.iss.kibo.rpc.api.types.ImuResult;
import p001jp.jaxa.iss.kibo.rpc.api.types.MonoImage;
import p001jp.jaxa.iss.kibo.rpc.api.types.PointCloud;
import rosgraph_msgs.Clock;
import sensor_msgs.Image;
import sensor_msgs.Imu;
import sensor_msgs.PointCloud2;

/* renamed from: jp.jaxa.iss.kibo.rpc.api.GetterNode */
public class GetterNode extends AbstractNodeMain {
    static final String DOCK_CAM_TOPIC = "/hw/cam_dock";
    static final String DOCK_STATE_TOPIC = "/beh/dock/state";
    static final String EKF_NOISE_TOPIC = "/gnc/ekf_noise";
    static final String EKF_TOPIC = "/gnc/ekf";
    private static final String EMULATOR_ROS_HOSTNAME = "hlp";
    static final String HAZ_CAM_TOPIC = "/hw/depth_haz/points";
    static final String IMU_TOPIC = "/hw/imu";
    static final String NAV_CAM_TOPIC = "/hw/cam_nav";
    static final String NODE_NAME = "GetterNode";
    static final String PERCH_CAM_TOPIC = "/hw/depth_perch/points";
    private static final URI ROS_MASTER_URI = URI.create("http://llp:11311");
    public static GetterNode instance = null;
    /* access modifiers changed from: private */
    public final Object Undock_lock = new Object();
    /* access modifiers changed from: private */
    public Clock clock;
    private Subscriber<Clock> clockSubscriber;
    /* access modifiers changed from: private */
    public final Object clock_lock = new Object();
    private Subscriber<Image> dockCamSubscriber;
    private Subscriber<DockState> dockStateSubscriber;
    private Subscriber<EkfState> ekfSubscriber;
    private Subscriber<PointCloud2> hazCamSubscriber;
    private Subscriber<Imu> imuSubscriber;
    /* access modifiers changed from: private */
    public ImuResult imu_pub;
    /* access modifiers changed from: private */
    public final Object imu_pub_lock = new Object();
    /* access modifiers changed from: private */
    public DefaultKinematics m_kinematics = new DefaultKinematics();
    /* access modifiers changed from: private */
    public final Object m_kinematics_lock = new Object();
    private boolean m_nodeStarted = false;
    /* access modifiers changed from: private */
    public MonoImage monoImageDockCam;
    /* access modifiers changed from: private */
    public MonoImage monoImageNavCam;
    /* access modifiers changed from: private */
    public final Object mono_image_dock_cam_lock = new Object();
    /* access modifiers changed from: private */
    public final Object mono_image_nav_cam_lock = new Object();
    private Subscriber<Image> navCamSubscriber;
    private NodeMainExecutorService nodeMainExecutorService = new NodeMainExecutorService();
    private boolean on_simulation = false;
    private Subscriber<PointCloud2> perchCamSubscriber;
    /* access modifiers changed from: private */
    public PointCloud point_cloud_haz_cam;
    /* access modifiers changed from: private */
    public final Object point_cloud_haz_cam_lock = new Object();
    /* access modifiers changed from: private */
    public PointCloud point_cloud_perch_cam;
    /* access modifiers changed from: private */
    public final Object point_cloud_perch_cam_lock = new Object();
    /* access modifiers changed from: private */
    public boolean undocked = false;

    private GetterNode() {
        NodeConfiguration nodeConfiguration = NodeConfiguration.newPublic(EMULATOR_ROS_HOSTNAME);
        nodeConfiguration.setMasterUri(ROS_MASTER_URI);
        this.nodeMainExecutorService.execute(this, nodeConfiguration);
    }

    public GraphName getDefaultNodeName() {
        return GraphName.m181of(NODE_NAME);
    }

    public void onStart(ConnectedNode connectedNode) {
        Log.d("KiboRpcApi", "GetterNode initialization started.");
        try {
            boolean z = connectedNode.getParameterTree().getBoolean("/krpc/on_simulation", false);
            this.on_simulation = z;
            if (z) {
                this.ekfSubscriber = connectedNode.newSubscriber(EKF_NOISE_TOPIC, EkfState._TYPE);
            } else {
                this.ekfSubscriber = connectedNode.newSubscriber(EKF_TOPIC, EkfState._TYPE);
            }
            this.imuSubscriber = connectedNode.newSubscriber(IMU_TOPIC, Imu._TYPE);
            this.navCamSubscriber = connectedNode.newSubscriber(NAV_CAM_TOPIC, Image._TYPE);
            this.dockCamSubscriber = connectedNode.newSubscriber(DOCK_CAM_TOPIC, Image._TYPE);
            this.hazCamSubscriber = connectedNode.newSubscriber(HAZ_CAM_TOPIC, PointCloud2._TYPE);
            this.perchCamSubscriber = connectedNode.newSubscriber(PERCH_CAM_TOPIC, PointCloud2._TYPE);
            this.dockStateSubscriber = connectedNode.newSubscriber(DOCK_STATE_TOPIC, DockState._TYPE);
            this.clockSubscriber = connectedNode.newSubscriber(Topics.CLOCK, Clock._TYPE);
            this.ekfSubscriber.addMessageListener(new MessageListener<EkfState>() {
                public void onNewMessage(EkfState ekfState) {
                    synchronized (GetterNode.this.m_kinematics_lock) {
                        DefaultKinematics unused = GetterNode.this.m_kinematics = new DefaultKinematics(ekfState);
                    }
                    GetterNode.instance = GetterNode.this;
                }
            });
            this.imuSubscriber.addMessageListener(new MessageListener<Imu>() {
                public void onNewMessage(Imu imu) {
                    synchronized (GetterNode.this.imu_pub_lock) {
                        ImuResult unused = GetterNode.this.imu_pub = new ImuResult(imu);
                    }
                    GetterNode.instance = GetterNode.this;
                }
            }, 10);
            this.navCamSubscriber.addMessageListener(new MessageListener<Image>() {
                public void onNewMessage(Image message) {
                    synchronized (GetterNode.this.mono_image_nav_cam_lock) {
                        MonoImage unused = GetterNode.this.monoImageNavCam = new MonoImage(message);
                    }
                    GetterNode.instance = GetterNode.this;
                }
            }, 1);
            this.dockCamSubscriber.addMessageListener(new MessageListener<Image>() {
                public void onNewMessage(Image message) {
                    synchronized (GetterNode.this.mono_image_dock_cam_lock) {
                        MonoImage unused = GetterNode.this.monoImageDockCam = new MonoImage(message);
                    }
                    GetterNode.instance = GetterNode.this;
                }
            }, 1);
            this.hazCamSubscriber.addMessageListener(new MessageListener<PointCloud2>() {
                public void onNewMessage(PointCloud2 message) {
                    synchronized (GetterNode.this.point_cloud_haz_cam_lock) {
                        PointCloud unused = GetterNode.this.point_cloud_haz_cam = new PointCloud(message);
                    }
                    GetterNode.instance = GetterNode.this;
                }
            }, 1);
            this.perchCamSubscriber.addMessageListener(new MessageListener<PointCloud2>() {
                public void onNewMessage(PointCloud2 message) {
                    synchronized (GetterNode.this.point_cloud_perch_cam_lock) {
                        PointCloud unused = GetterNode.this.point_cloud_perch_cam = new PointCloud(message);
                    }
                    GetterNode.instance = GetterNode.this;
                }
            }, 1);
            this.dockStateSubscriber.addMessageListener(new MessageListener<DockState>() {
                public void onNewMessage(DockState message) {
                    if (message.getState() == -4) {
                        synchronized (GetterNode.this.Undock_lock) {
                            boolean unused = GetterNode.this.undocked = true;
                        }
                    }
                    GetterNode.instance = GetterNode.this;
                }
            }, 1);
            this.clockSubscriber.addMessageListener(new MessageListener<Clock>() {
                public void onNewMessage(Clock message) {
                    synchronized (GetterNode.this.clock_lock) {
                        Clock unused = GetterNode.this.clock = message;
                    }
                }
            });
            instance = this;
            this.m_nodeStarted = true;
            Log.d("KiboRpcApi", "GetterNode initialization succeeded.");
        } catch (Exception e) {
            Log.e("KiboRpcApi", "GetterNode initialization failed, msg:", e);
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

    public Kinematics getCurrentKinematics() {
        DefaultKinematics defaultKinematics;
        synchronized (this.m_kinematics_lock) {
            defaultKinematics = this.m_kinematics;
        }
        return defaultKinematics;
    }

    public ImuResult getImu() {
        ImuResult imuResult;
        synchronized (this.imu_pub_lock) {
            try {
                imuResult = this.imu_pub;
            } catch (Exception e) {
                Log.e("KiboRpcApi", "getImu failed, msg:", e);
                return null;
            } catch (Throwable th) {
                throw th;
            }
        }
        return imuResult;
    }

    public Bitmap getBitmapNavCam() {
        Bitmap bitmap;
        synchronized (this.mono_image_nav_cam_lock) {
            try {
                bitmap = this.monoImageNavCam.getBitmap();
            } catch (Exception e) {
                Log.e("KiboRpcApi", "getBitmapNavCam failed, msg:", e);
                return null;
            } catch (Throwable th) {
                throw th;
            }
        }
        return bitmap;
    }

    public Bitmap getBitmapDockCam() {
        Bitmap bitmap;
        synchronized (this.mono_image_dock_cam_lock) {
            try {
                bitmap = this.monoImageDockCam.getBitmap();
            } catch (Exception e) {
                Log.e("KiboRpcApi", "getBitmapDockCam failed, msg:", e);
                return null;
            } catch (Throwable th) {
                throw th;
            }
        }
        return bitmap;
    }

    public Mat getMatNavCam() {
        Mat mat;
        synchronized (this.mono_image_nav_cam_lock) {
            try {
                mat = this.monoImageNavCam.getMat();
            } catch (Exception e) {
                Log.e("KiboRpcApi", "getMatNavCam failed, msg:", e);
                return null;
            } catch (Throwable th) {
                throw th;
            }
        }
        return mat;
    }

    public Mat getMatDockCam() {
        Mat mat;
        synchronized (this.mono_image_dock_cam_lock) {
            try {
                mat = this.monoImageDockCam.getMat();
            } catch (Exception e) {
                Log.e("KiboRpcApi", "getMatDockCam failed, msg:", e);
                return null;
            } catch (Throwable th) {
                throw th;
            }
        }
        return mat;
    }

    public PointCloud getPointCloudHazCam() {
        PointCloud clone;
        synchronized (this.point_cloud_haz_cam_lock) {
            try {
                clone = this.point_cloud_haz_cam.clone();
            } catch (Exception e) {
                Log.e("KiboRpcApi", "getPointCloudHazCam failed, msg:", e);
                return null;
            } catch (Throwable th) {
                throw th;
            }
        }
        return clone;
    }

    public PointCloud getPointCloudPerchCam() {
        PointCloud clone;
        synchronized (this.point_cloud_perch_cam_lock) {
            try {
                clone = this.point_cloud_perch_cam.clone();
            } catch (Exception e) {
                Log.e("KiboRpcApi", "getPointCloudPerchCam failed, msg:", e);
                return null;
            } catch (Throwable th) {
                throw th;
            }
        }
        return clone;
    }

    public boolean undocked() {
        return this.undocked;
    }

    public boolean getOnSimulation() {
        return this.on_simulation;
    }

    public Time getCurrentTime() {
        Time time;
        if (!this.on_simulation) {
            return Time.fromMillis(System.currentTimeMillis());
        }
        if (this.clock == null) {
            return new Time();
        }
        synchronized (this.clock_lock) {
            time = new Time(this.clock.getClock());
        }
        return time;
    }
}
