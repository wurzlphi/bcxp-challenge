package de.exxcellent.challenge.input.csv;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


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

    static class DataObject {

        @CsvColumn(columnName = "the first one")
        float firstColumn;
        @CsvColumn(columnName = "the/second$one")
        String secondColumn;

    }

}
