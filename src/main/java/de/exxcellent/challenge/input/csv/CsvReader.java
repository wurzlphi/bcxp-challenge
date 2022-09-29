package de.exxcellent.challenge.input.csv;

import java.io.File;
import java.util.List;
import java.util.Objects;

import de.exxcellent.challenge.input.DataReader;


public class CsvReader<T> implements DataReader<T>{

    private final File fileToRead;
    private final Class<? extends T> objectType;

    public CsvReader(File fileToRead, Class<? extends T> objectType) {
        this.fileToRead = Objects.requireNonNull(fileToRead);
        this.objectType = Objects.requireNonNull(objectType);
    }

    public List<T> readData() {
        return null;
    }

}
