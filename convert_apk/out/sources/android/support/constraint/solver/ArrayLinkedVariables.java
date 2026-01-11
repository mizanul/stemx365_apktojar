package android.support.constraint.solver;

import android.support.constraint.solver.SolverVariable;
import java.io.PrintStream;
import java.util.Arrays;

public class ArrayLinkedVariables {
    private static final boolean DEBUG = false;
    private static final int NONE = -1;
    private int ROW_SIZE = 8;
    private SolverVariable candidate = null;
    int currentSize = 0;
    private int[] mArrayIndices = new int[8];
    private int[] mArrayNextIndices = new int[8];
    private float[] mArrayValues = new float[8];
    private final Cache mCache;
    private boolean mDidFillOnce = false;
    private int mHead = -1;
    private int mLast = -1;
    private final ArrayRow mRow;

    ArrayLinkedVariables(ArrayRow arrayRow, Cache cache) {
        this.mRow = arrayRow;
        this.mCache = cache;
    }

    public final void put(SolverVariable variable, float value) {
        if (value == 0.0f) {
            remove(variable);
        } else if (this.mHead == -1) {
            this.mHead = 0;
            this.mArrayValues[0] = value;
            this.mArrayIndices[0] = variable.f15id;
            this.mArrayNextIndices[this.mHead] = -1;
            this.currentSize++;
            if (!this.mDidFillOnce) {
                this.mLast++;
            }
        } else {
            int current = this.mHead;
            int previous = -1;
            int counter = 0;
            while (current != -1 && counter < this.currentSize) {
                if (this.mArrayIndices[current] == variable.f15id) {
                    this.mArrayValues[current] = value;
                    return;
                }
                if (this.mArrayIndices[current] < variable.f15id) {
                    previous = current;
                }
                current = this.mArrayNextIndices[current];
                counter++;
            }
            int i = this.mLast;
            int availableIndice = i + 1;
            if (this.mDidFillOnce) {
                int[] iArr = this.mArrayIndices;
                if (iArr[i] == -1) {
                    availableIndice = this.mLast;
                } else {
                    availableIndice = iArr.length;
                }
            }
            int[] iArr2 = this.mArrayIndices;
            if (availableIndice >= iArr2.length && this.currentSize < iArr2.length) {
                int i2 = 0;
                while (true) {
                    int[] iArr3 = this.mArrayIndices;
                    if (i2 >= iArr3.length) {
                        break;
                    } else if (iArr3[i2] == -1) {
                        availableIndice = i2;
                        break;
                    } else {
                        i2++;
                    }
                }
            }
            int[] iArr4 = this.mArrayIndices;
            if (availableIndice >= iArr4.length) {
                availableIndice = iArr4.length;
                int i3 = this.ROW_SIZE * 2;
                this.ROW_SIZE = i3;
                this.mDidFillOnce = false;
                this.mLast = availableIndice - 1;
                this.mArrayValues = Arrays.copyOf(this.mArrayValues, i3);
                this.mArrayIndices = Arrays.copyOf(this.mArrayIndices, this.ROW_SIZE);
                this.mArrayNextIndices = Arrays.copyOf(this.mArrayNextIndices, this.ROW_SIZE);
            }
            this.mArrayIndices[availableIndice] = variable.f15id;
            this.mArrayValues[availableIndice] = value;
            if (previous != -1) {
                int[] iArr5 = this.mArrayNextIndices;
                iArr5[availableIndice] = iArr5[previous];
                iArr5[previous] = availableIndice;
            } else {
                this.mArrayNextIndices[availableIndice] = this.mHead;
                this.mHead = availableIndice;
            }
            this.currentSize++;
            if (!this.mDidFillOnce) {
                this.mLast++;
            }
            if (this.currentSize >= this.mArrayIndices.length) {
                this.mDidFillOnce = true;
            }
        }
    }

