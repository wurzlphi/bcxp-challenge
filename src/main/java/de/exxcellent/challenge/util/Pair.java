package de.exxcellent.challenge.util;

import java.util.Objects;


/**
 * A simple pair to hold two associated objects.
 *
 * @param <K>
 *         The type of the key (first) object
 * @param <V>
 *         The type of the value (second) object
 */
public class Pair<K, V> {

    public K key;
    public V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pair)) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(key, pair.key) && Objects.equals(value, pair.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }

}
