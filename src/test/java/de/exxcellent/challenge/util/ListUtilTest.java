package de.exxcellent.challenge.util;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ListUtilTest {

    @Test
    public void testArgmin() {
        List<Pair<Integer, String>> list = new ArrayList<>(List.of(
                new Pair<>(1, "One"),
                new Pair<>(2, "Two"),
                new Pair<>(3, "Twelve"),
                new Pair<>(12, "Three")
        ));
        assertEquals(Optional.of(list.get(0)), ListUtil.argmin(list, Pair::getKey));

        Collections.shuffle(new ArrayList<>(list));
        assertEquals(Optional.of(list.get(0)), ListUtil.argmin(list, Pair::getKey));
    }

    @Test
    public void testFindLast() {
        List<Pair<Integer, String>> list = new ArrayList<>(List.of(
                new Pair<>(1, "One"),
                new Pair<>(2, "Two"),
                new Pair<>(3, "Twelve"),
                new Pair<>(12, "Three")
        ));
        assertEquals(Optional.of(list.get(3)), ListUtil.findLast(list));
        assertEquals(Optional.empty(), ListUtil.findLast(Collections.emptyList()));
    }
}
