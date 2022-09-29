package de.exxcellent.challenge.input.csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

import de.exxcellent.challenge.input.AbstractDataReader;
import de.exxcellent.challenge.util.Pair;


/**
 * An implementation of {@link AbstractDataReader} that can handle csv files.
 * Deserialization is realized by annotating the fields of a class with the interface
 * {@link CsvColumn} and assigning names as they appear in the file.
 * Please note, that boxed types are currently not supported.
 *
 * @see de.exxcellent.challenge.input.AbstractDataReader
 * @see de.exxcellent.challenge.input.DataReader
 */
public class CsvReader<T> extends AbstractDataReader<T> {

    public static final List<Pair<Class<?>, Function<String, ?>>> CONVERTERS = List.of(
            new Pair<>(double.class, Double::parseDouble),
            new Pair<>(float.class, Float::parseFloat),
            new Pair<>(long.class, Long::parseLong),
            new Pair<>(int.class, Integer::parseInt),
            new Pair<>(short.class, Short::parseShort),
            new Pair<>(byte.class, Byte::parseByte),
            new Pair<>(char.class, (value) -> {
                if (value.length() > 0)
                    return value.charAt(0);
                throw new IllegalStateException("No character present.");
            }),
            new Pair<>(boolean.class, Boolean::parseBoolean),
            new Pair<>(String.class, (value) -> value)
    );

    private final String quotedSeparator;

    public CsvReader(char separator, Reader source, Class<? extends T> objectType) {
        super(source, objectType);
        this.quotedSeparator = quoteSeparator(separator);
    }

    public List<T> readData() throws IOException {
        Map<String, Field> columnNameToField = buildColumnNameToFieldMap();

        try (BufferedReader br = new BufferedReader(source)) {
            String firstLine = br.readLine();
            if (firstLine == null || firstLine.isBlank())
                throw new IllegalStateException("Source is empty.");

            String[] columnNames = firstLine.split(quotedSeparator);
            if (Arrays.stream(columnNames).anyMatch(String::isEmpty))
                throw new IllegalStateException("Column name may not be empty.");
            if (columnNames.length != columnNameToField.size())
                throw new IllegalStateException(
                        "Number of columns in source doesn't match the " +
                        "number of annotated fields in class '" + objectType.getCanonicalName() +
                        "'.");

            return br.lines().map(line -> {
                T newObject = instantiateNewObject();
                String[] values = line.split(quotedSeparator);
                for (int i = 0; i < Math.min(values.length, columnNames.length); i++) {
                    Field field = Objects.requireNonNull(columnNameToField.get(columnNames[i]),
                                                         "Column '" + columnNames[i] + "' is not " +
                                                         "present as an annotation.");
                    assignValue(newObject, field, values[i]);
                }
                return newObject;
            }).collect(Collectors.toList());
        }
    }

    private Map<String, Field> buildColumnNameToFieldMap() {
        List<Field> fieldsWithAnnotation = Arrays.stream(objectType.getDeclaredFields()).filter(
                field -> field.getAnnotation(CsvColumn.class) != null &&
                         !Modifier.isStatic(field.getModifiers())).collect(
                Collectors.toList());
        Map<String, Field> asMap = fieldsWithAnnotation.stream().collect(
                Collectors.toMap(field -> field.getAnnotation(CsvColumn.class).columnName(),
                                 field -> field));
        if (asMap.isEmpty())
            throw new IllegalStateException("Class '" + objectType.getCanonicalName() + "' does " +
                                            "not have any fields annotated with '" +
                                            CsvColumn.class.getCanonicalName() + "'.");
        return asMap;
    }

    private T instantiateNewObject() {
        try {
            return objectType.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException |
                 InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private void assignValue(T object, Field field, String value) {
        try {
            for (var converter : CONVERTERS) {
                if (field.getType().isAssignableFrom(converter.key)) {
                    field.setAccessible(true);
                    field.set(object, converter.value.apply(value));
                    return;
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        throw new IllegalStateException(
                "Unrecognized field type '" + field.getType().getCanonicalName() + "' for field '" +
                field.getName() + "'");
    }

    private static String quoteSeparator(char separator) {
        return Matcher.quoteReplacement(String.valueOf(separator));
    }

}
