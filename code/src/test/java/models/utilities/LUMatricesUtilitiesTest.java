package models.utilities;

import models.exceptions.InvalidMatrixSizeForMultiplication;
import models.exceptions.NonSquareMatrixException;
import models.exceptions.ZeroPivotException;
import models.matrices.Matrix;
import models.utilities.objects.LUMatrixTuple;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Mateusz Gasior on 24-Feb-17.
 */
class LUMatricesUtilitiesTest {
    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void reorder() {

    }

    @Test
    void factorize() throws NonSquareMatrixException, ZeroPivotException, InvalidMatrixSizeForMultiplication {
        final int n = 4;
        Matrix input = new Matrix(n, n);
        double[][] inputs = {{1, 2, 1, 3}, {2, 3, 1, 4}, {1, 4, 3, 2}, {2, 4, 1, 5}};
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                input.setAt(i, j, inputs[i][j]);

        LUMatrixTuple factorize = LUMatricesUtilities.factorize(input);

        double[][] correctL = {{-1, 2, 0, -1}, {-1, -0.5, 0.2, 1.0}, {1, 0.4, 0.2, -1}, {1, -0.4, -0.2, 0}};
        double[][] correctU = {{2, 3, 1, 4}, {0, 2.5, 2.5, 0}, {0, 0, -1, 1}, {0, 0, 0, 1}};

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                assertEquals(correctL[i][j], factorize.getL().getAt(i, j));

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                assertEquals(correctU[i][j], factorize.getU().getAt(i, j));
    }

    @Test
    void factorize2() throws NonSquareMatrixException, ZeroPivotException, InvalidMatrixSizeForMultiplication {
        final int n = 3;
        Matrix input = new Matrix(n, n);
        double[][] inputs = {{1, 3, 5}, {2, 4, 7}, {1, 1, 0}};
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                input.setAt(i, j, inputs[i][j]);

        LUMatrixTuple factorize = LUMatricesUtilities.factorize(input);

        double[][] correctL = {{1, 0, 0}, {0.5, 1, 0}, {0.5, -1, 1}};
        double[][] correctU = {{2, 4, 7}, {0, 1, 1.5}, {0, 0, -2}};

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                assertEquals(correctL[i][j], factorize.getL().getAt(i, j));

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                assertEquals(correctU[i][j], factorize.getU().getAt(i, j));
    }

    @Test
    void solve() {

    }

}