package models.utilities;

import models.exceptions.InvalidMatrixSizesException;
import models.exceptions.NonSquareMatrixException;
import models.exceptions.SingularMatrixException;
import models.matrices.Matrix;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Mateusz Gasior on 24-Feb-17.
 */
class InverseMatrixTests {
    @Test
    void findInverseSetA() throws NonSquareMatrixException, SingularMatrixException, InvalidMatrixSizesException {
        final int n = 3;

        double[][] data = {{5, -2, 3}, {-3, 9, 1}, {2, -1, -7}};
        Matrix input = new Matrix(data);

        Matrix output = new LUDecomposition(input).inverse();

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
    void findInverseSetB() throws NonSquareMatrixException, SingularMatrixException, InvalidMatrixSizesException {
        final int n = 3;

        double[][] data = {{2, -1, 3}, {4, 2, 1}, {-6, -1, 2}};
        Matrix input = new Matrix(data);

        Matrix output = new LUDecomposition(input).inverse();

        double[][] correctOutput = {{5.0 / 48, -1.0 / 48, -7.0 / 48}, {-7.0 / 24, 11.0 / 24, 5.0 / 24}, {1.0 / 6, 1.0 / 6, 1.0 / 6}};

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                assertEquals(correctOutput[i][j], output.getAt(i, j), 1e-2);
    }
}