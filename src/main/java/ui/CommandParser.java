package ui;


import java.util.HashMap;
import java.util.Map;
import java.util.OptionalInt;

/**
 * Парсер команд пользователя.
 * Поддерживает как короткие (-c), так и длинные (--class) флаги.
 *
 * <p>Примеры использования:
 * <ul>
 *   <li>{@code start -c 1 -s 2 -n 10 -f 3 -a 1}</li>
 *   <li>{@code start --class 1 --source 2 --size 10 --field 3 --algo 1}</li>
 *   <li>{@code help}</li>
 *   <li>{@code exit}</li>
 * </ul>
 */
public final class CommandParser {

    /** Маппинг коротких флагов на длинные */
    private static final Map<String, String> FLAG_ALIASES = new HashMap<>();

    static {
        FLAG_ALIASES.put("-c", "--class");
        FLAG_ALIASES.put("-s", "--source");
        FLAG_ALIASES.put("-n", "--size");
        FLAG_ALIASES.put("-f", "--field");
        FLAG_ALIASES.put("-a", "--algo");
        FLAG_ALIASES.put("-p", "--path");
    }

    private CommandParser() {
        // Утилитарный класс
    }

    /**
     * Парсит строку ввода в объект Command.
     *
     * @param input строка ввода пользователя
     * @return распарсенная команда
     * @throws CommandParseException если формат команды некорректен
     */
    public static Command parse(String input) throws CommandParseException {
        if (input == null || input.isBlank()) {
            throw new CommandParseException("Команда не может быть пустой");
        }

        String[] tokens = input.trim().split("\\s+");
        String action = tokens[0].toLowerCase();

        var builder = new Command.Builder().setAction(action);

        int i = 1;
        while (i < tokens.length) {
            String token = tokens[i];

            if (!token.startsWith("-")) {
                throw new CommandParseException(
                        "Неожиданный аргумент: '" + token + "'. Используйте флаги (-c, -s, -n, -f, -a)");
            }

            String flag = normalizeFlag(token);

            if (i + 1 >= tokens.length) {
                throw new CommandParseException("Флаг '" + token + "' требует значение");
            }

            String value = tokens[i + 1];
            applyFlag(builder, flag, value);

            i += 2;
        }

        return builder.build();
    }

    /**
     * Нормализует короткий флаг в длинный.
     */
    private static String normalizeFlag(String flag) {
        return FLAG_ALIASES.getOrDefault(flag.toLowerCase(), flag.toLowerCase());
    }

    /**
     * Применяет значение флага к Builder.
     */
    private static void applyFlag(Command.Builder builder, String flag, String value)
            throws CommandParseException {

        switch (flag) {
            case "--class" -> builder.setClassType(parseIntFlag(flag, value, 1, 5));
            case "--source" -> builder.setDataSourceType(parseIntFlag(flag, value, 1, 3));
            case "--size" -> builder.setCollectionSize(parseIntFlag(flag, value, 1, 10000));
            case "--field" -> builder.setFieldIndex(parseIntFlag(flag, value, 1, 3));
            case "--algo" -> builder.setAlgorithmType(parseIntFlag(flag, value, 1, 3));
            case "--path" -> builder.setFilePath(value);
            default -> throw new CommandParseException("Неизвестный флаг: '" + flag + "'");
        }
    }

    /**
     * Парсит целочисленное значение флага с проверкой диапазона.
     */
    private static int parseIntFlag(String flag, String value, int min, int max)
            throws CommandParseException {

        try {
            int parsed = Integer.parseInt(value);
            if (parsed < min || parsed > max) {
                throw new CommandParseException(
                        "Значение флага '" + flag + "' должно быть от " + min + " до " + max
                                + ", получено: " + parsed);
            }
            return parsed;
        } catch (NumberFormatException e) {
            throw new CommandParseException(
                    "Значение флага '" + flag + "' должно быть числом, получено: '" + value + "'");
        }
    }

    /**
     * Исключение при парсинге команды.
     */
    public static class CommandParseException extends Exception {
        public CommandParseException(String message) {
            super(message);
        }
    }
}