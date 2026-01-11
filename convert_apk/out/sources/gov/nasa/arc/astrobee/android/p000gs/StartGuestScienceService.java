package gov.nasa.arc.astrobee.android.p000gs;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;
import org.apache.xmlrpc.serializer.ObjectArraySerializer;
import org.ros.internal.transport.ConnectionHeaderFields;

/* renamed from: gov.nasa.arc.astrobee.android.gs.StartGuestScienceService */
public abstract class StartGuestScienceService extends Service {
    private static final String LIB_LOG_TAG = "GuestScienceLib";
    private static final String SERVICE_CLASSNAME = "gov.nasa.arc.astrobee.android.gs.manager.MessengerService";
    private static final String SERVICE_PACKAGE_NAME = "gov.nasa.arc.astrobee.android.gs.manager";
    /* access modifiers changed from: private */
    public boolean mBound;
    final Messenger mCommandMessenger = new Messenger(new IncomingCommandHandler());
    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Messenger unused = StartGuestScienceService.this.mService = new Messenger(iBinder);
            boolean unused2 = StartGuestScienceService.this.mBound = true;
            StartGuestScienceService.this.sendMessenger();
        }

        public void onServiceDisconnected(ComponentName componentName) {
            Messenger unused = StartGuestScienceService.this.mService = null;
            boolean unused2 = StartGuestScienceService.this.mBound = false;
        }
    };
    /* access modifiers changed from: private */
    public String mDataBasePath = "";
    private String mFullApkName = "";
    /* access modifiers changed from: private */
    public Messenger mService = null;

    public abstract void onGuestScienceCustomCmd(String str);

    public abstract void onGuestScienceStart();

    public abstract void onGuestScienceStop();

    /* renamed from: gov.nasa.arc.astrobee.android.gs.StartGuestScienceService$IncomingCommandHandler */
    class IncomingCommandHandler extends Handler {
        IncomingCommandHandler() {
        }

        public void handleMessage(Message msg) {
            if (msg.what == MessageType.PATH.toInt()) {
                Bundle data = msg.getData();
                if (data == null) {
                    Log.e(StartGuestScienceService.LIB_LOG_TAG, "Path message didn't contain data! This shouldn't happen. If it does, contact the Astrobee guest science team.");
                } else if (data.containsKey("path")) {
                    String unused = StartGuestScienceService.this.mDataBasePath = data.getString("path");
                    if (StartGuestScienceService.this.mDataBasePath != "") {
                        StartGuestScienceService.this.onGuestScienceStart();
                    } else {
                        StartGuestScienceService.this.terminate();
                    }
                } else {
                    Log.e(StartGuestScienceService.LIB_LOG_TAG, "Path not found in message of type path! This shouldn't happen. If it does, contact the Astrobee guest science team.");
                }
            } else if (msg.what == MessageType.CMD.toInt()) {
                Bundle data2 = msg.getData();
                if (data2 == null) {
                    Log.e(StartGuestScienceService.LIB_LOG_TAG, "Command message didn't contain data! This shouldn't happen. If it does, contact the Astrobee guest science team.");
                } else if (data2.containsKey("command")) {
                    StartGuestScienceService.this.onGuestScienceCustomCmd(data2.getString("command"));
                } else {
                    Log.e(StartGuestScienceService.LIB_LOG_TAG, "Command not found in message of type command! This shouldn't happen. If it does, contact the Astrobee guest science team.");
                }
            } else if (msg.what == MessageType.STOP.toInt()) {
                StartGuestScienceService.this.onGuestScienceStop();
            } else {
                Log.e(StartGuestScienceService.LIB_LOG_TAG, "Message type not recognized! This shouldn't happen. If it does, contact the Astrobee guest science team.");
            }
        }
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        this.mFullApkName = getApplicationContext().getPackageName();
        Intent bindIntent = new Intent();
        bindIntent.setClassName(SERVICE_PACKAGE_NAME, SERVICE_CLASSNAME);
        bindService(bindIntent, this.mConnection, 1);
        return 2;
    }

    public boolean sendMessenger() {
        Message msg = Message.obtain((Handler) null, MessageType.MESSENGER.toInt());
        Bundle dataBundle = new Bundle();
        dataBundle.putString("apkFullName", this.mFullApkName);
        dataBundle.putParcelable("commandMessenger", this.mCommandMessenger);
        msg.setData(dataBundle);
        try {
            this.mService.send(msg);
            return true;
        } catch (RemoteException e) {
            Log.e(LIB_LOG_TAG, e.getMessage(), e);
            return false;
        }
    }

    public void onDestroy() {
        if (this.mBound) {
            unbindService(this.mConnection);
            this.mBound = false;
        }
        super.onDestroy();
    }

    public String getGuestScienceDataBasePath() {
        return this.mDataBasePath;
    }

    public void terminate() {
        Process.killProcess(Process.myPid());
    }

    public void sendStarted(String topic) {
        sendData(MessageType.JSON, topic, "{\"Summary\": \"Started\"}");
    }

    public void sendStopped(String topic) {
        sendData(MessageType.JSON, topic, "{\"Summary\": \"Stopped\"}");
    }

    public void sendReceivedCustomCommand(String topic) {
        sendData(MessageType.JSON, topic, "{\"Summary\": \"Received Custom Command\"}");
    }

    public void sendData(MessageType type, String topic, String data) {
        sendMsg(type, topic, data.getBytes());
    }

    public void sendData(MessageType type, String topic, byte[] data) {
        sendMsg(type, topic, data);
    }

    public void sendMsg(MessageType type, String topic, byte[] data) {
        if (!this.mBound) {
            Log.e(LIB_LOG_TAG, "Not bound to guest science manager. This shouldn't happen. If it does, contact the Astrobee guest science team.,");
        } else if (data.length <= 2048) {
            Bundle dataBundle = new Bundle();
            dataBundle.putString("apkFullName", this.mFullApkName);
            dataBundle.putString(ConnectionHeaderFields.TOPIC, topic);
            dataBundle.putByteArray(ObjectArraySerializer.DATA_TAG, data);
            Message msg = Message.obtain((Handler) null, type.toInt());
            msg.setData(dataBundle);
            try {
                this.mService.send(msg);
            } catch (RemoteException e) {
                Log.e(LIB_LOG_TAG, e.getMessage(), e);
            }
        } else {
            throw new RuntimeException("Data passed to sendData function is too big to send to ground. Must be 2K.");
        }
    }
}
