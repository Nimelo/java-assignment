package models.utilities.objects;

import models.matrices.Matrix;

/**
 * Class that stores LU Matrices.
 * Created by Mateusz Gasior on 24-Feb-17.
 */
public class LUMatrixTuple {
    /**
     * Matrix L.
     */
    private Matrix l;

    /**
     * Matrix U.
     */
    private Matrix u;

    /**
     * Constructor that takes 2 parameters.
     * Matrix L and U as input parameters.
     * @param l Lower triangular matrix.
     * @param u Upper triangular matrix.
     */
    public LUMatrixTuple(Matrix l, Matrix u) {
        this.l = l;
        this.u = u;
    }

    /**
     * Returns Lower triangular matrix.
     * @return Lower triangular matrix.
     */
    public Matrix getL() {
        return l;
    }

    /**
     * Returns Upper triangular matrix.
     * @return Upper triangular matrix.
     */
    public Matrix getU() {
        return u;
    }
}
