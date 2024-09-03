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
    
    @Override
    public String toString() {
        return "PhysicsVector{" + "pX=" + pX + ", pY=" + pY + '}';
    }
}
