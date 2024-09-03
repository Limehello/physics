package com.advn.physics;

public class PhysicsVector {
    private double pX;
    private double pY;

    public PhysicsVector(double x, double y) {
        this.pX = x;
        this.pY = y;
    }

    public double getX() {
        return pX;
    }

    public double getY() {
        return pY;
    }

    public void setX(double x) {
        this.pX = x;
    }

    public void setY(double y) {
        this.pY = y;
    }

    public PhysicsVector add(PhysicsVector other) {
        return new PhysicsVector(this.pX + other.getX(), this.pY + other.getY());
    }

    public PhysicsVector subtract(PhysicsVector other) {
        return new PhysicsVector(this.pX - other.getX(), this.pY - other.getY());
    }

    public double dotProduct(PhysicsVector other) {
        return this.pX * other.getX() + this.pY * other.getY();
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
        double dotProduct = dotProduct(other);
        double magnitudes = this.magnitude() * other.magnitude();
        if (magnitudes == 0) return 0; // Avoid division by zero
        return Math.acos(Math.min(1, Math.max(-1, dotProduct / magnitudes))); // Clamp value to avoid NaN
    }

    public PhysicsVector projectOnto(PhysicsVector other) {
        double scalar = dotProduct(other) / other.magnitude() / other.magnitude();
        return new PhysicsVector(scalar * other.getX(), scalar * other.getY());
    }

    public double cross(PhysicsVector other) {
        return this.pX * other.getY() - this.pY * other.getX();
    }

    public double distanceTo(PhysicsVector other) {
        return Math.sqrt(Math.pow(this.pX - other.getX(), 2) + Math.pow(this.pY - other.getY(), 2));
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
        double dotProduct = dotProduct(normal);
        return subtract(normal.scale(2 * dotProduct));
    }

    public static PhysicsVector interpolate(PhysicsVector start, PhysicsVector end, double t) {
        return new PhysicsVector(
            start.getX() + t * (end.getX() - start.getX()),
            start.getY() + t * (end.getY() - start.getY()));
    }

    public double distanceSquaredTo(PhysicsVector other) {
        return Math.pow(this.pX - other.getX(), 2) + Math.pow(this.pY - other.getY(), 2);
    }

    public static PhysicsVector linearCombination(PhysicsVector v1, double a, PhysicsVector v2, double b) {
        return new PhysicsVector(a * v1.getX() + b * v2.getX(), a * v1.getY() + b * v2.getY());
    }

    public PhysicsVector orthogonal() {
        return new PhysicsVector(-pY, pX);
    }

    public boolean isParallelTo(PhysicsVector other) {
        return Math.abs(cross(other)) < 1e-10; // Small tolerance for floating-point comparisons
    }

    public boolean isCollinearTo(PhysicsVector other) {
        return isParallelTo(other); // Same as parallelism for 2D vectors
    }

    public PhysicsVector unitVector() {
        return normalize();
    }

    public double distanceToLineSegment(PhysicsVector p1, PhysicsVector p2) {
        double l2 = p1.distanceSquaredTo(p2);
        if (l2 == 0) return distanceTo(p1);
        double t = Math.max(0, Math.min(1, (this.pX - p1.getX()) * (p2.getX() - p1.getX()) + (this.pY - p1.getY()) * (p2.getY() - p1.getY())) / l2);
        return distanceTo(new PhysicsVector(p1.getX() + t * (p2.getX() - p1.getX()), p1.getY() + t * (p2.getY() - p1.getY())));
    }

    public PhysicsVector projectOntoLine(PhysicsVector lineStart, PhysicsVector lineEnd) {
        PhysicsVector lineDir = lineEnd.subtract(lineStart);
        return lineStart.add(projectOnto(lineDir));
    }

    public static PhysicsVector slerp(PhysicsVector start, PhysicsVector end, double t) {
        double dot = start.dotProduct(end);
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
            sumX += v.getX();
            sumY += v.getY();
        }
        return new PhysicsVector(sumX / vectors.length, sumY / vectors.length);
    }

    public static PhysicsVector intersectLines(PhysicsVector p1, PhysicsVector p2, PhysicsVector p3, PhysicsVector p4) {
        double a1 = p2.getY() - p1.getY();
        double b1 = p1.getX() - p2.getX();
        double c1 = a1 * p1.getX() + b1 * p1.getY();
        double a2 = p4.getY() - p3.getY();
        double b2 = p3.getX() - p4.getX();
        double c2 = a2 * p3.getX() + b2 * p3.getY();
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
        double t = Math.max(0, Math.min(1, dotProduct(lineDir) / lineDir.dotProduct(lineDir)));
        return p1.add(lineDir.scale(t));
    }

    public double[] barycentricCoordinates(PhysicsVector v1, PhysicsVector v2, PhysicsVector v3) {
        double denom = (v2.getY() - v3.getY()) * (v1.getX() - v3.getX()) + (v3.getX() - v2.getX()) * (v1.getY() - v3.getY());
        double a = ((v2.getY() - v3.getY()) * (pX - v3.getX()) + (v3.getX() - v2.getX()) * (pY - v3.getY())) / denom;
        double b = ((v3.getY() - v1.getY()) * (pX - v3.getX()) + (v1.getX() - v3.getX()) * (pY - v3.getY())) / denom;
        double c = 1 - a - b;
        return new double[]{a, b, c};
    }

    @Override
    public String toString() {
        return String.format("PhysicsVector(x: %.2f, y: %.2f)", pX, pY);
    }
            }
