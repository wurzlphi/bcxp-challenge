package de.exxcellent.challenge.input.csv;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class CsvReaderTest {

    public static URI WEATHER;

    static {
        try {
            WEATHER =
                    CsvReader.class.getClassLoader().getResource("de/exxcellent/challenge/weather" +
                                                                 ".csv").toURI();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testFileExists() {
        assertTrue(new File(WEATHER).exists());
    }

    @Test
    public void testDifferentSeparators() throws IOException {
        assertDoesNotThrow(() -> {
            new CsvReader<>(',', new FileReader(new File(WEATHER)),
                            Object.class);
        });
        assertDoesNotThrow(() -> {
            new CsvReader<>(';', new FileReader(new File(WEATHER)),
                            Object.class);
        });
        assertDoesNotThrow(() -> {
            new CsvReader<>('$', new FileReader(new File(WEATHER)),
                            Object.class);
        });
    }

    @Test
    public void testReadData() throws IOException {
        String input = "the first one,the/second$one\n42.3,some text";
        var csvReader = new CsvReader<>(',', new StringReader(input), DataObject.class);
        List<DataObject> result = csvReader.readData();

        assertEquals(42.3f, result.get(0).firstColumn);
        assertEquals("some text", result.get(0).secondColumn);
    }

    @Test
    public void testEmptySource() {
        String input = "";
        var csvReader = new CsvReader<>(',', new StringReader(input), DataObject.class);
        assertThrows(IllegalStateException.class, csvReader::readData);
    }

    @Test
    public void testEmptyName() {
        String input = "first,\n42.3,some text";
        var csvReader = new CsvReader<>(',', new StringReader(input), DataObject.class);
        assertThrows(IllegalStateException.class, csvReader::readData);
    }

    @Test
    public void testMismatchingNumberOfColumns() {
        String input = "first,second,third\n42.3,some text";
        var csvReader = new CsvReader<>(',', new StringReader(input), DataObject.class);
        assertThrows(IllegalStateException.class, csvReader::readData);
    }

    @Test
    public void testInconsistentNames() {
        String input = "first,second\n42.3,some text";
        var csvReader = new CsvReader<>(',', new StringReader(input), DataObject.class);
        assertThrows(NullPointerException.class, csvReader::readData);
    }

    @Test
    public void testHasAnyAnnotations() {
        String input = "first,second\n42.3,some text";
        var csvReader = new CsvReader<>(',', new StringReader(input), NoAnnotations.class);
        assertThrows(IllegalStateException.class, csvReader::readData);
    }

    @Test
    public void testUniqueNames() {
        String input = "first,second\n42.3,some text";
        var csvReader = new CsvReader<>(',', new StringReader(input), NonUniqueNames.class);
        assertThrows(IllegalStateException.class, csvReader::readData);
    }

    @Test
    public void testUnsupportedType() {
        String input = "field\n42.3";
        var csvReader = new CsvReader<>(',', new StringReader(input), UnsupportedFieldType.class);
        assertThrows(IllegalStateException.class, csvReader::readData);
    }

    static class DataObject {

        @CsvColumn(columnName = "the first one")
        float firstColumn;
        @CsvColumn(columnName = "the/second$one")
        String secondColumn;

    }

    static class NoAnnotations {
        float first;
        String second;
    }

    static class NonUniqueNames {
        @CsvColumn(columnName = "field")
        float field;
        @CsvColumn(columnName = "field")
        double another;
    }

    static class UnsupportedFieldType {
        @CsvColumn(columnName = "field")
        IntStream field;
    }

}