    public final void add(SolverVariable variable, float value) {
        if (value != 0.0f) {
            if (this.mHead == -1) {
                this.mHead = 0;
                this.mArrayValues[0] = value;
                this.mArrayIndices[0] = variable.f15id;
                this.mArrayNextIndices[this.mHead] = -1;
                this.currentSize++;
                if (!this.mDidFillOnce) {
                    this.mLast++;
                    return;
                }
                return;
            }
            int current = this.mHead;
            int previous = -1;
            int counter = 0;
            while (current != -1 && counter < this.currentSize) {
                int idx = this.mArrayIndices[current];
                if (idx == variable.f15id) {
                    float[] fArr = this.mArrayValues;
                    fArr[current] = fArr[current] + value;
                    if (fArr[current] == 0.0f) {
                        if (current == this.mHead) {
                            this.mHead = this.mArrayNextIndices[current];
                        } else {
                            int[] iArr = this.mArrayNextIndices;
                            iArr[previous] = iArr[current];
                        }
                        this.mCache.mIndexedVariables[idx].removeClientEquation(this.mRow);
                        if (this.mDidFillOnce) {
                            this.mLast = current;
                        }
                        this.currentSize--;
                        return;
                    }
                    return;
                }
                if (this.mArrayIndices[current] < variable.f15id) {
                    previous = current;
                }
                current = this.mArrayNextIndices[current];
                counter++;
            }
            int i = this.mLast;
            int availableIndice = i + 1;
            if (this.mDidFillOnce) {
                int[] iArr2 = this.mArrayIndices;
                if (iArr2[i] == -1) {
                    availableIndice = this.mLast;
                } else {
                    availableIndice = iArr2.length;
                }
            }
            int[] iArr3 = this.mArrayIndices;
            if (availableIndice >= iArr3.length && this.currentSize < iArr3.length) {
                int i2 = 0;
                while (true) {
                    int[] iArr4 = this.mArrayIndices;
                    if (i2 >= iArr4.length) {
                        break;
                    } else if (iArr4[i2] == -1) {
                        availableIndice = i2;
                        break;
                    } else {
                        i2++;
                    }
                }
            }
            int[] iArr5 = this.mArrayIndices;
            if (availableIndice >= iArr5.length) {
                availableIndice = iArr5.length;
                int i3 = this.ROW_SIZE * 2;
                this.ROW_SIZE = i3;
                this.mDidFillOnce = false;
                this.mLast = availableIndice - 1;
                this.mArrayValues = Arrays.copyOf(this.mArrayValues, i3);
                this.mArrayIndices = Arrays.copyOf(this.mArrayIndices, this.ROW_SIZE);
                this.mArrayNextIndices = Arrays.copyOf(this.mArrayNextIndices, this.ROW_SIZE);
            }
            this.mArrayIndices[availableIndice] = variable.f15id;
            this.mArrayValues[availableIndice] = value;
            if (previous != -1) {
                int[] iArr6 = this.mArrayNextIndices;
                iArr6[availableIndice] = iArr6[previous];
                iArr6[previous] = availableIndice;
            } else {
                this.mArrayNextIndices[availableIndice] = this.mHead;
                this.mHead = availableIndice;
            }
            this.currentSize++;
            if (!this.mDidFillOnce) {
                this.mLast++;
            }
            int i4 = this.mLast;
            int[] iArr7 = this.mArrayIndices;
            if (i4 >= iArr7.length) {
                this.mDidFillOnce = true;
                this.mLast = iArr7.length - 1;
            }
        }
    }

    public final float remove(SolverVariable variable) {
        if (this.candidate == variable) {
            this.candidate = null;
        }
        if (this.mHead == -1) {
            return 0.0f;
        }
        int current = this.mHead;
        int previous = -1;
        int counter = 0;
        while (current != -1 && counter < this.currentSize) {
            int idx = this.mArrayIndices[current];
            if (idx == variable.f15id) {
                if (current == this.mHead) {
                    this.mHead = this.mArrayNextIndices[current];
                } else {
                    int[] iArr = this.mArrayNextIndices;
                    iArr[previous] = iArr[current];
                }
                this.mCache.mIndexedVariables[idx].removeClientEquation(this.mRow);
                this.currentSize--;
                this.mArrayIndices[current] = -1;
                if (this.mDidFillOnce) {
                    this.mLast = current;
                }
                return this.mArrayValues[current];
            }
            previous = current;
            current = this.mArrayNextIndices[current];
            counter++;
        }
        return 0.0f;
    }

