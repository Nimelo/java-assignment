package controllers.results;

import models.matrices.Matrix;
import models.vectors.Vector;

/**
 * Result of LUPivot routine invoked from controller.
 * Created by Mateusz Gasior on 25-Feb-17.
 */
public class LUPivotResult {
    /**
     * Original Matrix.
     */
    private final Matrix originalMatrix;

    /**
     * Original Vector.
     */
    private final Vector originalVector;

    /**
     * Lower triangular Matrix.
     */
    private final Matrix lMatrix;

    /**
     * Upper triangular Matrix.
     */
    private final Matrix uMatrix;

    /**
     * Solution Vector.
     */
    private final Vector solution;

    /**
     * Determinant of original matrix.
     */
    private final double determinant;

    /**
     * Parametrized constructor.
     *
     * @param matrix      Original Matrix.
     * @param vector      Original Vector.
     * @param l           Lower triangular Matrix.
     * @param u           Upper triangular Matrix.
     * @param solution    Solution Vector.
     * @param determinant Determinant of original Matrix.
     */
    public LUPivotResult(Matrix matrix, Vector vector, Matrix l, Matrix u, Vector solution, double determinant) {
        originalMatrix = matrix;
        originalVector = vector;
        lMatrix = l;
        uMatrix = u;
        this.solution = solution;
        this.determinant = determinant;
    }

    /**
     * Overridden toString routine.
     * @return String with specific format.
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("LU Decomposition with scaled partial pivoting\n")
                .append("Original matrix\n").append(originalMatrix.toString())
                .append("Original vector\n").append(originalVector.toString())
                .append("Lower matrix\n").append(lMatrix.toString())
                .append("Upper matrix\n").append(uMatrix.toString())
                .append("Solution\n").append(solution.toString())
                .append(String.format("Determinant = %f", determinant));

        return stringBuilder.toString();
    }
}
