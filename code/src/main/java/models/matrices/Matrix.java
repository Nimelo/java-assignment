package models.matrices;

import models.exceptions.InvalidMatrixSizeForMultiplication;
import models.exceptions.MatrixVectorMultiplicationSizeException;
import models.vectors.Vector;

import java.util.Arrays;

/**
 * A matrix class for data storage od a 2D array of doubles.
 * Implementation uses 2D array of doubles.
 * Created by Mateusz Gasior on 24-Feb-17.
 */
public final class Matrix {
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
     * Gives product of multiplication of two matrices
     *
     * @param a Matrix from the right hand-side.
     * @return Result of multiplication.
     * @throws InvalidMatrixSizeForMultiplication when matrices have different size
     */
    public Matrix multiply(final Matrix a) throws InvalidMatrixSizeForMultiplication {
        int nRows = getRows();
        int nCols = getColumns();

        if (a.getRows() != nRows
                || a.getColumns() != nCols)
            throw new InvalidMatrixSizeForMultiplication();

        Matrix multiplication = new Matrix(nRows, nCols);

        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < a.getColumns(); j++) {
                for (int k = 0; k < getColumns(); k++) {
                    multiplication.setAt(i, j, multiplication.getAt(i, j) + this.getAt(i, k) * a.getAt(k, j));
                }
            }
        }

        return multiplication;
    }

    /**
     * Calculates determinant of the matrix
     *
     * @return Determinant of matrix
     */
    public double determinant() throws Exception {
        throw new Exception();
    }

    /**
     * Transposing matrix routine
     *
     * @return
     */
    public Matrix transpose() {
        Matrix transpose = new Matrix(columns, rows);
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                transpose.data[i][j] = this.data[j][i];
            }
        }

        return transpose;
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
     * Multiplies Matrix by a Vector.
     *
     * @param vector Right hand-side vector
     * @return Product of Matrix-Vector multiplication - Vector
     * @throws MatrixVectorMultiplicationSizeException when size of the vector is not equal to number of columns in a matrix
     */
    public Vector multiply(Vector vector) throws MatrixVectorMultiplicationSizeException {
        int nRows = getRows();
        int nCols = getColumns();

        if (nCols != vector.getSize())
            throw new MatrixVectorMultiplicationSizeException();

        Vector res = new Vector(nRows);

        for (int i = 0; i < nRows; i++)
            for (int j = 0; j < nCols; j++)
                res.getData()[i] += this.getData()[i][j] * vector.getData()[j];

        return res;
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
}