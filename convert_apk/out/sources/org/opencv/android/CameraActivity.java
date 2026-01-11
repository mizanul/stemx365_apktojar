package org.opencv.android;

import android.app.Activity;
import android.os.Build;
import java.util.ArrayList;
import java.util.List;

public class CameraActivity extends Activity {
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 200;

    /* access modifiers changed from: protected */
    public List<? extends CameraBridgeViewBase> getCameraViewList() {
        return new ArrayList();
    }

    /* access modifiers changed from: protected */
    public void onCameraPermissionGranted() {
        List<? extends CameraBridgeViewBase> cameraViews = getCameraViewList();
        if (cameraViews != null) {
            for (CameraBridgeViewBase cameraBridgeViewBase : cameraViews) {
                if (cameraBridgeViewBase != null) {
                    cameraBridgeViewBase.setCameraPermissionGranted();
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        boolean havePermission = true;
        if (Build.VERSION.SDK_INT >= 23 && checkSelfPermission("android.permission.CAMERA") != 0) {
            requestPermissions(new String[]{"android.permission.CAMERA"}, 200);
            havePermission = false;
        }
        if (havePermission) {
            onCameraPermissionGranted();
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 200 && grantResults.length > 0 && grantResults[0] == 0) {
            onCameraPermissionGranted();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
