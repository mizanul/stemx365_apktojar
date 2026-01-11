package android.support.constraint.solver.widgets;

import android.support.constraint.solver.Cache;
import android.support.constraint.solver.SolverVariable;
import java.util.ArrayList;
import java.util.HashSet;

public class ConstraintAnchor {
    private static final boolean ALLOW_BINARY = false;
    public static final int ANY_GROUP = Integer.MAX_VALUE;
    public static final int APPLY_GROUP_RESULTS = -2;
    public static final int AUTO_CONSTRAINT_CREATOR = 2;
    public static final int SCOUT_CREATOR = 1;
    private static final int UNSET_GONE_MARGIN = -1;
    public static final int USER_CREATOR = 0;
    public static final boolean USE_CENTER_ANCHOR = false;
    private int mConnectionCreator = 0;
    private ConnectionType mConnectionType = ConnectionType.RELAXED;
    int mGoneMargin = -1;
    int mGroup = Integer.MAX_VALUE;
    public int mMargin = 0;
    final ConstraintWidget mOwner;
    SolverVariable mSolverVariable;
    private Strength mStrength = Strength.NONE;
    ConstraintAnchor mTarget;
    final Type mType;

    public enum ConnectionType {
        RELAXED,
        STRICT
    }

    public enum Strength {
        NONE,
        STRONG,
        WEAK
    }

    public enum Type {
        NONE,
        LEFT,
        TOP,
        RIGHT,
        BOTTOM,
        BASELINE,
        CENTER,
        CENTER_X,
        CENTER_Y
    }

    public ConstraintAnchor(ConstraintWidget owner, Type type) {
        this.mOwner = owner;
        this.mType = type;
    }

    public SolverVariable getSolverVariable() {
        return this.mSolverVariable;
    }

    public void resetSolverVariable(Cache cache) {
        SolverVariable solverVariable = this.mSolverVariable;
        if (solverVariable == null) {
            this.mSolverVariable = new SolverVariable(SolverVariable.Type.UNRESTRICTED);
        } else {
            solverVariable.reset();
        }
    }

    public void setGroup(int group) {
        this.mGroup = group;
    }

    public int getGroup() {
        return this.mGroup;
    }

    public ConstraintWidget getOwner() {
        return this.mOwner;
    }

    public Type getType() {
        return this.mType;
    }

    public int getMargin() {
        ConstraintAnchor constraintAnchor;
        if (this.mOwner.getVisibility() == 8) {
            return 0;
        }
        if (this.mGoneMargin <= -1 || (constraintAnchor = this.mTarget) == null || constraintAnchor.mOwner.getVisibility() != 8) {
            return this.mMargin;
        }
        return this.mGoneMargin;
    }

    public Strength getStrength() {
        return this.mStrength;
    }

    public ConstraintAnchor getTarget() {
        return this.mTarget;
    }

    public ConnectionType getConnectionType() {
        return this.mConnectionType;
    }

    public void setConnectionType(ConnectionType type) {
        this.mConnectionType = type;
    }

    public int getConnectionCreator() {
        return this.mConnectionCreator;
    }

    public void setConnectionCreator(int creator) {
        this.mConnectionCreator = creator;
    }

    public void reset() {
        this.mTarget = null;
        this.mMargin = 0;
        this.mGoneMargin = -1;
        this.mStrength = Strength.STRONG;
        this.mConnectionCreator = 0;
        this.mConnectionType = ConnectionType.RELAXED;
    }

    public boolean connect(ConstraintAnchor toAnchor, int margin, Strength strength, int creator) {
        return connect(toAnchor, margin, -1, strength, creator, false);
    }

    public boolean connect(ConstraintAnchor toAnchor, int margin, int goneMargin, Strength strength, int creator, boolean forceConnection) {
        if (toAnchor == null) {
            this.mTarget = null;
            this.mMargin = 0;
            this.mGoneMargin = -1;
            this.mStrength = Strength.NONE;
            this.mConnectionCreator = 2;
            return true;
        } else if (!forceConnection && !isValidConnection(toAnchor)) {
            return false;
        } else {
            this.mTarget = toAnchor;
            if (margin > 0) {
                this.mMargin = margin;
            } else {
                this.mMargin = 0;
            }
            this.mGoneMargin = goneMargin;
            this.mStrength = strength;
            this.mConnectionCreator = creator;
            return true;
        }
    }

