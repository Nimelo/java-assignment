package models.utilities;

import models.exceptions.InvalidMatrixSizesException;
import models.exceptions.NonSquareMatrixException;
import models.exceptions.SingularMatrixException;
import models.matrices.Matrix;
import models.vectors.Vector;

import java.io.Serializable;

/**
 * Performs LU decomposition of m by n matrix.
 * If isNonsingular() returns false then all linear solver will fail.
 * Created by Mateusz Gasior on 26-Feb-17.
 */
public class LUDecomposition implements Serializable {

    /**
     * Array for internal storage of decomposition.
     */
    private double[][] LU;

    /**
     * Row dimension.
     */
    private int m;

    /**
     * Column dimension.
     */
    private int n;

    /**
     * Pivot sign.
     */
    private int pivSign;

    /**
     * Internal storage of pivot vector.
     */
    private int[] piv;

    /**
     * LU Decomposition
     * Structure to access L, U and piv.
     *
     * @param A Rectangular matrix
     */

    /**
     * LU Decomposition
     * Structure to access L, U and pivot.
     *
     * @param A Rectangular matrix.
     */
    public LUDecomposition(Matrix A) {

        // Use a "left-looking", dot-product, Crout/Doolittle algorithm.

        LU = A.getDataCopy();
        m = A.getRows();
        n = A.getColumns();
        piv = new int[m];
        for (int i = 0; i < m; i++) {
            piv[i] = i;
        }
        pivSign = 1;
        double[] LUrowi;
        double[] LUcolj = new double[m];

        // Outer loop.

        for (int j = 0; j < n; j++) {

            // Make a copy of the j-th column to localize references.

            for (int i = 0; i < m; i++) {
                LUcolj[i] = LU[i][j];
            }

            // Apply previous transformations.

            for (int i = 0; i < m; i++) {
                LUrowi = LU[i];

                // Most of the time is spent in the following dot product.

                int kmax = Math.min(i, j);
                double s = 0.0;
                for (int k = 0; k < kmax; k++) {
                    s += LUrowi[k] * LUcolj[k];
                }

                LUrowi[j] = LUcolj[i] -= s;
            }

            // Find pivot and exchange if necessary.

            int p = j;
            for (int i = j + 1; i < m; i++) {
                if (Math.abs(LUcolj[i]) > Math.abs(LUcolj[p])) {
                    p = i;
                }
            }
            if (p != j) {
                for (int k = 0; k < n; k++) {
                    double t = LU[p][k];
                    LU[p][k] = LU[j][k];
                    LU[j][k] = t;
                }
                int k = piv[p];
                piv[p] = piv[j];
                piv[j] = k;
                pivSign = -pivSign;
            }

            // Compute multipliers.

            if (j < m & LU[j][j] != 0.0) {
                for (int i = j + 1; i < m; i++) {
                    LU[i][j] /= LU[j][j];
                }
            }
        }
    }

    /**
     * Return lower triangular matrix from LU decomposition.
     *
     * @return Lower triangular matrix.
     */
    public Matrix getL() {
        Matrix X = new Matrix(m, n);
        double[][] L = X.getData();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i > j) {
                    L[i][j] = LU[i][j];
                } else if (i == j) {
                    L[i][j] = 1.0;
                } else {
                    L[i][j] = 0.0;
                }
            }
        }
        return X;
    }

    /**
     * Return upper triangular matrix from LU decomposition.
     *
     * @return Upper triangular matrix.
     */
    public Matrix getU() {
        Matrix X = new Matrix(n, n);
        double[][] U = X.getData();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i <= j) {
                    U[i][j] = LU[i][j];
                } else {
                    U[i][j] = 0.0;
                }
            }
        }
        return X;
    }

    /**
     * Returns pivot permutation vector.
     *
     * @return Pivot permutation vector.
     */
    public int[] getPivot() {
        int[] p = new int[m];
        for (int i = 0; i < m; i++) {
            p[i] = piv[i];
        }
        return p;
    }

    /**
     * Returns pivot permutation vector.
     *
     * @return Pivot permuation vector.
     */
    public double[] getDoublePivot() {
        double[] vals = new double[m];
        for (int i = 0; i < m; i++) {
            vals[i] = (double) piv[i];
        }
        return vals;
    }

    /**
     * Calculates determinant of square matrix.
     *
     * @return determinant of Matrix.
     * @throws NonSquareMatrixException when Matrix is not square
     */
    public double det() throws NonSquareMatrixException {
        if (m != n) {
            throw new NonSquareMatrixException();
        }
        double d = (double) pivSign;
        for (int j = 0; j < n; j++) {
            d *= LU[j][j];
        }
        return d;
    }

    /**
     * Solves linear system Ax = b
     *
     * @param B Right hand-side vector.
     * @return Solution for linear system.
     * @throws InvalidMatrixSizesException when dimensions of the system are not correct
     * @throws SingularMatrixException     when matrix is singular.
     */
    public Vector solve(Vector B) throws SingularMatrixException, InvalidMatrixSizesException {
        Matrix matrix = new Matrix(B.getSize(), 1);
        for (int i = 0; i < B.getSize(); i++) {
            matrix.getData()[i][0] = B.getAt(i);
        }

        Matrix solve = this.solve(matrix);

        double[] doubles = new double[solve.getRows()];
        for (int i = 0; i < doubles.length; i++) {
            doubles[i] = solve.getData()[i][0];
        }

        return new Vector(doubles);
    }

    /**
     * Solves linear system AX= B
     *
     * @param B A Matrix with as many rows as A and any number of columns.
     * @return Solution for linear system.
     * @throws InvalidMatrixSizesException when dimensions of the system are not correct
     * @throws SingularMatrixException     when matrix is singular.
     */
    public Matrix solve(Matrix B) throws SingularMatrixException, InvalidMatrixSizesException {
        if (B.getRows() != m) {
            throw new InvalidMatrixSizesException();
        }
        if (!this.isNonSingular()) {
            throw new SingularMatrixException();
        }

        // Copy right hand side with pivoting
        int nx = B.getColumns();
        Matrix Xmat = B.getMatrix(piv, 0, nx - 1);
        double[][] X = Xmat.getData();

        // Solve L*Y = B(piv,:)
        for (int k = 0; k < n; k++) {
            for (int i = k + 1; i < n; i++) {
                for (int j = 0; j < nx; j++) {
                    X[i][j] -= X[k][j] * LU[i][k];
                }
            }
        }
        // Solve U*X = Y;
        for (int k = n - 1; k >= 0; k--) {
            for (int j = 0; j < nx; j++) {
                X[k][j] /= LU[k][k];
            }
            for (int i = 0; i < k; i++) {
                for (int j = 0; j < nx; j++) {
                    X[i][j] -= X[k][j] * LU[i][k];
                }
            }
        }
        return Xmat;
    }

    /**
     * Checks if the Matrix is non singular.
     *
     * @return Non singularity of matrix. When there is no zeros at diagonal.
     */
    public boolean isNonSingular() {
        for (int j = 0; j < n; j++) {
            if (LU[j][j] == 0)
                return false;
        }
        return true;
    }

    /**
     * Calculates the inverse of matrix.
     *
     * @return Inverse of matrix.
     * @throws InvalidMatrixSizesException when dimensions of the system are not correct
     * @throws SingularMatrixException     when matrix is singular.
     */
    public Matrix inverse() throws SingularMatrixException, InvalidMatrixSizesException {
        return solve(Matrix.identity(m, m));
    }
}
