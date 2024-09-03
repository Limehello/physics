apackage com.advn.physics;

public class PhysicsMass {
    private double mass; // Mass in grams

    // Constructor
    public PhysicsMass(double mass) {
        this.mass = mass;
    }

    // Getters and Setters
    public double getMass() {
        return this.mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    // Conversion Methods
    /**
     * Converts mass from grams to kilograms.
     */
    public double convertToKg() {
        return this.mass / 1000;
    }

    /**
     * Converts mass from grams to milligrams.
     */
    public double convertToMg() {
        return this.mass * 1000;
    }

    /**
     * Converts mass from grams to pounds.
     */
    public double convertToLb() {
        return this.mass / 453.59237; // 1 lb = 453.59237 grams
    }

    /**
     * Converts mass from grams to ounces.
     */
    public double convertToOz() {
        return this.mass / 28.349523; // 1 oz = 28.349523 grams
    }

    /**
     * Converts mass from grams to metric tons.
     */
    public double convertToMetricTon() {
        return this.mass / 1_000_000;
    }

    /**
     * Converts mass from grams to grains.
     */
    public double convertToGrains() {
        return this.mass / 0.06479891; // 1 grain = 0.06479891 grams
    }

    /**
     * Converts mass from grams to stones.
     */
    public double convertToSt() {
        return this.mass / 6350.29; // 1 stone = 6350.29 grams
    }

    /**
     * Converts mass from grams to slugs.
     */
    public double convertToSlugs() {
        return this.mass / 14593.9; // 1 slug = 14593.9 grams
    }

    /**
     * Converts mass from grams to carats.
     */
    public double convertToCarats() {
        return this.mass * 5; // 1 gram = 5 carats
    }

    /**
     * Converts mass from grams to atomic mass units.
     */
    public double convertToAmu() {
        return this.mass / 1.66053906660e-24; // Atomic mass unit in grams
    }

    // Static Methods
    /**
     * Calculates the center of mass for a system of masses.
     * @param masses Array of PhysicsMass objects.
     * @param positions Array of positions corresponding to the masses.
     * @return The center of mass position.
     */
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

        if (totalMass == 0) {
            throw new ArithmeticException("Total mass cannot be zero.");
        }

        return weightedSum / totalMass;
    }

    // Instance Methods
    /**
     * Computes the ratio of this mass to another mass.
     * @param other The other PhysicsMass object.
     * @return The ratio of this mass to the other mass.
     */
    public double ratioWith(PhysicsMass other) {
        return this.mass / other.getMass();
    }

    /**
     * Calculates the gravitational potential energy at a given height.
     * @param height Height in meters.
     * @return The gravitational potential energy in joules.
     */
    public double calculateGravitationalPotentialEnergy(double height) {
        final double g = 9.81; // Acceleration due to gravity in m/s^2
        return this.mass * height * g;
    }

    /**
     * Calculates the kinetic energy for a given velocity.
     * @param velocity Velocity in meters per second.
     * @return The kinetic energy in joules.
     */
    public double calculateKineticEnergy(double velocity) {
        return 0.5 * this.mass * Math.pow(velocity, 2);
    }

    /**
     * Calculates mass based on density and volume.
     * @param density Density in grams per cubic centimeter.
     * @param volume Volume in cubic centimeters.
     * @return The mass in grams.
     */
    public static double calculateMassFromDensityAndVolume(double density, double volume) {
        return density * volume;
    }

    /**
     * Converts mass from grams to solar masses.
     */
    public double convertToSolarMasses() {
        final double solarMass = 1.9885e30; // Mass of the sun in kg
        return this.convertToKg() / solarMass;
    }

    /**
     * Calculates relativistic mass based on velocity.
     * @param velocity Velocity in meters per second.
     * @return The relativistic mass.
     */
    public double calculateRelativisticMass(double velocity) {
        final double c = 2.998e8; // Speed of light in m/s
        if (velocity >= c) {
            throw new IllegalArgumentException("Velocity cannot be greater than or equal to the speed of light.");
        }
        return this.mass / Math.sqrt(1 - Math.pow(velocity / c, 2));
    }

    /**
     * Calculates the energy equivalent of the mass using E=mc^2.
     * @return The energy in joules.
     */
    public double calculateEnergyEquivalent() {
        final double c = 2.998e8; // Speed of light in m/s
        return this.mass * Math.pow(c, 2);
    }

    /**
     * Converts mass from grams to Planck masses.
     */
    public double convertToPlanckMass() {
        final double planckMass = 2.176434e-8; // Planck mass in kg
        return this.convertToKg() / planckMass; // Convert mass to Planck units
    }

    /**
     * Calculates the relative difference between this mass and another mass.
     * @param other The other PhysicsMass object.
     * @return The relative difference.
     */
    public double relativeDifferenceWith(PhysicsMass other) {
        return Math.abs(this.mass - other.getMass()) / Math.max(this.mass, other.getMass());
    }

    /**
     * Calculates the absolute difference between this mass and another mass.
     * @param other The other PhysicsMass object.
     * @return The absolute difference.
     */
    public double differenceWith(PhysicsMass other) {
        return Math.abs(this.mass - other.getMass());
    }

    /**
     * Scales the mass by a given percentage.
     * @param percentage The percentage to scale by.
     */
    public void scaleMass(double percentage) {
        this.mass *= (percentage / 100.0);
    }

    /**
     * Adds another mass to this mass.
     * @param other The other PhysicsMass object.
     */
    public void addMass(PhysicsMass other) {
        this.mass += other.getMass();
    }

    /**
     * Subtracts another mass from this mass.
     * @param other The other PhysicsMass object.
     */
    public void subtractMass(PhysicsMass other) {
        this.mass -= other.getMass();
    }

    /**
     * Multiplies the mass by a given factor.
     * @param factor The factor to multiply by.
     */
    public void multiplyMass(double factor) {
        this.mass *= factor;
    }

    /**
     * Divides the mass by a given factor.
     * @param factor The factor to divide by.
     */
    public void divideMass(double factor) {
        if (factor == 0) {
            throw new ArithmeticException("Division by zero.");
        }
        this.mass /= factor;
    }

    /**
     * Checks if this mass is equal to another mass within a tolerance.
     * @param other The other PhysicsMass object.
     * @return True if the masses are considered equal, false otherwise.
     */
    public boolean isEqualTo(PhysicsMass other) {
        final double epsilon = 1e-9;
        return Math.abs(this.mass - other.getMass()) < epsilon;
    }

    @Override
    public String toString() {
        return String.format("PhysicsMass(mass: %.2f grams, %.2f kg)", mass, convertToKg());
    }
}
