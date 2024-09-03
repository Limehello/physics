package com.advn.physics;

public class Matrix {
    private final double[][] values;

    /**
     * Creates a matrix with specified number of rows and columns.
     *
     * @param rows the number of rows
     * @param cols the number of columns
     */
    public Matrix(long rows, long cols) {
        values = new double[(int) rows][(int) cols];
    }

    /**
     * Creates a matrix from a 2D array.
     *
     * @param values 2D array representing matrix values
     * @throws IllegalArgumentException if the array is null or empty
     */
    public Matrix(double[][] values) {
        if (values == null || values.length == 0 || values[0].length == 0) {
            throw new IllegalArgumentException("Matrix cannot be null or empty.");
        }
        this.values = new double[values.length][values[0].length];
        for (int i = 0; i < values.length; i++) {
            System.arraycopy(values[i], 0, this.values[i], 0, values[i].length);
        }
    }

    /**
     * Gets the number of rows in the matrix.
     *
     * @return number of rows
     */
    public long getRows() {
        return values.length;
    }

    /**
     * Gets the number of columns in the matrix.
     *
     * @return number of columns
     */
    public long getCols() {
        return values[0].length;
    }

    /**
     * Retrieves the value at a specified row and column.
     *
     * @param row the row index
     * @param col the column index
     * @return the value at the specified position
     * @throws IndexOutOfBoundsException if indices are out of bounds
     */
    public double get(long row, long col) {
        checkIndex(row, col);
        return values[(int) row][(int) col];
    }

    /**
     * Sets the value at a specified row and column.
     *
     * @param row   the row index
     * @param col   the column index
     * @param value the value to set
     * @throws IndexOutOfBoundsException if indices are out of bounds
     */
    public void set(long row, long col, double value) {
        checkIndex(row, col);
        values[(int) row][(int) col] = value;
    }

    /**
     * Checks if the given row and column indices are valid.
     *
     * @param row the row index
     * @param col the column index
     * @throws IndexOutOfBoundsException if indices are out of bounds
     */
    private void checkIndex(long row, long col) {
        if (row < 0 || row >= getRows() || col < 0 || col >= getCols()) {
            throw new IndexOutOfBoundsException("Invalid index: (" + row + ", " + col + ").");
        }
    }

    /**
     * Multiplies this matrix with another matrix.
     *
     * @param other the matrix to multiply with
     * @return the resulting matrix
     * @throws IllegalArgumentException if dimensions do not match for multiplication
     */
    public Matrix multiply(Matrix other) {
        if (this.getCols() != other.getRows()) {
            throw new IllegalArgumentException("Matrix dimensions do not match for multiplication.");
        }

        long rows = this.getRows();
        long cols = other.getCols();
        long inner = this.getCols();
        Matrix result = new Matrix(rows, cols);

        for (long i = 0; i < rows; i++) {
            for (long j = 0; j < cols; j++) {
                double sum = 0;
                for (long k = 0; k < inner; k++) {
                    sum += this.get(i, k) * other.get(k, j);
                }
                result.set(i, j, sum);
            }
        }

        return result;
    }

    /**
     * Adds this matrix to another matrix.
     *
     * @param other the matrix to add
     * @return the resulting matrix
     * @throws IllegalArgumentException if dimensions do not match for addition
     */
    public Matrix add(Matrix other) {
        if (this.getRows() != other.getRows() || this.getCols() != other.getCols()) {
            throw new IllegalArgumentException("Matrix dimensions do not match for addition.");
        }

        long rows = this.getRows();
        long cols = this.getCols();
        Matrix result = new Matrix(rows, cols);

        for (long i = 0; i < rows; i++) {
            for (long j = 0; j < cols; j++) {
                result.set(i, j, this.get(i, j) + other.get(i, j));
            }
        }

        return result;
    }

