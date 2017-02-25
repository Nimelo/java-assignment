package models.utilities;

import models.exceptions.InvalidMatrixSizeForMultiplication;
import models.exceptions.NonSquareMatrixException;
import models.exceptions.ZeroPivotException;
import models.matrices.Matrix;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Mateusz Gasior on 24-Feb-17.
 */
class InverseMatrixUtilitiesTest {
    @Test
    void findInverseLSetA() {
        double[][] correctL = {{1, 0, 0}, {-0.6, 1, 0}, {0.4, -0.025641, 1}};

        Matrix actualInverseL = InverseMatrixUtilities.findInverseOfLowerTriangularMatrix(new Matrix(correctL));

        double[][] expectedInverseL = {{1, 0, 0}, {0.6, 1, 0}, {-0.3846154, 0.025641, 1}};

        for (int i = 0; i < expectedInverseL.length; i++)
            for (int j = 0; j < expectedInverseL[0].length; j++)
                assertEquals(expectedInverseL[i][j], actualInverseL.getData()[i][j], 1e-2);
    }

    @Test
    void findInverseUSetA() {
        double[][] u = {{5, -2, 3}, {0, 7.8, 2.8}, {0, 0, -8.12821}};

        Matrix actualInverseU = InverseMatrixUtilities.findInverseOfUpperTriangularMatrix(new Matrix(u));

        double[][] expectedInverseU =
                {
                        {0.2, 0.051282051282051294, 0.09148259501043203},
                        {0, 0.12820512820512822, 0.044164011384346495},
                        {0, 0, -0.12302831742782237}
                };

        for (int i = 0; i < expectedInverseU.length; i++)
            for (int j = 0; j < expectedInverseU[0].length; j++)
                assertEquals(expectedInverseU[i][j], actualInverseU.getData()[i][j], 1e-2);
    }

    @Test
    void findInverseSetA() throws NonSquareMatrixException, InvalidMatrixSizeForMultiplication, ZeroPivotException {
        final int n = 3;

        double[][] data = {{5, -2, 3}, {-3, 9, 1}, {2, -1, -7}};
        Matrix input = new Matrix(data);

        Matrix output = InverseMatrixUtilities.findInverse(input);

        double[][] correctOutput =
                {
                        {0.19558359621451105, 0.053627760252365944, 0.09148264984227131},
                        {0.059936908517350174, 0.12933753943217668, 0.04416403785488959},
                        {0.04731861198738171, -0.0031545741324921135, -0.12302839116719244}
                };

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                assertEquals(correctOutput[i][j], output.getAt(i, j), 1e-2);
    }

    @Test
    void findInverseLSetB() {
        double[][] correctL = {{1, 0, 0}, {2, 1, 0}, {-3, -1, 1}};

        Matrix actualInverseL = InverseMatrixUtilities.findInverseOfLowerTriangularMatrix(new Matrix(correctL));

        double[][] expectedInverseL = {{1, 0, 0}, {-2, 1, 0}, {1, 1, 1}};

        for (int i = 0; i < expectedInverseL.length; i++)
            for (int j = 0; j < expectedInverseL[0].length; j++)
                assertEquals(expectedInverseL[i][j], actualInverseL.getData()[i][j], 1e-2);
    }

    @Test
    void findInverseUSetB() {
        double[][] u = {{2, -1, 3}, {0, 4, -5}, {0, 0, 6}};

        Matrix actualInverseU = InverseMatrixUtilities.findInverseOfUpperTriangularMatrix(new Matrix(u));

        double[][] expectedInverseU = {{0.5, 1.0 / 8, -7.0 / 48}, {0, 1.0 / 4, 5.0 / 24}, {0, 0, 1.0 / 6}};

        for (int i = 0; i < expectedInverseU.length; i++)
            for (int j = 0; j < expectedInverseU[0].length; j++)
                assertEquals(expectedInverseU[i][j], actualInverseU.getData()[i][j], 1e-2);
    }

    @Test
    void findInversSetB() throws NonSquareMatrixException, InvalidMatrixSizeForMultiplication, ZeroPivotException {
        final int n = 3;

        double[][] data = {{2, -1, 3}, {4, 2, 1}, {-6, -1, 2}};
        Matrix input = new Matrix(data);

        Matrix output = InverseMatrixUtilities.findInverse(input);

        double[][] correctOutput = {{5.0 / 48, -1.0 / 48, -7.0 / 48}, {-7.0 / 24, 11.0 / 24, 5.0 / 24}, {1.0 / 6, 1.0 / 6, 1.0 / 6}};

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                assertEquals(correctOutput[i][j], output.getAt(i, j), 1e-2);
    }
}