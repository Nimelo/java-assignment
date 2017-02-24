package models.utilities;

import models.exceptions.InvalidMatrixSizeForMultiplication;
import models.exceptions.NonSquareMatrixException;
import models.exceptions.ZeroPivotException;
import models.matrices.Matrix;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Mateusz Gasior on 24-Feb-17.
 */
class InverseMatrixUtilitiesTest {

    @org.junit.jupiter.api.BeforeEach
    void setUp() {

    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {

    }

    @org.junit.jupiter.api.Test
    void findInverse() throws NonSquareMatrixException, InvalidMatrixSizeForMultiplication, ZeroPivotException {
        final int n = 4;
        Matrix input = new Matrix(n, n);
        double[][] inputs = {{1, 2, 1, 3}, {2, 3, 1, 4}, {1, 4, 3, 2}, {2, 4, 1, 5}};
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                input.setAt(i, j, inputs[i][j]);

        Matrix output = InverseMatrixUtilities.findInverse(input);

        double[][] correctOutput = {{-1, 2, 0, -1},{-1, -0.5, 0.2, 1.0},{1, 0.4, 0.2, -1},{1, -0.4, -0.2, 0}};

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                assertEquals(correctOutput[i][j], output.getAt(i, j));
    }

}