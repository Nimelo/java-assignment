package controllers.transformers;

import controllers.exceptions.MatrixExtractionException;
import controllers.exceptions.NotEqualAmountOfColumnsInMatrixException;
import controllers.exceptions.VectorExtractionException;
import models.matrices.Matrix;
import models.vectors.Vector;

/**
 * Provides transformation utils from strings to objects.
 * Created by Mateusz Gasior on 26-Feb-17.
 *
 * @author Mateusz Gasior
 * @see Matrix
 * @see Vector
 */
public class TransformationUtils {
    /**
     * White-space characters regex.
     */
    public static final String WHITE_SPACE_CHARACTERS_REGEX = "\\s+";

    /**
     * Row separator regex.
     */
    public static final String ROW_SEPARATOR_REGEX = "\n";

    /**
     * Extracts matrix from a String.
     * Column separator is space.
     * Row separator is a new line character.
     *
     * @param matrix Matrix in a string format.
     * @return Matrix extracted from string.
     * @throws NotEqualAmountOfColumnsInMatrixException when number of columns is different for rows.
     * @throws MatrixExtractionException                when elements are not real numbers.
     */
    public static Matrix transformToMatrix(String matrix) throws MatrixExtractionException, NotEqualAmountOfColumnsInMatrixException {

        String[] rows = matrix.trim().split(ROW_SEPARATOR_REGEX);
        double[][] data = new double[rows.length][rows[0].split(WHITE_SPACE_CHARACTERS_REGEX).length];

        for (int i = 0; i < rows.length; i++) {
            String[] columns = rows[i].trim().split(WHITE_SPACE_CHARACTERS_REGEX);
            if (columns.length != data[0].length) {
                throw new NotEqualAmountOfColumnsInMatrixException();
            }

            for (int j = 0; j < columns.length; j++) {
                try {
                    data[i][j] = Double.valueOf(columns[j]);
                } catch (Throwable t) {
                    throw new MatrixExtractionException();
                }
            }
        }

        return new Matrix(data);
    }

    /**
     * Extracts vector from a String.
     * Value separator is single space.
     *
     * @param vector Vector in a string format.
     * @return Vector extracted from string.
     * @throws VectorExtractionException when elements of vector are not real numbers.
     */
    public static Vector transformToVector(String vector) throws VectorExtractionException {
        String[] values = vector.trim().split(WHITE_SPACE_CHARACTERS_REGEX);
        double[] data = new double[values.length];
        for (int i = 0; i < values.length; i++) {
            try {
                data[i] = Double.valueOf(values[i]);
            } catch (Throwable throwable) {
                throw new VectorExtractionException();
            }
        }

        return new Vector(data);
    }
}