    public boolean connect(ConstraintAnchor toAnchor, int margin, int creator) {
        return connect(toAnchor, margin, -1, Strength.STRONG, creator, false);
    }

    public boolean connect(ConstraintAnchor toAnchor, int margin) {
        return connect(toAnchor, margin, -1, Strength.STRONG, 0, false);
    }

    public boolean isConnected() {
        return this.mTarget != null;
    }

    public boolean isValidConnection(ConstraintAnchor anchor) {
        boolean isCompatible = false;
        if (anchor == null) {
            return false;
        }
        Type target = anchor.getType();
        Type type = this.mType;
        if (target != type) {
            int i = C00841.f16x1d400623[this.mType.ordinal()];
            if (i != 1) {
                if (i == 2 || i == 3) {
                    boolean isCompatible2 = target == Type.LEFT || target == Type.RIGHT;
                    if (!(anchor.getOwner() instanceof Guideline)) {
                        return isCompatible2;
                    }
                    if (isCompatible2 || target == Type.CENTER_X) {
                        isCompatible = true;
                    }
                    return isCompatible;
                } else if (i != 4 && i != 5) {
                    return false;
                } else {
                    boolean isCompatible3 = target == Type.TOP || target == Type.BOTTOM;
                    if (!(anchor.getOwner() instanceof Guideline)) {
                        return isCompatible3;
                    }
                    if (isCompatible3 || target == Type.CENTER_Y) {
                        isCompatible = true;
                    }
                    return isCompatible;
                }
            } else if (target == Type.BASELINE || target == Type.CENTER_X || target == Type.CENTER_Y) {
                return false;
            } else {
                return true;
            }
        } else if (type == Type.CENTER) {
            return false;
        } else {
            if (this.mType != Type.BASELINE || (anchor.getOwner().hasBaseline() && getOwner().hasBaseline())) {
                return true;
            }
            return false;
        }
    }

    public boolean isSideAnchor() {
        int i = C00841.f16x1d400623[this.mType.ordinal()];
        if (i == 2 || i == 3 || i == 4 || i == 5) {
            return true;
        }
        return false;
    }

    public boolean isSimilarDimensionConnection(ConstraintAnchor anchor) {
        Type target = anchor.getType();
        if (target == this.mType) {
            return true;
        }
        switch (C00841.f16x1d400623[this.mType.ordinal()]) {
            case 1:
                if (target != Type.BASELINE) {
                    return true;
                }
                return false;
            case 2:
            case 3:
            case 6:
                if (target == Type.LEFT || target == Type.RIGHT || target == Type.CENTER_X) {
                    return true;
                }
                return false;
            case 4:
            case 5:
            case 7:
            case 8:
                if (target == Type.TOP || target == Type.BOTTOM || target == Type.CENTER_Y || target == Type.BASELINE) {
                    return true;
                }
                return false;
            default:
                return false;
        }
    }

    public void setStrength(Strength strength) {
        if (isConnected()) {
            this.mStrength = strength;
        }
    }

    public void setMargin(int margin) {
        if (isConnected()) {
            this.mMargin = margin;
        }
    }

    public void setGoneMargin(int margin) {
        if (isConnected()) {
            this.mGoneMargin = margin;
        }
    }

    public boolean isVerticalAnchor() {
        int i = C00841.f16x1d400623[this.mType.ordinal()];
        if (i == 1 || i == 2 || i == 3 || i == 6) {
            return false;
        }
        return true;
    }

    public String toString() {
        String str;
        HashSet<ConstraintAnchor> visited = new HashSet<>();
        StringBuilder sb = new StringBuilder();
        sb.append(this.mOwner.getDebugName());
        sb.append(":");
        sb.append(this.mType.toString());
        if (this.mTarget != null) {
            str = " connected to " + this.mTarget.toString(visited);
        } else {
            str = "";
        }
        sb.append(str);
        return sb.toString();
    }

