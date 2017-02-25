package models.utilities;

import models.exceptions.InvalidMatrixSizeForMultiplication;
import models.exceptions.MatrixVectorMultiplicationSizeException;
import models.exceptions.NonSquareMatrixException;
import models.exceptions.ZeroPivotException;
import models.matrices.Matrix;
import models.utilities.objects.LUMatrixTuple;
import models.vectors.Vector;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Mateusz Gasior on 24-Feb-17.
 */
class LUMatricesUtilitiesTest {
    @Test
    void pivotFactorization() throws NonSquareMatrixException, ZeroPivotException, InvalidMatrixSizeForMultiplication {
        final int n = 4;
        double[][] data = {{1, 4, 2, 3}, {1, 2, 1, 0}, {2, 6, 3, 1}, {0, 0, 1, 4}};
        Matrix input = new Matrix(data);

        Matrix reorder = LUMatricesUtilities.reorder(input).getMatrix();
        LUMatrixTuple factorize = LUMatricesUtilities.factorize(reorder.multiply(input));

        double[][] correctL = {{1, 0, 0, 0}, {1, 1, 0, 0}, {0, 0, 1, 0}, {2, 1, 0, 1}};
        double[][] correctU = {{1, 2, 1, 0}, {0, 2, 1, 3}, {0, 0, 1, 4}, {0, 0, 0, -2}};

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                assertEquals(correctL[i][j], factorize.getL().getAt(i, j));

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                assertEquals(correctU[i][j], factorize.getU().getAt(i, j));
    }

    @Test
    void factorizationSetA() throws NonSquareMatrixException, ZeroPivotException, InvalidMatrixSizeForMultiplication {
        final int n = 3;

        double[][] data = {{5, -2, 3}, {-3, 9, 1}, {2, -1, -7}};
        Matrix input = new Matrix(data);

        LUMatrixTuple factorize = LUMatricesUtilities.factorize(input);

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
    void factorizationSetB() throws NonSquareMatrixException, ZeroPivotException, InvalidMatrixSizeForMultiplication {
        final int n = 3;

        double[][] data = {{2, -1, 3}, {4, 2, 1}, {-6, -1, 2}};
        Matrix input = new Matrix(data);

        LUMatrixTuple factorize = LUMatricesUtilities.factorize(input);

        double[][] correctL = {{1, 0, 0}, {2, 1, 0}, {-3, -1, 1}};
        double[][] correctU = {{2, -1, 3}, {0, 4, -5}, {0, 0, 6}};

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                assertEquals(correctL[i][j], factorize.getL().getAt(i, j), 1e-2);

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                assertEquals(correctU[i][j], factorize.getU().getAt(i, j), 1e-2);
    }

    @Test
    void solveSetA() throws NonSquareMatrixException, ZeroPivotException, InvalidMatrixSizeForMultiplication, MatrixVectorMultiplicationSizeException {
        double[][] matrix = {{1, 4, 2, 3}, {1, 2, 1, 0}, {2, 6, 3, 1}, {0, 0, 1, 4}};
        Matrix input = new Matrix(matrix);

        double[] vectorData = {1, 2, 3, 4};
        Vector vector = new Vector(vectorData);

        Matrix reorder = LUMatricesUtilities.reorder(input).getMatrix();
        LUMatrixTuple factorize = LUMatricesUtilities.factorize(reorder.multiply(input));
        Vector solution = LUMatricesUtilities.solve(factorize.getL(), factorize.getU(), reorder.multiply(vector));

        double[] correctSolution = {3, -2.5, 4, 0};

        for (int i = 0; i < correctSolution.length; i++) {
            assertEquals(correctSolution[i], solution.getData()[i], 0.01);
        }
    }

    @Test
    void solveSetB() throws NonSquareMatrixException, ZeroPivotException, InvalidMatrixSizeForMultiplication, MatrixVectorMultiplicationSizeException {
        double[][] data = {{5, -2, 3}, {-3, 9, 1}, {2, -1, -7}};
        Matrix input = new Matrix(data);

        double[] vectorData = {-1, 2, 3};
        Vector vector = new Vector(vectorData);

        Matrix reorder = LUMatricesUtilities.reorder(input).getMatrix();
        LUMatrixTuple factorize = LUMatricesUtilities.factorize(reorder.multiply(input));
        Vector solution = LUMatricesUtilities.solve(factorize.getL(), factorize.getU(), reorder.multiply(vector));

        double[] correctSolution = {0.186, 0.331, -0.423};

        for (int i = 0; i < correctSolution.length; i++) {
            assertEquals(correctSolution[i], solution.getData()[i], 0.01);
        }
    }

    @Test
    void solveSetC() throws NonSquareMatrixException, ZeroPivotException, InvalidMatrixSizeForMultiplication, MatrixVectorMultiplicationSizeException {
        double[][] data = {{2, -1, 3}, {4, 2, 1}, {-6, -1, 2}};
        Matrix input = new Matrix(data);

        double[] vectorData = {1, 2, 3};
        Vector vector = new Vector(vectorData);

        Matrix reorder = LUMatricesUtilities.reorder(input).getMatrix();
        LUMatrixTuple factorize = LUMatricesUtilities.factorize(reorder.multiply(input));
        Vector solution = LUMatricesUtilities.solve(factorize.getL(), factorize.getU(), reorder.multiply(vector));

        double[] correctSolution = {-0.375, 1.25, 1};

        for (int i = 0; i < correctSolution.length; i++) {
            assertEquals(correctSolution[i], solution.getData()[i], 0.01);
        }
    }

    @Test
    void solveSetD() throws NonSquareMatrixException, ZeroPivotException, InvalidMatrixSizeForMultiplication, MatrixVectorMultiplicationSizeException {
        double[][] data = {{1, 2, 3}, {4, 5, 6}, {7, 8, 2}};
        Matrix input = new Matrix(data);

        double[] vectorData = {1, 1, 1};
        Vector vector = new Vector(vectorData);

        Matrix reorder = LUMatricesUtilities.reorder(input).getMatrix();
        LUMatrixTuple factorize = LUMatricesUtilities.factorize(reorder.multiply(input));
        Vector solution = LUMatricesUtilities.solve(factorize.getL(), factorize.getU(), reorder.multiply(vector));

        double[] correctSolution = {6, 15, 17};

        for (int i = 0; i < correctSolution.length; i++) {
            assertEquals(correctSolution[i], solution.getData()[i], 0.01);
        }
    }
}