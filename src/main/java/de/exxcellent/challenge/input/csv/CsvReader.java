package de.exxcellent.challenge.input.csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

import de.exxcellent.challenge.input.FileDataReader;


public class CsvReader<T> extends FileDataReader<T> {

    private final char separator;
    private final String quotedSeparator;

    public CsvReader(char separator, File fileToRead, Class<? extends T> objectType) {
        super(fileToRead, objectType);
        this.separator = separator;
        this.quotedSeparator = quoteSeparator(separator);
    }

    public List<T> readData() throws IOException {
        Map<String, Field> columnNameToField = buildColumnNameToFieldMap();

        try (BufferedReader br = new BufferedReader(new FileReader(fileToRead))) {
            String[] columnNames = br.lines().findFirst().map(line -> line.split(quotedSeparator))
                                     .orElseThrow(
                                             () -> new IllegalStateException("File is empty."));
            if (columnNames.length != columnNameToField.size())
                throw new IllegalStateException(
                        "Number of columns in file " + fileToRead.getAbsolutePath() +
                        " doesn't match the " +
                        "number of annotated fields in class " + objectType.getCanonicalName() +
                        ".");

            return br.lines().skip(1).map(line -> {
                T newObject = instantiateNewObject();
                String[] values = line.split(quotedSeparator);
                for (int i = 0; i < Math.min(values.length, columnNames.length); i++) {
                    assignValue(newObject, columnNameToField.get(columnNames[i]), values[i]);
                }
                return newObject;
            }).collect(Collectors.toList());
        }
    }

    public char getSeparator() {
        return separator;
    }

    private Map<String, Field> buildColumnNameToFieldMap() {
        List<Field> fieldsWithAnnotation = Arrays.stream(objectType.getFields()).filter(
                field -> field.getAnnotation(CsvColumn.class) != null).collect(Collectors.toList());
        Map<String, Field> asMap = fieldsWithAnnotation.stream().collect(
                Collectors.toMap(field -> field.getAnnotation(CsvColumn.class).columnName(),
                                 field -> field));
        if (asMap.isEmpty())
            throw new IllegalStateException("Class " + objectType.getCanonicalName() + " does " +
                                            "not have any fields annotated with " +
                                            CsvColumn.class.getCanonicalName() + ".");
        if (asMap.size() != fieldsWithAnnotation.size())
            throw new IllegalStateException("Class " + objectType.getCanonicalName() + " maps " +
                                            "multiple fields to the same column name. Multiple " +
                                            "columns of the same name are not supported.");
        return asMap;
    }

    private T instantiateNewObject() {
        try {
            return objectType.getConstructor().newInstance();
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException |
                 InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private void assignValue(T object, Field field, String value) {

    }

    private static String quoteSeparator(char separator) {
        return Matcher.quoteReplacement(String.valueOf(separator));
    }

}
