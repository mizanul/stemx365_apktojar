package org.ros.android;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;
import com.google.common.base.Preconditions;
import java.net.URI;
import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import org.ros.RosCore;
import org.ros.android.android_10.C0068R;
import org.ros.concurrent.ListenerGroup;
import org.ros.concurrent.SignalRunnable;
import org.ros.exception.RosRuntimeException;
import org.ros.node.DefaultNodeMainExecutor;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeListener;
import org.ros.node.NodeMain;
import org.ros.node.NodeMainExecutor;

public class NodeMainExecutorService extends Service implements NodeMainExecutor {
    static final String ACTION_SHUTDOWN = "org.ros.android.ACTION_SHUTDOWN_NODE_RUNNER_SERVICE";
    static final String ACTION_START = "org.ros.android.ACTION_START_NODE_RUNNER_SERVICE";
    static final String EXTRA_NOTIFICATION_TICKER = "org.ros.android.EXTRA_NOTIFICATION_TICKER";
    static final String EXTRA_NOTIFICATION_TITLE = "org.ros.android.EXTRA_NOTIFICATION_TITLE";
    private static final int ONGOING_NOTIFICATION = 1;
    private static final String TAG = "NodeMainExecutorService";
    private final IBinder binder = new LocalBinder();
    private Handler handler;
    private final ListenerGroup<NodeMainExecutorServiceListener> listeners = new ListenerGroup<>(this.nodeMainExecutor.getScheduledExecutorService());
    private URI masterUri;
    private final NodeMainExecutor nodeMainExecutor = DefaultNodeMainExecutor.newDefault();
    private RosCore rosCore;
    private String rosHostname = null;
    private PowerManager.WakeLock wakeLock;
    private WifiManager.WifiLock wifiLock;

    class LocalBinder extends Binder {
        LocalBinder() {
        }

        /* access modifiers changed from: package-private */
        public NodeMainExecutorService getService() {
            return NodeMainExecutorService.this;
        }
    }

    public void onCreate() {
        this.handler = new Handler();
        PowerManager.WakeLock newWakeLock = ((PowerManager) getSystemService("power")).newWakeLock(1, TAG);
        this.wakeLock = newWakeLock;
        newWakeLock.acquire();
        int wifiLockType = 1;
        try {
            wifiLockType = WifiManager.class.getField("WIFI_MODE_FULL_HIGH_PERF").getInt((Object) null);
        } catch (Exception e) {
            Log.w(TAG, "Unable to acquire high performance wifi lock.");
        }
        WifiManager.WifiLock createWifiLock = ((WifiManager) getSystemService("wifi")).createWifiLock(wifiLockType, TAG);
        this.wifiLock = createWifiLock;
        createWifiLock.acquire();
    }

    public void execute(NodeMain nodeMain, NodeConfiguration nodeConfiguration, Collection<NodeListener> nodeListeneners) {
        this.nodeMainExecutor.execute(nodeMain, nodeConfiguration, nodeListeneners);
    }

    public void execute(NodeMain nodeMain, NodeConfiguration nodeConfiguration) {
        execute(nodeMain, nodeConfiguration, (Collection<NodeListener>) null);
    }

    public ScheduledExecutorService getScheduledExecutorService() {
        return this.nodeMainExecutor.getScheduledExecutorService();
    }

    public void shutdownNodeMain(NodeMain nodeMain) {
        this.nodeMainExecutor.shutdownNodeMain(nodeMain);
    }

