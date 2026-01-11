package android.support.constraint;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.constraint.solver.widgets.ConstraintAnchor;
import android.support.constraint.solver.widgets.ConstraintWidget;
import android.support.constraint.solver.widgets.ConstraintWidgetContainer;
import android.support.constraint.solver.widgets.Guideline;
import android.support.p002v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

public class ConstraintLayout extends ViewGroup {
    static final boolean ALLOWS_EMBEDDED = false;
    private static final boolean SIMPLE_LAYOUT = true;
    private static final String TAG = "ConstraintLayout";
    public static final String VERSION = "ConstraintLayout-1.0.0";
    SparseArray<View> mChildrenByIds = new SparseArray<>();
    private ConstraintSet mConstraintSet = null;
    private boolean mDirtyHierarchy = true;
    ConstraintWidgetContainer mLayoutWidget = new ConstraintWidgetContainer();
    private int mMaxHeight = Integer.MAX_VALUE;
    private int mMaxWidth = Integer.MAX_VALUE;
    private int mMinHeight = 0;
    private int mMinWidth = 0;
    private int mOptimizationLevel = 2;
    private final ArrayList<ConstraintWidget> mVariableDimensionsWidgets = new ArrayList<>(100);

    public ConstraintLayout(Context context) {
        super(context);
        init((AttributeSet) null);
    }

    public ConstraintLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ConstraintLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public void setId(int id) {
        this.mChildrenByIds.remove(getId());
        super.setId(id);
        this.mChildrenByIds.put(getId(), this);
    }

