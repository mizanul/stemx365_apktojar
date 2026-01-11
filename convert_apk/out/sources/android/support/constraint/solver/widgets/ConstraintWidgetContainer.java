package android.support.constraint.solver.widgets;

import android.support.constraint.solver.LinearSystem;
import android.support.constraint.solver.widgets.ConstraintAnchor;
import android.support.constraint.solver.widgets.ConstraintWidget;
import java.util.ArrayList;
import java.util.Arrays;

public class ConstraintWidgetContainer extends WidgetContainer {
    static boolean ALLOW_ROOT_GROUP = true;
    private static final int CHAIN_FIRST = 0;
    private static final int CHAIN_FIRST_VISIBLE = 2;
    private static final int CHAIN_LAST = 1;
    private static final int CHAIN_LAST_VISIBLE = 3;
    private static final boolean DEBUG = false;
    private static final boolean DEBUG_LAYOUT = false;
    private static final boolean DEBUG_OPTIMIZE = false;
    private static final int FLAG_CHAIN_DANGLING = 1;
    private static final int FLAG_CHAIN_OPTIMIZE = 0;
    private static final int FLAG_RECOMPUTE_BOUNDS = 2;
    private static final int MAX_ITERATIONS = 8;
    public static final int OPTIMIZATION_ALL = 2;
    public static final int OPTIMIZATION_BASIC = 4;
    public static final int OPTIMIZATION_CHAIN = 8;
    public static final int OPTIMIZATION_NONE = 1;
    private static final boolean USE_SNAPSHOT = true;
    private static final boolean USE_THREAD = false;
    private boolean[] flags = new boolean[3];
    protected LinearSystem mBackgroundSystem = null;
    private ConstraintWidget[] mChainEnds = new ConstraintWidget[4];
    private boolean mHeightMeasuredTooSmall = false;
    private ConstraintWidget[] mHorizontalChainsArray = new ConstraintWidget[4];
    private int mHorizontalChainsSize = 0;
    private ConstraintWidget[] mMatchConstraintsChainedWidgets = new ConstraintWidget[4];
    private int mOptimizationLevel = 2;
    int mPaddingBottom;
    int mPaddingLeft;
    int mPaddingRight;
    int mPaddingTop;
    private Snapshot mSnapshot;
    protected LinearSystem mSystem = new LinearSystem();
    private ConstraintWidget[] mVerticalChainsArray = new ConstraintWidget[4];
    private int mVerticalChainsSize = 0;
    private boolean mWidthMeasuredTooSmall = false;
    int mWrapHeight;
    int mWrapWidth;

    public ConstraintWidgetContainer() {
    }

