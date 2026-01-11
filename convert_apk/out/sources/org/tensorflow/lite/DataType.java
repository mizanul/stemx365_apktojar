package org.tensorflow.lite;

public enum DataType {
    FLOAT32(1),
    INT32(2),
    UINT8(3),
    INT64(4),
    STRING(5),
    BOOL(6),
    INT16(7),
    INT8(9);
    
    private static final DataType[] values = null;
    private final int value;

    static {
        values = values();
    }

    private DataType(int value2) {
        this.value = value2;
    }

    /* renamed from: org.tensorflow.lite.DataType$1 */
    static /* synthetic */ class C10661 {
        static final /* synthetic */ int[] $SwitchMap$org$tensorflow$lite$DataType = null;

        static {
            int[] iArr = new int[DataType.values().length];
            $SwitchMap$org$tensorflow$lite$DataType = iArr;
            try {
                iArr[DataType.FLOAT32.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$org$tensorflow$lite$DataType[DataType.INT32.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$org$tensorflow$lite$DataType[DataType.INT16.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$org$tensorflow$lite$DataType[DataType.INT8.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$org$tensorflow$lite$DataType[DataType.UINT8.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$org$tensorflow$lite$DataType[DataType.INT64.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$org$tensorflow$lite$DataType[DataType.BOOL.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$org$tensorflow$lite$DataType[DataType.STRING.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
        }
    }

    public int byteSize() {
        switch (C10661.$SwitchMap$org$tensorflow$lite$DataType[ordinal()]) {
            case 1:
            case 2:
                return 4;
            case 3:
                return 2;
            case 4:
            case 5:
                return 1;
            case 6:
                return 8;
            case 7:
                return -1;
            case 8:
                return -1;
            default:
                throw new IllegalArgumentException("DataType error: DataType " + this + " is not supported yet");
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: c */
    public int mo19415c() {
        return this.value;
    }
}
