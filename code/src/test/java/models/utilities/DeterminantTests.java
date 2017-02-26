package models.utilities;

import models.exceptions.InvalidMatrixSizesException;
import models.exceptions.NonSquareMatrixException;
import models.matrices.Matrix;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Mateusz Gasior on 25-Feb-17.
 */
class DeterminantTests {
    @Test
    void computeDeterminantSetA() throws InvalidMatrixSizesException, NonSquareMatrixException {
        double[][] data = {{1, 3, 2}, {4, 1, 3}, {2, 5, 2}};

        Matrix matrix = new Matrix(data);

        double actualDeterminant = new LUDecomposition(matrix).det();
        double expectedDeterminant = 17;

        assertEquals(expectedDeterminant, actualDeterminant, 1e-2);
    }

    @Test
    void computeDeterminantSetB() throws InvalidMatrixSizesException, NonSquareMatrixException {
        double[][] data = {{3, 2, 0, 1}, {4, 0, 1, 2}, {3, 0, 2, 1}, {9, 2, 3, 1}};

        Matrix matrix = new Matrix(data);

        double actualDeterminant = new LUDecomposition(matrix).det();
        double expectedDeterminant = 24;

        assertEquals(expectedDeterminant, actualDeterminant, 1e-2);
    }

    @Test
    void computeDeterminantSetC() throws InvalidMatrixSizesException, NonSquareMatrixException {
        double[][] data = {{1, 2, 3}, {0, -4, 1}, {0, 3, -1}};

        Matrix matrix = new Matrix(data);

        double actualDeterminant = new LUDecomposition(matrix).det();
        double expectedDeterminant = 1;

        assertEquals(expectedDeterminant, actualDeterminant, 1e-2);
    }

    @Test
    void computeDeterminantSetD() throws InvalidMatrixSizesException, NonSquareMatrixException {
        double[][] data = {{5, -2, 1}, {0, 3, -1}, {2, 0, 7}};

        Matrix matrix = new Matrix(data);

        double actualDeterminant = new LUDecomposition(matrix).det();
        double expectedDeterminant = 103;

        assertEquals(expectedDeterminant, actualDeterminant, 1e-2);
    }

    @Test
    void computeDeterminantSetE() throws InvalidMatrixSizesException, NonSquareMatrixException {
        double[][] data = {{1, -4}, {0, 3}};

        Matrix matrix = new Matrix(data);

        double actualDeterminant = new LUDecomposition(matrix).det();
        double expectedDeterminant = 3;

        assertEquals(expectedDeterminant, actualDeterminant, 1e-2);
    }

    @Test
    void computeDeterminantSetF() throws InvalidMatrixSizesException, NonSquareMatrixException {
        double[][] data = {{2, 1}, {-1, 3}};

        Matrix matrix = new Matrix(data);

        double actualDeterminant = new LUDecomposition(matrix).det();
        double expectedDeterminant = 7;

        assertEquals(expectedDeterminant, actualDeterminant, 1e-2);
    }

    @Test
    void computeDeterminantSetG() throws InvalidMatrixSizesException, NonSquareMatrixException {
        double[][] data = {{1, 5, 6, 7, 5}, {0, 6, 8, 2, 1}, {0, 5, 7, 5, 6}, {8, 9, 2, 1, 5}, {6, 5, 8, 5, 2}};

        Matrix matrix = new Matrix(data);

        double actualDeterminant = new LUDecomposition(matrix).det();
        double expectedDeterminant = 6544;

        assertEquals(expectedDeterminant, actualDeterminant, 1e-2);
    }

    @Test
    void computeDeterminantSetH() throws InvalidMatrixSizesException, NonSquareMatrixException {
        double[][] data = {{-1, 1, 1, 2, 3}, {5, 6, 4, 8, 5}, {2, 1, 5, 9, 8}, {7, 5, 2, 3, 4}, {2, 56, 8, 2, 0}};

        Matrix matrix = new Matrix(data);

        double actualDeterminant = new LUDecomposition(matrix).det();
        double expectedDeterminant = -6962;

        assertEquals(expectedDeterminant, actualDeterminant, 1e-2);
    }
}