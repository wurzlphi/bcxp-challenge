package de.exxcellent.challenge.input;

import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Objects;


/**
 * Common super class for all data readers. Contains the {@link #source} to deserialize objects from
 * and an {@link #objectType} of which instances will be created when {@link #readData()}} is
 * invoked.
 *
 * @see de.exxcellent.challenge.input.DataReader
 */
public abstract class AbstractDataReader<T> implements DataReader<T>, Closeable {

    protected final Reader source;
    protected final Class<? extends T> objectType;

    public AbstractDataReader(Reader source, Class<? extends T> objectType) {
        this.source = Objects.requireNonNull(source);
        this.objectType = Objects.requireNonNull(objectType);
    }

    /**
     * {@inheritDoc}
     * After a call to this method, the underlying {@link Reader} will have been closed., i.e.
     * this method is single use.
     */
    public abstract List<T> readData() throws IOException;

    @Override
    public void close() throws IOException {
        source.close();
    }

}
