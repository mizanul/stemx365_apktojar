package org.opencv.tracking;

import org.opencv.video.Tracker;

public class Tracking {
    public static final int CvFeatureParams_HAAR = 0;
    public static final int CvFeatureParams_HOG = 2;
    public static final int CvFeatureParams_LBP = 1;
    public static final int TrackerContribSamplerCSC_MODE_DETECT = 5;
    public static final int TrackerContribSamplerCSC_MODE_INIT_NEG = 2;
    public static final int TrackerContribSamplerCSC_MODE_INIT_POS = 1;
    public static final int TrackerContribSamplerCSC_MODE_TRACK_NEG = 4;
    public static final int TrackerContribSamplerCSC_MODE_TRACK_POS = 3;
    public static final int TrackerSamplerCS_MODE_CLASSIFY = 3;
    public static final int TrackerSamplerCS_MODE_NEGATIVE = 2;
    public static final int TrackerSamplerCS_MODE_POSITIVE = 1;

    private static native long legacy_upgradeTrackingAPI_0(long j);

    public static Tracker legacy_upgradeTrackingAPI(legacy_Tracker legacy_tracker) {
        return Tracker.__fromPtr__(legacy_upgradeTrackingAPI_0(legacy_tracker.getNativeObjAddr()));
    }
}
