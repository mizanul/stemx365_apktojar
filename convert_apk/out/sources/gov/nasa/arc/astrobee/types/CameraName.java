package gov.nasa.arc.astrobee.types;

import ff_msgs.CommandConstants;

public enum CameraName {
    SCIENCE(CommandConstants.PARAM_NAME_CAMERA_NAME_SCI),
    NAVIGATION(CommandConstants.PARAM_NAME_CAMERA_NAME_NAV),
    HAZARD(CommandConstants.PARAM_NAME_CAMERA_NAME_HAZ),
    DOCK(CommandConstants.PARAM_NAME_CAMERA_NAME_DOCK),
    PERCH("Perch");
    
    private final String m_value;

    private CameraName(String value) {
        this.m_value = value;
    }

    public String toString() {
        return this.m_value;
    }
}
