package models.internals.results;

import models.matrices.Matrix;
import models.utilities.LUDecomposition;
import models.vectors.Vector;

import java.io.Serializable;

/**
 * Common result of computation routines.
 * Created by Mateusz Gasior on 26-Feb-17.
 */
public class Result implements Serializable {
    /**
     * Original Matrix.
     */
    protected final Matrix originalMatrix;

    /**
     * LU Decomposition of original matrix.
     */
    protected final LUDecomposition luDecomposition;

    /**
     * Determinant of original matrix.
     */
    protected final Double determinant;

    /**
     * Parametrized constructor.
     *
     * @param originalMatrix  Original Matrix.
     * @param luDecomposition LU Decomposition of original matrix.
     * @param determinant     Determinant of original matrix.
     */
    public Result(Matrix originalMatrix, LUDecomposition luDecomposition, Double determinant) {
        this.originalMatrix = originalMatrix;
        this.luDecomposition = luDecomposition;
        this.determinant = determinant;
    }

    /**
     * Returns original matrix.
     *
     * @return Original matrix.
     */
    public Matrix getOriginalMatrix() {
        return originalMatrix;
    }

    /**
     * Returns LU Decomposition of original matrix.
     *
     * @return LU Decomposition of original matrix.
     */
    public LUDecomposition getLuDecomposition() {
        return luDecomposition;
    }
}