    private void init(AttributeSet attrs) {
        this.mLayoutWidget.setCompanionWidget(this);
        this.mChildrenByIds.put(getId(), this);
        this.mConstraintSet = null;
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, C0019R.styleable.ConstraintLayout_Layout);
            int N = a.getIndexCount();
            for (int i = 0; i < N; i++) {
                int attr = a.getIndex(i);
                if (attr == C0019R.styleable.ConstraintLayout_Layout_android_minWidth) {
                    this.mMinWidth = a.getDimensionPixelOffset(attr, this.mMinWidth);
                } else if (attr == C0019R.styleable.ConstraintLayout_Layout_android_minHeight) {
                    this.mMinHeight = a.getDimensionPixelOffset(attr, this.mMinHeight);
                } else if (attr == C0019R.styleable.ConstraintLayout_Layout_android_maxWidth) {
                    this.mMaxWidth = a.getDimensionPixelOffset(attr, this.mMaxWidth);
                } else if (attr == C0019R.styleable.ConstraintLayout_Layout_android_maxHeight) {
                    this.mMaxHeight = a.getDimensionPixelOffset(attr, this.mMaxHeight);
                } else if (attr == C0019R.styleable.ConstraintLayout_Layout_layout_optimizationLevel) {
                    this.mOptimizationLevel = a.getInt(attr, this.mOptimizationLevel);
                } else if (attr == C0019R.styleable.ConstraintLayout_Layout_constraintSet) {
                    int id = a.getResourceId(attr, 0);
                    ConstraintSet constraintSet = new ConstraintSet();
                    this.mConstraintSet = constraintSet;
                    constraintSet.load(getContext(), id);
                }
            }
            a.recycle();
        }
        this.mLayoutWidget.setOptimizationLevel(this.mOptimizationLevel);
    }

    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        if (Build.VERSION.SDK_INT < 14) {
            onViewAdded(child);
        }
    }

    public void removeView(View view) {
        super.removeView(view);
        if (Build.VERSION.SDK_INT < 14) {
            onViewRemoved(view);
        }
    }

    public void onViewAdded(View view) {
        if (Build.VERSION.SDK_INT >= 14) {
            super.onViewAdded(view);
        }
        ConstraintWidget widget = getViewWidget(view);
        if ((view instanceof Guideline) && !(widget instanceof Guideline)) {
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            layoutParams.widget = new Guideline();
            layoutParams.isGuideline = true;
            ((Guideline) layoutParams.widget).setOrientation(layoutParams.orientation);
            ConstraintWidget widget2 = layoutParams.widget;
        }
        this.mChildrenByIds.put(view.getId(), view);
        this.mDirtyHierarchy = true;
    }

    public void onViewRemoved(View view) {
        if (Build.VERSION.SDK_INT >= 14) {
            super.onViewRemoved(view);
        }
        this.mChildrenByIds.remove(view.getId());
        this.mLayoutWidget.remove(getViewWidget(view));
        this.mDirtyHierarchy = true;
    }

    public void setMinWidth(int value) {
        if (value != this.mMinWidth) {
            this.mMinWidth = value;
            requestLayout();
        }
    }

    public void setMinHeight(int value) {
        if (value != this.mMinHeight) {
            this.mMinHeight = value;
            requestLayout();
        }
    }

    public int getMinWidth() {
        return this.mMinWidth;
    }

    public int getMinHeight() {
        return this.mMinHeight;
    }

    public void setMaxWidth(int value) {
        if (value != this.mMaxWidth) {
            this.mMaxWidth = value;
            requestLayout();
        }
    }

    public void setMaxHeight(int value) {
        if (value != this.mMaxHeight) {
            this.mMaxHeight = value;
            requestLayout();
        }
    }

    public int getMaxWidth() {
        return this.mMaxWidth;
    }

    public int getMaxHeight() {
        return this.mMaxHeight;
    }

    private void updateHierarchy() {
        int count = getChildCount();
        boolean recompute = false;
        int i = 0;
        while (true) {
            if (i >= count) {
                break;
            } else if (getChildAt(i).isLayoutRequested()) {
                recompute = true;
                break;
            } else {
                i++;
            }
        }
        if (recompute) {
            this.mVariableDimensionsWidgets.clear();
            setChildrenConstraints();
        }
    }

    private void setChildrenConstraints() {
        int resolveGoneRightMargin;
        int resolveGoneLeftMargin;
        int resolvedRightToLeft;
        int resolvedLeftToRight;
        int resolvedLeftToLeft;
        int resolveGoneRightMargin2;
        float resolvedHorizontalBias;
        int resolvedRightToRight;
        float resolvedHorizontalBias2;
        ConstraintWidget target;
        ConstraintWidget target2;
        ConstraintWidget target3;
        ConstraintSet constraintSet = this.mConstraintSet;
        if (constraintSet != null) {
            constraintSet.applyToInternal(this);
        }
        int count = getChildCount();
        this.mLayoutWidget.removeAllChildren();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            ConstraintWidget widget = getViewWidget(child);
            if (widget != null) {
                LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
                widget.reset();
                widget.setVisibility(child.getVisibility());
                widget.setCompanionWidget(child);
                this.mLayoutWidget.add(widget);
                if (!layoutParams.verticalDimensionFixed || !layoutParams.horizontalDimensionFixed) {
                    this.mVariableDimensionsWidgets.add(widget);
                }
                if (layoutParams.isGuideline) {
                    Guideline guideline = (Guideline) widget;
                    if (layoutParams.guideBegin != -1) {
                        guideline.setGuideBegin(layoutParams.guideBegin);
                    }
                    if (layoutParams.guideEnd != -1) {
                        guideline.setGuideEnd(layoutParams.guideEnd);
                    }
                    if (layoutParams.guidePercent != -1.0f) {
                        guideline.setGuidePercent(layoutParams.guidePercent);
                    }
                } else if (layoutParams.resolvedLeftToLeft != -1 || layoutParams.resolvedLeftToRight != -1 || layoutParams.resolvedRightToLeft != -1 || layoutParams.resolvedRightToRight != -1 || layoutParams.topToTop != -1 || layoutParams.topToBottom != -1 || layoutParams.bottomToTop != -1 || layoutParams.bottomToBottom != -1 || layoutParams.baselineToBaseline != -1 || layoutParams.editorAbsoluteX != -1 || layoutParams.editorAbsoluteY != -1 || layoutParams.width == -1 || layoutParams.height == -1) {
                    int resolvedLeftToLeft2 = layoutParams.resolvedLeftToLeft;
                    int resolvedLeftToRight2 = layoutParams.resolvedLeftToRight;
                    int resolvedRightToLeft2 = layoutParams.resolvedRightToLeft;
                    int resolvedRightToRight2 = layoutParams.resolvedRightToRight;
                    int resolveGoneLeftMargin2 = layoutParams.resolveGoneLeftMargin;
                    int resolveGoneRightMargin3 = layoutParams.resolveGoneRightMargin;
                    float resolvedHorizontalBias3 = layoutParams.resolvedHorizontalBias;
                    if (Build.VERSION.SDK_INT < 17) {
                        int resolvedLeftToLeft3 = layoutParams.leftToLeft;
                        int resolvedLeftToRight3 = layoutParams.leftToRight;
                        int resolvedRightToLeft3 = layoutParams.rightToLeft;
                        int resolvedRightToRight3 = layoutParams.rightToRight;
                        int resolveGoneLeftMargin3 = layoutParams.goneLeftMargin;
                        int resolveGoneRightMargin4 = layoutParams.goneRightMargin;
                        float resolvedHorizontalBias4 = layoutParams.horizontalBias;
                        if (resolvedLeftToLeft3 == -1 && resolvedLeftToRight3 == -1) {
                            if (layoutParams.startToStart != -1) {
                                resolvedLeftToLeft3 = layoutParams.startToStart;
                            } else if (layoutParams.startToEnd != -1) {
                                resolvedLeftToRight3 = layoutParams.startToEnd;
                            }
                        }
                        if (resolvedRightToLeft3 == -1 && resolvedRightToRight3 == -1) {
                            if (layoutParams.endToStart != -1) {
                                resolvedLeftToRight = resolvedLeftToRight3;
                                resolvedRightToLeft = layoutParams.endToStart;
                                resolveGoneLeftMargin = resolveGoneLeftMargin3;
                                resolveGoneRightMargin = resolveGoneRightMargin4;
                                resolvedHorizontalBias = resolvedHorizontalBias4;
                                resolvedLeftToLeft = resolvedLeftToLeft3;
                                resolveGoneRightMargin2 = resolvedRightToRight3;
                            } else if (layoutParams.endToEnd != -1) {
                                resolvedLeftToRight = resolvedLeftToRight3;
                                resolvedRightToLeft = resolvedRightToLeft3;
                                resolveGoneLeftMargin = resolveGoneLeftMargin3;
                                resolveGoneRightMargin = resolveGoneRightMargin4;
                                resolvedHorizontalBias = resolvedHorizontalBias4;
                                resolvedLeftToLeft = resolvedLeftToLeft3;
                                resolveGoneRightMargin2 = layoutParams.endToEnd;
                            }
                        }
                        resolvedLeftToRight = resolvedLeftToRight3;
                        resolvedRightToLeft = resolvedRightToLeft3;
                        resolveGoneLeftMargin = resolveGoneLeftMargin3;
                        resolveGoneRightMargin = resolveGoneRightMargin4;
                        resolvedHorizontalBias = resolvedHorizontalBias4;
                        resolvedLeftToLeft = resolvedLeftToLeft3;
                        resolveGoneRightMargin2 = resolvedRightToRight3;
                    } else {
                        resolvedLeftToRight = resolvedLeftToRight2;
                        resolvedRightToLeft = resolvedRightToLeft2;
                        resolveGoneLeftMargin = resolveGoneLeftMargin2;
                        resolveGoneRightMargin = resolveGoneRightMargin3;
                        resolvedHorizontalBias = resolvedHorizontalBias3;
                        resolvedLeftToLeft = resolvedLeftToLeft2;
                        resolveGoneRightMargin2 = resolvedRightToRight2;
                    }
                    if (resolvedLeftToLeft != -1) {
                        ConstraintWidget target4 = getTargetWidget(resolvedLeftToLeft);
                        if (target4 != null) {
                            resolvedHorizontalBias2 = resolvedHorizontalBias;
                            resolvedRightToRight = resolveGoneRightMargin2;
                            widget.immediateConnect(ConstraintAnchor.Type.LEFT, target4, ConstraintAnchor.Type.LEFT, layoutParams.leftMargin, resolveGoneLeftMargin);
                        } else {
                            resolvedHorizontalBias2 = resolvedHorizontalBias;
                            resolvedRightToRight = resolveGoneRightMargin2;
                        }
                    } else {
                        resolvedHorizontalBias2 = resolvedHorizontalBias;
                        resolvedRightToRight = resolveGoneRightMargin2;
                        if (!(resolvedLeftToRight == -1 || (target3 = getTargetWidget(resolvedLeftToRight)) == null)) {
                            widget.immediateConnect(ConstraintAnchor.Type.LEFT, target3, ConstraintAnchor.Type.RIGHT, layoutParams.leftMargin, resolveGoneLeftMargin);
                        }
                    }
                    if (resolvedRightToLeft != -1) {
                        ConstraintWidget target5 = getTargetWidget(resolvedRightToLeft);
                        if (target5 != null) {
                            widget.immediateConnect(ConstraintAnchor.Type.RIGHT, target5, ConstraintAnchor.Type.LEFT, layoutParams.rightMargin, resolveGoneRightMargin);
                        }
                        int i2 = resolvedRightToRight;
                    } else {
                        int resolvedRightToRight4 = resolvedRightToRight;
                        if (resolvedRightToRight4 != -1) {
                            ConstraintWidget target6 = getTargetWidget(resolvedRightToRight4);
                            if (target6 != null) {
                                int i3 = resolvedRightToRight4;
                                widget.immediateConnect(ConstraintAnchor.Type.RIGHT, target6, ConstraintAnchor.Type.RIGHT, layoutParams.rightMargin, resolveGoneRightMargin);
                            }
                        }
                    }
                    if (layoutParams.topToTop != -1) {
                        ConstraintWidget target7 = getTargetWidget(layoutParams.topToTop);
                        if (target7 != null) {
                            widget.immediateConnect(ConstraintAnchor.Type.TOP, target7, ConstraintAnchor.Type.TOP, layoutParams.topMargin, layoutParams.goneTopMargin);
                        }
                    } else if (!(layoutParams.topToBottom == -1 || (target2 = getTargetWidget(layoutParams.topToBottom)) == null)) {
                        widget.immediateConnect(ConstraintAnchor.Type.TOP, target2, ConstraintAnchor.Type.BOTTOM, layoutParams.topMargin, layoutParams.goneTopMargin);
                    }
                    if (layoutParams.bottomToTop != -1) {
                        ConstraintWidget target8 = getTargetWidget(layoutParams.bottomToTop);
                        if (target8 != null) {
                            widget.immediateConnect(ConstraintAnchor.Type.BOTTOM, target8, ConstraintAnchor.Type.TOP, layoutParams.bottomMargin, layoutParams.goneBottomMargin);
                        }
                    } else if (!(layoutParams.bottomToBottom == -1 || (target = getTargetWidget(layoutParams.bottomToBottom)) == null)) {
                        widget.immediateConnect(ConstraintAnchor.Type.BOTTOM, target, ConstraintAnchor.Type.BOTTOM, layoutParams.bottomMargin, layoutParams.goneBottomMargin);
                    }
                    if (layoutParams.baselineToBaseline != -1) {
                        View view = this.mChildrenByIds.get(layoutParams.baselineToBaseline);
                        ConstraintWidget target9 = getTargetWidget(layoutParams.baselineToBaseline);
                        if (!(target9 == null || view == null || !(view.getLayoutParams() instanceof LayoutParams))) {
                            layoutParams.needsBaseline = true;
                            ((LayoutParams) view.getLayoutParams()).needsBaseline = true;
                            ConstraintAnchor baseline = widget.getAnchor(ConstraintAnchor.Type.BASELINE);
                            baseline.connect(target9.getAnchor(ConstraintAnchor.Type.BASELINE), 0, -1, ConstraintAnchor.Strength.STRONG, 0, true);
                            widget.getAnchor(ConstraintAnchor.Type.TOP).reset();
                            widget.getAnchor(ConstraintAnchor.Type.BOTTOM).reset();
                        }
                    }
                    float resolvedHorizontalBias5 = resolvedHorizontalBias2;
                    if (resolvedHorizontalBias5 >= 0.0f && resolvedHorizontalBias5 != 0.5f) {
                        widget.setHorizontalBiasPercent(resolvedHorizontalBias5);
                    }
                    if (layoutParams.verticalBias >= 0.0f && layoutParams.verticalBias != 0.5f) {
                        widget.setVerticalBiasPercent(layoutParams.verticalBias);
                    }
                    if (isInEditMode() && !(layoutParams.editorAbsoluteX == -1 && layoutParams.editorAbsoluteY == -1)) {
                        widget.setOrigin(layoutParams.editorAbsoluteX, layoutParams.editorAbsoluteY);
                    }
                    if (layoutParams.horizontalDimensionFixed) {
                        widget.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
                        widget.setWidth(layoutParams.width);
                    } else if (layoutParams.width == -1) {
                        widget.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_PARENT);
                        widget.getAnchor(ConstraintAnchor.Type.LEFT).mMargin = layoutParams.leftMargin;
                        widget.getAnchor(ConstraintAnchor.Type.RIGHT).mMargin = layoutParams.rightMargin;
                    } else {
                        widget.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT);
                        widget.setWidth(0);
                    }
                    if (layoutParams.verticalDimensionFixed) {
                        widget.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
                        widget.setHeight(layoutParams.height);
                    } else if (layoutParams.height == -1) {
                        widget.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_PARENT);
                        widget.getAnchor(ConstraintAnchor.Type.TOP).mMargin = layoutParams.topMargin;
                        widget.getAnchor(ConstraintAnchor.Type.BOTTOM).mMargin = layoutParams.bottomMargin;
                    } else {
                        widget.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT);
                        widget.setHeight(0);
                    }
                    if (layoutParams.dimensionRatio != null) {
                        widget.setDimensionRatio(layoutParams.dimensionRatio);
                    }
                    widget.setHorizontalWeight(layoutParams.horizontalWeight);
                    widget.setVerticalWeight(layoutParams.verticalWeight);
                    widget.setHorizontalChainStyle(layoutParams.horizontalChainStyle);
                    widget.setVerticalChainStyle(layoutParams.verticalChainStyle);
                    widget.setHorizontalMatchStyle(layoutParams.matchConstraintDefaultWidth, layoutParams.matchConstraintMinWidth, layoutParams.matchConstraintMaxWidth);
                    widget.setVerticalMatchStyle(layoutParams.matchConstraintDefaultHeight, layoutParams.matchConstraintMinHeight, layoutParams.matchConstraintMaxHeight);
                }
            }
        }
    }

    private final ConstraintWidget getTargetWidget(int id) {
        if (id == 0) {
            return this.mLayoutWidget;
        }
        View view = this.mChildrenByIds.get(id);
        if (view == this) {
            return this.mLayoutWidget;
        }
        if (view == null) {
            return null;
        }
        return ((LayoutParams) view.getLayoutParams()).widget;
    }

    private final ConstraintWidget getViewWidget(View view) {
        if (view == this) {
            return this.mLayoutWidget;
        }
        if (view == null) {
            return null;
        }
        return ((LayoutParams) view.getLayoutParams()).widget;
    }

    private void internalMeasureChildren(int parentWidthSpec, int parentHeightSpec) {
        int baseline;
        int childWidthMeasureSpec;
        int childHeightMeasureSpec;
        int i = parentWidthSpec;
        int i2 = parentHeightSpec;
        int heightPadding = getPaddingTop() + getPaddingBottom();
        int widthPadding = getPaddingLeft() + getPaddingRight();
        int widgetsCount = getChildCount();
        int i3 = 0;
        while (i3 < widgetsCount) {
            View child = getChildAt(i3);
            if (child.getVisibility() != 8) {
                LayoutParams params = (LayoutParams) child.getLayoutParams();
                ConstraintWidget widget = params.widget;
                if (!params.isGuideline) {
                    int width = params.width;
                    int height = params.height;
                    boolean z = true;
                    if (!params.horizontalDimensionFixed && !params.verticalDimensionFixed && ((params.horizontalDimensionFixed || params.matchConstraintDefaultWidth != 1) && params.width != -1 && (params.verticalDimensionFixed || !(params.matchConstraintDefaultHeight == 1 || params.height == -1)))) {
                        z = false;
                    }
                    boolean doMeasure = z;
                    boolean didWrapMeasureWidth = false;
                    boolean didWrapMeasureHeight = false;
                    if (doMeasure) {
                        if (width == 0 || width == -1) {
                            didWrapMeasureWidth = true;
                            childWidthMeasureSpec = getChildMeasureSpec(i, widthPadding, -2);
                        } else {
                            childWidthMeasureSpec = getChildMeasureSpec(i, widthPadding, width);
                        }
                        if (height == 0 || height == -1) {
                            childHeightMeasureSpec = getChildMeasureSpec(i2, heightPadding, -2);
                            didWrapMeasureHeight = true;
                        } else {
                            childHeightMeasureSpec = getChildMeasureSpec(i2, heightPadding, height);
                        }
                        child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
                        width = child.getMeasuredWidth();
                        height = child.getMeasuredHeight();
                    }
                    widget.setWidth(width);
                    widget.setHeight(height);
                    if (didWrapMeasureWidth) {
                        widget.setWrapWidth(width);
                    }
                    if (didWrapMeasureHeight) {
                        widget.setWrapHeight(height);
                    }
                    if (params.needsBaseline && (baseline = child.getBaseline()) != -1) {
                        widget.setBaselineDistance(baseline);
                    }
                }
            }
            i3++;
            i = parentWidthSpec;
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        boolean containerWrapWidth;
        int sizeDependentWidgetsCount;
        int paddingTop;
        int paddingLeft;
        int widthSpec;
        int heightSpec;
        int baseline;
        int i = widthMeasureSpec;
        int i2 = heightMeasureSpec;
        int paddingLeft2 = getPaddingLeft();
        int paddingTop2 = getPaddingTop();
        this.mLayoutWidget.setX(paddingLeft2);
        this.mLayoutWidget.setY(paddingTop2);
        setSelfDimensionBehaviour(widthMeasureSpec, heightMeasureSpec);
        boolean containerWrapHeight = false;
        if (this.mDirtyHierarchy) {
            this.mDirtyHierarchy = false;
            updateHierarchy();
        }
        internalMeasureChildren(widthMeasureSpec, heightMeasureSpec);
        if (getChildCount() > 0) {
            solveLinearSystem();
        }
        int childState = 0;
        int sizeDependentWidgetsCount2 = this.mVariableDimensionsWidgets.size();
        int heightPadding = getPaddingBottom() + paddingTop2;
        int widthPadding = getPaddingRight() + paddingLeft2;
        if (sizeDependentWidgetsCount2 > 0) {
            boolean needSolverPass = false;
            boolean containerWrapWidth2 = this.mLayoutWidget.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
            if (this.mLayoutWidget.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                containerWrapHeight = true;
            }
            int i3 = 0;
            while (i3 < sizeDependentWidgetsCount2) {
                ConstraintWidget widget = this.mVariableDimensionsWidgets.get(i3);
                if (widget instanceof Guideline) {
                    paddingLeft = paddingLeft2;
                } else {
                    View child = (View) widget.getCompanionWidget();
                    if (child == null) {
                        paddingLeft = paddingLeft2;
                    } else {
                        paddingLeft = paddingLeft2;
                        if (child.getVisibility() != 8) {
                            LayoutParams params = (LayoutParams) child.getLayoutParams();
                            paddingTop = paddingTop2;
                            sizeDependentWidgetsCount = sizeDependentWidgetsCount2;
                            if (params.width == -2) {
                                widthSpec = getChildMeasureSpec(i, widthPadding, params.width);
                            } else {
                                widthSpec = View.MeasureSpec.makeMeasureSpec(widget.getWidth(), 1073741824);
                            }
                            if (params.height == -2) {
                                heightSpec = getChildMeasureSpec(i2, heightPadding, params.height);
                            } else {
                                heightSpec = View.MeasureSpec.makeMeasureSpec(widget.getHeight(), 1073741824);
                            }
                            child.measure(widthSpec, heightSpec);
                            int measuredWidth = child.getMeasuredWidth();
                            int i4 = widthSpec;
                            int widthSpec2 = child.getMeasuredHeight();
                            int i5 = heightSpec;
                            if (measuredWidth != widget.getWidth()) {
                                widget.setWidth(measuredWidth);
                                if (containerWrapWidth2) {
                                    int i6 = measuredWidth;
                                    if (widget.getRight() > this.mLayoutWidget.getWidth()) {
                                        containerWrapWidth = containerWrapWidth2;
                                        this.mLayoutWidget.setWidth(Math.max(this.mMinWidth, widget.getRight() + widget.getAnchor(ConstraintAnchor.Type.RIGHT).getMargin()));
                                    } else {
                                        containerWrapWidth = containerWrapWidth2;
                                    }
                                } else {
                                    containerWrapWidth = containerWrapWidth2;
                                }
                                needSolverPass = true;
                            } else {
                                containerWrapWidth = containerWrapWidth2;
                            }
                            if (widthSpec2 != widget.getHeight()) {
                                widget.setHeight(widthSpec2);
                                if (containerWrapHeight && widget.getBottom() > this.mLayoutWidget.getHeight()) {
                                    this.mLayoutWidget.setHeight(Math.max(this.mMinHeight, widget.getBottom() + widget.getAnchor(ConstraintAnchor.Type.BOTTOM).getMargin()));
                                }
                                needSolverPass = true;
                            }
                            if (!(!params.needsBaseline || (baseline = child.getBaseline()) == -1 || baseline == widget.getBaselineDistance())) {
                                widget.setBaselineDistance(baseline);
                                needSolverPass = true;
                            }
                            if (Build.VERSION.SDK_INT >= 11) {
                                childState = combineMeasuredStates(childState, child.getMeasuredState());
                            }
                            i3++;
                            paddingLeft2 = paddingLeft;
                            paddingTop2 = paddingTop;
                            sizeDependentWidgetsCount2 = sizeDependentWidgetsCount;
                            containerWrapWidth2 = containerWrapWidth;
                        }
                    }
                }
                paddingTop = paddingTop2;
                sizeDependentWidgetsCount = sizeDependentWidgetsCount2;
                containerWrapWidth = containerWrapWidth2;
                i3++;
                paddingLeft2 = paddingLeft;
                paddingTop2 = paddingTop;
                sizeDependentWidgetsCount2 = sizeDependentWidgetsCount;
                containerWrapWidth2 = containerWrapWidth;
            }
            int i7 = paddingTop2;
            int i8 = sizeDependentWidgetsCount2;
            boolean z = containerWrapWidth2;
            if (needSolverPass) {
                solveLinearSystem();
            }
        } else {
            int i9 = paddingTop2;
            int i10 = sizeDependentWidgetsCount2;
        }
        int androidLayoutWidth = this.mLayoutWidget.getWidth() + widthPadding;
        int androidLayoutHeight = this.mLayoutWidget.getHeight() + heightPadding;
        if (Build.VERSION.SDK_INT >= 11) {
            int resolvedWidthSize = resolveSizeAndState(androidLayoutWidth, i, childState);
            int resolvedHeightSize = resolveSizeAndState(androidLayoutHeight, i2, childState << 16);
            int resolvedWidthSize2 = Math.min(this.mMaxWidth, resolvedWidthSize);
            int resolvedHeightSize2 = Math.min(this.mMaxHeight, resolvedHeightSize);
            int resolvedWidthSize3 = resolvedWidthSize2 & ViewCompat.MEASURED_SIZE_MASK;
            int resolvedHeightSize3 = resolvedHeightSize2 & ViewCompat.MEASURED_SIZE_MASK;
            if (this.mLayoutWidget.isWidthMeasuredTooSmall()) {
                resolvedWidthSize3 |= 16777216;
            }
            if (this.mLayoutWidget.isHeightMeasuredTooSmall()) {
                resolvedHeightSize3 |= 16777216;
            }
            setMeasuredDimension(resolvedWidthSize3, resolvedHeightSize3);
            return;
        }
        setMeasuredDimension(androidLayoutWidth, androidLayoutHeight);
    }

    private void setSelfDimensionBehaviour(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = View.MeasureSpec.getSize(heightMeasureSpec);
        int heightPadding = getPaddingTop() + getPaddingBottom();
        int widthPadding = getPaddingLeft() + getPaddingRight();
        ConstraintWidget.DimensionBehaviour widthBehaviour = ConstraintWidget.DimensionBehaviour.FIXED;
        ConstraintWidget.DimensionBehaviour heightBehaviour = ConstraintWidget.DimensionBehaviour.FIXED;
        int desiredWidth = 0;
        int desiredHeight = 0;
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        if (widthMode == Integer.MIN_VALUE) {
            widthBehaviour = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
            desiredWidth = widthSize;
        } else if (widthMode == 0) {
            widthBehaviour = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
        } else if (widthMode == 1073741824) {
            desiredWidth = Math.min(this.mMaxWidth, widthSize) - widthPadding;
        }
        if (heightMode == Integer.MIN_VALUE) {
            heightBehaviour = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
            desiredHeight = heightSize;
        } else if (heightMode == 0) {
            heightBehaviour = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
        } else if (heightMode == 1073741824) {
            desiredHeight = Math.min(this.mMaxHeight, heightSize) - heightPadding;
        }
        this.mLayoutWidget.setMinWidth(0);
        this.mLayoutWidget.setMinHeight(0);
        this.mLayoutWidget.setHorizontalDimensionBehaviour(widthBehaviour);
        this.mLayoutWidget.setWidth(desiredWidth);
        this.mLayoutWidget.setVerticalDimensionBehaviour(heightBehaviour);
        this.mLayoutWidget.setHeight(desiredHeight);
        this.mLayoutWidget.setMinWidth((this.mMinWidth - getPaddingLeft()) - getPaddingRight());
        this.mLayoutWidget.setMinHeight((this.mMinHeight - getPaddingTop()) - getPaddingBottom());
    }

    /* access modifiers changed from: protected */
    public void solveLinearSystem() {
        this.mLayoutWidget.layout();
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int widgetsCount = getChildCount();
        boolean isInEditMode = isInEditMode();
        for (int i = 0; i < widgetsCount; i++) {
            View child = getChildAt(i);
            LayoutParams params = (LayoutParams) child.getLayoutParams();
            if (child.getVisibility() != 8 || params.isGuideline || isInEditMode) {
                ConstraintWidget widget = params.widget;
                int l = widget.getDrawX();
                int t = widget.getDrawY();
                child.layout(l, t, widget.getWidth() + l, widget.getHeight() + t);
            }
        }
    }

    public void setOptimizationLevel(int level) {
        this.mLayoutWidget.setOptimizationLevel(level);
    }

    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    /* access modifiers changed from: protected */
    public LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }

    /* access modifiers changed from: protected */
    public ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    public void setConstraintSet(ConstraintSet set) {
        this.mConstraintSet = set;
    }

    public static class LayoutParams extends ViewGroup.MarginLayoutParams {
        public static final int BASELINE = 5;
        public static final int BOTTOM = 4;
        public static final int CHAIN_PACKED = 2;
        public static final int CHAIN_SPREAD = 0;
        public static final int CHAIN_SPREAD_INSIDE = 1;
        public static final int END = 7;
        public static final int HORIZONTAL = 0;
        public static final int LEFT = 1;
        public static final int MATCH_CONSTRAINT = 0;
        public static final int MATCH_CONSTRAINT_SPREAD = 0;
        public static final int MATCH_CONSTRAINT_WRAP = 1;
        public static final int PARENT_ID = 0;
        public static final int RIGHT = 2;
        public static final int START = 6;
        public static final int TOP = 3;
        public static final int UNSET = -1;
        public static final int VERTICAL = 1;
        public int baselineToBaseline;
        public int bottomToBottom;
        public int bottomToTop;
        public String dimensionRatio;
        int dimensionRatioSide;
        float dimensionRatioValue;
        public int editorAbsoluteX;
        public int editorAbsoluteY;
        public int endToEnd;
        public int endToStart;
        public int goneBottomMargin;
        public int goneEndMargin;
        public int goneLeftMargin;
        public int goneRightMargin;
        public int goneStartMargin;
        public int goneTopMargin;
        public int guideBegin;
        public int guideEnd;
        public float guidePercent;
        public float horizontalBias;
        public int horizontalChainStyle;
        boolean horizontalDimensionFixed;
        public float horizontalWeight;
        boolean isGuideline;
        public int leftToLeft;
        public int leftToRight;
        public int matchConstraintDefaultHeight;
        public int matchConstraintDefaultWidth;
        public int matchConstraintMaxHeight;
        public int matchConstraintMaxWidth;
        public int matchConstraintMinHeight;
        public int matchConstraintMinWidth;
        boolean needsBaseline;
        public int orientation;
        int resolveGoneLeftMargin;
        int resolveGoneRightMargin;
        float resolvedHorizontalBias;
        int resolvedLeftToLeft;
        int resolvedLeftToRight;
        int resolvedRightToLeft;
        int resolvedRightToRight;
        public int rightToLeft;
        public int rightToRight;
        public int startToEnd;
        public int startToStart;
        public int topToBottom;
        public int topToTop;
        public float verticalBias;
        public int verticalChainStyle;
        boolean verticalDimensionFixed;
        public float verticalWeight;
        ConstraintWidget widget;

        public LayoutParams(LayoutParams source) {
            super(source);
            this.guideBegin = -1;
            this.guideEnd = -1;
            this.guidePercent = -1.0f;
            this.leftToLeft = -1;
            this.leftToRight = -1;
            this.rightToLeft = -1;
            this.rightToRight = -1;
            this.topToTop = -1;
            this.topToBottom = -1;
            this.bottomToTop = -1;
            this.bottomToBottom = -1;
            this.baselineToBaseline = -1;
            this.startToEnd = -1;
            this.startToStart = -1;
            this.endToStart = -1;
            this.endToEnd = -1;
            this.goneLeftMargin = -1;
            this.goneTopMargin = -1;
            this.goneRightMargin = -1;
            this.goneBottomMargin = -1;
            this.goneStartMargin = -1;
            this.goneEndMargin = -1;
            this.horizontalBias = 0.5f;
            this.verticalBias = 0.5f;
            this.dimensionRatio = null;
            this.dimensionRatioValue = 0.0f;
            this.dimensionRatioSide = 1;
            this.horizontalWeight = 0.0f;
            this.verticalWeight = 0.0f;
            this.horizontalChainStyle = 0;
            this.verticalChainStyle = 0;
            this.matchConstraintDefaultWidth = 0;
            this.matchConstraintDefaultHeight = 0;
            this.matchConstraintMinWidth = 0;
            this.matchConstraintMinHeight = 0;
            this.matchConstraintMaxWidth = 0;
            this.matchConstraintMaxHeight = 0;
            this.editorAbsoluteX = -1;
            this.editorAbsoluteY = -1;
            this.orientation = -1;
            this.horizontalDimensionFixed = true;
            this.verticalDimensionFixed = true;
            this.needsBaseline = false;
            this.isGuideline = false;
            this.resolvedLeftToLeft = -1;
            this.resolvedLeftToRight = -1;
            this.resolvedRightToLeft = -1;
            this.resolvedRightToRight = -1;
            this.resolveGoneLeftMargin = -1;
            this.resolveGoneRightMargin = -1;
            this.resolvedHorizontalBias = 0.5f;
            this.widget = new ConstraintWidget();
            this.guideBegin = source.guideBegin;
            this.guideEnd = source.guideEnd;
            this.guidePercent = source.guidePercent;
            this.leftToLeft = source.leftToLeft;
            this.leftToRight = source.leftToRight;
            this.rightToLeft = source.rightToLeft;
            this.rightToRight = source.rightToRight;
            this.topToTop = source.topToTop;
            this.topToBottom = source.topToBottom;
            this.bottomToTop = source.bottomToTop;
            this.bottomToBottom = source.bottomToBottom;
            this.baselineToBaseline = source.baselineToBaseline;
            this.startToEnd = source.startToEnd;
            this.startToStart = source.startToStart;
            this.endToStart = source.endToStart;
            this.endToEnd = source.endToEnd;
            this.goneLeftMargin = source.goneLeftMargin;
            this.goneTopMargin = source.goneTopMargin;
            this.goneRightMargin = source.goneRightMargin;
            this.goneBottomMargin = source.goneBottomMargin;
            this.goneStartMargin = source.goneStartMargin;
            this.goneEndMargin = source.goneEndMargin;
            this.horizontalBias = source.horizontalBias;
            this.verticalBias = source.verticalBias;
            this.dimensionRatio = source.dimensionRatio;
            this.dimensionRatioValue = source.dimensionRatioValue;
            this.dimensionRatioSide = source.dimensionRatioSide;
            this.horizontalWeight = source.horizontalWeight;
            this.verticalWeight = source.verticalWeight;
            this.horizontalChainStyle = source.horizontalChainStyle;
            this.verticalChainStyle = source.verticalChainStyle;
            this.matchConstraintDefaultWidth = source.matchConstraintDefaultWidth;
            this.matchConstraintDefaultHeight = source.matchConstraintDefaultHeight;
            this.matchConstraintMinWidth = source.matchConstraintMinWidth;
            this.matchConstraintMaxWidth = source.matchConstraintMaxWidth;
            this.matchConstraintMinHeight = source.matchConstraintMinHeight;
            this.matchConstraintMaxHeight = source.matchConstraintMaxHeight;
            this.editorAbsoluteX = source.editorAbsoluteX;
            this.editorAbsoluteY = source.editorAbsoluteY;
            this.orientation = source.orientation;
            this.horizontalDimensionFixed = source.horizontalDimensionFixed;
            this.verticalDimensionFixed = source.verticalDimensionFixed;
            this.needsBaseline = source.needsBaseline;
            this.isGuideline = source.isGuideline;
            this.resolvedLeftToLeft = source.resolvedLeftToLeft;
            this.resolvedLeftToRight = source.resolvedLeftToRight;
            this.resolvedRightToLeft = source.resolvedRightToLeft;
            this.resolvedRightToRight = source.resolvedRightToRight;
            this.resolveGoneLeftMargin = source.resolveGoneLeftMargin;
            this.resolveGoneRightMargin = source.resolveGoneRightMargin;
            this.resolvedHorizontalBias = source.resolvedHorizontalBias;
            this.widget = source.widget;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            int i;
            int commaIndex;
            int i2 = -1;
            this.guideBegin = -1;
            this.guideEnd = -1;
            this.guidePercent = -1.0f;
            this.leftToLeft = -1;
            this.leftToRight = -1;
            this.rightToLeft = -1;
            this.rightToRight = -1;
            this.topToTop = -1;
            this.topToBottom = -1;
            this.bottomToTop = -1;
            this.bottomToBottom = -1;
            this.baselineToBaseline = -1;
            this.startToEnd = -1;
            this.startToStart = -1;
            this.endToStart = -1;
            this.endToEnd = -1;
            this.goneLeftMargin = -1;
            this.goneTopMargin = -1;
            this.goneRightMargin = -1;
            this.goneBottomMargin = -1;
            this.goneStartMargin = -1;
            this.goneEndMargin = -1;
            this.horizontalBias = 0.5f;
            this.verticalBias = 0.5f;
            this.dimensionRatio = null;
            this.dimensionRatioValue = 0.0f;
            this.dimensionRatioSide = 1;
            this.horizontalWeight = 0.0f;
            this.verticalWeight = 0.0f;
            int i3 = 0;
            this.horizontalChainStyle = 0;
            this.verticalChainStyle = 0;
            this.matchConstraintDefaultWidth = 0;
            this.matchConstraintDefaultHeight = 0;
            this.matchConstraintMinWidth = 0;
            this.matchConstraintMinHeight = 0;
            this.matchConstraintMaxWidth = 0;
            this.matchConstraintMaxHeight = 0;
            this.editorAbsoluteX = -1;
            this.editorAbsoluteY = -1;
            this.orientation = -1;
            this.horizontalDimensionFixed = true;
            this.verticalDimensionFixed = true;
            this.needsBaseline = false;
            this.isGuideline = false;
            this.resolvedLeftToLeft = -1;
            this.resolvedLeftToRight = -1;
            this.resolvedRightToLeft = -1;
            this.resolvedRightToRight = -1;
            this.resolveGoneLeftMargin = -1;
            this.resolveGoneRightMargin = -1;
            this.resolvedHorizontalBias = 0.5f;
            this.widget = new ConstraintWidget();
            TypedArray a = c.obtainStyledAttributes(attrs, C0019R.styleable.ConstraintLayout_Layout);
            int N = a.getIndexCount();
            int i4 = 0;
            while (i4 < N) {
                int attr = a.getIndex(i4);
                if (attr == C0019R.styleable.ConstraintLayout_Layout_layout_constraintLeft_toLeftOf) {
                    int resourceId = a.getResourceId(attr, this.leftToLeft);
                    this.leftToLeft = resourceId;
                    if (resourceId == i2) {
                        this.leftToLeft = a.getInt(attr, i2);
                        i = i3;
                    } else {
                        i = i3;
                    }
                } else if (attr == C0019R.styleable.ConstraintLayout_Layout_layout_constraintLeft_toRightOf) {
                    int resourceId2 = a.getResourceId(attr, this.leftToRight);
                    this.leftToRight = resourceId2;
                    if (resourceId2 == i2) {
                        this.leftToRight = a.getInt(attr, i2);
                        i = i3;
                    } else {
                        i = i3;
                    }
                } else if (attr == C0019R.styleable.ConstraintLayout_Layout_layout_constraintRight_toLeftOf) {
                    int resourceId3 = a.getResourceId(attr, this.rightToLeft);
                    this.rightToLeft = resourceId3;
                    if (resourceId3 == i2) {
                        this.rightToLeft = a.getInt(attr, i2);
                        i = i3;
                    } else {
                        i = i3;
                    }
                } else if (attr == C0019R.styleable.ConstraintLayout_Layout_layout_constraintRight_toRightOf) {
                    int resourceId4 = a.getResourceId(attr, this.rightToRight);
                    this.rightToRight = resourceId4;
                    if (resourceId4 == i2) {
                        this.rightToRight = a.getInt(attr, i2);
                        i = i3;
                    } else {
                        i = i3;
                    }
                } else if (attr == C0019R.styleable.ConstraintLayout_Layout_layout_constraintTop_toTopOf) {
                    int resourceId5 = a.getResourceId(attr, this.topToTop);
                    this.topToTop = resourceId5;
                    if (resourceId5 == i2) {
                        this.topToTop = a.getInt(attr, i2);
                        i = i3;
                    } else {
                        i = i3;
                    }
                } else if (attr == C0019R.styleable.ConstraintLayout_Layout_layout_constraintTop_toBottomOf) {
                    int resourceId6 = a.getResourceId(attr, this.topToBottom);
                    this.topToBottom = resourceId6;
                    if (resourceId6 == i2) {
                        this.topToBottom = a.getInt(attr, i2);
                        i = i3;
                    } else {
                        i = i3;
                    }
                } else if (attr == C0019R.styleable.ConstraintLayout_Layout_layout_constraintBottom_toTopOf) {
                    int resourceId7 = a.getResourceId(attr, this.bottomToTop);
                    this.bottomToTop = resourceId7;
                    if (resourceId7 == i2) {
                        this.bottomToTop = a.getInt(attr, i2);
                        i = i3;
                    } else {
                        i = i3;
                    }
                } else if (attr == C0019R.styleable.ConstraintLayout_Layout_layout_constraintBottom_toBottomOf) {
                    int resourceId8 = a.getResourceId(attr, this.bottomToBottom);
                    this.bottomToBottom = resourceId8;
                    if (resourceId8 == i2) {
                        this.bottomToBottom = a.getInt(attr, i2);
                        i = i3;
                    } else {
                        i = i3;
                    }
                } else if (attr == C0019R.styleable.ConstraintLayout_Layout_layout_constraintBaseline_toBaselineOf) {
                    int resourceId9 = a.getResourceId(attr, this.baselineToBaseline);
                    this.baselineToBaseline = resourceId9;
                    if (resourceId9 == i2) {
                        this.baselineToBaseline = a.getInt(attr, i2);
                        i = i3;
                    } else {
                        i = i3;
                    }
                } else if (attr == C0019R.styleable.ConstraintLayout_Layout_layout_editor_absoluteX) {
                    this.editorAbsoluteX = a.getDimensionPixelOffset(attr, this.editorAbsoluteX);
                    i = i3;
                } else if (attr == C0019R.styleable.ConstraintLayout_Layout_layout_editor_absoluteY) {
                    this.editorAbsoluteY = a.getDimensionPixelOffset(attr, this.editorAbsoluteY);
                    i = i3;
                } else if (attr == C0019R.styleable.ConstraintLayout_Layout_layout_constraintGuide_begin) {
                    this.guideBegin = a.getDimensionPixelOffset(attr, this.guideBegin);
                    i = i3;
                } else if (attr == C0019R.styleable.ConstraintLayout_Layout_layout_constraintGuide_end) {
                    this.guideEnd = a.getDimensionPixelOffset(attr, this.guideEnd);
                    i = i3;
                } else if (attr == C0019R.styleable.ConstraintLayout_Layout_layout_constraintGuide_percent) {
                    this.guidePercent = a.getFloat(attr, this.guidePercent);
                    i = i3;
                } else if (attr == C0019R.styleable.ConstraintLayout_Layout_android_orientation) {
                    this.orientation = a.getInt(attr, this.orientation);
                    i = i3;
                } else if (attr == C0019R.styleable.ConstraintLayout_Layout_layout_constraintStart_toEndOf) {
                    int resourceId10 = a.getResourceId(attr, this.startToEnd);
                    this.startToEnd = resourceId10;
                    if (resourceId10 == i2) {
                        this.startToEnd = a.getInt(attr, i2);
                        i = i3;
                    } else {
                        i = i3;
                    }
                } else if (attr == C0019R.styleable.ConstraintLayout_Layout_layout_constraintStart_toStartOf) {
                    int resourceId11 = a.getResourceId(attr, this.startToStart);
                    this.startToStart = resourceId11;
                    if (resourceId11 == i2) {
                        this.startToStart = a.getInt(attr, i2);
                        i = i3;
                    } else {
                        i = i3;
                    }
                } else if (attr == C0019R.styleable.ConstraintLayout_Layout_layout_constraintEnd_toStartOf) {
                    int resourceId12 = a.getResourceId(attr, this.endToStart);
                    this.endToStart = resourceId12;
                    if (resourceId12 == i2) {
                        this.endToStart = a.getInt(attr, i2);
                        i = i3;
                    } else {
                        i = i3;
                    }
                } else if (attr == C0019R.styleable.ConstraintLayout_Layout_layout_constraintEnd_toEndOf) {
                    int resourceId13 = a.getResourceId(attr, this.endToEnd);
                    this.endToEnd = resourceId13;
                    if (resourceId13 == i2) {
                        this.endToEnd = a.getInt(attr, i2);
                        i = i3;
                    } else {
                        i = i3;
                    }
                } else if (attr == C0019R.styleable.ConstraintLayout_Layout_layout_goneMarginLeft) {
                    this.goneLeftMargin = a.getDimensionPixelSize(attr, this.goneLeftMargin);
                    i = i3;
                } else if (attr == C0019R.styleable.ConstraintLayout_Layout_layout_goneMarginTop) {
                    this.goneTopMargin = a.getDimensionPixelSize(attr, this.goneTopMargin);
                    i = i3;
                } else if (attr == C0019R.styleable.ConstraintLayout_Layout_layout_goneMarginRight) {
                    this.goneRightMargin = a.getDimensionPixelSize(attr, this.goneRightMargin);
                    i = i3;
                } else if (attr == C0019R.styleable.ConstraintLayout_Layout_layout_goneMarginBottom) {
                    this.goneBottomMargin = a.getDimensionPixelSize(attr, this.goneBottomMargin);
                    i = i3;
                } else if (attr == C0019R.styleable.ConstraintLayout_Layout_layout_goneMarginStart) {
                    this.goneStartMargin = a.getDimensionPixelSize(attr, this.goneStartMargin);
                    i = i3;
                } else if (attr == C0019R.styleable.ConstraintLayout_Layout_layout_goneMarginEnd) {
                    this.goneEndMargin = a.getDimensionPixelSize(attr, this.goneEndMargin);
                    i = i3;
                } else if (attr == C0019R.styleable.ConstraintLayout_Layout_layout_constraintHorizontal_bias) {
                    this.horizontalBias = a.getFloat(attr, this.horizontalBias);
                    i = i3;
                } else if (attr == C0019R.styleable.ConstraintLayout_Layout_layout_constraintVertical_bias) {
                    this.verticalBias = a.getFloat(attr, this.verticalBias);
                    i = i3;
                } else if (attr == C0019R.styleable.ConstraintLayout_Layout_layout_constraintDimensionRatio) {
                    String string = a.getString(attr);
                    this.dimensionRatio = string;
                    this.dimensionRatioValue = Float.NaN;
                    this.dimensionRatioSide = i2;
                    if (string != null) {
                        int len = string.length();
                        int commaIndex2 = this.dimensionRatio.indexOf(44);
                        if (commaIndex2 <= 0 || commaIndex2 >= len - 1) {
                            commaIndex = 0;
                        } else {
                            String dimension = this.dimensionRatio.substring(i3, commaIndex2);
                            if (dimension.equalsIgnoreCase("W")) {
                                this.dimensionRatioSide = i3;
                            } else if (dimension.equalsIgnoreCase("H")) {
                                this.dimensionRatioSide = 1;
                            }
                            commaIndex = commaIndex2 + 1;
                        }
                        int colonIndex = this.dimensionRatio.indexOf(58);
                        if (colonIndex < 0 || colonIndex >= len - 1) {
                            String r = this.dimensionRatio.substring(commaIndex);
                            if (r.length() > 0) {
                                try {
                                    this.dimensionRatioValue = Float.parseFloat(r);
                                } catch (NumberFormatException e) {
                                }
                            }
                        } else {
                            String nominator = this.dimensionRatio.substring(commaIndex, colonIndex);
                            String denominator = this.dimensionRatio.substring(colonIndex + 1);
                            if (nominator.length() > 0 && denominator.length() > 0) {
                                try {
                                    float nominatorValue = Float.parseFloat(nominator);
                                    float denominatorValue = Float.parseFloat(denominator);
                                    if (nominatorValue > 0.0f && denominatorValue > 0.0f) {
                                        if (this.dimensionRatioSide == 1) {
                                            this.dimensionRatioValue = Math.abs(denominatorValue / nominatorValue);
                                        } else {
                                            this.dimensionRatioValue = Math.abs(nominatorValue / denominatorValue);
                                        }
                                    }
                                } catch (NumberFormatException e2) {
                                }
                            }
                        }
                        i = 0;
                    } else {
                        i = 0;
                    }
                } else if (attr == C0019R.styleable.ConstraintLayout_Layout_layout_constraintHorizontal_weight) {
                    this.horizontalWeight = a.getFloat(attr, 0.0f);
                    i = 0;
                } else if (attr == C0019R.styleable.ConstraintLayout_Layout_layout_constraintVertical_weight) {
                    this.verticalWeight = a.getFloat(attr, 0.0f);
                    i = 0;
                } else if (attr == C0019R.styleable.ConstraintLayout_Layout_layout_constraintHorizontal_chainStyle) {
                    i = 0;
                    this.horizontalChainStyle = a.getInt(attr, 0);
                } else {
                    i = 0;
                    if (attr == C0019R.styleable.ConstraintLayout_Layout_layout_constraintVertical_chainStyle) {
                        this.verticalChainStyle = a.getInt(attr, 0);
                    } else if (attr == C0019R.styleable.ConstraintLayout_Layout_layout_constraintWidth_default) {
                        this.matchConstraintDefaultWidth = a.getInt(attr, 0);
                    } else if (attr == C0019R.styleable.ConstraintLayout_Layout_layout_constraintHeight_default) {
                        this.matchConstraintDefaultHeight = a.getInt(attr, 0);
                    } else if (attr == C0019R.styleable.ConstraintLayout_Layout_layout_constraintWidth_min) {
                        this.matchConstraintMinWidth = a.getDimensionPixelSize(attr, this.matchConstraintMinWidth);
                    } else if (attr == C0019R.styleable.ConstraintLayout_Layout_layout_constraintWidth_max) {
                        this.matchConstraintMaxWidth = a.getDimensionPixelSize(attr, this.matchConstraintMaxWidth);
                    } else if (attr == C0019R.styleable.ConstraintLayout_Layout_layout_constraintHeight_min) {
                        this.matchConstraintMinHeight = a.getDimensionPixelSize(attr, this.matchConstraintMinHeight);
                    } else if (attr == C0019R.styleable.ConstraintLayout_Layout_layout_constraintHeight_max) {
                        this.matchConstraintMaxHeight = a.getDimensionPixelSize(attr, this.matchConstraintMaxHeight);
                    } else if (!(attr == C0019R.styleable.ConstraintLayout_Layout_layout_constraintLeft_creator || attr == C0019R.styleable.ConstraintLayout_Layout_layout_constraintTop_creator || attr == C0019R.styleable.ConstraintLayout_Layout_layout_constraintRight_creator || attr == C0019R.styleable.ConstraintLayout_Layout_layout_constraintBottom_creator)) {
                        int i5 = C0019R.styleable.ConstraintLayout_Layout_layout_constraintBaseline_creator;
                    }
                }
                i4++;
                i3 = i;
                i2 = -1;
            }
            a.recycle();
            validate();
        }

        public void validate() {
            this.isGuideline = false;
            this.horizontalDimensionFixed = true;
            this.verticalDimensionFixed = true;
            if (this.width == 0 || this.width == -1) {
                this.horizontalDimensionFixed = false;
            }
            if (this.height == 0 || this.height == -1) {
                this.verticalDimensionFixed = false;
            }
            if (this.guidePercent != -1.0f || this.guideBegin != -1 || this.guideEnd != -1) {
                this.isGuideline = true;
                this.horizontalDimensionFixed = true;
                this.verticalDimensionFixed = true;
                if (!(this.widget instanceof Guideline)) {
                    this.widget = new Guideline();
                }
                ((Guideline) this.widget).setOrientation(this.orientation);
            }
        }

        public LayoutParams(int width, int height) {
            super(width, height);
            this.guideBegin = -1;
            this.guideEnd = -1;
            this.guidePercent = -1.0f;
            this.leftToLeft = -1;
            this.leftToRight = -1;
            this.rightToLeft = -1;
            this.rightToRight = -1;
            this.topToTop = -1;
            this.topToBottom = -1;
            this.bottomToTop = -1;
            this.bottomToBottom = -1;
            this.baselineToBaseline = -1;
            this.startToEnd = -1;
            this.startToStart = -1;
            this.endToStart = -1;
            this.endToEnd = -1;
            this.goneLeftMargin = -1;
            this.goneTopMargin = -1;
            this.goneRightMargin = -1;
            this.goneBottomMargin = -1;
            this.goneStartMargin = -1;
            this.goneEndMargin = -1;
            this.horizontalBias = 0.5f;
            this.verticalBias = 0.5f;
            this.dimensionRatio = null;
            this.dimensionRatioValue = 0.0f;
            this.dimensionRatioSide = 1;
            this.horizontalWeight = 0.0f;
            this.verticalWeight = 0.0f;
            this.horizontalChainStyle = 0;
            this.verticalChainStyle = 0;
            this.matchConstraintDefaultWidth = 0;
            this.matchConstraintDefaultHeight = 0;
            this.matchConstraintMinWidth = 0;
            this.matchConstraintMinHeight = 0;
            this.matchConstraintMaxWidth = 0;
            this.matchConstraintMaxHeight = 0;
            this.editorAbsoluteX = -1;
            this.editorAbsoluteY = -1;
            this.orientation = -1;
            this.horizontalDimensionFixed = true;
            this.verticalDimensionFixed = true;
            this.needsBaseline = false;
            this.isGuideline = false;
            this.resolvedLeftToLeft = -1;
            this.resolvedLeftToRight = -1;
            this.resolvedRightToLeft = -1;
            this.resolvedRightToRight = -1;
            this.resolveGoneLeftMargin = -1;
            this.resolveGoneRightMargin = -1;
            this.resolvedHorizontalBias = 0.5f;
            this.widget = new ConstraintWidget();
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
            this.guideBegin = -1;
            this.guideEnd = -1;
            this.guidePercent = -1.0f;
            this.leftToLeft = -1;
            this.leftToRight = -1;
            this.rightToLeft = -1;
            this.rightToRight = -1;
            this.topToTop = -1;
            this.topToBottom = -1;
            this.bottomToTop = -1;
            this.bottomToBottom = -1;
            this.baselineToBaseline = -1;
            this.startToEnd = -1;
            this.startToStart = -1;
            this.endToStart = -1;
            this.endToEnd = -1;
            this.goneLeftMargin = -1;
            this.goneTopMargin = -1;
            this.goneRightMargin = -1;
            this.goneBottomMargin = -1;
            this.goneStartMargin = -1;
            this.goneEndMargin = -1;
            this.horizontalBias = 0.5f;
            this.verticalBias = 0.5f;
            this.dimensionRatio = null;
            this.dimensionRatioValue = 0.0f;
            this.dimensionRatioSide = 1;
            this.horizontalWeight = 0.0f;
            this.verticalWeight = 0.0f;
            this.horizontalChainStyle = 0;
            this.verticalChainStyle = 0;
            this.matchConstraintDefaultWidth = 0;
            this.matchConstraintDefaultHeight = 0;
            this.matchConstraintMinWidth = 0;
            this.matchConstraintMinHeight = 0;
            this.matchConstraintMaxWidth = 0;
            this.matchConstraintMaxHeight = 0;
            this.editorAbsoluteX = -1;
            this.editorAbsoluteY = -1;
            this.orientation = -1;
            this.horizontalDimensionFixed = true;
            this.verticalDimensionFixed = true;
            this.needsBaseline = false;
            this.isGuideline = false;
            this.resolvedLeftToLeft = -1;
            this.resolvedLeftToRight = -1;
            this.resolvedRightToLeft = -1;
            this.resolvedRightToRight = -1;
            this.resolveGoneLeftMargin = -1;
            this.resolveGoneRightMargin = -1;
            this.resolvedHorizontalBias = 0.5f;
            this.widget = new ConstraintWidget();
        }

        public void resolveLayoutDirection(int layoutDirection) {
            super.resolveLayoutDirection(layoutDirection);
            this.resolvedRightToLeft = -1;
            this.resolvedRightToRight = -1;
            this.resolvedLeftToLeft = -1;
            this.resolvedLeftToRight = -1;
            this.resolveGoneLeftMargin = -1;
            this.resolveGoneRightMargin = -1;
            this.resolveGoneLeftMargin = this.goneLeftMargin;
            this.resolveGoneRightMargin = this.goneRightMargin;
            this.resolvedHorizontalBias = this.horizontalBias;
            boolean isRtl = true;
            if (1 != getLayoutDirection()) {
                isRtl = false;
            }
            if (isRtl) {
                int i = this.startToEnd;
                if (i != -1) {
                    this.resolvedRightToLeft = i;
                } else {
                    int i2 = this.startToStart;
                    if (i2 != -1) {
                        this.resolvedRightToRight = i2;
                    }
                }
                int i3 = this.endToStart;
                if (i3 != -1) {
                    this.resolvedLeftToRight = i3;
                }
                int i4 = this.endToEnd;
                if (i4 != -1) {
                    this.resolvedLeftToLeft = i4;
                }
                int i5 = this.goneStartMargin;
                if (i5 != -1) {
                    this.resolveGoneRightMargin = i5;
                }
                int i6 = this.goneEndMargin;
                if (i6 != -1) {
                    this.resolveGoneLeftMargin = i6;
                }
                this.resolvedHorizontalBias = 1.0f - this.horizontalBias;
            } else {
                int i7 = this.startToEnd;
                if (i7 != -1) {
                    this.resolvedLeftToRight = i7;
                }
                int i8 = this.startToStart;
                if (i8 != -1) {
                    this.resolvedLeftToLeft = i8;
                }
                int i9 = this.endToStart;
                if (i9 != -1) {
                    this.resolvedRightToLeft = i9;
                }
                int i10 = this.endToEnd;
                if (i10 != -1) {
                    this.resolvedRightToRight = i10;
                }
                int i11 = this.goneStartMargin;
                if (i11 != -1) {
                    this.resolveGoneLeftMargin = i11;
                }
                int i12 = this.goneEndMargin;
                if (i12 != -1) {
                    this.resolveGoneRightMargin = i12;
                }
            }
            if (this.endToStart == -1 && this.endToEnd == -1) {
                int i13 = this.rightToLeft;
                if (i13 != -1) {
                    this.resolvedRightToLeft = i13;
                } else {
                    int i14 = this.rightToRight;
                    if (i14 != -1) {
                        this.resolvedRightToRight = i14;
                    }
                }
            }
            if (this.startToStart == -1 && this.startToEnd == -1) {
                int i15 = this.leftToLeft;
                if (i15 != -1) {
                    this.resolvedLeftToLeft = i15;
                    return;
                }
                int i16 = this.leftToRight;
                if (i16 != -1) {
                    this.resolvedLeftToRight = i16;
                }
            }
        }
    }

    public void requestLayout() {
        super.requestLayout();
        this.mDirtyHierarchy = true;
    }
}
