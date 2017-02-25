package controllers.results;

import models.matrices.Matrix;

/**
 * Result of inversion routine invoked from the controller.
 * Created by Mateusz Gasior on 25-Feb-17.
 */
public class InverseResult {
    /**
     * Original Matrix.
     */
    private final Matrix originalMatrix;

    /**
     * Lower triangular Matrix L.
     */
    private final Matrix l;

    /**
     * Upper triangular Matrix U.
     */
    private final Matrix u;

    /**
     * Inverted original Matrix.
     */
    private final Matrix inverse;

    /**
     * Determinant of original Matrix.
     */
    private final double determinant;

    /**
     * Parameterized constructor.
     *
     * @param matrix      Original Matrix.
     * @param l           Lower triangular Matrix.
     * @param u           Upper triangular Matrix.
     * @param inverse     Inverse Matrix.
     * @param determinant Determinant of original Matrix.
     */
    public InverseResult(Matrix matrix, Matrix l, Matrix u, Matrix inverse, double determinant) {
        this.originalMatrix = matrix;
        this.l = l;
        this.u = u;
        this.inverse = inverse;
        this.determinant = determinant;
    }

    /**
     * Overridden toString routine.
     * @return String in specific format.
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();


        stringBuilder.append("Matrix Inversion\n")
                .append("Original Matrix\n").append(originalMatrix.toString());
        if (l == null || u == null || inverse == null) {
            stringBuilder.append("Singular matrix");
        } else {
            stringBuilder.append("Lower Matrix\n").append(l.toString())
                    .append("Upper Matrix\n").append(u.toString())
                    .append("Inverse Matrix\n").append(inverse.toString())
                    .append(String.format("Determinant = %f", determinant));
        }

        return stringBuilder.toString();
    }
}
