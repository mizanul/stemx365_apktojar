package gov.nasa.arc.astrobee.android.p000gs;

/* renamed from: gov.nasa.arc.astrobee.android.gs.MessageType */
public enum MessageType {
    MESSENGER(0),
    PATH(1),
    STOP(2),
    CMD(3),
    JSON(4),
    STRING(5),
    BINARY(6);
    
    private final int mValue;

    private MessageType(int value) {
        this.mValue = value;
    }

    public int toInt() {
        return this.mValue;
    }
}
