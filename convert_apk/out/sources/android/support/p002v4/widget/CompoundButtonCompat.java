package android.support.p002v4.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.widget.CompoundButton;
import java.lang.reflect.Field;

/* renamed from: android.support.v4.widget.CompoundButtonCompat */
public final class CompoundButtonCompat {
    private static final CompoundButtonCompatBaseImpl IMPL;

    static {
        if (Build.VERSION.SDK_INT >= 23) {
            IMPL = new CompoundButtonCompatApi23Impl();
        } else if (Build.VERSION.SDK_INT >= 21) {
            IMPL = new CompoundButtonCompatApi21Impl();
        } else {
            IMPL = new CompoundButtonCompatBaseImpl();
        }
    }

    /* renamed from: android.support.v4.widget.CompoundButtonCompat$CompoundButtonCompatBaseImpl */
    static class CompoundButtonCompatBaseImpl {
        private static final String TAG = "CompoundButtonCompat";
        private static Field sButtonDrawableField;
        private static boolean sButtonDrawableFieldFetched;

        CompoundButtonCompatBaseImpl() {
        }

        public void setButtonTintList(CompoundButton button, ColorStateList tint) {
            if (button instanceof TintableCompoundButton) {
                ((TintableCompoundButton) button).setSupportButtonTintList(tint);
            }
        }

        public ColorStateList getButtonTintList(CompoundButton button) {
            if (button instanceof TintableCompoundButton) {
                return ((TintableCompoundButton) button).getSupportButtonTintList();
            }
            return null;
        }

        public void setButtonTintMode(CompoundButton button, PorterDuff.Mode tintMode) {
            if (button instanceof TintableCompoundButton) {
                ((TintableCompoundButton) button).setSupportButtonTintMode(tintMode);
            }
        }

        public PorterDuff.Mode getButtonTintMode(CompoundButton button) {
            if (button instanceof TintableCompoundButton) {
                return ((TintableCompoundButton) button).getSupportButtonTintMode();
            }
            return null;
        }

        public Drawable getButtonDrawable(CompoundButton button) {
            if (!sButtonDrawableFieldFetched) {
                try {
                    Field declaredField = CompoundButton.class.getDeclaredField("mButtonDrawable");
                    sButtonDrawableField = declaredField;
                    declaredField.setAccessible(true);
                } catch (NoSuchFieldException e) {
                    Log.i(TAG, "Failed to retrieve mButtonDrawable field", e);
                }
                sButtonDrawableFieldFetched = true;
            }
            Field field = sButtonDrawableField;
            if (field != null) {
                try {
                    return (Drawable) field.get(button);
                } catch (IllegalAccessException e2) {
                    Log.i(TAG, "Failed to get button drawable via reflection", e2);
                    sButtonDrawableField = null;
                }
            }
            return null;
        }
    }

    /* renamed from: android.support.v4.widget.CompoundButtonCompat$CompoundButtonCompatApi21Impl */
    static class CompoundButtonCompatApi21Impl extends CompoundButtonCompatBaseImpl {
        CompoundButtonCompatApi21Impl() {
        }

        public void setButtonTintList(CompoundButton button, ColorStateList tint) {
            button.setButtonTintList(tint);
        }

        public ColorStateList getButtonTintList(CompoundButton button) {
            return button.getButtonTintList();
        }

        public void setButtonTintMode(CompoundButton button, PorterDuff.Mode tintMode) {
            button.setButtonTintMode(tintMode);
        }

        public PorterDuff.Mode getButtonTintMode(CompoundButton button) {
            return button.getButtonTintMode();
        }
    }

    /* renamed from: android.support.v4.widget.CompoundButtonCompat$CompoundButtonCompatApi23Impl */
    static class CompoundButtonCompatApi23Impl extends CompoundButtonCompatApi21Impl {
        CompoundButtonCompatApi23Impl() {
        }

        public Drawable getButtonDrawable(CompoundButton button) {
            return button.getButtonDrawable();
        }
    }

    private CompoundButtonCompat() {
    }

    public static void setButtonTintList(CompoundButton button, ColorStateList tint) {
        IMPL.setButtonTintList(button, tint);
    }

    public static ColorStateList getButtonTintList(CompoundButton button) {
        return IMPL.getButtonTintList(button);
    }

    public static void setButtonTintMode(CompoundButton button, PorterDuff.Mode tintMode) {
        IMPL.setButtonTintMode(button, tintMode);
    }

    public static PorterDuff.Mode getButtonTintMode(CompoundButton button) {
        return IMPL.getButtonTintMode(button);
    }

    public static Drawable getButtonDrawable(CompoundButton button) {
        return IMPL.getButtonDrawable(button);
    }
}
