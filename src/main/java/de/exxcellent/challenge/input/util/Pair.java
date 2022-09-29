package de.exxcellent.challenge.input.util;

/**
 * A simple pair to hold two associated objects.
 * @param <K> The type of the key (first) object
 * @param <V> The type of the value (second) object
 */
public class Pair<K, V> {
    public K key;
    public V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }
}
