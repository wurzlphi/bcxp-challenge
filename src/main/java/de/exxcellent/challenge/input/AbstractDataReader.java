package de.exxcellent.challenge.input;

import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;
import java.util.Objects;


public abstract class AbstractDataReader<T> implements DataReader<T>, Closeable {

    protected final Reader source;
    protected final Class<? extends T> objectType;

    public AbstractDataReader(Reader source, Class<? extends T> objectType) {
        this.source = Objects.requireNonNull(source);
        this.objectType = Objects.requireNonNull(objectType);
    }

    @Override
    public void close() throws IOException {
        source.close();
    }

}
