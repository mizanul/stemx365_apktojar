package org.opencv.xfeatures2d;

import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.Size;

public class Xfeatures2d {
    private static native void matchGMS_0(double d, double d2, double d3, double d4, long j, long j2, long j3, long j4, boolean z, boolean z2, double d5);

    private static native void matchGMS_1(double d, double d2, double d3, double d4, long j, long j2, long j3, long j4, boolean z, boolean z2);

    private static native void matchGMS_2(double d, double d2, double d3, double d4, long j, long j2, long j3, long j4, boolean z);

    private static native void matchGMS_3(double d, double d2, double d3, double d4, long j, long j2, long j3, long j4);

    private static native void matchLOGOS_0(long j, long j2, long j3, long j4, long j5);

    public static void matchGMS(Size size1, Size size2, MatOfKeyPoint keypoints1, MatOfKeyPoint keypoints2, MatOfDMatch matches1to2, MatOfDMatch matchesGMS, boolean withRotation, boolean withScale, double thresholdFactor) {
        Size size = size1;
        Size size3 = size2;
        MatOfKeyPoint matOfKeyPoint = keypoints1;
        MatOfKeyPoint matOfKeyPoint2 = keypoints2;
        double d = size.width;
        double d2 = d;
        MatOfDMatch matOfDMatch = matches1to2;
        MatOfDMatch matOfDMatch2 = matchesGMS;
        MatOfKeyPoint matOfKeyPoint3 = matOfKeyPoint;
        MatOfKeyPoint matOfKeyPoint4 = matOfKeyPoint2;
        MatOfDMatch matOfDMatch3 = matOfDMatch;
        MatOfDMatch matOfDMatch4 = matOfDMatch2;
        double d3 = d2;
        matchGMS_0(d3, size.height, size3.width, size3.height, matOfKeyPoint.nativeObj, matOfKeyPoint2.nativeObj, matOfDMatch.nativeObj, matOfDMatch2.nativeObj, withRotation, withScale, thresholdFactor);
    }

    public static void matchGMS(Size size1, Size size2, MatOfKeyPoint keypoints1, MatOfKeyPoint keypoints2, MatOfDMatch matches1to2, MatOfDMatch matchesGMS, boolean withRotation, boolean withScale) {
        Size size = size1;
        Size size3 = size2;
        MatOfKeyPoint matOfKeyPoint = keypoints1;
        MatOfKeyPoint matOfKeyPoint2 = keypoints2;
        double d = size.width;
        double d2 = d;
        MatOfDMatch matOfDMatch = matches1to2;
        MatOfDMatch matOfDMatch2 = matchesGMS;
        MatOfKeyPoint matOfKeyPoint3 = matOfKeyPoint;
        MatOfKeyPoint matOfKeyPoint4 = matOfKeyPoint2;
        MatOfDMatch matOfDMatch3 = matOfDMatch;
        MatOfDMatch matOfDMatch4 = matOfDMatch2;
        double d3 = d2;
        matchGMS_1(d3, size.height, size3.width, size3.height, matOfKeyPoint.nativeObj, matOfKeyPoint2.nativeObj, matOfDMatch.nativeObj, matOfDMatch2.nativeObj, withRotation, withScale);
    }

    public static void matchGMS(Size size1, Size size2, MatOfKeyPoint keypoints1, MatOfKeyPoint keypoints2, MatOfDMatch matches1to2, MatOfDMatch matchesGMS, boolean withRotation) {
        Size size = size1;
        Size size3 = size2;
        MatOfKeyPoint matOfKeyPoint = keypoints1;
        MatOfKeyPoint matOfKeyPoint2 = keypoints2;
        double d = size.width;
        double d2 = d;
        MatOfDMatch matOfDMatch = matches1to2;
        MatOfDMatch matOfDMatch2 = matchesGMS;
        MatOfKeyPoint matOfKeyPoint3 = matOfKeyPoint;
        MatOfKeyPoint matOfKeyPoint4 = matOfKeyPoint2;
        MatOfDMatch matOfDMatch3 = matOfDMatch;
        MatOfDMatch matOfDMatch4 = matOfDMatch2;
        double d3 = d2;
        matchGMS_2(d3, size.height, size3.width, size3.height, matOfKeyPoint.nativeObj, matOfKeyPoint2.nativeObj, matOfDMatch.nativeObj, matOfDMatch2.nativeObj, withRotation);
    }

    public static void matchGMS(Size size1, Size size2, MatOfKeyPoint keypoints1, MatOfKeyPoint keypoints2, MatOfDMatch matches1to2, MatOfDMatch matchesGMS) {
        Size size = size1;
        Size size3 = size2;
        MatOfKeyPoint matOfKeyPoint = keypoints1;
        MatOfKeyPoint matOfKeyPoint2 = keypoints2;
        MatOfKeyPoint matOfKeyPoint3 = matOfKeyPoint;
        MatOfKeyPoint matOfKeyPoint4 = matOfKeyPoint2;
        matchGMS_3(size.width, size.height, size3.width, size3.height, matOfKeyPoint.nativeObj, matOfKeyPoint2.nativeObj, matches1to2.nativeObj, matchesGMS.nativeObj);
    }

    public static void matchLOGOS(MatOfKeyPoint keypoints1, MatOfKeyPoint keypoints2, MatOfInt nn1, MatOfInt nn2, MatOfDMatch matches1to2) {
        matchLOGOS_0(keypoints1.nativeObj, keypoints2.nativeObj, nn1.nativeObj, nn2.nativeObj, matches1to2.nativeObj);
    }
}
