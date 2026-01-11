package p001jp.jaxa.iss.kibo.rpc.api;

import android.util.Log;
import gov.nasa.arc.astrobee.Robot;
import gov.nasa.arc.astrobee.android.p000gs.StartGuestScienceService;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.opencv.android.OpenCVLoader;
import org.ros.address.Address;
import org.xbill.DNS.ResolverConfig;

/* renamed from: jp.jaxa.iss.kibo.rpc.api.KiboRpcService */
public class KiboRpcService extends StartGuestScienceService {
    protected KiboRpcApi api = null;

    /* renamed from: df */
    private final DateFormat f4df = new SimpleDateFormat("yyyyMMdd hhmmssSSS");
    public GetterNode getterNode;
    public Robot robot;

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0057, code lost:
        r6 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
        r8 = new org.json.JSONObject();
        r8.put(org.ros.internal.transport.ConnectionHeaderFields.ERROR, "Program Down: Exception: " + r6.getClass().getName());
        sendData(gov.nasa.arc.astrobee.android.p000gs.MessageType.JSON, org.apache.xmlrpc.serializer.ObjectArraySerializer.DATA_TAG, r8.toString());
        android.util.Log.e("KiboRpcApi", "Program Down: Exception: ", r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0088, code lost:
        r6 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:?, code lost:
        r8 = new org.json.JSONObject();
        r8.put(org.ros.internal.transport.ConnectionHeaderFields.ERROR, "Program Down: Exception: " + r6.getClass().getName());
        sendData(gov.nasa.arc.astrobee.android.p000gs.MessageType.JSON, org.apache.xmlrpc.serializer.ObjectArraySerializer.DATA_TAG, r8.toString());
        android.util.Log.e("KiboRpcApi", "Program Down: Exception: ", r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00b9, code lost:
        r6 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:?, code lost:
        r8 = new org.json.JSONObject();
        r8.put(org.ros.internal.transport.ConnectionHeaderFields.ERROR, "Program Down: Exception: " + r6.getClass().getName());
        sendData(gov.nasa.arc.astrobee.android.p000gs.MessageType.JSON, org.apache.xmlrpc.serializer.ObjectArraySerializer.DATA_TAG, r8.toString());
        android.util.Log.e("KiboRpcApi", "Program Down: Exception: ", r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00f8, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00f9, code lost:
        sendData(gov.nasa.arc.astrobee.android.p000gs.MessageType.JSON, org.apache.xmlrpc.serializer.ObjectArraySerializer.DATA_TAG, r3.getClass().getName());
        android.util.Log.e("KiboRpcApi", "Program Down: Exception: ", r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:?, code lost:
        return;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x00f8 A[ExcHandler: JSONException (r3v1 'e' org.json.JSONException A[CUSTOM_DECLARE]), Splitter:B:1:0x000b] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onGuestScienceCustomCmd(java.lang.String r12) {
        /*
            r11 = this;
            java.lang.String r0 = "KiboRpcApi"
            java.lang.String r1 = "data"
            java.lang.String r2 = "Program Down: Exception: "
            java.lang.String r3 = "info"
            r11.sendReceivedCustomCommand(r3)
            org.json.JSONObject r3 = new org.json.JSONObject     // Catch:{ JSONException -> 0x00f8, Exception -> 0x00e6 }
            r3.<init>(r12)     // Catch:{ JSONException -> 0x00f8, Exception -> 0x00e6 }
            java.lang.String r4 = "name"
            java.lang.String r4 = r3.getString(r4)     // Catch:{ JSONException -> 0x00f8, Exception -> 0x00e6 }
            org.json.JSONObject r5 = new org.json.JSONObject     // Catch:{ JSONException -> 0x00f8, Exception -> 0x00e6 }
            r5.<init>()     // Catch:{ JSONException -> 0x00f8, Exception -> 0x00e6 }
            r6 = -1
            int r7 = r4.hashCode()     // Catch:{ JSONException -> 0x00f8, Exception -> 0x00e6 }
            r8 = 2
            r9 = 1
            switch(r7) {
                case 802033245: goto L_0x003a;
                case 802033246: goto L_0x0030;
                case 802033247: goto L_0x0026;
                default: goto L_0x0025;
            }     // Catch:{ JSONException -> 0x00f8, Exception -> 0x00e6 }
        L_0x0025:
            goto L_0x0043
        L_0x0026:
            java.lang.String r7 = "runPlan3"
            boolean r7 = r4.equals(r7)     // Catch:{ JSONException -> 0x00f8, Exception -> 0x00e6 }
            if (r7 == 0) goto L_0x0025
            r6 = r8
            goto L_0x0043
        L_0x0030:
            java.lang.String r7 = "runPlan2"
            boolean r7 = r4.equals(r7)     // Catch:{ JSONException -> 0x00f8, Exception -> 0x00e6 }
            if (r7 == 0) goto L_0x0025
            r6 = r9
            goto L_0x0043
        L_0x003a:
            java.lang.String r7 = "runPlan1"
            boolean r7 = r4.equals(r7)     // Catch:{ JSONException -> 0x00f8, Exception -> 0x00e6 }
            if (r7 == 0) goto L_0x0025
            r6 = 0
        L_0x0043:
            java.lang.String r7 = "error"
            if (r6 == 0) goto L_0x00b5
            if (r6 == r9) goto L_0x0084
            if (r6 == r8) goto L_0x0053
            gov.nasa.arc.astrobee.android.gs.MessageType r6 = gov.nasa.arc.astrobee.android.p000gs.MessageType.JSON     // Catch:{ JSONException -> 0x00f8, Exception -> 0x00e6 }
            java.lang.String r7 = "ERROR: Unrecognized command"
            r11.sendData((gov.nasa.arc.astrobee.android.p000gs.MessageType) r6, (java.lang.String) r1, (java.lang.String) r7)     // Catch:{ JSONException -> 0x00f8, Exception -> 0x00e6 }
            return
        L_0x0053:
            r11.runPlan3()     // Catch:{ Exception -> 0x0057, JSONException -> 0x00f8 }
            goto L_0x0083
        L_0x0057:
            r6 = move-exception
            org.json.JSONObject r8 = new org.json.JSONObject     // Catch:{ JSONException -> 0x00f8, Exception -> 0x00e6 }
            r8.<init>()     // Catch:{ JSONException -> 0x00f8, Exception -> 0x00e6 }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x00f8, Exception -> 0x00e6 }
            r9.<init>()     // Catch:{ JSONException -> 0x00f8, Exception -> 0x00e6 }
            r9.append(r2)     // Catch:{ JSONException -> 0x00f8, Exception -> 0x00e6 }
            java.lang.Class r10 = r6.getClass()     // Catch:{ JSONException -> 0x00f8, Exception -> 0x00e6 }
            java.lang.String r10 = r10.getName()     // Catch:{ JSONException -> 0x00f8, Exception -> 0x00e6 }
            r9.append(r10)     // Catch:{ JSONException -> 0x00f8, Exception -> 0x00e6 }
            java.lang.String r9 = r9.toString()     // Catch:{ JSONException -> 0x00f8, Exception -> 0x00e6 }
            r8.put(r7, r9)     // Catch:{ JSONException -> 0x00f8, Exception -> 0x00e6 }
            gov.nasa.arc.astrobee.android.gs.MessageType r7 = gov.nasa.arc.astrobee.android.p000gs.MessageType.JSON     // Catch:{ JSONException -> 0x00f8, Exception -> 0x00e6 }
            java.lang.String r9 = r8.toString()     // Catch:{ JSONException -> 0x00f8, Exception -> 0x00e6 }
            r11.sendData((gov.nasa.arc.astrobee.android.p000gs.MessageType) r7, (java.lang.String) r1, (java.lang.String) r9)     // Catch:{ JSONException -> 0x00f8, Exception -> 0x00e6 }
            android.util.Log.e(r0, r2, r6)     // Catch:{ JSONException -> 0x00f8, Exception -> 0x00e6 }
        L_0x0083:
            return
        L_0x0084:
            r11.runPlan2()     // Catch:{ Exception -> 0x0088, JSONException -> 0x00f8 }
            goto L_0x00b4
        L_0x0088:
            r6 = move-exception
            org.json.JSONObject r8 = new org.json.JSONObject     // Catch:{ JSONException -> 0x00f8, Exception -> 0x00e6 }
            r8.<init>()     // Catch:{ JSONException -> 0x00f8, Exception -> 0x00e6 }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x00f8, Exception -> 0x00e6 }
            r9.<init>()     // Catch:{ JSONException -> 0x00f8, Exception -> 0x00e6 }
            r9.append(r2)     // Catch:{ JSONException -> 0x00f8, Exception -> 0x00e6 }
            java.lang.Class r10 = r6.getClass()     // Catch:{ JSONException -> 0x00f8, Exception -> 0x00e6 }
            java.lang.String r10 = r10.getName()     // Catch:{ JSONException -> 0x00f8, Exception -> 0x00e6 }
            r9.append(r10)     // Catch:{ JSONException -> 0x00f8, Exception -> 0x00e6 }
            java.lang.String r9 = r9.toString()     // Catch:{ JSONException -> 0x00f8, Exception -> 0x00e6 }
            r8.put(r7, r9)     // Catch:{ JSONException -> 0x00f8, Exception -> 0x00e6 }
            gov.nasa.arc.astrobee.android.gs.MessageType r7 = gov.nasa.arc.astrobee.android.p000gs.MessageType.JSON     // Catch:{ JSONException -> 0x00f8, Exception -> 0x00e6 }
            java.lang.String r9 = r8.toString()     // Catch:{ JSONException -> 0x00f8, Exception -> 0x00e6 }
            r11.sendData((gov.nasa.arc.astrobee.android.p000gs.MessageType) r7, (java.lang.String) r1, (java.lang.String) r9)     // Catch:{ JSONException -> 0x00f8, Exception -> 0x00e6 }
            android.util.Log.e(r0, r2, r6)     // Catch:{ JSONException -> 0x00f8, Exception -> 0x00e6 }
        L_0x00b4:
            return
        L_0x00b5:
            r11.runPlan1()     // Catch:{ Exception -> 0x00b9, JSONException -> 0x00f8 }
            goto L_0x00e5
        L_0x00b9:
            r6 = move-exception
            org.json.JSONObject r8 = new org.json.JSONObject     // Catch:{ JSONException -> 0x00f8, Exception -> 0x00e6 }
            r8.<init>()     // Catch:{ JSONException -> 0x00f8, Exception -> 0x00e6 }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x00f8, Exception -> 0x00e6 }
            r9.<init>()     // Catch:{ JSONException -> 0x00f8, Exception -> 0x00e6 }
            r9.append(r2)     // Catch:{ JSONException -> 0x00f8, Exception -> 0x00e6 }
            java.lang.Class r10 = r6.getClass()     // Catch:{ JSONException -> 0x00f8, Exception -> 0x00e6 }
            java.lang.String r10 = r10.getName()     // Catch:{ JSONException -> 0x00f8, Exception -> 0x00e6 }
            r9.append(r10)     // Catch:{ JSONException -> 0x00f8, Exception -> 0x00e6 }
            java.lang.String r9 = r9.toString()     // Catch:{ JSONException -> 0x00f8, Exception -> 0x00e6 }
            r8.put(r7, r9)     // Catch:{ JSONException -> 0x00f8, Exception -> 0x00e6 }
            gov.nasa.arc.astrobee.android.gs.MessageType r7 = gov.nasa.arc.astrobee.android.p000gs.MessageType.JSON     // Catch:{ JSONException -> 0x00f8, Exception -> 0x00e6 }
            java.lang.String r9 = r8.toString()     // Catch:{ JSONException -> 0x00f8, Exception -> 0x00e6 }
            r11.sendData((gov.nasa.arc.astrobee.android.p000gs.MessageType) r7, (java.lang.String) r1, (java.lang.String) r9)     // Catch:{ JSONException -> 0x00f8, Exception -> 0x00e6 }
            android.util.Log.e(r0, r2, r6)     // Catch:{ JSONException -> 0x00f8, Exception -> 0x00e6 }
        L_0x00e5:
            return
        L_0x00e6:
            r3 = move-exception
            gov.nasa.arc.astrobee.android.gs.MessageType r4 = gov.nasa.arc.astrobee.android.p000gs.MessageType.JSON
            java.lang.Class r5 = r3.getClass()
            java.lang.String r5 = r5.getName()
            r11.sendData((gov.nasa.arc.astrobee.android.p000gs.MessageType) r4, (java.lang.String) r1, (java.lang.String) r5)
            android.util.Log.e(r0, r2, r3)
            goto L_0x010a
        L_0x00f8:
            r3 = move-exception
            gov.nasa.arc.astrobee.android.gs.MessageType r4 = gov.nasa.arc.astrobee.android.p000gs.MessageType.JSON
            java.lang.Class r5 = r3.getClass()
            java.lang.String r5 = r5.getName()
            r11.sendData((gov.nasa.arc.astrobee.android.p000gs.MessageType) r4, (java.lang.String) r1, (java.lang.String) r5)
            android.util.Log.e(r0, r2, r3)
        L_0x010a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: p001jp.jaxa.iss.kibo.rpc.api.KiboRpcService.onGuestScienceCustomCmd(java.lang.String):void");
    }

    public final void onGuestScienceStart() {
        System.setProperty("dns.server", Address.LOOPBACK);
        System.setProperty("dns.search", "iss");
        ResolverConfig.refresh();
        if (!OpenCVLoader.initDebug()) {
            Log.e("OpenCv", "Unable to load OpenCV");
        } else {
            Log.d("OpenCv", "OpenCV loaded");
        }
        this.api = KiboRpcApi.getInstance(this);
        sendStarted("info");
    }

    public final void onGuestScienceStop() {
        this.api.shutdownFactory();
        sendStopped("info");
        terminate();
    }

    /* access modifiers changed from: protected */
    public void runPlan1() {
    }

    /* access modifiers changed from: protected */
    public void runPlan2() {
    }

    /* access modifiers changed from: protected */
    public void runPlan3() {
    }
}
