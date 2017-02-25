package controllers;

import controllers.exceptions.InversionConstraintsException;
import controllers.exceptions.LUPivotConstraintsException;
import controllers.exceptions.NotEqualAmountOfColumnsInMatrixException;
import controllers.results.InverseResult;
import controllers.results.LUPivotResult;
import models.exceptions.*;
import models.matrices.Matrix;
import models.utilities.DeterminantUtilities;
import models.utilities.LUMatricesUtilities;
import models.utilities.objects.LUMatrixTuple;
import models.utilities.objects.PivotingResult;
import models.vectors.Vector;

import static models.utilities.InverseMatrixUtilities.findInverseOfLowerTriangularMatrix;
import static models.utilities.InverseMatrixUtilities.findInverseOfUpperTriangularMatrix;

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
     * @return Result of LU Pivot routine.
     * @throws NonSquareMatrixException
     * @throws ZeroPivotException
     * @throws InvalidMatrixSizeForMultiplication
     * @throws InvalidMatrixSizesException
     * @throws LUPivotConstraintsException
     * @throws MatrixVectorMultiplicationSizeException
     */
    public LUPivotResult LUPivot() throws NonSquareMatrixException, ZeroPivotException, InvalidMatrixSizeForMultiplication, InvalidMatrixSizesException, LUPivotConstraintsException, MatrixVectorMultiplicationSizeException {
        checkLUPivotConstraints();
        PivotingResult reorder = LUMatricesUtilities.reorder(matrix);
        LUMatrixTuple factorize = LUMatricesUtilities.factorize(reorder.getMatrix().multiply(matrix));
        Vector solution = LUMatricesUtilities.solve(factorize.getL(), factorize.getU(), reorder.getMatrix().multiply(vector));
        double determinant = DeterminantUtilities.computeDeterminant(factorize.getL(), factorize.getU(), reorder.getNumberOfRowExchanges());

        return new LUPivotResult(matrix, vector, factorize.getL(), factorize.getU(), solution, determinant);
    }

    private void checkInverseConstraints() throws InversionConstraintsException {
        if (matrix == null || !matrix.isSquare()) {
            throw new InversionConstraintsException();
        }
    }

    private void checkLUPivotConstraints() throws LUPivotConstraintsException {
        if (matrix == null || vector == null) {
            throw new LUPivotConstraintsException();
        } else {
            if (vector.getSize() != matrix.getColumns()) {
                throw new LUPivotConstraintsException();
            }
        }
    }

    public InverseResult inverse() throws InversionConstraintsException {
        checkInverseConstraints();
        Matrix inverse = null;
        double determinant = 0.0;
        Matrix l = null;
        Matrix u = null;

        try {
            PivotingResult reorder = LUMatricesUtilities.reorder(matrix);
            Matrix p = reorder.getMatrix();
            LUMatrixTuple luTuple = LUMatricesUtilities.factorize(p.multiply(matrix));

            l = luTuple.getL();
            u = luTuple.getU();

            Matrix inverseL = findInverseOfLowerTriangularMatrix(luTuple.getL());
            Matrix inverseU = findInverseOfUpperTriangularMatrix(luTuple.getU());

            inverse = inverseU.multiply(inverseL).multiply(p);

            determinant = DeterminantUtilities.computeDeterminant(l, u, reorder.getNumberOfRowExchanges());
        } catch (Throwable e) {

        } finally {
            return new InverseResult(matrix, l, u, inverse, determinant);
        }
    }

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
