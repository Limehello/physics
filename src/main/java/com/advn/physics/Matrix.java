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
