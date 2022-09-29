package de.exxcellent.challenge.input.csv;

import java.io.File;
import java.util.List;

import de.exxcellent.challenge.input.FileDataReader;


public class CsvReader<T> extends FileDataReader<T> {

    public CsvReader(File fileToRead, Class<? extends T> objectType) {
        super(fileToRead, objectType);
    }

    public List<T> readData() {
        return null;
    }

}
