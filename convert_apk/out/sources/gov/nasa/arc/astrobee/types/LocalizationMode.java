package gov.nasa.arc.astrobee.types;

import ff_msgs.CommandConstants;

public enum LocalizationMode {
    NONE(CommandConstants.PARAM_NAME_LOCALIZATION_MODE_NONE),
    MAPPED_LANDMARKS(CommandConstants.PARAM_NAME_LOCALIZATION_MODE_MAPPED_LANDMARKS),
    ARTAGS(CommandConstants.PARAM_NAME_LOCALIZATION_MODE_ARTAGS),
    HANDRAIL(CommandConstants.PARAM_NAME_LOCALIZATION_MODE_HANDRAIL),
    PERCH("Perch"),
    TRUTH(CommandConstants.PARAM_NAME_LOCALIZATION_MODE_TRUTH);
    
    private final String m_value;

    private LocalizationMode(String value) {
        this.m_value = value;
    }

    public String toString() {
        return this.m_value;
    }
}
