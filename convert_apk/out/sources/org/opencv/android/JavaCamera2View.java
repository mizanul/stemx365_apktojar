package org.opencv.android;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Size;
import android.view.Surface;
import java.nio.ByteBuffer;
import java.util.Arrays;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class JavaCamera2View extends CameraBridgeViewBase {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final String LOGTAG = "JavaCamera2View";
    protected Handler mBackgroundHandler;
    private HandlerThread mBackgroundThread;
    protected CameraDevice mCameraDevice;
    protected String mCameraID;
    protected CameraCaptureSession mCaptureSession;
    protected ImageReader mImageReader;
    protected int mPreviewFormat = 35;
    protected CaptureRequest.Builder mPreviewRequestBuilder;
    protected Size mPreviewSize = new Size(-1, -1);
    private final CameraDevice.StateCallback mStateCallback = new CameraDevice.StateCallback() {
        public void onOpened(CameraDevice cameraDevice) {
            JavaCamera2View.this.mCameraDevice = cameraDevice;
            JavaCamera2View.this.createCameraPreviewSession();
        }

        public void onDisconnected(CameraDevice cameraDevice) {
            cameraDevice.close();
            JavaCamera2View.this.mCameraDevice = null;
        }

        public void onError(CameraDevice cameraDevice, int error) {
            cameraDevice.close();
            JavaCamera2View.this.mCameraDevice = null;
        }
    };

    public JavaCamera2View(Context context, int cameraId) {
        super(context, cameraId);
    }

    public JavaCamera2View(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void startBackgroundThread() {
        Log.i(LOGTAG, "startBackgroundThread");
        stopBackgroundThread();
        HandlerThread handlerThread = new HandlerThread("OpenCVCameraBackground");
        this.mBackgroundThread = handlerThread;
        handlerThread.start();
        this.mBackgroundHandler = new Handler(this.mBackgroundThread.getLooper());
    }

    private void stopBackgroundThread() {
        Log.i(LOGTAG, "stopBackgroundThread");
        HandlerThread handlerThread = this.mBackgroundThread;
        if (handlerThread != null) {
            handlerThread.quitSafely();
            try {
                this.mBackgroundThread.join();
                this.mBackgroundThread = null;
                this.mBackgroundHandler = null;
            } catch (InterruptedException e) {
                Log.e(LOGTAG, "stopBackgroundThread", e);
            }
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x005e, code lost:
        r11.mCameraID = r7;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean initializeCamera() {
        /*
            r11 = this;
            java.lang.String r0 = "JavaCamera2View"
            java.lang.String r1 = "initializeCamera"
            android.util.Log.i(r0, r1)
            android.content.Context r1 = r11.getContext()
            java.lang.String r2 = "camera"
            java.lang.Object r1 = r1.getSystemService(r2)
            android.hardware.camera2.CameraManager r1 = (android.hardware.camera2.CameraManager) r1
            r2 = 0
            java.lang.String[] r3 = r1.getCameraIdList()     // Catch:{ CameraAccessException -> 0x00cb, IllegalArgumentException -> 0x00c4, SecurityException -> 0x00bd }
            int r4 = r3.length     // Catch:{ CameraAccessException -> 0x00cb, IllegalArgumentException -> 0x00c4, SecurityException -> 0x00bd }
            if (r4 != 0) goto L_0x0021
            java.lang.String r4 = "Error: camera isn't detected."
            android.util.Log.e(r0, r4)     // Catch:{ CameraAccessException -> 0x00cb, IllegalArgumentException -> 0x00c4, SecurityException -> 0x00bd }
            return r2
        L_0x0021:
            int r4 = r11.mCameraIndex     // Catch:{ CameraAccessException -> 0x00cb, IllegalArgumentException -> 0x00c4, SecurityException -> 0x00bd }
            r5 = -1
            r6 = 1
            if (r4 != r5) goto L_0x002c
            r4 = r3[r2]     // Catch:{ CameraAccessException -> 0x00cb, IllegalArgumentException -> 0x00c4, SecurityException -> 0x00bd }
            r11.mCameraID = r4     // Catch:{ CameraAccessException -> 0x00cb, IllegalArgumentException -> 0x00c4, SecurityException -> 0x00bd }
            goto L_0x0064
        L_0x002c:
            int r4 = r3.length     // Catch:{ CameraAccessException -> 0x00cb, IllegalArgumentException -> 0x00c4, SecurityException -> 0x00bd }
            r5 = r2
        L_0x002e:
            if (r5 >= r4) goto L_0x0064
            r7 = r3[r5]     // Catch:{ CameraAccessException -> 0x00cb, IllegalArgumentException -> 0x00c4, SecurityException -> 0x00bd }
            android.hardware.camera2.CameraCharacteristics r8 = r1.getCameraCharacteristics(r7)     // Catch:{ CameraAccessException -> 0x00cb, IllegalArgumentException -> 0x00c4, SecurityException -> 0x00bd }
            int r9 = r11.mCameraIndex     // Catch:{ CameraAccessException -> 0x00cb, IllegalArgumentException -> 0x00c4, SecurityException -> 0x00bd }
            r10 = 99
            if (r9 != r10) goto L_0x004a
            android.hardware.camera2.CameraCharacteristics$Key r9 = android.hardware.camera2.CameraCharacteristics.LENS_FACING     // Catch:{ CameraAccessException -> 0x00cb, IllegalArgumentException -> 0x00c4, SecurityException -> 0x00bd }
            java.lang.Object r9 = r8.get(r9)     // Catch:{ CameraAccessException -> 0x00cb, IllegalArgumentException -> 0x00c4, SecurityException -> 0x00bd }
            java.lang.Integer r9 = (java.lang.Integer) r9     // Catch:{ CameraAccessException -> 0x00cb, IllegalArgumentException -> 0x00c4, SecurityException -> 0x00bd }
            int r9 = r9.intValue()     // Catch:{ CameraAccessException -> 0x00cb, IllegalArgumentException -> 0x00c4, SecurityException -> 0x00bd }
            if (r9 == r6) goto L_0x005e
        L_0x004a:
            int r9 = r11.mCameraIndex     // Catch:{ CameraAccessException -> 0x00cb, IllegalArgumentException -> 0x00c4, SecurityException -> 0x00bd }
            r10 = 98
            if (r9 != r10) goto L_0x0061
            android.hardware.camera2.CameraCharacteristics$Key r9 = android.hardware.camera2.CameraCharacteristics.LENS_FACING     // Catch:{ CameraAccessException -> 0x00cb, IllegalArgumentException -> 0x00c4, SecurityException -> 0x00bd }
            java.lang.Object r9 = r8.get(r9)     // Catch:{ CameraAccessException -> 0x00cb, IllegalArgumentException -> 0x00c4, SecurityException -> 0x00bd }
            java.lang.Integer r9 = (java.lang.Integer) r9     // Catch:{ CameraAccessException -> 0x00cb, IllegalArgumentException -> 0x00c4, SecurityException -> 0x00bd }
            int r9 = r9.intValue()     // Catch:{ CameraAccessException -> 0x00cb, IllegalArgumentException -> 0x00c4, SecurityException -> 0x00bd }
            if (r9 != 0) goto L_0x0061
        L_0x005e:
            r11.mCameraID = r7     // Catch:{ CameraAccessException -> 0x00cb, IllegalArgumentException -> 0x00c4, SecurityException -> 0x00bd }
            goto L_0x0064
        L_0x0061:
            int r5 = r5 + 1
            goto L_0x002e
        L_0x0064:
            java.lang.String r4 = r11.mCameraID     // Catch:{ CameraAccessException -> 0x00cb, IllegalArgumentException -> 0x00c4, SecurityException -> 0x00bd }
            if (r4 == 0) goto L_0x0088
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ CameraAccessException -> 0x00cb, IllegalArgumentException -> 0x00c4, SecurityException -> 0x00bd }
            r4.<init>()     // Catch:{ CameraAccessException -> 0x00cb, IllegalArgumentException -> 0x00c4, SecurityException -> 0x00bd }
            java.lang.String r5 = "Opening camera: "
            r4.append(r5)     // Catch:{ CameraAccessException -> 0x00cb, IllegalArgumentException -> 0x00c4, SecurityException -> 0x00bd }
            java.lang.String r5 = r11.mCameraID     // Catch:{ CameraAccessException -> 0x00cb, IllegalArgumentException -> 0x00c4, SecurityException -> 0x00bd }
            r4.append(r5)     // Catch:{ CameraAccessException -> 0x00cb, IllegalArgumentException -> 0x00c4, SecurityException -> 0x00bd }
            java.lang.String r4 = r4.toString()     // Catch:{ CameraAccessException -> 0x00cb, IllegalArgumentException -> 0x00c4, SecurityException -> 0x00bd }
            android.util.Log.i(r0, r4)     // Catch:{ CameraAccessException -> 0x00cb, IllegalArgumentException -> 0x00c4, SecurityException -> 0x00bd }
            java.lang.String r4 = r11.mCameraID     // Catch:{ CameraAccessException -> 0x00cb, IllegalArgumentException -> 0x00c4, SecurityException -> 0x00bd }
            android.hardware.camera2.CameraDevice$StateCallback r5 = r11.mStateCallback     // Catch:{ CameraAccessException -> 0x00cb, IllegalArgumentException -> 0x00c4, SecurityException -> 0x00bd }
            android.os.Handler r7 = r11.mBackgroundHandler     // Catch:{ CameraAccessException -> 0x00cb, IllegalArgumentException -> 0x00c4, SecurityException -> 0x00bd }
            r1.openCamera(r4, r5, r7)     // Catch:{ CameraAccessException -> 0x00cb, IllegalArgumentException -> 0x00c4, SecurityException -> 0x00bd }
            goto L_0x00b5
        L_0x0088:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ CameraAccessException -> 0x00cb, IllegalArgumentException -> 0x00c4, SecurityException -> 0x00bd }
            r4.<init>()     // Catch:{ CameraAccessException -> 0x00cb, IllegalArgumentException -> 0x00c4, SecurityException -> 0x00bd }
            java.lang.String r5 = "Trying to open camera with the value ("
            r4.append(r5)     // Catch:{ CameraAccessException -> 0x00cb, IllegalArgumentException -> 0x00c4, SecurityException -> 0x00bd }
            int r5 = r11.mCameraIndex     // Catch:{ CameraAccessException -> 0x00cb, IllegalArgumentException -> 0x00c4, SecurityException -> 0x00bd }
            r4.append(r5)     // Catch:{ CameraAccessException -> 0x00cb, IllegalArgumentException -> 0x00c4, SecurityException -> 0x00bd }
            java.lang.String r5 = ")"
            r4.append(r5)     // Catch:{ CameraAccessException -> 0x00cb, IllegalArgumentException -> 0x00c4, SecurityException -> 0x00bd }
            java.lang.String r4 = r4.toString()     // Catch:{ CameraAccessException -> 0x00cb, IllegalArgumentException -> 0x00c4, SecurityException -> 0x00bd }
            android.util.Log.i(r0, r4)     // Catch:{ CameraAccessException -> 0x00cb, IllegalArgumentException -> 0x00c4, SecurityException -> 0x00bd }
            int r4 = r11.mCameraIndex     // Catch:{ CameraAccessException -> 0x00cb, IllegalArgumentException -> 0x00c4, SecurityException -> 0x00bd }
            int r5 = r3.length     // Catch:{ CameraAccessException -> 0x00cb, IllegalArgumentException -> 0x00c4, SecurityException -> 0x00bd }
            if (r4 >= r5) goto L_0x00b6
            int r4 = r11.mCameraIndex     // Catch:{ CameraAccessException -> 0x00cb, IllegalArgumentException -> 0x00c4, SecurityException -> 0x00bd }
            r4 = r3[r4]     // Catch:{ CameraAccessException -> 0x00cb, IllegalArgumentException -> 0x00c4, SecurityException -> 0x00bd }
            r11.mCameraID = r4     // Catch:{ CameraAccessException -> 0x00cb, IllegalArgumentException -> 0x00c4, SecurityException -> 0x00bd }
            android.hardware.camera2.CameraDevice$StateCallback r5 = r11.mStateCallback     // Catch:{ CameraAccessException -> 0x00cb, IllegalArgumentException -> 0x00c4, SecurityException -> 0x00bd }
            android.os.Handler r7 = r11.mBackgroundHandler     // Catch:{ CameraAccessException -> 0x00cb, IllegalArgumentException -> 0x00c4, SecurityException -> 0x00bd }
            r1.openCamera(r4, r5, r7)     // Catch:{ CameraAccessException -> 0x00cb, IllegalArgumentException -> 0x00c4, SecurityException -> 0x00bd }
        L_0x00b5:
            return r6
        L_0x00b6:
            android.hardware.camera2.CameraAccessException r4 = new android.hardware.camera2.CameraAccessException     // Catch:{ CameraAccessException -> 0x00cb, IllegalArgumentException -> 0x00c4, SecurityException -> 0x00bd }
            r5 = 2
            r4.<init>(r5)     // Catch:{ CameraAccessException -> 0x00cb, IllegalArgumentException -> 0x00c4, SecurityException -> 0x00bd }
            throw r4     // Catch:{ CameraAccessException -> 0x00cb, IllegalArgumentException -> 0x00c4, SecurityException -> 0x00bd }
        L_0x00bd:
            r3 = move-exception
            java.lang.String r4 = "OpenCamera - Security Exception"
            android.util.Log.e(r0, r4, r3)
            goto L_0x00d2
        L_0x00c4:
            r3 = move-exception
            java.lang.String r4 = "OpenCamera - Illegal Argument Exception"
            android.util.Log.e(r0, r4, r3)
            goto L_0x00d1
        L_0x00cb:
            r3 = move-exception
            java.lang.String r4 = "OpenCamera - Camera Access Exception"
            android.util.Log.e(r0, r4, r3)
        L_0x00d1:
        L_0x00d2:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.opencv.android.JavaCamera2View.initializeCamera():boolean");
    }

    /* access modifiers changed from: private */
    public void createCameraPreviewSession() {
        int w = this.mPreviewSize.getWidth();
        int h = this.mPreviewSize.getHeight();
        Log.i(LOGTAG, "createCameraPreviewSession(" + w + "x" + h + ")");
        if (w >= 0 && h >= 0) {
            try {
                if (this.mCameraDevice == null) {
                    Log.e(LOGTAG, "createCameraPreviewSession: camera isn't opened");
                } else if (this.mCaptureSession != null) {
                    Log.e(LOGTAG, "createCameraPreviewSession: mCaptureSession is already started");
                } else {
                    ImageReader newInstance = ImageReader.newInstance(w, h, this.mPreviewFormat, 2);
                    this.mImageReader = newInstance;
                    newInstance.setOnImageAvailableListener(new ImageReader.OnImageAvailableListener() {
                        static final /* synthetic */ boolean $assertionsDisabled = false;

                        static {
                            Class<JavaCamera2View> cls = JavaCamera2View.class;
                        }

                        public void onImageAvailable(ImageReader reader) {
                            Image image = reader.acquireLatestImage();
                            if (image != null) {
                                Image.Plane[] planes = image.getPlanes();
                                JavaCamera2Frame tempFrame = new JavaCamera2Frame(image);
                                JavaCamera2View.this.deliverAndDrawFrame(tempFrame);
                                tempFrame.release();
                                image.close();
                            }
                        }
                    }, this.mBackgroundHandler);
                    Surface surface = this.mImageReader.getSurface();
                    CaptureRequest.Builder createCaptureRequest = this.mCameraDevice.createCaptureRequest(1);
                    this.mPreviewRequestBuilder = createCaptureRequest;
                    createCaptureRequest.addTarget(surface);
                    this.mCameraDevice.createCaptureSession(Arrays.asList(new Surface[]{surface}), new CameraCaptureSession.StateCallback() {
                        public void onConfigured(CameraCaptureSession cameraCaptureSession) {
                            Log.i(JavaCamera2View.LOGTAG, "createCaptureSession::onConfigured");
                            if (JavaCamera2View.this.mCameraDevice != null) {
                                JavaCamera2View.this.mCaptureSession = cameraCaptureSession;
                                try {
                                    JavaCamera2View.this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, 4);
                                    JavaCamera2View.this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, 2);
                                    JavaCamera2View.this.mCaptureSession.setRepeatingRequest(JavaCamera2View.this.mPreviewRequestBuilder.build(), (CameraCaptureSession.CaptureCallback) null, JavaCamera2View.this.mBackgroundHandler);
                                    Log.i(JavaCamera2View.LOGTAG, "CameraPreviewSession has been started");
                                } catch (Exception e) {
                                    Log.e(JavaCamera2View.LOGTAG, "createCaptureSession failed", e);
                                }
                            }
                        }

                        public void onConfigureFailed(CameraCaptureSession cameraCaptureSession) {
                            Log.e(JavaCamera2View.LOGTAG, "createCameraPreviewSession failed");
                        }
                    }, (Handler) null);
                }
            } catch (CameraAccessException e) {
                Log.e(LOGTAG, "createCameraPreviewSession", e);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void disconnectCamera() {
        Log.i(LOGTAG, "close camera");
        try {
            CameraDevice c = this.mCameraDevice;
            this.mCameraDevice = null;
            if (this.mCaptureSession != null) {
                this.mCaptureSession.close();
                this.mCaptureSession = null;
            }
            if (c != null) {
                c.close();
            }
            Log.i(LOGTAG, "camera closed!");
        } finally {
            stopBackgroundThread();
            ImageReader imageReader = this.mImageReader;
            if (imageReader != null) {
                imageReader.close();
                this.mImageReader = null;
            }
        }
    }

    public static class JavaCameraSizeAccessor implements CameraBridgeViewBase.ListItemAccessor {
        public int getWidth(Object obj) {
            return ((Size) obj).getWidth();
        }

        public int getHeight(Object obj) {
            return ((Size) obj).getHeight();
        }
    }

    /* access modifiers changed from: package-private */
    public boolean calcPreviewSize(int width, int height) {
        Log.i(LOGTAG, "calcPreviewSize: " + width + "x" + height);
        if (this.mCameraID == null) {
            Log.e(LOGTAG, "Camera isn't initialized!");
            return false;
        }
        try {
            org.opencv.core.Size frameSize = calculateCameraFrameSize(Arrays.asList(((StreamConfigurationMap) ((CameraManager) getContext().getSystemService("camera")).getCameraCharacteristics(this.mCameraID).get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)).getOutputSizes(ImageReader.class)), new JavaCameraSizeAccessor(), width, height);
            Log.i(LOGTAG, "Selected preview size to " + Integer.valueOf((int) frameSize.width) + "x" + Integer.valueOf((int) frameSize.height));
            if (((double) this.mPreviewSize.getWidth()) == frameSize.width && ((double) this.mPreviewSize.getHeight()) == frameSize.height) {
                return false;
            }
            this.mPreviewSize = new Size((int) frameSize.width, (int) frameSize.height);
            return true;
        } catch (CameraAccessException e) {
            Log.e(LOGTAG, "calcPreviewSize - Camera Access Exception", e);
            return false;
        } catch (IllegalArgumentException e2) {
            Log.e(LOGTAG, "calcPreviewSize - Illegal Argument Exception", e2);
            return false;
        } catch (SecurityException e3) {
            Log.e(LOGTAG, "calcPreviewSize - Security Exception", e3);
            return false;
        }
    }

    /* access modifiers changed from: protected */
    public boolean connectCamera(int width, int height) {
        Log.i(LOGTAG, "setCameraPreviewSize(" + width + "x" + height + ")");
        startBackgroundThread();
        initializeCamera();
        try {
            boolean needReconfig = calcPreviewSize(width, height);
            this.mFrameWidth = this.mPreviewSize.getWidth();
            this.mFrameHeight = this.mPreviewSize.getHeight();
            if (getLayoutParams().width == -1 && getLayoutParams().height == -1) {
                this.mScale = Math.min(((float) height) / ((float) this.mFrameHeight), ((float) width) / ((float) this.mFrameWidth));
            } else {
                this.mScale = 0.0f;
            }
            AllocateCache();
            if (!needReconfig) {
                return true;
            }
            if (this.mCaptureSession != null) {
                Log.d(LOGTAG, "closing existing previewSession");
                this.mCaptureSession.close();
                this.mCaptureSession = null;
            }
            createCameraPreviewSession();
            return true;
        } catch (RuntimeException e) {
            throw new RuntimeException("Interrupted while setCameraPreviewSize.", e);
        }
    }

    private class JavaCamera2Frame implements CameraBridgeViewBase.CvCameraViewFrame {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private Mat mGray = new Mat();
        private Image mImage;
        private Mat mRgba = new Mat();

        static {
            Class<JavaCamera2View> cls = JavaCamera2View.class;
        }

        public Mat gray() {
            Image.Plane[] planes = this.mImage.getPlanes();
            int w = this.mImage.getWidth();
            int h = this.mImage.getHeight();
            ByteBuffer y_plane = planes[0].getBuffer();
            int i = h;
            int i2 = w;
            ByteBuffer byteBuffer = y_plane;
            Mat mat = new Mat(i, i2, CvType.CV_8UC1, byteBuffer, (long) planes[0].getRowStride());
            this.mGray = mat;
            return mat;
        }

        public Mat rgba() {
            Image.Plane[] planes = this.mImage.getPlanes();
            int w = this.mImage.getWidth();
            int h = this.mImage.getHeight();
            if (planes[1].getPixelStride() == 2) {
                ByteBuffer y_plane = planes[0].getBuffer();
                int y_plane_step = planes[0].getRowStride();
                ByteBuffer uv_plane1 = planes[1].getBuffer();
                int uv_plane1_step = planes[1].getRowStride();
                ByteBuffer uv_plane2 = planes[2].getBuffer();
                int uv_plane2_step = planes[2].getRowStride();
                Mat y_mat = new Mat(h, w, CvType.CV_8UC1, y_plane, (long) y_plane_step);
                int uv_plane2_step2 = uv_plane2_step;
                int i = uv_plane1_step;
                Mat mat = new Mat(h / 2, w / 2, CvType.CV_8UC2, uv_plane1, (long) uv_plane1_step);
                Mat mat2 = new Mat(h / 2, w / 2, CvType.CV_8UC2, uv_plane2, (long) uv_plane2_step2);
                if (mat2.dataAddr() - mat.dataAddr() > 0) {
                    Imgproc.cvtColorTwoPlane(y_mat, mat, this.mRgba, 94);
                } else {
                    Imgproc.cvtColorTwoPlane(y_mat, mat2, this.mRgba, 96);
                }
                return this.mRgba;
            }
            byte[] yuv_bytes = new byte[(((h / 2) + h) * w)];
            ByteBuffer y_plane2 = planes[0].getBuffer();
            ByteBuffer u_plane = planes[1].getBuffer();
            ByteBuffer v_plane = planes[2].getBuffer();
            int yuv_bytes_offset = 0;
            int y_plane_step2 = planes[0].getRowStride();
            if (y_plane_step2 == w) {
                y_plane2.get(yuv_bytes, 0, w * h);
                yuv_bytes_offset = w * h;
            } else {
                int padding = y_plane_step2 - w;
                for (int i2 = 0; i2 < h; i2++) {
                    y_plane2.get(yuv_bytes, yuv_bytes_offset, w);
                    yuv_bytes_offset += w;
                    if (i2 < h - 1) {
                        y_plane2.position(y_plane2.position() + padding);
                    }
                }
            }
            int chromaRowPadding = planes[1].getRowStride() - (w / 2);
            if (chromaRowPadding == 0) {
                u_plane.get(yuv_bytes, yuv_bytes_offset, (w * h) / 4);
                v_plane.get(yuv_bytes, yuv_bytes_offset + ((w * h) / 4), (w * h) / 4);
            } else {
                for (int i3 = 0; i3 < h / 2; i3++) {
                    u_plane.get(yuv_bytes, yuv_bytes_offset, w / 2);
                    yuv_bytes_offset += w / 2;
                    if (i3 < (h / 2) - 1) {
                        u_plane.position(u_plane.position() + chromaRowPadding);
                    }
                }
                for (int i4 = 0; i4 < h / 2; i4++) {
                    v_plane.get(yuv_bytes, yuv_bytes_offset, w / 2);
                    yuv_bytes_offset += w / 2;
                    if (i4 < (h / 2) - 1) {
                        v_plane.position(v_plane.position() + chromaRowPadding);
                    }
                }
            }
            Mat yuv_mat = new Mat((h / 2) + h, w, CvType.CV_8UC1);
            yuv_mat.put(0, 0, yuv_bytes);
            Image.Plane[] planeArr = planes;
            Imgproc.cvtColor(yuv_mat, this.mRgba, 104, 4);
            return this.mRgba;
        }

        public JavaCamera2Frame(Image image) {
            this.mImage = image;
        }

        public void release() {
            this.mRgba.release();
            this.mGray.release();
        }
    }
}
