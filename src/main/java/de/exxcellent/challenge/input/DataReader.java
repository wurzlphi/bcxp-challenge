package de.exxcellent.challenge.input;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;


/**
 * Reads data from a serialized format (e.g. csv) and processes it to an in-memory model.
 * @param <T> The type of data to read and of which objects representing the data read will be
 *           instantiated.
 */
public interface DataReader<T> {

    /**
     * Reads data according to the configuration of this {@link DataReader}, e.g. from a file,
     * instantiates objects, and returns them.
     *
     * @return A list of objects representing the data in the given file
     * @throws IOException If the underlying source is a file which doesn't exist or is
     * inaccessible
     */
    List<T> readData() throws IOException;

}
