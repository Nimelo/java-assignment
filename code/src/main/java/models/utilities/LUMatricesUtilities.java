package models.utilities;

import models.exceptions.NonSquareMatrixException;
import models.exceptions.ZeroPivotException;
import models.matrices.Matrix;
import models.utilities.objects.LUMatrixTuple;
import models.vectors.Vector;

/**
 * Utilities class for LU Matrices.
 * Provides features as decomposition, reorder and solver.
 * Created by Mateusz Gasior on 24-Feb-17.
 */
public class LUMatricesUtilities {
    /**
     * Limit for zero in calculations.
     */
    public static final double ZERO_LIMIT_BOUNDARY = 1.e-07;

    /**
     * Computes the permutation matrix P such that the matrix PA can be
     * factorised into LU and the system PA = Pb can be solved by forward and backward substitution.
     * Output is the permutation matrix P
     *
     * @param a A square matrix to be factorised.
     * @return Permutation matrix P
     * @throws NonSquareMatrixException when given Matrix has no square shape
     * @throws ZeroPivotException       when zero will occur on the pivot
     */
    public static Matrix reorder(final Matrix a) throws NonSquareMatrixException, ZeroPivotException {
        if (!a.isSquare())
            throw new NonSquareMatrixException();
        Matrix p = new Matrix(a.getRows(), a.getColumns());
        int pvtk, pvti, n = a.getRows();
        double aet, tmp, mult;
        int[] pvt = new int[n];
        Matrix temp = new Matrix(a);

        for (int k = 0; k < n; k++)
            pvt[k] = k;

        Vector scale = new Vector(n);
        for (int k = 0; k < n; k++) {
            scale.setAt(k, 0);
            for (int j = 0; j < n; j++)
                if (Math.abs(scale.getAt(k)) < Math.abs(temp.getAt(k, j)))
                    scale.setAt(k, Math.abs(temp.getAt(k, j)));
        }

        for (int k = 0; k < n - 1; k++) {
            int pc = k;
            aet = Math.abs(temp.getAt(pvt[k], k) / scale.getAt(k));
            for (int i = k + 1; i < n; i++) {
                tmp = Math.abs(temp.getAt(pvt[i], k) / scale.getAt(pvt[i]));
                if (tmp > aet) {
                    aet = tmp;
                    pc = i;
                }
            }
            if (Math.abs(aet) < ZERO_LIMIT_BOUNDARY)
                throw new ZeroPivotException();
            if (pc != k) {
                int ii = pvt[k];
                pvt[k] = pvt[pc];
                pvt[pc] = ii;
            }

            pvtk = pvt[k];
            for (int i = k + 1; i < n; i++) {
                pvti = pvt[i];
                if (temp.getAt(pvti, k) != 0) {
                    mult = temp.getAt(pvti, k) / temp.getAt(pvtk, k);
                    temp.setAt(pvti, k, mult);
                    for (int j = k + 1; j < n; j++) {
                        temp.setAt(pvti, j, temp.getAt(pvti, j) - mult * temp.getAt(pvtk, j));
                    }
                }
            }
        }

        for (int i = 0; i < n; i++) {
            p.setAt(i, pvt[i], 1.0);
        }

        return p;
    }

    /**
     * LU factorization routine.
     * Takes in a square matrix a and produces the lower (L) and upper (U) triangular matrices that factorise a
     *
     * @param a Input square matrix
     * @return Upper and lower triangular matrices that factorise matrix a
     * @throws NonSquareMatrixException when given Matrix has no square shape
     * @throws ZeroPivotException       when zero will occur on the pivot
     */
    public static LUMatrixTuple factorize(final Matrix a) throws NonSquareMatrixException, ZeroPivotException {
        if (!a.isSquare())
            throw new NonSquareMatrixException();

        int n = a.getRows();
        double mult;
        Matrix temp = new Matrix(a);
        Matrix l = new Matrix(n, n);
        Matrix u = new Matrix(n, n);

        for (int k = 0; k < n - 1; k++) {
            for (int i = k + 1; i < n; i++) {
                if (Math.abs(temp.getAt(k, k)) < ZERO_LIMIT_BOUNDARY)
                    throw new ZeroPivotException();
                mult = temp.getAt(i, k) / temp.getAt(k, k);
                temp.setAt(i, k, mult);
                for (int j = k + 1; j < n; j++) {
                    temp.setAt(i, j, temp.getAt(i, j) - mult * temp.getAt(k, j));
                    if (Math.abs(temp.getAt(i, i)) < ZERO_LIMIT_BOUNDARY)
                        throw new ZeroPivotException();
                }
            }
        }

        for (int i = 0; i < n; i++)
            l.setAt(i, i, 1.0);
        for (int i = 1; i < n; i++)
            for (int j = 0; j < i; j++)
                l.setAt(i, j, temp.getAt(i, j));

        for (int i = 0; i < n; i++)
            for (int j = i; j < n; j++)
                u.setAt(i, j, temp.getAt(i, j));

        return new LUMatrixTuple(l, u);
    }

    /**
     * Solves the equation LUx = b by performing forward and backward substitution.
     * Output is the solution vector x.
     *
     * @param l Lower square triangular matrix l.
     * @param u Upper square triangular matrix u.
     * @param b Right hand side vector b.
     * @return Solved vector x.
     */
    public static Vector solve(final Matrix l, final Matrix u, final Vector b) {
        int n = l.getRows();
        Vector x = new Vector(b);

        for (int i = 1; i < n; i++)
            for (int j = 0; j < i; j++)
                x.setAt(i, x.getAt(i) - l.getAt(i, j) * x.getAt(j));

        for (int i = n - 1; i >= 0; i--) {
            for (int j = i + 1; j < n; j++) {
                x.setAt(i, x.getAt(i) - u.getAt(i, j) * x.getAt(j));
            }
            x.setAt(i, x.getAt(i) / u.getAt(i, i));
        }

        return x;
    }
}