    public void shutdown() {
        this.handler.post(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(NodeMainExecutorService.this);
                builder.setMessage("Continue shutting down?");
                builder.setPositiveButton("Shutdown", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        NodeMainExecutorService.this.forceShutdown();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.getWindow().setType(2003);
                alertDialog.show();
            }
        });
    }

    public void forceShutdown() {
        signalOnShutdown();
        stopForeground(true);
        stopSelf();
    }

    public void addListener(NodeMainExecutorServiceListener listener) {
        this.listeners.add(listener);
    }

    public void removeListener(NodeMainExecutorServiceListener listener) {
        this.listeners.remove(listener);
    }

    private void signalOnShutdown() {
        this.listeners.signal(new SignalRunnable<NodeMainExecutorServiceListener>() {
            public void run(NodeMainExecutorServiceListener nodeMainExecutorServiceListener) {
                nodeMainExecutorServiceListener.onShutdown(NodeMainExecutorService.this);
            }
        });
    }

    public void onDestroy() {
        toast("Shutting down...");
        this.nodeMainExecutor.shutdown();
        RosCore rosCore2 = this.rosCore;
        if (rosCore2 != null) {
            rosCore2.shutdown();
        }
        if (this.wakeLock.isHeld()) {
            this.wakeLock.release();
        }
        if (this.wifiLock.isHeld()) {
            this.wifiLock.release();
        }
        super.onDestroy();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getAction() == null) {
            return 2;
        }
        if (intent.getAction().equals(ACTION_START)) {
            Preconditions.checkArgument(intent.hasExtra(EXTRA_NOTIFICATION_TICKER));
            Preconditions.checkArgument(intent.hasExtra(EXTRA_NOTIFICATION_TITLE));
            Notification notification = new Notification(C0068R.mipmap.icon, intent.getStringExtra(EXTRA_NOTIFICATION_TICKER), System.currentTimeMillis());
            Intent notificationIntent = new Intent(this, NodeMainExecutorService.class);
            notificationIntent.setAction(ACTION_SHUTDOWN);
            notification.setLatestEventInfo(this, intent.getStringExtra(EXTRA_NOTIFICATION_TITLE), "Tap to shutdown.", PendingIntent.getService(this, 0, notificationIntent, 0));
            startForeground(1, notification);
        }
        if (intent.getAction().equals(ACTION_SHUTDOWN)) {
            shutdown();
        }
        return 2;
    }

    public IBinder onBind(Intent intent) {
        return this.binder;
    }

    public URI getMasterUri() {
        return this.masterUri;
    }

    public void setMasterUri(URI uri) {
        this.masterUri = uri;
    }

    public void setRosHostname(String hostname) {
        this.rosHostname = hostname;
    }

    public String getRosHostname() {
        return this.rosHostname;
    }

    @Deprecated
    public void startMaster() {
        startMaster(true);
    }

    public void startMaster(boolean isPrivate) {
        AsyncTask<Boolean, Void, URI> task = new AsyncTask<Boolean, Void, URI>() {
            /* access modifiers changed from: protected */
            public URI doInBackground(Boolean[] params) {
                NodeMainExecutorService.this.startMasterBlocking(params[0].booleanValue());
                return NodeMainExecutorService.this.getMasterUri();
            }
        };
        task.execute(new Boolean[]{Boolean.valueOf(isPrivate)});
        try {
            task.get();
        } catch (InterruptedException e) {
            throw new RosRuntimeException((Throwable) e);
        } catch (ExecutionException e2) {
            throw new RosRuntimeException((Throwable) e2);
        }
    }

    /* access modifiers changed from: private */
    public void startMasterBlocking(boolean isPrivate) {
        if (isPrivate) {
            this.rosCore = RosCore.newPrivate();
        } else {
            String str = this.rosHostname;
            if (str != null) {
                this.rosCore = RosCore.newPublic(str, 11311);
            } else {
                this.rosCore = RosCore.newPublic(11311);
            }
        }
        this.rosCore.start();
        try {
            this.rosCore.awaitStart();
            this.masterUri = this.rosCore.getUri();
        } catch (Exception e) {
            throw new RosRuntimeException((Throwable) e);
        }
    }

    public void toast(final String text) {
        this.handler.post(new Runnable() {
            public void run() {
                Toast.makeText(NodeMainExecutorService.this, text, 0).show();
            }
        });
    }
}
