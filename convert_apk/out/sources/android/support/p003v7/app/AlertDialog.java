package android.support.p003v7.app;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.support.p003v7.app.AlertController;
import android.support.p003v7.appcompat.C0049R;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

/* renamed from: android.support.v7.app.AlertDialog */
public class AlertDialog extends AppCompatDialog implements DialogInterface {
    static final int LAYOUT_HINT_NONE = 0;
    static final int LAYOUT_HINT_SIDE = 1;
    final AlertController mAlert;

    protected AlertDialog(Context context) {
        this(context, 0);
    }

    protected AlertDialog(Context context, int themeResId) {
        super(context, resolveDialogTheme(context, themeResId));
        this.mAlert = new AlertController(getContext(), this, getWindow());
    }

    protected AlertDialog(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        this(context, 0);
        setCancelable(cancelable);
        setOnCancelListener(cancelListener);
    }

    static int resolveDialogTheme(Context context, int resid) {
        if (((resid >>> 24) & 255) >= 1) {
            return resid;
        }
        TypedValue outValue = new TypedValue();
        context.getTheme().resolveAttribute(C0049R.attr.alertDialogTheme, outValue, true);
        return outValue.resourceId;
    }

    public Button getButton(int whichButton) {
        return this.mAlert.getButton(whichButton);
    }

    public ListView getListView() {
        return this.mAlert.getListView();
    }

    public void setTitle(CharSequence title) {
        super.setTitle(title);
        this.mAlert.setTitle(title);
    }

    public void setCustomTitle(View customTitleView) {
        this.mAlert.setCustomTitle(customTitleView);
    }

    public void setMessage(CharSequence message) {
        this.mAlert.setMessage(message);
    }

    public void setView(View view) {
        this.mAlert.setView(view);
    }

    public void setView(View view, int viewSpacingLeft, int viewSpacingTop, int viewSpacingRight, int viewSpacingBottom) {
        this.mAlert.setView(view, viewSpacingLeft, viewSpacingTop, viewSpacingRight, viewSpacingBottom);
    }

    /* access modifiers changed from: package-private */
    public void setButtonPanelLayoutHint(int layoutHint) {
        this.mAlert.setButtonPanelLayoutHint(layoutHint);
    }

    public void setButton(int whichButton, CharSequence text, Message msg) {
        this.mAlert.setButton(whichButton, text, (DialogInterface.OnClickListener) null, msg);
    }

    public void setButton(int whichButton, CharSequence text, DialogInterface.OnClickListener listener) {
        this.mAlert.setButton(whichButton, text, listener, (Message) null);
    }

    public void setIcon(int resId) {
        this.mAlert.setIcon(resId);
    }

    public void setIcon(Drawable icon) {
        this.mAlert.setIcon(icon);
    }

