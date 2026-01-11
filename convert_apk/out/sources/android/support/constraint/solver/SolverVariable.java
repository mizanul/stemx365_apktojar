package android.support.constraint.solver;

import java.util.Arrays;

public class SolverVariable {
    private static final boolean INTERNAL_DEBUG = false;
    static final int MAX_STRENGTH = 6;
    public static final int STRENGTH_EQUALITY = 5;
    public static final int STRENGTH_HIGH = 3;
    public static final int STRENGTH_HIGHEST = 4;
    public static final int STRENGTH_LOW = 1;
    public static final int STRENGTH_MEDIUM = 2;
    public static final int STRENGTH_NONE = 0;
    private static int uniqueId = 1;
    public float computedValue;
    int definitionId = -1;

    /* renamed from: id */
    public int f15id = -1;
    ArrayRow[] mClientEquations = new ArrayRow[8];
    int mClientEquationsCount = 0;
    private String mName;
    Type mType;
    public int strength = 0;
    float[] strengthVector = new float[6];

    public enum Type {
        UNRESTRICTED,
        CONSTANT,
        SLACK,
        ERROR,
        UNKNOWN
    }

    /* renamed from: android.support.constraint.solver.SolverVariable$1 */
    static /* synthetic */ class C00831 {
        static final /* synthetic */ int[] $SwitchMap$android$support$constraint$solver$SolverVariable$Type;

        static {
            int[] iArr = new int[Type.values().length];
            $SwitchMap$android$support$constraint$solver$SolverVariable$Type = iArr;
            try {
                iArr[Type.UNRESTRICTED.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$android$support$constraint$solver$SolverVariable$Type[Type.CONSTANT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$android$support$constraint$solver$SolverVariable$Type[Type.SLACK.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$android$support$constraint$solver$SolverVariable$Type[Type.ERROR.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    private static String getUniqueName(Type type) {
        uniqueId++;
        int i = C00831.$SwitchMap$android$support$constraint$solver$SolverVariable$Type[type.ordinal()];
        if (i == 1) {
            return "U" + uniqueId;
        } else if (i == 2) {
            return "C" + uniqueId;
        } else if (i == 3) {
            return "S" + uniqueId;
        } else if (i != 4) {
            return "V" + uniqueId;
        } else {
            return "e" + uniqueId;
        }
    }

    public SolverVariable(String name, Type type) {
        this.mName = name;
        this.mType = type;
    }

    public SolverVariable(Type type) {
        this.mType = type;
    }

    /* access modifiers changed from: package-private */
    public void clearStrengths() {
        for (int i = 0; i < 6; i++) {
            this.strengthVector[i] = 0.0f;
        }
    }

    /* access modifiers changed from: package-private */
    public String strengthsToString() {
        String representation = this + "[";
        for (int j = 0; j < this.strengthVector.length; j++) {
            String representation2 = representation + this.strengthVector[j];
            if (j < this.strengthVector.length - 1) {
                representation = representation2 + ", ";
            } else {
                representation = representation2 + "] ";
            }
        }
        return representation;
    }

    /* access modifiers changed from: package-private */
    public void addClientEquation(ArrayRow equation) {
        int i = 0;
        while (true) {
            int i2 = this.mClientEquationsCount;
            if (i >= i2) {
                ArrayRow[] arrayRowArr = this.mClientEquations;
                if (i2 >= arrayRowArr.length) {
                    this.mClientEquations = (ArrayRow[]) Arrays.copyOf(arrayRowArr, arrayRowArr.length * 2);
                }
                ArrayRow[] arrayRowArr2 = this.mClientEquations;
                int i3 = this.mClientEquationsCount;
                arrayRowArr2[i3] = equation;
                this.mClientEquationsCount = i3 + 1;
                return;
            } else if (this.mClientEquations[i] != equation) {
                i++;
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void removeClientEquation(ArrayRow equation) {
        int i = 0;
        while (i < this.mClientEquationsCount) {
            if (this.mClientEquations[i] == equation) {
                int j = 0;
                while (true) {
                    int i2 = this.mClientEquationsCount;
                    if (j < (i2 - i) - 1) {
                        ArrayRow[] arrayRowArr = this.mClientEquations;
                        arrayRowArr[i + j] = arrayRowArr[i + j + 1];
                        j++;
                    } else {
                        this.mClientEquationsCount = i2 - 1;
                        return;
                    }
                }
            } else {
                i++;
            }
        }
    }

    public void reset() {
        this.mName = null;
        this.mType = Type.UNKNOWN;
        this.strength = 0;
        this.f15id = -1;
        this.definitionId = -1;
        this.computedValue = 0.0f;
        this.mClientEquationsCount = 0;
    }

    public String getName() {
        return this.mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public void setType(Type type) {
        this.mType = type;
    }

    public String toString() {
        return "" + this.mName;
    }
}
