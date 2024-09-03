package com.advn.physics;

public class PhysicsVector {
    private double pX;
    private double pY;
    public PhysicsVector(double x, double y) {
        this.pX = x;
        this.pY = y;
    }
    public double getPX() {
        return pX;
    }
    public double getPY() {
        return pY;
    }
    public void setPX(double x) {
        this.pX = x;
    }
    public void setPY(double y) {
        this.pY = y;
    }
    public PhysicsVector add(PhysicsVector other) {
        return new PhysicsVector(this.pX + other.getPX, this.pY + other.getPY);
    }
    public PhysicsVector subtract(PhysicsVector other) {
        return new PhysicsVector(this.pX - other.getPX, this.pY - other.getPY);
    }
    public double dotProduct(PhysicsVector other) {
        return this.pX * other.getPX + this.pY * other.getPY;
    }
    public double magnitude() {
        return Math.sqrt(pX * pX + pY * pY);
    }
    public PhysicsVector normalize() {
        double mag = magnitude();
        if (mag != 0) {
            return new PhysicsVector(pX / mag, pY / mag);
        } else {
            return new PhysicsVector(0, 0);
        }
    }
    public double angleBetween(PhysicsVector other) {
        double dotProduct = dot(other);
        double magnitudes = this.magnitude() * other.magnitude();
        if (magnitudes == 0) return 0; // Avoid division by zero
        return Math.acos(dotProduct / magnitudes);
    }
    public PhysicsVector projectOnto(PhysicsVector other) {
        double scalar = dot(other) / other.magnitude() / other.magnitude();
        return new PhysicsVector(scalar * other.pX, scalar * other.pY);
    }
    public double cross(PhysicsVector other) {
        return this.pX * other.pY - this.pY * other.pX;
    }
    public double distanceTo(PhysicsVector other) {
        return Math.sqrt(Math.pow(this.pX - other.pX, 2) + Math.pow(this.pY - other.pY, 2));
    }
    public PhysicsVector scale(double factor) {
        return new PhysicsVector(this.pX * factor, this.pY * factor);
    }
    public PhysicsVector clamp(double maxMagnitude) {
        double mag = magnitude();
        if (mag > maxMagnitude) {
            return normalize().scale(maxMagnitude);
        }
        return this;
    }
    public double[] toPolar() {
        return new double[]{magnitude(), Math.atan2(pY, pX)};
    }
    public static PhysicsVector fromPolar(double magnitude, double angle) {
        return new PhysicsVector(magnitude * Math.cos(angle), magnitude * Math.sin(angle));
    }
    public PhysicsVector reflect(PhysicsVector normal) {
        double dotProduct = dot(normal);
        return subtract(normal.scale(2 * dotProduct));
    }
    public static PhysicsVector interpolate(PhysicsVector start, PhysicsVector end, double t) {
        return new PhysicsVector(
            start.pX + t * (end.pX - start.pX),
            start.pY + t * (end.pY - start.pY));
    }
    public double distanceSquaredTo(PhysicsVector other) {
        return Math.pow(this.pX - other.pX, 2) + Math.pow(this.pY - other.pY, 2);
    }
    public static PhysicsVector linearCombination(PhysicsVector v1, double a, PhysicsVector v2, double b) {
        return new PhysicsVector(a * v1.pX + b * v2.pX, a * v1.pY + b * v2.pY);
    }
    public PhysicsVector orthogonal() {
        return new PhysicsVector(-pY, pX);
    }
    public boolean isParallelTo(PhysicsVector other) {
        return Math.abs(cross(other)) < 1e-10; // Small tolerance for floating-point comparisons
    }
    public boolean isCollinearTo(PhysicsVector other) {
        return Math.abs(cross(other)) < 1e-10; // Same as parallelism for 2D vectors
    }
    public PhysicsVector unitVector() {
        return normalize();
    }
    
    
    @Override
    public String toString() {
        return String.format("PhysicsVector(x: %.2f, y: %.2f)", pX, pY);
    }
}
