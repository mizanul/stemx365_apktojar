package gov.nasa.arc.astrobee.types;

import ff_msgs.CommandConstants;

public enum CameraMode {
    BOTH("Both"),
    RECORDING(CommandConstants.PARAM_NAME_CAMERA_MODE_RECORDING),
    STREAMING(CommandConstants.PARAM_NAME_CAMERA_MODE_STREAMING);
    
    private final String m_value;

    private CameraMode(String value) {
        this.m_value = value;
    }

    public String toString() {
        return this.m_value;
    }
}
