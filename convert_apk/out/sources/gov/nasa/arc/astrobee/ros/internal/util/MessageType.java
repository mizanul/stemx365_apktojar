package gov.nasa.arc.astrobee.ros.internal.util;

public enum MessageType {
    MESSENGER(0),
    STOP(1),
    CMD(2),
    JSON(3),
    STRING(4),
    BINARY(5);
    
    private final int mValue;

    private MessageType(int value) {
        this.mValue = value;
    }

    public int toInt() {
        return this.mValue;
    }

    public byte toByte() {
        return (byte) this.mValue;
    }
}
