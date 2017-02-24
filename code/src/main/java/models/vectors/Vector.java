package models.vectors;

/**
 * A Vector class for data storage of a 1D array of doubles.
 * Implementation uses array of doubles to store data.
 * Created by Mateusz Gasior on 24-Feb-17.
 */
public class Vector {
    /**
     * Internal array of stored data.
     */
    private final double[] data;

    /**
     * Size of stored array.
     */
    private final int size;

    /**
     * Parametrized constructor.
     * Sets the size of the stored data and allocated the array for given size.
     *
     * @param size Size of data to be stored inside Vector.
     */
    public Vector(int size) {
        this.size = size;
        this.data = new double[size];
        for (int i = 0; i < size; i++) {
            this.data[i] = 0;
        }
    }

    /**
     * Copy constructor.
     * Copies all elements from given Vector.
     *
     * @param toCopy Vector with elements to be copied.
     */
    public Vector(Vector toCopy) {
        this.size = toCopy.getSize();
        this.data = new double[size];
        for (int i = 0; i < size; i++) {
            this.data[i] = toCopy.data[i];
        }
    }

    /**
     * Returns the size of the Vector.
     *
     * @return Size of the Vector.
     */
    public int getSize() {
        return size;
    }

    /**
     * Returns value of data getAt given index.
     *
     * @param index Index in the data array.
     * @return Value of data getAt given index.
     */
    public double getAt(int index) {
        if (index < size) {
            return data[index];
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    public void setAt(int index, double value) {
        if (index < size)
            data[index] = value;
        else {
            throw new IndexOutOfBoundsException();
        }
    }

    /**
     * Compares two Vector to the object.
     * If object is of type Vector then check is made if all the values getAt given index are equal.
     * Otherwise false is returned.
     * Comparison does not takes any delta for deviation.
     *
     * @param obj Object to be compared.
     * @return True if obj is of type Vector and all of the data getAt given index are equal. Otherwise false.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Vector) {
            Vector otherVector = (Vector) obj;
            if (otherVector.getSize() == this.getSize()) {
                for (int i = 0; i < this.getSize(); i++) {
                    if (this.getAt(i) != otherVector.getAt(i)) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }
}
