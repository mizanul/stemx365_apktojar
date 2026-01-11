package gov.nasa.arc.astrobee;

import gov.nasa.arc.astrobee.types.Point;
import gov.nasa.arc.astrobee.types.Quaternion;
import gov.nasa.arc.astrobee.types.Vec3d;

public interface Kinematics {

    public enum Confidence {
        GOOD,
        POOR,
        LOST
    }

    Vec3d getAngularVelocity();

    Confidence getConfidence();

    Vec3d getLinearAcceleration();

    Vec3d getLinearVelocity();

    Quaternion getOrientation();

    Point getPosition();
}
