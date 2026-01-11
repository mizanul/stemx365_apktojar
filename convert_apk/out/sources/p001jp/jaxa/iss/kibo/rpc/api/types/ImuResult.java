package p001jp.jaxa.iss.kibo.rpc.api.types;

import gov.nasa.arc.astrobee.types.Quaternion;
import gov.nasa.arc.astrobee.types.Vec3d;
import sensor_msgs.Imu;

/* renamed from: jp.jaxa.iss.kibo.rpc.api.types.ImuResult */
public class ImuResult {
    private Vec3d angularVelocity;
    private double[] angularVelocityCovariance;
    private Vec3d linearAcceleration;
    private double[] linearAccelerationCovariance;
    private Quaternion orientation;
    private double[] orientationCovariance;

    public ImuResult() {
        this.angularVelocity = new Vec3d();
        this.angularVelocityCovariance = new double[9];
        this.linearAcceleration = new Vec3d();
        this.linearAccelerationCovariance = new double[9];
        this.orientation = new Quaternion();
        this.orientationCovariance = new double[9];
    }

    public ImuResult(Imu imu) {
        this.angularVelocity = new Vec3d(imu.getAngularVelocity().getX(), imu.getAngularVelocity().getY(), imu.getAngularVelocity().getZ());
        this.angularVelocityCovariance = (double[]) imu.getAngularVelocityCovariance().clone();
        this.linearAcceleration = new Vec3d(imu.getLinearAcceleration().getX(), imu.getLinearAcceleration().getY(), imu.getLinearAcceleration().getZ());
        this.linearAccelerationCovariance = (double[]) imu.getLinearAccelerationCovariance().clone();
        this.orientation = new Quaternion((float) imu.getOrientation().getX(), (float) imu.getOrientation().getY(), (float) imu.getOrientation().getZ(), (float) imu.getOrientation().getW());
        this.orientationCovariance = (double[]) imu.getOrientationCovariance().clone();
    }

    public Vec3d getAngularVelocity() {
        return this.angularVelocity;
    }

    public double[] getAngularVelocityCovariance() {
        return this.angularVelocityCovariance;
    }

    public Vec3d getLinearAcceleration() {
        return this.linearAcceleration;
    }

    public double[] getLinearAccelerationCovariance() {
        return this.linearAccelerationCovariance;
    }

    public Quaternion getOrientation() {
        return this.orientation;
    }

    public double[] getOrientationCovariance() {
        return this.orientationCovariance;
    }

    public void setAngularVelocity(Vec3d angularVelocity2) {
        this.angularVelocity = angularVelocity2;
    }

    public void setAngularVelocityCovariance(double[] angularVelocityCovariance2) {
        this.angularVelocityCovariance = angularVelocityCovariance2;
    }

    public void setLinearAcceleration(Vec3d linearAcceleration2) {
        this.linearAcceleration = linearAcceleration2;
    }

    public void setLinearAccelerationCovariance(double[] linearAccelerationCovariance2) {
        this.linearAccelerationCovariance = linearAccelerationCovariance2;
    }

    public void setOrientation(Quaternion orientation2) {
        this.orientation = orientation2;
    }

    public void setOrientationCovariance(double[] orientationCovariance2) {
        this.orientationCovariance = orientationCovariance2;
    }
}
