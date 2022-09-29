package de.exxcellent.challenge.input;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import de.exxcellent.challenge.input.csv.CsvReader;
import de.exxcellent.challenge.util.ListUtil;


/**
 * Reads data from a serialized format (e.g. csv) and processes it to an in-memory model.
 *
 * @param <T>
 *         The type of data to read and of which objects representing the data read will be
 *         instantiated.
 */
public interface DataReader<T> extends Closeable {

    /**
     * Reads data according to the configuration of this {@link DataReader}, e.g. from a file,
     * instantiates objects, and returns them. After a call to this method, the underlying
     * {@link Reader} will have been closed., i.e. this method is usually single use.
     *
     * @return A list of objects representing the data in the given file
     *
     * @throws IOException
     *         If the underlying source is a file which doesn't exist or is inaccessible
     */
    List<T> readData() throws IOException;

    /**
     * A factory class for data readers for convenience.
     */
    class Factory {

        /**
         * Creates a new {@link DataReader} from a file based on the file extension and default
         * separator ','.
         *
         * @param filePath
         *         The path to the file to read
         * @param objectType
         *         The type of objects to deserialize into
         *
         * @return An instance of {@link DataReader}
         *
         * @throws IOException
         *         If {@link FileReader#FileReader(String)} would throw, or if the extension is not
         *         recognized.
         */
        public static <T> DataReader<T> createDataReader(String filePath,
                                                         Class<? extends T> objectType)
        throws IOException {
            Optional<String> extension = ListUtil.findLast(Arrays.asList(filePath.split("\\.")));
            if (extension.isEmpty())
                throw new IOException("No file extension present. Can't determine file type.");
            switch (extension.get()) {
                case "csv":
                    return new CsvReader<>(',', new FileReader(filePath), objectType);
                default:
                    throw new IOException("Extension '" + extension.get() + "' not recognized.");
            }
        }

    }

}
