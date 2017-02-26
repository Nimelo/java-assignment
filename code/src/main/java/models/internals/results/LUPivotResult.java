package models.internals.results;

import models.matrices.Matrix;
import models.utilities.LUDecomposition;
import models.vectors.Vector;

/**
 * Result of LUPivot routine invoked from controller.
 * Created by Mateusz Gasior on 25-Feb-17.
 */
public class LUPivotResult extends Result {
    /**
     * Original Vector.
     */
    private final Vector originalVector;

    /**
     * Solution Vector.
     */
    private final Vector solution;

    /**
     * Parametrized constructor.
     *
     * @param matrix          Original Matrix.
     * @param vector          Original Vector.
     * @param luDecomposition LU Decomposition of original matrix.
     * @param solution        Solution Vector.
     * @param determinant     Determinant of original Matrix.
     */
    public LUPivotResult(Matrix matrix, Vector vector, LUDecomposition luDecomposition, Vector solution, Double determinant) {
        super(matrix, luDecomposition, determinant);
        originalVector = vector;
        this.solution = solution;
    }

    /**
     * Returns original vector.
     * @return Original vector.
     */
    public Vector getOriginalVector() {
        return originalVector;
    }

    /**
     * Overridden toString routine.
     *
     * @return String with specific format.
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("LU Decomposition with scaled partial pivoting\n")
                .append("Original matrix\n").append(originalMatrix.toString())
                .append("\nOriginal vector\n").append(originalVector.toString())
                .append("\nLower matrix\n").append(luDecomposition.getL().toString())
                .append("\nUpper matrix\n").append(luDecomposition.getU().toString())
                .append("\nSolution\n").append(solution.toString())
                .append(String.format("\nDeterminant = %f", determinant));

        return stringBuilder.toString();
    }
}
