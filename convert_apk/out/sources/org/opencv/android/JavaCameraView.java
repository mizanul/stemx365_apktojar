package org.opencv.android;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.util.Log;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class JavaCameraView extends CameraBridgeViewBase implements Camera.PreviewCallback {
    private static final int MAGIC_TEXTURE_ID = 10;
    private static final String TAG = "JavaCameraView";
    private byte[] mBuffer;
    protected Camera mCamera;
    protected JavaCameraFrame[] mCameraFrame;
    /* access modifiers changed from: private */
    public boolean mCameraFrameReady = false;
    /* access modifiers changed from: private */
    public int mChainIdx = 0;
    /* access modifiers changed from: private */
    public Mat[] mFrameChain;
    /* access modifiers changed from: private */
    public int mPreviewFormat = 17;
    /* access modifiers changed from: private */
    public boolean mStopThread;
    private SurfaceTexture mSurfaceTexture;
    private Thread mThread;

    public static class JavaCameraSizeAccessor implements CameraBridgeViewBase.ListItemAccessor {
        public int getWidth(Object obj) {
            return ((Camera.Size) obj).width;
        }

        public int getHeight(Object obj) {
            return ((Camera.Size) obj).height;
        }
    }

    public JavaCameraView(Context context, int cameraId) {
        super(context, cameraId);
    }

    public JavaCameraView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:107:0x0272 A[Catch:{ Exception -> 0x0330 }] */
    /* JADX WARNING: Removed duplicated region for block: B:108:0x0283 A[Catch:{ Exception -> 0x0330 }] */
    /* JADX WARNING: Removed duplicated region for block: B:111:0x028a A[Catch:{ Exception -> 0x0330 }] */
    /* JADX WARNING: Removed duplicated region for block: B:114:0x030b A[Catch:{ Exception -> 0x0330 }] */
    /* JADX WARNING: Removed duplicated region for block: B:115:0x031a A[Catch:{ Exception -> 0x0330 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean initializeCamera(int r17, int r18) {
        /*
            r16 = this;
            r1 = r16
            r2 = r17
            r3 = r18
            java.lang.String r0 = "JavaCameraView"
            java.lang.String r4 = "Initialize java camera"
            android.util.Log.d(r0, r4)
            r4 = 1
            monitor-enter(r16)
            r5 = 0
            r1.mCamera = r5     // Catch:{ all -> 0x0337 }
            int r0 = r1.mCameraIndex     // Catch:{ all -> 0x0337 }
            r6 = 9
            r7 = -1
            r8 = 1
            if (r0 != r7) goto L_0x00a8
            java.lang.String r0 = "JavaCameraView"
            java.lang.String r9 = "Trying to open camera with old open()"
            android.util.Log.d(r0, r9)     // Catch:{ all -> 0x0337 }
            android.hardware.Camera r0 = android.hardware.Camera.open()     // Catch:{ Exception -> 0x0028 }
            r1.mCamera = r0     // Catch:{ Exception -> 0x0028 }
            goto L_0x0043
        L_0x0028:
            r0 = move-exception
            java.lang.String r9 = "JavaCameraView"
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ all -> 0x0337 }
            r10.<init>()     // Catch:{ all -> 0x0337 }
            java.lang.String r11 = "Camera is not available (in use or does not exist): "
            r10.append(r11)     // Catch:{ all -> 0x0337 }
            java.lang.String r11 = r0.getLocalizedMessage()     // Catch:{ all -> 0x0337 }
            r10.append(r11)     // Catch:{ all -> 0x0337 }
            java.lang.String r10 = r10.toString()     // Catch:{ all -> 0x0337 }
            android.util.Log.e(r9, r10)     // Catch:{ all -> 0x0337 }
        L_0x0043:
            android.hardware.Camera r0 = r1.mCamera     // Catch:{ all -> 0x0337 }
            if (r0 != 0) goto L_0x0159
            int r0 = android.os.Build.VERSION.SDK_INT     // Catch:{ all -> 0x0337 }
            if (r0 < r6) goto L_0x0159
            r0 = 0
            r6 = 0
            r9 = r6
            r6 = r0
        L_0x004f:
            int r0 = android.hardware.Camera.getNumberOfCameras()     // Catch:{ all -> 0x0337 }
            if (r9 >= r0) goto L_0x00a6
            java.lang.String r0 = "JavaCameraView"
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ all -> 0x0337 }
            r10.<init>()     // Catch:{ all -> 0x0337 }
            java.lang.String r11 = "Trying to open camera with new open("
            r10.append(r11)     // Catch:{ all -> 0x0337 }
            java.lang.Integer r11 = java.lang.Integer.valueOf(r9)     // Catch:{ all -> 0x0337 }
            r10.append(r11)     // Catch:{ all -> 0x0337 }
            java.lang.String r11 = ")"
            r10.append(r11)     // Catch:{ all -> 0x0337 }
            java.lang.String r10 = r10.toString()     // Catch:{ all -> 0x0337 }
            android.util.Log.d(r0, r10)     // Catch:{ all -> 0x0337 }
            android.hardware.Camera r0 = android.hardware.Camera.open(r9)     // Catch:{ RuntimeException -> 0x007d }
            r1.mCamera = r0     // Catch:{ RuntimeException -> 0x007d }
            r0 = 1
            r6 = r0
            goto L_0x00a0
        L_0x007d:
            r0 = move-exception
            java.lang.String r10 = "JavaCameraView"
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ all -> 0x0337 }
            r11.<init>()     // Catch:{ all -> 0x0337 }
            java.lang.String r12 = "Camera #"
            r11.append(r12)     // Catch:{ all -> 0x0337 }
            r11.append(r9)     // Catch:{ all -> 0x0337 }
            java.lang.String r12 = "failed to open: "
            r11.append(r12)     // Catch:{ all -> 0x0337 }
            java.lang.String r12 = r0.getLocalizedMessage()     // Catch:{ all -> 0x0337 }
            r11.append(r12)     // Catch:{ all -> 0x0337 }
            java.lang.String r11 = r11.toString()     // Catch:{ all -> 0x0337 }
            android.util.Log.e(r10, r11)     // Catch:{ all -> 0x0337 }
        L_0x00a0:
            if (r6 == 0) goto L_0x00a3
            goto L_0x00a6
        L_0x00a3:
            int r9 = r9 + 1
            goto L_0x004f
        L_0x00a6:
            goto L_0x0159
        L_0x00a8:
            int r0 = android.os.Build.VERSION.SDK_INT     // Catch:{ all -> 0x0337 }
            if (r0 < r6) goto L_0x0159
            int r0 = r1.mCameraIndex     // Catch:{ all -> 0x0337 }
            int r6 = r1.mCameraIndex     // Catch:{ all -> 0x0337 }
            r9 = 98
            r10 = 99
            if (r6 != r10) goto L_0x00d7
            java.lang.String r6 = "JavaCameraView"
            java.lang.String r11 = "Trying to open back camera"
            android.util.Log.i(r6, r11)     // Catch:{ all -> 0x0337 }
            android.hardware.Camera$CameraInfo r6 = new android.hardware.Camera$CameraInfo     // Catch:{ all -> 0x0337 }
            r6.<init>()     // Catch:{ all -> 0x0337 }
            r11 = 0
        L_0x00c3:
            int r12 = android.hardware.Camera.getNumberOfCameras()     // Catch:{ all -> 0x0337 }
            if (r11 >= r12) goto L_0x00d5
            android.hardware.Camera.getCameraInfo(r11, r6)     // Catch:{ all -> 0x0337 }
            int r12 = r6.facing     // Catch:{ all -> 0x0337 }
            if (r12 != 0) goto L_0x00d2
            r0 = r11
            goto L_0x00d5
        L_0x00d2:
            int r11 = r11 + 1
            goto L_0x00c3
        L_0x00d5:
            r6 = r0
            goto L_0x00fc
        L_0x00d7:
            int r6 = r1.mCameraIndex     // Catch:{ all -> 0x0337 }
            if (r6 != r9) goto L_0x00fb
            java.lang.String r6 = "JavaCameraView"
            java.lang.String r11 = "Trying to open front camera"
            android.util.Log.i(r6, r11)     // Catch:{ all -> 0x0337 }
            android.hardware.Camera$CameraInfo r6 = new android.hardware.Camera$CameraInfo     // Catch:{ all -> 0x0337 }
            r6.<init>()     // Catch:{ all -> 0x0337 }
            r11 = 0
        L_0x00e8:
            int r12 = android.hardware.Camera.getNumberOfCameras()     // Catch:{ all -> 0x0337 }
            if (r11 >= r12) goto L_0x00fb
            android.hardware.Camera.getCameraInfo(r11, r6)     // Catch:{ all -> 0x0337 }
            int r12 = r6.facing     // Catch:{ all -> 0x0337 }
            if (r12 != r8) goto L_0x00f8
            r0 = r11
            r6 = r0
            goto L_0x00fc
        L_0x00f8:
            int r11 = r11 + 1
            goto L_0x00e8
        L_0x00fb:
            r6 = r0
        L_0x00fc:
            if (r6 != r10) goto L_0x0106
            java.lang.String r0 = "JavaCameraView"
            java.lang.String r9 = "Back camera not found!"
            android.util.Log.e(r0, r9)     // Catch:{ all -> 0x0337 }
            goto L_0x0159
        L_0x0106:
            if (r6 != r9) goto L_0x0110
            java.lang.String r0 = "JavaCameraView"
            java.lang.String r9 = "Front camera not found!"
            android.util.Log.e(r0, r9)     // Catch:{ all -> 0x0337 }
            goto L_0x0159
        L_0x0110:
            java.lang.String r0 = "JavaCameraView"
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ all -> 0x0337 }
            r9.<init>()     // Catch:{ all -> 0x0337 }
            java.lang.String r10 = "Trying to open camera with new open("
            r9.append(r10)     // Catch:{ all -> 0x0337 }
            java.lang.Integer r10 = java.lang.Integer.valueOf(r6)     // Catch:{ all -> 0x0337 }
            r9.append(r10)     // Catch:{ all -> 0x0337 }
            java.lang.String r10 = ")"
            r9.append(r10)     // Catch:{ all -> 0x0337 }
            java.lang.String r9 = r9.toString()     // Catch:{ all -> 0x0337 }
            android.util.Log.d(r0, r9)     // Catch:{ all -> 0x0337 }
            android.hardware.Camera r0 = android.hardware.Camera.open(r6)     // Catch:{ RuntimeException -> 0x0136 }
            r1.mCamera = r0     // Catch:{ RuntimeException -> 0x0136 }
            goto L_0x0159
        L_0x0136:
            r0 = move-exception
            java.lang.String r9 = "JavaCameraView"
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ all -> 0x0337 }
            r10.<init>()     // Catch:{ all -> 0x0337 }
            java.lang.String r11 = "Camera #"
            r10.append(r11)     // Catch:{ all -> 0x0337 }
            r10.append(r6)     // Catch:{ all -> 0x0337 }
            java.lang.String r11 = "failed to open: "
            r10.append(r11)     // Catch:{ all -> 0x0337 }
            java.lang.String r11 = r0.getLocalizedMessage()     // Catch:{ all -> 0x0337 }
            r10.append(r11)     // Catch:{ all -> 0x0337 }
            java.lang.String r10 = r10.toString()     // Catch:{ all -> 0x0337 }
            android.util.Log.e(r9, r10)     // Catch:{ all -> 0x0337 }
        L_0x0159:
            android.hardware.Camera r0 = r1.mCamera     // Catch:{ all -> 0x0337 }
            r6 = 0
            if (r0 != 0) goto L_0x0160
            monitor-exit(r16)     // Catch:{ all -> 0x0337 }
            return r6
        L_0x0160:
            android.hardware.Camera r0 = r1.mCamera     // Catch:{ Exception -> 0x0330 }
            android.hardware.Camera$Parameters r0 = r0.getParameters()     // Catch:{ Exception -> 0x0330 }
            java.lang.String r9 = "JavaCameraView"
            java.lang.String r10 = "getSupportedPreviewSizes()"
            android.util.Log.d(r9, r10)     // Catch:{ Exception -> 0x0330 }
            java.util.List r9 = r0.getSupportedPreviewSizes()     // Catch:{ Exception -> 0x0330 }
            if (r9 == 0) goto L_0x032e
            org.opencv.android.JavaCameraView$JavaCameraSizeAccessor r10 = new org.opencv.android.JavaCameraView$JavaCameraSizeAccessor     // Catch:{ Exception -> 0x0330 }
            r10.<init>()     // Catch:{ Exception -> 0x0330 }
            org.opencv.core.Size r10 = r1.calculateCameraFrameSize(r9, r10, r2, r3)     // Catch:{ Exception -> 0x0330 }
            java.lang.String r11 = android.os.Build.FINGERPRINT     // Catch:{ Exception -> 0x0330 }
            java.lang.String r12 = "generic"
            boolean r11 = r11.startsWith(r12)     // Catch:{ Exception -> 0x0330 }
            if (r11 != 0) goto L_0x01de
            java.lang.String r11 = android.os.Build.FINGERPRINT     // Catch:{ Exception -> 0x0330 }
            java.lang.String r12 = "unknown"
            boolean r11 = r11.startsWith(r12)     // Catch:{ Exception -> 0x0330 }
            if (r11 != 0) goto L_0x01de
            java.lang.String r11 = android.os.Build.MODEL     // Catch:{ Exception -> 0x0330 }
            java.lang.String r12 = "google_sdk"
            boolean r11 = r11.contains(r12)     // Catch:{ Exception -> 0x0330 }
            if (r11 != 0) goto L_0x01de
            java.lang.String r11 = android.os.Build.MODEL     // Catch:{ Exception -> 0x0330 }
            java.lang.String r12 = "Emulator"
            boolean r11 = r11.contains(r12)     // Catch:{ Exception -> 0x0330 }
            if (r11 != 0) goto L_0x01de
            java.lang.String r11 = android.os.Build.MODEL     // Catch:{ Exception -> 0x0330 }
            java.lang.String r12 = "Android SDK built for x86"
            boolean r11 = r11.contains(r12)     // Catch:{ Exception -> 0x0330 }
            if (r11 != 0) goto L_0x01de
            java.lang.String r11 = android.os.Build.MANUFACTURER     // Catch:{ Exception -> 0x0330 }
            java.lang.String r12 = "Genymotion"
            boolean r11 = r11.contains(r12)     // Catch:{ Exception -> 0x0330 }
            if (r11 != 0) goto L_0x01de
            java.lang.String r11 = android.os.Build.BRAND     // Catch:{ Exception -> 0x0330 }
            java.lang.String r12 = "generic"
            boolean r11 = r11.startsWith(r12)     // Catch:{ Exception -> 0x0330 }
            if (r11 == 0) goto L_0x01cd
            java.lang.String r11 = android.os.Build.DEVICE     // Catch:{ Exception -> 0x0330 }
            java.lang.String r12 = "generic"
            boolean r11 = r11.startsWith(r12)     // Catch:{ Exception -> 0x0330 }
            if (r11 != 0) goto L_0x01de
        L_0x01cd:
            java.lang.String r11 = "google_sdk"
            java.lang.String r12 = android.os.Build.PRODUCT     // Catch:{ Exception -> 0x0330 }
            boolean r11 = r11.equals(r12)     // Catch:{ Exception -> 0x0330 }
            if (r11 == 0) goto L_0x01d8
            goto L_0x01de
        L_0x01d8:
            r11 = 17
            r0.setPreviewFormat(r11)     // Catch:{ Exception -> 0x0330 }
            goto L_0x01e4
        L_0x01de:
            r11 = 842094169(0x32315659, float:1.0322389E-8)
            r0.setPreviewFormat(r11)     // Catch:{ Exception -> 0x0330 }
        L_0x01e4:
            int r11 = r0.getPreviewFormat()     // Catch:{ Exception -> 0x0330 }
            r1.mPreviewFormat = r11     // Catch:{ Exception -> 0x0330 }
            java.lang.String r11 = "JavaCameraView"
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0330 }
            r12.<init>()     // Catch:{ Exception -> 0x0330 }
            java.lang.String r13 = "Set preview size to "
            r12.append(r13)     // Catch:{ Exception -> 0x0330 }
            double r13 = r10.width     // Catch:{ Exception -> 0x0330 }
            int r13 = (int) r13     // Catch:{ Exception -> 0x0330 }
            java.lang.Integer r13 = java.lang.Integer.valueOf(r13)     // Catch:{ Exception -> 0x0330 }
            r12.append(r13)     // Catch:{ Exception -> 0x0330 }
            java.lang.String r13 = "x"
            r12.append(r13)     // Catch:{ Exception -> 0x0330 }
            double r13 = r10.height     // Catch:{ Exception -> 0x0330 }
            int r13 = (int) r13     // Catch:{ Exception -> 0x0330 }
            java.lang.Integer r13 = java.lang.Integer.valueOf(r13)     // Catch:{ Exception -> 0x0330 }
            r12.append(r13)     // Catch:{ Exception -> 0x0330 }
            java.lang.String r12 = r12.toString()     // Catch:{ Exception -> 0x0330 }
            android.util.Log.d(r11, r12)     // Catch:{ Exception -> 0x0330 }
            double r11 = r10.width     // Catch:{ Exception -> 0x0330 }
            int r11 = (int) r11     // Catch:{ Exception -> 0x0330 }
            double r12 = r10.height     // Catch:{ Exception -> 0x0330 }
            int r12 = (int) r12     // Catch:{ Exception -> 0x0330 }
            r0.setPreviewSize(r11, r12)     // Catch:{ Exception -> 0x0330 }
            int r11 = android.os.Build.VERSION.SDK_INT     // Catch:{ Exception -> 0x0330 }
            r12 = 14
            if (r11 < r12) goto L_0x0233
            java.lang.String r11 = android.os.Build.MODEL     // Catch:{ Exception -> 0x0330 }
            java.lang.String r12 = "GT-I9100"
            boolean r11 = r11.equals(r12)     // Catch:{ Exception -> 0x0330 }
            if (r11 != 0) goto L_0x0233
            r0.setRecordingHint(r8)     // Catch:{ Exception -> 0x0330 }
        L_0x0233:
            java.util.List r11 = r0.getSupportedFocusModes()     // Catch:{ Exception -> 0x0330 }
            if (r11 == 0) goto L_0x0246
            java.lang.String r12 = "continuous-video"
            boolean r12 = r11.contains(r12)     // Catch:{ Exception -> 0x0330 }
            if (r12 == 0) goto L_0x0246
            java.lang.String r12 = "continuous-video"
            r0.setFocusMode(r12)     // Catch:{ Exception -> 0x0330 }
        L_0x0246:
            android.hardware.Camera r12 = r1.mCamera     // Catch:{ Exception -> 0x0330 }
            r12.setParameters(r0)     // Catch:{ Exception -> 0x0330 }
            android.hardware.Camera r12 = r1.mCamera     // Catch:{ Exception -> 0x0330 }
            android.hardware.Camera$Parameters r12 = r12.getParameters()     // Catch:{ Exception -> 0x0330 }
            r0 = r12
            android.hardware.Camera$Size r12 = r0.getPreviewSize()     // Catch:{ Exception -> 0x0330 }
            int r12 = r12.width     // Catch:{ Exception -> 0x0330 }
            r1.mFrameWidth = r12     // Catch:{ Exception -> 0x0330 }
            android.hardware.Camera$Size r12 = r0.getPreviewSize()     // Catch:{ Exception -> 0x0330 }
            int r12 = r12.height     // Catch:{ Exception -> 0x0330 }
            r1.mFrameHeight = r12     // Catch:{ Exception -> 0x0330 }
            android.view.ViewGroup$LayoutParams r12 = r16.getLayoutParams()     // Catch:{ Exception -> 0x0330 }
            int r12 = r12.width     // Catch:{ Exception -> 0x0330 }
            if (r12 != r7) goto L_0x0283
            android.view.ViewGroup$LayoutParams r12 = r16.getLayoutParams()     // Catch:{ Exception -> 0x0330 }
            int r12 = r12.height     // Catch:{ Exception -> 0x0330 }
            if (r12 != r7) goto L_0x0283
            float r7 = (float) r3     // Catch:{ Exception -> 0x0330 }
            int r12 = r1.mFrameHeight     // Catch:{ Exception -> 0x0330 }
            float r12 = (float) r12     // Catch:{ Exception -> 0x0330 }
            float r7 = r7 / r12
            float r12 = (float) r2     // Catch:{ Exception -> 0x0330 }
            int r13 = r1.mFrameWidth     // Catch:{ Exception -> 0x0330 }
            float r13 = (float) r13     // Catch:{ Exception -> 0x0330 }
            float r12 = r12 / r13
            float r7 = java.lang.Math.min(r7, r12)     // Catch:{ Exception -> 0x0330 }
            r1.mScale = r7     // Catch:{ Exception -> 0x0330 }
            goto L_0x0286
        L_0x0283:
            r7 = 0
            r1.mScale = r7     // Catch:{ Exception -> 0x0330 }
        L_0x0286:
            org.opencv.android.FpsMeter r7 = r1.mFpsMeter     // Catch:{ Exception -> 0x0330 }
            if (r7 == 0) goto L_0x0293
            org.opencv.android.FpsMeter r7 = r1.mFpsMeter     // Catch:{ Exception -> 0x0330 }
            int r12 = r1.mFrameWidth     // Catch:{ Exception -> 0x0330 }
            int r13 = r1.mFrameHeight     // Catch:{ Exception -> 0x0330 }
            r7.setResolution(r12, r13)     // Catch:{ Exception -> 0x0330 }
        L_0x0293:
            int r7 = r1.mFrameWidth     // Catch:{ Exception -> 0x0330 }
            int r12 = r1.mFrameHeight     // Catch:{ Exception -> 0x0330 }
            int r7 = r7 * r12
            int r12 = r0.getPreviewFormat()     // Catch:{ Exception -> 0x0330 }
            int r12 = android.graphics.ImageFormat.getBitsPerPixel(r12)     // Catch:{ Exception -> 0x0330 }
            int r12 = r12 * r7
            int r12 = r12 / 8
            r7 = r12
            byte[] r12 = new byte[r7]     // Catch:{ Exception -> 0x0330 }
            r1.mBuffer = r12     // Catch:{ Exception -> 0x0330 }
            android.hardware.Camera r13 = r1.mCamera     // Catch:{ Exception -> 0x0330 }
            r13.addCallbackBuffer(r12)     // Catch:{ Exception -> 0x0330 }
            android.hardware.Camera r12 = r1.mCamera     // Catch:{ Exception -> 0x0330 }
            r12.setPreviewCallbackWithBuffer(r1)     // Catch:{ Exception -> 0x0330 }
            r12 = 2
            org.opencv.core.Mat[] r13 = new org.opencv.core.Mat[r12]     // Catch:{ Exception -> 0x0330 }
            r1.mFrameChain = r13     // Catch:{ Exception -> 0x0330 }
            org.opencv.core.Mat r14 = new org.opencv.core.Mat     // Catch:{ Exception -> 0x0330 }
            int r15 = r1.mFrameHeight     // Catch:{ Exception -> 0x0330 }
            int r5 = r1.mFrameHeight     // Catch:{ Exception -> 0x0330 }
            int r5 = r5 / r12
            int r15 = r15 + r5
            int r5 = r1.mFrameWidth     // Catch:{ Exception -> 0x0330 }
            int r8 = org.opencv.core.CvType.CV_8UC1     // Catch:{ Exception -> 0x0330 }
            r14.<init>((int) r15, (int) r5, (int) r8)     // Catch:{ Exception -> 0x0330 }
            r13[r6] = r14     // Catch:{ Exception -> 0x0330 }
            org.opencv.core.Mat[] r5 = r1.mFrameChain     // Catch:{ Exception -> 0x0330 }
            org.opencv.core.Mat r8 = new org.opencv.core.Mat     // Catch:{ Exception -> 0x0330 }
            int r13 = r1.mFrameHeight     // Catch:{ Exception -> 0x0330 }
            int r14 = r1.mFrameHeight     // Catch:{ Exception -> 0x0330 }
            int r14 = r14 / r12
            int r13 = r13 + r14
            int r14 = r1.mFrameWidth     // Catch:{ Exception -> 0x0330 }
            int r15 = org.opencv.core.CvType.CV_8UC1     // Catch:{ Exception -> 0x0330 }
            r8.<init>((int) r13, (int) r14, (int) r15)     // Catch:{ Exception -> 0x0330 }
            r13 = 1
            r5[r13] = r8     // Catch:{ Exception -> 0x0330 }
            r16.AllocateCache()     // Catch:{ Exception -> 0x0330 }
            org.opencv.android.JavaCameraView$JavaCameraFrame[] r5 = new org.opencv.android.JavaCameraView.JavaCameraFrame[r12]     // Catch:{ Exception -> 0x0330 }
            r1.mCameraFrame = r5     // Catch:{ Exception -> 0x0330 }
            org.opencv.android.JavaCameraView$JavaCameraFrame r8 = new org.opencv.android.JavaCameraView$JavaCameraFrame     // Catch:{ Exception -> 0x0330 }
            org.opencv.core.Mat[] r12 = r1.mFrameChain     // Catch:{ Exception -> 0x0330 }
            r12 = r12[r6]     // Catch:{ Exception -> 0x0330 }
            int r13 = r1.mFrameWidth     // Catch:{ Exception -> 0x0330 }
            int r14 = r1.mFrameHeight     // Catch:{ Exception -> 0x0330 }
            r8.<init>(r12, r13, r14)     // Catch:{ Exception -> 0x0330 }
            r5[r6] = r8     // Catch:{ Exception -> 0x0330 }
            org.opencv.android.JavaCameraView$JavaCameraFrame[] r5 = r1.mCameraFrame     // Catch:{ Exception -> 0x0330 }
            org.opencv.android.JavaCameraView$JavaCameraFrame r6 = new org.opencv.android.JavaCameraView$JavaCameraFrame     // Catch:{ Exception -> 0x0330 }
            org.opencv.core.Mat[] r8 = r1.mFrameChain     // Catch:{ Exception -> 0x0330 }
            r12 = 1
            r8 = r8[r12]     // Catch:{ Exception -> 0x0330 }
            int r12 = r1.mFrameWidth     // Catch:{ Exception -> 0x0330 }
            int r13 = r1.mFrameHeight     // Catch:{ Exception -> 0x0330 }
            r6.<init>(r8, r12, r13)     // Catch:{ Exception -> 0x0330 }
            r8 = 1
            r5[r8] = r6     // Catch:{ Exception -> 0x0330 }
            int r5 = android.os.Build.VERSION.SDK_INT     // Catch:{ Exception -> 0x0330 }
            r6 = 11
            if (r5 < r6) goto L_0x031a
            android.graphics.SurfaceTexture r5 = new android.graphics.SurfaceTexture     // Catch:{ Exception -> 0x0330 }
            r6 = 10
            r5.<init>(r6)     // Catch:{ Exception -> 0x0330 }
            r1.mSurfaceTexture = r5     // Catch:{ Exception -> 0x0330 }
            android.hardware.Camera r6 = r1.mCamera     // Catch:{ Exception -> 0x0330 }
            r6.setPreviewTexture(r5)     // Catch:{ Exception -> 0x0330 }
            goto L_0x0320
        L_0x031a:
            android.hardware.Camera r5 = r1.mCamera     // Catch:{ Exception -> 0x0330 }
            r6 = 0
            r5.setPreviewDisplay(r6)     // Catch:{ Exception -> 0x0330 }
        L_0x0320:
            java.lang.String r5 = "JavaCameraView"
            java.lang.String r6 = "startPreview"
            android.util.Log.d(r5, r6)     // Catch:{ Exception -> 0x0330 }
            android.hardware.Camera r5 = r1.mCamera     // Catch:{ Exception -> 0x0330 }
            r5.startPreview()     // Catch:{ Exception -> 0x0330 }
            goto L_0x032f
        L_0x032e:
            r4 = 0
        L_0x032f:
            goto L_0x0335
        L_0x0330:
            r0 = move-exception
            r4 = 0
            r0.printStackTrace()     // Catch:{ all -> 0x0337 }
        L_0x0335:
            monitor-exit(r16)     // Catch:{ all -> 0x0337 }
            return r4
        L_0x0337:
            r0 = move-exception
            monitor-exit(r16)     // Catch:{ all -> 0x0337 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.opencv.android.JavaCameraView.initializeCamera(int, int):boolean");
    }

    /* access modifiers changed from: protected */
    public void releaseCamera() {
        synchronized (this) {
            if (this.mCamera != null) {
                this.mCamera.stopPreview();
                this.mCamera.setPreviewCallback((Camera.PreviewCallback) null);
                this.mCamera.release();
            }
            this.mCamera = null;
            if (this.mFrameChain != null) {
                this.mFrameChain[0].release();
                this.mFrameChain[1].release();
            }
            if (this.mCameraFrame != null) {
                this.mCameraFrame[0].release();
                this.mCameraFrame[1].release();
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean connectCamera(int width, int height) {
        Log.d(TAG, "Connecting to camera");
        if (!initializeCamera(width, height)) {
            return false;
        }
        this.mCameraFrameReady = false;
        Log.d(TAG, "Starting processing thread");
        this.mStopThread = false;
        Thread thread = new Thread(new CameraWorker());
        this.mThread = thread;
        thread.start();
        return true;
    }

    /* access modifiers changed from: protected */
    public void disconnectCamera() {
        Log.d(TAG, "Disconnecting from camera");
        try {
            this.mStopThread = true;
            Log.d(TAG, "Notify thread");
            synchronized (this) {
                notify();
            }
            Log.d(TAG, "Waiting for thread");
            if (this.mThread != null) {
                this.mThread.join();
            }
        } catch (InterruptedException e) {
            try {
                e.printStackTrace();
            } catch (Throwable th) {
                this.mThread = null;
                throw th;
            }
        }
        this.mThread = null;
        releaseCamera();
        this.mCameraFrameReady = false;
    }

    public void onPreviewFrame(byte[] frame, Camera arg1) {
        synchronized (this) {
            this.mFrameChain[this.mChainIdx].put(0, 0, frame);
            this.mCameraFrameReady = true;
            notify();
        }
        Camera camera = this.mCamera;
        if (camera != null) {
            camera.addCallbackBuffer(this.mBuffer);
        }
    }

    private class JavaCameraFrame implements CameraBridgeViewBase.CvCameraViewFrame {
        private int mHeight;
        private Mat mRgba = new Mat();
        private int mWidth;
        private Mat mYuvFrameData;

        public Mat gray() {
            return this.mYuvFrameData.submat(0, this.mHeight, 0, this.mWidth);
        }

        public Mat rgba() {
            if (JavaCameraView.this.mPreviewFormat == 17) {
                Imgproc.cvtColor(this.mYuvFrameData, this.mRgba, 96, 4);
            } else if (JavaCameraView.this.mPreviewFormat == 842094169) {
                Imgproc.cvtColor(this.mYuvFrameData, this.mRgba, 100, 4);
            } else {
                throw new IllegalArgumentException("Preview Format can be NV21 or YV12");
            }
            return this.mRgba;
        }

        public JavaCameraFrame(Mat Yuv420sp, int width, int height) {
            this.mWidth = width;
            this.mHeight = height;
            this.mYuvFrameData = Yuv420sp;
        }

        public void release() {
            this.mRgba.release();
        }
    }

    private class CameraWorker implements Runnable {
        private CameraWorker() {
        }

        public void run() {
            do {
                boolean hasFrame = false;
                synchronized (JavaCameraView.this) {
                    while (!JavaCameraView.this.mCameraFrameReady && !JavaCameraView.this.mStopThread) {
                        try {
                            JavaCameraView.this.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if (JavaCameraView.this.mCameraFrameReady) {
                        int unused = JavaCameraView.this.mChainIdx = 1 - JavaCameraView.this.mChainIdx;
                        boolean unused2 = JavaCameraView.this.mCameraFrameReady = false;
                        hasFrame = true;
                    }
                }
                if (!JavaCameraView.this.mStopThread && hasFrame && !JavaCameraView.this.mFrameChain[1 - JavaCameraView.this.mChainIdx].empty()) {
                    JavaCameraView javaCameraView = JavaCameraView.this;
                    javaCameraView.deliverAndDrawFrame(javaCameraView.mCameraFrame[1 - JavaCameraView.this.mChainIdx]);
                }
            } while (!JavaCameraView.this.mStopThread);
            Log.d(JavaCameraView.TAG, "Finish processing thread");
        }
    }
}
