package models.utilities;

import models.exceptions.InvalidMatrixSizeForMultiplication;
import models.exceptions.InvalidMatrixSizesException;
import models.exceptions.NonSquareMatrixException;
import models.exceptions.ZeroPivotException;
import models.matrices.Matrix;
import models.utilities.objects.LUMatrixTuple;
import models.utilities.objects.PivotingResult;

/**
 * Provides features for calculating determinant of a matrix.
 * Created by Mateusz Gasior on 25-Feb-17.
 */
public class DeterminantUtilities {

    /**
     * Computes determinant for given matrix.
     * This method uses partial pivoting.
     *
     * @param matrix Matrix for computations
     * @return Determinant of given matrix.
     * @throws NonSquareMatrixException           when Matrix is not a square matrix.
     * @throws ZeroPivotException                 when zero occurs on pivot of a matrix.
     * @throws InvalidMatrixSizeForMultiplication when size of matrices is not the same during multiplication.
     * @throws InvalidMatrixSizesException        when size of matrices is not the same.
     */
    public static double computeDeterminant(final Matrix matrix) throws NonSquareMatrixException, ZeroPivotException, InvalidMatrixSizeForMultiplication, InvalidMatrixSizesException {
        PivotingResult reorder = LUMatricesUtilities.reorder(matrix);
        LUMatrixTuple factorize = LUMatricesUtilities.factorize(reorder.getMatrix().multiply(matrix));
        return computeDeterminant(factorize.getL(), factorize.getU(), reorder.getNumberOfRowExchanges());
    }

    /**
     * Computes determinant for given LU matrices and number of row exchanges.
     *
     * @param l Lower triangular matrix.
     * @param u Upper triangular matrix.
     * @param numberOfRowExchanges Number of exchanges during reordering.
     * @return Determinant for LU decomposed matrix.
     * @throws NonSquareMatrixException when L or U matrices are not square shaped.
     * @throws InvalidMatrixSizesException when L and U matrices have different sizes.
     */
    public static double computeDeterminant(final Matrix l, final Matrix u, int numberOfRowExchanges) throws NonSquareMatrixException, InvalidMatrixSizesException {
        if (!l.isSquare() || !u.isSquare())
            throw new NonSquareMatrixException();

        if (l.getRows() != u.getRows())
            throw new InvalidMatrixSizesException();

        int n = l.getRows();
        int s = numberOfRowExchanges;
        double determinant = 1.0;

        for (int i = 0; i < n; i++)
            determinant *= l.getAt(i, i) * u.getAt(i, i);

        return Math.pow(-1, s) * determinant;
    }
}
