package ivans.task.validators.file.input;

import ivans.task.exceptions.InvalidFileFormatException;

public final class FileLineValidator {

    private static final String CSV_SEPARATOR = ",";

    private FileLineValidator() {}

    public static String[] splitFields(String line, int expectedCount) {
        if (line == null || line.trim().isEmpty()) {
            throw new InvalidFileFormatException("Строка файла не может быть пустой");
        }
        String[] fields = line.split(CSV_SEPARATOR, -1);
        if (fields.length != expectedCount) {
            throw new InvalidFileFormatException("Ожидалось " + expectedCount + " полей, получено " + fields.length + " в строке: " + line);
        }
        for (int i = 0; i < fields.length; i++) {
            fields[i] = fields[i].trim();
        }
        return fields;
    }

    public static int parseIntField(String[] fields, int index, String fieldName) {
        try {
            return Integer.parseInt(fields[index]);
        } catch (NumberFormatException e) {
            throw new InvalidFileFormatException(fieldName + " должно быть целым числом, получено: " + fields[index]);
        }
    }

    public static double parseDoubleField(String[] fields, int index, String fieldName) {
        try {
            return Double.parseDouble(fields[index]);
        } catch (NumberFormatException e) {
            throw new InvalidFileFormatException(fieldName + " должно быть числом, получено: " + fields[index]);
        }
    }

    public static String parseStringField(String[] fields, int index, String fieldName) {
        String value = fields[index];
        if (value.isEmpty()) {
            throw new InvalidFileFormatException(fieldName + " не может быть пустым");
        }
        return value;
    }
}
