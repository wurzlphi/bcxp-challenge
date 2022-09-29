package de.exxcellent.challenge.util;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;


/**
 * Utility class for data analysis methods.
 */
public class Analysis {

    /**
     * Find the entry in the given list associated with the minimum. Values for each item are
     * computed using the given function and then compared.
     *
     * @param data The data to base calculation off of
     * @param keyComp A function that takes a data element and produces a key by which the
     *                data can be ordered.
     * @return The element associated with the minimum - or {@code null}
     * @param <T> The type of data
     * @param <N> The key type
     */
    public static <T, N extends Comparable<? super N>> Optional<T> argmin(List<? extends T> data,
                                                                          Function<? super T, N> keyComp) {
        return data.stream().map(d -> new Pair<>(d, keyComp.apply(d))).min(
                Comparator.comparing(Pair::getValue)).map(Pair::getKey);
    }

}