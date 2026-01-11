package org.ros.android;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.IBinder;
import com.google.common.base.Preconditions;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URI;
import java.net.URISyntaxException;
import org.ros.EnvironmentVariables;
import org.ros.address.InetAddressFactory;
import org.ros.android.NodeMainExecutorService;
import org.ros.exception.RosRuntimeException;
import org.ros.node.NodeMainExecutor;

public abstract class RosActivity extends Activity {
    private static final int MASTER_CHOOSER_REQUEST_CODE = 0;
    protected NodeMainExecutorService nodeMainExecutorService;
    private final NodeMainExecutorServiceConnection nodeMainExecutorServiceConnection;
    private final String notificationTicker;
    private final String notificationTitle;

    /* access modifiers changed from: protected */
    public abstract void init(NodeMainExecutor nodeMainExecutor);

    private final class NodeMainExecutorServiceConnection implements ServiceConnection {
        private URI customMasterUri;
        private NodeMainExecutorServiceListener serviceListener;

        public NodeMainExecutorServiceConnection(URI customUri) {
            this.customMasterUri = customUri;
        }

        public void onServiceConnected(ComponentName name, IBinder binder) {
            RosActivity.this.nodeMainExecutorService = ((NodeMainExecutorService.LocalBinder) binder).getService();
            if (this.customMasterUri != null) {
                RosActivity.this.nodeMainExecutorService.setMasterUri(this.customMasterUri);
                RosActivity.this.nodeMainExecutorService.setRosHostname(RosActivity.this.getDefaultHostAddress());
            }
            this.serviceListener = new NodeMainExecutorServiceListener() {
                public void onShutdown(NodeMainExecutorService nodeMainExecutorService) {
                    if (!RosActivity.this.isFinishing()) {
                        RosActivity.this.finish();
                    }
                }
            };
            RosActivity.this.nodeMainExecutorService.addListener(this.serviceListener);
            if (RosActivity.this.getMasterUri() == null) {
                RosActivity.this.startMasterChooser();
            } else {
                RosActivity.this.init();
            }
        }

        public void onServiceDisconnected(ComponentName name) {
            RosActivity.this.nodeMainExecutorService.removeListener(this.serviceListener);
            this.serviceListener = null;
        }

        public NodeMainExecutorServiceListener getServiceListener() {
            return this.serviceListener;
        }
    }

    protected RosActivity(String notificationTicker2, String notificationTitle2) {
        this(notificationTicker2, notificationTitle2, (URI) null);
    }

    protected RosActivity(String notificationTicker2, String notificationTitle2, URI customMasterUri) {
        this.notificationTicker = notificationTicker2;
        this.notificationTitle = notificationTitle2;
        this.nodeMainExecutorServiceConnection = new NodeMainExecutorServiceConnection(customMasterUri);
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        bindNodeMainExecutorService();
    }

    /* access modifiers changed from: protected */
    public void bindNodeMainExecutorService() {
        Intent intent = new Intent(this, NodeMainExecutorService.class);
        intent.setAction("org.ros.android.ACTION_START_NODE_RUNNER_SERVICE");
        intent.putExtra("org.ros.android.EXTRA_NOTIFICATION_TICKER", this.notificationTicker);
        intent.putExtra("org.ros.android.EXTRA_NOTIFICATION_TITLE", this.notificationTitle);
        startService(intent);
        Preconditions.checkState(bindService(intent, this.nodeMainExecutorServiceConnection, 1), "Failed to bind NodeMainExecutorService.");
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        unbindService(this.nodeMainExecutorServiceConnection);
        this.nodeMainExecutorService.removeListener(this.nodeMainExecutorServiceConnection.getServiceListener());
        super.onDestroy();
    }

    /* access modifiers changed from: protected */
    public void init() {
        new AsyncTask<Void, Void, Void>() {
            /* access modifiers changed from: protected */
            public Void doInBackground(Void... params) {
                RosActivity rosActivity = RosActivity.this;
                rosActivity.init(rosActivity.nodeMainExecutorService);
                return null;
            }
        }.execute(new Void[0]);
    }

    public void startMasterChooser() {
        Preconditions.checkState(getMasterUri() == null);
        super.startActivityForResult(new Intent(this, MasterChooser.class), 0);
    }

    public URI getMasterUri() {
        Preconditions.checkNotNull(this.nodeMainExecutorService);
        return this.nodeMainExecutorService.getMasterUri();
    }

    public String getRosHostname() {
        Preconditions.checkNotNull(this.nodeMainExecutorService);
        return this.nodeMainExecutorService.getRosHostname();
    }

    public void startActivityForResult(Intent intent, int requestCode) {
        Preconditions.checkArgument(requestCode != 0);
        super.startActivityForResult(intent, requestCode);
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        String host;
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            if (requestCode == 0) {
                String networkInterfaceName = data.getStringExtra("ROS_MASTER_NETWORK_INTERFACE");
                if (networkInterfaceName == null || networkInterfaceName.equals("")) {
                    host = getDefaultHostAddress();
                } else {
                    try {
                        host = InetAddressFactory.newNonLoopbackForNetworkInterface(NetworkInterface.getByName(networkInterfaceName)).getHostAddress();
                    } catch (SocketException e) {
                        throw new RosRuntimeException((Throwable) e);
                    }
                }
                this.nodeMainExecutorService.setRosHostname(host);
                if (data.getBooleanExtra("ROS_MASTER_CREATE_NEW", false)) {
                    this.nodeMainExecutorService.startMaster(data.getBooleanExtra("ROS_MASTER_PRIVATE", true));
                } else {
                    try {
                        this.nodeMainExecutorService.setMasterUri(new URI(data.getStringExtra(EnvironmentVariables.ROS_MASTER_URI)));
                    } catch (URISyntaxException e2) {
                        throw new RosRuntimeException((Throwable) e2);
                    }
                }
                new AsyncTask<Void, Void, Void>() {
                    /* access modifiers changed from: protected */
                    public Void doInBackground(Void... params) {
                        RosActivity rosActivity = RosActivity.this;
                        rosActivity.init(rosActivity.nodeMainExecutorService);
                        return null;
                    }
                }.execute(new Void[0]);
            } else {
                this.nodeMainExecutorService.forceShutdown();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /* access modifiers changed from: private */
    public String getDefaultHostAddress() {
        return InetAddressFactory.newNonLoopback().getHostAddress();
    }
}
