package android.support.constraint.solver.widgets;

import android.support.constraint.solver.ArrayRow;
import android.support.constraint.solver.Cache;
import android.support.constraint.solver.LinearSystem;
import android.support.constraint.solver.SolverVariable;
import android.support.constraint.solver.widgets.ConstraintAnchor;
import java.util.ArrayList;

public class ConstraintWidget {
    private static final boolean AUTOTAG_CENTER = false;
    public static final int CHAIN_PACKED = 2;
    public static final int CHAIN_SPREAD = 0;
    public static final int CHAIN_SPREAD_INSIDE = 1;
    public static float DEFAULT_BIAS = 0.5f;
    protected static final int DIRECT = 2;
    public static final int GONE = 8;
    public static final int HORIZONTAL = 0;
    public static final int INVISIBLE = 4;
    public static final int MATCH_CONSTRAINT_SPREAD = 0;
    public static final int MATCH_CONSTRAINT_WRAP = 1;
    protected static final int SOLVER = 1;
    public static final int UNKNOWN = -1;
    public static final int VERTICAL = 1;
    public static final int VISIBLE = 0;
    protected ArrayList<ConstraintAnchor> mAnchors;
    ConstraintAnchor mBaseline;
    int mBaselineDistance;
    ConstraintAnchor mBottom;
    boolean mBottomHasCentered;
    ConstraintAnchor mCenter;
    ConstraintAnchor mCenterX;
    ConstraintAnchor mCenterY;
    private Object mCompanionWidget;
    private int mContainerItemSkip;
    private String mDebugName;
    protected float mDimensionRatio;
    protected int mDimensionRatioSide;
    int mDistToBottom;
    int mDistToLeft;
    int mDistToRight;
    int mDistToTop;
    private int mDrawHeight;
    private int mDrawWidth;
    private int mDrawX;
    private int mDrawY;
    int mHeight;
    float mHorizontalBiasPercent;
    boolean mHorizontalChainFixedPosition;
    int mHorizontalChainStyle;
    DimensionBehaviour mHorizontalDimensionBehaviour;
    ConstraintWidget mHorizontalNextWidget;
    public int mHorizontalResolution;
    float mHorizontalWeight;
    boolean mHorizontalWrapVisited;
    ConstraintAnchor mLeft;
    boolean mLeftHasCentered;
    int mMatchConstraintDefaultHeight;
    int mMatchConstraintDefaultWidth;
    int mMatchConstraintMaxHeight;
    int mMatchConstraintMaxWidth;
    int mMatchConstraintMinHeight;
    int mMatchConstraintMinWidth;
    protected int mMinHeight;
    protected int mMinWidth;
    protected int mOffsetX;
    protected int mOffsetY;
    ConstraintWidget mParent;
    ConstraintAnchor mRight;
    boolean mRightHasCentered;
    private int mSolverBottom;
    private int mSolverLeft;
    private int mSolverRight;
    private int mSolverTop;
    ConstraintAnchor mTop;
    boolean mTopHasCentered;
    private String mType;
    float mVerticalBiasPercent;
    boolean mVerticalChainFixedPosition;
    int mVerticalChainStyle;
    DimensionBehaviour mVerticalDimensionBehaviour;
    ConstraintWidget mVerticalNextWidget;
    public int mVerticalResolution;
    float mVerticalWeight;
    boolean mVerticalWrapVisited;
    private int mVisibility;
    int mWidth;
    private int mWrapHeight;
    private int mWrapWidth;

    /* renamed from: mX */
    protected int f17mX;

    /* renamed from: mY */
    protected int f18mY;

    public enum ContentAlignment {
        BEGIN,
        MIDDLE,
        END,
        TOP,
        VERTICAL_MIDDLE,
        BOTTOM,
        LEFT,
        RIGHT
    }

    public enum DimensionBehaviour {
        FIXED,
        WRAP_CONTENT,
        MATCH_CONSTRAINT,
        MATCH_PARENT
    }

    public void reset() {
        this.mLeft.reset();
        this.mTop.reset();
        this.mRight.reset();
        this.mBottom.reset();
        this.mBaseline.reset();
        this.mCenterX.reset();
        this.mCenterY.reset();
        this.mCenter.reset();
        this.mParent = null;
        this.mWidth = 0;
        this.mHeight = 0;
        this.mDimensionRatio = 0.0f;
        this.mDimensionRatioSide = -1;
        this.f17mX = 0;
        this.f18mY = 0;
        this.mDrawX = 0;
        this.mDrawY = 0;
        this.mDrawWidth = 0;
        this.mDrawHeight = 0;
        this.mOffsetX = 0;
        this.mOffsetY = 0;
        this.mBaselineDistance = 0;
        this.mMinWidth = 0;
        this.mMinHeight = 0;
        this.mWrapWidth = 0;
        this.mWrapHeight = 0;
        float f = DEFAULT_BIAS;
        this.mHorizontalBiasPercent = f;
        this.mVerticalBiasPercent = f;
        this.mHorizontalDimensionBehaviour = DimensionBehaviour.FIXED;
        this.mVerticalDimensionBehaviour = DimensionBehaviour.FIXED;
        this.mCompanionWidget = null;
        this.mContainerItemSkip = 0;
        this.mVisibility = 0;
        this.mDebugName = null;
        this.mType = null;
        this.mHorizontalWrapVisited = false;
        this.mVerticalWrapVisited = false;
        this.mHorizontalChainStyle = 0;
        this.mVerticalChainStyle = 0;
        this.mHorizontalChainFixedPosition = false;
        this.mVerticalChainFixedPosition = false;
        this.mHorizontalWeight = 0.0f;
        this.mVerticalWeight = 0.0f;
        this.mHorizontalResolution = -1;
        this.mVerticalResolution = -1;
    }

