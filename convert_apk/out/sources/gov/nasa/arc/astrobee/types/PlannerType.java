package gov.nasa.arc.astrobee.types;

import ff_msgs.CommandConstants;

public enum PlannerType {
    TRAPEZOIDAL(CommandConstants.PARAM_NAME_PLANNER_TYPE_TRAPEZOIDAL),
    QP(CommandConstants.PARAM_NAME_PLANNER_TYPE_QUADRATIC_PROGRAM);
    
    private final String m_value;

    private PlannerType(String value) {
        this.m_value = value;
    }

    public String toString() {
        return this.m_value;
    }
}
