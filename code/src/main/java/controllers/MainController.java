package controllers;

import controllers.exceptions.*;
import controllers.transformers.TransformationUtils;
import models.internals.results.InverseResult;
import models.internals.results.LUPivotResult;
import models.internals.ApplicationModel;
import models.exceptions.*;
import models.internals.results.Result;
import models.matrices.Matrix;
import models.vectors.Vector;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Controller of Application.
 * Created by Mateusz Gasior on 25-Feb-17.
 */
public class MainController {

    /**
     * Model of application (matrix and vector).
     */
    private final ApplicationModel model;

    /**
     * Default parametrized constructor.
     *
     * @param model Model of application.
     */
    public MainController(ApplicationModel model) {
        this.model = model;
    }

    /**
     * Routine that calculates solution of problem Ax = b
     *
     * @return Result of LU Pivot routine.
     * @throws NonSquareMatrixException    when matrix does not have a square shape.
     * @throws InvalidMatrixSizesException when matrices during calculations will have different sizes.
     * @throws LUPivotConstraintsException when requirements for performing LU pivoting will not be meet.
     * @throws SingularMatrixException     when matrix is singular.
     */
    public LUPivotResult LUPivot() throws NonSquareMatrixException, InvalidMatrixSizesException, LUPivotConstraintsException, SingularMatrixException {
        return model.LUPivot();
    }

    /**
     * Inverse routine.
     *
     * @return Result of inverse routine for view.
     * @throws InversionConstraintsException when requirements for inverse routine does not meet desired requirements.
     */
    public InverseResult inverse() throws InversionConstraintsException {
        return this.model.inverse();
    }

    /**
     * Extracts and sets matrix from a String.
     * Column separator is space.
     * Row separator is a new line character.
     *
     * @param matrix Matrix in a string format.
     * @throws NotEqualAmountOfColumnsInMatrixException when number of columns is different for rows.
     * @throws MatrixExtractionException                when elements are not real numbers.
     */
    public void extractAndSetMatrix(String matrix) throws NotEqualAmountOfColumnsInMatrixException, MatrixExtractionException {
        this.model.setMatrix(TransformationUtils.transformToMatrix(matrix));
    }

    /**
     * Extracts and sets vector from a String.
     * Value separator is single space.
     *
     * @param vector Vector in a string format.
     * @throws VectorExtractionException when elements of vector are not real numbers.
     */
    public void extractAndSetVector(String vector) throws VectorExtractionException {
        this.model.setVector(TransformationUtils.transformToVector(vector));
    }

    public void serialize(String path) throws IOException {
        this.model.serializeLastResult(path);
    }

    public Result deserialize(String path) throws IOException, ClassNotFoundException {
        return this.model.deserializeResult(path);
    }
}
