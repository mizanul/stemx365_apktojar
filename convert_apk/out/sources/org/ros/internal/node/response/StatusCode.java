package org.ros.internal.node.response;

public enum StatusCode {
    ERROR(-1),
    FAILURE(0),
    SUCCESS(1);
    
    private final int intValue;

    private StatusCode(int value) {
        this.intValue = value;
    }

    public int toInt() {
        return this.intValue;
    }

    public static StatusCode fromInt(int intValue2) {
        if (intValue2 == -1) {
            return ERROR;
        }
        if (intValue2 != 1) {
            return FAILURE;
        }
        return SUCCESS;
    }

    /* renamed from: org.ros.internal.node.response.StatusCode$1 */
    static /* synthetic */ class C10171 {
        static final /* synthetic */ int[] $SwitchMap$org$ros$internal$node$response$StatusCode = null;

        static {
            int[] iArr = new int[StatusCode.values().length];
            $SwitchMap$org$ros$internal$node$response$StatusCode = iArr;
            try {
                iArr[StatusCode.ERROR.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$org$ros$internal$node$response$StatusCode[StatusCode.SUCCESS.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$org$ros$internal$node$response$StatusCode[StatusCode.FAILURE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    public String toString() {
        int i = C10171.$SwitchMap$org$ros$internal$node$response$StatusCode[ordinal()];
        if (i == 1) {
            return "Error";
        }
        if (i != 2) {
            return "Failure";
        }
        return "Success";
    }
}
