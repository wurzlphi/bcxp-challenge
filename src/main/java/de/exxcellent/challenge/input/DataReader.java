package de.exxcellent.challenge.input;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;


/**
 * Reads data from a serialized format (e.g. csv) and processes it to an in-memory model.
 * @param <T> The type of data to read and of which objects representing the data read will be
 *           instantiated.
 */
public interface DataReader<T> {

    /**
     * Reads data from the given {@link File} and tries to instantiate objects of the given
     * {@link Class} with them.
     *
     * @param fileToRead The path/ file to read data from
     * @param objectType The type of objects to be populated with the data
     * @return A list of objects representing the data in the given file
     * @throws FileNotFoundException If the file doesn't exist, is inaccessible, or generally unreadable.
     */
    List<T> readData(File fileToRead, Class<? extends T> objectType) throws FileNotFoundException;

}