    /**
     * Transposes the matrix (flips rows and columns).
     *
     * @return the transposed matrix
     */
    public Matrix transpose() {
        long rows = getRows();
        long cols = getCols();
        Matrix result = new Matrix(cols, rows);
        for (long i = 0; i < rows; i++) {
            for (long j = 0; j < cols; j++) {
                result.set(j, i, this.get(i, j));
            }
        }
        return result;
    }

    /**
     * Calculates the determinant of the matrix.
     *
     * @return the determinant value
     * @throws IllegalArgumentException if the matrix is not square
     * @throws UnsupportedOperationException if determinant calculation is not implemented for the matrix size
     */
    public double determinant() {
        if (getRows() != getCols()) {
            throw new IllegalArgumentException("Matrix must be square to compute determinant.");
        }

        if (getRows() == 2) {
            return get(0, 0) * get(1, 1) - get(0, 1) * get(1, 0);
        }

        throw new UnsupportedOperationException("Determinant calculation is only implemented for 2x2 matrices.");
    }

    /**
     * Calculates the inverse of the matrix.
     *
     * @return the inverse matrix
     * @throws IllegalArgumentException if the matrix is not square
     * @throws ArithmeticException if the matrix is singular
     * @throws UnsupportedOperationException if inverse calculation is not implemented for the matrix size
     */
    public Matrix inverse() {
        if (getRows() != getCols()) {
            throw new IllegalArgumentException("Matrix must be square to compute inverse.");
        }

        if (getRows() == 2) {
            double det = determinant();
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

    /**
     * Multiplies the matrix by a scalar.
     *
     * @param scalar the scalar value
     * @return the resulting matrix
     */
    public Matrix scalarMultiply(double scalar) {
        long rows = getRows();
        long cols = getCols();
        Matrix result = new Matrix(rows, cols);

        for (long i = 0; i < rows; i++) {
            for (long j = 0; j < cols; j++) {
                result.set(i, j, this.get(i, j) * scalar);
            }
        }

        return result;
    }

    /**
     * Calculates the norm (Euclidean length) of the matrix.
     *
     * @return the norm value
     */
    public double norm() {
        double sum = 0;
        for (long i = 0; i < getRows(); i++) {
            for (long j = 0; j < getCols(); j++) {
                sum += Math.pow(get(i, j), 2);
            }
        }
        return Math.sqrt(sum);
    }

    /**
     * Swaps two rows in the matrix.
     *
     * @param row1 the first row index
     * @param row2 the second row index
     * @throws IndexOutOfBoundsException if row indices are out of bounds
     */
    public void swapRows(long row1, long row2) {
        if (row1 < 0 || row1 >= getRows() || row2 < 0 || row2 >= getRows()) {
            throw new IndexOutOfBoundsException("Invalid row index: " + row1 + " or " + row2);
        }
        double[] temp = values[(int) row1];
        values[(int) row1] = values[(int) row2];
        values[(int) row2] = temp;
    }

    /**
     * Scales a specific row by a given factor.
     *
     * @param row   the row index
     * @param factor the scaling factor
     * @throws IndexOutOfBoundsException if the row index is out of bounds
     */
    public void scaleRow(long row, double factor) {
        if (row < 0 || row >= getRows()) {
            throw new IndexOutOfBoundsException("Invalid row index: " + row);
        }
        for (long j = 0; j < getCols(); j++) {
            values[(int) row][(int) j] *= factor;
        }
    }

    /**
     * Adds a scaled version of one row to another row.
     *
     * @param row1 the row to be updated
     * @param row2 the row to be added
     * @param factor the scaling factor for row2
     * @throws IndexOutOfBoundsException if row indices are out of bounds
     */
    public void addRows(long row1, long row2, double factor) {
        if (row1 < 0 || row1 >= getRows() || row2 < 0 || row2 >= getRows()) {
            throw new IndexOutOfBoundsException("Invalid row index: " + row1 + " or " + row2);
        }
        for (long j = 0; j < getCols(); j++) {
            values[(int) row1][(int) j] += factor * values[(int) row2][(int) j];
        }
    }

    /**
     * Raises the matrix to the power of a given integer.
     *
     * @param n the exponent
     * @return the resulting matrix
     * @throws IllegalArgumentException if the matrix is not square or the exponent is negative
     */
    public Matrix power(int n) {
        if (getRows() != getCols()) {
            throw new IllegalArgumentException("Matrix must be square to compute power.");
        }

        if (n < 0) {
            throw new IllegalArgumentException("Exponent must be non-negative.");
        }

        Matrix result = Matrix.identity((int) getRows());
        Matrix base = new Matrix(values);

        for (int i = 0; i < n; i++) {
            result = result.multiply(base);
        }

        return result;
    }

    /**
     * Creates an identity matrix of specified size.
     *
     * @param size the size of the identity matrix
     * @return the identity matrix
     */
    public static Matrix identity(int size) {
        Matrix result = new Matrix(size, size);
        for (int i = 0; i < size; i++) {
            result.set(i, i, 1);
        }
        return result;
    }

    /**
     * Creates a zero matrix with specified number of rows and columns.
     *
     * @param rows the number of rows
     * @param cols the number of columns
     * @return the zero matrix
     */
    public static Matrix zero(long rows, long cols) {
        return new Matrix(rows, cols);
    }

    /**
     * Rotates the matrix by a given angle (for 2x2 matrices).
     *
     * @param angleDegrees the rotation angle in degrees
     * @return the rotated matrix
     * @throws UnsupportedOperationException if the matrix is not 2x2
     */
    public Matrix rotate(double angleDegrees) {
        if (getRows() != 2 || getCols() != 2) {
            throw new UnsupportedOperationException("Rotation is only implemented for 2x2 matrices.");
        }

        double angleRadians = Math.toRadians(angleDegrees);
        double cosAngle = Math.cos(angleRadians);
        double sinAngle = Math.sin(angleRadians);

        Matrix rotationMatrix = new Matrix(2, 2);
        rotationMatrix.set(0, 0, cosAngle);
        rotationMatrix.set(0, 1, -sinAngle);
        rotationMatrix.set(1, 0, sinAngle);
        rotationMatrix.set(1, 1, cosAngle);

        return this.multiply(rotationMatrix);
    }

    /**
     * Scales the matrix by given x and y scaling factors (for 2x2 matrices).
     *
     * @param scaleX the scaling factor for the x direction
     * @param scaleY the scaling factor for the y direction
     * @return the scaled matrix
     * @throws UnsupportedOperationException if the matrix is not 2x2
     */
    public Matrix scale(double scaleX, double scaleY) {
        if (getRows() != 2 || getCols() != 2) {
            throw new UnsupportedOperationException("Scaling is only implemented for 2x2 matrices.");
        }

        Matrix scalingMatrix = new Matrix(2, 2);
        scalingMatrix.set(0, 0, scaleX);
        scalingMatrix.set(0, 1, 0);
        scalingMatrix.set(1, 0, 0);
        scalingMatrix.set(1, 1, scaleY);

        return this.multiply(scalingMatrix);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (long i = 0; i < getRows(); i++) {
            for (long j = 0; j < getCols(); j++) {
                sb.append(String.format("%.2f ", values[(int) i][(int) j]));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matrix matrix = (Matrix) o;
        if (getRows() != matrix.getRows() || getCols() != matrix.getCols()) return false;
        for (long i = 0; i < getRows(); i++) {
            for (long j = 0; j < getCols(); j++) {
                if (Double.compare(matrix.get(i, j), this.get(i, j)) != 0) return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 1;
        for (long i = 0; i < getRows(); i++) {
            for (long j = 0; j < getCols(); j++) {
                result = 31 * result + Double.hashCode(values[(int) i][(int) j]);
            }
        }
        return result;
    }
}
