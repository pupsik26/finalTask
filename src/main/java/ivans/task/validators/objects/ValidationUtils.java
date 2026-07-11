package ivans.task.validators.objects;

import ivans.task.exceptions.InvalidDataException;

import java.util.regex.Pattern;

final class ValidationUtils {

    static final Pattern TEXT_WITH_DIGITS = Pattern.compile("[a-zA-Zа-яА-ЯёЁ0-9\\s\\-]+");
    static final Pattern TEXT_LETTERS_ONLY = Pattern.compile("[a-zA-Zа-яА-ЯёЁ\\s\\-]+");
    static final Pattern EMAIL = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");

    private ValidationUtils() {
    }

    static void requireNonNull(Object value, String fieldName) {
        if (value == null) {
            throw new InvalidDataException(fieldName + " не может быть null");
        }
    }

    static void requireNonEmptyText(String value, String fieldName, Pattern allowedPattern) {
        if (value == null || value.trim().isEmpty()) {
            throw new InvalidDataException(fieldName + " не может быть пустым");
        }
        if (!allowedPattern.matcher(value).matches()) {
            throw new InvalidDataException(fieldName + " содержит недопустимые символы: " + value);
        }
    }

    static void requireInRange(double value, double min, double max, String fieldName) {
        if (value < min || value > max) {
            throw new InvalidDataException(fieldName + " должен быть в диапазоне " +
                    min + "-" + max + ", получено: " + value);
        }
    }
}
