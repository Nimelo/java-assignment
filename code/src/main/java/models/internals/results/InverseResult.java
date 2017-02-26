package models.internals.results;

import models.matrices.Matrix;
import models.utilities.LUDecomposition;

/**
 * Result of inversion routine invoked from the controller.
 * Created by Mateusz Gasior on 25-Feb-17.
 *
 * @author Mateusz Gasior
 * @see Result
 */
public class InverseResult extends Result {
    /**
     * Inverted original Matrix.
     */
    private final Matrix inverse;

    /**
     * Parameterized constructor.
     *
     * @param matrix          Original Matrix.
     * @param luDecomposition LU Decomposition of original Matrix.
     * @param inverse         Inverse Matrix.
     * @param determinant     Determinant of original Matrix.
     */
    public InverseResult(Matrix matrix, LUDecomposition luDecomposition, Matrix inverse, Double determinant) {
        super(matrix, luDecomposition, determinant);
        this.inverse = inverse;
    }

    /**
     * Overridden toString routine.
     *
     * @return String in specific format.
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Matrix Inversion\n")
                .append("Original Matrix\n").append(originalMatrix.toString());
        if (inverse == null) {
            stringBuilder.append("\nSingular matrix");
        } else {
            stringBuilder.append("\nLower Matrix\n").append(luDecomposition.getL().toString())
                    .append("\nUpper Matrix\n").append(luDecomposition.getU().toString())
                    .append("\nInverse Matrix\n").append(inverse.toString())
                    .append(String.format("\nDeterminant = %f", determinant));
        }

        return stringBuilder.toString();
    }
}
