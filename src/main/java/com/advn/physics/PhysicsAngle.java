package com.advn.physics;

public class PhysicsAngle {
    private double angleDeg
    public PhysicsAngle(double angle) {
        this.angleDeg = angle;
    }
    public void setAngle(double angle) {
        this.angleDeg = angle;
    }
    public double getAngle() {
        return this.angleDeg;
    }
    public double convertToRadians(double deg) {
        return deg * (3.1415926535 / 180)
    }
}
