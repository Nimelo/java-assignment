package models.exceptions;

/**
 * Describes exception that  occurs when requirements for LU Pivoting are not met.
 * Created by Mateusz Gasior on 25-Feb-17.
 *
 * @author Mateusz Gasior
 */
public class LUPivotConstraintsException extends Throwable {
    /**
     * Overridden constructor.
     *
     * @param message Message of error.
     */
    public LUPivotConstraintsException(String message) {
        super(message);
    }
}
