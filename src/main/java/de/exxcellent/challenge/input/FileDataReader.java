package de.exxcellent.challenge.input;

import java.io.File;
import java.util.Objects;


public abstract class FileDataReader<T> implements DataReader<T> {

    protected final File fileToRead;
    protected final Class<? extends T> objectType;

    public FileDataReader(File fileToRead, Class<? extends T> objectType) {
        this.fileToRead = Objects.requireNonNull(fileToRead);
        this.objectType = Objects.requireNonNull(objectType);
    }

    public File getFileToRead() {
        return fileToRead;
    }

    public Class<? extends T> getObjectType() {
        return objectType;
    }

}
