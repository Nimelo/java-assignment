package models.matrices;

import models.vectors.Vector;

import java.io.Serializable;

/**
 * A matrix class for data storage od a 2D array of doubles.
 * Implementation uses 2D array of doubles.
 * Created by Mateusz Gasior on 24-Feb-17.
 */
public final class Matrix implements Serializable {
    /**
     * Elements of matrix.
     */
    private final double[][] data;

    /**
     * Number of rows in matrix.
     */
    private final int rows;

    /**
     * Number of columns in matrix.
     */
    private final int columns;

    /**
     * Parametrized constructor.
     * Sets the rows and columns in matrix.
     * Allocates the data 2D array for given parameters.
     *
     * @param rows    Number of rows in matrix.
     * @param columns Number of columns in matrix.
     * @throws Exception when rows or columns is negative.
     */
    public Matrix(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.data = new double[rows][columns];
    }

    /**
     * Parametrized constructor.
     * Sets the rows and columns in matrix.
     * Allocates the data 2D array for given 2D array.
     *
     * @param data 2D array used to set elements
     */
    public Matrix(double[][] data) {
        this.rows = data.length;
        this.columns = data[0].length;
        this.data = new double[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                this.data[i][j] = data[i][j];
            }
        }
    }

    /**
     * Copy constructor.
     * Copies the shape and the data of the given matrix.
     *
     * @param toCopy Matrix to be copied.
     */
    public Matrix(Matrix toCopy) {
        this.rows = toCopy.getRows();
        this.columns = toCopy.getColumns();
        this.data = new double[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                this.data[i][j] = toCopy.data[i][j];
            }
        }
    }

    /**
     * Returns number of rows in matrix.
     *
     * @return Number of rows in matrix.
     */
    public int getRows() {
        return rows;
    }

    /**
     * Returns number of columns in matrix.
     *
     * @return Number of columns in matrix.
     */
    public int getColumns() {
        return columns;
    }

    /**
     * Returns data of the matrix getAt given position.
     *
     * @param row    Index of row in the matrix.
     * @param column Index of column on the matrix.
     * @return Element getAt index of row and matrix.
     */
    public double getAt(int row, int column) {
        if (row < rows)
            if (column < columns)
                return data[row][column];
        throw new IndexOutOfBoundsException();
    }

    /**
     * Sets value getAt given index in matrix.
     *
     * @param row    Index of row in the matrix.
     * @param column Index of column in the matrix.
     * @param value  Value to be stored.
     */
    public void setAt(int row, int column, double value) {
        if (row < rows)
            if (column < columns) {
                data[row][column] = value;
                return;
            }
        throw new IndexOutOfBoundsException();
    }

    /**
     * Checks if matrix has a square shape.
     *
     * @return True if rows equals to columns. Otherwise false.
     */
    public boolean isSquare() {
        return rows == columns;
    }

    /**
     * Returns data matrix
     *
     * @return elements of matrix
     */
    public double[][] getData() {
        return data;
    }

    /**
     * Transforms matrix to string object.
     *
     * @return Matrix interpretation in String format.
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                stringBuilder.append(String.format("%f ", data[i][j]));
            }
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }

    /**
     * Returns a sub-matrix for given parameters.
     *
     * @param r                  Array of row indices.
     * @param initialColumnIndex Initial column index
     * @param finalColumnIndex   Final column index
     * @return A(r(:), j0:finalColumnIndex)
     * @throws ArrayIndexOutOfBoundsException Submatrix indices
     */
    public Matrix getMatrix(int[] r, int initialColumnIndex, int finalColumnIndex) {
        Matrix X = new Matrix(r.length, finalColumnIndex - initialColumnIndex + 1);
        double[][] B = X.getData();
        try {
            for (int i = 0; i < r.length; i++) {
                for (int j = initialColumnIndex; j <= finalColumnIndex; j++) {
                    B[i][j - initialColumnIndex] = data[r[i]][j];
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ArrayIndexOutOfBoundsException("Submatrix indices");
        }
        return X;
    }


    public double[][] getDataCopy() {
        double[][] copy = new double[rows][columns];

        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++)
                copy[i][j] = data[i][j];

        return copy;
    }


    /**
     * Generates identity matrix.
     *
     * @param m Number of rows.
     * @param n Number of columns.
     * @return An m-by-n matrix with ones on the diagonal and zeros elsewhere.
     */
    public static Matrix identity(int m, int n) {
        Matrix A = new Matrix(m, n);
        double[][] X = A.getData();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                X[i][j] = (i == j ? 1.0 : 0.0);
            }
        }
        return A;
    }

    /**
     * Overridden equals method. Checks if all the elements in matrix are equal.
     *
     * @param obj Object to compare.
     * @return True if object is Matrix and elements are the same. Otherwise false.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Matrix)) {
            return false;
        } else {
            Matrix other = (Matrix) obj;

            if (rows != other.getRows()
                    || columns != other.getColumns())
                return false;

            for (int i = 0; i < rows; i++)
                for (int j = 0; j < columns; j++)
                    if (data[i][j] != other.data[i][j])
                        return false;
        }
        return true;
    }
}