    public ConstraintWidget() {
        this.mHorizontalResolution = -1;
        this.mVerticalResolution = -1;
        this.mMatchConstraintDefaultWidth = 0;
        this.mMatchConstraintDefaultHeight = 0;
        this.mMatchConstraintMinWidth = 0;
        this.mMatchConstraintMaxWidth = 0;
        this.mMatchConstraintMinHeight = 0;
        this.mMatchConstraintMaxHeight = 0;
        this.mLeft = new ConstraintAnchor(this, ConstraintAnchor.Type.LEFT);
        this.mTop = new ConstraintAnchor(this, ConstraintAnchor.Type.TOP);
        this.mRight = new ConstraintAnchor(this, ConstraintAnchor.Type.RIGHT);
        this.mBottom = new ConstraintAnchor(this, ConstraintAnchor.Type.BOTTOM);
        this.mBaseline = new ConstraintAnchor(this, ConstraintAnchor.Type.BASELINE);
        this.mCenterX = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER_X);
        this.mCenterY = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER_Y);
        this.mCenter = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER);
        this.mAnchors = new ArrayList<>();
        this.mParent = null;
        this.mWidth = 0;
        this.mHeight = 0;
        this.mDimensionRatio = 0.0f;
        this.mDimensionRatioSide = -1;
        this.mSolverLeft = 0;
        this.mSolverTop = 0;
        this.mSolverRight = 0;
        this.mSolverBottom = 0;
        this.f17mX = 0;
        this.f18mY = 0;
        this.mDrawX = 0;
        this.mDrawY = 0;
        this.mDrawWidth = 0;
        this.mDrawHeight = 0;
        this.mOffsetX = 0;
        this.mOffsetY = 0;
        this.mBaselineDistance = 0;
        float f = DEFAULT_BIAS;
        this.mHorizontalBiasPercent = f;
        this.mVerticalBiasPercent = f;
        this.mHorizontalDimensionBehaviour = DimensionBehaviour.FIXED;
        this.mVerticalDimensionBehaviour = DimensionBehaviour.FIXED;
        this.mContainerItemSkip = 0;
        this.mVisibility = 0;
        this.mDebugName = null;
        this.mType = null;
        this.mHorizontalChainStyle = 0;
        this.mVerticalChainStyle = 0;
        this.mHorizontalWeight = 0.0f;
        this.mVerticalWeight = 0.0f;
        this.mHorizontalNextWidget = null;
        this.mVerticalNextWidget = null;
        addAnchors();
    }

    public ConstraintWidget(int x, int y, int width, int height) {
        this.mHorizontalResolution = -1;
        this.mVerticalResolution = -1;
        this.mMatchConstraintDefaultWidth = 0;
        this.mMatchConstraintDefaultHeight = 0;
        this.mMatchConstraintMinWidth = 0;
        this.mMatchConstraintMaxWidth = 0;
        this.mMatchConstraintMinHeight = 0;
        this.mMatchConstraintMaxHeight = 0;
        this.mLeft = new ConstraintAnchor(this, ConstraintAnchor.Type.LEFT);
        this.mTop = new ConstraintAnchor(this, ConstraintAnchor.Type.TOP);
        this.mRight = new ConstraintAnchor(this, ConstraintAnchor.Type.RIGHT);
        this.mBottom = new ConstraintAnchor(this, ConstraintAnchor.Type.BOTTOM);
        this.mBaseline = new ConstraintAnchor(this, ConstraintAnchor.Type.BASELINE);
        this.mCenterX = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER_X);
        this.mCenterY = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER_Y);
        this.mCenter = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER);
        this.mAnchors = new ArrayList<>();
        this.mParent = null;
        this.mWidth = 0;
        this.mHeight = 0;
        this.mDimensionRatio = 0.0f;
        this.mDimensionRatioSide = -1;
        this.mSolverLeft = 0;
        this.mSolverTop = 0;
        this.mSolverRight = 0;
        this.mSolverBottom = 0;
        this.f17mX = 0;
        this.f18mY = 0;
        this.mDrawX = 0;
        this.mDrawY = 0;
        this.mDrawWidth = 0;
        this.mDrawHeight = 0;
        this.mOffsetX = 0;
        this.mOffsetY = 0;
        this.mBaselineDistance = 0;
        float f = DEFAULT_BIAS;
        this.mHorizontalBiasPercent = f;
        this.mVerticalBiasPercent = f;
        this.mHorizontalDimensionBehaviour = DimensionBehaviour.FIXED;
        this.mVerticalDimensionBehaviour = DimensionBehaviour.FIXED;
        this.mContainerItemSkip = 0;
        this.mVisibility = 0;
        this.mDebugName = null;
        this.mType = null;
        this.mHorizontalChainStyle = 0;
        this.mVerticalChainStyle = 0;
        this.mHorizontalWeight = 0.0f;
        this.mVerticalWeight = 0.0f;
        this.mHorizontalNextWidget = null;
        this.mVerticalNextWidget = null;
        this.f17mX = x;
        this.f18mY = y;
        this.mWidth = width;
        this.mHeight = height;
        addAnchors();
        forceUpdateDrawPosition();
    }

    public ConstraintWidget(int width, int height) {
        this(0, 0, width, height);
    }

    public void resetSolverVariables(Cache cache) {
        this.mLeft.resetSolverVariable(cache);
        this.mTop.resetSolverVariable(cache);
        this.mRight.resetSolverVariable(cache);
        this.mBottom.resetSolverVariable(cache);
        this.mBaseline.resetSolverVariable(cache);
        this.mCenter.resetSolverVariable(cache);
        this.mCenterX.resetSolverVariable(cache);
        this.mCenterY.resetSolverVariable(cache);
    }

    public void resetGroups() {
        int numAnchors = this.mAnchors.size();
        for (int i = 0; i < numAnchors; i++) {
            this.mAnchors.get(i).mGroup = Integer.MAX_VALUE;
        }
    }

    private void addAnchors() {
        this.mAnchors.add(this.mLeft);
        this.mAnchors.add(this.mTop);
        this.mAnchors.add(this.mRight);
        this.mAnchors.add(this.mBottom);
        this.mAnchors.add(this.mCenterX);
        this.mAnchors.add(this.mCenterY);
        this.mAnchors.add(this.mBaseline);
    }

    public boolean isRoot() {
        return this.mParent == null;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0004, code lost:
        r0 = r1.mParent;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean isRootContainer() {
        /*
            r1 = this;
            boolean r0 = r1 instanceof android.support.constraint.solver.widgets.ConstraintWidgetContainer
            if (r0 == 0) goto L_0x000e
            android.support.constraint.solver.widgets.ConstraintWidget r0 = r1.mParent
            if (r0 == 0) goto L_0x000c
            boolean r0 = r0 instanceof android.support.constraint.solver.widgets.ConstraintWidgetContainer
            if (r0 != 0) goto L_0x000e
        L_0x000c:
            r0 = 1
            goto L_0x000f
        L_0x000e:
            r0 = 0
        L_0x000f:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.constraint.solver.widgets.ConstraintWidget.isRootContainer():boolean");
    }

    public boolean isInsideConstraintLayout() {
        ConstraintWidget widget = getParent();
        if (widget == null) {
            return false;
        }
        while (widget != null) {
            if (widget instanceof ConstraintWidgetContainer) {
                return true;
            }
            widget = widget.getParent();
        }
        return false;
    }

    public boolean hasAncestor(ConstraintWidget widget) {
        ConstraintWidget parent = getParent();
        if (parent == widget) {
            return true;
        }
        if (parent == widget.getParent()) {
            return false;
        }
        while (parent != null) {
            if (parent == widget || parent == widget.getParent()) {
                return true;
            }
            parent = parent.getParent();
        }
        return false;
    }

    public WidgetContainer getRootWidgetContainer() {
        ConstraintWidget root = this;
        while (root.getParent() != null) {
            root = root.getParent();
        }
        if (root instanceof WidgetContainer) {
            return (WidgetContainer) root;
        }
        return null;
    }

    public ConstraintWidget getParent() {
        return this.mParent;
    }

    public void setParent(ConstraintWidget widget) {
        this.mParent = widget;
    }

    public String getType() {
        return this.mType;
    }

    public void setType(String type) {
        this.mType = type;
    }

    public void setVisibility(int visibility) {
        this.mVisibility = visibility;
    }

    public int getVisibility() {
        return this.mVisibility;
    }

    public String getDebugName() {
        return this.mDebugName;
    }

    public void setDebugName(String name) {
        this.mDebugName = name;
    }

    public void setDebugSolverName(LinearSystem system, String name) {
        this.mDebugName = name;
        SolverVariable left = system.createObjectVariable(this.mLeft);
        SolverVariable top = system.createObjectVariable(this.mTop);
        SolverVariable right = system.createObjectVariable(this.mRight);
        SolverVariable bottom = system.createObjectVariable(this.mBottom);
        left.setName(name + ".left");
        top.setName(name + ".top");
        right.setName(name + ".right");
        bottom.setName(name + ".bottom");
        if (this.mBaselineDistance > 0) {
            SolverVariable baseline = system.createObjectVariable(this.mBaseline);
            baseline.setName(name + ".baseline");
        }
    }

    public String toString() {
        String str;
        StringBuilder sb = new StringBuilder();
        String str2 = "";
        if (this.mType != null) {
            str = "type: " + this.mType + " ";
        } else {
            str = str2;
        }
        sb.append(str);
        if (this.mDebugName != null) {
            str2 = "id: " + this.mDebugName + " ";
        }
        sb.append(str2);
        sb.append("(");
        sb.append(this.f17mX);
        sb.append(", ");
        sb.append(this.f18mY);
        sb.append(") - (");
        sb.append(this.mWidth);
        sb.append(" x ");
        sb.append(this.mHeight);
        sb.append(")");
        sb.append(" wrap: (");
        sb.append(this.mWrapWidth);
        sb.append(" x ");
        sb.append(this.mWrapHeight);
        sb.append(")");
        return sb.toString();
    }

    /* access modifiers changed from: package-private */
    public int getInternalDrawX() {
        return this.mDrawX;
    }

    /* access modifiers changed from: package-private */
    public int getInternalDrawY() {
        return this.mDrawY;
    }

    public int getInternalDrawRight() {
        return this.mDrawX + this.mDrawWidth;
    }

    public int getInternalDrawBottom() {
        return this.mDrawY + this.mDrawHeight;
    }

    public int getX() {
        return this.f17mX;
    }

    public int getY() {
        return this.f18mY;
    }

    public int getWidth() {
        if (this.mVisibility == 8) {
            return 0;
        }
        return this.mWidth;
    }

    public int getOptimizerWrapWidth() {
        int w;
        int w2 = this.mWidth;
        if (this.mHorizontalDimensionBehaviour != DimensionBehaviour.MATCH_CONSTRAINT) {
            return w2;
        }
        if (this.mMatchConstraintDefaultWidth == 1) {
            w = Math.max(this.mMatchConstraintMinWidth, w2);
        } else if (this.mMatchConstraintMinWidth > 0) {
            w = this.mMatchConstraintMinWidth;
            this.mWidth = w;
        } else {
            w = 0;
        }
        int i = this.mMatchConstraintMaxWidth;
        if (i <= 0 || i >= w) {
            return w;
        }
        return this.mMatchConstraintMaxWidth;
    }

    public int getOptimizerWrapHeight() {
        int h;
        int h2 = this.mHeight;
        if (this.mVerticalDimensionBehaviour != DimensionBehaviour.MATCH_CONSTRAINT) {
            return h2;
        }
        if (this.mMatchConstraintDefaultHeight == 1) {
            h = Math.max(this.mMatchConstraintMinHeight, h2);
        } else if (this.mMatchConstraintMinHeight > 0) {
            h = this.mMatchConstraintMinHeight;
            this.mHeight = h;
        } else {
            h = 0;
        }
        int i = this.mMatchConstraintMaxHeight;
        if (i <= 0 || i >= h) {
            return h;
        }
        return this.mMatchConstraintMaxHeight;
    }

    public int getWrapWidth() {
        return this.mWrapWidth;
    }

    public int getHeight() {
        if (this.mVisibility == 8) {
            return 0;
        }
        return this.mHeight;
    }

    public int getWrapHeight() {
        return this.mWrapHeight;
    }

    public int getDrawX() {
        return this.mDrawX + this.mOffsetX;
    }

    public int getDrawY() {
        return this.mDrawY + this.mOffsetY;
    }

    public int getDrawWidth() {
        return this.mDrawWidth;
    }

    public int getDrawHeight() {
        return this.mDrawHeight;
    }

    public int getDrawBottom() {
        return getDrawY() + this.mDrawHeight;
    }

    public int getDrawRight() {
        return getDrawX() + this.mDrawWidth;
    }

    /* access modifiers changed from: protected */
    public int getRootX() {
        return this.f17mX + this.mOffsetX;
    }

    /* access modifiers changed from: protected */
    public int getRootY() {
        return this.f18mY + this.mOffsetY;
    }

    public int getMinWidth() {
        return this.mMinWidth;
    }

    public int getMinHeight() {
        return this.mMinHeight;
    }

    public int getLeft() {
        return getX();
    }

    public int getTop() {
        return getY();
    }

    public int getRight() {
        return getX() + this.mWidth;
    }

    public int getBottom() {
        return getY() + this.mHeight;
    }

    public float getHorizontalBiasPercent() {
        return this.mHorizontalBiasPercent;
    }

    public float getVerticalBiasPercent() {
        return this.mVerticalBiasPercent;
    }

    public boolean hasBaseline() {
        return this.mBaselineDistance > 0;
    }

    public int getBaselineDistance() {
        return this.mBaselineDistance;
    }

    public Object getCompanionWidget() {
        return this.mCompanionWidget;
    }

    public ArrayList<ConstraintAnchor> getAnchors() {
        return this.mAnchors;
    }

    public void setX(int x) {
        this.f17mX = x;
    }

    public void setY(int y) {
        this.f18mY = y;
    }

    public void setOrigin(int x, int y) {
        this.f17mX = x;
        this.f18mY = y;
    }

    public void setOffset(int x, int y) {
        this.mOffsetX = x;
        this.mOffsetY = y;
    }

    /* renamed from: android.support.constraint.solver.widgets.ConstraintWidget$1 */
    static /* synthetic */ class C00851 {

        /* renamed from: $SwitchMap$android$support$constraint$solver$widgets$ConstraintAnchor$Type */
        static final /* synthetic */ int[] f19x1d400623;

        static {
            int[] iArr = new int[ConstraintAnchor.Type.values().length];
            f19x1d400623 = iArr;
            try {
                iArr[ConstraintAnchor.Type.LEFT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f19x1d400623[ConstraintAnchor.Type.TOP.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f19x1d400623[ConstraintAnchor.Type.RIGHT.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f19x1d400623[ConstraintAnchor.Type.BOTTOM.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f19x1d400623[ConstraintAnchor.Type.BASELINE.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                f19x1d400623[ConstraintAnchor.Type.CENTER_X.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                f19x1d400623[ConstraintAnchor.Type.CENTER_Y.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                f19x1d400623[ConstraintAnchor.Type.CENTER.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
        }
    }

    public void setGoneMargin(ConstraintAnchor.Type type, int goneMargin) {
        int i = C00851.f19x1d400623[type.ordinal()];
        if (i == 1) {
            this.mLeft.mGoneMargin = goneMargin;
        } else if (i == 2) {
            this.mTop.mGoneMargin = goneMargin;
        } else if (i == 3) {
            this.mRight.mGoneMargin = goneMargin;
        } else if (i == 4) {
            this.mBottom.mGoneMargin = goneMargin;
        }
    }

    public void updateDrawPosition() {
        int left = this.f17mX;
        int top = this.f18mY;
        int right = this.f17mX + this.mWidth;
        int bottom = this.f18mY + this.mHeight;
        this.mDrawX = left;
        this.mDrawY = top;
        this.mDrawWidth = right - left;
        this.mDrawHeight = bottom - top;
    }

    public void forceUpdateDrawPosition() {
        int left = this.f17mX;
        int top = this.f18mY;
        int right = this.f17mX + this.mWidth;
        int bottom = this.f18mY + this.mHeight;
        this.mDrawX = left;
        this.mDrawY = top;
        this.mDrawWidth = right - left;
        this.mDrawHeight = bottom - top;
    }

    public void setDrawOrigin(int x, int y) {
        int i = x - this.mOffsetX;
        this.mDrawX = i;
        int i2 = y - this.mOffsetY;
        this.mDrawY = i2;
        this.f17mX = i;
        this.f18mY = i2;
    }

    public void setDrawX(int x) {
        int i = x - this.mOffsetX;
        this.mDrawX = i;
        this.f17mX = i;
    }

    public void setDrawY(int y) {
        int i = y - this.mOffsetY;
        this.mDrawY = i;
        this.f18mY = i;
    }

    public void setDrawWidth(int drawWidth) {
        this.mDrawWidth = drawWidth;
    }

    public void setDrawHeight(int drawHeight) {
        this.mDrawHeight = drawHeight;
    }

    public void setWidth(int w) {
        this.mWidth = w;
        int i = this.mMinWidth;
        if (w < i) {
            this.mWidth = i;
        }
    }

    public void setHeight(int h) {
        this.mHeight = h;
        int i = this.mMinHeight;
        if (h < i) {
            this.mHeight = i;
        }
    }

    public void setHorizontalMatchStyle(int horizontalMatchStyle, int min, int max) {
        this.mMatchConstraintDefaultWidth = horizontalMatchStyle;
        this.mMatchConstraintMinWidth = min;
        this.mMatchConstraintMaxWidth = max;
    }

    public void setVerticalMatchStyle(int verticalMatchStyle, int min, int max) {
        this.mMatchConstraintDefaultHeight = verticalMatchStyle;
        this.mMatchConstraintMinHeight = min;
        this.mMatchConstraintMaxHeight = max;
    }

    public void setDimensionRatio(String ratio) {
        int commaIndex;
        if (ratio == null || ratio.length() == 0) {
            this.mDimensionRatio = 0.0f;
            return;
        }
        int dimensionRatioSide = -1;
        float dimensionRatio = 0.0f;
        int len = ratio.length();
        int commaIndex2 = ratio.indexOf(44);
        if (commaIndex2 <= 0 || commaIndex2 >= len - 1) {
            commaIndex = 0;
        } else {
            String dimension = ratio.substring(0, commaIndex2);
            if (dimension.equalsIgnoreCase("W")) {
                dimensionRatioSide = 0;
            } else if (dimension.equalsIgnoreCase("H")) {
                dimensionRatioSide = 1;
            }
            commaIndex = commaIndex2 + 1;
        }
        int colonIndex = ratio.indexOf(58);
        if (colonIndex < 0 || colonIndex >= len - 1) {
            String r = ratio.substring(commaIndex);
            if (r.length() > 0) {
                try {
                    dimensionRatio = Float.parseFloat(r);
                } catch (NumberFormatException e) {
                }
            }
        } else {
            String nominator = ratio.substring(commaIndex, colonIndex);
            String denominator = ratio.substring(colonIndex + 1);
            if (nominator.length() > 0 && denominator.length() > 0) {
                try {
                    float nominatorValue = Float.parseFloat(nominator);
                    float denominatorValue = Float.parseFloat(denominator);
                    if (nominatorValue > 0.0f && denominatorValue > 0.0f) {
                        dimensionRatio = dimensionRatioSide == 1 ? Math.abs(denominatorValue / nominatorValue) : Math.abs(nominatorValue / denominatorValue);
                    }
                } catch (NumberFormatException e2) {
                }
            }
        }
        if (dimensionRatio > 0.0f) {
            this.mDimensionRatio = dimensionRatio;
            this.mDimensionRatioSide = dimensionRatioSide;
        }
    }

    public void setDimensionRatio(float ratio, int dimensionRatioSide) {
        this.mDimensionRatio = ratio;
        this.mDimensionRatioSide = dimensionRatioSide;
    }

    public float getDimensionRatio() {
        return this.mDimensionRatio;
    }

    public int getDimensionRatioSide() {
        return this.mDimensionRatioSide;
    }

    public void setHorizontalBiasPercent(float horizontalBiasPercent) {
        this.mHorizontalBiasPercent = horizontalBiasPercent;
    }

    public void setVerticalBiasPercent(float verticalBiasPercent) {
        this.mVerticalBiasPercent = verticalBiasPercent;
    }

    public void setMinWidth(int w) {
        if (w < 0) {
            this.mMinWidth = 0;
        } else {
            this.mMinWidth = w;
        }
    }

    public void setMinHeight(int h) {
        if (h < 0) {
            this.mMinHeight = 0;
        } else {
            this.mMinHeight = h;
        }
    }

    public void setWrapWidth(int w) {
        this.mWrapWidth = w;
    }

    public void setWrapHeight(int h) {
        this.mWrapHeight = h;
    }

    public void setDimension(int w, int h) {
        this.mWidth = w;
        int i = this.mMinWidth;
        if (w < i) {
            this.mWidth = i;
        }
        this.mHeight = h;
        int i2 = this.mMinHeight;
        if (h < i2) {
            this.mHeight = i2;
        }
    }

    public void setFrame(int left, int top, int right, int bottom) {
        int w = right - left;
        int h = bottom - top;
        this.f17mX = left;
        this.f18mY = top;
        if (this.mVisibility == 8) {
            this.mWidth = 0;
            this.mHeight = 0;
            return;
        }
        if (this.mHorizontalDimensionBehaviour == DimensionBehaviour.FIXED && w < this.mWidth) {
            w = this.mWidth;
        }
        if (this.mVerticalDimensionBehaviour == DimensionBehaviour.FIXED && h < this.mHeight) {
            h = this.mHeight;
        }
        this.mWidth = w;
        this.mHeight = h;
        int i = this.mMinHeight;
        if (h < i) {
            this.mHeight = i;
        }
        int i2 = this.mWidth;
        int i3 = this.mMinWidth;
        if (i2 < i3) {
            this.mWidth = i3;
        }
    }

    public void setHorizontalDimension(int left, int right) {
        this.f17mX = left;
        int i = right - left;
        this.mWidth = i;
        int i2 = this.mMinWidth;
        if (i < i2) {
            this.mWidth = i2;
        }
    }

    public void setVerticalDimension(int top, int bottom) {
        this.f18mY = top;
        int i = bottom - top;
        this.mHeight = i;
        int i2 = this.mMinHeight;
        if (i < i2) {
            this.mHeight = i2;
        }
    }

    public void setBaselineDistance(int baseline) {
        this.mBaselineDistance = baseline;
    }

    public void setCompanionWidget(Object companion) {
        this.mCompanionWidget = companion;
    }

    public void setContainerItemSkip(int skip) {
        if (skip >= 0) {
            this.mContainerItemSkip = skip;
        } else {
            this.mContainerItemSkip = 0;
        }
    }

    public int getContainerItemSkip() {
        return this.mContainerItemSkip;
    }

    public void setHorizontalWeight(float horizontalWeight) {
        this.mHorizontalWeight = horizontalWeight;
    }

    public void setVerticalWeight(float verticalWeight) {
        this.mVerticalWeight = verticalWeight;
    }

    public void setHorizontalChainStyle(int horizontalChainStyle) {
        this.mHorizontalChainStyle = horizontalChainStyle;
    }

    public int getHorizontalChainStyle() {
        return this.mHorizontalChainStyle;
    }

    public void setVerticalChainStyle(int verticalChainStyle) {
        this.mVerticalChainStyle = verticalChainStyle;
    }

    public int getVerticalChainStyle() {
        return this.mVerticalChainStyle;
    }

    public void connectedTo(ConstraintWidget source) {
    }

    public void immediateConnect(ConstraintAnchor.Type startType, ConstraintWidget target, ConstraintAnchor.Type endType, int margin, int goneMargin) {
        ConstraintAnchor startAnchor = getAnchor(startType);
        startAnchor.connect(target.getAnchor(endType), margin, goneMargin, ConstraintAnchor.Strength.STRONG, 0, true);
    }

    public void connect(ConstraintAnchor from, ConstraintAnchor to, int margin, int creator) {
        connect(from, to, margin, ConstraintAnchor.Strength.STRONG, creator);
    }

    public void connect(ConstraintAnchor from, ConstraintAnchor to, int margin) {
        connect(from, to, margin, ConstraintAnchor.Strength.STRONG, 0);
    }

    public void connect(ConstraintAnchor from, ConstraintAnchor to, int margin, ConstraintAnchor.Strength strength, int creator) {
        if (from.getOwner() == this) {
            connect(from.getType(), to.getOwner(), to.getType(), margin, strength, creator);
        }
    }

    public void connect(ConstraintAnchor.Type constraintFrom, ConstraintWidget target, ConstraintAnchor.Type constraintTo, int margin) {
        connect(constraintFrom, target, constraintTo, margin, ConstraintAnchor.Strength.STRONG);
    }

    public void connect(ConstraintAnchor.Type constraintFrom, ConstraintWidget target, ConstraintAnchor.Type constraintTo) {
        connect(constraintFrom, target, constraintTo, 0, ConstraintAnchor.Strength.STRONG);
    }

    public void connect(ConstraintAnchor.Type constraintFrom, ConstraintWidget target, ConstraintAnchor.Type constraintTo, int margin, ConstraintAnchor.Strength strength) {
        connect(constraintFrom, target, constraintTo, margin, strength, 0);
    }

    public void connect(ConstraintAnchor.Type constraintFrom, ConstraintWidget target, ConstraintAnchor.Type constraintTo, int margin, ConstraintAnchor.Strength strength, int creator) {
        int margin2;
        ConstraintAnchor.Type type = constraintFrom;
        ConstraintWidget constraintWidget = target;
        ConstraintAnchor.Type type2 = constraintTo;
        int i = creator;
        if (type == ConstraintAnchor.Type.CENTER) {
            if (type2 == ConstraintAnchor.Type.CENTER) {
                ConstraintAnchor left = getAnchor(ConstraintAnchor.Type.LEFT);
                ConstraintAnchor right = getAnchor(ConstraintAnchor.Type.RIGHT);
                ConstraintAnchor top = getAnchor(ConstraintAnchor.Type.TOP);
                ConstraintAnchor bottom = getAnchor(ConstraintAnchor.Type.BOTTOM);
                boolean centerX = false;
                boolean centerY = false;
                if ((left == null || !left.isConnected()) && (right == null || !right.isConnected())) {
                    ConstraintWidget constraintWidget2 = target;
                    ConstraintAnchor.Strength strength2 = strength;
                    int i2 = creator;
                    connect(ConstraintAnchor.Type.LEFT, constraintWidget2, ConstraintAnchor.Type.LEFT, 0, strength2, i2);
                    connect(ConstraintAnchor.Type.RIGHT, constraintWidget2, ConstraintAnchor.Type.RIGHT, 0, strength2, i2);
                    centerX = true;
                }
                if ((top == null || !top.isConnected()) && (bottom == null || !bottom.isConnected())) {
                    ConstraintWidget constraintWidget3 = target;
                    ConstraintAnchor.Strength strength3 = strength;
                    int i3 = creator;
                    connect(ConstraintAnchor.Type.TOP, constraintWidget3, ConstraintAnchor.Type.TOP, 0, strength3, i3);
                    connect(ConstraintAnchor.Type.BOTTOM, constraintWidget3, ConstraintAnchor.Type.BOTTOM, 0, strength3, i3);
                    centerY = true;
                }
                if (centerX && centerY) {
                    getAnchor(ConstraintAnchor.Type.CENTER).connect(constraintWidget.getAnchor(ConstraintAnchor.Type.CENTER), 0, i);
                } else if (centerX) {
                    getAnchor(ConstraintAnchor.Type.CENTER_X).connect(constraintWidget.getAnchor(ConstraintAnchor.Type.CENTER_X), 0, i);
                } else if (centerY) {
                    getAnchor(ConstraintAnchor.Type.CENTER_Y).connect(constraintWidget.getAnchor(ConstraintAnchor.Type.CENTER_Y), 0, i);
                }
                ConstraintAnchor.Strength strength4 = strength;
            } else {
                if (type2 == ConstraintAnchor.Type.LEFT || type2 == ConstraintAnchor.Type.RIGHT) {
                    ConstraintWidget constraintWidget4 = target;
                    ConstraintAnchor.Type type3 = constraintTo;
                    ConstraintAnchor.Strength strength5 = strength;
                    int i4 = creator;
                    connect(ConstraintAnchor.Type.LEFT, constraintWidget4, type3, 0, strength5, i4);
                    connect(ConstraintAnchor.Type.RIGHT, constraintWidget4, type3, 0, strength5, i4);
                    getAnchor(ConstraintAnchor.Type.CENTER).connect(target.getAnchor(constraintTo), 0, i);
                } else if (type2 == ConstraintAnchor.Type.TOP || type2 == ConstraintAnchor.Type.BOTTOM) {
                    ConstraintWidget constraintWidget5 = target;
                    ConstraintAnchor.Type type4 = constraintTo;
                    ConstraintAnchor.Strength strength6 = strength;
                    int i5 = creator;
                    connect(ConstraintAnchor.Type.TOP, constraintWidget5, type4, 0, strength6, i5);
                    connect(ConstraintAnchor.Type.BOTTOM, constraintWidget5, type4, 0, strength6, i5);
                    getAnchor(ConstraintAnchor.Type.CENTER).connect(target.getAnchor(constraintTo), 0, i);
                    ConstraintAnchor.Strength strength7 = strength;
                }
                ConstraintAnchor.Strength strength8 = strength;
            }
        } else if (type == ConstraintAnchor.Type.CENTER_X && (type2 == ConstraintAnchor.Type.LEFT || type2 == ConstraintAnchor.Type.RIGHT)) {
            ConstraintAnchor left2 = getAnchor(ConstraintAnchor.Type.LEFT);
            ConstraintAnchor targetAnchor = target.getAnchor(constraintTo);
            ConstraintAnchor right2 = getAnchor(ConstraintAnchor.Type.RIGHT);
            left2.connect(targetAnchor, 0, i);
            right2.connect(targetAnchor, 0, i);
            getAnchor(ConstraintAnchor.Type.CENTER_X).connect(targetAnchor, 0, i);
            ConstraintAnchor.Strength strength9 = strength;
        } else if (type == ConstraintAnchor.Type.CENTER_Y && (type2 == ConstraintAnchor.Type.TOP || type2 == ConstraintAnchor.Type.BOTTOM)) {
            ConstraintAnchor targetAnchor2 = target.getAnchor(constraintTo);
            getAnchor(ConstraintAnchor.Type.TOP).connect(targetAnchor2, 0, i);
            getAnchor(ConstraintAnchor.Type.BOTTOM).connect(targetAnchor2, 0, i);
            getAnchor(ConstraintAnchor.Type.CENTER_Y).connect(targetAnchor2, 0, i);
            ConstraintAnchor.Strength strength10 = strength;
        } else if (type == ConstraintAnchor.Type.CENTER_X && type2 == ConstraintAnchor.Type.CENTER_X) {
            getAnchor(ConstraintAnchor.Type.LEFT).connect(constraintWidget.getAnchor(ConstraintAnchor.Type.LEFT), 0, i);
            getAnchor(ConstraintAnchor.Type.RIGHT).connect(constraintWidget.getAnchor(ConstraintAnchor.Type.RIGHT), 0, i);
            getAnchor(ConstraintAnchor.Type.CENTER_X).connect(target.getAnchor(constraintTo), 0, i);
            ConstraintAnchor.Strength strength11 = strength;
        } else if (type == ConstraintAnchor.Type.CENTER_Y && type2 == ConstraintAnchor.Type.CENTER_Y) {
            getAnchor(ConstraintAnchor.Type.TOP).connect(constraintWidget.getAnchor(ConstraintAnchor.Type.TOP), 0, i);
            getAnchor(ConstraintAnchor.Type.BOTTOM).connect(constraintWidget.getAnchor(ConstraintAnchor.Type.BOTTOM), 0, i);
            getAnchor(ConstraintAnchor.Type.CENTER_Y).connect(target.getAnchor(constraintTo), 0, i);
            ConstraintAnchor.Strength strength12 = strength;
        } else {
            ConstraintAnchor fromAnchor = getAnchor(constraintFrom);
            ConstraintAnchor toAnchor = target.getAnchor(constraintTo);
            if (fromAnchor.isValidConnection(toAnchor)) {
                if (type == ConstraintAnchor.Type.BASELINE) {
                    ConstraintAnchor top2 = getAnchor(ConstraintAnchor.Type.TOP);
                    ConstraintAnchor bottom2 = getAnchor(ConstraintAnchor.Type.BOTTOM);
                    if (top2 != null) {
                        top2.reset();
                    }
                    if (bottom2 != null) {
                        bottom2.reset();
                    }
                    margin2 = 0;
                } else {
                    if (type == ConstraintAnchor.Type.TOP || type == ConstraintAnchor.Type.BOTTOM) {
                        ConstraintAnchor baseline = getAnchor(ConstraintAnchor.Type.BASELINE);
                        if (baseline != null) {
                            baseline.reset();
                        }
                        ConstraintAnchor center = getAnchor(ConstraintAnchor.Type.CENTER);
                        if (center.getTarget() != toAnchor) {
                            center.reset();
                        }
                        ConstraintAnchor opposite = getAnchor(constraintFrom).getOpposite();
                        ConstraintAnchor centerY2 = getAnchor(ConstraintAnchor.Type.CENTER_Y);
                        if (centerY2.isConnected()) {
                            opposite.reset();
                            centerY2.reset();
                        }
                    } else if (type == ConstraintAnchor.Type.LEFT || type == ConstraintAnchor.Type.RIGHT) {
                        ConstraintAnchor center2 = getAnchor(ConstraintAnchor.Type.CENTER);
                        if (center2.getTarget() != toAnchor) {
                            center2.reset();
                        }
                        ConstraintAnchor opposite2 = getAnchor(constraintFrom).getOpposite();
                        ConstraintAnchor centerX2 = getAnchor(ConstraintAnchor.Type.CENTER_X);
                        if (centerX2.isConnected()) {
                            opposite2.reset();
                            centerX2.reset();
                        }
                    }
                    margin2 = margin;
                }
                fromAnchor.connect(toAnchor, margin2, strength, i);
                toAnchor.getOwner().connectedTo(fromAnchor.getOwner());
                return;
            }
            ConstraintAnchor.Strength strength13 = strength;
        }
        int i6 = margin;
    }

    public void resetAllConstraints() {
        resetAnchors();
        setVerticalBiasPercent(DEFAULT_BIAS);
        setHorizontalBiasPercent(DEFAULT_BIAS);
        if (!(this instanceof ConstraintWidgetContainer)) {
            if (getHorizontalDimensionBehaviour() == DimensionBehaviour.MATCH_CONSTRAINT) {
                if (getWidth() == getWrapWidth()) {
                    setHorizontalDimensionBehaviour(DimensionBehaviour.WRAP_CONTENT);
                } else if (getWidth() > getMinWidth()) {
                    setHorizontalDimensionBehaviour(DimensionBehaviour.FIXED);
                }
            }
            if (getVerticalDimensionBehaviour() != DimensionBehaviour.MATCH_CONSTRAINT) {
                return;
            }
            if (getHeight() == getWrapHeight()) {
                setVerticalDimensionBehaviour(DimensionBehaviour.WRAP_CONTENT);
            } else if (getHeight() > getMinHeight()) {
                setVerticalDimensionBehaviour(DimensionBehaviour.FIXED);
            }
        }
    }

    public void resetAnchor(ConstraintAnchor anchor) {
        if (getParent() == null || !(getParent() instanceof ConstraintWidgetContainer) || !((ConstraintWidgetContainer) getParent()).handlesInternalConstraints()) {
            ConstraintAnchor left = getAnchor(ConstraintAnchor.Type.LEFT);
            ConstraintAnchor right = getAnchor(ConstraintAnchor.Type.RIGHT);
            ConstraintAnchor top = getAnchor(ConstraintAnchor.Type.TOP);
            ConstraintAnchor bottom = getAnchor(ConstraintAnchor.Type.BOTTOM);
            ConstraintAnchor center = getAnchor(ConstraintAnchor.Type.CENTER);
            ConstraintAnchor centerX = getAnchor(ConstraintAnchor.Type.CENTER_X);
            ConstraintAnchor centerY = getAnchor(ConstraintAnchor.Type.CENTER_Y);
            if (anchor == center) {
                if (left.isConnected() && right.isConnected() && left.getTarget() == right.getTarget()) {
                    left.reset();
                    right.reset();
                }
                if (top.isConnected() && bottom.isConnected() && top.getTarget() == bottom.getTarget()) {
                    top.reset();
                    bottom.reset();
                }
                this.mHorizontalBiasPercent = 0.5f;
                this.mVerticalBiasPercent = 0.5f;
            } else if (anchor == centerX) {
                if (left.isConnected() && right.isConnected() && left.getTarget().getOwner() == right.getTarget().getOwner()) {
                    left.reset();
                    right.reset();
                }
                this.mHorizontalBiasPercent = 0.5f;
            } else if (anchor == centerY) {
                if (top.isConnected() && bottom.isConnected() && top.getTarget().getOwner() == bottom.getTarget().getOwner()) {
                    top.reset();
                    bottom.reset();
                }
                this.mVerticalBiasPercent = 0.5f;
            } else if (anchor == left || anchor == right) {
                if (left.isConnected() && left.getTarget() == right.getTarget()) {
                    center.reset();
                }
            } else if ((anchor == top || anchor == bottom) && top.isConnected() && top.getTarget() == bottom.getTarget()) {
                center.reset();
            }
            anchor.reset();
        }
    }

    public void resetAnchors() {
        ConstraintWidget parent = getParent();
        if (parent == null || !(parent instanceof ConstraintWidgetContainer) || !((ConstraintWidgetContainer) getParent()).handlesInternalConstraints()) {
            int mAnchorsSize = this.mAnchors.size();
            for (int i = 0; i < mAnchorsSize; i++) {
                this.mAnchors.get(i).reset();
            }
        }
    }

    public void resetAnchors(int connectionCreator) {
        ConstraintWidget parent = getParent();
        if (parent == null || !(parent instanceof ConstraintWidgetContainer) || !((ConstraintWidgetContainer) getParent()).handlesInternalConstraints()) {
            int mAnchorsSize = this.mAnchors.size();
            for (int i = 0; i < mAnchorsSize; i++) {
                ConstraintAnchor anchor = this.mAnchors.get(i);
                if (connectionCreator == anchor.getConnectionCreator()) {
                    if (anchor.isVerticalAnchor()) {
                        setVerticalBiasPercent(DEFAULT_BIAS);
                    } else {
                        setHorizontalBiasPercent(DEFAULT_BIAS);
                    }
                    anchor.reset();
                }
            }
        }
    }

    public void disconnectWidget(ConstraintWidget widget) {
        ArrayList<ConstraintAnchor> anchors = getAnchors();
        int anchorsSize = anchors.size();
        for (int i = 0; i < anchorsSize; i++) {
            ConstraintAnchor anchor = anchors.get(i);
            if (anchor.isConnected() && anchor.getTarget().getOwner() == widget) {
                anchor.reset();
            }
        }
    }

    public void disconnectUnlockedWidget(ConstraintWidget widget) {
        ArrayList<ConstraintAnchor> anchors = getAnchors();
        int anchorsSize = anchors.size();
        for (int i = 0; i < anchorsSize; i++) {
            ConstraintAnchor anchor = anchors.get(i);
            if (anchor.isConnected() && anchor.getTarget().getOwner() == widget && anchor.getConnectionCreator() == 2) {
                anchor.reset();
            }
        }
    }

    public ConstraintAnchor getAnchor(ConstraintAnchor.Type anchorType) {
        switch (C00851.f19x1d400623[anchorType.ordinal()]) {
            case 1:
                return this.mLeft;
            case 2:
                return this.mTop;
            case 3:
                return this.mRight;
            case 4:
                return this.mBottom;
            case 5:
                return this.mBaseline;
            case 6:
                return this.mCenterX;
            case 7:
                return this.mCenterY;
            case 8:
                return this.mCenter;
            default:
                return null;
        }
    }

    public DimensionBehaviour getHorizontalDimensionBehaviour() {
        return this.mHorizontalDimensionBehaviour;
    }

    public DimensionBehaviour getVerticalDimensionBehaviour() {
        return this.mVerticalDimensionBehaviour;
    }

    public void setHorizontalDimensionBehaviour(DimensionBehaviour behaviour) {
        this.mHorizontalDimensionBehaviour = behaviour;
        if (behaviour == DimensionBehaviour.WRAP_CONTENT) {
            setWidth(this.mWrapWidth);
        }
    }

    public void setVerticalDimensionBehaviour(DimensionBehaviour behaviour) {
        this.mVerticalDimensionBehaviour = behaviour;
        if (behaviour == DimensionBehaviour.WRAP_CONTENT) {
            setHeight(this.mWrapHeight);
        }
    }

    public boolean isInHorizontalChain() {
        if (this.mLeft.mTarget != null && this.mLeft.mTarget.mTarget == this.mLeft) {
            return true;
        }
        if (this.mRight.mTarget == null || this.mRight.mTarget.mTarget != this.mRight) {
            return false;
        }
        return true;
    }

    public ConstraintWidget getHorizontalChainControlWidget() {
        ConstraintWidget found = null;
        if (!isInHorizontalChain()) {
            return null;
        }
        ConstraintWidget tmp = this;
        while (found == null && tmp != null) {
            ConstraintAnchor anchor = tmp.getAnchor(ConstraintAnchor.Type.LEFT);
            ConstraintAnchor targetAnchor = null;
            ConstraintAnchor targetOwner = anchor == null ? null : anchor.getTarget();
            ConstraintWidget target = targetOwner == null ? null : targetOwner.getOwner();
            if (target == getParent()) {
                return tmp;
            }
            if (target != null) {
                targetAnchor = target.getAnchor(ConstraintAnchor.Type.RIGHT).getTarget();
            }
            if (targetAnchor == null || targetAnchor.getOwner() == tmp) {
                tmp = target;
            } else {
                found = tmp;
            }
        }
        return found;
    }

    public boolean isInVerticalChain() {
        if (this.mTop.mTarget != null && this.mTop.mTarget.mTarget == this.mTop) {
            return true;
        }
        if (this.mBottom.mTarget == null || this.mBottom.mTarget.mTarget != this.mBottom) {
            return false;
        }
        return true;
    }

    public ConstraintWidget getVerticalChainControlWidget() {
        ConstraintWidget found = null;
        if (!isInVerticalChain()) {
            return null;
        }
        ConstraintWidget tmp = this;
        while (found == null && tmp != null) {
            ConstraintAnchor anchor = tmp.getAnchor(ConstraintAnchor.Type.TOP);
            ConstraintAnchor targetAnchor = null;
            ConstraintAnchor targetOwner = anchor == null ? null : anchor.getTarget();
            ConstraintWidget target = targetOwner == null ? null : targetOwner.getOwner();
            if (target == getParent()) {
                return tmp;
            }
            if (target != null) {
                targetAnchor = target.getAnchor(ConstraintAnchor.Type.BOTTOM).getTarget();
            }
            if (targetAnchor == null || targetAnchor.getOwner() == tmp) {
                tmp = target;
            } else {
                found = tmp;
            }
        }
        return found;
    }

    public void addToSolver(LinearSystem system) {
        addToSolver(system, Integer.MAX_VALUE);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:209:0x0423, code lost:
        if (r14 == -1) goto L_0x0427;
     */
    /* JADX WARNING: Removed duplicated region for block: B:163:0x02e4 A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:172:0x02f8  */
    /* JADX WARNING: Removed duplicated region for block: B:173:0x02fa  */
    /* JADX WARNING: Removed duplicated region for block: B:176:0x0301  */
    /* JADX WARNING: Removed duplicated region for block: B:194:0x03f6  */
    /* JADX WARNING: Removed duplicated region for block: B:197:0x040b A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:198:0x040c  */
    /* JADX WARNING: Removed duplicated region for block: B:216:0x0435  */
    /* JADX WARNING: Removed duplicated region for block: B:245:0x0559  */
    /* JADX WARNING: Removed duplicated region for block: B:265:0x0641  */
    /* JADX WARNING: Removed duplicated region for block: B:287:0x06e1  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void addToSolver(android.support.constraint.solver.LinearSystem r45, int r46) {
        /*
            r44 = this;
            r15 = r44
            r14 = r45
            r13 = r46
            r0 = 0
            r1 = 0
            r2 = 0
            r3 = 0
            r4 = 0
            r12 = 2147483647(0x7fffffff, float:NaN)
            if (r13 == r12) goto L_0x0016
            android.support.constraint.solver.widgets.ConstraintAnchor r5 = r15.mLeft
            int r5 = r5.mGroup
            if (r5 != r13) goto L_0x001c
        L_0x0016:
            android.support.constraint.solver.widgets.ConstraintAnchor r5 = r15.mLeft
            android.support.constraint.solver.SolverVariable r0 = r14.createObjectVariable(r5)
        L_0x001c:
            if (r13 == r12) goto L_0x0024
            android.support.constraint.solver.widgets.ConstraintAnchor r5 = r15.mRight
            int r5 = r5.mGroup
            if (r5 != r13) goto L_0x002a
        L_0x0024:
            android.support.constraint.solver.widgets.ConstraintAnchor r5 = r15.mRight
            android.support.constraint.solver.SolverVariable r1 = r14.createObjectVariable(r5)
        L_0x002a:
            if (r13 == r12) goto L_0x0035
            android.support.constraint.solver.widgets.ConstraintAnchor r5 = r15.mTop
            int r5 = r5.mGroup
            if (r5 != r13) goto L_0x0033
            goto L_0x0035
        L_0x0033:
            r11 = r2
            goto L_0x003c
        L_0x0035:
            android.support.constraint.solver.widgets.ConstraintAnchor r5 = r15.mTop
            android.support.constraint.solver.SolverVariable r2 = r14.createObjectVariable(r5)
            r11 = r2
        L_0x003c:
            if (r13 == r12) goto L_0x0047
            android.support.constraint.solver.widgets.ConstraintAnchor r2 = r15.mBottom
            int r2 = r2.mGroup
            if (r2 != r13) goto L_0x0045
            goto L_0x0047
        L_0x0045:
            r10 = r3
            goto L_0x004e
        L_0x0047:
            android.support.constraint.solver.widgets.ConstraintAnchor r2 = r15.mBottom
            android.support.constraint.solver.SolverVariable r3 = r14.createObjectVariable(r2)
            r10 = r3
        L_0x004e:
            if (r13 == r12) goto L_0x0059
            android.support.constraint.solver.widgets.ConstraintAnchor r2 = r15.mBaseline
            int r2 = r2.mGroup
            if (r2 != r13) goto L_0x0057
            goto L_0x0059
        L_0x0057:
            r9 = r4
            goto L_0x0060
        L_0x0059:
            android.support.constraint.solver.widgets.ConstraintAnchor r2 = r15.mBaseline
            android.support.constraint.solver.SolverVariable r4 = r14.createObjectVariable(r2)
            r9 = r4
        L_0x0060:
            r2 = 0
            r3 = 0
            android.support.constraint.solver.widgets.ConstraintWidget r4 = r15.mParent
            r8 = 1
            r7 = 0
            if (r4 == 0) goto L_0x01d3
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r15.mLeft
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.mTarget
            if (r4 == 0) goto L_0x0078
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r15.mLeft
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.mTarget
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.mTarget
            android.support.constraint.solver.widgets.ConstraintAnchor r5 = r15.mLeft
            if (r4 == r5) goto L_0x0088
        L_0x0078:
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r15.mRight
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.mTarget
            if (r4 == 0) goto L_0x0090
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r15.mRight
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.mTarget
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.mTarget
            android.support.constraint.solver.widgets.ConstraintAnchor r5 = r15.mRight
            if (r4 != r5) goto L_0x0090
        L_0x0088:
            android.support.constraint.solver.widgets.ConstraintWidget r4 = r15.mParent
            android.support.constraint.solver.widgets.ConstraintWidgetContainer r4 = (android.support.constraint.solver.widgets.ConstraintWidgetContainer) r4
            r4.addChain(r15, r7)
            r2 = 1
        L_0x0090:
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r15.mTop
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.mTarget
            if (r4 == 0) goto L_0x00a0
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r15.mTop
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.mTarget
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.mTarget
            android.support.constraint.solver.widgets.ConstraintAnchor r5 = r15.mTop
            if (r4 == r5) goto L_0x00b0
        L_0x00a0:
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r15.mBottom
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.mTarget
            if (r4 == 0) goto L_0x00b8
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r15.mBottom
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.mTarget
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.mTarget
            android.support.constraint.solver.widgets.ConstraintAnchor r5 = r15.mBottom
            if (r4 != r5) goto L_0x00b8
        L_0x00b0:
            android.support.constraint.solver.widgets.ConstraintWidget r4 = r15.mParent
            android.support.constraint.solver.widgets.ConstraintWidgetContainer r4 = (android.support.constraint.solver.widgets.ConstraintWidgetContainer) r4
            r4.addChain(r15, r8)
            r3 = 1
        L_0x00b8:
            android.support.constraint.solver.widgets.ConstraintWidget r4 = r15.mParent
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r4 = r4.getHorizontalDimensionBehaviour()
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r5 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            if (r4 != r5) goto L_0x0143
            if (r2 != 0) goto L_0x0143
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r15.mLeft
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.mTarget
            if (r4 == 0) goto L_0x00ed
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r15.mLeft
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.mTarget
            android.support.constraint.solver.widgets.ConstraintWidget r4 = r4.mOwner
            android.support.constraint.solver.widgets.ConstraintWidget r5 = r15.mParent
            if (r4 == r5) goto L_0x00d5
            goto L_0x00ed
        L_0x00d5:
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r15.mLeft
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.mTarget
            if (r4 == 0) goto L_0x0103
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r15.mLeft
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.mTarget
            android.support.constraint.solver.widgets.ConstraintWidget r4 = r4.mOwner
            android.support.constraint.solver.widgets.ConstraintWidget r5 = r15.mParent
            if (r4 != r5) goto L_0x0103
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r15.mLeft
            android.support.constraint.solver.widgets.ConstraintAnchor$ConnectionType r5 = android.support.constraint.solver.widgets.ConstraintAnchor.ConnectionType.STRICT
            r4.setConnectionType(r5)
            goto L_0x0104
        L_0x00ed:
            android.support.constraint.solver.widgets.ConstraintWidget r4 = r15.mParent
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.mLeft
            android.support.constraint.solver.SolverVariable r4 = r14.createObjectVariable(r4)
            android.support.constraint.solver.ArrayRow r5 = r45.createRow()
            android.support.constraint.solver.SolverVariable r6 = r45.createSlackVariable()
            r5.createRowGreaterThan(r0, r4, r6, r7)
            r14.addConstraint(r5)
        L_0x0103:
        L_0x0104:
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r15.mRight
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.mTarget
            if (r4 == 0) goto L_0x012d
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r15.mRight
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.mTarget
            android.support.constraint.solver.widgets.ConstraintWidget r4 = r4.mOwner
            android.support.constraint.solver.widgets.ConstraintWidget r5 = r15.mParent
            if (r4 == r5) goto L_0x0115
            goto L_0x012d
        L_0x0115:
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r15.mRight
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.mTarget
            if (r4 == 0) goto L_0x0143
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r15.mRight
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.mTarget
            android.support.constraint.solver.widgets.ConstraintWidget r4 = r4.mOwner
            android.support.constraint.solver.widgets.ConstraintWidget r5 = r15.mParent
            if (r4 != r5) goto L_0x0143
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r15.mRight
            android.support.constraint.solver.widgets.ConstraintAnchor$ConnectionType r5 = android.support.constraint.solver.widgets.ConstraintAnchor.ConnectionType.STRICT
            r4.setConnectionType(r5)
            goto L_0x0143
        L_0x012d:
            android.support.constraint.solver.widgets.ConstraintWidget r4 = r15.mParent
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.mRight
            android.support.constraint.solver.SolverVariable r4 = r14.createObjectVariable(r4)
            android.support.constraint.solver.ArrayRow r5 = r45.createRow()
            android.support.constraint.solver.SolverVariable r6 = r45.createSlackVariable()
            r5.createRowGreaterThan(r4, r1, r6, r7)
            r14.addConstraint(r5)
        L_0x0143:
            android.support.constraint.solver.widgets.ConstraintWidget r4 = r15.mParent
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r4 = r4.getVerticalDimensionBehaviour()
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r5 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            if (r4 != r5) goto L_0x01ce
            if (r3 != 0) goto L_0x01ce
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r15.mTop
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.mTarget
            if (r4 == 0) goto L_0x0178
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r15.mTop
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.mTarget
            android.support.constraint.solver.widgets.ConstraintWidget r4 = r4.mOwner
            android.support.constraint.solver.widgets.ConstraintWidget r5 = r15.mParent
            if (r4 == r5) goto L_0x0160
            goto L_0x0178
        L_0x0160:
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r15.mTop
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.mTarget
            if (r4 == 0) goto L_0x018e
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r15.mTop
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.mTarget
            android.support.constraint.solver.widgets.ConstraintWidget r4 = r4.mOwner
            android.support.constraint.solver.widgets.ConstraintWidget r5 = r15.mParent
            if (r4 != r5) goto L_0x018e
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r15.mTop
            android.support.constraint.solver.widgets.ConstraintAnchor$ConnectionType r5 = android.support.constraint.solver.widgets.ConstraintAnchor.ConnectionType.STRICT
            r4.setConnectionType(r5)
            goto L_0x018f
        L_0x0178:
            android.support.constraint.solver.widgets.ConstraintWidget r4 = r15.mParent
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.mTop
            android.support.constraint.solver.SolverVariable r4 = r14.createObjectVariable(r4)
            android.support.constraint.solver.ArrayRow r5 = r45.createRow()
            android.support.constraint.solver.SolverVariable r6 = r45.createSlackVariable()
            r5.createRowGreaterThan(r11, r4, r6, r7)
            r14.addConstraint(r5)
        L_0x018e:
        L_0x018f:
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r15.mBottom
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.mTarget
            if (r4 == 0) goto L_0x01b8
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r15.mBottom
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.mTarget
            android.support.constraint.solver.widgets.ConstraintWidget r4 = r4.mOwner
            android.support.constraint.solver.widgets.ConstraintWidget r5 = r15.mParent
            if (r4 == r5) goto L_0x01a0
            goto L_0x01b8
        L_0x01a0:
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r15.mBottom
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.mTarget
            if (r4 == 0) goto L_0x01ce
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r15.mBottom
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.mTarget
            android.support.constraint.solver.widgets.ConstraintWidget r4 = r4.mOwner
            android.support.constraint.solver.widgets.ConstraintWidget r5 = r15.mParent
            if (r4 != r5) goto L_0x01ce
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r15.mBottom
            android.support.constraint.solver.widgets.ConstraintAnchor$ConnectionType r5 = android.support.constraint.solver.widgets.ConstraintAnchor.ConnectionType.STRICT
            r4.setConnectionType(r5)
            goto L_0x01ce
        L_0x01b8:
            android.support.constraint.solver.widgets.ConstraintWidget r4 = r15.mParent
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.mBottom
            android.support.constraint.solver.SolverVariable r4 = r14.createObjectVariable(r4)
            android.support.constraint.solver.ArrayRow r5 = r45.createRow()
            android.support.constraint.solver.SolverVariable r6 = r45.createSlackVariable()
            r5.createRowGreaterThan(r4, r10, r6, r7)
            r14.addConstraint(r5)
        L_0x01ce:
            r19 = r2
            r20 = r3
            goto L_0x01d7
        L_0x01d3:
            r19 = r2
            r20 = r3
        L_0x01d7:
            int r2 = r15.mWidth
            int r3 = r15.mMinWidth
            if (r2 >= r3) goto L_0x01df
            int r2 = r15.mMinWidth
        L_0x01df:
            int r3 = r15.mHeight
            int r4 = r15.mMinHeight
            if (r3 >= r4) goto L_0x01e7
            int r3 = r15.mMinHeight
        L_0x01e7:
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r4 = r15.mHorizontalDimensionBehaviour
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r5 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r4 == r5) goto L_0x01ef
            r4 = r8
            goto L_0x01f0
        L_0x01ef:
            r4 = r7
        L_0x01f0:
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r5 = r15.mVerticalDimensionBehaviour
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r6 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r5 == r6) goto L_0x01f8
            r5 = r8
            goto L_0x01f9
        L_0x01f8:
            r5 = r7
        L_0x01f9:
            if (r4 != 0) goto L_0x020e
            android.support.constraint.solver.widgets.ConstraintAnchor r6 = r15.mLeft
            if (r6 == 0) goto L_0x020e
            android.support.constraint.solver.widgets.ConstraintAnchor r7 = r15.mRight
            if (r7 == 0) goto L_0x020e
            android.support.constraint.solver.widgets.ConstraintAnchor r6 = r6.mTarget
            if (r6 == 0) goto L_0x020d
            android.support.constraint.solver.widgets.ConstraintAnchor r6 = r15.mRight
            android.support.constraint.solver.widgets.ConstraintAnchor r6 = r6.mTarget
            if (r6 != 0) goto L_0x020e
        L_0x020d:
            r4 = 1
        L_0x020e:
            if (r5 != 0) goto L_0x0237
            android.support.constraint.solver.widgets.ConstraintAnchor r6 = r15.mTop
            if (r6 == 0) goto L_0x0237
            android.support.constraint.solver.widgets.ConstraintAnchor r7 = r15.mBottom
            if (r7 == 0) goto L_0x0237
            android.support.constraint.solver.widgets.ConstraintAnchor r6 = r6.mTarget
            if (r6 == 0) goto L_0x0222
            android.support.constraint.solver.widgets.ConstraintAnchor r6 = r15.mBottom
            android.support.constraint.solver.widgets.ConstraintAnchor r6 = r6.mTarget
            if (r6 != 0) goto L_0x0237
        L_0x0222:
            int r6 = r15.mBaselineDistance
            if (r6 == 0) goto L_0x0236
            android.support.constraint.solver.widgets.ConstraintAnchor r6 = r15.mBaseline
            if (r6 == 0) goto L_0x0237
            android.support.constraint.solver.widgets.ConstraintAnchor r6 = r15.mTop
            android.support.constraint.solver.widgets.ConstraintAnchor r6 = r6.mTarget
            if (r6 == 0) goto L_0x0236
            android.support.constraint.solver.widgets.ConstraintAnchor r6 = r15.mBaseline
            android.support.constraint.solver.widgets.ConstraintAnchor r6 = r6.mTarget
            if (r6 != 0) goto L_0x0237
        L_0x0236:
            r5 = 1
        L_0x0237:
            r6 = 0
            int r7 = r15.mDimensionRatioSide
            float r8 = r15.mDimensionRatio
            float r12 = r15.mDimensionRatio
            r21 = 0
            int r12 = (r12 > r21 ? 1 : (r12 == r21 ? 0 : -1))
            r21 = r11
            if (r12 <= 0) goto L_0x02d5
            int r12 = r15.mVisibility
            r11 = 8
            if (r12 == r11) goto L_0x02d5
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r11 = r15.mHorizontalDimensionBehaviour
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r12 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            r23 = 1065353216(0x3f800000, float:1.0)
            if (r11 != r12) goto L_0x0298
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r11 = r15.mVerticalDimensionBehaviour
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r12 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r11 != r12) goto L_0x0298
            r6 = 1
            if (r4 == 0) goto L_0x026f
            if (r5 != 0) goto L_0x026f
            r7 = 0
            r23 = r2
            r24 = r3
            r25 = r4
            r26 = r5
            r27 = r6
            r12 = r7
            r28 = r8
            goto L_0x02e2
        L_0x026f:
            if (r4 != 0) goto L_0x02d5
            if (r5 == 0) goto L_0x02d5
            r7 = 1
            int r11 = r15.mDimensionRatioSide
            r12 = -1
            if (r11 != r12) goto L_0x028a
            float r8 = r23 / r8
            r23 = r2
            r24 = r3
            r25 = r4
            r26 = r5
            r27 = r6
            r12 = r7
            r28 = r8
            goto L_0x02e2
        L_0x028a:
            r23 = r2
            r24 = r3
            r25 = r4
            r26 = r5
            r27 = r6
            r12 = r7
            r28 = r8
            goto L_0x02e2
        L_0x0298:
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r11 = r15.mHorizontalDimensionBehaviour
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r12 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r11 != r12) goto L_0x02b3
            r7 = 0
            int r11 = r15.mHeight
            float r11 = (float) r11
            float r11 = r11 * r8
            int r2 = (int) r11
            r4 = 1
            r23 = r2
            r24 = r3
            r25 = r4
            r26 = r5
            r27 = r6
            r12 = r7
            r28 = r8
            goto L_0x02e2
        L_0x02b3:
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r11 = r15.mVerticalDimensionBehaviour
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r12 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r11 != r12) goto L_0x02d5
            r7 = 1
            int r11 = r15.mDimensionRatioSide
            r12 = -1
            if (r11 != r12) goto L_0x02c1
            float r8 = r23 / r8
        L_0x02c1:
            int r11 = r15.mWidth
            float r11 = (float) r11
            float r11 = r11 * r8
            int r3 = (int) r11
            r5 = 1
            r23 = r2
            r24 = r3
            r25 = r4
            r26 = r5
            r27 = r6
            r12 = r7
            r28 = r8
            goto L_0x02e2
        L_0x02d5:
            r23 = r2
            r24 = r3
            r25 = r4
            r26 = r5
            r27 = r6
            r12 = r7
            r28 = r8
        L_0x02e2:
            if (r27 == 0) goto L_0x02eb
            if (r12 == 0) goto L_0x02e9
            r2 = -1
            if (r12 != r2) goto L_0x02eb
        L_0x02e9:
            r2 = 1
            goto L_0x02ec
        L_0x02eb:
            r2 = 0
        L_0x02ec:
            r29 = r2
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r2 = r15.mHorizontalDimensionBehaviour
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r3 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            if (r2 != r3) goto L_0x02fa
            boolean r2 = r15 instanceof android.support.constraint.solver.widgets.ConstraintWidgetContainer
            if (r2 == 0) goto L_0x02fa
            r2 = 1
            goto L_0x02fb
        L_0x02fa:
            r2 = 0
        L_0x02fb:
            int r3 = r15.mHorizontalResolution
            r11 = 2
            r8 = 3
            if (r3 == r11) goto L_0x03f6
            r7 = 2147483647(0x7fffffff, float:NaN)
            if (r13 == r7) goto L_0x0323
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r15.mLeft
            int r3 = r3.mGroup
            if (r3 != r13) goto L_0x0313
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r15.mRight
            int r3 = r3.mGroup
            if (r3 != r13) goto L_0x0313
            goto L_0x0323
        L_0x0313:
            r22 = r1
            r37 = r9
            r38 = r10
            r40 = r12
            r39 = r21
            r18 = 0
            r21 = r0
            goto L_0x0404
        L_0x0323:
            if (r29 == 0) goto L_0x03b8
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r15.mLeft
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r3.mTarget
            if (r3 == 0) goto L_0x03b8
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r15.mRight
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r3.mTarget
            if (r3 == 0) goto L_0x03b8
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r15.mLeft
            android.support.constraint.solver.SolverVariable r6 = r14.createObjectVariable(r3)
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r15.mRight
            android.support.constraint.solver.SolverVariable r5 = r14.createObjectVariable(r3)
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r15.mLeft
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r3.getTarget()
            android.support.constraint.solver.SolverVariable r4 = r14.createObjectVariable(r3)
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r15.mRight
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r3.getTarget()
            android.support.constraint.solver.SolverVariable r3 = r14.createObjectVariable(r3)
            android.support.constraint.solver.widgets.ConstraintAnchor r7 = r15.mLeft
            int r7 = r7.getMargin()
            r14.addGreaterThan(r6, r4, r7, r8)
            android.support.constraint.solver.widgets.ConstraintAnchor r7 = r15.mRight
            int r7 = r7.getMargin()
            r22 = -1
            int r7 = r7 * -1
            r14.addLowerThan(r5, r3, r7, r8)
            if (r19 != 0) goto L_0x039e
            android.support.constraint.solver.widgets.ConstraintAnchor r7 = r15.mLeft
            int r7 = r7.getMargin()
            float r8 = r15.mHorizontalBiasPercent
            android.support.constraint.solver.widgets.ConstraintAnchor r11 = r15.mRight
            int r11 = r11.getMargin()
            r32 = 4
            r33 = r3
            r3 = r45
            r34 = r4
            r4 = r6
            r35 = r5
            r5 = r34
            r36 = r6
            r6 = r7
            r16 = 2147483647(0x7fffffff, float:NaN)
            r18 = 0
            r7 = r8
            r8 = r33
            r37 = r9
            r9 = r35
            r38 = r10
            r10 = r11
            r39 = r21
            r11 = r32
            r3.addCentering(r4, r5, r6, r7, r8, r9, r10, r11)
            goto L_0x03b1
        L_0x039e:
            r33 = r3
            r34 = r4
            r35 = r5
            r36 = r6
            r37 = r9
            r38 = r10
            r39 = r21
            r16 = 2147483647(0x7fffffff, float:NaN)
            r18 = 0
        L_0x03b1:
            r21 = r0
            r22 = r1
            r40 = r12
            goto L_0x0404
        L_0x03b8:
            r16 = r7
            r37 = r9
            r38 = r10
            r39 = r21
            r18 = 0
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r15.mLeft
            android.support.constraint.solver.widgets.ConstraintAnchor r5 = r15.mRight
            int r6 = r15.f17mX
            int r7 = r6 + r23
            int r9 = r15.mMinWidth
            float r10 = r15.mHorizontalBiasPercent
            int r11 = r15.mMatchConstraintDefaultWidth
            int r8 = r15.mMatchConstraintMinWidth
            int r3 = r15.mMatchConstraintMaxWidth
            r21 = r0
            r0 = r44
            r22 = r1
            r1 = r45
            r17 = r3
            r3 = r25
            r30 = r8
            r8 = r23
            r31 = r11
            r11 = r29
            r40 = r12
            r12 = r19
            r13 = r31
            r14 = r30
            r15 = r17
            r0.applyConstraints(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15)
            goto L_0x0404
        L_0x03f6:
            r22 = r1
            r37 = r9
            r38 = r10
            r40 = r12
            r39 = r21
            r18 = 0
            r21 = r0
        L_0x0404:
            r15 = r44
            int r0 = r15.mVerticalResolution
            r1 = 2
            if (r0 != r1) goto L_0x040c
            return
        L_0x040c:
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r0 = r15.mVerticalDimensionBehaviour
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r1 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            if (r0 != r1) goto L_0x0418
            boolean r0 = r15 instanceof android.support.constraint.solver.widgets.ConstraintWidgetContainer
            if (r0 == 0) goto L_0x0418
            r0 = 1
            goto L_0x041a
        L_0x0418:
            r0 = r18
        L_0x041a:
            r2 = r0
            if (r27 == 0) goto L_0x0429
            r14 = r40
            r13 = 1
            if (r14 == r13) goto L_0x0426
            r0 = -1
            if (r14 != r0) goto L_0x042d
            goto L_0x0427
        L_0x0426:
            r0 = -1
        L_0x0427:
            r8 = r13
            goto L_0x042f
        L_0x0429:
            r14 = r40
            r0 = -1
            r13 = 1
        L_0x042d:
            r8 = r18
        L_0x042f:
            r30 = r8
            int r1 = r15.mBaselineDistance
            if (r1 <= 0) goto L_0x0559
            android.support.constraint.solver.widgets.ConstraintAnchor r1 = r15.mBottom
            r12 = 5
            r11 = r46
            r10 = 2147483647(0x7fffffff, float:NaN)
            if (r11 == r10) goto L_0x0453
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r15.mBottom
            int r3 = r3.mGroup
            if (r3 != r11) goto L_0x044c
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r15.mBaseline
            int r3 = r3.mGroup
            if (r3 != r11) goto L_0x044c
            goto L_0x0453
        L_0x044c:
            r9 = r45
            r7 = r37
            r8 = r39
            goto L_0x0460
        L_0x0453:
            int r3 = r44.getBaselineDistance()
            r9 = r45
            r7 = r37
            r8 = r39
            r9.addEquality(r7, r8, r3, r12)
        L_0x0460:
            r6 = r24
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r15.mBaseline
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r3.mTarget
            if (r3 == 0) goto L_0x046e
            int r3 = r15.mBaselineDistance
            android.support.constraint.solver.widgets.ConstraintAnchor r1 = r15.mBaseline
            r24 = r3
        L_0x046e:
            if (r11 == r10) goto L_0x0485
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r15.mTop
            int r3 = r3.mGroup
            if (r3 != r11) goto L_0x047b
            int r3 = r1.mGroup
            if (r3 != r11) goto L_0x047b
            goto L_0x0485
        L_0x047b:
            r32 = r7
            r15 = r8
            r1 = r9
            r43 = r14
            r14 = r38
            goto L_0x054d
        L_0x0485:
            if (r30 == 0) goto L_0x050a
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r15.mTop
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r3.mTarget
            if (r3 == 0) goto L_0x050a
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r15.mBottom
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r3.mTarget
            if (r3 == 0) goto L_0x050a
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r15.mTop
            android.support.constraint.solver.SolverVariable r12 = r9.createObjectVariable(r3)
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r15.mBottom
            android.support.constraint.solver.SolverVariable r5 = r9.createObjectVariable(r3)
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r15.mTop
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r3.getTarget()
            android.support.constraint.solver.SolverVariable r4 = r9.createObjectVariable(r3)
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r15.mBottom
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r3.getTarget()
            android.support.constraint.solver.SolverVariable r3 = r9.createObjectVariable(r3)
            android.support.constraint.solver.widgets.ConstraintAnchor r10 = r15.mTop
            int r10 = r10.getMargin()
            r11 = 3
            r9.addGreaterThan(r12, r4, r10, r11)
            android.support.constraint.solver.widgets.ConstraintAnchor r10 = r15.mBottom
            int r10 = r10.getMargin()
            int r10 = r10 * r0
            r9.addLowerThan(r5, r3, r10, r11)
            if (r20 != 0) goto L_0x04f7
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r15.mTop
            int r0 = r0.getMargin()
            float r10 = r15.mVerticalBiasPercent
            android.support.constraint.solver.widgets.ConstraintAnchor r11 = r15.mBottom
            int r11 = r11.getMargin()
            r16 = 4
            r17 = r3
            r3 = r45
            r18 = r4
            r4 = r12
            r31 = r5
            r5 = r18
            r41 = r6
            r6 = r0
            r32 = r7
            r7 = r10
            r0 = r8
            r8 = r17
            r10 = r9
            r9 = r31
            r10 = r11
            r11 = r16
            r3.addCentering(r4, r5, r6, r7, r8, r9, r10, r11)
            goto L_0x0502
        L_0x04f7:
            r17 = r3
            r18 = r4
            r31 = r5
            r41 = r6
            r32 = r7
            r0 = r8
        L_0x0502:
            r1 = r45
            r15 = r0
            r43 = r14
            r14 = r38
            goto L_0x054d
        L_0x050a:
            r41 = r6
            r32 = r7
            r0 = r8
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r15.mTop
            int r6 = r15.f18mY
            int r7 = r6 + r24
            int r9 = r15.mMinHeight
            float r10 = r15.mVerticalBiasPercent
            int r11 = r15.mMatchConstraintDefaultHeight
            int r8 = r15.mMatchConstraintMinHeight
            int r5 = r15.mMatchConstraintMaxHeight
            r3 = r0
            r0 = r44
            r16 = r1
            r1 = r45
            r42 = r3
            r3 = r26
            r17 = r5
            r5 = r16
            r18 = r8
            r8 = r24
            r31 = r11
            r11 = r30
            r12 = r20
            r13 = r31
            r43 = r14
            r14 = r18
            r15 = r17
            r0.applyConstraints(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15)
            r14 = r38
            r0 = r41
            r15 = r42
            r3 = 5
            r1.addEquality(r14, r15, r0, r3)
        L_0x054d:
            r0 = r44
            r34 = r2
            r38 = r14
            r39 = r15
            r2 = r46
            goto L_0x063f
        L_0x0559:
            r1 = r45
            r43 = r14
            r32 = r37
            r14 = r38
            r15 = r39
            r13 = r46
            r12 = 2147483647(0x7fffffff, float:NaN)
            if (r13 == r12) goto L_0x0583
            r11 = r44
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r11.mTop
            int r3 = r3.mGroup
            if (r3 != r13) goto L_0x0579
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r11.mBottom
            int r3 = r3.mGroup
            if (r3 != r13) goto L_0x0579
            goto L_0x0585
        L_0x0579:
            r34 = r2
            r0 = r11
            r2 = r13
            r38 = r14
            r39 = r15
            goto L_0x063f
        L_0x0583:
            r11 = r44
        L_0x0585:
            if (r30 == 0) goto L_0x0606
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r11.mTop
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r3.mTarget
            if (r3 == 0) goto L_0x0606
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r11.mBottom
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r3.mTarget
            if (r3 == 0) goto L_0x0606
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r11.mTop
            android.support.constraint.solver.SolverVariable r10 = r1.createObjectVariable(r3)
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r11.mBottom
            android.support.constraint.solver.SolverVariable r9 = r1.createObjectVariable(r3)
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r11.mTop
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r3.getTarget()
            android.support.constraint.solver.SolverVariable r8 = r1.createObjectVariable(r3)
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r11.mBottom
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r3.getTarget()
            android.support.constraint.solver.SolverVariable r7 = r1.createObjectVariable(r3)
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r11.mTop
            int r3 = r3.getMargin()
            r6 = 3
            r1.addGreaterThan(r10, r8, r3, r6)
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r11.mBottom
            int r3 = r3.getMargin()
            int r3 = r3 * r0
            r1.addLowerThan(r9, r7, r3, r6)
            if (r20 != 0) goto L_0x05f5
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r11.mTop
            int r0 = r0.getMargin()
            float r5 = r11.mVerticalBiasPercent
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r11.mBottom
            int r16 = r3.getMargin()
            r17 = 4
            r3 = r45
            r4 = r10
            r18 = r5
            r5 = r8
            r6 = r0
            r0 = r7
            r7 = r18
            r18 = r8
            r8 = r0
            r31 = r9
            r33 = r10
            r10 = r16
            r16 = r0
            r0 = r11
            r11 = r17
            r3.addCentering(r4, r5, r6, r7, r8, r9, r10, r11)
            goto L_0x05fe
        L_0x05f5:
            r16 = r7
            r18 = r8
            r31 = r9
            r33 = r10
            r0 = r11
        L_0x05fe:
            r34 = r2
            r2 = r13
            r38 = r14
            r39 = r15
            goto L_0x063f
        L_0x0606:
            r0 = r11
            android.support.constraint.solver.widgets.ConstraintAnchor r7 = r0.mTop
            android.support.constraint.solver.widgets.ConstraintAnchor r8 = r0.mBottom
            int r9 = r0.f18mY
            int r10 = r9 + r24
            int r11 = r0.mMinHeight
            float r6 = r0.mVerticalBiasPercent
            int r5 = r0.mMatchConstraintDefaultHeight
            int r4 = r0.mMatchConstraintMinHeight
            int r3 = r0.mMatchConstraintMaxHeight
            r18 = r3
            r3 = r44
            r17 = r4
            r4 = r45
            r16 = r5
            r5 = r2
            r31 = r6
            r6 = r26
            r33 = r11
            r11 = r24
            r34 = r2
            r2 = r12
            r12 = r33
            r2 = r13
            r13 = r31
            r38 = r14
            r14 = r30
            r39 = r15
            r15 = r20
            r3.applyConstraints(r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18)
        L_0x063f:
            if (r27 == 0) goto L_0x06e1
            android.support.constraint.solver.ArrayRow r3 = r45.createRow()
            r4 = 2147483647(0x7fffffff, float:NaN)
            if (r2 == r4) goto L_0x0663
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r0.mLeft
            int r4 = r4.mGroup
            if (r4 != r2) goto L_0x0657
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r0.mRight
            int r4 = r4.mGroup
            if (r4 != r2) goto L_0x0657
            goto L_0x0663
        L_0x0657:
            r11 = r21
            r12 = r22
            r14 = r38
            r13 = r39
            r4 = r43
            goto L_0x06eb
        L_0x0663:
            r4 = r43
            if (r4 != 0) goto L_0x0683
            r5 = r3
            r6 = r22
            r7 = r21
            r8 = r38
            r9 = r39
            r10 = r28
            android.support.constraint.solver.ArrayRow r5 = r5.createRowDimensionRatio(r6, r7, r8, r9, r10)
            r1.addConstraint(r5)
            r11 = r21
            r12 = r22
            r14 = r38
            r13 = r39
            goto L_0x06eb
        L_0x0683:
            r5 = 1
            if (r4 != r5) goto L_0x06a1
            r5 = r3
            r6 = r38
            r7 = r39
            r8 = r22
            r9 = r21
            r10 = r28
            android.support.constraint.solver.ArrayRow r5 = r5.createRowDimensionRatio(r6, r7, r8, r9, r10)
            r1.addConstraint(r5)
            r11 = r21
            r12 = r22
            r14 = r38
            r13 = r39
            goto L_0x06eb
        L_0x06a1:
            int r5 = r0.mMatchConstraintMinWidth
            if (r5 <= 0) goto L_0x06ae
            r11 = r21
            r12 = r22
            r6 = 3
            r1.addGreaterThan(r12, r11, r5, r6)
            goto L_0x06b3
        L_0x06ae:
            r11 = r21
            r12 = r22
            r6 = 3
        L_0x06b3:
            int r5 = r0.mMatchConstraintMinHeight
            if (r5 <= 0) goto L_0x06bf
            r14 = r38
            r13 = r39
            r1.addGreaterThan(r14, r13, r5, r6)
            goto L_0x06c3
        L_0x06bf:
            r14 = r38
            r13 = r39
        L_0x06c3:
            r15 = 4
            r5 = r3
            r6 = r12
            r7 = r11
            r8 = r14
            r9 = r13
            r10 = r28
            r5.createRowDimensionRatio(r6, r7, r8, r9, r10)
            android.support.constraint.solver.SolverVariable r5 = r45.createErrorVariable()
            android.support.constraint.solver.SolverVariable r6 = r45.createErrorVariable()
            r5.strength = r15
            r6.strength = r15
            r3.addError(r5, r6)
            r1.addConstraint(r3)
            goto L_0x06eb
        L_0x06e1:
            r11 = r21
            r12 = r22
            r14 = r38
            r13 = r39
            r4 = r43
        L_0x06eb:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.constraint.solver.widgets.ConstraintWidget.addToSolver(android.support.constraint.solver.LinearSystem, int):void");
    }

    private void applyConstraints(LinearSystem system, boolean wrapContent, boolean dimensionFixed, ConstraintAnchor beginAnchor, ConstraintAnchor endAnchor, int beginPosition, int endPosition, int dimension, int minDimension, float bias, boolean useRatio, boolean inChain, int matchConstraintDefault, int matchMinDimension, int matchMaxDimension) {
        boolean dimensionFixed2;
        int dimension2;
        int dimension3;
        int dimension4;
        int dimension5;
        LinearSystem linearSystem = system;
        int i = beginPosition;
        int i2 = endPosition;
        int i3 = minDimension;
        int i4 = matchMinDimension;
        int i5 = matchMaxDimension;
        SolverVariable begin = linearSystem.createObjectVariable(beginAnchor);
        SolverVariable end = linearSystem.createObjectVariable(endAnchor);
        SolverVariable beginTarget = linearSystem.createObjectVariable(beginAnchor.getTarget());
        SolverVariable endTarget = linearSystem.createObjectVariable(endAnchor.getTarget());
        int beginAnchorMargin = beginAnchor.getMargin();
        int endAnchorMargin = endAnchor.getMargin();
        if (this.mVisibility == 8) {
            dimensionFixed2 = true;
            dimension2 = 0;
        } else {
            dimensionFixed2 = dimensionFixed;
            dimension2 = dimension;
        }
        if (beginTarget == null && endTarget == null) {
            linearSystem.addConstraint(system.createRow().createRowEquals(begin, i));
            if (useRatio) {
                dimension3 = dimension2;
                int i6 = endAnchorMargin;
                int i7 = beginAnchorMargin;
                SolverVariable solverVariable = endTarget;
                SolverVariable solverVariable2 = beginTarget;
                SolverVariable solverVariable3 = end;
                SolverVariable solverVariable4 = begin;
            } else if (wrapContent) {
                linearSystem.addConstraint(LinearSystem.createRowEquals(linearSystem, end, begin, i3, true));
                dimension3 = dimension2;
                int i8 = endAnchorMargin;
                int i9 = beginAnchorMargin;
                SolverVariable solverVariable5 = endTarget;
                SolverVariable solverVariable6 = beginTarget;
                SolverVariable solverVariable7 = end;
                SolverVariable solverVariable8 = begin;
            } else if (dimensionFixed2) {
                linearSystem.addConstraint(LinearSystem.createRowEquals(linearSystem, end, begin, dimension2, false));
                dimension3 = dimension2;
                int i10 = endAnchorMargin;
                int i11 = beginAnchorMargin;
                SolverVariable solverVariable9 = endTarget;
                SolverVariable solverVariable10 = beginTarget;
                SolverVariable solverVariable11 = end;
                SolverVariable solverVariable12 = begin;
            } else {
                linearSystem.addConstraint(system.createRow().createRowEquals(end, i2));
                dimension3 = dimension2;
                int i12 = endAnchorMargin;
                int i13 = beginAnchorMargin;
                SolverVariable solverVariable13 = endTarget;
                SolverVariable solverVariable14 = beginTarget;
                SolverVariable solverVariable15 = end;
                SolverVariable solverVariable16 = begin;
            }
        } else if (beginTarget != null && endTarget == null) {
            linearSystem.addConstraint(system.createRow().createRowEquals(begin, beginTarget, beginAnchorMargin));
            if (wrapContent) {
                linearSystem.addConstraint(LinearSystem.createRowEquals(linearSystem, end, begin, i3, true));
                dimension3 = dimension2;
                int i14 = endAnchorMargin;
                int i15 = beginAnchorMargin;
                SolverVariable solverVariable17 = endTarget;
                SolverVariable solverVariable18 = beginTarget;
                SolverVariable solverVariable19 = end;
                SolverVariable solverVariable20 = begin;
            } else if (useRatio) {
                dimension3 = dimension2;
                int i16 = endAnchorMargin;
                int i17 = beginAnchorMargin;
                SolverVariable solverVariable21 = endTarget;
                SolverVariable solverVariable22 = beginTarget;
                SolverVariable solverVariable23 = end;
                SolverVariable solverVariable24 = begin;
            } else if (dimensionFixed2) {
                linearSystem.addConstraint(system.createRow().createRowEquals(end, begin, dimension2));
                dimension3 = dimension2;
                int i18 = endAnchorMargin;
                int i19 = beginAnchorMargin;
                SolverVariable solverVariable25 = endTarget;
                SolverVariable solverVariable26 = beginTarget;
                SolverVariable solverVariable27 = end;
                SolverVariable solverVariable28 = begin;
            } else {
                linearSystem.addConstraint(system.createRow().createRowEquals(end, i2));
                dimension3 = dimension2;
                int i20 = endAnchorMargin;
                int i21 = beginAnchorMargin;
                SolverVariable solverVariable29 = endTarget;
                SolverVariable solverVariable30 = beginTarget;
                SolverVariable solverVariable31 = end;
                SolverVariable solverVariable32 = begin;
            }
        } else if (beginTarget == null && endTarget != null) {
            linearSystem.addConstraint(system.createRow().createRowEquals(end, endTarget, endAnchorMargin * -1));
            if (wrapContent) {
                linearSystem.addConstraint(LinearSystem.createRowEquals(linearSystem, end, begin, i3, true));
                dimension3 = dimension2;
                int i22 = endAnchorMargin;
                int i23 = beginAnchorMargin;
                SolverVariable solverVariable33 = endTarget;
                SolverVariable solverVariable34 = beginTarget;
                SolverVariable solverVariable35 = end;
                SolverVariable solverVariable36 = begin;
            } else if (useRatio) {
                dimension3 = dimension2;
                int i24 = endAnchorMargin;
                int i25 = beginAnchorMargin;
                SolverVariable solverVariable37 = endTarget;
                SolverVariable solverVariable38 = beginTarget;
                SolverVariable solverVariable39 = end;
                SolverVariable solverVariable40 = begin;
            } else if (dimensionFixed2) {
                linearSystem.addConstraint(system.createRow().createRowEquals(end, begin, dimension2));
                dimension3 = dimension2;
                int i26 = endAnchorMargin;
                int i27 = beginAnchorMargin;
                SolverVariable solverVariable41 = endTarget;
                SolverVariable solverVariable42 = beginTarget;
                SolverVariable solverVariable43 = end;
                SolverVariable solverVariable44 = begin;
            } else {
                linearSystem.addConstraint(system.createRow().createRowEquals(begin, i));
                dimension3 = dimension2;
                int i28 = endAnchorMargin;
                int i29 = beginAnchorMargin;
                SolverVariable solverVariable45 = endTarget;
                SolverVariable solverVariable46 = beginTarget;
                SolverVariable solverVariable47 = end;
                SolverVariable solverVariable48 = begin;
            }
        } else if (dimensionFixed2) {
            if (wrapContent) {
                linearSystem.addConstraint(LinearSystem.createRowEquals(linearSystem, end, begin, i3, true));
            } else {
                linearSystem.addConstraint(system.createRow().createRowEquals(end, begin, dimension2));
            }
            if (beginAnchor.getStrength() == endAnchor.getStrength()) {
                int dimension6 = dimension2;
                if (beginTarget == endTarget) {
                    SolverVariable endTarget2 = endTarget;
                    SolverVariable solverVariable49 = begin;
                    linearSystem.addConstraint(LinearSystem.createRowCentering(system, begin, beginTarget, 0, 0.5f, endTarget2, end, 0, true));
                    SolverVariable solverVariable50 = end;
                    dimension3 = dimension6;
                    int i30 = endAnchorMargin;
                    int i31 = beginAnchorMargin;
                    SolverVariable solverVariable51 = beginTarget;
                    SolverVariable solverVariable52 = endTarget2;
                } else {
                    int endAnchorMargin2 = endAnchorMargin;
                    int beginAnchorMargin2 = beginAnchorMargin;
                    SolverVariable endTarget3 = endTarget;
                    SolverVariable beginTarget2 = beginTarget;
                    SolverVariable begin2 = begin;
                    int dimension7 = dimension6;
                    SolverVariable end2 = end;
                    if (!inChain) {
                        int beginAnchorMargin3 = beginAnchorMargin2;
                        SolverVariable beginTarget3 = beginTarget2;
                        linearSystem.addConstraint(LinearSystem.createRowGreaterThan(linearSystem, begin2, beginTarget3, beginAnchorMargin3, beginAnchor.getConnectionType() != ConstraintAnchor.ConnectionType.STRICT));
                        boolean useBidirectionalError = endAnchor.getConnectionType() != ConstraintAnchor.ConnectionType.STRICT;
                        SolverVariable end3 = end2;
                        linearSystem.addConstraint(LinearSystem.createRowLowerThan(linearSystem, end3, endTarget3, endAnchorMargin2 * -1, useBidirectionalError));
                        SolverVariable end4 = end3;
                        boolean z = useBidirectionalError;
                        int i32 = beginAnchorMargin3;
                        SolverVariable solverVariable53 = beginTarget3;
                        linearSystem.addConstraint(LinearSystem.createRowCentering(system, begin2, beginTarget3, beginAnchorMargin3, bias, endTarget3, end4, endAnchorMargin2, false));
                        SolverVariable solverVariable54 = end4;
                        dimension3 = dimension7;
                        int i33 = endAnchorMargin2;
                        SolverVariable solverVariable55 = endTarget3;
                    } else {
                        int i34 = beginAnchorMargin2;
                        SolverVariable solverVariable56 = beginTarget2;
                        SolverVariable solverVariable57 = end2;
                        dimension3 = dimension7;
                        int i35 = endAnchorMargin2;
                        SolverVariable solverVariable58 = endTarget3;
                    }
                }
            } else if (beginAnchor.getStrength() == ConstraintAnchor.Strength.STRONG) {
                linearSystem.addConstraint(system.createRow().createRowEquals(begin, beginTarget, beginAnchorMargin));
                SolverVariable slack = system.createSlackVariable();
                ArrayRow row = system.createRow();
                row.createRowLowerThan(end, endTarget, slack, endAnchorMargin * -1);
                linearSystem.addConstraint(row);
                int i36 = endAnchorMargin;
                int i37 = beginAnchorMargin;
                SolverVariable solverVariable59 = endTarget;
                SolverVariable solverVariable60 = beginTarget;
                SolverVariable solverVariable61 = end;
                SolverVariable solverVariable62 = begin;
                dimension3 = dimension2;
            } else {
                int dimension8 = dimension2;
                SolverVariable slack2 = system.createSlackVariable();
                ArrayRow row2 = system.createRow();
                row2.createRowGreaterThan(begin, beginTarget, slack2, beginAnchorMargin);
                linearSystem.addConstraint(row2);
                SolverVariable solverVariable63 = slack2;
                linearSystem.addConstraint(system.createRow().createRowEquals(end, endTarget, endAnchorMargin * -1));
                int i38 = endAnchorMargin;
                int i39 = beginAnchorMargin;
                SolverVariable solverVariable64 = endTarget;
                SolverVariable solverVariable65 = beginTarget;
                SolverVariable solverVariable66 = end;
                SolverVariable solverVariable67 = begin;
                dimension3 = dimension8;
            }
        } else {
            int dimension9 = dimension2;
            int endAnchorMargin3 = endAnchorMargin;
            int beginAnchorMargin4 = beginAnchorMargin;
            SolverVariable endTarget4 = endTarget;
            SolverVariable beginTarget4 = beginTarget;
            SolverVariable end5 = end;
            SolverVariable begin3 = begin;
            if (useRatio) {
                linearSystem.addGreaterThan(begin3, beginTarget4, beginAnchorMargin4, 3);
                int endAnchorMargin4 = endAnchorMargin3;
                SolverVariable end6 = end5;
                SolverVariable endTarget5 = endTarget4;
                linearSystem.addLowerThan(end6, endTarget5, endAnchorMargin4 * -1, 3);
                SolverVariable endTarget6 = end6;
                linearSystem.addConstraint(LinearSystem.createRowCentering(system, begin3, beginTarget4, beginAnchorMargin4, bias, endTarget5, end6, endAnchorMargin4, true));
                dimension3 = dimension9;
                SolverVariable solverVariable68 = endTarget5;
                int i40 = endAnchorMargin4;
            } else {
                SolverVariable end7 = end5;
                int endAnchorMargin5 = endAnchorMargin3;
                if (inChain) {
                    dimension3 = dimension9;
                    SolverVariable solverVariable69 = endTarget4;
                    int i41 = endAnchorMargin5;
                } else if (matchConstraintDefault == 1) {
                    int dimension10 = dimension9;
                    if (i4 > dimension10) {
                        dimension4 = matchMinDimension;
                    } else {
                        dimension4 = dimension10;
                    }
                    if (i5 > 0) {
                        if (i5 < dimension4) {
                            dimension5 = matchMaxDimension;
                            linearSystem.addEquality(end7, begin3, dimension5, 3);
                            linearSystem.addGreaterThan(begin3, beginTarget4, beginAnchorMargin4, 2);
                            int endAnchorMargin6 = endAnchorMargin5;
                            SolverVariable endTarget7 = endTarget4;
                            linearSystem.addLowerThan(end7, endTarget7, -endAnchorMargin6, 2);
                            int endAnchorMargin7 = endAnchorMargin6;
                            system.addCentering(begin3, beginTarget4, beginAnchorMargin4, bias, endTarget7, end7, endAnchorMargin7, 4);
                            int i42 = dimension5;
                            int i43 = endAnchorMargin7;
                            SolverVariable solverVariable70 = endTarget4;
                            return;
                        }
                        linearSystem.addLowerThan(end7, begin3, i5, 3);
                    }
                    dimension5 = dimension4;
                    linearSystem.addEquality(end7, begin3, dimension5, 3);
                    linearSystem.addGreaterThan(begin3, beginTarget4, beginAnchorMargin4, 2);
                    int endAnchorMargin62 = endAnchorMargin5;
                    SolverVariable endTarget72 = endTarget4;
                    linearSystem.addLowerThan(end7, endTarget72, -endAnchorMargin62, 2);
                    int endAnchorMargin72 = endAnchorMargin62;
                    system.addCentering(begin3, beginTarget4, beginAnchorMargin4, bias, endTarget72, end7, endAnchorMargin72, 4);
                    int i422 = dimension5;
                    int i432 = endAnchorMargin72;
                    SolverVariable solverVariable702 = endTarget4;
                    return;
                } else {
                    int dimension11 = dimension9;
                    int endAnchorMargin8 = endAnchorMargin5;
                    if (i4 == 0 && i5 == 0) {
                        linearSystem.addConstraint(system.createRow().createRowEquals(begin3, beginTarget4, beginAnchorMargin4));
                        int endAnchorMargin9 = endAnchorMargin8;
                        SolverVariable endTarget8 = endTarget4;
                        linearSystem.addConstraint(system.createRow().createRowEquals(end7, endTarget8, endAnchorMargin9 * -1));
                        SolverVariable solverVariable71 = endTarget8;
                        dimension3 = dimension11;
                        int i44 = endAnchorMargin9;
                    } else {
                        int endAnchorMargin10 = endAnchorMargin8;
                        SolverVariable endTarget9 = endTarget4;
                        if (i5 > 0) {
                            linearSystem.addLowerThan(end7, begin3, i5, 3);
                        }
                        linearSystem.addGreaterThan(begin3, beginTarget4, beginAnchorMargin4, 2);
                        linearSystem.addLowerThan(end7, endTarget9, -endAnchorMargin10, 2);
                        SolverVariable solverVariable72 = endTarget9;
                        dimension3 = dimension11;
                        int i45 = endAnchorMargin10;
                        system.addCentering(begin3, beginTarget4, beginAnchorMargin4, bias, endTarget9, end7, endAnchorMargin10, 4);
                    }
                }
            }
        }
    }

    public void updateFromSolver(LinearSystem system, int group) {
        if (group == Integer.MAX_VALUE) {
            setFrame(system.getObjectVariableValue(this.mLeft), system.getObjectVariableValue(this.mTop), system.getObjectVariableValue(this.mRight), system.getObjectVariableValue(this.mBottom));
        } else if (group == -2) {
            setFrame(this.mSolverLeft, this.mSolverTop, this.mSolverRight, this.mSolverBottom);
        } else {
            if (this.mLeft.mGroup == group) {
                this.mSolverLeft = system.getObjectVariableValue(this.mLeft);
            }
            if (this.mTop.mGroup == group) {
                this.mSolverTop = system.getObjectVariableValue(this.mTop);
            }
            if (this.mRight.mGroup == group) {
                this.mSolverRight = system.getObjectVariableValue(this.mRight);
            }
            if (this.mBottom.mGroup == group) {
                this.mSolverBottom = system.getObjectVariableValue(this.mBottom);
            }
        }
    }

    public void updateFromSolver(LinearSystem system) {
        updateFromSolver(system, Integer.MAX_VALUE);
    }
}
