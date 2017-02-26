package models.utilities;

import models.exceptions.InvalidMatrixSizesException;
import models.exceptions.NonSquareMatrixException;
import models.exceptions.SingularMatrixException;
import models.matrices.Matrix;
import models.vectors.Vector;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Mateusz Gasior on 24-Feb-17.
 */
class FactorizationTests {
    @Test
    void pivotFactorization() throws NonSquareMatrixException {
        final int n = 4;
        double[][] data = {{1, 2, 1, 3}, {2, 3, 1, 4}, {1, 4, 3, 2}, {2, 4, 1, 5}};
        Matrix input = new Matrix(data);

        LUDecomposition factorize = new LUDecomposition(input);

        double[][] correctL = {{1, 0, 0, 0}, {0.5, 1, 0, 0}, {1, 0.4, 1, 0}, {0.5, 0.2, 0, 1}};
        double[][] correctU = {{2, 3, 1, 4}, {0, 2.5, 2.5, 0}, {0, 0, -1, 1}, {0, 0, 0, 1}};

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                assertEquals(correctL[i][j], factorize.getL().getAt(i, j), 1e-2);

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                assertEquals(correctU[i][j], factorize.getU().getAt(i, j), 1e-2);
    }

    @Test
    void factorizationSetA() throws NonSquareMatrixException {
        final int n = 3;

        double[][] data = {{5, -2, 3}, {-3, 9, 1}, {2, -1, -7}};
        Matrix input = new Matrix(data);

        LUDecomposition factorize = new LUDecomposition(input);

        double[][] correctL = {{1, 0, 0}, {-0.6, 1, 0}, {0.4, -0.025641, 1}};
        double[][] correctU = {{5, -2, 3}, {0, 7.8, 2.8}, {0, 0, -8.12821}};

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                assertEquals(correctL[i][j], factorize.getL().getAt(i, j), 1e-2);

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                assertEquals(correctU[i][j], factorize.getU().getAt(i, j), 1e-2);
    }

    @Test
    void factorizationSetB() throws NonSquareMatrixException {
        final int n = 3;

        double[][] data = {{2, -1, 3}, {4, 2, 1}, {-6, -1, 2}};
        Matrix input = new Matrix(data);

        LUDecomposition factorize = new LUDecomposition(input);

        double[][] correctL = {{1, 0, 0}, {-0.6667, 1, 0}, {-0.3333, -1, 1}};
        double[][] correctU = {{-6, -1, 2}, {0, 1.33, 2.33}, {0, 0, 6}};

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                assertEquals(correctL[i][j], factorize.getL().getAt(i, j), 1e-2);

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                assertEquals(correctU[i][j], factorize.getU().getAt(i, j), 1e-2);
    }

    @Test
    void solveSetA() throws NonSquareMatrixException, SingularMatrixException, InvalidMatrixSizesException {
        double[][] matrix = {{1, 4, 2, 3}, {1, 2, 1, 0}, {2, 6, 3, 1}, {0, 0, 1, 4}};
        Matrix input = new Matrix(matrix);

        double[] vectorData = {1, 2, 3, 4};
        Vector vector = new Vector(vectorData);

        LUDecomposition factorize = new LUDecomposition(input);
        Vector solution = factorize.solve(vector);

        double[] correctSolution = {3, -2.5, 4, 0};

        for (int i = 0; i < correctSolution.length; i++) {
            assertEquals(correctSolution[i], solution.getData()[i], 0.01);
        }
    }

    @Test
    void solveSetB() throws NonSquareMatrixException, SingularMatrixException, InvalidMatrixSizesException {
        double[][] data = {{5, -2, 3}, {-3, 9, 1}, {2, -1, -7}};
        Matrix input = new Matrix(data);

        double[] vectorData = {-1, 2, 3};
        Vector vector = new Vector(vectorData);

        LUDecomposition factorize = new LUDecomposition(input);
        Vector solution = factorize.solve(vector);

        double[] correctSolution = {0.186, 0.331, -0.423};

        for (int i = 0; i < correctSolution.length; i++) {
            assertEquals(correctSolution[i], solution.getData()[i], 0.01);
        }
    }

    @Test
    void solveSetC() throws NonSquareMatrixException, SingularMatrixException, InvalidMatrixSizesException {
        double[][] data = {{2, -1, 3}, {4, 2, 1}, {-6, -1, 2}};
        Matrix input = new Matrix(data);

        double[] vectorData = {1, 2, 3};
        Vector vector = new Vector(vectorData);

        LUDecomposition factorize = new LUDecomposition(input);
        Vector solution = factorize.solve(vector);

        double[] correctSolution = {-0.375, 1.25, 1};

        for (int i = 0; i < correctSolution.length; i++) {
            assertEquals(correctSolution[i], solution.getData()[i], 0.01);
        }
    }
}