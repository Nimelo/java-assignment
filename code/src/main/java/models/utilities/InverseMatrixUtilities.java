package models.utilities;

import models.exceptions.InvalidMatrixSizeForMultiplication;
import models.exceptions.NonSquareMatrixException;
import models.exceptions.ZeroPivotException;
import models.matrices.Matrix;
import models.utilities.objects.LUMatrixTuple;
import models.vectors.Vector;

/**
 * Utilities class for determining inverse matrix.
 * Created by Mateusz Gasior on 24-Feb-17.
 */
public class InverseMatrixUtilities {

    /**
     * Determines Inverse Matrix using partial pivoting LU decomposition.
     * If exception is thrown inverse matrix does not exist.
     *
     * @param a Matrix for which inverse matrix should be determined
     * @return Inversion matrix
     * @throws NonSquareMatrixException           when provided matrix is not square shaped
     * @throws ZeroPivotException                 when zero occurs on a pivot during LU decomposition
     * @throws InvalidMatrixSizeForMultiplication when matrix sizes are not correct during multiplication
     */
    public static Matrix findInverse(final Matrix a) throws NonSquareMatrixException, ZeroPivotException, InvalidMatrixSizeForMultiplication {
        Matrix p = LUMatricesUtilities.reorder(a);
        LUMatrixTuple luTuple = LUMatricesUtilities.factorize(p.multiply(a));

        Matrix inverseL = findInverseOfLowerTriangularMatrix(luTuple.getL());
        Matrix inverseU = findInverseOfUpperTriangularMatrix(luTuple.getU());

        return inverseU.multiply(inverseL).multiply(p);
    }

    /**
     * Determines Inverse Matrix for upper triangular matrix.
     *
     * @param u Upper triangular matrix.
     * @return Inversion matrix
     */
    public static Matrix findInverseOfUpperTriangularMatrix(Matrix u) {
        int n = u.getRows();
        Matrix inverse = new Matrix(n, n);

        for (int k = 0; k < n; k++) {
            Vector e = new Vector(n);
            e.setAt(k, 1);

            for (int i = 0; i < n; i++)
                inverse.setAt(k, i, e.getAt(i));

            for (int i = n - 1; i >= 0; i--) {
                for (int j = i + 1; j < n; j++) {
                    inverse.getData()[k][i] -= u.getData()[i][j] * inverse.getData()[k][j];
                }
                inverse.getData()[k][i] /= u.getData()[i][i];
            }
        }

        return inverse.transpose();
    }

    /**
     * Determines Inverse Matrix for lower triangular matrix.
     *
     * @param l Lower triangular matrix.
     * @return Inversion matrix.
     */
    public static Matrix findInverseOfLowerTriangularMatrix(Matrix l) {
        int n = l.getRows();
        Matrix inverse = new Matrix(n, n);

        for (int k = 0; k < n; k++) {
            Vector e = new Vector(n);
            e.setAt(k, 1);

            for (int i = 0; i < n; i++)
                inverse.setAt(k, i, e.getAt(i));

            for (int i = 1; i < n; i++) {
                for (int j = 0; j < i; j++) {
                    inverse.getData()[k][i] -= l.getData()[i][j] * inverse.getData()[k][j];
                }
            }
        }

        return inverse.transpose();
    }
}
