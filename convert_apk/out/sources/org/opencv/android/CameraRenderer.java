package org.opencv.android;

import android.hardware.Camera;
import android.util.Log;

public class CameraRenderer extends CameraGLRendererBase {
    public static final String LOGTAG = "CameraRenderer";
    private Camera mCamera;
    private boolean mPreviewStarted = false;

    CameraRenderer(CameraGLSurfaceView view) {
        super(view);
    }

    /* access modifiers changed from: protected */
    public synchronized void closeCamera() {
        Log.i(LOGTAG, "closeCamera");
        if (this.mCamera != null) {
            this.mCamera.stopPreview();
            this.mPreviewStarted = false;
            this.mCamera.release();
            this.mCamera = null;
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00eb A[Catch:{ Exception -> 0x001e }] */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x00f3 A[Catch:{ Exception -> 0x001e }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void openCamera(int r8) {
        /*
            r7 = this;
            monitor-enter(r7)
            java.lang.String r0 = "CameraRenderer"
            java.lang.String r1 = "openCamera"
            android.util.Log.i(r0, r1)     // Catch:{ all -> 0x0192 }
            r7.closeCamera()     // Catch:{ all -> 0x0192 }
            r0 = -1
            r1 = 9
            if (r8 != r0) goto L_0x0097
            java.lang.String r0 = "CameraRenderer"
            java.lang.String r2 = "Trying to open camera with old open()"
            android.util.Log.d(r0, r2)     // Catch:{ all -> 0x0192 }
            android.hardware.Camera r0 = android.hardware.Camera.open()     // Catch:{ Exception -> 0x001e }
            r7.mCamera = r0     // Catch:{ Exception -> 0x001e }
            goto L_0x0039
        L_0x001e:
            r0 = move-exception
            java.lang.String r2 = "CameraRenderer"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0192 }
            r3.<init>()     // Catch:{ all -> 0x0192 }
            java.lang.String r4 = "Camera is not available (in use or does not exist): "
            r3.append(r4)     // Catch:{ all -> 0x0192 }
            java.lang.String r4 = r0.getLocalizedMessage()     // Catch:{ all -> 0x0192 }
            r3.append(r4)     // Catch:{ all -> 0x0192 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x0192 }
            android.util.Log.e(r2, r3)     // Catch:{ all -> 0x0192 }
        L_0x0039:
            android.hardware.Camera r0 = r7.mCamera     // Catch:{ all -> 0x0192 }
            if (r0 != 0) goto L_0x0142
            int r0 = android.os.Build.VERSION.SDK_INT     // Catch:{ all -> 0x0192 }
            if (r0 < r1) goto L_0x0142
            r0 = 0
            r1 = 0
        L_0x0043:
            int r2 = android.hardware.Camera.getNumberOfCameras()     // Catch:{ all -> 0x0192 }
            if (r1 >= r2) goto L_0x0095
            java.lang.String r2 = "CameraRenderer"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0192 }
            r3.<init>()     // Catch:{ all -> 0x0192 }
            java.lang.String r4 = "Trying to open camera with new open("
            r3.append(r4)     // Catch:{ all -> 0x0192 }
            r3.append(r1)     // Catch:{ all -> 0x0192 }
            java.lang.String r4 = ")"
            r3.append(r4)     // Catch:{ all -> 0x0192 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x0192 }
            android.util.Log.d(r2, r3)     // Catch:{ all -> 0x0192 }
            android.hardware.Camera r2 = android.hardware.Camera.open(r1)     // Catch:{ RuntimeException -> 0x006c }
            r7.mCamera = r2     // Catch:{ RuntimeException -> 0x006c }
            r0 = 1
            goto L_0x008f
        L_0x006c:
            r2 = move-exception
            java.lang.String r3 = "CameraRenderer"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0192 }
            r4.<init>()     // Catch:{ all -> 0x0192 }
            java.lang.String r5 = "Camera #"
            r4.append(r5)     // Catch:{ all -> 0x0192 }
            r4.append(r1)     // Catch:{ all -> 0x0192 }
            java.lang.String r5 = "failed to open: "
            r4.append(r5)     // Catch:{ all -> 0x0192 }
            java.lang.String r5 = r2.getLocalizedMessage()     // Catch:{ all -> 0x0192 }
            r4.append(r5)     // Catch:{ all -> 0x0192 }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x0192 }
            android.util.Log.e(r3, r4)     // Catch:{ all -> 0x0192 }
        L_0x008f:
            if (r0 == 0) goto L_0x0092
            goto L_0x0095
        L_0x0092:
            int r1 = r1 + 1
            goto L_0x0043
        L_0x0095:
            goto L_0x0142
        L_0x0097:
            int r0 = android.os.Build.VERSION.SDK_INT     // Catch:{ all -> 0x0192 }
            if (r0 < r1) goto L_0x0142
            int r0 = r7.mCameraIndex     // Catch:{ all -> 0x0192 }
            int r1 = r7.mCameraIndex     // Catch:{ all -> 0x0192 }
            r2 = 98
            r3 = 99
            if (r1 != r3) goto L_0x00c5
            java.lang.String r1 = "CameraRenderer"
            java.lang.String r4 = "Trying to open BACK camera"
            android.util.Log.i(r1, r4)     // Catch:{ all -> 0x0192 }
            android.hardware.Camera$CameraInfo r1 = new android.hardware.Camera$CameraInfo     // Catch:{ all -> 0x0192 }
            r1.<init>()     // Catch:{ all -> 0x0192 }
            r4 = 0
        L_0x00b2:
            int r5 = android.hardware.Camera.getNumberOfCameras()     // Catch:{ all -> 0x0192 }
            if (r4 >= r5) goto L_0x00c4
            android.hardware.Camera.getCameraInfo(r4, r1)     // Catch:{ all -> 0x0192 }
            int r5 = r1.facing     // Catch:{ all -> 0x0192 }
            if (r5 != 0) goto L_0x00c1
            r0 = r4
            goto L_0x00c4
        L_0x00c1:
            int r4 = r4 + 1
            goto L_0x00b2
        L_0x00c4:
            goto L_0x00e9
        L_0x00c5:
            int r1 = r7.mCameraIndex     // Catch:{ all -> 0x0192 }
            if (r1 != r2) goto L_0x00c4
            java.lang.String r1 = "CameraRenderer"
            java.lang.String r4 = "Trying to open FRONT camera"
            android.util.Log.i(r1, r4)     // Catch:{ all -> 0x0192 }
            android.hardware.Camera$CameraInfo r1 = new android.hardware.Camera$CameraInfo     // Catch:{ all -> 0x0192 }
            r1.<init>()     // Catch:{ all -> 0x0192 }
            r4 = 0
        L_0x00d6:
            int r5 = android.hardware.Camera.getNumberOfCameras()     // Catch:{ all -> 0x0192 }
            if (r4 >= r5) goto L_0x00e9
            android.hardware.Camera.getCameraInfo(r4, r1)     // Catch:{ all -> 0x0192 }
            int r5 = r1.facing     // Catch:{ all -> 0x0192 }
            r6 = 1
            if (r5 != r6) goto L_0x00e6
            r0 = r4
            goto L_0x00e9
        L_0x00e6:
            int r4 = r4 + 1
            goto L_0x00d6
        L_0x00e9:
            if (r0 != r3) goto L_0x00f3
            java.lang.String r1 = "CameraRenderer"
            java.lang.String r2 = "Back camera not found!"
            android.util.Log.e(r1, r2)     // Catch:{ all -> 0x0192 }
            goto L_0x0142
        L_0x00f3:
            if (r0 != r2) goto L_0x00fd
            java.lang.String r1 = "CameraRenderer"
            java.lang.String r2 = "Front camera not found!"
            android.util.Log.e(r1, r2)     // Catch:{ all -> 0x0192 }
            goto L_0x0142
        L_0x00fd:
            java.lang.String r1 = "CameraRenderer"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0192 }
            r2.<init>()     // Catch:{ all -> 0x0192 }
            java.lang.String r3 = "Trying to open camera with new open("
            r2.append(r3)     // Catch:{ all -> 0x0192 }
            r2.append(r0)     // Catch:{ all -> 0x0192 }
            java.lang.String r3 = ")"
            r2.append(r3)     // Catch:{ all -> 0x0192 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x0192 }
            android.util.Log.d(r1, r2)     // Catch:{ all -> 0x0192 }
            android.hardware.Camera r1 = android.hardware.Camera.open(r0)     // Catch:{ RuntimeException -> 0x011f }
            r7.mCamera = r1     // Catch:{ RuntimeException -> 0x011f }
            goto L_0x0142
        L_0x011f:
            r1 = move-exception
            java.lang.String r2 = "CameraRenderer"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0192 }
            r3.<init>()     // Catch:{ all -> 0x0192 }
            java.lang.String r4 = "Camera #"
            r3.append(r4)     // Catch:{ all -> 0x0192 }
            r3.append(r0)     // Catch:{ all -> 0x0192 }
            java.lang.String r4 = "failed to open: "
            r3.append(r4)     // Catch:{ all -> 0x0192 }
            java.lang.String r4 = r1.getLocalizedMessage()     // Catch:{ all -> 0x0192 }
            r3.append(r4)     // Catch:{ all -> 0x0192 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x0192 }
            android.util.Log.e(r2, r3)     // Catch:{ all -> 0x0192 }
        L_0x0142:
            android.hardware.Camera r0 = r7.mCamera     // Catch:{ all -> 0x0192 }
            if (r0 != 0) goto L_0x014f
            java.lang.String r0 = "CameraRenderer"
            java.lang.String r1 = "Error: can't open camera"
            android.util.Log.e(r0, r1)     // Catch:{ all -> 0x0192 }
            monitor-exit(r7)
            return
        L_0x014f:
            android.hardware.Camera r0 = r7.mCamera     // Catch:{ all -> 0x0192 }
            android.hardware.Camera$Parameters r0 = r0.getParameters()     // Catch:{ all -> 0x0192 }
            java.util.List r1 = r0.getSupportedFocusModes()     // Catch:{ all -> 0x0192 }
            if (r1 == 0) goto L_0x0168
            java.lang.String r2 = "continuous-video"
            boolean r2 = r1.contains(r2)     // Catch:{ all -> 0x0192 }
            if (r2 == 0) goto L_0x0168
            java.lang.String r2 = "continuous-video"
            r0.setFocusMode(r2)     // Catch:{ all -> 0x0192 }
        L_0x0168:
            android.hardware.Camera r2 = r7.mCamera     // Catch:{ all -> 0x0192 }
            r2.setParameters(r0)     // Catch:{ all -> 0x0192 }
            android.hardware.Camera r2 = r7.mCamera     // Catch:{ IOException -> 0x0175 }
            android.graphics.SurfaceTexture r3 = r7.mSTexture     // Catch:{ IOException -> 0x0175 }
            r2.setPreviewTexture(r3)     // Catch:{ IOException -> 0x0175 }
            goto L_0x0190
        L_0x0175:
            r2 = move-exception
            java.lang.String r3 = "CameraRenderer"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0192 }
            r4.<init>()     // Catch:{ all -> 0x0192 }
            java.lang.String r5 = "setPreviewTexture() failed: "
            r4.append(r5)     // Catch:{ all -> 0x0192 }
            java.lang.String r5 = r2.getMessage()     // Catch:{ all -> 0x0192 }
            r4.append(r5)     // Catch:{ all -> 0x0192 }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x0192 }
            android.util.Log.e(r3, r4)     // Catch:{ all -> 0x0192 }
        L_0x0190:
            monitor-exit(r7)
            return
        L_0x0192:
            r8 = move-exception
            monitor-exit(r7)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: org.opencv.android.CameraRenderer.openCamera(int):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:41:0x0102  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void setCameraPreviewSize(int r14, int r15) {
        /*
            r13 = this;
            monitor-enter(r13)
            java.lang.String r0 = "CameraRenderer"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0126 }
            r1.<init>()     // Catch:{ all -> 0x0126 }
            java.lang.String r2 = "setCameraPreviewSize: "
            r1.append(r2)     // Catch:{ all -> 0x0126 }
            r1.append(r14)     // Catch:{ all -> 0x0126 }
            java.lang.String r2 = "x"
            r1.append(r2)     // Catch:{ all -> 0x0126 }
            r1.append(r15)     // Catch:{ all -> 0x0126 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x0126 }
            android.util.Log.i(r0, r1)     // Catch:{ all -> 0x0126 }
            android.hardware.Camera r0 = r13.mCamera     // Catch:{ all -> 0x0126 }
            if (r0 != 0) goto L_0x002d
            java.lang.String r0 = "CameraRenderer"
            java.lang.String r1 = "Camera isn't initialized!"
            android.util.Log.e(r0, r1)     // Catch:{ all -> 0x0126 }
            monitor-exit(r13)
            return
        L_0x002d:
            int r0 = r13.mMaxCameraWidth     // Catch:{ all -> 0x0126 }
            if (r0 <= 0) goto L_0x0038
            int r0 = r13.mMaxCameraWidth     // Catch:{ all -> 0x0126 }
            if (r0 >= r14) goto L_0x0038
            int r0 = r13.mMaxCameraWidth     // Catch:{ all -> 0x0126 }
            r14 = r0
        L_0x0038:
            int r0 = r13.mMaxCameraHeight     // Catch:{ all -> 0x0126 }
            if (r0 <= 0) goto L_0x0043
            int r0 = r13.mMaxCameraHeight     // Catch:{ all -> 0x0126 }
            if (r0 >= r15) goto L_0x0043
            int r0 = r13.mMaxCameraHeight     // Catch:{ all -> 0x0126 }
            r15 = r0
        L_0x0043:
            android.hardware.Camera r0 = r13.mCamera     // Catch:{ all -> 0x0126 }
            android.hardware.Camera$Parameters r0 = r0.getParameters()     // Catch:{ all -> 0x0126 }
            java.util.List r1 = r0.getSupportedPreviewSizes()     // Catch:{ all -> 0x0126 }
            r2 = 0
            r3 = 0
            int r4 = r1.size()     // Catch:{ all -> 0x0126 }
            if (r4 <= 0) goto L_0x0110
            float r4 = (float) r14     // Catch:{ all -> 0x0126 }
            float r5 = (float) r15     // Catch:{ all -> 0x0126 }
            float r4 = r4 / r5
            java.util.Iterator r5 = r1.iterator()     // Catch:{ all -> 0x0126 }
        L_0x005c:
            boolean r6 = r5.hasNext()     // Catch:{ all -> 0x0126 }
            if (r6 == 0) goto L_0x00a9
            java.lang.Object r6 = r5.next()     // Catch:{ all -> 0x0126 }
            android.hardware.Camera$Size r6 = (android.hardware.Camera.Size) r6     // Catch:{ all -> 0x0126 }
            int r7 = r6.width     // Catch:{ all -> 0x0126 }
            int r8 = r6.height     // Catch:{ all -> 0x0126 }
            java.lang.String r9 = "CameraRenderer"
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ all -> 0x0126 }
            r10.<init>()     // Catch:{ all -> 0x0126 }
            java.lang.String r11 = "checking camera preview size: "
            r10.append(r11)     // Catch:{ all -> 0x0126 }
            r10.append(r7)     // Catch:{ all -> 0x0126 }
            java.lang.String r11 = "x"
            r10.append(r11)     // Catch:{ all -> 0x0126 }
            r10.append(r8)     // Catch:{ all -> 0x0126 }
            java.lang.String r10 = r10.toString()     // Catch:{ all -> 0x0126 }
            android.util.Log.d(r9, r10)     // Catch:{ all -> 0x0126 }
            if (r7 > r14) goto L_0x00a8
            if (r8 > r15) goto L_0x00a8
            if (r7 < r2) goto L_0x00a8
            if (r8 < r3) goto L_0x00a8
            float r9 = (float) r7     // Catch:{ all -> 0x0126 }
            float r10 = (float) r8     // Catch:{ all -> 0x0126 }
            float r9 = r9 / r10
            float r9 = r4 - r9
            float r9 = java.lang.Math.abs(r9)     // Catch:{ all -> 0x0126 }
            double r9 = (double) r9     // Catch:{ all -> 0x0126 }
            r11 = 4596373779694328218(0x3fc999999999999a, double:0.2)
            int r9 = (r9 > r11 ? 1 : (r9 == r11 ? 0 : -1))
            if (r9 >= 0) goto L_0x00a8
            r2 = r7
            r3 = r8
        L_0x00a8:
            goto L_0x005c
        L_0x00a9:
            r5 = 0
            if (r2 <= 0) goto L_0x00ce
            if (r3 > 0) goto L_0x00af
            goto L_0x00ce
        L_0x00af:
            java.lang.String r6 = "CameraRenderer"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x0126 }
            r7.<init>()     // Catch:{ all -> 0x0126 }
            java.lang.String r8 = "Selected best size: "
            r7.append(r8)     // Catch:{ all -> 0x0126 }
            r7.append(r2)     // Catch:{ all -> 0x0126 }
            java.lang.String r8 = " x "
            r7.append(r8)     // Catch:{ all -> 0x0126 }
            r7.append(r3)     // Catch:{ all -> 0x0126 }
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x0126 }
            android.util.Log.i(r6, r7)     // Catch:{ all -> 0x0126 }
            goto L_0x00fe
        L_0x00ce:
            java.lang.Object r6 = r1.get(r5)     // Catch:{ all -> 0x0126 }
            android.hardware.Camera$Size r6 = (android.hardware.Camera.Size) r6     // Catch:{ all -> 0x0126 }
            int r6 = r6.width     // Catch:{ all -> 0x0126 }
            r2 = r6
            java.lang.Object r6 = r1.get(r5)     // Catch:{ all -> 0x0126 }
            android.hardware.Camera$Size r6 = (android.hardware.Camera.Size) r6     // Catch:{ all -> 0x0126 }
            int r6 = r6.height     // Catch:{ all -> 0x0126 }
            r3 = r6
            java.lang.String r6 = "CameraRenderer"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x0126 }
            r7.<init>()     // Catch:{ all -> 0x0126 }
            java.lang.String r8 = "Error: best size was not selected, using "
            r7.append(r8)     // Catch:{ all -> 0x0126 }
            r7.append(r2)     // Catch:{ all -> 0x0126 }
            java.lang.String r8 = " x "
            r7.append(r8)     // Catch:{ all -> 0x0126 }
            r7.append(r3)     // Catch:{ all -> 0x0126 }
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x0126 }
            android.util.Log.e(r6, r7)     // Catch:{ all -> 0x0126 }
        L_0x00fe:
            boolean r6 = r13.mPreviewStarted     // Catch:{ all -> 0x0126 }
            if (r6 == 0) goto L_0x0109
            android.hardware.Camera r6 = r13.mCamera     // Catch:{ all -> 0x0126 }
            r6.stopPreview()     // Catch:{ all -> 0x0126 }
            r13.mPreviewStarted = r5     // Catch:{ all -> 0x0126 }
        L_0x0109:
            r13.mCameraWidth = r2     // Catch:{ all -> 0x0126 }
            r13.mCameraHeight = r3     // Catch:{ all -> 0x0126 }
            r0.setPreviewSize(r2, r3)     // Catch:{ all -> 0x0126 }
        L_0x0110:
            java.lang.String r4 = "orientation"
            java.lang.String r5 = "landscape"
            r0.set(r4, r5)     // Catch:{ all -> 0x0126 }
            android.hardware.Camera r4 = r13.mCamera     // Catch:{ all -> 0x0126 }
            r4.setParameters(r0)     // Catch:{ all -> 0x0126 }
            android.hardware.Camera r4 = r13.mCamera     // Catch:{ all -> 0x0126 }
            r4.startPreview()     // Catch:{ all -> 0x0126 }
            r4 = 1
            r13.mPreviewStarted = r4     // Catch:{ all -> 0x0126 }
            monitor-exit(r13)
            return
        L_0x0126:
            r14 = move-exception
            monitor-exit(r13)
            throw r14
        */
        throw new UnsupportedOperationException("Method not decompiled: org.opencv.android.CameraRenderer.setCameraPreviewSize(int, int):void");
    }
}
