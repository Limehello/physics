package com.advn.physics;

public class PhysicsAngle {
    private double angle; // Angle in radians

    public PhysicsAngle(double angle) {
        this.angle = angle;
        normalize(); // Ensure angle is normalized upon creation
    }

    public void setAngle(double angle) {
        this.angle = angle;
        normalize(); // Normalize when setting a new angle
    }

    public double getAngle() {
        return this.angle;
    }

    public double convertToRadians(double deg) {
        return deg * (Math.PI / 180);
    }

    public double convertToDegrees(double rad) {
        return rad * (180 / Math.PI);
    }

    public void add(PhysicsAngle other) {
        this.angle += other.getAngle();
        normalize();
    }

    public void subtract(PhysicsAngle other) {
        this.angle -= other.getAngle();
        normalize();
    }

    public void normalize() {
        this.angle = this.angle % (2 * Math.PI);
        if (this.angle < 0) {
            this.angle += 2 * Math.PI;
        }
    }

    public double angleBetween(PhysicsAngle other) {
        double difference = Math.abs(this.angle - other.getAngle());
        return Math.min(difference, 2 * Math.PI - difference);
    }
    
    @Override
    public String toString() {
        return String.format("PhysicsAngle(angle: %.2f radians, %.2f degrees)", angle, convertToDegrees(angle));
    }
}
