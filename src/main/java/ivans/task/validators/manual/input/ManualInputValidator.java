package ivans.task.validators.manual.input;

import ivans.task.exceptions.InvalidInputException;

public final class ManualInputValidator {

    private ManualInputValidator() {}

    public static String requireNonBlank(String raw, String fieldName) {
        if (raw == null || raw.trim().isEmpty()) {
            throw new InvalidInputException(fieldName + " не может быть пустым");
        }
        return raw;
    }

    public static int readInt(String raw, String fieldName) {
        String value = requireNonBlank(raw, fieldName);
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new InvalidInputException(fieldName + " должно быть целым числом, введено: " + raw);
        }
    }

    public static double readDouble(String raw, String fieldName) {
        String value = requireNonBlank(raw, fieldName);
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new InvalidInputException(fieldName + " должно быть числом, введено: " + raw);
        }
    }

    public static int readPositiveInt(String raw, String fieldName) {
        int value = readInt(raw, fieldName);
        if (value <= 0) {
            throw new InvalidInputException(fieldName + " должно быть положительным числом, введено: " + raw);
        }
        return value;
    }
}
