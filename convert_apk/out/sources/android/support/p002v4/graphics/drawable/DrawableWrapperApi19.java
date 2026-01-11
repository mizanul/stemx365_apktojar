package android.support.p002v4.graphics.drawable;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.p002v4.graphics.drawable.DrawableWrapperApi14;

/* renamed from: android.support.v4.graphics.drawable.DrawableWrapperApi19 */
class DrawableWrapperApi19 extends DrawableWrapperApi14 {
    DrawableWrapperApi19(Drawable drawable) {
        super(drawable);
    }

    DrawableWrapperApi19(DrawableWrapperApi14.DrawableWrapperState state, Resources resources) {
        super(state, resources);
    }

    public void setAutoMirrored(boolean mirrored) {
        this.mDrawable.setAutoMirrored(mirrored);
    }

    public boolean isAutoMirrored() {
        return this.mDrawable.isAutoMirrored();
    }

    /* access modifiers changed from: package-private */
    public DrawableWrapperApi14.DrawableWrapperState mutateConstantState() {
        return new DrawableWrapperStateKitKat(this.mState, (Resources) null);
    }

    /* renamed from: android.support.v4.graphics.drawable.DrawableWrapperApi19$DrawableWrapperStateKitKat */
    private static class DrawableWrapperStateKitKat extends DrawableWrapperApi14.DrawableWrapperState {
        DrawableWrapperStateKitKat(DrawableWrapperApi14.DrawableWrapperState orig, Resources res) {
            super(orig, res);
        }

        public Drawable newDrawable(Resources res) {
            return new DrawableWrapperApi19(this, res);
        }
    }
}
