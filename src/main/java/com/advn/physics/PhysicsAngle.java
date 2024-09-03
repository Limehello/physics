package com.advn.physics;

public class PhysicsAngle {
    private double angle;
    public PhysicsAngle(double angle) {
        this.angle = angle;
    }
    public void setAngle(double angle) {
        this.angle = angle;
    }
    public double getAngle() {
        return this.angle;
    }
    public double convertToRadians(double deg) {
        return deg * (3.141592653589793 / 180)
    }
    public void addAngle(Angle other) {
        this.angle += other.getAngle();
    }
    public void subtractAngle(Angle other) {
        this.angle -= other.getAngle();
    }
    public void normalizeAngle() {
        this.angle = this.angle % (2 * Math.PI);
        if (this.angle < 0) {
            this.angle += 2 * Math.PI;
        }
    }
    public double angleBetween(Angle other) {
        double difference = Math.abs(this.angle - other.getAngle());
        return Math.min(difference, 2 * Math.PI - difference);
    }
}
