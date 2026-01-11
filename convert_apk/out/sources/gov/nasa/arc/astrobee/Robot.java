package gov.nasa.arc.astrobee;

import gov.nasa.arc.astrobee.internal.BaseRobot;
import gov.nasa.arc.astrobee.types.Point;
import gov.nasa.arc.astrobee.types.Quaternion;

public interface Robot extends BaseRobot {
    Kinematics getCurrentKinematics();

    PendingResult simpleMove6DOF(Point point, Quaternion quaternion);
}
