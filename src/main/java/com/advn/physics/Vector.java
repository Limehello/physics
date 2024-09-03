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
    public double distanceToLineSegment(PhysicsVector p1, PhysicsVector p2) {
        double l2 = p1.distanceSquaredTo(p2);
        if (l2 == 0) return distanceTo(p1);
        double t = Math.max(0, Math.min(1, (this.pX - p1.pX) * (p2.pX - p1.pX) + (this.pY - p1.pY) * (p2.pY - p1.pY)) / l2);
        return distanceTo(new PhysicsVector(p1.pX + t * (p2.pX - p1.pX), p1.pY + t * (p2.pY - p1.pY)));
    }
    public PhysicsVector projectOntoLine(PhysicsVector lineStart, PhysicsVector lineEnd) {
        PhysicsVector lineDir = lineEnd.subtract(lineStart);
        return lineStart.add(projectOnto(lineDir));
    }
    public static PhysicsVector slerp(PhysicsVector start, PhysicsVector end, double t) {
        double dot = start.dot(end);
        double theta = Math.acos(dot);
        double sinTheta = Math.sin(theta);
        if (sinTheta < 1e-6) {
            return interpolate(start, end, t);
        }
        double weightStart = Math.sin((1 - t) * theta) / sinTheta;
        double weightEnd = Math.sin(t * theta) / sinTheta;
        return start.scale(weightStart).add(end.scale(weightEnd));
    }
    public static PhysicsVector computeCentroid(PhysicsVector[] vectors) {
        double sumX = 0;
        double sumY = 0;
        for (PhysicsVector v : vectors) {
            sumX += v.pX;
            sumY += v.pY;
        }
        return new PhysicsVector(sumX / vectors.length, sumY / vectors.length);
    }
    public static PhysicsVector intersectLines(PhysicsVector p1, PhysicsVector p2, PhysicsVector p3, PhysicsVector p4) {
        double a1 = p2.pY - p1.pY;
        double b1 = p1.pX - p2.pX;
        double c1 = a1 * p1.pX + b1 * p1.pY;
        double a2 = p4.pY - p3.pY;
        double b2 = p3.pX - p4.pX;
        double c2 = a2 * p3.pX + b2 * p3.pY;
        double determinant = a1 * b2 - a2 * b1;
        if (Math.abs(determinant) < 1e-10) {
            return null; // Lines are parallel
        }
        double x = (b2 * c1 - b1 * c2) / determinant;
        double y = (a1 * c2 - a2 * c1) / determinant;
        return new PhysicsVector(x, y);
    }
    public PhysicsVector closestPointOnLineSegment(PhysicsVector p1, PhysicsVector p2) {
        PhysicsVector lineDir = p2.subtract(p1);
        double t = Math.max(0, Math.min(1, dot(lineDir) / lineDir.dot(lineDir)));
        return p1.add(lineDir.scale(t));
    }
    public double[] barycentricCoordinates(PhysicsVector v1, PhysicsVector v2, PhysicsVector v3) {
        double denom = (v2.getPY() - v3.getPY()) * (v1.getPX() - v3.getPX()) + (v3.getPX() - v2.getPX()) * (v1.getPY() - v3.getPY());
        double a = ((v2.getPY() - v3.getPY()) * (pX - v3.getPX()) + (v3.getPX() - v2.getPX()) * (pY - v3.getPY())) / denom;
        double b = ((v3.getPY() - v1.getPY()) * (pX - v3.getPX()) + (v1.getPX() - v3.getPX()) * (pY - v3.getPY())) / denom;
        double c = 1 - a - b;
        return new double[]{a, b, c};
    }
    
    @Override
    public String toString() {
        return String.format("PhysicsVector(x: %.2f, y: %.2f)", pX, pY);
    }
}
