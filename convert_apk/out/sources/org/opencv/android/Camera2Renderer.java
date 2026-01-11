package org.opencv.android;

import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.util.Size;
import android.view.Surface;
import java.util.Arrays;
import java.util.concurrent.Semaphore;

public class Camera2Renderer extends CameraGLRendererBase {
    protected final String LOGTAG = "Camera2Renderer";
    /* access modifiers changed from: private */
    public Handler mBackgroundHandler;
    private HandlerThread mBackgroundThread;
    /* access modifiers changed from: private */
    public CameraDevice mCameraDevice;
    private String mCameraID;
    /* access modifiers changed from: private */
    public Semaphore mCameraOpenCloseLock = new Semaphore(1);
    /* access modifiers changed from: private */
    public CameraCaptureSession mCaptureSession;
    /* access modifiers changed from: private */
    public CaptureRequest.Builder mPreviewRequestBuilder;
    private Size mPreviewSize = new Size(-1, -1);
    private final CameraDevice.StateCallback mStateCallback = new CameraDevice.StateCallback() {
        public void onOpened(CameraDevice cameraDevice) {
            CameraDevice unused = Camera2Renderer.this.mCameraDevice = cameraDevice;
            Camera2Renderer.this.mCameraOpenCloseLock.release();
            Camera2Renderer.this.createCameraPreviewSession();
        }

        public void onDisconnected(CameraDevice cameraDevice) {
            cameraDevice.close();
            CameraDevice unused = Camera2Renderer.this.mCameraDevice = null;
            Camera2Renderer.this.mCameraOpenCloseLock.release();
        }

        public void onError(CameraDevice cameraDevice, int error) {
            cameraDevice.close();
            CameraDevice unused = Camera2Renderer.this.mCameraDevice = null;
            Camera2Renderer.this.mCameraOpenCloseLock.release();
        }
    };

    Camera2Renderer(CameraGLSurfaceView view) {
        super(view);
    }

    /* access modifiers changed from: protected */
    public void doStart() {
        Log.d("Camera2Renderer", "doStart");
        startBackgroundThread();
        super.doStart();
    }

    /* access modifiers changed from: protected */
    public void doStop() {
        Log.d("Camera2Renderer", "doStop");
        super.doStop();
        stopBackgroundThread();
    }

