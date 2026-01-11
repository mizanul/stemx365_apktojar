package gov.nasa.arc.astrobee.ros;

import ff_msgs.EkfState;
import gov.nasa.arc.astrobee.Kinematics;
import gov.nasa.arc.astrobee.types.Point;
import gov.nasa.arc.astrobee.types.Quaternion;
import gov.nasa.arc.astrobee.types.Vec3d;

public class DefaultKinematics implements Kinematics {
    private final Vec3d m_angularVelocity;
    private final Kinematics.Confidence m_confidence;
    private final Vec3d m_linearAcceleration;
    private final Vec3d m_linearVelocity;
    private final Quaternion m_orientation;
    private final Point m_position;

    public DefaultKinematics(EkfState ekf) {
        this.m_position = new Point(ekf.getPose().getPosition().getX(), ekf.getPose().getPosition().getY(), ekf.getPose().getPosition().getZ());
        this.m_orientation = new Quaternion((float) ekf.getPose().getOrientation().getX(), (float) ekf.getPose().getOrientation().getY(), (float) ekf.getPose().getOrientation().getZ(), (float) ekf.getPose().getOrientation().getW());
        this.m_linearVelocity = new Vec3d(ekf.getVelocity().getX(), ekf.getVelocity().getY(), ekf.getVelocity().getZ());
        this.m_angularVelocity = new Vec3d(ekf.getOmega().getX(), ekf.getOmega().getY(), ekf.getOmega().getZ());
        this.m_linearAcceleration = new Vec3d(ekf.getAccel().getX(), ekf.getAccel().getY(), ekf.getAccel().getZ());
        byte confidence = ekf.getConfidence();
        if (confidence == 0) {
            this.m_confidence = Kinematics.Confidence.GOOD;
        } else if (confidence == 1) {
            this.m_confidence = Kinematics.Confidence.POOR;
        } else if (confidence == 2) {
            this.m_confidence = Kinematics.Confidence.LOST;
        } else {
            throw new IllegalArgumentException("Invalid condifence byte?");
        }
    }

    public DefaultKinematics() {
        this.m_position = new Point();
        this.m_orientation = new Quaternion();
        this.m_linearVelocity = new Vec3d();
        this.m_angularVelocity = new Vec3d();
        this.m_linearAcceleration = new Vec3d();
        this.m_confidence = Kinematics.Confidence.LOST;
    }

    public Point getPosition() {
        return this.m_position;
    }

    public Quaternion getOrientation() {
        return this.m_orientation;
    }

    public Kinematics.Confidence getConfidence() {
        return this.m_confidence;
    }

    public Vec3d getLinearVelocity() {
        return this.m_linearVelocity;
    }

    public Vec3d getAngularVelocity() {
        return this.m_angularVelocity;
    }

    public Vec3d getLinearAcceleration() {
        return this.m_linearAcceleration;
    }
}