    public ConstraintWidgetContainer(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public ConstraintWidgetContainer(int width, int height) {
        super(width, height);
    }

    public void setOptimizationLevel(int value) {
        this.mOptimizationLevel = value;
    }

    public String getType() {
        return "ConstraintLayout";
    }

    public void reset() {
        this.mSystem.reset();
        this.mPaddingLeft = 0;
        this.mPaddingRight = 0;
        this.mPaddingTop = 0;
        this.mPaddingBottom = 0;
        super.reset();
    }

    public boolean isWidthMeasuredTooSmall() {
        return this.mWidthMeasuredTooSmall;
    }

    public boolean isHeightMeasuredTooSmall() {
        return this.mHeightMeasuredTooSmall;
    }

    public static ConstraintWidgetContainer createContainer(ConstraintWidgetContainer container, String name, ArrayList<ConstraintWidget> widgets, int padding) {
        Rectangle bounds = getBounds(widgets);
        if (bounds.width == 0 || bounds.height == 0) {
            return null;
        }
        if (padding > 0) {
            int maxPadding = Math.min(bounds.f22x, bounds.f23y);
            if (padding > maxPadding) {
                padding = maxPadding;
            }
            bounds.grow(padding, padding);
        }
        container.setOrigin(bounds.f22x, bounds.f23y);
        container.setDimension(bounds.width, bounds.height);
        container.setDebugName(name);
        ConstraintWidget parent = widgets.get(0).getParent();
        int widgetsSize = widgets.size();
        for (int i = 0; i < widgetsSize; i++) {
            ConstraintWidget widget = widgets.get(i);
            if (widget.getParent() == parent) {
                container.add(widget);
                widget.setX(widget.getX() - bounds.f22x);
                widget.setY(widget.getY() - bounds.f23y);
            }
        }
        return container;
    }

    public boolean addChildrenToSolver(LinearSystem system, int group) {
        addToSolver(system, group);
        int count = this.mChildren.size();
        boolean setMatchParent = false;
        int i = this.mOptimizationLevel;
        if (i != 2 && i != 4) {
            setMatchParent = true;
        } else if (optimize(system)) {
            return false;
        }
        for (int i2 = 0; i2 < count; i2++) {
            ConstraintWidget widget = (ConstraintWidget) this.mChildren.get(i2);
            if (widget instanceof ConstraintWidgetContainer) {
                ConstraintWidget.DimensionBehaviour horizontalBehaviour = widget.mHorizontalDimensionBehaviour;
                ConstraintWidget.DimensionBehaviour verticalBehaviour = widget.mVerticalDimensionBehaviour;
                if (horizontalBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                    widget.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
                }
                if (verticalBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                    widget.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
                }
                widget.addToSolver(system, group);
                if (horizontalBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                    widget.setHorizontalDimensionBehaviour(horizontalBehaviour);
                }
                if (verticalBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                    widget.setVerticalDimensionBehaviour(verticalBehaviour);
                }
            } else {
                if (setMatchParent) {
                    Optimizer.checkMatchParent(this, system, widget);
                }
                widget.addToSolver(system, group);
            }
        }
        if (this.mHorizontalChainsSize > 0) {
            applyHorizontalChain(system);
        }
        if (this.mVerticalChainsSize <= 0) {
            return true;
        }
        applyVerticalChain(system);
        return true;
    }

    private boolean optimize(LinearSystem system) {
        int count = this.mChildren.size();
        boolean done = false;
        int dv = 0;
        int dh = 0;
        int n = 0;
        for (int i = 0; i < count; i++) {
            ConstraintWidget widget = (ConstraintWidget) this.mChildren.get(i);
            widget.mHorizontalResolution = -1;
            widget.mVerticalResolution = -1;
            if (widget.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || widget.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                widget.mHorizontalResolution = 1;
                widget.mVerticalResolution = 1;
            }
        }
        while (!done) {
            int prev = dv;
            int preh = dh;
            dv = 0;
            dh = 0;
            n++;
            for (int i2 = 0; i2 < count; i2++) {
                ConstraintWidget widget2 = (ConstraintWidget) this.mChildren.get(i2);
                if (widget2.mHorizontalResolution == -1) {
                    if (this.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                        widget2.mHorizontalResolution = 1;
                    } else {
                        Optimizer.checkHorizontalSimpleDependency(this, system, widget2);
                    }
                }
                if (widget2.mVerticalResolution == -1) {
                    if (this.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                        widget2.mVerticalResolution = 1;
                    } else {
                        Optimizer.checkVerticalSimpleDependency(this, system, widget2);
                    }
                }
                if (widget2.mVerticalResolution == -1) {
                    dv++;
                }
                if (widget2.mHorizontalResolution == -1) {
                    dh++;
                }
            }
            if (dv == 0 && dh == 0) {
                done = true;
            } else if (prev == dv && preh == dh) {
                done = true;
            }
        }
        int sh = 0;
        int sv = 0;
        for (int i3 = 0; i3 < count; i3++) {
            ConstraintWidget widget3 = (ConstraintWidget) this.mChildren.get(i3);
            if (widget3.mHorizontalResolution == 1 || widget3.mHorizontalResolution == -1) {
                sh++;
            }
            if (widget3.mVerticalResolution == 1 || widget3.mVerticalResolution == -1) {
                sv++;
            }
        }
        if (sh == 0 && sv == 0) {
            return true;
        }
        return false;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r19v0, resolved type: android.support.constraint.solver.SolverVariable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r19v1, resolved type: android.support.constraint.solver.SolverVariable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r19v3, resolved type: android.support.constraint.solver.widgets.ConstraintWidget} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r19v4, resolved type: android.support.constraint.solver.widgets.ConstraintWidget} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r19v5, resolved type: android.support.constraint.solver.widgets.ConstraintWidget} */
    /* JADX WARNING: type inference failed for: r7v15, types: [android.support.constraint.solver.SolverVariable] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:206:0x054b  */
    /* JADX WARNING: Removed duplicated region for block: B:243:0x054d A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void applyHorizontalChain(android.support.constraint.solver.LinearSystem r38) {
        /*
            r37 = this;
            r6 = r37
            r15 = r38
            r0 = 0
            r14 = r0
        L_0x0006:
            int r0 = r6.mHorizontalChainsSize
            if (r14 >= r0) goto L_0x05d5
            android.support.constraint.solver.widgets.ConstraintWidget[] r0 = r6.mHorizontalChainsArray
            r13 = r0[r14]
            android.support.constraint.solver.widgets.ConstraintWidget[] r2 = r6.mChainEnds
            r3 = r0[r14]
            r4 = 0
            boolean[] r5 = r6.flags
            r0 = r37
            r1 = r38
            int r0 = r0.countMatchConstraintsChainedWidgets(r1, r2, r3, r4, r5)
            android.support.constraint.solver.widgets.ConstraintWidget[] r1 = r6.mChainEnds
            r2 = 2
            r1 = r1[r2]
            if (r1 != 0) goto L_0x0029
            r24 = r14
            r0 = r15
            goto L_0x05ce
        L_0x0029:
            boolean[] r3 = r6.flags
            r4 = 1
            boolean r3 = r3[r4]
            if (r3 == 0) goto L_0x0059
            int r2 = r13.getDrawX()
        L_0x0034:
            if (r1 == 0) goto L_0x0054
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r1.mLeft
            android.support.constraint.solver.SolverVariable r3 = r3.mSolverVariable
            r15.addEquality(r3, r2)
            android.support.constraint.solver.widgets.ConstraintWidget r3 = r1.mHorizontalNextWidget
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r1.mLeft
            int r4 = r4.getMargin()
            int r5 = r1.getWidth()
            int r4 = r4 + r5
            android.support.constraint.solver.widgets.ConstraintAnchor r5 = r1.mRight
            int r5 = r5.getMargin()
            int r4 = r4 + r5
            int r2 = r2 + r4
            r1 = r3
            goto L_0x0034
        L_0x0054:
            r24 = r14
            r0 = r15
            goto L_0x05ce
        L_0x0059:
            int r3 = r13.mHorizontalChainStyle
            r5 = 0
            if (r3 != 0) goto L_0x0060
            r3 = r4
            goto L_0x0061
        L_0x0060:
            r3 = r5
        L_0x0061:
            int r7 = r13.mHorizontalChainStyle
            if (r7 != r2) goto L_0x0067
            r7 = r4
            goto L_0x0068
        L_0x0067:
            r7 = r5
        L_0x0068:
            r16 = r7
            r12 = r13
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r7 = r6.mHorizontalDimensionBehaviour
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r8 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            if (r7 != r8) goto L_0x0073
            r7 = r4
            goto L_0x0074
        L_0x0073:
            r7 = r5
        L_0x0074:
            r17 = r7
            int r7 = r6.mOptimizationLevel
            if (r7 == r2) goto L_0x007e
            r8 = 8
            if (r7 != r8) goto L_0x0098
        L_0x007e:
            boolean[] r7 = r6.flags
            boolean r7 = r7[r5]
            if (r7 == 0) goto L_0x0098
            boolean r7 = r12.mHorizontalChainFixedPosition
            if (r7 == 0) goto L_0x0098
            if (r16 != 0) goto L_0x0098
            if (r17 != 0) goto L_0x0098
            int r7 = r13.mHorizontalChainStyle
            if (r7 != 0) goto L_0x0098
            android.support.constraint.solver.widgets.Optimizer.applyDirectResolutionHorizontalChain(r6, r15, r0, r12)
            r24 = r14
            r0 = r15
            goto L_0x05ce
        L_0x0098:
            r11 = 3
            if (r0 == 0) goto L_0x0391
            if (r16 == 0) goto L_0x00a5
            r34 = r0
            r33 = r13
            r32 = r14
            goto L_0x0397
        L_0x00a5:
            r7 = 0
            r8 = 0
        L_0x00a7:
            if (r1 == 0) goto L_0x016a
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r9 = r1.mHorizontalDimensionBehaviour
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r10 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r9 == r10) goto L_0x0124
            android.support.constraint.solver.widgets.ConstraintAnchor r9 = r1.mLeft
            int r9 = r9.getMargin()
            if (r7 == 0) goto L_0x00be
            android.support.constraint.solver.widgets.ConstraintAnchor r10 = r7.mRight
            int r10 = r10.getMargin()
            int r9 = r9 + r10
        L_0x00be:
            r10 = 3
            android.support.constraint.solver.widgets.ConstraintAnchor r2 = r1.mLeft
            android.support.constraint.solver.widgets.ConstraintAnchor r2 = r2.mTarget
            android.support.constraint.solver.widgets.ConstraintWidget r2 = r2.mOwner
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r2 = r2.mHorizontalDimensionBehaviour
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r4 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r2 != r4) goto L_0x00cc
            r10 = 2
        L_0x00cc:
            android.support.constraint.solver.widgets.ConstraintAnchor r2 = r1.mLeft
            android.support.constraint.solver.SolverVariable r2 = r2.mSolverVariable
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r1.mLeft
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.mTarget
            android.support.constraint.solver.SolverVariable r4 = r4.mSolverVariable
            r15.addGreaterThan(r2, r4, r9, r10)
            android.support.constraint.solver.widgets.ConstraintAnchor r2 = r1.mRight
            int r2 = r2.getMargin()
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r1.mRight
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.mTarget
            android.support.constraint.solver.widgets.ConstraintWidget r4 = r4.mOwner
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.mLeft
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.mTarget
            if (r4 == 0) goto L_0x0106
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r1.mRight
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.mTarget
            android.support.constraint.solver.widgets.ConstraintWidget r4 = r4.mOwner
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.mLeft
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.mTarget
            android.support.constraint.solver.widgets.ConstraintWidget r4 = r4.mOwner
            if (r4 != r1) goto L_0x0106
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r1.mRight
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.mTarget
            android.support.constraint.solver.widgets.ConstraintWidget r4 = r4.mOwner
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.mLeft
            int r4 = r4.getMargin()
            int r2 = r2 + r4
        L_0x0106:
            r4 = 3
            android.support.constraint.solver.widgets.ConstraintAnchor r9 = r1.mRight
            android.support.constraint.solver.widgets.ConstraintAnchor r9 = r9.mTarget
            android.support.constraint.solver.widgets.ConstraintWidget r9 = r9.mOwner
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r9 = r9.mHorizontalDimensionBehaviour
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r10 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r9 != r10) goto L_0x0114
            r4 = 2
        L_0x0114:
            android.support.constraint.solver.widgets.ConstraintAnchor r9 = r1.mRight
            android.support.constraint.solver.SolverVariable r9 = r9.mSolverVariable
            android.support.constraint.solver.widgets.ConstraintAnchor r10 = r1.mRight
            android.support.constraint.solver.widgets.ConstraintAnchor r10 = r10.mTarget
            android.support.constraint.solver.SolverVariable r10 = r10.mSolverVariable
            int r5 = -r2
            r15.addLowerThan(r9, r10, r5, r4)
            r10 = 1
            goto L_0x0162
        L_0x0124:
            float r2 = r1.mHorizontalWeight
            float r8 = r8 + r2
            r2 = 0
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r1.mRight
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.mTarget
            if (r4 == 0) goto L_0x0147
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r1.mRight
            int r2 = r4.getMargin()
            android.support.constraint.solver.widgets.ConstraintWidget[] r4 = r6.mChainEnds
            r4 = r4[r11]
            if (r1 == r4) goto L_0x0147
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r1.mRight
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.mTarget
            android.support.constraint.solver.widgets.ConstraintWidget r4 = r4.mOwner
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.mLeft
            int r4 = r4.getMargin()
            int r2 = r2 + r4
        L_0x0147:
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r1.mRight
            android.support.constraint.solver.SolverVariable r4 = r4.mSolverVariable
            android.support.constraint.solver.widgets.ConstraintAnchor r5 = r1.mLeft
            android.support.constraint.solver.SolverVariable r5 = r5.mSolverVariable
            r9 = 0
            r10 = 1
            r15.addGreaterThan(r4, r5, r9, r10)
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r1.mRight
            android.support.constraint.solver.SolverVariable r4 = r4.mSolverVariable
            android.support.constraint.solver.widgets.ConstraintAnchor r5 = r1.mRight
            android.support.constraint.solver.widgets.ConstraintAnchor r5 = r5.mTarget
            android.support.constraint.solver.SolverVariable r5 = r5.mSolverVariable
            int r9 = -r2
            r15.addLowerThan(r4, r5, r9, r10)
        L_0x0162:
            r7 = r1
            android.support.constraint.solver.widgets.ConstraintWidget r1 = r1.mHorizontalNextWidget
            r4 = r10
            r2 = 2
            r5 = 0
            goto L_0x00a7
        L_0x016a:
            r10 = r4
            if (r0 != r10) goto L_0x0201
            android.support.constraint.solver.widgets.ConstraintWidget[] r2 = r6.mMatchConstraintsChainedWidgets
            r9 = 0
            r2 = r2[r9]
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r2.mLeft
            int r4 = r4.getMargin()
            android.support.constraint.solver.widgets.ConstraintAnchor r5 = r2.mLeft
            android.support.constraint.solver.widgets.ConstraintAnchor r5 = r5.mTarget
            if (r5 == 0) goto L_0x0187
            android.support.constraint.solver.widgets.ConstraintAnchor r5 = r2.mLeft
            android.support.constraint.solver.widgets.ConstraintAnchor r5 = r5.mTarget
            int r5 = r5.getMargin()
            int r4 = r4 + r5
        L_0x0187:
            android.support.constraint.solver.widgets.ConstraintAnchor r5 = r2.mRight
            int r5 = r5.getMargin()
            android.support.constraint.solver.widgets.ConstraintAnchor r9 = r2.mRight
            android.support.constraint.solver.widgets.ConstraintAnchor r9 = r9.mTarget
            if (r9 == 0) goto L_0x019c
            android.support.constraint.solver.widgets.ConstraintAnchor r9 = r2.mRight
            android.support.constraint.solver.widgets.ConstraintAnchor r9 = r9.mTarget
            int r9 = r9.getMargin()
            int r5 = r5 + r9
        L_0x019c:
            android.support.constraint.solver.widgets.ConstraintAnchor r9 = r12.mRight
            android.support.constraint.solver.widgets.ConstraintAnchor r9 = r9.mTarget
            android.support.constraint.solver.SolverVariable r9 = r9.mSolverVariable
            android.support.constraint.solver.widgets.ConstraintWidget[] r10 = r6.mChainEnds
            r11 = r10[r11]
            if (r2 != r11) goto L_0x01b2
            r11 = 1
            r10 = r10[r11]
            android.support.constraint.solver.widgets.ConstraintAnchor r10 = r10.mRight
            android.support.constraint.solver.widgets.ConstraintAnchor r10 = r10.mTarget
            android.support.constraint.solver.SolverVariable r9 = r10.mSolverVariable
            goto L_0x01b3
        L_0x01b2:
            r11 = 1
        L_0x01b3:
            int r10 = r2.mMatchConstraintDefaultWidth
            if (r10 != r11) goto L_0x01e2
            android.support.constraint.solver.widgets.ConstraintAnchor r10 = r12.mLeft
            android.support.constraint.solver.SolverVariable r10 = r10.mSolverVariable
            android.support.constraint.solver.widgets.ConstraintAnchor r11 = r12.mLeft
            android.support.constraint.solver.widgets.ConstraintAnchor r11 = r11.mTarget
            android.support.constraint.solver.SolverVariable r11 = r11.mSolverVariable
            r30 = r1
            r1 = 1
            r15.addGreaterThan(r10, r11, r4, r1)
            android.support.constraint.solver.widgets.ConstraintAnchor r10 = r12.mRight
            android.support.constraint.solver.SolverVariable r10 = r10.mSolverVariable
            int r11 = -r5
            r15.addLowerThan(r10, r9, r11, r1)
            android.support.constraint.solver.widgets.ConstraintAnchor r1 = r12.mRight
            android.support.constraint.solver.SolverVariable r1 = r1.mSolverVariable
            android.support.constraint.solver.widgets.ConstraintAnchor r10 = r12.mLeft
            android.support.constraint.solver.SolverVariable r10 = r10.mSolverVariable
            int r11 = r12.getWidth()
            r31 = r7
            r7 = 2
            r15.addEquality(r1, r10, r11, r7)
            goto L_0x01fc
        L_0x01e2:
            r30 = r1
            r31 = r7
            android.support.constraint.solver.widgets.ConstraintAnchor r1 = r2.mLeft
            android.support.constraint.solver.SolverVariable r1 = r1.mSolverVariable
            android.support.constraint.solver.widgets.ConstraintAnchor r7 = r2.mLeft
            android.support.constraint.solver.widgets.ConstraintAnchor r7 = r7.mTarget
            android.support.constraint.solver.SolverVariable r7 = r7.mSolverVariable
            r10 = 1
            r15.addEquality(r1, r7, r4, r10)
            android.support.constraint.solver.widgets.ConstraintAnchor r1 = r2.mRight
            android.support.constraint.solver.SolverVariable r1 = r1.mSolverVariable
            int r7 = -r5
            r15.addEquality(r1, r9, r7, r10)
        L_0x01fc:
            r24 = r14
            r0 = r15
            goto L_0x05ce
        L_0x0201:
            r30 = r1
            r31 = r7
            r9 = 0
            r1 = 0
        L_0x0207:
            int r2 = r0 + -1
            if (r1 >= r2) goto L_0x0386
            android.support.constraint.solver.widgets.ConstraintWidget[] r2 = r6.mMatchConstraintsChainedWidgets
            r4 = r2[r1]
            int r5 = r1 + 1
            r2 = r2[r5]
            android.support.constraint.solver.widgets.ConstraintAnchor r5 = r4.mLeft
            android.support.constraint.solver.SolverVariable r5 = r5.mSolverVariable
            android.support.constraint.solver.widgets.ConstraintAnchor r7 = r4.mRight
            android.support.constraint.solver.SolverVariable r7 = r7.mSolverVariable
            android.support.constraint.solver.widgets.ConstraintAnchor r10 = r2.mLeft
            android.support.constraint.solver.SolverVariable r10 = r10.mSolverVariable
            android.support.constraint.solver.widgets.ConstraintAnchor r9 = r2.mRight
            android.support.constraint.solver.SolverVariable r9 = r9.mSolverVariable
            r18 = r9
            android.support.constraint.solver.widgets.ConstraintWidget[] r9 = r6.mChainEnds
            r32 = r14
            r14 = r9[r11]
            if (r2 != r14) goto L_0x0235
            r14 = 1
            r9 = r9[r14]
            android.support.constraint.solver.widgets.ConstraintAnchor r9 = r9.mRight
            android.support.constraint.solver.SolverVariable r9 = r9.mSolverVariable
            goto L_0x0237
        L_0x0235:
            r9 = r18
        L_0x0237:
            android.support.constraint.solver.widgets.ConstraintAnchor r14 = r4.mLeft
            int r14 = r14.getMargin()
            android.support.constraint.solver.widgets.ConstraintAnchor r11 = r4.mLeft
            android.support.constraint.solver.widgets.ConstraintAnchor r11 = r11.mTarget
            if (r11 == 0) goto L_0x026a
            android.support.constraint.solver.widgets.ConstraintAnchor r11 = r4.mLeft
            android.support.constraint.solver.widgets.ConstraintAnchor r11 = r11.mTarget
            android.support.constraint.solver.widgets.ConstraintWidget r11 = r11.mOwner
            android.support.constraint.solver.widgets.ConstraintAnchor r11 = r11.mRight
            android.support.constraint.solver.widgets.ConstraintAnchor r11 = r11.mTarget
            if (r11 == 0) goto L_0x026a
            android.support.constraint.solver.widgets.ConstraintAnchor r11 = r4.mLeft
            android.support.constraint.solver.widgets.ConstraintAnchor r11 = r11.mTarget
            android.support.constraint.solver.widgets.ConstraintWidget r11 = r11.mOwner
            android.support.constraint.solver.widgets.ConstraintAnchor r11 = r11.mRight
            android.support.constraint.solver.widgets.ConstraintAnchor r11 = r11.mTarget
            android.support.constraint.solver.widgets.ConstraintWidget r11 = r11.mOwner
            if (r11 != r4) goto L_0x026a
            android.support.constraint.solver.widgets.ConstraintAnchor r11 = r4.mLeft
            android.support.constraint.solver.widgets.ConstraintAnchor r11 = r11.mTarget
            android.support.constraint.solver.widgets.ConstraintWidget r11 = r11.mOwner
            android.support.constraint.solver.widgets.ConstraintAnchor r11 = r11.mRight
            int r11 = r11.getMargin()
            int r14 = r14 + r11
        L_0x026a:
            android.support.constraint.solver.widgets.ConstraintAnchor r11 = r4.mLeft
            android.support.constraint.solver.widgets.ConstraintAnchor r11 = r11.mTarget
            android.support.constraint.solver.SolverVariable r11 = r11.mSolverVariable
            r33 = r13
            r13 = 2
            r15.addGreaterThan(r5, r11, r14, r13)
            android.support.constraint.solver.widgets.ConstraintAnchor r11 = r4.mRight
            int r11 = r11.getMargin()
            android.support.constraint.solver.widgets.ConstraintAnchor r13 = r4.mRight
            android.support.constraint.solver.widgets.ConstraintAnchor r13 = r13.mTarget
            if (r13 == 0) goto L_0x0299
            android.support.constraint.solver.widgets.ConstraintWidget r13 = r4.mHorizontalNextWidget
            if (r13 == 0) goto L_0x0299
            android.support.constraint.solver.widgets.ConstraintWidget r13 = r4.mHorizontalNextWidget
            android.support.constraint.solver.widgets.ConstraintAnchor r13 = r13.mLeft
            android.support.constraint.solver.widgets.ConstraintAnchor r13 = r13.mTarget
            if (r13 == 0) goto L_0x0297
            android.support.constraint.solver.widgets.ConstraintWidget r13 = r4.mHorizontalNextWidget
            android.support.constraint.solver.widgets.ConstraintAnchor r13 = r13.mLeft
            int r13 = r13.getMargin()
            goto L_0x0298
        L_0x0297:
            r13 = 0
        L_0x0298:
            int r11 = r11 + r13
        L_0x0299:
            android.support.constraint.solver.widgets.ConstraintAnchor r13 = r4.mRight
            android.support.constraint.solver.widgets.ConstraintAnchor r13 = r13.mTarget
            android.support.constraint.solver.SolverVariable r13 = r13.mSolverVariable
            int r14 = -r11
            r18 = r11
            r11 = 2
            r15.addLowerThan(r7, r13, r14, r11)
            int r11 = r1 + 1
            int r13 = r0 + -1
            if (r11 != r13) goto L_0x0334
            android.support.constraint.solver.widgets.ConstraintAnchor r11 = r2.mLeft
            int r11 = r11.getMargin()
            android.support.constraint.solver.widgets.ConstraintAnchor r13 = r2.mLeft
            android.support.constraint.solver.widgets.ConstraintAnchor r13 = r13.mTarget
            if (r13 == 0) goto L_0x02df
            android.support.constraint.solver.widgets.ConstraintAnchor r13 = r2.mLeft
            android.support.constraint.solver.widgets.ConstraintAnchor r13 = r13.mTarget
            android.support.constraint.solver.widgets.ConstraintWidget r13 = r13.mOwner
            android.support.constraint.solver.widgets.ConstraintAnchor r13 = r13.mRight
            android.support.constraint.solver.widgets.ConstraintAnchor r13 = r13.mTarget
            if (r13 == 0) goto L_0x02df
            android.support.constraint.solver.widgets.ConstraintAnchor r13 = r2.mLeft
            android.support.constraint.solver.widgets.ConstraintAnchor r13 = r13.mTarget
            android.support.constraint.solver.widgets.ConstraintWidget r13 = r13.mOwner
            android.support.constraint.solver.widgets.ConstraintAnchor r13 = r13.mRight
            android.support.constraint.solver.widgets.ConstraintAnchor r13 = r13.mTarget
            android.support.constraint.solver.widgets.ConstraintWidget r13 = r13.mOwner
            if (r13 != r2) goto L_0x02df
            android.support.constraint.solver.widgets.ConstraintAnchor r13 = r2.mLeft
            android.support.constraint.solver.widgets.ConstraintAnchor r13 = r13.mTarget
            android.support.constraint.solver.widgets.ConstraintWidget r13 = r13.mOwner
            android.support.constraint.solver.widgets.ConstraintAnchor r13 = r13.mRight
            int r13 = r13.getMargin()
            int r11 = r11 + r13
        L_0x02df:
            android.support.constraint.solver.widgets.ConstraintAnchor r13 = r2.mLeft
            android.support.constraint.solver.widgets.ConstraintAnchor r13 = r13.mTarget
            android.support.constraint.solver.SolverVariable r13 = r13.mSolverVariable
            r14 = 2
            r15.addGreaterThan(r10, r13, r11, r14)
            android.support.constraint.solver.widgets.ConstraintAnchor r13 = r2.mRight
            android.support.constraint.solver.widgets.ConstraintWidget[] r14 = r6.mChainEnds
            r34 = r0
            r18 = 3
            r0 = r14[r18]
            if (r2 != r0) goto L_0x02fa
            r0 = 1
            r14 = r14[r0]
            android.support.constraint.solver.widgets.ConstraintAnchor r13 = r14.mRight
        L_0x02fa:
            int r0 = r13.getMargin()
            android.support.constraint.solver.widgets.ConstraintAnchor r11 = r13.mTarget
            if (r11 == 0) goto L_0x0325
            android.support.constraint.solver.widgets.ConstraintAnchor r11 = r13.mTarget
            android.support.constraint.solver.widgets.ConstraintWidget r11 = r11.mOwner
            android.support.constraint.solver.widgets.ConstraintAnchor r11 = r11.mLeft
            android.support.constraint.solver.widgets.ConstraintAnchor r11 = r11.mTarget
            if (r11 == 0) goto L_0x0325
            android.support.constraint.solver.widgets.ConstraintAnchor r11 = r13.mTarget
            android.support.constraint.solver.widgets.ConstraintWidget r11 = r11.mOwner
            android.support.constraint.solver.widgets.ConstraintAnchor r11 = r11.mLeft
            android.support.constraint.solver.widgets.ConstraintAnchor r11 = r11.mTarget
            android.support.constraint.solver.widgets.ConstraintWidget r11 = r11.mOwner
            if (r11 != r2) goto L_0x0325
            android.support.constraint.solver.widgets.ConstraintAnchor r11 = r13.mTarget
            android.support.constraint.solver.widgets.ConstraintWidget r11 = r11.mOwner
            android.support.constraint.solver.widgets.ConstraintAnchor r11 = r11.mLeft
            int r11 = r11.getMargin()
            int r0 = r0 + r11
            r11 = r0
            goto L_0x0326
        L_0x0325:
            r11 = r0
        L_0x0326:
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r13.mTarget
            android.support.constraint.solver.SolverVariable r0 = r0.mSolverVariable
            int r14 = -r11
            r18 = r11
            r11 = 2
            r15.addLowerThan(r9, r0, r14, r11)
            r0 = r18
            goto L_0x0339
        L_0x0334:
            r34 = r0
            r11 = 2
            r0 = r18
        L_0x0339:
            int r13 = r12.mMatchConstraintMaxWidth
            if (r13 <= 0) goto L_0x0342
            int r13 = r12.mMatchConstraintMaxWidth
            r15.addLowerThan(r7, r5, r13, r11)
        L_0x0342:
            android.support.constraint.solver.ArrayRow r13 = r38.createRow()
            float r14 = r4.mHorizontalWeight
            float r11 = r2.mHorizontalWeight
            r35 = r0
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r4.mLeft
            int r23 = r0.getMargin()
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r4.mRight
            int r25 = r0.getMargin()
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r2.mLeft
            int r27 = r0.getMargin()
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r2.mRight
            int r29 = r0.getMargin()
            r18 = r13
            r19 = r14
            r20 = r8
            r21 = r11
            r22 = r5
            r24 = r7
            r26 = r10
            r28 = r9
            r18.createRowEqualDimension(r19, r20, r21, r22, r23, r24, r25, r26, r27, r28, r29)
            r15.addConstraint(r13)
            int r1 = r1 + 1
            r14 = r32
            r13 = r33
            r0 = r34
            r9 = 0
            r11 = 3
            goto L_0x0207
        L_0x0386:
            r34 = r0
            r33 = r13
            r32 = r14
            r0 = r15
            r24 = r32
            goto L_0x05ce
        L_0x0391:
            r34 = r0
            r33 = r13
            r32 = r14
        L_0x0397:
            r0 = 0
            r2 = 0
            r4 = r1
            r5 = 0
            r7 = 0
            r18 = r7
        L_0x039e:
            r19 = 0
            if (r1 == 0) goto L_0x055d
            android.support.constraint.solver.widgets.ConstraintWidget r7 = r1.mHorizontalNextWidget
            if (r7 != 0) goto L_0x03ac
            android.support.constraint.solver.widgets.ConstraintWidget[] r8 = r6.mChainEnds
            r9 = 1
            r2 = r8[r9]
            r5 = 1
        L_0x03ac:
            if (r16 == 0) goto L_0x040a
            android.support.constraint.solver.widgets.ConstraintAnchor r8 = r1.mLeft
            int r9 = r8.getMargin()
            if (r0 == 0) goto L_0x03bd
            android.support.constraint.solver.widgets.ConstraintAnchor r10 = r0.mRight
            int r10 = r10.getMargin()
            int r9 = r9 + r10
        L_0x03bd:
            r10 = 1
            if (r4 == r1) goto L_0x03c1
            r10 = 3
        L_0x03c1:
            android.support.constraint.solver.SolverVariable r11 = r8.mSolverVariable
            android.support.constraint.solver.widgets.ConstraintAnchor r13 = r8.mTarget
            android.support.constraint.solver.SolverVariable r13 = r13.mSolverVariable
            r15.addGreaterThan(r11, r13, r9, r10)
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r11 = r1.mHorizontalDimensionBehaviour
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r13 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r11 != r13) goto L_0x0404
            android.support.constraint.solver.widgets.ConstraintAnchor r11 = r1.mRight
            int r13 = r1.mMatchConstraintDefaultWidth
            r14 = 1
            if (r13 != r14) goto L_0x03ec
            int r13 = r1.mMatchConstraintMinWidth
            int r14 = r1.getWidth()
            int r13 = java.lang.Math.max(r13, r14)
            android.support.constraint.solver.SolverVariable r14 = r11.mSolverVariable
            android.support.constraint.solver.SolverVariable r6 = r8.mSolverVariable
            r20 = r12
            r12 = 3
            r15.addEquality(r14, r6, r13, r12)
            goto L_0x0407
        L_0x03ec:
            r20 = r12
            r12 = 3
            android.support.constraint.solver.SolverVariable r6 = r8.mSolverVariable
            android.support.constraint.solver.widgets.ConstraintAnchor r13 = r8.mTarget
            android.support.constraint.solver.SolverVariable r13 = r13.mSolverVariable
            int r14 = r8.mMargin
            r15.addGreaterThan(r6, r13, r14, r12)
            android.support.constraint.solver.SolverVariable r6 = r11.mSolverVariable
            android.support.constraint.solver.SolverVariable r13 = r8.mSolverVariable
            int r14 = r1.mMatchConstraintMinWidth
            r15.addLowerThan(r6, r13, r14, r12)
            goto L_0x0407
        L_0x0404:
            r20 = r12
            r12 = 3
        L_0x0407:
            r13 = r33
            goto L_0x046f
        L_0x040a:
            r20 = r12
            r12 = 3
            r6 = 5
            if (r3 != 0) goto L_0x043f
            if (r5 == 0) goto L_0x043f
            if (r0 == 0) goto L_0x043f
            android.support.constraint.solver.widgets.ConstraintAnchor r8 = r1.mRight
            android.support.constraint.solver.widgets.ConstraintAnchor r8 = r8.mTarget
            if (r8 != 0) goto L_0x0428
            android.support.constraint.solver.widgets.ConstraintAnchor r6 = r1.mRight
            android.support.constraint.solver.SolverVariable r6 = r6.mSolverVariable
            int r8 = r1.getDrawRight()
            r15.addEquality(r6, r8)
            r13 = r33
            goto L_0x046f
        L_0x0428:
            android.support.constraint.solver.widgets.ConstraintAnchor r8 = r1.mRight
            int r8 = r8.getMargin()
            android.support.constraint.solver.widgets.ConstraintAnchor r9 = r1.mRight
            android.support.constraint.solver.SolverVariable r9 = r9.mSolverVariable
            android.support.constraint.solver.widgets.ConstraintAnchor r10 = r2.mRight
            android.support.constraint.solver.widgets.ConstraintAnchor r10 = r10.mTarget
            android.support.constraint.solver.SolverVariable r10 = r10.mSolverVariable
            int r11 = -r8
            r15.addEquality(r9, r10, r11, r6)
            r13 = r33
            goto L_0x046f
        L_0x043f:
            if (r3 != 0) goto L_0x047e
            if (r5 != 0) goto L_0x047e
            if (r0 != 0) goto L_0x047e
            android.support.constraint.solver.widgets.ConstraintAnchor r8 = r1.mLeft
            android.support.constraint.solver.widgets.ConstraintAnchor r8 = r8.mTarget
            if (r8 != 0) goto L_0x0459
            android.support.constraint.solver.widgets.ConstraintAnchor r6 = r1.mLeft
            android.support.constraint.solver.SolverVariable r6 = r6.mSolverVariable
            int r8 = r1.getDrawX()
            r15.addEquality(r6, r8)
            r13 = r33
            goto L_0x046f
        L_0x0459:
            android.support.constraint.solver.widgets.ConstraintAnchor r8 = r1.mLeft
            int r8 = r8.getMargin()
            android.support.constraint.solver.widgets.ConstraintAnchor r9 = r1.mLeft
            android.support.constraint.solver.SolverVariable r9 = r9.mSolverVariable
            r13 = r33
            android.support.constraint.solver.widgets.ConstraintAnchor r10 = r13.mLeft
            android.support.constraint.solver.widgets.ConstraintAnchor r10 = r10.mTarget
            android.support.constraint.solver.SolverVariable r10 = r10.mSolverVariable
            r15.addEquality(r9, r10, r8, r6)
        L_0x046f:
            r29 = r0
            r21 = r3
            r18 = r7
            r27 = r12
            r36 = r13
            r0 = r15
            r24 = r32
            goto L_0x0547
        L_0x047e:
            r13 = r33
            android.support.constraint.solver.widgets.ConstraintAnchor r6 = r1.mLeft
            android.support.constraint.solver.widgets.ConstraintAnchor r14 = r1.mRight
            int r11 = r6.getMargin()
            int r10 = r14.getMargin()
            android.support.constraint.solver.SolverVariable r8 = r6.mSolverVariable
            android.support.constraint.solver.widgets.ConstraintAnchor r9 = r6.mTarget
            android.support.constraint.solver.SolverVariable r9 = r9.mSolverVariable
            r12 = 1
            r15.addGreaterThan(r8, r9, r11, r12)
            android.support.constraint.solver.SolverVariable r8 = r14.mSolverVariable
            android.support.constraint.solver.widgets.ConstraintAnchor r9 = r14.mTarget
            android.support.constraint.solver.SolverVariable r9 = r9.mSolverVariable
            r21 = r3
            int r3 = -r10
            r15.addLowerThan(r8, r9, r3, r12)
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r6.mTarget
            if (r3 == 0) goto L_0x04ab
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r6.mTarget
            android.support.constraint.solver.SolverVariable r3 = r3.mSolverVariable
            goto L_0x04ad
        L_0x04ab:
            r3 = r19
        L_0x04ad:
            if (r0 != 0) goto L_0x04bf
            android.support.constraint.solver.widgets.ConstraintAnchor r8 = r13.mLeft
            android.support.constraint.solver.widgets.ConstraintAnchor r8 = r8.mTarget
            if (r8 == 0) goto L_0x04bc
            android.support.constraint.solver.widgets.ConstraintAnchor r8 = r13.mLeft
            android.support.constraint.solver.widgets.ConstraintAnchor r8 = r8.mTarget
            android.support.constraint.solver.SolverVariable r8 = r8.mSolverVariable
            goto L_0x04be
        L_0x04bc:
            r8 = r19
        L_0x04be:
            r3 = r8
        L_0x04bf:
            if (r7 != 0) goto L_0x04d3
            android.support.constraint.solver.widgets.ConstraintAnchor r8 = r2.mRight
            android.support.constraint.solver.widgets.ConstraintAnchor r8 = r8.mTarget
            if (r8 == 0) goto L_0x04ce
            android.support.constraint.solver.widgets.ConstraintAnchor r8 = r2.mRight
            android.support.constraint.solver.widgets.ConstraintAnchor r8 = r8.mTarget
            android.support.constraint.solver.widgets.ConstraintWidget r8 = r8.mOwner
            goto L_0x04d0
        L_0x04ce:
            r8 = r19
        L_0x04d0:
            r7 = r8
            r12 = r7
            goto L_0x04d4
        L_0x04d3:
            r12 = r7
        L_0x04d4:
            if (r12 == 0) goto L_0x0534
            android.support.constraint.solver.widgets.ConstraintAnchor r7 = r12.mLeft
            android.support.constraint.solver.SolverVariable r7 = r7.mSolverVariable
            if (r5 == 0) goto L_0x04ef
            android.support.constraint.solver.widgets.ConstraintAnchor r8 = r2.mRight
            android.support.constraint.solver.widgets.ConstraintAnchor r8 = r8.mTarget
            if (r8 == 0) goto L_0x04e9
            android.support.constraint.solver.widgets.ConstraintAnchor r8 = r2.mRight
            android.support.constraint.solver.widgets.ConstraintAnchor r8 = r8.mTarget
            android.support.constraint.solver.SolverVariable r8 = r8.mSolverVariable
            goto L_0x04eb
        L_0x04e9:
            r8 = r19
        L_0x04eb:
            r7 = r8
            r18 = r7
            goto L_0x04f1
        L_0x04ef:
            r18 = r7
        L_0x04f1:
            if (r3 == 0) goto L_0x0522
            if (r18 == 0) goto L_0x0522
            android.support.constraint.solver.SolverVariable r8 = r6.mSolverVariable
            r22 = 1056964608(0x3f000000, float:0.5)
            android.support.constraint.solver.SolverVariable r9 = r14.mSolverVariable
            r23 = 4
            r7 = r38
            r24 = r9
            r9 = r3
            r25 = r10
            r10 = r11
            r26 = r11
            r27 = 3
            r11 = r22
            r22 = r12
            r12 = r18
            r36 = r13
            r13 = r24
            r28 = r14
            r24 = r32
            r14 = r25
            r29 = r0
            r0 = r15
            r15 = r23
            r7.addCentering(r8, r9, r10, r11, r12, r13, r14, r15)
            goto L_0x0545
        L_0x0522:
            r29 = r0
            r25 = r10
            r26 = r11
            r22 = r12
            r36 = r13
            r28 = r14
            r0 = r15
            r24 = r32
            r27 = 3
            goto L_0x0545
        L_0x0534:
            r29 = r0
            r25 = r10
            r26 = r11
            r22 = r12
            r36 = r13
            r28 = r14
            r0 = r15
            r24 = r32
            r27 = 3
        L_0x0545:
            r18 = r22
        L_0x0547:
            r3 = r1
            if (r5 == 0) goto L_0x054b
            goto L_0x054d
        L_0x054b:
            r19 = r18
        L_0x054d:
            r1 = r19
            r6 = r37
            r15 = r0
            r0 = r3
            r12 = r20
            r3 = r21
            r32 = r24
            r33 = r36
            goto L_0x039e
        L_0x055d:
            r29 = r0
            r21 = r3
            r20 = r12
            r0 = r15
            r24 = r32
            r36 = r33
            if (r16 == 0) goto L_0x05cb
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r4.mLeft
            android.support.constraint.solver.widgets.ConstraintAnchor r6 = r2.mRight
            int r22 = r3.getMargin()
            int r15 = r6.getMargin()
            r14 = r36
            android.support.constraint.solver.widgets.ConstraintAnchor r7 = r14.mLeft
            android.support.constraint.solver.widgets.ConstraintAnchor r7 = r7.mTarget
            if (r7 == 0) goto L_0x0585
            android.support.constraint.solver.widgets.ConstraintAnchor r7 = r14.mLeft
            android.support.constraint.solver.widgets.ConstraintAnchor r7 = r7.mTarget
            android.support.constraint.solver.SolverVariable r7 = r7.mSolverVariable
            goto L_0x0587
        L_0x0585:
            r7 = r19
        L_0x0587:
            r23 = r7
            android.support.constraint.solver.widgets.ConstraintAnchor r7 = r2.mRight
            android.support.constraint.solver.widgets.ConstraintAnchor r7 = r7.mTarget
            if (r7 == 0) goto L_0x0597
            android.support.constraint.solver.widgets.ConstraintAnchor r7 = r2.mRight
            android.support.constraint.solver.widgets.ConstraintAnchor r7 = r7.mTarget
            android.support.constraint.solver.SolverVariable r7 = r7.mSolverVariable
            r19 = r7
        L_0x0597:
            r13 = r19
            if (r23 == 0) goto L_0x05c4
            if (r13 == 0) goto L_0x05c4
            android.support.constraint.solver.SolverVariable r7 = r6.mSolverVariable
            int r8 = -r15
            r9 = 1
            r0.addLowerThan(r7, r13, r8, r9)
            android.support.constraint.solver.SolverVariable r8 = r3.mSolverVariable
            float r11 = r14.mHorizontalBiasPercent
            android.support.constraint.solver.SolverVariable r12 = r6.mSolverVariable
            r19 = 4
            r7 = r38
            r9 = r23
            r10 = r22
            r25 = r12
            r12 = r13
            r26 = r13
            r13 = r25
            r25 = r14
            r14 = r15
            r27 = r15
            r15 = r19
            r7.addCentering(r8, r9, r10, r11, r12, r13, r14, r15)
            goto L_0x05cd
        L_0x05c4:
            r26 = r13
            r25 = r14
            r27 = r15
            goto L_0x05cd
        L_0x05cb:
            r25 = r36
        L_0x05cd:
        L_0x05ce:
            int r14 = r24 + 1
            r6 = r37
            r15 = r0
            goto L_0x0006
        L_0x05d5:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.constraint.solver.widgets.ConstraintWidgetContainer.applyHorizontalChain(android.support.constraint.solver.LinearSystem):void");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r19v0, resolved type: android.support.constraint.solver.SolverVariable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r19v1, resolved type: android.support.constraint.solver.SolverVariable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r19v3, resolved type: android.support.constraint.solver.widgets.ConstraintWidget} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r19v4, resolved type: android.support.constraint.solver.widgets.ConstraintWidget} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r19v5, resolved type: android.support.constraint.solver.widgets.ConstraintWidget} */
    /* JADX WARNING: type inference failed for: r7v15, types: [android.support.constraint.solver.SolverVariable] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:215:0x0578  */
    /* JADX WARNING: Removed duplicated region for block: B:252:0x057a A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void applyVerticalChain(android.support.constraint.solver.LinearSystem r38) {
        /*
            r37 = this;
            r6 = r37
            r15 = r38
            r0 = 0
            r14 = r0
        L_0x0006:
            int r0 = r6.mVerticalChainsSize
            if (r14 >= r0) goto L_0x0602
            android.support.constraint.solver.widgets.ConstraintWidget[] r0 = r6.mVerticalChainsArray
            r13 = r0[r14]
            android.support.constraint.solver.widgets.ConstraintWidget[] r2 = r6.mChainEnds
            r3 = r0[r14]
            r4 = 1
            boolean[] r5 = r6.flags
            r0 = r37
            r1 = r38
            int r0 = r0.countMatchConstraintsChainedWidgets(r1, r2, r3, r4, r5)
            android.support.constraint.solver.widgets.ConstraintWidget[] r1 = r6.mChainEnds
            r2 = 2
            r1 = r1[r2]
            if (r1 != 0) goto L_0x0029
            r25 = r14
            r0 = r15
            goto L_0x05fb
        L_0x0029:
            boolean[] r3 = r6.flags
            r4 = 1
            boolean r3 = r3[r4]
            if (r3 == 0) goto L_0x0059
            int r2 = r13.getDrawY()
        L_0x0034:
            if (r1 == 0) goto L_0x0054
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r1.mTop
            android.support.constraint.solver.SolverVariable r3 = r3.mSolverVariable
            r15.addEquality(r3, r2)
            android.support.constraint.solver.widgets.ConstraintWidget r3 = r1.mVerticalNextWidget
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r1.mTop
            int r4 = r4.getMargin()
            int r5 = r1.getHeight()
            int r4 = r4 + r5
            android.support.constraint.solver.widgets.ConstraintAnchor r5 = r1.mBottom
            int r5 = r5.getMargin()
            int r4 = r4 + r5
            int r2 = r2 + r4
            r1 = r3
            goto L_0x0034
        L_0x0054:
            r25 = r14
            r0 = r15
            goto L_0x05fb
        L_0x0059:
            int r3 = r13.mVerticalChainStyle
            r5 = 0
            if (r3 != 0) goto L_0x0060
            r3 = r4
            goto L_0x0061
        L_0x0060:
            r3 = r5
        L_0x0061:
            int r7 = r13.mVerticalChainStyle
            if (r7 != r2) goto L_0x0067
            r7 = r4
            goto L_0x0068
        L_0x0067:
            r7 = r5
        L_0x0068:
            r16 = r7
            r12 = r13
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r7 = r6.mVerticalDimensionBehaviour
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r8 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            if (r7 != r8) goto L_0x0073
            r7 = r4
            goto L_0x0074
        L_0x0073:
            r7 = r5
        L_0x0074:
            r17 = r7
            int r7 = r6.mOptimizationLevel
            if (r7 == r2) goto L_0x007e
            r8 = 8
            if (r7 != r8) goto L_0x0098
        L_0x007e:
            boolean[] r7 = r6.flags
            boolean r7 = r7[r5]
            if (r7 == 0) goto L_0x0098
            boolean r7 = r12.mVerticalChainFixedPosition
            if (r7 == 0) goto L_0x0098
            if (r16 != 0) goto L_0x0098
            if (r17 != 0) goto L_0x0098
            int r7 = r13.mVerticalChainStyle
            if (r7 != 0) goto L_0x0098
            android.support.constraint.solver.widgets.Optimizer.applyDirectResolutionVerticalChain(r6, r15, r0, r12)
            r25 = r14
            r0 = r15
            goto L_0x05fb
        L_0x0098:
            r11 = 3
            if (r0 == 0) goto L_0x0391
            if (r16 == 0) goto L_0x00a5
            r34 = r0
            r33 = r13
            r32 = r14
            goto L_0x0397
        L_0x00a5:
            r7 = 0
            r8 = 0
        L_0x00a7:
            if (r1 == 0) goto L_0x016a
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r9 = r1.mVerticalDimensionBehaviour
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r10 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r9 == r10) goto L_0x0124
            android.support.constraint.solver.widgets.ConstraintAnchor r9 = r1.mTop
            int r9 = r9.getMargin()
            if (r7 == 0) goto L_0x00be
            android.support.constraint.solver.widgets.ConstraintAnchor r10 = r7.mBottom
            int r10 = r10.getMargin()
            int r9 = r9 + r10
        L_0x00be:
            r10 = 3
            android.support.constraint.solver.widgets.ConstraintAnchor r2 = r1.mTop
            android.support.constraint.solver.widgets.ConstraintAnchor r2 = r2.mTarget
            android.support.constraint.solver.widgets.ConstraintWidget r2 = r2.mOwner
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r2 = r2.mVerticalDimensionBehaviour
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r4 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r2 != r4) goto L_0x00cc
            r10 = 2
        L_0x00cc:
            android.support.constraint.solver.widgets.ConstraintAnchor r2 = r1.mTop
            android.support.constraint.solver.SolverVariable r2 = r2.mSolverVariable
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r1.mTop
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.mTarget
            android.support.constraint.solver.SolverVariable r4 = r4.mSolverVariable
            r15.addGreaterThan(r2, r4, r9, r10)
            android.support.constraint.solver.widgets.ConstraintAnchor r2 = r1.mBottom
            int r2 = r2.getMargin()
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r1.mBottom
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.mTarget
            android.support.constraint.solver.widgets.ConstraintWidget r4 = r4.mOwner
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.mTop
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.mTarget
            if (r4 == 0) goto L_0x0106
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r1.mBottom
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.mTarget
            android.support.constraint.solver.widgets.ConstraintWidget r4 = r4.mOwner
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.mTop
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.mTarget
            android.support.constraint.solver.widgets.ConstraintWidget r4 = r4.mOwner
            if (r4 != r1) goto L_0x0106
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r1.mBottom
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.mTarget
            android.support.constraint.solver.widgets.ConstraintWidget r4 = r4.mOwner
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.mTop
            int r4 = r4.getMargin()
            int r2 = r2 + r4
        L_0x0106:
            r4 = 3
            android.support.constraint.solver.widgets.ConstraintAnchor r9 = r1.mBottom
            android.support.constraint.solver.widgets.ConstraintAnchor r9 = r9.mTarget
            android.support.constraint.solver.widgets.ConstraintWidget r9 = r9.mOwner
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r9 = r9.mVerticalDimensionBehaviour
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r10 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r9 != r10) goto L_0x0114
            r4 = 2
        L_0x0114:
            android.support.constraint.solver.widgets.ConstraintAnchor r9 = r1.mBottom
            android.support.constraint.solver.SolverVariable r9 = r9.mSolverVariable
            android.support.constraint.solver.widgets.ConstraintAnchor r10 = r1.mBottom
            android.support.constraint.solver.widgets.ConstraintAnchor r10 = r10.mTarget
            android.support.constraint.solver.SolverVariable r10 = r10.mSolverVariable
            int r5 = -r2
            r15.addLowerThan(r9, r10, r5, r4)
            r10 = 1
            goto L_0x0162
        L_0x0124:
            float r2 = r1.mVerticalWeight
            float r8 = r8 + r2
            r2 = 0
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r1.mBottom
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.mTarget
            if (r4 == 0) goto L_0x0147
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r1.mBottom
            int r2 = r4.getMargin()
            android.support.constraint.solver.widgets.ConstraintWidget[] r4 = r6.mChainEnds
            r4 = r4[r11]
            if (r1 == r4) goto L_0x0147
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r1.mBottom
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.mTarget
            android.support.constraint.solver.widgets.ConstraintWidget r4 = r4.mOwner
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.mTop
            int r4 = r4.getMargin()
            int r2 = r2 + r4
        L_0x0147:
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r1.mBottom
            android.support.constraint.solver.SolverVariable r4 = r4.mSolverVariable
            android.support.constraint.solver.widgets.ConstraintAnchor r5 = r1.mTop
            android.support.constraint.solver.SolverVariable r5 = r5.mSolverVariable
            r9 = 0
            r10 = 1
            r15.addGreaterThan(r4, r5, r9, r10)
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r1.mBottom
            android.support.constraint.solver.SolverVariable r4 = r4.mSolverVariable
            android.support.constraint.solver.widgets.ConstraintAnchor r5 = r1.mBottom
            android.support.constraint.solver.widgets.ConstraintAnchor r5 = r5.mTarget
            android.support.constraint.solver.SolverVariable r5 = r5.mSolverVariable
            int r9 = -r2
            r15.addLowerThan(r4, r5, r9, r10)
        L_0x0162:
            r7 = r1
            android.support.constraint.solver.widgets.ConstraintWidget r1 = r1.mVerticalNextWidget
            r4 = r10
            r2 = 2
            r5 = 0
            goto L_0x00a7
        L_0x016a:
            r10 = r4
            if (r0 != r10) goto L_0x0201
            android.support.constraint.solver.widgets.ConstraintWidget[] r2 = r6.mMatchConstraintsChainedWidgets
            r9 = 0
            r2 = r2[r9]
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r2.mTop
            int r4 = r4.getMargin()
            android.support.constraint.solver.widgets.ConstraintAnchor r5 = r2.mTop
            android.support.constraint.solver.widgets.ConstraintAnchor r5 = r5.mTarget
            if (r5 == 0) goto L_0x0187
            android.support.constraint.solver.widgets.ConstraintAnchor r5 = r2.mTop
            android.support.constraint.solver.widgets.ConstraintAnchor r5 = r5.mTarget
            int r5 = r5.getMargin()
            int r4 = r4 + r5
        L_0x0187:
            android.support.constraint.solver.widgets.ConstraintAnchor r5 = r2.mBottom
            int r5 = r5.getMargin()
            android.support.constraint.solver.widgets.ConstraintAnchor r9 = r2.mBottom
            android.support.constraint.solver.widgets.ConstraintAnchor r9 = r9.mTarget
            if (r9 == 0) goto L_0x019c
            android.support.constraint.solver.widgets.ConstraintAnchor r9 = r2.mBottom
            android.support.constraint.solver.widgets.ConstraintAnchor r9 = r9.mTarget
            int r9 = r9.getMargin()
            int r5 = r5 + r9
        L_0x019c:
            android.support.constraint.solver.widgets.ConstraintAnchor r9 = r12.mBottom
            android.support.constraint.solver.widgets.ConstraintAnchor r9 = r9.mTarget
            android.support.constraint.solver.SolverVariable r9 = r9.mSolverVariable
            android.support.constraint.solver.widgets.ConstraintWidget[] r10 = r6.mChainEnds
            r11 = r10[r11]
            if (r2 != r11) goto L_0x01b2
            r11 = 1
            r10 = r10[r11]
            android.support.constraint.solver.widgets.ConstraintAnchor r10 = r10.mBottom
            android.support.constraint.solver.widgets.ConstraintAnchor r10 = r10.mTarget
            android.support.constraint.solver.SolverVariable r9 = r10.mSolverVariable
            goto L_0x01b3
        L_0x01b2:
            r11 = 1
        L_0x01b3:
            int r10 = r2.mMatchConstraintDefaultHeight
            if (r10 != r11) goto L_0x01e2
            android.support.constraint.solver.widgets.ConstraintAnchor r10 = r12.mTop
            android.support.constraint.solver.SolverVariable r10 = r10.mSolverVariable
            android.support.constraint.solver.widgets.ConstraintAnchor r11 = r12.mTop
            android.support.constraint.solver.widgets.ConstraintAnchor r11 = r11.mTarget
            android.support.constraint.solver.SolverVariable r11 = r11.mSolverVariable
            r30 = r1
            r1 = 1
            r15.addGreaterThan(r10, r11, r4, r1)
            android.support.constraint.solver.widgets.ConstraintAnchor r10 = r12.mBottom
            android.support.constraint.solver.SolverVariable r10 = r10.mSolverVariable
            int r11 = -r5
            r15.addLowerThan(r10, r9, r11, r1)
            android.support.constraint.solver.widgets.ConstraintAnchor r1 = r12.mBottom
            android.support.constraint.solver.SolverVariable r1 = r1.mSolverVariable
            android.support.constraint.solver.widgets.ConstraintAnchor r10 = r12.mTop
            android.support.constraint.solver.SolverVariable r10 = r10.mSolverVariable
            int r11 = r12.getHeight()
            r31 = r7
            r7 = 2
            r15.addEquality(r1, r10, r11, r7)
            goto L_0x01fc
        L_0x01e2:
            r30 = r1
            r31 = r7
            android.support.constraint.solver.widgets.ConstraintAnchor r1 = r2.mTop
            android.support.constraint.solver.SolverVariable r1 = r1.mSolverVariable
            android.support.constraint.solver.widgets.ConstraintAnchor r7 = r2.mTop
            android.support.constraint.solver.widgets.ConstraintAnchor r7 = r7.mTarget
            android.support.constraint.solver.SolverVariable r7 = r7.mSolverVariable
            r10 = 1
            r15.addEquality(r1, r7, r4, r10)
            android.support.constraint.solver.widgets.ConstraintAnchor r1 = r2.mBottom
            android.support.constraint.solver.SolverVariable r1 = r1.mSolverVariable
            int r7 = -r5
            r15.addEquality(r1, r9, r7, r10)
        L_0x01fc:
            r25 = r14
            r0 = r15
            goto L_0x05fb
        L_0x0201:
            r30 = r1
            r31 = r7
            r9 = 0
            r1 = 0
        L_0x0207:
            int r2 = r0 + -1
            if (r1 >= r2) goto L_0x0386
            android.support.constraint.solver.widgets.ConstraintWidget[] r2 = r6.mMatchConstraintsChainedWidgets
            r4 = r2[r1]
            int r5 = r1 + 1
            r2 = r2[r5]
            android.support.constraint.solver.widgets.ConstraintAnchor r5 = r4.mTop
            android.support.constraint.solver.SolverVariable r5 = r5.mSolverVariable
            android.support.constraint.solver.widgets.ConstraintAnchor r7 = r4.mBottom
            android.support.constraint.solver.SolverVariable r7 = r7.mSolverVariable
            android.support.constraint.solver.widgets.ConstraintAnchor r10 = r2.mTop
            android.support.constraint.solver.SolverVariable r10 = r10.mSolverVariable
            android.support.constraint.solver.widgets.ConstraintAnchor r9 = r2.mBottom
            android.support.constraint.solver.SolverVariable r9 = r9.mSolverVariable
            r18 = r9
            android.support.constraint.solver.widgets.ConstraintWidget[] r9 = r6.mChainEnds
            r32 = r14
            r14 = r9[r11]
            if (r2 != r14) goto L_0x0235
            r14 = 1
            r9 = r9[r14]
            android.support.constraint.solver.widgets.ConstraintAnchor r9 = r9.mBottom
            android.support.constraint.solver.SolverVariable r9 = r9.mSolverVariable
            goto L_0x0237
        L_0x0235:
            r9 = r18
        L_0x0237:
            android.support.constraint.solver.widgets.ConstraintAnchor r14 = r4.mTop
            int r14 = r14.getMargin()
            android.support.constraint.solver.widgets.ConstraintAnchor r11 = r4.mTop
            android.support.constraint.solver.widgets.ConstraintAnchor r11 = r11.mTarget
            if (r11 == 0) goto L_0x026a
            android.support.constraint.solver.widgets.ConstraintAnchor r11 = r4.mTop
            android.support.constraint.solver.widgets.ConstraintAnchor r11 = r11.mTarget
            android.support.constraint.solver.widgets.ConstraintWidget r11 = r11.mOwner
            android.support.constraint.solver.widgets.ConstraintAnchor r11 = r11.mBottom
            android.support.constraint.solver.widgets.ConstraintAnchor r11 = r11.mTarget
            if (r11 == 0) goto L_0x026a
            android.support.constraint.solver.widgets.ConstraintAnchor r11 = r4.mTop
            android.support.constraint.solver.widgets.ConstraintAnchor r11 = r11.mTarget
            android.support.constraint.solver.widgets.ConstraintWidget r11 = r11.mOwner
            android.support.constraint.solver.widgets.ConstraintAnchor r11 = r11.mBottom
            android.support.constraint.solver.widgets.ConstraintAnchor r11 = r11.mTarget
            android.support.constraint.solver.widgets.ConstraintWidget r11 = r11.mOwner
            if (r11 != r4) goto L_0x026a
            android.support.constraint.solver.widgets.ConstraintAnchor r11 = r4.mTop
            android.support.constraint.solver.widgets.ConstraintAnchor r11 = r11.mTarget
            android.support.constraint.solver.widgets.ConstraintWidget r11 = r11.mOwner
            android.support.constraint.solver.widgets.ConstraintAnchor r11 = r11.mBottom
            int r11 = r11.getMargin()
            int r14 = r14 + r11
        L_0x026a:
            android.support.constraint.solver.widgets.ConstraintAnchor r11 = r4.mTop
            android.support.constraint.solver.widgets.ConstraintAnchor r11 = r11.mTarget
            android.support.constraint.solver.SolverVariable r11 = r11.mSolverVariable
            r33 = r13
            r13 = 2
            r15.addGreaterThan(r5, r11, r14, r13)
            android.support.constraint.solver.widgets.ConstraintAnchor r11 = r4.mBottom
            int r11 = r11.getMargin()
            android.support.constraint.solver.widgets.ConstraintAnchor r13 = r4.mBottom
            android.support.constraint.solver.widgets.ConstraintAnchor r13 = r13.mTarget
            if (r13 == 0) goto L_0x0299
            android.support.constraint.solver.widgets.ConstraintWidget r13 = r4.mVerticalNextWidget
            if (r13 == 0) goto L_0x0299
            android.support.constraint.solver.widgets.ConstraintWidget r13 = r4.mVerticalNextWidget
            android.support.constraint.solver.widgets.ConstraintAnchor r13 = r13.mTop
            android.support.constraint.solver.widgets.ConstraintAnchor r13 = r13.mTarget
            if (r13 == 0) goto L_0x0297
            android.support.constraint.solver.widgets.ConstraintWidget r13 = r4.mVerticalNextWidget
            android.support.constraint.solver.widgets.ConstraintAnchor r13 = r13.mTop
            int r13 = r13.getMargin()
            goto L_0x0298
        L_0x0297:
            r13 = 0
        L_0x0298:
            int r11 = r11 + r13
        L_0x0299:
            android.support.constraint.solver.widgets.ConstraintAnchor r13 = r4.mBottom
            android.support.constraint.solver.widgets.ConstraintAnchor r13 = r13.mTarget
            android.support.constraint.solver.SolverVariable r13 = r13.mSolverVariable
            int r14 = -r11
            r18 = r11
            r11 = 2
            r15.addLowerThan(r7, r13, r14, r11)
            int r11 = r1 + 1
            int r13 = r0 + -1
            if (r11 != r13) goto L_0x0334
            android.support.constraint.solver.widgets.ConstraintAnchor r11 = r2.mTop
            int r11 = r11.getMargin()
            android.support.constraint.solver.widgets.ConstraintAnchor r13 = r2.mTop
            android.support.constraint.solver.widgets.ConstraintAnchor r13 = r13.mTarget
            if (r13 == 0) goto L_0x02df
            android.support.constraint.solver.widgets.ConstraintAnchor r13 = r2.mTop
            android.support.constraint.solver.widgets.ConstraintAnchor r13 = r13.mTarget
            android.support.constraint.solver.widgets.ConstraintWidget r13 = r13.mOwner
            android.support.constraint.solver.widgets.ConstraintAnchor r13 = r13.mBottom
            android.support.constraint.solver.widgets.ConstraintAnchor r13 = r13.mTarget
            if (r13 == 0) goto L_0x02df
            android.support.constraint.solver.widgets.ConstraintAnchor r13 = r2.mTop
            android.support.constraint.solver.widgets.ConstraintAnchor r13 = r13.mTarget
            android.support.constraint.solver.widgets.ConstraintWidget r13 = r13.mOwner
            android.support.constraint.solver.widgets.ConstraintAnchor r13 = r13.mBottom
            android.support.constraint.solver.widgets.ConstraintAnchor r13 = r13.mTarget
            android.support.constraint.solver.widgets.ConstraintWidget r13 = r13.mOwner
            if (r13 != r2) goto L_0x02df
            android.support.constraint.solver.widgets.ConstraintAnchor r13 = r2.mTop
            android.support.constraint.solver.widgets.ConstraintAnchor r13 = r13.mTarget
            android.support.constraint.solver.widgets.ConstraintWidget r13 = r13.mOwner
            android.support.constraint.solver.widgets.ConstraintAnchor r13 = r13.mBottom
            int r13 = r13.getMargin()
            int r11 = r11 + r13
        L_0x02df:
            android.support.constraint.solver.widgets.ConstraintAnchor r13 = r2.mTop
            android.support.constraint.solver.widgets.ConstraintAnchor r13 = r13.mTarget
            android.support.constraint.solver.SolverVariable r13 = r13.mSolverVariable
            r14 = 2
            r15.addGreaterThan(r10, r13, r11, r14)
            android.support.constraint.solver.widgets.ConstraintAnchor r13 = r2.mBottom
            android.support.constraint.solver.widgets.ConstraintWidget[] r14 = r6.mChainEnds
            r34 = r0
            r18 = 3
            r0 = r14[r18]
            if (r2 != r0) goto L_0x02fa
            r0 = 1
            r14 = r14[r0]
            android.support.constraint.solver.widgets.ConstraintAnchor r13 = r14.mBottom
        L_0x02fa:
            int r0 = r13.getMargin()
            android.support.constraint.solver.widgets.ConstraintAnchor r11 = r13.mTarget
            if (r11 == 0) goto L_0x0325
            android.support.constraint.solver.widgets.ConstraintAnchor r11 = r13.mTarget
            android.support.constraint.solver.widgets.ConstraintWidget r11 = r11.mOwner
            android.support.constraint.solver.widgets.ConstraintAnchor r11 = r11.mTop
            android.support.constraint.solver.widgets.ConstraintAnchor r11 = r11.mTarget
            if (r11 == 0) goto L_0x0325
            android.support.constraint.solver.widgets.ConstraintAnchor r11 = r13.mTarget
            android.support.constraint.solver.widgets.ConstraintWidget r11 = r11.mOwner
            android.support.constraint.solver.widgets.ConstraintAnchor r11 = r11.mTop
            android.support.constraint.solver.widgets.ConstraintAnchor r11 = r11.mTarget
            android.support.constraint.solver.widgets.ConstraintWidget r11 = r11.mOwner
            if (r11 != r2) goto L_0x0325
            android.support.constraint.solver.widgets.ConstraintAnchor r11 = r13.mTarget
            android.support.constraint.solver.widgets.ConstraintWidget r11 = r11.mOwner
            android.support.constraint.solver.widgets.ConstraintAnchor r11 = r11.mTop
            int r11 = r11.getMargin()
            int r0 = r0 + r11
            r11 = r0
            goto L_0x0326
        L_0x0325:
            r11 = r0
        L_0x0326:
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r13.mTarget
            android.support.constraint.solver.SolverVariable r0 = r0.mSolverVariable
            int r14 = -r11
            r18 = r11
            r11 = 2
            r15.addLowerThan(r9, r0, r14, r11)
            r0 = r18
            goto L_0x0339
        L_0x0334:
            r34 = r0
            r11 = 2
            r0 = r18
        L_0x0339:
            int r13 = r12.mMatchConstraintMaxHeight
            if (r13 <= 0) goto L_0x0342
            int r13 = r12.mMatchConstraintMaxHeight
            r15.addLowerThan(r7, r5, r13, r11)
        L_0x0342:
            android.support.constraint.solver.ArrayRow r13 = r38.createRow()
            float r14 = r4.mVerticalWeight
            float r11 = r2.mVerticalWeight
            r35 = r0
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r4.mTop
            int r23 = r0.getMargin()
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r4.mBottom
            int r25 = r0.getMargin()
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r2.mTop
            int r27 = r0.getMargin()
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r2.mBottom
            int r29 = r0.getMargin()
            r18 = r13
            r19 = r14
            r20 = r8
            r21 = r11
            r22 = r5
            r24 = r7
            r26 = r10
            r28 = r9
            r18.createRowEqualDimension(r19, r20, r21, r22, r23, r24, r25, r26, r27, r28, r29)
            r15.addConstraint(r13)
            int r1 = r1 + 1
            r14 = r32
            r13 = r33
            r0 = r34
            r9 = 0
            r11 = 3
            goto L_0x0207
        L_0x0386:
            r34 = r0
            r33 = r13
            r32 = r14
            r0 = r15
            r25 = r32
            goto L_0x05fb
        L_0x0391:
            r34 = r0
            r33 = r13
            r32 = r14
        L_0x0397:
            r0 = 0
            r2 = 0
            r4 = r1
            r5 = 0
            r7 = 0
            r18 = r7
        L_0x039e:
            r19 = 0
            if (r1 == 0) goto L_0x058a
            android.support.constraint.solver.widgets.ConstraintWidget r7 = r1.mVerticalNextWidget
            if (r7 != 0) goto L_0x03ac
            android.support.constraint.solver.widgets.ConstraintWidget[] r8 = r6.mChainEnds
            r9 = 1
            r2 = r8[r9]
            r5 = 1
        L_0x03ac:
            if (r16 == 0) goto L_0x0434
            android.support.constraint.solver.widgets.ConstraintAnchor r8 = r1.mTop
            int r9 = r8.getMargin()
            if (r0 == 0) goto L_0x03bd
            android.support.constraint.solver.widgets.ConstraintAnchor r10 = r0.mBottom
            int r10 = r10.getMargin()
            int r9 = r9 + r10
        L_0x03bd:
            r10 = 1
            if (r4 == r1) goto L_0x03c1
            r10 = 3
        L_0x03c1:
            r11 = 0
            r13 = 0
            android.support.constraint.solver.widgets.ConstraintAnchor r14 = r8.mTarget
            if (r14 == 0) goto L_0x03ce
            android.support.constraint.solver.SolverVariable r11 = r8.mSolverVariable
            android.support.constraint.solver.widgets.ConstraintAnchor r14 = r8.mTarget
            android.support.constraint.solver.SolverVariable r13 = r14.mSolverVariable
            goto L_0x03e3
        L_0x03ce:
            android.support.constraint.solver.widgets.ConstraintAnchor r14 = r1.mBaseline
            android.support.constraint.solver.widgets.ConstraintAnchor r14 = r14.mTarget
            if (r14 == 0) goto L_0x03e3
            android.support.constraint.solver.widgets.ConstraintAnchor r14 = r1.mBaseline
            android.support.constraint.solver.SolverVariable r11 = r14.mSolverVariable
            android.support.constraint.solver.widgets.ConstraintAnchor r14 = r1.mBaseline
            android.support.constraint.solver.widgets.ConstraintAnchor r14 = r14.mTarget
            android.support.constraint.solver.SolverVariable r13 = r14.mSolverVariable
            int r14 = r8.getMargin()
            int r9 = r9 - r14
        L_0x03e3:
            if (r11 == 0) goto L_0x03ea
            if (r13 == 0) goto L_0x03ea
            r15.addGreaterThan(r11, r13, r9, r10)
        L_0x03ea:
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r14 = r1.mVerticalDimensionBehaviour
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r6 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r14 != r6) goto L_0x042a
            android.support.constraint.solver.widgets.ConstraintAnchor r6 = r1.mBottom
            int r14 = r1.mMatchConstraintDefaultHeight
            r18 = r9
            r9 = 1
            if (r14 != r9) goto L_0x0410
            int r9 = r1.mMatchConstraintMinHeight
            int r14 = r1.getHeight()
            int r9 = java.lang.Math.max(r9, r14)
            android.support.constraint.solver.SolverVariable r14 = r6.mSolverVariable
            r20 = r10
            android.support.constraint.solver.SolverVariable r10 = r8.mSolverVariable
            r21 = r11
            r11 = 3
            r15.addEquality(r14, r10, r9, r11)
            goto L_0x0431
        L_0x0410:
            r20 = r10
            r21 = r11
            r11 = 3
            android.support.constraint.solver.SolverVariable r9 = r8.mSolverVariable
            android.support.constraint.solver.widgets.ConstraintAnchor r10 = r8.mTarget
            android.support.constraint.solver.SolverVariable r10 = r10.mSolverVariable
            int r14 = r8.mMargin
            r15.addGreaterThan(r9, r10, r14, r11)
            android.support.constraint.solver.SolverVariable r9 = r6.mSolverVariable
            android.support.constraint.solver.SolverVariable r10 = r8.mSolverVariable
            int r14 = r1.mMatchConstraintMinHeight
            r15.addLowerThan(r9, r10, r14, r11)
            goto L_0x0431
        L_0x042a:
            r18 = r9
            r20 = r10
            r21 = r11
            r11 = 3
        L_0x0431:
            r13 = r33
            goto L_0x0497
        L_0x0434:
            r11 = 3
            r6 = 5
            if (r3 != 0) goto L_0x0467
            if (r5 == 0) goto L_0x0467
            if (r0 == 0) goto L_0x0467
            android.support.constraint.solver.widgets.ConstraintAnchor r8 = r1.mBottom
            android.support.constraint.solver.widgets.ConstraintAnchor r8 = r8.mTarget
            if (r8 != 0) goto L_0x0450
            android.support.constraint.solver.widgets.ConstraintAnchor r6 = r1.mBottom
            android.support.constraint.solver.SolverVariable r6 = r6.mSolverVariable
            int r8 = r1.getDrawBottom()
            r15.addEquality(r6, r8)
            r13 = r33
            goto L_0x0497
        L_0x0450:
            android.support.constraint.solver.widgets.ConstraintAnchor r8 = r1.mBottom
            int r8 = r8.getMargin()
            android.support.constraint.solver.widgets.ConstraintAnchor r9 = r1.mBottom
            android.support.constraint.solver.SolverVariable r9 = r9.mSolverVariable
            android.support.constraint.solver.widgets.ConstraintAnchor r10 = r2.mBottom
            android.support.constraint.solver.widgets.ConstraintAnchor r10 = r10.mTarget
            android.support.constraint.solver.SolverVariable r10 = r10.mSolverVariable
            int r13 = -r8
            r15.addEquality(r9, r10, r13, r6)
            r13 = r33
            goto L_0x0497
        L_0x0467:
            if (r3 != 0) goto L_0x04a8
            if (r5 != 0) goto L_0x04a8
            if (r0 != 0) goto L_0x04a8
            android.support.constraint.solver.widgets.ConstraintAnchor r8 = r1.mTop
            android.support.constraint.solver.widgets.ConstraintAnchor r8 = r8.mTarget
            if (r8 != 0) goto L_0x0481
            android.support.constraint.solver.widgets.ConstraintAnchor r6 = r1.mTop
            android.support.constraint.solver.SolverVariable r6 = r6.mSolverVariable
            int r8 = r1.getDrawY()
            r15.addEquality(r6, r8)
            r13 = r33
            goto L_0x0497
        L_0x0481:
            android.support.constraint.solver.widgets.ConstraintAnchor r8 = r1.mTop
            int r8 = r8.getMargin()
            android.support.constraint.solver.widgets.ConstraintAnchor r9 = r1.mTop
            android.support.constraint.solver.SolverVariable r9 = r9.mSolverVariable
            r13 = r33
            android.support.constraint.solver.widgets.ConstraintAnchor r10 = r13.mTop
            android.support.constraint.solver.widgets.ConstraintAnchor r10 = r10.mTarget
            android.support.constraint.solver.SolverVariable r10 = r10.mSolverVariable
            r15.addEquality(r9, r10, r8, r6)
        L_0x0497:
            r29 = r0
            r20 = r3
            r18 = r7
            r27 = r11
            r22 = r12
            r36 = r13
            r0 = r15
            r25 = r32
            goto L_0x0574
        L_0x04a8:
            r13 = r33
            android.support.constraint.solver.widgets.ConstraintAnchor r6 = r1.mTop
            android.support.constraint.solver.widgets.ConstraintAnchor r14 = r1.mBottom
            int r10 = r6.getMargin()
            int r9 = r14.getMargin()
            android.support.constraint.solver.SolverVariable r8 = r6.mSolverVariable
            android.support.constraint.solver.widgets.ConstraintAnchor r11 = r6.mTarget
            android.support.constraint.solver.SolverVariable r11 = r11.mSolverVariable
            r20 = r3
            r3 = 1
            r15.addGreaterThan(r8, r11, r10, r3)
            android.support.constraint.solver.SolverVariable r8 = r14.mSolverVariable
            android.support.constraint.solver.widgets.ConstraintAnchor r11 = r14.mTarget
            android.support.constraint.solver.SolverVariable r11 = r11.mSolverVariable
            r18 = r10
            int r10 = -r9
            r15.addLowerThan(r8, r11, r10, r3)
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r6.mTarget
            if (r3 == 0) goto L_0x04d7
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r6.mTarget
            android.support.constraint.solver.SolverVariable r3 = r3.mSolverVariable
            goto L_0x04d9
        L_0x04d7:
            r3 = r19
        L_0x04d9:
            if (r0 != 0) goto L_0x04eb
            android.support.constraint.solver.widgets.ConstraintAnchor r8 = r13.mTop
            android.support.constraint.solver.widgets.ConstraintAnchor r8 = r8.mTarget
            if (r8 == 0) goto L_0x04e8
            android.support.constraint.solver.widgets.ConstraintAnchor r8 = r13.mTop
            android.support.constraint.solver.widgets.ConstraintAnchor r8 = r8.mTarget
            android.support.constraint.solver.SolverVariable r8 = r8.mSolverVariable
            goto L_0x04ea
        L_0x04e8:
            r8 = r19
        L_0x04ea:
            r3 = r8
        L_0x04eb:
            if (r7 != 0) goto L_0x04ff
            android.support.constraint.solver.widgets.ConstraintAnchor r8 = r2.mBottom
            android.support.constraint.solver.widgets.ConstraintAnchor r8 = r8.mTarget
            if (r8 == 0) goto L_0x04fa
            android.support.constraint.solver.widgets.ConstraintAnchor r8 = r2.mBottom
            android.support.constraint.solver.widgets.ConstraintAnchor r8 = r8.mTarget
            android.support.constraint.solver.widgets.ConstraintWidget r8 = r8.mOwner
            goto L_0x04fc
        L_0x04fa:
            r8 = r19
        L_0x04fc:
            r7 = r8
            r11 = r7
            goto L_0x0500
        L_0x04ff:
            r11 = r7
        L_0x0500:
            if (r11 == 0) goto L_0x0561
            android.support.constraint.solver.widgets.ConstraintAnchor r7 = r11.mTop
            android.support.constraint.solver.SolverVariable r7 = r7.mSolverVariable
            if (r5 == 0) goto L_0x051b
            android.support.constraint.solver.widgets.ConstraintAnchor r8 = r2.mBottom
            android.support.constraint.solver.widgets.ConstraintAnchor r8 = r8.mTarget
            if (r8 == 0) goto L_0x0515
            android.support.constraint.solver.widgets.ConstraintAnchor r8 = r2.mBottom
            android.support.constraint.solver.widgets.ConstraintAnchor r8 = r8.mTarget
            android.support.constraint.solver.SolverVariable r8 = r8.mSolverVariable
            goto L_0x0517
        L_0x0515:
            r8 = r19
        L_0x0517:
            r7 = r8
            r21 = r7
            goto L_0x051d
        L_0x051b:
            r21 = r7
        L_0x051d:
            if (r3 == 0) goto L_0x054f
            if (r21 == 0) goto L_0x054f
            android.support.constraint.solver.SolverVariable r8 = r6.mSolverVariable
            r22 = 1056964608(0x3f000000, float:0.5)
            android.support.constraint.solver.SolverVariable r10 = r14.mSolverVariable
            r23 = 4
            r7 = r38
            r24 = r9
            r9 = r3
            r25 = r10
            r10 = r18
            r26 = r11
            r27 = 3
            r11 = r22
            r22 = r12
            r12 = r21
            r36 = r13
            r13 = r25
            r28 = r14
            r25 = r32
            r14 = r24
            r29 = r0
            r0 = r15
            r15 = r23
            r7.addCentering(r8, r9, r10, r11, r12, r13, r14, r15)
            goto L_0x0572
        L_0x054f:
            r29 = r0
            r24 = r9
            r26 = r11
            r22 = r12
            r36 = r13
            r28 = r14
            r0 = r15
            r25 = r32
            r27 = 3
            goto L_0x0572
        L_0x0561:
            r29 = r0
            r24 = r9
            r26 = r11
            r22 = r12
            r36 = r13
            r28 = r14
            r0 = r15
            r25 = r32
            r27 = 3
        L_0x0572:
            r18 = r26
        L_0x0574:
            r3 = r1
            if (r5 == 0) goto L_0x0578
            goto L_0x057a
        L_0x0578:
            r19 = r18
        L_0x057a:
            r1 = r19
            r6 = r37
            r15 = r0
            r0 = r3
            r3 = r20
            r12 = r22
            r32 = r25
            r33 = r36
            goto L_0x039e
        L_0x058a:
            r29 = r0
            r20 = r3
            r22 = r12
            r0 = r15
            r25 = r32
            r36 = r33
            if (r16 == 0) goto L_0x05f8
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r4.mTop
            android.support.constraint.solver.widgets.ConstraintAnchor r6 = r2.mBottom
            int r21 = r3.getMargin()
            int r15 = r6.getMargin()
            r14 = r36
            android.support.constraint.solver.widgets.ConstraintAnchor r7 = r14.mTop
            android.support.constraint.solver.widgets.ConstraintAnchor r7 = r7.mTarget
            if (r7 == 0) goto L_0x05b2
            android.support.constraint.solver.widgets.ConstraintAnchor r7 = r14.mTop
            android.support.constraint.solver.widgets.ConstraintAnchor r7 = r7.mTarget
            android.support.constraint.solver.SolverVariable r7 = r7.mSolverVariable
            goto L_0x05b4
        L_0x05b2:
            r7 = r19
        L_0x05b4:
            r23 = r7
            android.support.constraint.solver.widgets.ConstraintAnchor r7 = r2.mBottom
            android.support.constraint.solver.widgets.ConstraintAnchor r7 = r7.mTarget
            if (r7 == 0) goto L_0x05c4
            android.support.constraint.solver.widgets.ConstraintAnchor r7 = r2.mBottom
            android.support.constraint.solver.widgets.ConstraintAnchor r7 = r7.mTarget
            android.support.constraint.solver.SolverVariable r7 = r7.mSolverVariable
            r19 = r7
        L_0x05c4:
            r13 = r19
            if (r23 == 0) goto L_0x05f1
            if (r13 == 0) goto L_0x05f1
            android.support.constraint.solver.SolverVariable r7 = r6.mSolverVariable
            int r8 = -r15
            r9 = 1
            r0.addLowerThan(r7, r13, r8, r9)
            android.support.constraint.solver.SolverVariable r8 = r3.mSolverVariable
            float r11 = r14.mVerticalBiasPercent
            android.support.constraint.solver.SolverVariable r12 = r6.mSolverVariable
            r19 = 4
            r7 = r38
            r9 = r23
            r10 = r21
            r24 = r12
            r12 = r13
            r26 = r13
            r13 = r24
            r24 = r14
            r14 = r15
            r27 = r15
            r15 = r19
            r7.addCentering(r8, r9, r10, r11, r12, r13, r14, r15)
            goto L_0x05fa
        L_0x05f1:
            r26 = r13
            r24 = r14
            r27 = r15
            goto L_0x05fa
        L_0x05f8:
            r24 = r36
        L_0x05fa:
        L_0x05fb:
            int r14 = r25 + 1
            r6 = r37
            r15 = r0
            goto L_0x0006
        L_0x0602:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.constraint.solver.widgets.ConstraintWidgetContainer.applyVerticalChain(android.support.constraint.solver.LinearSystem):void");
    }

    public void updateChildrenFromSolver(LinearSystem system, int group, boolean[] flags2) {
        flags2[2] = false;
        updateFromSolver(system, group);
        int count = this.mChildren.size();
        for (int i = 0; i < count; i++) {
            ConstraintWidget widget = (ConstraintWidget) this.mChildren.get(i);
            widget.updateFromSolver(system, group);
            if (widget.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && widget.getWidth() < widget.getWrapWidth()) {
                flags2[2] = true;
            }
            if (widget.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && widget.getHeight() < widget.getWrapHeight()) {
                flags2[2] = true;
            }
        }
    }

    public void setPadding(int left, int top, int right, int bottom) {
        this.mPaddingLeft = left;
        this.mPaddingTop = top;
        this.mPaddingRight = right;
        this.mPaddingBottom = bottom;
    }

    public void layout() {
        boolean z;
        int prex = this.f17mX;
        int prey = this.f18mY;
        int prew = Math.max(0, getWidth());
        int preh = Math.max(0, getHeight());
        this.mWidthMeasuredTooSmall = false;
        this.mHeightMeasuredTooSmall = false;
        if (this.mParent != null) {
            if (this.mSnapshot == null) {
                this.mSnapshot = new Snapshot(this);
            }
            this.mSnapshot.updateFrom(this);
            setX(this.mPaddingLeft);
            setY(this.mPaddingTop);
            resetAnchors();
            resetSolverVariables(this.mSystem.getCache());
        } else {
            this.f17mX = 0;
            this.f18mY = 0;
        }
        boolean wrap_override = false;
        ConstraintWidget.DimensionBehaviour originalVerticalDimensionBehaviour = this.mVerticalDimensionBehaviour;
        ConstraintWidget.DimensionBehaviour originalHorizontalDimensionBehaviour = this.mHorizontalDimensionBehaviour;
        boolean z2 = true;
        if (this.mOptimizationLevel == 2 && (this.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || this.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT)) {
            findWrapSize(this.mChildren, this.flags);
            wrap_override = this.flags[0];
            if (prew > 0 && preh > 0 && (this.mWrapWidth > prew || this.mWrapHeight > preh)) {
                wrap_override = false;
            }
            if (wrap_override) {
                if (this.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                    this.mHorizontalDimensionBehaviour = ConstraintWidget.DimensionBehaviour.FIXED;
                    if (prew <= 0 || prew >= this.mWrapWidth) {
                        setWidth(Math.max(this.mMinWidth, this.mWrapWidth));
                    } else {
                        this.mWidthMeasuredTooSmall = true;
                        setWidth(prew);
                    }
                }
                if (this.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                    this.mVerticalDimensionBehaviour = ConstraintWidget.DimensionBehaviour.FIXED;
                    if (preh <= 0 || preh >= this.mWrapHeight) {
                        setHeight(Math.max(this.mMinHeight, this.mWrapHeight));
                    } else {
                        this.mHeightMeasuredTooSmall = true;
                        setHeight(preh);
                    }
                }
            }
        }
        resetChains();
        int count = this.mChildren.size();
        for (int i = 0; i < count; i++) {
            ConstraintWidget widget = (ConstraintWidget) this.mChildren.get(i);
            if (widget instanceof WidgetContainer) {
                ((WidgetContainer) widget).layout();
            }
        }
        boolean wrap_override2 = wrap_override;
        int countSolve = 0;
        boolean needsSolving = true;
        while (needsSolving) {
            int countSolve2 = countSolve + 1;
            try {
                this.mSystem.reset();
                needsSolving = addChildrenToSolver(this.mSystem, Integer.MAX_VALUE);
                if (needsSolving) {
                    this.mSystem.minimize();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (!needsSolving) {
                updateFromSolver(this.mSystem, Integer.MAX_VALUE);
                int i2 = 0;
                while (true) {
                    if (i2 >= count) {
                        break;
                    }
                    ConstraintWidget widget2 = (ConstraintWidget) this.mChildren.get(i2);
                    if (widget2.mHorizontalDimensionBehaviour != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || widget2.getWidth() >= widget2.getWrapWidth()) {
                        if (widget2.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && widget2.getHeight() < widget2.getWrapHeight()) {
                            this.flags[2] = z2;
                            break;
                        }
                        i2++;
                    } else {
                        this.flags[2] = z2;
                        break;
                    }
                }
            } else {
                updateChildrenFromSolver(this.mSystem, Integer.MAX_VALUE, this.flags);
            }
            boolean needsSolving2 = false;
            if (countSolve2 < 8 && this.flags[2]) {
                int maxX = 0;
                int maxY = 0;
                for (int i3 = 0; i3 < count; i3++) {
                    ConstraintWidget widget3 = (ConstraintWidget) this.mChildren.get(i3);
                    maxX = Math.max(maxX, widget3.f17mX + widget3.getWidth());
                    maxY = Math.max(maxY, widget3.f18mY + widget3.getHeight());
                }
                int maxX2 = Math.max(this.mMinWidth, maxX);
                int maxY2 = Math.max(this.mMinHeight, maxY);
                if (originalHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT && getWidth() < maxX2) {
                    setWidth(maxX2);
                    this.mHorizontalDimensionBehaviour = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                    wrap_override2 = true;
                    needsSolving2 = true;
                }
                if (originalVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT && getHeight() < maxY2) {
                    setHeight(maxY2);
                    this.mVerticalDimensionBehaviour = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                    wrap_override2 = true;
                    needsSolving2 = true;
                }
            }
            int width = Math.max(this.mMinWidth, getWidth());
            if (width > getWidth()) {
                setWidth(width);
                this.mHorizontalDimensionBehaviour = ConstraintWidget.DimensionBehaviour.FIXED;
                wrap_override2 = true;
                needsSolving2 = true;
            }
            int height = Math.max(this.mMinHeight, getHeight());
            if (height > getHeight()) {
                setHeight(height);
                this.mVerticalDimensionBehaviour = ConstraintWidget.DimensionBehaviour.FIXED;
                wrap_override2 = true;
                needsSolving2 = true;
            }
            if (!wrap_override2) {
                if (this.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT && prew > 0 && getWidth() > prew) {
                    this.mWidthMeasuredTooSmall = true;
                    wrap_override2 = true;
                    this.mHorizontalDimensionBehaviour = ConstraintWidget.DimensionBehaviour.FIXED;
                    setWidth(prew);
                    needsSolving2 = true;
                }
                if (this.mVerticalDimensionBehaviour != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || preh <= 0) {
                    z = true;
                } else if (getHeight() > preh) {
                    z = true;
                    this.mHeightMeasuredTooSmall = true;
                    wrap_override2 = true;
                    this.mVerticalDimensionBehaviour = ConstraintWidget.DimensionBehaviour.FIXED;
                    setHeight(preh);
                    needsSolving2 = true;
                } else {
                    z = true;
                }
            } else {
                z = true;
            }
            needsSolving = needsSolving2;
            countSolve = countSolve2;
            z2 = z;
        }
        if (this.mParent != null) {
            int width2 = Math.max(this.mMinWidth, getWidth());
            int height2 = Math.max(this.mMinHeight, getHeight());
            this.mSnapshot.applyTo(this);
            setWidth(this.mPaddingLeft + width2 + this.mPaddingRight);
            setHeight(this.mPaddingTop + height2 + this.mPaddingBottom);
        } else {
            this.f17mX = prex;
            this.f18mY = prey;
        }
        if (wrap_override2) {
            this.mHorizontalDimensionBehaviour = originalHorizontalDimensionBehaviour;
            this.mVerticalDimensionBehaviour = originalVerticalDimensionBehaviour;
        }
        resetSolverVariables(this.mSystem.getCache());
        if (this == getRootConstraintContainer()) {
            updateDrawPosition();
        }
    }

    static int setGroup(ConstraintAnchor anchor, int group) {
        int oldGroup = anchor.mGroup;
        if (anchor.mOwner.getParent() == null) {
            return group;
        }
        if (oldGroup <= group) {
            return oldGroup;
        }
        anchor.mGroup = group;
        ConstraintAnchor opposite = anchor.getOpposite();
        ConstraintAnchor target = anchor.mTarget;
        int group2 = opposite != null ? setGroup(opposite, group) : group;
        int group3 = target != null ? setGroup(target, group2) : group2;
        int group4 = opposite != null ? setGroup(opposite, group3) : group3;
        anchor.mGroup = group4;
        return group4;
    }

    public int layoutFindGroupsSimple() {
        int size = this.mChildren.size();
        for (int j = 0; j < size; j++) {
            ConstraintWidget widget = (ConstraintWidget) this.mChildren.get(j);
            widget.mLeft.mGroup = 0;
            widget.mRight.mGroup = 0;
            widget.mTop.mGroup = 1;
            widget.mBottom.mGroup = 1;
            widget.mBaseline.mGroup = 1;
        }
        return 2;
    }

    public void findHorizontalWrapRecursive(ConstraintWidget widget, boolean[] flags2) {
        boolean z = false;
        if (widget.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && widget.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && widget.mDimensionRatio > 0.0f) {
            flags2[0] = false;
            return;
        }
        int w = widget.getOptimizerWrapWidth();
        if (widget.mHorizontalDimensionBehaviour != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || widget.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || widget.mDimensionRatio <= 0.0f) {
            int distToRight = w;
            int distToLeft = w;
            ConstraintWidget leftWidget = null;
            ConstraintWidget rightWidget = null;
            widget.mHorizontalWrapVisited = true;
            if (widget instanceof Guideline) {
                Guideline guideline = (Guideline) widget;
                if (guideline.getOrientation() == 1) {
                    distToLeft = 0;
                    distToRight = 0;
                    if (guideline.getRelativeBegin() != -1) {
                        distToLeft = guideline.getRelativeBegin();
                    } else if (guideline.getRelativeEnd() != -1) {
                        distToRight = guideline.getRelativeEnd();
                    }
                }
            } else if (!widget.mRight.isConnected() && !widget.mLeft.isConnected()) {
                distToLeft += widget.getX();
            } else if (widget.mRight.mTarget == null || widget.mLeft.mTarget == null || (widget.mRight.mTarget != widget.mLeft.mTarget && (widget.mRight.mTarget.mOwner != widget.mLeft.mTarget.mOwner || widget.mRight.mTarget.mOwner == widget.mParent))) {
                if (widget.mRight.mTarget != null) {
                    rightWidget = widget.mRight.mTarget.mOwner;
                    distToRight += widget.mRight.getMargin();
                    if (!rightWidget.isRoot() && !rightWidget.mHorizontalWrapVisited) {
                        findHorizontalWrapRecursive(rightWidget, flags2);
                    }
                }
                if (widget.mLeft.mTarget != null) {
                    leftWidget = widget.mLeft.mTarget.mOwner;
                    distToLeft += widget.mLeft.getMargin();
                    if (!leftWidget.isRoot() && !leftWidget.mHorizontalWrapVisited) {
                        findHorizontalWrapRecursive(leftWidget, flags2);
                    }
                }
                if (widget.mRight.mTarget != null && !rightWidget.isRoot()) {
                    if (widget.mRight.mTarget.mType == ConstraintAnchor.Type.RIGHT) {
                        distToRight += rightWidget.mDistToRight - rightWidget.getOptimizerWrapWidth();
                    } else if (widget.mRight.mTarget.getType() == ConstraintAnchor.Type.LEFT) {
                        distToRight += rightWidget.mDistToRight;
                    }
                    widget.mRightHasCentered = rightWidget.mRightHasCentered || !(rightWidget.mLeft.mTarget == null || rightWidget.mRight.mTarget == null || rightWidget.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT);
                    if (widget.mRightHasCentered && (rightWidget.mLeft.mTarget == null || rightWidget.mLeft.mTarget.mOwner != widget)) {
                        distToRight += distToRight - rightWidget.mDistToRight;
                    }
                }
                if (widget.mLeft.mTarget != null && !leftWidget.isRoot()) {
                    if (widget.mLeft.mTarget.getType() == ConstraintAnchor.Type.LEFT) {
                        distToLeft += leftWidget.mDistToLeft - leftWidget.getOptimizerWrapWidth();
                    } else if (widget.mLeft.mTarget.getType() == ConstraintAnchor.Type.RIGHT) {
                        distToLeft += leftWidget.mDistToLeft;
                    }
                    if (leftWidget.mLeftHasCentered || !(leftWidget.mLeft.mTarget == null || leftWidget.mRight.mTarget == null || leftWidget.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT)) {
                        z = true;
                    }
                    widget.mLeftHasCentered = z;
                    if (widget.mLeftHasCentered && (leftWidget.mRight.mTarget == null || leftWidget.mRight.mTarget.mOwner != widget)) {
                        distToLeft += distToLeft - leftWidget.mDistToLeft;
                    }
                }
            } else {
                flags2[0] = false;
                return;
            }
            if (widget.getVisibility() == 8) {
                distToLeft -= widget.mWidth;
                distToRight -= widget.mWidth;
            }
            widget.mDistToLeft = distToLeft;
            widget.mDistToRight = distToRight;
            return;
        }
        flags2[0] = false;
    }

    public void findVerticalWrapRecursive(ConstraintWidget widget, boolean[] flags2) {
        boolean z = false;
        if (widget.mVerticalDimensionBehaviour != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || widget.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || widget.mDimensionRatio <= 0.0f) {
            int h = widget.getOptimizerWrapHeight();
            int distToTop = h;
            int distToBottom = h;
            ConstraintWidget topWidget = null;
            ConstraintWidget bottomWidget = null;
            widget.mVerticalWrapVisited = true;
            if (widget instanceof Guideline) {
                Guideline guideline = (Guideline) widget;
                if (guideline.getOrientation() == 0) {
                    distToTop = 0;
                    distToBottom = 0;
                    if (guideline.getRelativeBegin() != -1) {
                        distToTop = guideline.getRelativeBegin();
                    } else if (guideline.getRelativeEnd() != -1) {
                        distToBottom = guideline.getRelativeEnd();
                    }
                }
            } else if (widget.mBaseline.mTarget == null && widget.mTop.mTarget == null && widget.mBottom.mTarget == null) {
                distToTop += widget.getY();
            } else if (widget.mBottom.mTarget != null && widget.mTop.mTarget != null && (widget.mBottom.mTarget == widget.mTop.mTarget || (widget.mBottom.mTarget.mOwner == widget.mTop.mTarget.mOwner && widget.mBottom.mTarget.mOwner != widget.mParent))) {
                flags2[0] = false;
                return;
            } else if (widget.mBaseline.isConnected()) {
                ConstraintWidget baseLineWidget = widget.mBaseline.mTarget.getOwner();
                if (!baseLineWidget.mVerticalWrapVisited) {
                    findVerticalWrapRecursive(baseLineWidget, flags2);
                }
                int distToTop2 = Math.max((baseLineWidget.mDistToTop - baseLineWidget.mHeight) + h, h);
                int distToBottom2 = Math.max((baseLineWidget.mDistToBottom - baseLineWidget.mHeight) + h, h);
                if (widget.getVisibility() == 8) {
                    distToTop2 -= widget.mHeight;
                    distToBottom2 -= widget.mHeight;
                }
                widget.mDistToTop = distToTop2;
                widget.mDistToBottom = distToBottom2;
                return;
            } else {
                if (widget.mTop.isConnected()) {
                    topWidget = widget.mTop.mTarget.getOwner();
                    distToTop += widget.mTop.getMargin();
                    if (!topWidget.isRoot() && !topWidget.mVerticalWrapVisited) {
                        findVerticalWrapRecursive(topWidget, flags2);
                    }
                }
                if (widget.mBottom.isConnected()) {
                    bottomWidget = widget.mBottom.mTarget.getOwner();
                    distToBottom += widget.mBottom.getMargin();
                    if (!bottomWidget.isRoot() && !bottomWidget.mVerticalWrapVisited) {
                        findVerticalWrapRecursive(bottomWidget, flags2);
                    }
                }
                if (widget.mTop.mTarget != null && !topWidget.isRoot()) {
                    if (widget.mTop.mTarget.getType() == ConstraintAnchor.Type.TOP) {
                        distToTop += topWidget.mDistToTop - topWidget.getOptimizerWrapHeight();
                    } else if (widget.mTop.mTarget.getType() == ConstraintAnchor.Type.BOTTOM) {
                        distToTop += topWidget.mDistToTop;
                    }
                    widget.mTopHasCentered = topWidget.mTopHasCentered || !(topWidget.mTop.mTarget == null || topWidget.mTop.mTarget.mOwner == widget || topWidget.mBottom.mTarget == null || topWidget.mBottom.mTarget.mOwner == widget || topWidget.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT);
                    if (widget.mTopHasCentered && (topWidget.mBottom.mTarget == null || topWidget.mBottom.mTarget.mOwner != widget)) {
                        distToTop += distToTop - topWidget.mDistToTop;
                    }
                }
                if (widget.mBottom.mTarget != null && !bottomWidget.isRoot()) {
                    if (widget.mBottom.mTarget.getType() == ConstraintAnchor.Type.BOTTOM) {
                        distToBottom += bottomWidget.mDistToBottom - bottomWidget.getOptimizerWrapHeight();
                    } else if (widget.mBottom.mTarget.getType() == ConstraintAnchor.Type.TOP) {
                        distToBottom += bottomWidget.mDistToBottom;
                    }
                    if (bottomWidget.mBottomHasCentered || !(bottomWidget.mTop.mTarget == null || bottomWidget.mTop.mTarget.mOwner == widget || bottomWidget.mBottom.mTarget == null || bottomWidget.mBottom.mTarget.mOwner == widget || bottomWidget.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT)) {
                        z = true;
                    }
                    widget.mBottomHasCentered = z;
                    if (widget.mBottomHasCentered && (bottomWidget.mTop.mTarget == null || bottomWidget.mTop.mTarget.mOwner != widget)) {
                        distToBottom += distToBottom - bottomWidget.mDistToBottom;
                    }
                }
            }
            if (widget.getVisibility() == 8) {
                distToTop -= widget.mHeight;
                distToBottom -= widget.mHeight;
            }
            widget.mDistToTop = distToTop;
            widget.mDistToBottom = distToBottom;
            return;
        }
        flags2[0] = false;
    }

    public void findWrapSize(ArrayList<ConstraintWidget> children, boolean[] flags2) {
        ArrayList<ConstraintWidget> arrayList = children;
        boolean[] zArr = flags2;
        int maxTopDist = 0;
        int maxLeftDist = 0;
        int maxRightDist = 0;
        int maxBottomDist = 0;
        int maxConnectWidth = 0;
        int maxConnectHeight = 0;
        int size = children.size();
        char c = 0;
        zArr[0] = true;
        int j = 0;
        while (j < size) {
            ConstraintWidget widget = arrayList.get(j);
            if (!widget.isRoot()) {
                if (!widget.mHorizontalWrapVisited) {
                    findHorizontalWrapRecursive(widget, zArr);
                }
                if (!widget.mVerticalWrapVisited) {
                    findVerticalWrapRecursive(widget, zArr);
                }
                if (zArr[c]) {
                    int connectWidth = (widget.mDistToLeft + widget.mDistToRight) - widget.getWidth();
                    int connectHeight = (widget.mDistToTop + widget.mDistToBottom) - widget.getHeight();
                    if (widget.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
                        connectWidth = widget.getWidth() + widget.mLeft.mMargin + widget.mRight.mMargin;
                    }
                    if (widget.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
                        connectHeight = widget.getHeight() + widget.mTop.mMargin + widget.mBottom.mMargin;
                    }
                    if (widget.getVisibility() == 8) {
                        connectWidth = 0;
                        connectHeight = 0;
                    }
                    maxLeftDist = Math.max(maxLeftDist, widget.mDistToLeft);
                    maxRightDist = Math.max(maxRightDist, widget.mDistToRight);
                    maxBottomDist = Math.max(maxBottomDist, widget.mDistToBottom);
                    maxTopDist = Math.max(maxTopDist, widget.mDistToTop);
                    maxConnectWidth = Math.max(maxConnectWidth, connectWidth);
                    maxConnectHeight = Math.max(maxConnectHeight, connectHeight);
                } else {
                    return;
                }
            }
            j++;
            c = 0;
        }
        this.mWrapWidth = Math.max(this.mMinWidth, Math.max(Math.max(maxLeftDist, maxRightDist), maxConnectWidth));
        this.mWrapHeight = Math.max(this.mMinHeight, Math.max(Math.max(maxTopDist, maxBottomDist), maxConnectHeight));
        for (int j2 = 0; j2 < size; j2++) {
            ConstraintWidget child = arrayList.get(j2);
            child.mHorizontalWrapVisited = false;
            child.mVerticalWrapVisited = false;
            child.mLeftHasCentered = false;
            child.mRightHasCentered = false;
            child.mTopHasCentered = false;
            child.mBottomHasCentered = false;
        }
    }

    public int layoutFindGroups() {
        ConstraintAnchor anchor;
        int i = 1;
        int i2 = 2;
        int i3 = 3;
        int i4 = 4;
        ConstraintAnchor.Type[] dir = {ConstraintAnchor.Type.LEFT, ConstraintAnchor.Type.RIGHT, ConstraintAnchor.Type.TOP, ConstraintAnchor.Type.BASELINE, ConstraintAnchor.Type.BOTTOM};
        int label = 1;
        int size = this.mChildren.size();
        for (int j = 0; j < size; j++) {
            ConstraintWidget widget = (ConstraintWidget) this.mChildren.get(j);
            ConstraintAnchor anchor2 = widget.mLeft;
            if (anchor2.mTarget == null) {
                anchor2.mGroup = Integer.MAX_VALUE;
            } else if (setGroup(anchor2, label) == label) {
                label++;
            }
            ConstraintAnchor anchor3 = widget.mTop;
            if (anchor3.mTarget == null) {
                anchor3.mGroup = Integer.MAX_VALUE;
            } else if (setGroup(anchor3, label) == label) {
                label++;
            }
            ConstraintAnchor anchor4 = widget.mRight;
            if (anchor4.mTarget == null) {
                anchor4.mGroup = Integer.MAX_VALUE;
            } else if (setGroup(anchor4, label) == label) {
                label++;
            }
            ConstraintAnchor anchor5 = widget.mBottom;
            if (anchor5.mTarget == null) {
                anchor5.mGroup = Integer.MAX_VALUE;
            } else if (setGroup(anchor5, label) == label) {
                label++;
            }
            ConstraintAnchor anchor6 = widget.mBaseline;
            if (anchor6.mTarget == null) {
                anchor6.mGroup = Integer.MAX_VALUE;
            } else if (setGroup(anchor6, label) == label) {
                label++;
            }
        }
        boolean notDone = true;
        int count = 0;
        int fix = 0;
        while (notDone) {
            notDone = false;
            count++;
            int j2 = 0;
            while (j2 < size) {
                ConstraintWidget widget2 = (ConstraintWidget) this.mChildren.get(j2);
                int i5 = 0;
                while (i5 < dir.length) {
                    int i6 = C00862.f20x1d400623[dir[i5].ordinal()];
                    if (i6 == i) {
                        anchor = widget2.mLeft;
                    } else if (i6 == i2) {
                        anchor = widget2.mTop;
                    } else if (i6 == i3) {
                        anchor = widget2.mRight;
                    } else if (i6 != i4) {
                        anchor = i6 != 5 ? null : widget2.mBaseline;
                    } else {
                        anchor = widget2.mBottom;
                    }
                    ConstraintAnchor target = anchor.mTarget;
                    if (target != null) {
                        if (!(target.mOwner.getParent() == null || target.mGroup == anchor.mGroup)) {
                            int i7 = anchor.mGroup > target.mGroup ? target.mGroup : anchor.mGroup;
                            anchor.mGroup = i7;
                            target.mGroup = i7;
                            fix++;
                            notDone = true;
                        }
                        ConstraintAnchor opposite = target.getOpposite();
                        if (!(opposite == null || opposite.mGroup == anchor.mGroup)) {
                            int i8 = anchor.mGroup > opposite.mGroup ? opposite.mGroup : anchor.mGroup;
                            anchor.mGroup = i8;
                            opposite.mGroup = i8;
                            fix++;
                            notDone = true;
                        }
                    }
                    i5++;
                    i = 1;
                    i2 = 2;
                    i3 = 3;
                    i4 = 4;
                }
                j2++;
                i = 1;
                i2 = 2;
                i3 = 3;
                i4 = 4;
            }
            i = 1;
            i2 = 2;
            i3 = 3;
            i4 = 4;
        }
        int index = 0;
        int[] table = new int[((this.mChildren.size() * dir.length) + 1)];
        Arrays.fill(table, -1);
        for (int j3 = 0; j3 < size; j3++) {
            ConstraintWidget widget3 = (ConstraintWidget) this.mChildren.get(j3);
            ConstraintAnchor anchor7 = widget3.mLeft;
            if (anchor7.mGroup != Integer.MAX_VALUE) {
                int g = anchor7.mGroup;
                if (table[g] == -1) {
                    table[g] = index;
                    index++;
                }
                anchor7.mGroup = table[g];
            }
            ConstraintAnchor anchor8 = widget3.mTop;
            if (anchor8.mGroup != Integer.MAX_VALUE) {
                int g2 = anchor8.mGroup;
                if (table[g2] == -1) {
                    table[g2] = index;
                    index++;
                }
                anchor8.mGroup = table[g2];
            }
            ConstraintAnchor anchor9 = widget3.mRight;
            if (anchor9.mGroup != Integer.MAX_VALUE) {
                int g3 = anchor9.mGroup;
                if (table[g3] == -1) {
                    table[g3] = index;
                    index++;
                }
                anchor9.mGroup = table[g3];
            }
            ConstraintAnchor anchor10 = widget3.mBottom;
            if (anchor10.mGroup != Integer.MAX_VALUE) {
                int g4 = anchor10.mGroup;
                if (table[g4] == -1) {
                    table[g4] = index;
                    index++;
                }
                anchor10.mGroup = table[g4];
            }
            ConstraintAnchor anchor11 = widget3.mBaseline;
            if (anchor11.mGroup != Integer.MAX_VALUE) {
                int g5 = anchor11.mGroup;
                if (table[g5] == -1) {
                    table[g5] = index;
                    index++;
                }
                anchor11.mGroup = table[g5];
            }
        }
        return index;
    }

    /* renamed from: android.support.constraint.solver.widgets.ConstraintWidgetContainer$2 */
    static /* synthetic */ class C00862 {

        /* renamed from: $SwitchMap$android$support$constraint$solver$widgets$ConstraintAnchor$Type */
        static final /* synthetic */ int[] f20x1d400623;

        static {
            int[] iArr = new int[ConstraintAnchor.Type.values().length];
            f20x1d400623 = iArr;
            try {
                iArr[ConstraintAnchor.Type.LEFT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f20x1d400623[ConstraintAnchor.Type.TOP.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f20x1d400623[ConstraintAnchor.Type.RIGHT.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f20x1d400623[ConstraintAnchor.Type.BOTTOM.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f20x1d400623[ConstraintAnchor.Type.BASELINE.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    public void layoutWithGroup(int numOfGroups) {
        int prex = this.f17mX;
        int prey = this.f18mY;
        if (this.mParent != null) {
            if (this.mSnapshot == null) {
                this.mSnapshot = new Snapshot(this);
            }
            this.mSnapshot.updateFrom(this);
            this.f17mX = 0;
            this.f18mY = 0;
            resetAnchors();
            resetSolverVariables(this.mSystem.getCache());
        } else {
            this.f17mX = 0;
            this.f18mY = 0;
        }
        int count = this.mChildren.size();
        for (int i = 0; i < count; i++) {
            ConstraintWidget widget = (ConstraintWidget) this.mChildren.get(i);
            if (widget instanceof WidgetContainer) {
                ((WidgetContainer) widget).layout();
            }
        }
        this.mLeft.mGroup = 0;
        this.mRight.mGroup = 0;
        this.mTop.mGroup = 1;
        this.mBottom.mGroup = 1;
        this.mSystem.reset();
        for (int i2 = 0; i2 < numOfGroups; i2++) {
            try {
                addToSolver(this.mSystem, i2);
                this.mSystem.minimize();
                updateFromSolver(this.mSystem, i2);
            } catch (Exception e) {
                e.printStackTrace();
            }
            updateFromSolver(this.mSystem, -2);
        }
        if (this.mParent != null) {
            int width = getWidth();
            int height = getHeight();
            this.mSnapshot.applyTo(this);
            setWidth(width);
            setHeight(height);
        } else {
            this.f17mX = prex;
            this.f18mY = prey;
        }
        if (this == getRootConstraintContainer()) {
            updateDrawPosition();
        }
    }

    public boolean handlesInternalConstraints() {
        return false;
    }

    public ArrayList<Guideline> getVerticalGuidelines() {
        ArrayList<Guideline> guidelines = new ArrayList<>();
        int mChildrenSize = this.mChildren.size();
        for (int i = 0; i < mChildrenSize; i++) {
            ConstraintWidget widget = (ConstraintWidget) this.mChildren.get(i);
            if (widget instanceof Guideline) {
                Guideline guideline = (Guideline) widget;
                if (guideline.getOrientation() == 1) {
                    guidelines.add(guideline);
                }
            }
        }
        return guidelines;
    }

    public ArrayList<Guideline> getHorizontalGuidelines() {
        ArrayList<Guideline> guidelines = new ArrayList<>();
        int mChildrenSize = this.mChildren.size();
        for (int i = 0; i < mChildrenSize; i++) {
            ConstraintWidget widget = (ConstraintWidget) this.mChildren.get(i);
            if (widget instanceof Guideline) {
                Guideline guideline = (Guideline) widget;
                if (guideline.getOrientation() == 0) {
                    guidelines.add(guideline);
                }
            }
        }
        return guidelines;
    }

    public LinearSystem getSystem() {
        return this.mSystem;
    }

    private void resetChains() {
        this.mHorizontalChainsSize = 0;
        this.mVerticalChainsSize = 0;
    }

    /* access modifiers changed from: package-private */
    public void addChain(ConstraintWidget constraintWidget, int type) {
        ConstraintWidget widget = constraintWidget;
        if (type == 0) {
            while (widget.mLeft.mTarget != null && widget.mLeft.mTarget.mOwner.mRight.mTarget != null && widget.mLeft.mTarget.mOwner.mRight.mTarget == widget.mLeft && widget.mLeft.mTarget.mOwner != widget) {
                widget = widget.mLeft.mTarget.mOwner;
            }
            addHorizontalChain(widget);
        } else if (type == 1) {
            while (widget.mTop.mTarget != null && widget.mTop.mTarget.mOwner.mBottom.mTarget != null && widget.mTop.mTarget.mOwner.mBottom.mTarget == widget.mTop && widget.mTop.mTarget.mOwner != widget) {
                widget = widget.mTop.mTarget.mOwner;
            }
            addVerticalChain(widget);
        }
    }

    private void addHorizontalChain(ConstraintWidget widget) {
        int i = 0;
        while (true) {
            int i2 = this.mHorizontalChainsSize;
            if (i >= i2) {
                int i3 = i2 + 1;
                ConstraintWidget[] constraintWidgetArr = this.mHorizontalChainsArray;
                if (i3 >= constraintWidgetArr.length) {
                    this.mHorizontalChainsArray = (ConstraintWidget[]) Arrays.copyOf(constraintWidgetArr, constraintWidgetArr.length * 2);
                }
                ConstraintWidget[] constraintWidgetArr2 = this.mHorizontalChainsArray;
                int i4 = this.mHorizontalChainsSize;
                constraintWidgetArr2[i4] = widget;
                this.mHorizontalChainsSize = i4 + 1;
                return;
            } else if (this.mHorizontalChainsArray[i] != widget) {
                i++;
            } else {
                return;
            }
        }
    }

    private void addVerticalChain(ConstraintWidget widget) {
        int i = 0;
        while (true) {
            int i2 = this.mVerticalChainsSize;
            if (i >= i2) {
                int i3 = i2 + 1;
                ConstraintWidget[] constraintWidgetArr = this.mVerticalChainsArray;
                if (i3 >= constraintWidgetArr.length) {
                    this.mVerticalChainsArray = (ConstraintWidget[]) Arrays.copyOf(constraintWidgetArr, constraintWidgetArr.length * 2);
                }
                ConstraintWidget[] constraintWidgetArr2 = this.mVerticalChainsArray;
                int i4 = this.mVerticalChainsSize;
                constraintWidgetArr2[i4] = widget;
                this.mVerticalChainsSize = i4 + 1;
                return;
            } else if (this.mVerticalChainsArray[i] != widget) {
                i++;
            } else {
                return;
            }
        }
    }

    private int countMatchConstraintsChainedWidgets(LinearSystem system, ConstraintWidget[] chainEnds, ConstraintWidget widget, int direction, boolean[] flags2) {
        char c;
        char c2;
        LinearSystem linearSystem = system;
        ConstraintWidget widget2 = widget;
        int count = 0;
        flags2[0] = true;
        flags2[1] = false;
        ConstraintWidget constraintWidget = null;
        chainEnds[0] = null;
        chainEnds[2] = null;
        chainEnds[1] = null;
        chainEnds[3] = null;
        float f = 0.0f;
        int i = 5;
        if (direction == 0) {
            boolean fixedPosition = true;
            ConstraintWidget first = widget;
            ConstraintWidget last = null;
            if (!(widget2.mLeft.mTarget == null || widget2.mLeft.mTarget.mOwner == this)) {
                fixedPosition = false;
            }
            widget2.mHorizontalNextWidget = null;
            ConstraintWidget firstVisible = null;
            if (widget.getVisibility() != 8) {
                firstVisible = widget;
            }
            ConstraintWidget lastVisible = firstVisible;
            while (widget2.mRight.mTarget != null) {
                widget2.mHorizontalNextWidget = constraintWidget;
                if (widget2.getVisibility() != 8) {
                    if (firstVisible == null) {
                        firstVisible = widget2;
                    }
                    if (!(lastVisible == null || lastVisible == widget2)) {
                        lastVisible.mHorizontalNextWidget = widget2;
                    }
                    lastVisible = widget2;
                } else {
                    linearSystem.addEquality(widget2.mLeft.mSolverVariable, widget2.mLeft.mTarget.mSolverVariable, 0, 5);
                    linearSystem.addEquality(widget2.mRight.mSolverVariable, widget2.mLeft.mSolverVariable, 0, 5);
                }
                if (widget2.getVisibility() != 8 && widget2.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                    if (widget2.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                        flags2[0] = false;
                    }
                    if (widget2.mDimensionRatio <= f) {
                        flags2[0] = false;
                        int i2 = count + 1;
                        ConstraintWidget[] constraintWidgetArr = this.mMatchConstraintsChainedWidgets;
                        if (i2 >= constraintWidgetArr.length) {
                            this.mMatchConstraintsChainedWidgets = (ConstraintWidget[]) Arrays.copyOf(constraintWidgetArr, constraintWidgetArr.length * 2);
                        }
                        this.mMatchConstraintsChainedWidgets[count] = widget2;
                        count++;
                    }
                }
                if (widget2.mRight.mTarget.mOwner.mLeft.mTarget == null || widget2.mRight.mTarget.mOwner.mLeft.mTarget.mOwner != widget2 || widget2.mRight.mTarget.mOwner == widget2) {
                    break;
                }
                widget2 = widget2.mRight.mTarget.mOwner;
                last = widget2;
                constraintWidget = null;
                f = 0.0f;
            }
            if (!(widget2.mRight.mTarget == null || widget2.mRight.mTarget.mOwner == this)) {
                fixedPosition = false;
            }
            if (first.mLeft.mTarget == null || last.mRight.mTarget == null) {
                c2 = 1;
                flags2[1] = true;
            } else {
                c2 = 1;
            }
            first.mHorizontalChainFixedPosition = fixedPosition;
            last.mHorizontalNextWidget = null;
            chainEnds[0] = first;
            chainEnds[2] = firstVisible;
            chainEnds[c2] = last;
            chainEnds[3] = lastVisible;
        } else {
            boolean fixedPosition2 = true;
            ConstraintWidget first2 = widget;
            ConstraintWidget last2 = null;
            if (!(widget2.mTop.mTarget == null || widget2.mTop.mTarget.mOwner == this)) {
                fixedPosition2 = false;
            }
            widget2.mVerticalNextWidget = null;
            ConstraintWidget firstVisible2 = null;
            if (widget.getVisibility() != 8) {
                firstVisible2 = widget;
            }
            ConstraintWidget lastVisible2 = firstVisible2;
            while (widget2.mBottom.mTarget != null) {
                widget2.mVerticalNextWidget = null;
                if (widget2.getVisibility() != 8) {
                    if (firstVisible2 == null) {
                        firstVisible2 = widget2;
                    }
                    if (!(lastVisible2 == null || lastVisible2 == widget2)) {
                        lastVisible2.mVerticalNextWidget = widget2;
                    }
                    lastVisible2 = widget2;
                } else {
                    linearSystem.addEquality(widget2.mTop.mSolverVariable, widget2.mTop.mTarget.mSolverVariable, 0, i);
                    linearSystem.addEquality(widget2.mBottom.mSolverVariable, widget2.mTop.mSolverVariable, 0, i);
                }
                if (widget2.getVisibility() != 8 && widget2.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                    if (widget2.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                        flags2[0] = false;
                    }
                    if (widget2.mDimensionRatio <= 0.0f) {
                        flags2[0] = false;
                        int i3 = count + 1;
                        ConstraintWidget[] constraintWidgetArr2 = this.mMatchConstraintsChainedWidgets;
                        if (i3 >= constraintWidgetArr2.length) {
                            this.mMatchConstraintsChainedWidgets = (ConstraintWidget[]) Arrays.copyOf(constraintWidgetArr2, constraintWidgetArr2.length * 2);
                        }
                        this.mMatchConstraintsChainedWidgets[count] = widget2;
                        count++;
                    }
                }
                if (widget2.mBottom.mTarget.mOwner.mTop.mTarget == null || widget2.mBottom.mTarget.mOwner.mTop.mTarget.mOwner != widget2 || widget2.mBottom.mTarget.mOwner == widget2) {
                    break;
                }
                widget2 = widget2.mBottom.mTarget.mOwner;
                last2 = widget2;
                i = 5;
            }
            if (!(widget2.mBottom.mTarget == null || widget2.mBottom.mTarget.mOwner == this)) {
                fixedPosition2 = false;
            }
            if (first2.mTop.mTarget == null || last2.mBottom.mTarget == null) {
                c = 1;
                flags2[1] = true;
            } else {
                c = 1;
            }
            first2.mVerticalChainFixedPosition = fixedPosition2;
            last2.mVerticalNextWidget = null;
            chainEnds[0] = first2;
            chainEnds[2] = firstVisible2;
            chainEnds[c] = last2;
            chainEnds[3] = lastVisible2;
        }
        return count;
    }
}
