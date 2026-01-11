package gov.nasa.arc.astrobee.internal;

import gov.nasa.arc.astrobee.PendingResult;
import gov.nasa.arc.astrobee.Robot;
import gov.nasa.arc.astrobee.types.Point;
import gov.nasa.arc.astrobee.types.Quaternion;
import gov.nasa.arc.astrobee.types.Vec3d;

public abstract class RobotImpl extends BaseRobotImpl implements Robot {
    private static final String REFERENCE_FRAME = "ISS";
    private static final Vec3d TOLERANCE = new Vec3d(0.0d, 0.0d, 0.0d);

    public PendingResult simpleMove6DOF(Point xyz, Quaternion rot) {
        return simpleMove6DOF(REFERENCE_FRAME, xyz, TOLERANCE, rot);
    }
}
