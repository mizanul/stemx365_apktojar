package gov.nasa.arc.astrobee.types;

public enum FlightMode {
    OFF("off"),
    QUIET("quiet"),
    NOMINAL("nominal"),
    DIFFICULT("difficult"),
    PRECISION("precision");
    
    private final String m_value;

    private FlightMode(String value) {
        this.m_value = value;
    }

    public String toString() {
        return this.m_value;
    }
}
