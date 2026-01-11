package gov.nasa.arc.astrobee.types;

import ff_msgs.CommandConstants;

public enum FlashlightLocation {
    BACK(CommandConstants.PARAM_NAME_FLASHLIGHT_LOCATION_BACK),
    FRONT(CommandConstants.PARAM_NAME_FLASHLIGHT_LOCATION_FRONT);
    
    private final String m_value;

    private FlashlightLocation(String value) {
        this.m_value = value;
    }

    public String toString() {
        return this.m_value;
    }
}
