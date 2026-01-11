package gov.nasa.arc.astrobee.types;

import ff_msgs.CommandConstants;

public enum CameraResolution {
    R224X171(CommandConstants.PARAM_NAME_CAMERA_RESOLUTION_224X171),
    R320X240(CommandConstants.PARAM_NAME_CAMERA_RESOLUTION_320X240),
    R480X270(CommandConstants.PARAM_NAME_CAMERA_RESOLUTION_480X270),
    R640X480(CommandConstants.PARAM_NAME_CAMERA_RESOLUTION_640X480),
    R960X540(CommandConstants.PARAM_NAME_CAMERA_RESOLUTION_960X540),
    R1024X768(CommandConstants.PARAM_NAME_CAMERA_RESOLUTION_1024X768),
    R1280X720(CommandConstants.PARAM_NAME_CAMERA_RESOLUTION_1280X720),
    R1280X960(CommandConstants.PARAM_NAME_CAMERA_RESOLUTION_1280X960),
    R1920X1080(CommandConstants.PARAM_NAME_CAMERA_RESOLUTION_1920X1080);
    
    private final String m_value;

    private CameraResolution(String value) {
        this.m_value = value;
    }

    public String toString() {
        return this.m_value;
    }
}
