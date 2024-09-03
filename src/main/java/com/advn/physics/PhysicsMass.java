package com.advn.physics;

public class PhysicsMass {
    private double mass;

    public PhysicsMass(double mass) {
        this.mass = mass;
    }

    public double getMass() {
        return this.mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public double convertToKG() {
        return this.mass / 1000;
    }

    public double convertToMG() {
        return this.mass * 1000;
    }

    public double convertToLB() {
        return this.mass / 453.6;
    }

    public double convertToOZ() {
        return this.mass / 28.3;
    }

    public double convertToMetricTon() {
        return this.mass / 1000000;
    }

    public double convertToGrains() {
        return this.mass / 0.06479891;
    }

    public double convertToST() {
        return this.mass / 6350.29;
    }

    public double convertToSlugs() {
        return this.mass / 14593.9;
    }

    public double convertToCarats() {
        return this.mass * 5;
    }

    public double convertToAMU() {
        return this.mass / 1.66053906660e-24;
    }

    public static double calculateCenterOfMass(PhysicsMass[] masses, double[] positions) {
        if (masses.length != positions.length) {
            throw new IllegalArgumentException("Masses and positions arrays must have the same length.");
        }

        double totalMass = 0;
        double weightedSum = 0;

        for (int i = 0; i < masses.length; i++) {
            totalMass += masses[i].getMass();
            weightedSum += masses[i].getMass() * positions[i];
        }

        return weightedSum / totalMass;
    }

    public double ratioWith(PhysicsMass other) {
        return this.mass / other.getMass();
    }

    public double calculateGravitationalPotentialEnergy(double height) {
        final double g = 9.81; // acceleration due to gravity in m/s^2
        return this.mass * height * g;
    }

    public double calculateKineticEnergy(double velocity) {
        return 0.5 * this.mass * Math.pow(velocity, 2);
    }

    public static double calculateMassFromDensityAndVolume(double density, double volume) {
        return density * volume;
    }

    public double convertToSolarMasses() {
        final double solarMass = 1.9885e30; // mass of the sun in kg
        return this.convertToKG() / solarMass;
    }

    public double calculateRelativisticMass(double velocity) {
        final double c = 2.998e8; // speed of light in m/s
        return this.mass / Math.sqrt(1 - Math.pow(velocity / c, 2));
    }

    public double calculateEnergyEquivalent() {
        final double c = 2.998e8; // speed of light in m/s
        return this.mass * Math.pow(c, 2);
    }

    public double convertToPlanckMass() {
        final double planckMass = 2.176434e-8; // Planck mass in kg
        return this.convertToKG() / planckMass;
    }

    public double relativeDifferenceWith(PhysicsMass other) {
        return Math.abs(this.mass - other.getMass()) / Math.max(this.mass, other.getMass());
    }

    public double differenceWith(PhysicsMass other) {
        return Math.abs(this.mass - other.getMass());
    }

    public void scaleMass(double percentage) {
        this.mass *= (percentage / 100.0);
    }

    public void addMass(PhysicsMass other) {
        this.mass += other.getMass();
    }

    public void subtractMass(PhysicsMass other) {
        this.mass -= other.getMass();
    }

    public void multiplyMass(double factor) {
        this.mass *= factor;
    }

    public void divideMass(double factor) {
        this.mass /= factor;
    }

    public boolean isEqualTo(PhysicsMass other) {
        final double epsilon = 1e-9;
        return Math.abs(this.mass - other.getMass()) < epsilon;
    }
}
