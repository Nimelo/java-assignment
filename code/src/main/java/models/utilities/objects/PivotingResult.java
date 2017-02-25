package models.utilities.objects;

import models.matrices.Matrix;

/**
 * Result of Scaled Partial Pivoting.
 * Created by Mateusz Gasior on 25-Feb-17.
 */
public class PivotingResult {
    /**
     * Matrix.
     */
    private final Matrix matrix;

    /**
     * Number of exchanges during reordering.
     */
    private final int numberOfRowExchanges;

    /**
     * Parametrized constructor.
     * @param matrix Matrix.
     * @param numberOfRowExchanges Number of exchanges during reordering.
     */
    public PivotingResult(Matrix matrix, int numberOfRowExchanges) {
        this.matrix = matrix;
        this.numberOfRowExchanges = numberOfRowExchanges;
    }

    /**
     * Returns matrix.
     * @return Matrix.
     */
    public Matrix getMatrix() {
        return matrix;
    }

    /**
     * Returns number of exchanges during reordering.
     * @return Number of exchanges during reordering.
     */
    public int getNumberOfRowExchanges() {
        return numberOfRowExchanges;
    }
}
