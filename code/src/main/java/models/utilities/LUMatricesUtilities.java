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
        // Note: pivoting information is stored in temperary vector pvt
        int n = a.getRows();
        int pvtk, pvti;
        double aet, tmp, mult;

        int[] pvt = new int[n];
        Matrix temp = new Matrix(a);

        for (int k = 0; k < n; k++) pvt[k] = k;

        Vector scale = new Vector(n);             // find scale vector
        for (int k = 0; k < n; k++) {
            scale.getData()[k] = 0;
            for (int j = 0; j < n; j++)
                if (Math.abs(scale.getData()[k]) < Math.abs(temp.getData()[k][j]))
                    scale.getData()[k] = Math.abs(temp.getData()[k][j]);
        }

        for (int k = 0; k < n - 1; k++) {            // main elimination loop

            // find the pivot in column k in rows pvt[k], pvt[k+1], ..., pvt[n-1]
            int pc = k;
            aet = Math.abs(temp.getData()[pvt[k]][k] / scale.getData()[k]);
            for (int i = k + 1; i < n; i++) {
                tmp = Math.abs(temp.getData()[pvt[i]][k] / scale.getData()[pvt[i]]);
                if (tmp > aet) {
                    aet = tmp;
                    pc = i;
                }
            }
            if (Math.abs(aet) < ZERO_LIMIT_BOUNDARY) throw new ZeroPivotException();
            if (pc != k) {                      // swap pvt[k] and pvt[pc]
                int ii = pvt[k];
                pvt[k] = pvt[pc];
                pvt[pc] = ii;
            }

            // now eliminate the column entries logically below mx[pvt[k]][k]
            pvtk = pvt[k];                           // pivot row
            for (int i = k + 1; i < n; i++) {
                pvti = pvt[i];
                if (temp.getData()[pvti][k] != 0) {
                    mult = temp.getData()[pvti][k] / temp.getData()[pvtk][k];
                    temp.getData()[pvti][k] = mult;
                    for (int j = k + 1; j < n; j++)
                        temp.getData()[pvti][j] -= mult * temp.getData()[pvtk][j];
                }
            }
        }

        Matrix p = new Matrix(n, n);
        for (int i = 0; i < n; i++)
            p.getData()[i][pvt[i]] = 1.0;

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

        // LU decomposition without pivoting
        for (int k = 0; k < n - 1; k++) {
            for (int i = k + 1; i < n; i++) {
                if (Math.abs(temp.getData()[k][k]) < 1.e-07) throw new ZeroPivotException();
                mult = temp.getData()[i][k] / temp.getData()[k][k];
                temp.getData()[i][k] = mult;                      // entries of L are saved in temp
                for (int j = k + 1; j < n; j++) {
                    temp.getData()[i][j] -= mult * temp.getData()[k][j];      // entries of U are saved in temp
                    if (Math.abs(temp.getData()[i][i]) < 1.e-07) throw new ZeroPivotException();
                }
            }
        }

        // create l and u from temp
        for (int i = 0; i < n; i++) l.getData()[i][i] = 1.0;
        for (int i = 1; i < n; i++)
            for (int j = 0; j < i; j++) l.getData()[i][j] = temp.getData()[i][j];

        for (int i = 0; i < n; i++)
            for (int j = i; j < n; j++) u.getData()[i][j] = temp.getData()[i][j];

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
