package models.internals;

import models.exceptions.InversionConstraintsException;
import models.exceptions.LUPivotConstraintsException;
import models.exceptions.InvalidMatrixSizesException;
import models.exceptions.NonSquareMatrixException;
import models.exceptions.SingularMatrixException;
import models.internals.results.InverseResult;
import models.internals.results.LUPivotResult;
import models.internals.results.Result;
import models.matrices.Matrix;
import models.utilities.LUDecomposition;
import models.vectors.Vector;

import java.io.*;

/**
 * Model of application.
 * Contains matrix and vector.
 * Created by Mateusz Gasior on 26-Feb-17.
 *
 * @author Mateusz Gasior
 * @see Matrix
 * @see Vector
 * @see LUDecomposition
 */
public class ApplicationModel {
    /**
     * Matrix A.
     */
    private Matrix matrix;

    /**
     * Vector b.
     */
    private Vector vector;

    /**
     * LU Decomposition for matrix A.
     */
    private LUDecomposition luDecomposition;

    /**
     * Result of last performed operation.
     */
    private Result lastResult;

    /**
     * Returns matrix.
     *
     * @return Matrix.
     */
    public Matrix getMatrix() {
        return matrix;
    }

    /**
     * Sets matrix.
     *
     * @param matrix New matrix.
     */
    public void setMatrix(Matrix matrix) {
        if (getMatrix() == null || !this.getMatrix().equals(matrix)) {
            this.matrix = matrix;
            this.luDecomposition = null;
        }
    }

    /**
     * Returns vector.
     *
     * @return Vector.
     */
    public Vector getVector() {
        return vector;
    }

    /**
     * Sets vector.
     *
     * @param vector New vector.
     */
    public void setVector(Vector vector) {
        this.vector = vector;
    }

    /**
     * Checks if inverse routine can be performed.
     *
     * @throws InversionConstraintsException when routine cannot be performed.
     *                                       Matrix is null.
     *                                       Matrix is not a square matrix.
     */
    private void checkInverseConstraints() throws InversionConstraintsException {
        if (getMatrix() == null || !getMatrix().isSquare()) {
            throw new InversionConstraintsException();
        }
    }

    /**
     * Checks if LU Pivoting routine can be performed.
     *
     * @throws LUPivotConstraintsException when routine cannot be performed.
     *                                     Matrix or Vector is null.
     *                                     Size of the vector is not equal to number of columns of matrix.
     */
    private void checkLUPivotConstraints() throws LUPivotConstraintsException {
        if (getMatrix() == null || getVector() == null) {
            throw new LUPivotConstraintsException("Matrix or vector is null.");
        } else {
            if (getVector().getSize() != getMatrix().getColumns()) {
                throw new LUPivotConstraintsException("Number of columns in vector is incorrect.");
            }
        }
    }

    /**
     * Routine that calculates solution of problem Ax = b
     *
     * @return Result of LU Pivot routine.
     * @throws NonSquareMatrixException    when matrix does not have a square shape.
     * @throws InvalidMatrixSizesException when matrices during calculations will have different sizes.
     * @throws LUPivotConstraintsException when requirements for performing LU pivoting will not be meet.
     * @throws SingularMatrixException     when matrix is singular.
     * @throws LUPivotConstraintsException when LU pivoting cannot be performed.
     */
    public LUPivotResult LUPivot() throws NonSquareMatrixException, SingularMatrixException, InvalidMatrixSizesException, LUPivotConstraintsException {
        checkLUPivotConstraints();
        if (luDecomposition == null)
            luDecomposition = new LUDecomposition(getMatrix());
        Vector solution = luDecomposition.solve(getVector());
        double determinant = luDecomposition.det();
        LUPivotResult result = new LUPivotResult(getMatrix(), getVector(), luDecomposition, solution, determinant);
        this.lastResult = result;
        return result;
    }

    /**
     * Inverse routine.
     *
     * @return Result of inverse routine for view.
     * @throws InversionConstraintsException when requirements for inverse routine are not met.
     */
    public InverseResult inverse() throws InversionConstraintsException {
        checkInverseConstraints();
        Matrix inverse = null;
        Double determinant = null;

        try {
            if (luDecomposition == null)
                luDecomposition = new LUDecomposition(getMatrix());
            inverse = luDecomposition.inverse();
            determinant = luDecomposition.det();
        } catch (Throwable e) {

        } finally {
            InverseResult result = new InverseResult(getMatrix(), luDecomposition, inverse, determinant);
            this.lastResult = result;
            return result;
        }
    }

    /**
     * Serializes last result (performed action) to the file.
     *
     * @param path Path for the new file.
     * @throws IOException when error occurs during saving.
     */
    public void serializeLastResult(String path) throws IOException {
        FileOutputStream fileOut = new FileOutputStream(path);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(this.lastResult);
        out.close();
        fileOut.close();
    }

    /**
     * Deserialize result from the file.
     * Sets last result to last object.
     * Sets matrix, vector and LU decomposition objects.
     *
     * @param path Path to the file.
     * @return Deserialized object.
     * @throws IOException            when error occurs during reading.
     * @throws ClassNotFoundException when deserialization error occurs.
     */
    public Result deserializeResult(String path) throws IOException, ClassNotFoundException {
        FileInputStream fileIn = new FileInputStream(path);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        this.lastResult = (Result) in.readObject();
        in.close();
        fileIn.close();

        this.matrix = lastResult.getOriginalMatrix();
        if (lastResult instanceof LUPivotResult) {
            this.vector = ((LUPivotResult) lastResult).getOriginalVector();
        }
        this.luDecomposition = lastResult.getLuDecomposition();

        return this.lastResult;
    }
}
