package android.support.p003v7.widget;

import android.content.Context;
import android.os.Build;
import android.support.p002v4.view.ViewPropertyAnimatorCompat;
import android.support.p002v4.widget.ListViewAutoScrollHelper;
import android.support.p003v7.appcompat.C0049R;
import android.util.AttributeSet;
import android.view.View;

/* renamed from: android.support.v7.widget.DropDownListView */
class DropDownListView extends ListViewCompat {
    private ViewPropertyAnimatorCompat mClickAnimation;
    private boolean mDrawsInPressedState;
    private boolean mHijackFocus;
    private boolean mListSelectionHidden;
    private ListViewAutoScrollHelper mScrollHelper;

    public DropDownListView(Context context, boolean hijackFocus) {
        super(context, (AttributeSet) null, C0049R.attr.dropDownListViewStyle);
        this.mHijackFocus = hijackFocus;
        setCacheColorHint(0);
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x004c  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0062  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onForwardedEvent(android.view.MotionEvent r12, int r13) {
        /*
            r11 = this;
            r0 = 1
            r1 = 0
            int r2 = r12.getActionMasked()
            r3 = 1
            if (r2 == r3) goto L_0x0012
            r4 = 2
            if (r2 == r4) goto L_0x0013
            r4 = 3
            if (r2 == r4) goto L_0x0010
            goto L_0x0043
        L_0x0010:
            r0 = 0
            goto L_0x0043
        L_0x0012:
            r0 = 0
        L_0x0013:
            int r4 = r12.findPointerIndex(r13)
            if (r4 >= 0) goto L_0x001b
            r0 = 0
            goto L_0x0043
        L_0x001b:
            float r5 = r12.getX(r4)
            int r5 = (int) r5
            float r6 = r12.getY(r4)
            int r6 = (int) r6
            int r7 = r11.pointToPosition(r5, r6)
            r8 = -1
            if (r7 != r8) goto L_0x002e
            r1 = 1
            goto L_0x0043
        L_0x002e:
            int r8 = r11.getFirstVisiblePosition()
            int r8 = r7 - r8
            android.view.View r8 = r11.getChildAt(r8)
            float r9 = (float) r5
            float r10 = (float) r6
            r11.setPressedItem(r8, r7, r9, r10)
            r0 = 1
            if (r2 != r3) goto L_0x0043
            r11.clickPressedItem(r8, r7)
        L_0x0043:
            if (r0 == 0) goto L_0x0047
            if (r1 == 0) goto L_0x004a
        L_0x0047:
            r11.clearPressedItem()
        L_0x004a:
            if (r0 == 0) goto L_0x0062
            android.support.v4.widget.ListViewAutoScrollHelper r4 = r11.mScrollHelper
            if (r4 != 0) goto L_0x0057
            android.support.v4.widget.ListViewAutoScrollHelper r4 = new android.support.v4.widget.ListViewAutoScrollHelper
            r4.<init>(r11)
            r11.mScrollHelper = r4
        L_0x0057:
            android.support.v4.widget.ListViewAutoScrollHelper r4 = r11.mScrollHelper
            r4.setEnabled(r3)
            android.support.v4.widget.ListViewAutoScrollHelper r3 = r11.mScrollHelper
            r3.onTouch(r11, r12)
            goto L_0x006a
        L_0x0062:
            android.support.v4.widget.ListViewAutoScrollHelper r3 = r11.mScrollHelper
            if (r3 == 0) goto L_0x006a
            r4 = 0
            r3.setEnabled(r4)
        L_0x006a:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.p003v7.widget.DropDownListView.onForwardedEvent(android.view.MotionEvent, int):boolean");
    }

    private void clickPressedItem(View child, int position) {
        performItemClick(child, position, getItemIdAtPosition(position));
    }

    /* access modifiers changed from: package-private */
    public void setListSelectionHidden(boolean hideListSelection) {
        this.mListSelectionHidden = hideListSelection;
    }

    private void clearPressedItem() {
        this.mDrawsInPressedState = false;
        setPressed(false);
        drawableStateChanged();
        View motionView = getChildAt(this.mMotionPosition - getFirstVisiblePosition());
        if (motionView != null) {
            motionView.setPressed(false);
        }
        ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = this.mClickAnimation;
        if (viewPropertyAnimatorCompat != null) {
            viewPropertyAnimatorCompat.cancel();
            this.mClickAnimation = null;
        }
    }

    private void setPressedItem(View child, int position, float x, float y) {
        View motionView;
        this.mDrawsInPressedState = true;
        if (Build.VERSION.SDK_INT >= 21) {
            drawableHotspotChanged(x, y);
        }
        if (!isPressed()) {
            setPressed(true);
        }
        layoutChildren();
        if (!(this.mMotionPosition == -1 || (motionView = getChildAt(this.mMotionPosition - getFirstVisiblePosition())) == null || motionView == child || !motionView.isPressed())) {
            motionView.setPressed(false);
        }
        this.mMotionPosition = position;
        float childX = x - ((float) child.getLeft());
        float childY = y - ((float) child.getTop());
        if (Build.VERSION.SDK_INT >= 21) {
            child.drawableHotspotChanged(childX, childY);
        }
        if (!child.isPressed()) {
            child.setPressed(true);
        }
        positionSelectorLikeTouchCompat(position, child, x, y);
        setSelectorEnabled(false);
        refreshDrawableState();
    }

    /* access modifiers changed from: protected */
    public boolean touchModeDrawsInPressedStateCompat() {
        return this.mDrawsInPressedState || super.touchModeDrawsInPressedStateCompat();
    }

    public boolean isInTouchMode() {
        return (this.mHijackFocus && this.mListSelectionHidden) || super.isInTouchMode();
    }

    public boolean hasWindowFocus() {
        return this.mHijackFocus || super.hasWindowFocus();
    }

    public boolean isFocused() {
        return this.mHijackFocus || super.isFocused();
    }

    public boolean hasFocus() {
        return this.mHijackFocus || super.hasFocus();
    }
}
