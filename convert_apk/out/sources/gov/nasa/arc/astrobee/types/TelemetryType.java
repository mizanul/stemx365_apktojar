package gov.nasa.arc.astrobee.types;

import ff_msgs.CommandConstants;

public enum TelemetryType {
    COMM_STATUS(CommandConstants.PARAM_NAME_TELEMETRY_TYPE_COMM_STATUS),
    CPU_STATE(CommandConstants.PARAM_NAME_TELEMETRY_TYPE_CPU_STATE),
    DISK_STATE(CommandConstants.PARAM_NAME_TELEMETRY_TYPE_DISK_STATE),
    EKF_STATE(CommandConstants.PARAM_NAME_TELEMETRY_TYPE_EKF_STATE),
    GNC_STATE(CommandConstants.PARAM_NAME_TELEMETRY_TYPE_GNC_STATE),
    PMC_CMD_STATE(CommandConstants.PARAM_NAME_TELEMETRY_TYPE_PMC_CMD_STATE),
    POSITION(CommandConstants.PARAM_NAME_TELEMETRY_TYPE_POSITION),
    SPARSE_MAPPING_POSE(CommandConstants.PARAM_NAME_TELEMETRY_TYPE_SPARSE_MAPPING_POSE);
    
    private final String m_value;

    private TelemetryType(String value) {
        this.m_value = value;
    }

    public String toString() {
        return this.m_value;
    }
}
