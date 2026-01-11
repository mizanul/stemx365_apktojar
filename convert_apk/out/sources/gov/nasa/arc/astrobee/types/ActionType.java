package gov.nasa.arc.astrobee.types;

import ff_msgs.CommandConstants;

public enum ActionType {
    PAN(CommandConstants.PARAM_NAME_ACTION_TYPE_PAN),
    TILT(CommandConstants.PARAM_NAME_ACTION_TYPE_TILT),
    BOTH("Both");
    
    private final String m_value;

    private ActionType(String value) {
        this.m_value = value;
    }

    public String toString() {
        return this.m_value;
    }
}
