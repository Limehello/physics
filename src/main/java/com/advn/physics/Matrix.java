package com.advn.physics;

public class Matrix {
    private float[][] values;

    public Matrix(int rows, int cols) {
        values = new float[rows][cols];
    }

    public Matrix(float[][] values) {
        this.values = values;
    }

    public int getRows() {
        return values.length;
    }

    public int getCols() {
        return values[0].length;
    }

    public float get(int row, int col) {
        return values[row][col];
    }

    public void set(int row, int col, float value) {
        values[row][col] = value;
    }

    public Matrix multiply(Matrix other) {
        if (this.getCols() != other.getRows()) {
            throw new IllegalArgumentException("Matrix dimensions do not match for multiplication.");
        }

        int rows = this.getRows();
        int cols = other.getCols();
        int inner = this.getCols();
        Matrix result = new Matrix(rows, cols);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                float sum = 0;
                for (int k = 0; k < inner; k++) {
                    sum += this.get(i, k) * other.get(k, j);
                }
                result.set(i, j, sum);
            }
        }

        return result;
    }

    public Matrix add(Matrix other) {
        if (this.getRows() != other.getRows() || this.getCols() != other.getCols()) {
            throw new IllegalArgumentException("Matrix dimensions do not match for addition.");
        }

        int rows = this.getRows();
        int cols = this.getCols();
        Matrix result = new Matrix(rows, cols);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.set(i, j, this.get(i, j) + other.get(i, j));
            }
        }

        return result;
    }
    public Matrix transpose() {
        int rows = getRows();
        int cols = getCols();
        Matrix result = new Matrix(cols, rows);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.set(j, i, this.get(i, j));
            }
        }
        return result;
    }
    public float determinant() {
    if (getRows() != getCols()) {
        throw new IllegalArgumentException("Matrix must be square to compute determinant.");
    }

    if (getRows() == 2) {
        return get(0, 0) * get(1, 1) - get(0, 1) * get(1, 0);
    }

    throw new UnsupportedOperationException("Determinant calculation is only implemented for 2x2 matrices.");
    }
    public Matrix inverse() {
    if (getRows() != getCols()) {
        throw new IllegalArgumentException("Matrix must be square to compute inverse.");
    }

    if (getRows() == 2) {
        float det = determinant();
        if (det == 0) {
            throw new ArithmeticException("Matrix is singular and cannot be inverted.");
        }
        Matrix result = new Matrix(2, 2);
        result.set(0, 0, get(1, 1) / det);
        result.set(0, 1, -get(0, 1) / det);
        result.set(1, 0, -get(1, 0) / det);
        result.set(1, 1, get(0, 0) / det);
        return result;
    }

    throw new UnsupportedOperationException("Inverse calculation is only implemented for 2x2 matrices.");
    }
    public Matrix scalarMultiply(float scalar) {
    int rows = getRows();
    int cols = getCols();
    Matrix result = new Matrix(rows, cols);

    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            result.set(i, j, this.get(i, j) * scalar);
        }
    }

    return result;
    }
    public float norm() {
    float sum = 0;
    for (int i = 0; i < getRows(); i++) {
        for (int j = 0; j < getCols(); j++) {
            sum += Math.pow(get(i, j), 2);
        }
    }
    return (float) Math.sqrt(sum);
    }
    public void swapRows(int row1, int row2) {
    float[] temp = values[row1];
    values[row1] = values[row2];
    values[row2] = temp;
}

public void scaleRow(int row, float factor) {
    for (int j = 0; j < getCols(); j++) {
        values[row][j] *= factor;
    }
}

public void addRows(int row1, int row2, float factor) {
    for (int j = 0; j < getCols(); j++) {
        values[row1][j] += factor * values[row2][j];
    }
}
    public Matrix power(int n) {
    if (getRows() != getCols()) {
        throw new IllegalArgumentException("Matrix must be square to compute power.");
    }

    Matrix result = new Matrix(values);
    Matrix base = new Matrix(values);

    for (int i = 1; i < n; i++) {
        result = result.multiply(base);
    }

    return result;
    }
    public static Matrix identity(int size) {
    Matrix result = new Matrix(size, size);
    for (int i = 0; i < size; i++) {
        result.set(i, i, 1);
    }
    return result;
}

public static Matrix zero(int rows, int cols) {
    return new Matrix(rows, cols);
}
    public Matrix rotate(float angleDegrees) {
        double angleRadians = Math.toRadians(angleDegrees);
        float cosAngle = (float) Math.cos(angleRadians);
        float sinAngle = (float) Math.sin(angleRadians);

        Matrix rotationMatrix = new Matrix(2, 2);
        rotationMatrix.set(0, 0, cosAngle);
        rotationMatrix.set(0, 1, -sinAngle);
        rotationMatrix.set(1, 0, sinAngle);
        rotationMatrix.set(1, 1, cosAngle);

        return this.multiply(rotationMatrix);
    }
    public Matrix scale(float scaleX, float scaleY) {
        Matrix scalingMatrix = new Matrix(2, 2);
        scalingMatrix.set(0, 0, scaleX);
        scalingMatrix.set(0, 1, 0);
        scalingMatrix.set(1, 0, 0);
        scalingMatrix.set(1, 1, scaleY);

        return this.multiply(scalingMatrix);
    }
    
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getCols(); j++) {
                sb.append(String.format("%.2f ", values[i][j]));
            }
        sb.append("\n");
        }
        return sb.toString();
    }
}
