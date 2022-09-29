package de.exxcellent.challenge.input.csv;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;


/**
 * Annotation for use with {@link CsvReader}. Used to map fields of classes to their
 * corresponding CSV columns.
 */
@Target(ElementType.FIELD)
public @interface CsvColumn {

    /**
     * The actual name of the column associated with this field.
     * @return The column name
     */
    String columnName();

}