    private String toString(HashSet<ConstraintAnchor> visited) {
        String str;
        if (!visited.add(this)) {
            return "<-";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(this.mOwner.getDebugName());
        sb.append(":");
        sb.append(this.mType.toString());
        if (this.mTarget != null) {
            str = " connected to " + this.mTarget.toString(visited);
        } else {
            str = "";
        }
        sb.append(str);
        return sb.toString();
    }

    public int getSnapPriorityLevel() {
        int i = C00841.f16x1d400623[this.mType.ordinal()];
        if (i == 1) {
            return 3;
        }
        if (i == 2 || i == 3 || i == 7) {
            return 1;
        }
        if (i != 8) {
            return 0;
        }
        return 2;
    }

    public int getPriorityLevel() {
        int i = C00841.f16x1d400623[this.mType.ordinal()];
        if (i == 1 || i == 2 || i == 3 || i == 4 || i == 5) {
            return 2;
        }
        if (i != 8) {
            return 0;
        }
        return 1;
    }

    public boolean isSnapCompatibleWith(ConstraintAnchor anchor) {
        if (this.mType == Type.CENTER) {
            return false;
        }
        if (this.mType == anchor.getType()) {
            return true;
        }
        switch (this.mType) {
            case LEFT:
                int i = C00841.f16x1d400623[anchor.getType().ordinal()];
                if (i == 3 || i == 6) {
                    return true;
                }
                return false;
            case RIGHT:
                int i2 = C00841.f16x1d400623[anchor.getType().ordinal()];
                if (i2 == 2 || i2 == 6) {
                    return true;
                }
                return false;
            case TOP:
                int i3 = C00841.f16x1d400623[anchor.getType().ordinal()];
                if (i3 == 5 || i3 == 7) {
                    return true;
                }
                return false;
            case BOTTOM:
                int i4 = C00841.f16x1d400623[anchor.getType().ordinal()];
                if (i4 == 4 || i4 == 7) {
                    return true;
                }
                return false;
            case CENTER_X:
                int i5 = C00841.f16x1d400623[anchor.getType().ordinal()];
                if (i5 == 2 || i5 == 3) {
                    return true;
                }
                return false;
            case CENTER_Y:
                int i6 = C00841.f16x1d400623[anchor.getType().ordinal()];
                if (i6 == 4 || i6 == 5) {
                    return true;
                }
                return false;
            default:
                return false;
        }
    }

    public boolean isConnectionAllowed(ConstraintWidget target, ConstraintAnchor anchor) {
        return isConnectionAllowed(target);
    }

    public boolean isConnectionAllowed(ConstraintWidget target) {
        if (isConnectionToMe(target, new HashSet<>())) {
            return false;
        }
        ConstraintWidget parent = getOwner().getParent();
        if (parent == target || target.getParent() == parent) {
            return true;
        }
        return false;
    }

    private boolean isConnectionToMe(ConstraintWidget target, HashSet<ConstraintWidget> checked) {
        if (checked.contains(target)) {
            return false;
        }
        checked.add(target);
        if (target == getOwner()) {
            return true;
        }
        ArrayList<ConstraintAnchor> targetAnchors = target.getAnchors();
        int targetAnchorsSize = targetAnchors.size();
        for (int i = 0; i < targetAnchorsSize; i++) {
            ConstraintAnchor anchor = targetAnchors.get(i);
            if (anchor.isSimilarDimensionConnection(this) && anchor.isConnected() && isConnectionToMe(anchor.getTarget().getOwner(), checked)) {
                return true;
            }
        }
        return false;
    }

    public final ConstraintAnchor getOpposite() {
        int i = C00841.f16x1d400623[this.mType.ordinal()];
        if (i == 2) {
            return this.mOwner.mRight;
        }
        if (i == 3) {
            return this.mOwner.mLeft;
        }
        if (i == 4) {
            return this.mOwner.mBottom;
        }
        if (i != 5) {
            return null;
        }
        return this.mOwner.mTop;
    }
}