    public void setIconAttribute(int attrId) {
        TypedValue out = new TypedValue();
        getContext().getTheme().resolveAttribute(attrId, out, true);
        this.mAlert.setIcon(out.resourceId);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mAlert.installContent();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (this.mAlert.onKeyDown(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (this.mAlert.onKeyUp(keyCode, event)) {
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    /* renamed from: android.support.v7.app.AlertDialog$Builder */
    public static class Builder {

        /* renamed from: P */
        private final AlertController.AlertParams f38P;
        private final int mTheme;

        public Builder(Context context) {
            this(context, AlertDialog.resolveDialogTheme(context, 0));
        }

        public Builder(Context context, int themeResId) {
            this.f38P = new AlertController.AlertParams(new ContextThemeWrapper(context, AlertDialog.resolveDialogTheme(context, themeResId)));
            this.mTheme = themeResId;
        }

        public Context getContext() {
            return this.f38P.mContext;
        }

        public Builder setTitle(int titleId) {
            AlertController.AlertParams alertParams = this.f38P;
            alertParams.mTitle = alertParams.mContext.getText(titleId);
            return this;
        }

        public Builder setTitle(CharSequence title) {
            this.f38P.mTitle = title;
            return this;
        }

        public Builder setCustomTitle(View customTitleView) {
            this.f38P.mCustomTitleView = customTitleView;
            return this;
        }

        public Builder setMessage(int messageId) {
            AlertController.AlertParams alertParams = this.f38P;
            alertParams.mMessage = alertParams.mContext.getText(messageId);
            return this;
        }

        public Builder setMessage(CharSequence message) {
            this.f38P.mMessage = message;
            return this;
        }

        public Builder setIcon(int iconId) {
            this.f38P.mIconId = iconId;
            return this;
        }

        public Builder setIcon(Drawable icon) {
            this.f38P.mIcon = icon;
            return this;
        }

        public Builder setIconAttribute(int attrId) {
            TypedValue out = new TypedValue();
            this.f38P.mContext.getTheme().resolveAttribute(attrId, out, true);
            this.f38P.mIconId = out.resourceId;
            return this;
        }

        public Builder setPositiveButton(int textId, DialogInterface.OnClickListener listener) {
            AlertController.AlertParams alertParams = this.f38P;
            alertParams.mPositiveButtonText = alertParams.mContext.getText(textId);
            this.f38P.mPositiveButtonListener = listener;
            return this;
        }

        public Builder setPositiveButton(CharSequence text, DialogInterface.OnClickListener listener) {
            this.f38P.mPositiveButtonText = text;
            this.f38P.mPositiveButtonListener = listener;
            return this;
        }

        public Builder setNegativeButton(int textId, DialogInterface.OnClickListener listener) {
            AlertController.AlertParams alertParams = this.f38P;
            alertParams.mNegativeButtonText = alertParams.mContext.getText(textId);
            this.f38P.mNegativeButtonListener = listener;
            return this;
        }

        public Builder setNegativeButton(CharSequence text, DialogInterface.OnClickListener listener) {
            this.f38P.mNegativeButtonText = text;
            this.f38P.mNegativeButtonListener = listener;
            return this;
        }

        public Builder setNeutralButton(int textId, DialogInterface.OnClickListener listener) {
            AlertController.AlertParams alertParams = this.f38P;
            alertParams.mNeutralButtonText = alertParams.mContext.getText(textId);
            this.f38P.mNeutralButtonListener = listener;
            return this;
        }

        public Builder setNeutralButton(CharSequence text, DialogInterface.OnClickListener listener) {
            this.f38P.mNeutralButtonText = text;
            this.f38P.mNeutralButtonListener = listener;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            this.f38P.mCancelable = cancelable;
            return this;
        }

        public Builder setOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
            this.f38P.mOnCancelListener = onCancelListener;
            return this;
        }

        public Builder setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
            this.f38P.mOnDismissListener = onDismissListener;
            return this;
        }

        public Builder setOnKeyListener(DialogInterface.OnKeyListener onKeyListener) {
            this.f38P.mOnKeyListener = onKeyListener;
            return this;
        }

        public Builder setItems(int itemsId, DialogInterface.OnClickListener listener) {
            AlertController.AlertParams alertParams = this.f38P;
            alertParams.mItems = alertParams.mContext.getResources().getTextArray(itemsId);
            this.f38P.mOnClickListener = listener;
            return this;
        }

        public Builder setItems(CharSequence[] items, DialogInterface.OnClickListener listener) {
            this.f38P.mItems = items;
            this.f38P.mOnClickListener = listener;
            return this;
        }

        public Builder setAdapter(ListAdapter adapter, DialogInterface.OnClickListener listener) {
            this.f38P.mAdapter = adapter;
            this.f38P.mOnClickListener = listener;
            return this;
        }

        public Builder setCursor(Cursor cursor, DialogInterface.OnClickListener listener, String labelColumn) {
            this.f38P.mCursor = cursor;
            this.f38P.mLabelColumn = labelColumn;
            this.f38P.mOnClickListener = listener;
            return this;
        }

        public Builder setMultiChoiceItems(int itemsId, boolean[] checkedItems, DialogInterface.OnMultiChoiceClickListener listener) {
            AlertController.AlertParams alertParams = this.f38P;
            alertParams.mItems = alertParams.mContext.getResources().getTextArray(itemsId);
            this.f38P.mOnCheckboxClickListener = listener;
            this.f38P.mCheckedItems = checkedItems;
            this.f38P.mIsMultiChoice = true;
            return this;
        }

        public Builder setMultiChoiceItems(CharSequence[] items, boolean[] checkedItems, DialogInterface.OnMultiChoiceClickListener listener) {
            this.f38P.mItems = items;
            this.f38P.mOnCheckboxClickListener = listener;
            this.f38P.mCheckedItems = checkedItems;
            this.f38P.mIsMultiChoice = true;
            return this;
        }

        public Builder setMultiChoiceItems(Cursor cursor, String isCheckedColumn, String labelColumn, DialogInterface.OnMultiChoiceClickListener listener) {
            this.f38P.mCursor = cursor;
            this.f38P.mOnCheckboxClickListener = listener;
            this.f38P.mIsCheckedColumn = isCheckedColumn;
            this.f38P.mLabelColumn = labelColumn;
            this.f38P.mIsMultiChoice = true;
            return this;
        }

        public Builder setSingleChoiceItems(int itemsId, int checkedItem, DialogInterface.OnClickListener listener) {
            AlertController.AlertParams alertParams = this.f38P;
            alertParams.mItems = alertParams.mContext.getResources().getTextArray(itemsId);
            this.f38P.mOnClickListener = listener;
            this.f38P.mCheckedItem = checkedItem;
            this.f38P.mIsSingleChoice = true;
            return this;
        }

        public Builder setSingleChoiceItems(Cursor cursor, int checkedItem, String labelColumn, DialogInterface.OnClickListener listener) {
            this.f38P.mCursor = cursor;
            this.f38P.mOnClickListener = listener;
            this.f38P.mCheckedItem = checkedItem;
            this.f38P.mLabelColumn = labelColumn;
            this.f38P.mIsSingleChoice = true;
            return this;
        }

        public Builder setSingleChoiceItems(CharSequence[] items, int checkedItem, DialogInterface.OnClickListener listener) {
            this.f38P.mItems = items;
            this.f38P.mOnClickListener = listener;
            this.f38P.mCheckedItem = checkedItem;
            this.f38P.mIsSingleChoice = true;
            return this;
        }

        public Builder setSingleChoiceItems(ListAdapter adapter, int checkedItem, DialogInterface.OnClickListener listener) {
            this.f38P.mAdapter = adapter;
            this.f38P.mOnClickListener = listener;
            this.f38P.mCheckedItem = checkedItem;
            this.f38P.mIsSingleChoice = true;
            return this;
        }

        public Builder setOnItemSelectedListener(AdapterView.OnItemSelectedListener listener) {
            this.f38P.mOnItemSelectedListener = listener;
            return this;
        }

        public Builder setView(int layoutResId) {
            this.f38P.mView = null;
            this.f38P.mViewLayoutResId = layoutResId;
            this.f38P.mViewSpacingSpecified = false;
            return this;
        }

        public Builder setView(View view) {
            this.f38P.mView = view;
            this.f38P.mViewLayoutResId = 0;
            this.f38P.mViewSpacingSpecified = false;
            return this;
        }

        @Deprecated
        public Builder setView(View view, int viewSpacingLeft, int viewSpacingTop, int viewSpacingRight, int viewSpacingBottom) {
            this.f38P.mView = view;
            this.f38P.mViewLayoutResId = 0;
            this.f38P.mViewSpacingSpecified = true;
            this.f38P.mViewSpacingLeft = viewSpacingLeft;
            this.f38P.mViewSpacingTop = viewSpacingTop;
            this.f38P.mViewSpacingRight = viewSpacingRight;
            this.f38P.mViewSpacingBottom = viewSpacingBottom;
            return this;
        }

        @Deprecated
        public Builder setInverseBackgroundForced(boolean useInverseBackground) {
            this.f38P.mForceInverseBackground = useInverseBackground;
            return this;
        }

        public Builder setRecycleOnMeasureEnabled(boolean enabled) {
            this.f38P.mRecycleOnMeasure = enabled;
            return this;
        }

        public AlertDialog create() {
            AlertDialog dialog = new AlertDialog(this.f38P.mContext, this.mTheme);
            this.f38P.apply(dialog.mAlert);
            dialog.setCancelable(this.f38P.mCancelable);
            if (this.f38P.mCancelable) {
                dialog.setCanceledOnTouchOutside(true);
            }
            dialog.setOnCancelListener(this.f38P.mOnCancelListener);
            dialog.setOnDismissListener(this.f38P.mOnDismissListener);
            if (this.f38P.mOnKeyListener != null) {
                dialog.setOnKeyListener(this.f38P.mOnKeyListener);
            }
            return dialog;
        }

        public AlertDialog show() {
            AlertDialog dialog = create();
            dialog.show();
            return dialog;
        }
    }
}