    /* access modifiers changed from: package-private */
    public boolean cacPreviewSize(int width, int height) {
        int i = width;
        int i2 = height;
        Log.i("Camera2Renderer", "cacPreviewSize: " + i + "x" + i2);
        if (this.mCameraID == null) {
            Log.e("Camera2Renderer", "Camera isn't initialized!");
            return false;
        }
        CameraManager manager = (CameraManager) this.mView.getContext().getSystemService("camera");
        try {
            CameraCharacteristics characteristics = manager.getCameraCharacteristics(this.mCameraID);
            int bestWidth = 0;
            int bestHeight = 0;
            float aspect = ((float) i) / ((float) i2);
            Size[] outputSizes = ((StreamConfigurationMap) characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)).getOutputSizes(SurfaceTexture.class);
            int length = outputSizes.length;
            int i3 = 0;
            while (i3 < length) {
                Size psize = outputSizes[i3];
                int w = psize.getWidth();
                int h = psize.getHeight();
                StringBuilder sb = new StringBuilder();
                CameraCharacteristics characteristics2 = characteristics;
                sb.append("trying size: ");
                int w2 = w;
                sb.append(w2);
                sb.append("x");
                CameraManager manager2 = manager;
                int h2 = h;
                try {
                    sb.append(h2);
                    Log.d("Camera2Renderer", sb.toString());
                    if (i >= w2 && i2 >= h2 && bestWidth <= w2 && bestHeight <= h2 && ((double) Math.abs(aspect - (((float) w2) / ((float) h2)))) < 0.2d) {
                        bestWidth = w2;
                        bestHeight = h2;
                    }
                    i3++;
                    i = width;
                    i2 = height;
                    manager = manager2;
                    characteristics = characteristics2;
                } catch (CameraAccessException e) {
                    Log.e("Camera2Renderer", "cacPreviewSize - Camera Access Exception");
                    return false;
                } catch (IllegalArgumentException e2) {
                    Log.e("Camera2Renderer", "cacPreviewSize - Illegal Argument Exception");
                    return false;
                } catch (SecurityException e3) {
                    Log.e("Camera2Renderer", "cacPreviewSize - Security Exception");
                    return false;
                }
            }
            CameraManager cameraManager = manager;
            Log.i("Camera2Renderer", "best size: " + bestWidth + "x" + bestHeight);
            if (bestWidth == 0 || bestHeight == 0) {
                return false;
            }
            if (this.mPreviewSize.getWidth() == bestWidth && this.mPreviewSize.getHeight() == bestHeight) {
                return false;
            }
            this.mPreviewSize = new Size(bestWidth, bestHeight);
            return true;
        } catch (CameraAccessException e4) {
            CameraManager cameraManager2 = manager;
            Log.e("Camera2Renderer", "cacPreviewSize - Camera Access Exception");
            return false;
        } catch (IllegalArgumentException e5) {
            CameraManager cameraManager3 = manager;
            Log.e("Camera2Renderer", "cacPreviewSize - Illegal Argument Exception");
            return false;
        } catch (SecurityException e6) {
            CameraManager cameraManager4 = manager;
            Log.e("Camera2Renderer", "cacPreviewSize - Security Exception");
            return false;
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0059, code lost:
        r9.mCameraID = r5;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void openCamera(int r10) {
        /*
            r9 = this;
            java.lang.String r0 = "Camera2Renderer"
            java.lang.String r1 = "openCamera"
            android.util.Log.i(r0, r1)
            org.opencv.android.CameraGLSurfaceView r1 = r9.mView
            android.content.Context r1 = r1.getContext()
            java.lang.String r2 = "camera"
            java.lang.Object r1 = r1.getSystemService(r2)
            android.hardware.camera2.CameraManager r1 = (android.hardware.camera2.CameraManager) r1
            java.lang.String[] r2 = r1.getCameraIdList()     // Catch:{ CameraAccessException -> 0x00ac, IllegalArgumentException -> 0x00a5, SecurityException -> 0x009e, InterruptedException -> 0x0097 }
            int r3 = r2.length     // Catch:{ CameraAccessException -> 0x00ac, IllegalArgumentException -> 0x00a5, SecurityException -> 0x009e, InterruptedException -> 0x0097 }
            if (r3 != 0) goto L_0x0022
            java.lang.String r3 = "Error: camera isn't detected."
            android.util.Log.e(r0, r3)     // Catch:{ CameraAccessException -> 0x00ac, IllegalArgumentException -> 0x00a5, SecurityException -> 0x009e, InterruptedException -> 0x0097 }
            return
        L_0x0022:
            r3 = -1
            r4 = 0
            if (r10 != r3) goto L_0x002b
            r3 = r2[r4]     // Catch:{ CameraAccessException -> 0x00ac, IllegalArgumentException -> 0x00a5, SecurityException -> 0x009e, InterruptedException -> 0x0097 }
            r9.mCameraID = r3     // Catch:{ CameraAccessException -> 0x00ac, IllegalArgumentException -> 0x00a5, SecurityException -> 0x009e, InterruptedException -> 0x0097 }
            goto L_0x005f
        L_0x002b:
            int r3 = r2.length     // Catch:{ CameraAccessException -> 0x00ac, IllegalArgumentException -> 0x00a5, SecurityException -> 0x009e, InterruptedException -> 0x0097 }
        L_0x002c:
            if (r4 >= r3) goto L_0x005f
            r5 = r2[r4]     // Catch:{ CameraAccessException -> 0x00ac, IllegalArgumentException -> 0x00a5, SecurityException -> 0x009e, InterruptedException -> 0x0097 }
            android.hardware.camera2.CameraCharacteristics r6 = r1.getCameraCharacteristics(r5)     // Catch:{ CameraAccessException -> 0x00ac, IllegalArgumentException -> 0x00a5, SecurityException -> 0x009e, InterruptedException -> 0x0097 }
            r7 = 99
            if (r10 != r7) goto L_0x0047
            android.hardware.camera2.CameraCharacteristics$Key r7 = android.hardware.camera2.CameraCharacteristics.LENS_FACING     // Catch:{ CameraAccessException -> 0x00ac, IllegalArgumentException -> 0x00a5, SecurityException -> 0x009e, InterruptedException -> 0x0097 }
            java.lang.Object r7 = r6.get(r7)     // Catch:{ CameraAccessException -> 0x00ac, IllegalArgumentException -> 0x00a5, SecurityException -> 0x009e, InterruptedException -> 0x0097 }
            java.lang.Integer r7 = (java.lang.Integer) r7     // Catch:{ CameraAccessException -> 0x00ac, IllegalArgumentException -> 0x00a5, SecurityException -> 0x009e, InterruptedException -> 0x0097 }
            int r7 = r7.intValue()     // Catch:{ CameraAccessException -> 0x00ac, IllegalArgumentException -> 0x00a5, SecurityException -> 0x009e, InterruptedException -> 0x0097 }
            r8 = 1
            if (r7 == r8) goto L_0x0059
        L_0x0047:
            r7 = 98
            if (r10 != r7) goto L_0x005c
            android.hardware.camera2.CameraCharacteristics$Key r7 = android.hardware.camera2.CameraCharacteristics.LENS_FACING     // Catch:{ CameraAccessException -> 0x00ac, IllegalArgumentException -> 0x00a5, SecurityException -> 0x009e, InterruptedException -> 0x0097 }
            java.lang.Object r7 = r6.get(r7)     // Catch:{ CameraAccessException -> 0x00ac, IllegalArgumentException -> 0x00a5, SecurityException -> 0x009e, InterruptedException -> 0x0097 }
            java.lang.Integer r7 = (java.lang.Integer) r7     // Catch:{ CameraAccessException -> 0x00ac, IllegalArgumentException -> 0x00a5, SecurityException -> 0x009e, InterruptedException -> 0x0097 }
            int r7 = r7.intValue()     // Catch:{ CameraAccessException -> 0x00ac, IllegalArgumentException -> 0x00a5, SecurityException -> 0x009e, InterruptedException -> 0x0097 }
            if (r7 != 0) goto L_0x005c
        L_0x0059:
            r9.mCameraID = r5     // Catch:{ CameraAccessException -> 0x00ac, IllegalArgumentException -> 0x00a5, SecurityException -> 0x009e, InterruptedException -> 0x0097 }
            goto L_0x005f
        L_0x005c:
            int r4 = r4 + 1
            goto L_0x002c
        L_0x005f:
            java.lang.String r3 = r9.mCameraID     // Catch:{ CameraAccessException -> 0x00ac, IllegalArgumentException -> 0x00a5, SecurityException -> 0x009e, InterruptedException -> 0x0097 }
            if (r3 == 0) goto L_0x00b2
            java.util.concurrent.Semaphore r3 = r9.mCameraOpenCloseLock     // Catch:{ CameraAccessException -> 0x00ac, IllegalArgumentException -> 0x00a5, SecurityException -> 0x009e, InterruptedException -> 0x0097 }
            r4 = 2500(0x9c4, double:1.235E-320)
            java.util.concurrent.TimeUnit r6 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch:{ CameraAccessException -> 0x00ac, IllegalArgumentException -> 0x00a5, SecurityException -> 0x009e, InterruptedException -> 0x0097 }
            boolean r3 = r3.tryAcquire(r4, r6)     // Catch:{ CameraAccessException -> 0x00ac, IllegalArgumentException -> 0x00a5, SecurityException -> 0x009e, InterruptedException -> 0x0097 }
            if (r3 == 0) goto L_0x008f
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ CameraAccessException -> 0x00ac, IllegalArgumentException -> 0x00a5, SecurityException -> 0x009e, InterruptedException -> 0x0097 }
            r3.<init>()     // Catch:{ CameraAccessException -> 0x00ac, IllegalArgumentException -> 0x00a5, SecurityException -> 0x009e, InterruptedException -> 0x0097 }
            java.lang.String r4 = "Opening camera: "
            r3.append(r4)     // Catch:{ CameraAccessException -> 0x00ac, IllegalArgumentException -> 0x00a5, SecurityException -> 0x009e, InterruptedException -> 0x0097 }
            java.lang.String r4 = r9.mCameraID     // Catch:{ CameraAccessException -> 0x00ac, IllegalArgumentException -> 0x00a5, SecurityException -> 0x009e, InterruptedException -> 0x0097 }
            r3.append(r4)     // Catch:{ CameraAccessException -> 0x00ac, IllegalArgumentException -> 0x00a5, SecurityException -> 0x009e, InterruptedException -> 0x0097 }
            java.lang.String r3 = r3.toString()     // Catch:{ CameraAccessException -> 0x00ac, IllegalArgumentException -> 0x00a5, SecurityException -> 0x009e, InterruptedException -> 0x0097 }
            android.util.Log.i(r0, r3)     // Catch:{ CameraAccessException -> 0x00ac, IllegalArgumentException -> 0x00a5, SecurityException -> 0x009e, InterruptedException -> 0x0097 }
            java.lang.String r3 = r9.mCameraID     // Catch:{ CameraAccessException -> 0x00ac, IllegalArgumentException -> 0x00a5, SecurityException -> 0x009e, InterruptedException -> 0x0097 }
            android.hardware.camera2.CameraDevice$StateCallback r4 = r9.mStateCallback     // Catch:{ CameraAccessException -> 0x00ac, IllegalArgumentException -> 0x00a5, SecurityException -> 0x009e, InterruptedException -> 0x0097 }
            android.os.Handler r5 = r9.mBackgroundHandler     // Catch:{ CameraAccessException -> 0x00ac, IllegalArgumentException -> 0x00a5, SecurityException -> 0x009e, InterruptedException -> 0x0097 }
            r1.openCamera(r3, r4, r5)     // Catch:{ CameraAccessException -> 0x00ac, IllegalArgumentException -> 0x00a5, SecurityException -> 0x009e, InterruptedException -> 0x0097 }
            goto L_0x00b2
        L_0x008f:
            java.lang.RuntimeException r3 = new java.lang.RuntimeException     // Catch:{ CameraAccessException -> 0x00ac, IllegalArgumentException -> 0x00a5, SecurityException -> 0x009e, InterruptedException -> 0x0097 }
            java.lang.String r4 = "Time out waiting to lock camera opening."
            r3.<init>(r4)     // Catch:{ CameraAccessException -> 0x00ac, IllegalArgumentException -> 0x00a5, SecurityException -> 0x009e, InterruptedException -> 0x0097 }
            throw r3     // Catch:{ CameraAccessException -> 0x00ac, IllegalArgumentException -> 0x00a5, SecurityException -> 0x009e, InterruptedException -> 0x0097 }
        L_0x0097:
            r2 = move-exception
            java.lang.String r3 = "OpenCamera - Interrupted Exception"
            android.util.Log.e(r0, r3)
            goto L_0x00b3
        L_0x009e:
            r2 = move-exception
            java.lang.String r3 = "OpenCamera - Security Exception"
            android.util.Log.e(r0, r3)
            goto L_0x00b2
        L_0x00a5:
            r2 = move-exception
            java.lang.String r3 = "OpenCamera - Illegal Argument Exception"
            android.util.Log.e(r0, r3)
            goto L_0x00b2
        L_0x00ac:
            r2 = move-exception
            java.lang.String r3 = "OpenCamera - Camera Access Exception"
            android.util.Log.e(r0, r3)
        L_0x00b2:
        L_0x00b3:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.opencv.android.Camera2Renderer.openCamera(int):void");
    }

    /* access modifiers changed from: protected */
    public void closeCamera() {
        Log.i("Camera2Renderer", "closeCamera");
        try {
            this.mCameraOpenCloseLock.acquire();
            if (this.mCaptureSession != null) {
                this.mCaptureSession.close();
                this.mCaptureSession = null;
            }
            if (this.mCameraDevice != null) {
                this.mCameraDevice.close();
                this.mCameraDevice = null;
            }
            this.mCameraOpenCloseLock.release();
        } catch (InterruptedException e) {
            throw new RuntimeException("Interrupted while trying to lock camera closing.", e);
        } catch (Throwable th) {
            this.mCameraOpenCloseLock.release();
            throw th;
        }
    }

    /* access modifiers changed from: private */
    public void createCameraPreviewSession() {
        int w = this.mPreviewSize.getWidth();
        int h = this.mPreviewSize.getHeight();
        Log.i("Camera2Renderer", "createCameraPreviewSession(" + w + "x" + h + ")");
        if (w >= 0 && h >= 0) {
            try {
                this.mCameraOpenCloseLock.acquire();
                if (this.mCameraDevice == null) {
                    this.mCameraOpenCloseLock.release();
                    Log.e("Camera2Renderer", "createCameraPreviewSession: camera isn't opened");
                } else if (this.mCaptureSession != null) {
                    this.mCameraOpenCloseLock.release();
                    Log.e("Camera2Renderer", "createCameraPreviewSession: mCaptureSession is already started");
                } else if (this.mSTexture == null) {
                    this.mCameraOpenCloseLock.release();
                    Log.e("Camera2Renderer", "createCameraPreviewSession: preview SurfaceTexture is null");
                } else {
                    this.mSTexture.setDefaultBufferSize(w, h);
                    Surface surface = new Surface(this.mSTexture);
                    CaptureRequest.Builder createCaptureRequest = this.mCameraDevice.createCaptureRequest(1);
                    this.mPreviewRequestBuilder = createCaptureRequest;
                    createCaptureRequest.addTarget(surface);
                    this.mCameraDevice.createCaptureSession(Arrays.asList(new Surface[]{surface}), new CameraCaptureSession.StateCallback() {
                        public void onConfigured(CameraCaptureSession cameraCaptureSession) {
                            CameraCaptureSession unused = Camera2Renderer.this.mCaptureSession = cameraCaptureSession;
                            try {
                                Camera2Renderer.this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, 4);
                                Camera2Renderer.this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, 2);
                                Camera2Renderer.this.mCaptureSession.setRepeatingRequest(Camera2Renderer.this.mPreviewRequestBuilder.build(), (CameraCaptureSession.CaptureCallback) null, Camera2Renderer.this.mBackgroundHandler);
                                Log.i("Camera2Renderer", "CameraPreviewSession has been started");
                            } catch (CameraAccessException e) {
                                Log.e("Camera2Renderer", "createCaptureSession failed");
                            }
                            Camera2Renderer.this.mCameraOpenCloseLock.release();
                        }

                        public void onConfigureFailed(CameraCaptureSession cameraCaptureSession) {
                            Log.e("Camera2Renderer", "createCameraPreviewSession failed");
                            Camera2Renderer.this.mCameraOpenCloseLock.release();
                        }
                    }, this.mBackgroundHandler);
                }
            } catch (CameraAccessException e) {
                Log.e("Camera2Renderer", "createCameraPreviewSession");
            } catch (InterruptedException e2) {
                throw new RuntimeException("Interrupted while createCameraPreviewSession", e2);
            }
        }
    }

