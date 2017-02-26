package controllers;

import controllers.exceptions.InversionConstraintsException;
import controllers.exceptions.LUPivotConstraintsException;
import controllers.exceptions.NotEqualAmountOfColumnsInMatrixException;
import controllers.results.InverseResult;
import controllers.results.LUPivotResult;
import models.exceptions.*;
import models.matrices.Matrix;
import models.utilities.LUDecomposition;
import models.vectors.Vector;

/**
 * Controller of Application.
 * Created by Mateusz Gasior on 25-Feb-17.
 */
public class MainController {
    /**
     * Matrix A.
     */
    private Matrix matrix;

    /**
     * Vector b.
     */
    private Vector vector;

    /**
     * Routine that calculates solution of problem Ax = b
     *
     * @return Result of LU Pivot routine.
     * @throws NonSquareMatrixException    when matrix does not have a square shape.
     * @throws InvalidMatrixSizesException when matrices during calculations will have different sizes.
     * @throws LUPivotConstraintsException when requirements for performing LU pivoting will not be meet.
     * @throws SingularMatrixException     when matrix is singular.
     */
    public LUPivotResult LUPivot() throws NonSquareMatrixException, InvalidMatrixSizesException, LUPivotConstraintsException, SingularMatrixException {
        checkLUPivotConstraints();

        LUDecomposition factorize = new LUDecomposition(matrix);
        Vector solution = factorize.solve(vector);
        double determinant = factorize.det();

        return new LUPivotResult(matrix, vector, factorize.getL(), factorize.getU(), solution, determinant);
    }

    /**
     * Checks if inverse routine can be performed.
     *
     * @throws InversionConstraintsException when routine cannot be performed.
     */
    private void checkInverseConstraints() throws InversionConstraintsException {
        if (matrix == null || !matrix.isSquare()) {
            throw new InversionConstraintsException();
        }
    }

    /**
     * Checks if LU Pivoting routine can be performed.
     *
     * @throws LUPivotConstraintsException when routine cannot be performed.
     */
    private void checkLUPivotConstraints() throws LUPivotConstraintsException {
        if (matrix == null || vector == null) {
            throw new LUPivotConstraintsException();
        } else {
            if (vector.getSize() != matrix.getColumns()) {
                throw new LUPivotConstraintsException();
            }
        }
    }

    /**
     * Inverse routine.
     *
     * @return Result of inverse routine for view.
     * @throws InversionConstraintsException when requirements for inverse routine does not meet desired requirements.
     */
    public InverseResult inverse() throws InversionConstraintsException {
        checkInverseConstraints();
        Matrix inverse = null;
        double determinant = 0.0;
        Matrix l = null;
        Matrix u = null;

        try {
            LUDecomposition luDecomposition = new LUDecomposition(matrix);

            l = luDecomposition.getL();
            u = luDecomposition.getU();

            inverse = luDecomposition.inverse();

            determinant = luDecomposition.det();
        } catch (Throwable e) {

        } finally {
            return new InverseResult(matrix, l, u, inverse, determinant);
        }
    }

    /**
     * Extracts and sets matrix from a String.
     * Column separator is space.
     * Row separator is a new line character.
     *
     * @param matrix Matrix in a string format.
     * @throws NotEqualAmountOfColumnsInMatrixException when number of columns is different for rows.
     */
    public void extractAndSetMatrix(String matrix) throws NotEqualAmountOfColumnsInMatrixException {
        final String rowsSeparator = "\n";
        final String columnSeparator = " ";

        String[] rows = matrix.split(rowsSeparator);
        double[][] data = new double[rows.length][rows[0].split(columnSeparator).length];

        for (int i = 0; i < rows.length; i++) {
            String[] columns = rows[i].split(columnSeparator);
            if (columns.length != data[0].length) {
                throw new NotEqualAmountOfColumnsInMatrixException();
            }

            for (int j = 0; j < columns.length; j++) {
                data[i][j] = Double.valueOf(columns[j]);
            }
        }

        this.matrix = new Matrix(data);
    }

    /**
     * Extracts and sets vector from a String.
     * Value separator is single space.
     *
     * @param vector Vector in a string format.
     */
    public void extractAndSetVector(String vector) {
        final String valuesSeparator = " ";

        String[] values = vector.split(valuesSeparator);

        double[] data = new double[values.length];
        for (int i = 0; i < values.length; i++) {
            data[i] = Double.valueOf(values[i]);
        }

        this.vector = new Vector(data);
    }
}
