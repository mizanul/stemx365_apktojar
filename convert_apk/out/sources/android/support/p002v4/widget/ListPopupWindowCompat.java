package android.support.p002v4.widget;

import android.os.Build;
import android.view.View;
import android.widget.ListPopupWindow;

/* renamed from: android.support.v4.widget.ListPopupWindowCompat */
public final class ListPopupWindowCompat {
    private ListPopupWindowCompat() {
    }

    @Deprecated
    public static View.OnTouchListener createDragToOpenListener(Object listPopupWindow, View src2) {
        return createDragToOpenListener((ListPopupWindow) listPopupWindow, src2);
    }

    public static View.OnTouchListener createDragToOpenListener(ListPopupWindow listPopupWindow, View src2) {
        if (Build.VERSION.SDK_INT >= 19) {
            return listPopupWindow.createDragToOpenListener(src2);
        }
        return null;
    }
}