    private void startBackgroundThread() {
        Log.i("Camera2Renderer", "startBackgroundThread");
        stopBackgroundThread();
        HandlerThread handlerThread = new HandlerThread("CameraBackground");
        this.mBackgroundThread = handlerThread;
        handlerThread.start();
        this.mBackgroundHandler = new Handler(this.mBackgroundThread.getLooper());
    }

    private void stopBackgroundThread() {
        Log.i("Camera2Renderer", "stopBackgroundThread");
        HandlerThread handlerThread = this.mBackgroundThread;
        if (handlerThread != null) {
            handlerThread.quitSafely();
            try {
                this.mBackgroundThread.join();
                this.mBackgroundThread = null;
                this.mBackgroundHandler = null;
            } catch (InterruptedException e) {
                Log.e("Camera2Renderer", "stopBackgroundThread");
            }
        }
    }

    /* access modifiers changed from: protected */
    public void setCameraPreviewSize(int width, int height) {
        Log.i("Camera2Renderer", "setCameraPreviewSize(" + width + "x" + height + ")");
        if (this.mMaxCameraWidth > 0 && this.mMaxCameraWidth < width) {
            width = this.mMaxCameraWidth;
        }
        if (this.mMaxCameraHeight > 0 && this.mMaxCameraHeight < height) {
            height = this.mMaxCameraHeight;
        }
        try {
            this.mCameraOpenCloseLock.acquire();
            boolean needReconfig = cacPreviewSize(width, height);
            this.mCameraWidth = this.mPreviewSize.getWidth();
            this.mCameraHeight = this.mPreviewSize.getHeight();
            if (!needReconfig) {
                this.mCameraOpenCloseLock.release();
                return;
            }
            if (this.mCaptureSession != null) {
                Log.d("Camera2Renderer", "closing existing previewSession");
                this.mCaptureSession.close();
                this.mCaptureSession = null;
            }
            this.mCameraOpenCloseLock.release();
            createCameraPreviewSession();
        } catch (InterruptedException e) {
            this.mCameraOpenCloseLock.release();
            throw new RuntimeException("Interrupted while setCameraPreviewSize.", e);
        }
    }
}
