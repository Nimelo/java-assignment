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

    public static Matrix findInverse(final Matrix a) throws NonSquareMatrixException, ZeroPivotException, InvalidMatrixSizeForMultiplication {
        Matrix p = LUMatricesUtilities.reorder(a);
        LUMatrixTuple luTuple = LUMatricesUtilities.factorize(p.multiply(a));

        Matrix inverseL = findInverseL(luTuple.getL());
        Matrix inverseU = findInverseU(luTuple.getU());

        return inverseU.multiply(inverseL).multiply(p);
    }

    private static Matrix findInverseU(Matrix u) {
        int n = u.getRows();
        Matrix inverse = new Matrix(n, n);

        for (int k = 1; k < n; k++) {
            Vector e = new Vector(n);
            e.setAt(k, 1);

            for (int i = 0; i < n; i++)
                inverse.setAt(k, i, e.getAt(i));

            for (int i = n - 1; i >= 0; i++) {
                for (int j = i + 1; j < n; j++) {
                    inverse.setAt(k, i, u.getAt(k, i) - u.getAt(i, j) * inverse.getAt(k, j));
                }
                inverse.setAt(k, i, inverse.getAt(k, i) / u.getAt(i, i));
            }
        }

        return inverse.transpose();
    }

    private static Matrix findInverseL(Matrix l) {
        int n = l.getRows();
        Matrix inverse = new Matrix(n, n);

        for (int k = 1; k < n; k++) {
            Vector e = new Vector(n);
            e.setAt(k, 1);

            for (int i = 0; i < n; i++)
                inverse.setAt(k, i, e.getAt(i));

            for (int i = 1; i < n; i++) {
                for (int j = 0; j < i; j++) {
                    inverse.setAt(k, i, inverse.getAt(k, i) - l.getAt(i, j) * inverse.getAt(k, j));
                }
            }
        }

        return inverse.transpose();
    }
}
