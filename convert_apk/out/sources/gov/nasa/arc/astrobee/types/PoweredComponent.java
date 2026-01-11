package gov.nasa.arc.astrobee.types;

import ff_msgs.CommandConstants;

public enum PoweredComponent {
    LASER_POINTER(CommandConstants.PARAM_NAME_POWERED_COMPONENT_LASER_POINTER),
    PAYLOAD_TOP_AFT(CommandConstants.PARAM_NAME_POWERED_COMPONENT_PAYLOAD_TOP_AFT),
    PAYLOAD_BOTTOM_AFT(CommandConstants.PARAM_NAME_POWERED_COMPONENT_PAYLOAD_BOTTOM_AFT),
    PAYLOAD_BOTTOM_FRONT(CommandConstants.PARAM_NAME_POWERED_COMPONENT_PAYLOAD_BOTTOM_FRONT),
    PMC(CommandConstants.PARAM_NAME_POWERED_COMPONENT_PMCS_AND_SIGNAL_LIGHTS);
    
    private final String m_value;

    private PoweredComponent(String value) {
        this.m_value = value;
    }

    public String toString() {
        return this.m_value;
    }
}
