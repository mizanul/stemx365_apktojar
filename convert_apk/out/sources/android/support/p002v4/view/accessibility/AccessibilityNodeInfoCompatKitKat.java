package android.support.p002v4.view.accessibility;

import android.view.accessibility.AccessibilityNodeInfo;

/* renamed from: android.support.v4.view.accessibility.AccessibilityNodeInfoCompatKitKat */
class AccessibilityNodeInfoCompatKitKat {

    /* renamed from: android.support.v4.view.accessibility.AccessibilityNodeInfoCompatKitKat$RangeInfo */
    static class RangeInfo {
        RangeInfo() {
        }

        static float getCurrent(Object info) {
            return ((AccessibilityNodeInfo.RangeInfo) info).getCurrent();
        }

        static float getMax(Object info) {
            return ((AccessibilityNodeInfo.RangeInfo) info).getMax();
        }

        static float getMin(Object info) {
            return ((AccessibilityNodeInfo.RangeInfo) info).getMin();
        }

        static int getType(Object info) {
            return ((AccessibilityNodeInfo.RangeInfo) info).getType();
        }
    }

    AccessibilityNodeInfoCompatKitKat() {
    }
}
