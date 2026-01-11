package gov.nasa.arc.astrobee;

public interface Result {
    String getMessage();

    Status getStatus();

    boolean hasSucceeded();

    public enum Status {
        NOT(0),
        OK(1),
        BAD_SYNTAX(2),
        EXEC_FAILED(3),
        CANCELED(4);
        
        private final byte m_value;

        private Status(int value) {
            this.m_value = (byte) value;
        }

        public byte getValue() {
            return this.m_value;
        }

        public static Status fromValue(byte value) {
            for (Status s : values()) {
                if (s.m_value == value) {
                    return s;
                }
            }
            throw new IllegalArgumentException("No such Status with that value");
        }

        public String toString() {
            return name() + "(" + this.m_value + ")";
        }
    }
}