    public final void clear() {
        this.mHead = -1;
        this.mLast = -1;
        this.mDidFillOnce = false;
        this.currentSize = 0;
    }

    /* access modifiers changed from: package-private */
    public final boolean containsKey(SolverVariable variable) {
        if (this.mHead == -1) {
            return false;
        }
        int current = this.mHead;
        int counter = 0;
        while (current != -1 && counter < this.currentSize) {
            if (this.mArrayIndices[current] == variable.f15id) {
                return true;
            }
            current = this.mArrayNextIndices[current];
            counter++;
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public boolean hasAtLeastOnePositiveVariable() {
        int current = this.mHead;
        int counter = 0;
        while (current != -1 && counter < this.currentSize) {
            if (this.mArrayValues[current] > 0.0f) {
                return true;
            }
            current = this.mArrayNextIndices[current];
            counter++;
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public void invert() {
        int current = this.mHead;
        int counter = 0;
        while (current != -1 && counter < this.currentSize) {
            float[] fArr = this.mArrayValues;
            fArr[current] = fArr[current] * -1.0f;
            current = this.mArrayNextIndices[current];
            counter++;
        }
    }

    /* access modifiers changed from: package-private */
    public void divideByAmount(float amount) {
        int current = this.mHead;
        int counter = 0;
        while (current != -1 && counter < this.currentSize) {
            float[] fArr = this.mArrayValues;
            fArr[current] = fArr[current] / amount;
            current = this.mArrayNextIndices[current];
            counter++;
        }
    }

    /* access modifiers changed from: package-private */
    public void updateClientEquations(ArrayRow row) {
        int current = this.mHead;
        int counter = 0;
        while (current != -1 && counter < this.currentSize) {
            this.mCache.mIndexedVariables[this.mArrayIndices[current]].addClientEquation(row);
            current = this.mArrayNextIndices[current];
            counter++;
        }
    }

    /* access modifiers changed from: package-private */
    public SolverVariable pickPivotCandidate() {
        SolverVariable restrictedCandidate = null;
        SolverVariable unrestrictedCandidate = null;
        int current = this.mHead;
        int counter = 0;
        while (current != -1 && counter < this.currentSize) {
            float[] fArr = this.mArrayValues;
            float amount = fArr[current];
            if (amount < 0.0f) {
                if (amount > (-0.001f)) {
                    fArr[current] = 0.0f;
                    amount = 0.0f;
                }
            } else if (amount < 0.001f) {
                fArr[current] = 0.0f;
                amount = 0.0f;
            }
            if (amount != 0.0f) {
                SolverVariable variable = this.mCache.mIndexedVariables[this.mArrayIndices[current]];
                if (variable.mType == SolverVariable.Type.UNRESTRICTED) {
                    if (amount < 0.0f) {
                        return variable;
                    }
                    if (unrestrictedCandidate == null) {
                        unrestrictedCandidate = variable;
                    }
                } else if (amount < 0.0f && (restrictedCandidate == null || variable.strength < restrictedCandidate.strength)) {
                    restrictedCandidate = variable;
                }
            }
            current = this.mArrayNextIndices[current];
            counter++;
        }
        if (unrestrictedCandidate != null) {
            return unrestrictedCandidate;
        }
        return restrictedCandidate;
    }

    /* access modifiers changed from: package-private */
    public void updateFromRow(ArrayRow self, ArrayRow definition) {
        int current = this.mHead;
        int counter = 0;
        while (current != -1 && counter < this.currentSize) {
            if (this.mArrayIndices[current] == definition.variable.f15id) {
                float value = this.mArrayValues[current];
                remove(definition.variable);
                ArrayLinkedVariables definitionVariables = definition.variables;
                int definitionCurrent = definitionVariables.mHead;
                int definitionCounter = 0;
                while (definitionCurrent != -1 && definitionCounter < definitionVariables.currentSize) {
                    add(this.mCache.mIndexedVariables[definitionVariables.mArrayIndices[definitionCurrent]], definitionVariables.mArrayValues[definitionCurrent] * value);
                    definitionCurrent = definitionVariables.mArrayNextIndices[definitionCurrent];
                    definitionCounter++;
                }
                self.constantValue += definition.constantValue * value;
                definition.variable.removeClientEquation(self);
                current = this.mHead;
                counter = 0;
            } else {
                current = this.mArrayNextIndices[current];
                counter++;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void updateFromSystem(ArrayRow self, ArrayRow[] rows) {
        int current = this.mHead;
        int counter = 0;
        while (current != -1 && counter < this.currentSize) {
            SolverVariable variable = this.mCache.mIndexedVariables[this.mArrayIndices[current]];
            if (variable.definitionId != -1) {
                float value = this.mArrayValues[current];
                remove(variable);
                ArrayRow definition = rows[variable.definitionId];
                if (!definition.isSimpleDefinition) {
                    ArrayLinkedVariables definitionVariables = definition.variables;
                    int definitionCurrent = definitionVariables.mHead;
                    int definitionCounter = 0;
                    while (definitionCurrent != -1 && definitionCounter < definitionVariables.currentSize) {
                        add(this.mCache.mIndexedVariables[definitionVariables.mArrayIndices[definitionCurrent]], definitionVariables.mArrayValues[definitionCurrent] * value);
                        definitionCurrent = definitionVariables.mArrayNextIndices[definitionCurrent];
                        definitionCounter++;
                    }
                }
                self.constantValue += definition.constantValue * value;
                definition.variable.removeClientEquation(self);
                current = this.mHead;
                counter = 0;
            } else {
                current = this.mArrayNextIndices[current];
                counter++;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public SolverVariable getPivotCandidate() {
        SolverVariable solverVariable = this.candidate;
        if (solverVariable != null) {
            return solverVariable;
        }
        int current = this.mHead;
        int counter = 0;
        SolverVariable pivot = null;
        while (current != -1 && counter < this.currentSize) {
            if (this.mArrayValues[current] < 0.0f) {
                SolverVariable v = this.mCache.mIndexedVariables[this.mArrayIndices[current]];
                if (pivot == null || pivot.strength < v.strength) {
                    pivot = v;
                }
            }
            current = this.mArrayNextIndices[current];
            counter++;
        }
        return pivot;
    }

    /* access modifiers changed from: package-private */
    public final SolverVariable getVariable(int index) {
        int current = this.mHead;
        int counter = 0;
        while (current != -1 && counter < this.currentSize) {
            if (counter == index) {
                return this.mCache.mIndexedVariables[this.mArrayIndices[current]];
            }
            current = this.mArrayNextIndices[current];
            counter++;
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public final float getVariableValue(int index) {
        int current = this.mHead;
        int counter = 0;
        while (current != -1 && counter < this.currentSize) {
            if (counter == index) {
                return this.mArrayValues[current];
            }
            current = this.mArrayNextIndices[current];
            counter++;
        }
        return 0.0f;
    }

    public final float get(SolverVariable v) {
        int current = this.mHead;
        int counter = 0;
        while (current != -1 && counter < this.currentSize) {
            if (this.mArrayIndices[current] == v.f15id) {
                return this.mArrayValues[current];
            }
            current = this.mArrayNextIndices[current];
            counter++;
        }
        return 0.0f;
    }

    /* access modifiers changed from: package-private */
    public int sizeInBytes() {
        return 0 + (this.mArrayIndices.length * 4 * 3) + 36;
    }

    public void display() {
        int count = this.currentSize;
        System.out.print("{ ");
        for (int i = 0; i < count; i++) {
            SolverVariable v = getVariable(i);
            if (v != null) {
                PrintStream printStream = System.out;
                printStream.print(v + " = " + getVariableValue(i) + " ");
            }
        }
        System.out.println(" }");
    }

    public String toString() {
        String result = "";
        int current = this.mHead;
        int counter = 0;
        while (current != -1 && counter < this.currentSize) {
            result = ((result + " -> ") + this.mArrayValues[current] + " : ") + this.mCache.mIndexedVariables[this.mArrayIndices[current]];
            current = this.mArrayNextIndices[current];
            counter++;
        }
        return result;
    }
}
